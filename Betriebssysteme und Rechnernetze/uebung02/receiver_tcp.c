#include <sys/socket.h>
#include <stdlib.h>
#include <netinet/in.h>
#include <unistd.h>
#include <stdio.h>
#include <openssl/sha.h>
#include <string.h>
#include <arpa/inet.h>
#include "Aufgabe2.h"

#define MTU_SIZE 1400

/** 
 * combines size number of bytes from buff, starting at index, and returns
 * the new value
 */
unsigned getBits(unsigned char *buff, int index, int size){
    unsigned value;
    value = 0;
    while(size > 0){
        value = value << 8;
        value = value | buff[index++];
        size--;
    }
    return value;
}

/**
 * writes the content of file into a new file at the given path
 */
void writeFile(unsigned char *file, unsigned char *path, int filesize){
    FILE *filep;
    filep = fopen(path, "w");
    int i;
    for(i = 0; i < filesize; i++)
        fputc(file[i], filep);
    fclose(filep);
}

void usage(){
    printf("USAGE: ./receiver_tcp <ip-address> <port>\n");
}

int main(int argc, char **argv){
    struct timeval timeout;
    char *ip_addr, *port;
    int sockfd, err, len, index, left;
    struct sockaddr_in server_addr;
    unsigned char header_packet[64], content_packet[MTU_SIZE], id, *name,
                  *file, *sha_packet, *sha_comp_packet, *sha512_s;
    unsigned short length;
    unsigned int size, currentInd;
    socklen_t adderlen;

    timeout.tv_sec = 10;
    timeout.tv_usec = 0;
    if(argc != 3){
        printf("Invalid input length\n");
        usage();
        exit(0);
    }
    ip_addr = *++argv;
    if(inet_addr(ip_addr) == INADDR_NONE){
        printf("Invalid address (%s)\n", ip_addr);
        usage();
        exit(0);
    }
    port = *++argv;
    if(atoi(port) == 0 || atoi(port) > 65535){
        printf(port_error, port);
        usage();
        exit(0);
    }
    sockfd = socket(AF_INET, SOCK_STREAM, 0);
    if(sockfd < 0){
        printf("Socket Error\n");
        exit(0);
    }

    server_addr.sin_family = AF_INET;
    server_addr.sin_addr.s_addr = inet_addr(ip_addr);
    server_addr.sin_port = htons(atoi(port));

    adderlen = sizeof(struct sockaddr_in);

    // [1] connect to sender
    err = connect(sockfd, (struct sockaddr *) &server_addr, adderlen);
    if(err < 0){
        printf("Send Error\n");
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

    // [2] receive the header packet
    while(1){
        len = read(sockfd, header_packet, sizeof(header_packet));
        if(len < 0){
            if(len == -1)
                printf(timeout_error);
            else 
                printf("Receive Error\n");
            exit(0);
        }
        if(len < 8)
            continue;
        if(header_packet[0] != HEADER_T){
            printf(packet_error, header_packet[0]);
            exit(0);
        }
        break;
    }
    // get the header values
    id = getBits(header_packet, 0, sizeof(char));
    length = getBits(header_packet, 1, sizeof(short));
    name = malloc(sizeof(char) * length);
    strncpy(name, header_packet + 3, length);
    size = getBits(header_packet, length + 3, sizeof(int));

    printf(filename_str, name);
    printf(filesize_str, size);

    left = size;
    file = malloc(sizeof(char) * size);
    currentInd = 0;
    int packetsize;
    packetsize = 0;
    // [3] receive the file packet(s)
    while(left > 0){
        packetsize = left + sizeof(char) >= MTU_SIZE ? MTU_SIZE :
            left + sizeof(char);
        while(1){
            len = read(sockfd, content_packet, packetsize);
            if(len < 0){
                if(len == -1)
                    printf(timeout_error);
                else
                    printf("Receive Error\n");
            }
            if(len < packetsize)
                continue;
            if(content_packet[0] != DATA_T){
                printf(packet_error, content_packet[0]);
                exit(0);
            }
            break;
        }
        // write each packet's content (without typ-id) into file
        memcpy(file+currentInd, content_packet+1, len - 1);
        currentInd += len - sizeof(char);
        left -= (MTU_SIZE - sizeof(char)); 
    }

    sha_packet = malloc(sizeof(char) * 65);
    // [4] receive the sha packet
    while(1){
        len = read(sockfd, sha_packet, 65);
        if(len < 0){
            if(len == -1)
                printf(timeout_error);
            else
                printf("Receive Error\n");
            exit(0);
        }
        if(len < 65)
            continue;
        if(sha_packet[0] != SHA512_T){
            printf("%d", sha_packet[0]);
            printf(packet_error, sha_packet[0]);
            exit(0);
        }
        break;
    }

    // calculate the sha512 value and compare it to the received value
    sha512_s = create_sha512_string(SHA512(file, size, NULL));
    printf(receiver_sha512, sha512_s);
    sha_comp_packet = malloc(sizeof(unsigned char) * 2);
    sha_comp_packet[0] = SHA512_CMP_T;
    sha_comp_packet[1] = memcmp(sha_packet+1, 
          SHA512(file, size, NULL), 64) != 0 ? SHA512_CMP_ERROR : SHA512_CMP_OK;
    // [5] send the sha_comp packet
    while(1){
        len = write(sockfd, sha_comp_packet, 2);
        if(len < 0){
            if(len == -1)
                printf(timeout_error);
            else
                printf("Send Error\n");
            exit(0);
        }
        if(len == 2)
            break;
    }
    printf(sha_comp_packet[1] == SHA512_CMP_OK ? SHA512_OK : SHA512_ERROR);
        
    unsigned char *path;
    path = malloc(sizeof(char) * (length + 9));
    memcpy(path, "received/", 9);
    memcpy(path + 9, name, length);
    writeFile(file, path, size);
    // close the socket and free memory
    close(sockfd);
    free(file);
    free(name);
    free(sha_packet);
    free(sha_comp_packet);
    free(path);
    free(sha512_s);
}
