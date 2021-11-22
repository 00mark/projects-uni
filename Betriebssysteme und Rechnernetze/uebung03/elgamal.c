#include <stdlib.h>
#include <stdio.h>
#include <sys/ioctl.h>
#include <fcntl.h>
#include <unistd.h>
#include "brpa3_970770_968803.h"
#include "mod_exp.h"

// encrypt c with A^b*c % p
unsigned long encrypt(unsigned long c, unsigned short A){
    int i;
    unsigned long tmp;

    tmp = A;
    for(i = 0; i < 3; i++)
        tmp *= A;
    return tmp * c % 59;
}

int main(){
    char *buff;
    int fd, i, size;
    unsigned short opkey, secr, A;
    unsigned long current, enc;
    
    secr = 9;
    A = mod_exp(2, 9, 59);
    fd = open("/dev/brpa3_970770_968803", O_RDWR);
    if(fd == -1){
        printf("unable to access the character-device\n");
        return -1;
    }
    printf("setting secret to 9...\n");
    if(ioctl(fd, BRPA3_SET_SECRET, &secr) == -1){
        printf("unable to set secret\n");
        close(fd);
        return -1;
    }
    if(ioctl(fd, BRPA3_GET_OPENKEY, &opkey) == -1){
        printf("unable to get openkey\n");
        close(fd);
        return -1;
    }else
        printf("openkey: %hu\n", opkey);
    for(i = 1; i < 59; i++){
        enc = encrypt(i, A);
        buff = malloc(get_num_chars(enc) + 1);
        // fill buffer with the correct chars
        set_chars(buff, enc, get_num_chars(enc)); 
        buff[get_num_chars(enc)] = '\0';
        write(fd, buff, get_num_chars(enc) + 1);
        read(fd, buff, get_num_chars(enc) + 1);
        size = i < 10 ? 2 : 3;
        printf("before: %d, after: %lu\n", i, get_content_data(buff, size));
        free(buff);
    }
    close(fd);
}
