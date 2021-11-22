#ifndef GENLIST_TCC
#define GENLIST_TCC

#include <exception>
#include <iostream>

#include "GenList.hpp"

namespace asteroids
{

template<typename T> GenList<T>::GenList() : m_len(0)
{
    m_nodes = NULL;     
}

template<typename T> GenList<T>::~GenList()
{
    Node *tmp;
    while(m_nodes)
    {
        tmp = m_nodes;
        m_nodes = m_nodes->next;
        delete tmp;
    }
}

template<typename T> int GenList<T>::len()
{
    return m_len;
}

template<typename T> void GenList<T>::insert(T item, int index)
{
    Node *tmp = m_nodes;
    Node *new_node = NULL;
    Node *new_prev = NULL;
    Node *new_next = NULL;
    int i;

    if(index > m_len || index < 0)
    {
        throw std::out_of_range("Cannot insert at the specified index");
    }

    // Get the node at the given index
    for(i = 0; i != index; i++, new_prev = tmp, tmp = tmp->next);
    new_next = tmp;
    new_node = new Node; 
    new_node->content = item;

    if(new_next)
    {
        new_next->prev = new_node;
    }
    new_node->next = new_next;

    if(new_prev)
    {
        new_prev->next = new_node;
    }
    else
    // Change start of the list in case first element was inserted
    {
        m_nodes = new_node;
    }
    new_node->prev = new_prev;

    m_len++;
}

template<typename T> void GenList<T>::pop(int index)
{
    Node* tmp = m_nodes;
    Node* new_prev = NULL;
    Node* new_next = NULL;
    int i;

    if(index < 0 || index > m_len - 1)
    {
        throw std::out_of_range("Cannot delete at the specified index");
    }

    // Get the node at the given index
    for(i = 0; i != index; i++, new_prev = tmp, tmp = tmp->next);
    new_next = tmp->next;

    if(new_next)
    {
        new_next->prev = new_prev;
    }
    if(new_prev)
    {
        new_prev->next = new_next;
    }
    // Change start of the list in case first element was removed
    else
    {
        m_nodes = new_next;
    }
    delete(tmp);
    
    m_len--;
}

template<typename T> void GenList<T>::for_each(void (*do_something)(T& item),
        bool reverse)
{
    Node *tmp = m_nodes;
    int i = 0;

    // Is the list empty ?
    if(!m_len)
    {
        return;
    }
    if(reverse)
    {
        while(i != m_len - 1)
        {
            tmp = tmp->next;
            i++;
        }
        for(; i >= 0; i--, do_something(tmp->content), tmp = tmp->prev);
    }
    else
    {
        for(; i < m_len; i++, do_something(tmp->content), tmp = tmp->next);
    }
}

template<typename T> void GenList<T>::print_list()
{
    Node *tmp = m_nodes;

    std::cout << "NULL <-";
    while(tmp)
    {
        std::cout << "-> " << tmp->content << " <-";
        tmp = tmp->next;
    }
    std::cout << "-> NULL" << std::endl;
}

}

#endif
