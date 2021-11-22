#include <stdlib.h>
#include <stdio.h>
#include <string.h>

#include "line_mean.h"

/**
 * calculates mean of numbers in lptr
 * @param lptr: char pointer, a line. Is assumed to contain four integers
 * @return: double, mean. LINE_ERR if malformed
 */
double get_mean(char *lptr)
{
    int i1, i2, i3, i4;

    // does the line contain four ints?
    if((sscanf(lptr, "%d %d %d %d", &i1, &i2, &i3, &i4)) != 4)
        return LINE_ERR;
    return (i1 + i2 + i3 + i4) / 4.0;
}

int main()
{
    FILE *fp;
    double mean;
    char *lptr;
    size_t l;

    lptr = NULL;
    l = 0;
    // open file defined in "line_mean.h"
    if(!(fp = fopen(FILE_LOCATION, "r"))){
        printf("Could not open the file\n");
        return -1;
    }
    while(getline(&lptr, &l, fp) != -1){
        if(lptr[strlen(lptr) - 1] == '\n')
            lptr[strlen(lptr) -1] = '\0';
        if((mean = get_mean(lptr)) != LINE_ERR)
            printf("%s   %.4f\n", lptr, mean);
        else
            printf("%s   Err\n", lptr);
        free(lptr);
        lptr = NULL;
    }
    free(lptr);
    fclose(fp);

    return 0;
}
