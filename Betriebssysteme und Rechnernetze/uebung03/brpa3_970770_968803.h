#ifndef BRPA3_IOCTL_H
#define BRPA3_IOCTL_H
#include <linux/ioctl.h>
 
#define BRPA3_SET_OPENKEY _IOW('b', 1, unsigned short *)
#define BRPA3_SET_SECRET _IOW('b', 2, unsigned short *)
#define BRPA3_GET_OPENKEY _IOR('b', 3, unsigned short *)

// return the count of numbers data consists of
static ssize_t get_num_chars(unsigned long data){
    ssize_t num_chars;

    num_chars = 0;
    while((data /= 10) != 0)
        num_chars++;
    return ++num_chars;
}

// fill buff with datas numbers
static void set_chars(char buff[], unsigned long data, ssize_t size){
    int i;
    
    for(i = size -1; i >= 0; i--){
        buff[i] =  data % 10 + 48;
        data /= 10;
    }
}

// get the numeric value of buffs chars
static unsigned long get_content_data(char buff[], size_t size){
    int i;
    unsigned long m, result;
    char c;

	m = 1;
	result = 0;
	if(size > 1 && buff[0] != 0){
        for(i = size - 2; i >= 0; i--){
            c = buff[i];
            result += (c % 48) * m;
            m *= 10;
        }
    }
    return result;
}
 
#endif
