#ifndef ListSort_h
#define ListSort_h

// split the list into two parts.
// size of first part: (size+1)/2, size of second part: size - (size+1)/2
struct node** split(struct node *listPtr, int size);

// merge firstH and secondH into one list
struct node* merge(struct node *firstH, struct node *secondH);

// sort the list
struct node* mergeSort(struct node *listPtr, int size);

#endif
