#include <stdio.h>
#include <fcntl.h>
#include <unistd.h>
#include <sys/ioctl.h>
#include "brpa3_970770_968803.h"
 
// change the value of secret
void set_secret(int fd){
    unsigned short secr;
 
    printf("new value for secret:\n> ");
    scanf("%hu", &secr);
    if(ioctl(fd, BRPA3_SET_SECRET, &secr) == -1){
        printf("unable to set secret\n");
        printf("value has to be greater than 0 and smaller than p\n");
    }
}

// change the value of openkey_sender
void set_openkey(int fd){
    unsigned short opkey;

    printf("new value for openkey_sender:\n> ");
    scanf("%hu", &opkey);
    if(ioctl(fd, BRPA3_SET_OPENKEY, &opkey) == -1)
        printf("unable to set openkey_sender\n");
}

// get the value of openkey
void get_openkey(int fd){
    unsigned short opkey;

    if(ioctl(fd, BRPA3_GET_OPENKEY, &opkey) == -1)
        printf("unable to get openkey\n");
    else{
        printf("openkey: %hu\n", opkey);
    }
}

// print usage information
void usage(){
    printf("./brpa3_ioctl { 1, 2, 3 }\n");
    printf("1: set new value for secret\n");
    printf("2: set new value for openkey_sender\n");
    printf("3: get value of openkey\n");
}
 
int main(int argc, char **argv){
    int fd;

    if(argc == 1){
        usage();
        return -1;
    }
    fd = open("/dev/brpa3_970770_968803", O_RDWR);
    if(fd == -1){
        printf("unable to access the character-device\n");
        return -1;
    }
    // is the argument valid?
    if(argc == 2 && argv[1][0] >= 49 && argv[1][0] <= 51){
        switch(argv[1][0] % 48){
            case 1:
                set_secret(fd);
                break;
            case 2:
                set_openkey(fd);
                break;
            case 3:
                get_openkey(fd);
        }
    }
    else{
        usage();
        close(fd);
        return -1;
    }
    close(fd);
    return 0;
}
