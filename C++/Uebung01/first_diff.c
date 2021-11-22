#include <stdlib.h>
#include <string.h>

#include "first_diff.h"

/**
 * Prints an error message 
 * @param err: int, type of error
 */
void show_err(int err)
{
    char usage[] = "Usage: [path_to]/first_diff file1 file2\n";

    switch(err){
        case ERR_ARGS_LESS : printf("Insufficient Number Of Arguments\n%s",
                                     usage);
                             break;
        case ERR_ARGS_MORE : printf("Too Many Arguments\n%s", usage);
                             break;
        case ERR_FILE      : printf("Cannot Open The Specified Files\n%s",
                                     usage);
                             break;
        default            : printf("Unknown Error\n%s", usage);
    }
}

/**
 * Compares two files
 * @param fp1: FILE pointer, points to the first file
 * @param fp2: FILE pointer, points to the second file
 * @return: long, offset of first line where files differ or NO_DIFF
 */
long compare(FILE *fp1, FILE *fp2)
{
    char s1[LINE_LEN], s2[LINE_LEN], *tmp1, *tmp2; 
    int l1;
    long off;

    s1[0] = s2[0] = '\0';
    while(!strcmp(s1, s2)){
        off = ftell(fp1);
        tmp1 = fgets(s1, LINE_LEN, fp1);
        tmp2 = fgets(s2, LINE_LEN, fp2);
        // reached the end of both files => files do not differ
        if(!tmp1 && !tmp2)
            return NO_DIFF;
        else if(!tmp1 || !tmp2)
            return off;
        l1 = strlen(s1);
        // are both lines longer than LINE_LEN?
        while(!strcmp(s1, s2) && s1[l1 - 1] != '\n' && s1[l1 - 1] != EOF){
            tmp1 = fgets(s1, LINE_LEN, fp1);
            tmp2 = fgets(s2, LINE_LEN, fp2);
            l1 = strlen(s1);
        }
    }

    return off;
}

int main(int argc, char **argv)
{
    FILE *fp1, *fp2;
    char *line1, *line2, *tmp;
    long off;
    size_t size;

    if(argc < 3){
        show_err(ERR_ARGS_LESS);
        return ERR_ARGS_LESS;
    }else if(argc > 3){
        show_err(ERR_ARGS_MORE);
        return ERR_ARGS_MORE;
    }else if((!(fp1 = fopen(argv[1], "r")) || !(fp2 = fopen(argv[2], "r")))){
        if(fp1)
            fclose(fp1);
        if(fp2)
            fclose(fp2);
        show_err(ERR_FILE);
        return ERR_FILE;
    }
    if((off = compare(fp1, fp2)) == NO_DIFF)
        printf("Files Do Not Differ\n");
    else{
        // move file position indicator to the appropriate offset
        fseek(fp1, off, SEEK_SET);
        fseek(fp2, off, SEEK_SET);
        line1 = line2 = NULL;
        size = 0;
        if(getline(&line1, &size, fp1) != -1)
            printf("[%s]\n%s\n", argv[1], line1);
        else
            printf("[%s]\n\n", argv[1]);
        if(getline(&line2, &size, fp2) != -1)
            printf("[%s]\n%s\n", argv[2], line2);
        else
            printf("[%s]\n\n", argv[2]);
        free(line1);
        free(line2);
    }
    fclose(fp1);
    fclose(fp2);

    return 0;
}
