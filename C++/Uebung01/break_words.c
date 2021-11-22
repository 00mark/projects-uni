#include <stdlib.h>
#include <stdio.h>
#include <string.h>
#include <ctype.h>

#include "break_words.h"

/**
 * reads a word from stdin
 * @param exit: int pointer, used to indicate a newline character has been read
 * @return: char pointer, the word read. Has to be freed
 */
char *get_word(int *exit)
{
    char *word;
    int i, c, size;

    i = 0;
    size = WORD_LEN;
    word = malloc(size);
    while(!isspace((c = getchar()))){
        // reallocate word if needed
        if(i >= size - 1){
            size *= 2;
            word = realloc(word, size);
        }
        word[i++] = c;
    }
    if(c == '\n')
        *exit = 1;
    word[i] = '\0';

    return word;
}

/**
 * prints a word using only putchar()
 * @param word: char pointer, points to the word
 */
void put_word(char *word)
{
    int i, len;

    len = strlen(word);
    // len > 0?
    if(len){
        for(i = 0; i < len; i++)
            putchar(word[i]);
        putchar('\n');
    }
}

int main()
{
    int exit;
    char *word;

    exit = 0;
    while(!exit){
        put_word((word = get_word(&exit)));
        free(word);
    }

    return 0;
}
