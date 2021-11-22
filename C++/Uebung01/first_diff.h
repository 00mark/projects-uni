#include <stdio.h>

#ifndef FIRST_DIFF
#define FIRST_DIFF

#define LINE_LEN 1000
#define ERR_ARGS_LESS -1
#define ERR_ARGS_MORE -2
#define ERR_FILE -3
#define NO_DIFF -42

void show_err(int err);
long compare(FILE *fp1, FILE *fp2);

#endif
