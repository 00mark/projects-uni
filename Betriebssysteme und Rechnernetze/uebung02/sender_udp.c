#include <stdlib.h>
#include <netinet/in.h>
#include <unistd.h>
#include <stdio.h>
#include <openssl/sha.h>
#include <string.h>
#include <arpa/inet.h>
#include "Aufgabe2.h"

#define MTU_SIZE 1400

char *port, *path, *filename;
FILE *filep;

/**
 * returns the name of the newly created archive without the preceding path
 */
char* getFileName(char *path){
    int i, ending_slash;
    char *newpath;
    ending_slash = 0;
    for(i = strlen(path) - 1; i > 0; i--)
        if(path[i] == '/'){
            if(i == strlen(path) - 1)
                ending_slash = 1;
            else{
                i++;
                break;
            }
        }
    for(; i > 0; i--)
        path++;
    newpath = malloc((strlen(path)+ strlen(".tar.gz") + 1
                - ending_slash) * sizeof(char));
    memcpy(newpath, path, strlen(path) - ending_slash);
    memcpy(newpath + strlen(path) - ending_slash, ".tar.gz", strlen(".tar.gz"));
    newpath[strlen(path) + strlen(".tar.gz") - ending_slash] = '\0';
    return newpath;
}

/**
 * devides the bits of content into size different bytes and writes them
 * into buff
 */
int setBits(unsigned char *buff, unsigned content, int index, int size){
    while(size > 0){
        size--;
        buff[index++] = content >> size*8;
    }
    return index;
}

unsigned short getFileNameLength(char *fname){
    return strlen(fname); 
}

/**
 * returns the size of the file located at path
 */
unsigned int getFileSize(char *path){
    int size;
    fseek(filep, 0, SEEK_END);
    size = ftell(filep);
    rewind(filep);
    return size;
}

/**
 * sets the bytes from index to size of packet
 */
int setPacket(unsigned char *packet, int index, int size){
    char c;
    int i;
    i = index;
    for(; size > i; size--){
        c = fgetc(filep);
        packet[index++] = c;
    }
    return index;
}

/**
 * sets the filename
 */
int setFileName(char *name, int i, int j){
    int diff = i;
    for(; i < j; i++)
        name[i] = filename[i-diff];
    return i;
}

void usage(){
    printf("USAGE: ./sender_udp <port> <filepath>\n");
}

