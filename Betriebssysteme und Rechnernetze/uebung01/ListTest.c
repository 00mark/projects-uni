#include <stdio.h>
#include <stdlib.h>
#include "ListTest.h"
#include "ListSort.h"

struct node *current, *first;
int set = FIRST_NOT_SET;
int size = 0;

void addNode(int value){
    struct node newNode;
    // first has been set
    if(set){
        current->next = malloc(sizeof(newNode));
        current = current->next;
        // first has not been set
    }else{
        first = malloc(sizeof(newNode));
        current = first; 
        set = FIRST_SET;
    }
    current->val = value;
    current->next = NULL;
    size++;
}

void printList(struct node *listPtr){
    struct node *current;
    current = listPtr;
    printf("[");
    // print each element of the list
    while(current != NULL){
        current->next == NULL ? printf("%d", current->val) : printf("%d ",
                current->val);
        current = current->next;
    }
    printf("]");
}

void clearList(struct node *listPtr){
    struct node *current;
    current = listPtr;
    // free each element of the list
    while(current != NULL){
        current = current->next;
        free(listPtr);
        listPtr = current;
    }
}

int main(int argc, char **argv){
    int i;
    struct node *b;
    // try to add each input to the list, exit if invalid
    for(i = argc; i > 1; i--){
        if(atoi(*++argv) == 0 && **argv != '0'){
            printf("invalid input\n");
            exit(0);
        }else
            addNode(atoi(*argv));
    }
    // the list has elements
    if(size > 0){
        printf("list input: ");
        printList(first);
        printf("\n");
        // sort the list
        b = mergeSort(first, size);
        printf("sorted list: ");
        printList(b);
        printf("\n");
        printf("free memory...\n");
        // free memory
        clearList(b);
    }
}
