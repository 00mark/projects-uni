#ifndef fibo_h
#define fibo_h

#define FIRST_SET 1
#define FIRST_NOT_SET 0

// each node has a value and points to another node or NULL
struct node{
    int val;
    struct node *next;
};

// adds a new node to the list (or creates the first element of a list)
// with val value and next NULL
void addNode(int value);

// prints the value of each node of the list that listPtr points to
void printList(struct node *listPtr);

// frees each node of the list that listPtr points to
void clearList(struct node *listPtr);

#endif
