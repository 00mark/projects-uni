#include <stdlib.h>
#include <stdio.h>
#include "ListTest.h"

struct node** split(struct node *listPtr, int size){
    int i, mid;
    struct node **buffer;

    printf("split: ");
    printList(listPtr);

    mid = (size+1)/2;
    buffer = malloc(sizeof(*listPtr)); 
    buffer[0] = listPtr;

    // let buffer[0] point to the middle element of the list
    for(i = 1; i < mid; i++)
        buffer[0] = buffer[0]->next;

    // let buffer[1] point to the next element (mid+1)
    buffer[1] = buffer[0]->next;
    buffer[0]->next = NULL;
    buffer[0] = listPtr;

    printf(" into ");
    printList(buffer[0]);
    printf(" and ");
    printList(buffer[1]);
    printf("\n");

    return buffer;
}

struct node* merge(struct node *firstH, struct node *secondH){
    struct node *current, *buffer;

    printf("merge: ");
    printList(firstH);
    printf(" and ");
    printList(secondH);
    printf("\n");

    // locate the smallest element of both lists
    if(firstH->val <= secondH->val){
        current = firstH;
        firstH = firstH->next;
    }else{
        current = secondH;
        secondH = secondH->next;
    }
    buffer = current; 

    // loop until firstH and secondH have advanced past their last elements
    while(firstH != NULL || secondH != NULL){
        if(secondH == NULL || (firstH != NULL && firstH->val <= secondH->val)){
            current->next = firstH;
            firstH = firstH->next;
        }else{
            current->next = secondH;
            secondH = secondH->next;
        }
        current = current->next;
        current->next = NULL;
    }
    return buffer;
}

struct node* mergeSort(struct node *listPtr, int size){
    struct node *merged, **splitted;

    printf("mergeSort: ");
    printList(listPtr);
    printf("\n");

    if(size == 1)
        return listPtr;
    // size > 1
    else{
        // split the list
        splitted = split(listPtr, size);
        // merge the result
        merged = merge(mergeSort(splitted[0], (size + 1) / 2),
                mergeSort(splitted[1], size - (size + 1) / 2));
        free(splitted);
        return merged;
    }
}