int main(int argc, char **argv){
    struct timeval timeout;
    int sockfd, err, len, index, left, currentInd;
    struct sockaddr_in addr, from, dest;
    unsigned char request_packet[1], packet[MTU_SIZE], *file, *sha_packet,
                  *sha512_s, *sha_comp_packet, content_packet[MTU_SIZE];
    unsigned flen;
    unsigned int sequencenr, packetsize;
    char *pathtmp, *command;

    timeout.tv_sec = 10;
    timeout.tv_usec = 0;
    if(argc != 3){
        printf("Invalid input length\n");
        usage();
        exit(0);
    }
    port = *++argv;
    if(atoi(port) == 0 || atoi(port) > 65535){
        printf(port_error, port);
        usage();
        exit(0);
    }
    pathtmp = *++argv;
    filename = getFileName(pathtmp);
    // use shell command to archive the given file
    command = malloc((strlen("tar -cjf ") + getFileNameLength(filename) + 1 +
                strlen(pathtmp)) * sizeof(char) + 1);
    memcpy(command, "tar -cjf ", 10);
    memcpy(command + 9, filename, getFileNameLength(filename));
    memcpy(command + 9 + getFileNameLength(filename), " ", 1);
    memcpy(command + 9 + getFileNameLength(filename) + 1, pathtmp,
            strlen(pathtmp));
    command[9 + getFileNameLength(filename) + strlen(pathtmp) + 1] = '\0';
    system(command);
    path = filename;
    filep = fopen(path, "r");

    sockfd = socket(AF_INET, SOCK_DGRAM, 0);
    if(sockfd < 0){
        printf("Socket Error\n");
        exit(0);
    }
    addr.sin_family = AF_INET;
    addr.sin_port = htons(atoi(port));
    addr.sin_addr.s_addr = htonl(INADDR_ANY);

    err = bind(sockfd, (struct sockaddr *) &addr, sizeof(struct sockaddr_in));
    if(err < 0){
        printf("Bind Error\n");
        exit(0);
    }

    flen = sizeof(struct sockaddr_in);
    // [1] receive the request packet
    len = recvfrom(sockfd, request_packet, sizeof(request_packet), 0,
            (struct sockaddr *) &from, &flen);
    if(len < 0){
        if(len == -1)
            printf(timeout_error);
        else
            printf("Receive Error\n");
        exit(0);
    }
    if(request_packet[0] != REQUEST_T){
        printf(packet_error, request_packet[0]);
        exit(0);
    }

    // set timeout
    if(setsockopt(sockfd, SOL_SOCKET, SO_RCVTIMEO, (char *) &timeout,
            sizeof(struct timeval)) < 0){
        printf("Unable to set timeout");    
        exit(0);
    }
    if(setsockopt(sockfd, SOL_SOCKET, SO_SNDTIMEO, (char *) &timeout,
            sizeof(struct timeval)) < 0){
        printf("Unable to set timeout");    
        exit(0);
    }

    unsigned char header_packet[getFileNameLength(filename) + sizeof(char) +
        sizeof(short) + sizeof(int)];
    index = setBits(header_packet, HEADER_T, 0, sizeof(char));
    index = setBits(header_packet, getFileNameLength(filename), index,
            sizeof(short));
    index = setFileName(header_packet, index,
            index + getFileNameLength(filename));
    index = setBits(header_packet, getFileSize(path), index, sizeof(int));

    printf(filename_str, filename);
    printf(filesize_str, getFileSize(path));

    dest.sin_family = AF_INET;
    dest.sin_addr.s_addr = from.sin_addr.s_addr;
    dest.sin_port = from.sin_port;

    // [2] send the header packet
    err = sendto(sockfd, header_packet, index + 1, 0, (struct sockaddr *) &dest,
            sizeof(struct sockaddr_in));
    if(err < 0){
        if(err == -1)
            printf(timeout_error);
        else
            printf("Send Error\n");
        exit(0);
    }

    left = getFileSize(path);
    file = malloc(sizeof(char) * left);
    currentInd = 0;
    sequencenr = 0;
    content_packet[0] = DATA_T;
    // [3] send the file packet(s)
    while(left > 0){
        packetsize = left + sizeof(char) + sizeof(int) >= MTU_SIZE ? MTU_SIZE :
            left + sizeof(char) + sizeof(int);
        setBits(content_packet, sequencenr, 1, sizeof(int));
        setPacket(content_packet, sizeof(char) + sizeof(int), packetsize);
        err = sendto(sockfd, content_packet, packetsize, 0,
                (struct sockaddr *) &dest, sizeof(struct sockaddr_in));
        if(err < 0){
            if(err == -1)
                printf(timeout_error);
            else
                printf("Send Error\n");
            exit(0);
        }
        memcpy(file+currentInd, content_packet+5, packetsize - 5);
        currentInd += packetsize - (sizeof(char) + sizeof(int));
        sequencenr++;
        left -= (MTU_SIZE - (sizeof(char) + sizeof(int)));
    }
    
    sha_packet = malloc(sizeof(char) * 65);
    sha_packet[0] = SHA512_T;
    memcpy(sha_packet+1, SHA512(file, getFileSize(path), NULL), 64);
    // [4] send the sha packet
    err = sendto(sockfd, sha_packet, 65, 0,  
            (struct sockaddr *) &dest, sizeof(struct sockaddr_in));
    if(err < 0){
        if(err == -1)
            printf(timeout_error);
        else
            printf("Send Error\n");
        exit(0);
    }

    sha512_s = create_sha512_string(sha_packet+1);
    printf(sender_sha512, sha512_s);

    sha_comp_packet = malloc(sizeof(char) * 2);
    // [5] receive the sha_comp packet
    len = recvfrom(sockfd, sha_comp_packet, 2, 0, (struct sockaddr *) &from,
            &flen);
    if(len < 0){
        if(len == -1)
            printf(timeout_error);
        else
            printf("Receive Error\n");
        exit(0);
    }
    if(sha_comp_packet[0] != SHA512_CMP_T){
        printf(packet_error, sha_comp_packet[0]);
        exit(0);
    }
    printf(sha_comp_packet[1] == SHA512_CMP_OK ? SHA512_OK : SHA512_ERROR);

    // close file, socket and free memory
    fclose(filep);
    close(sockfd);
    free(filename);
    free(command);
    free(sha_packet);
    free(file);
    free(sha_comp_packet);
    free(sha512_s);
}
