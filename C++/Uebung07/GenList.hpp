#ifndef GENLIST
#define GENLIST

namespace asteroids
{

// Template for a simple generic list
template<typename T> class GenList
{
    public:
        /**
         * @brief Create a new empty list
         */
        GenList();
        /**
         * @brief Clear the list
         */
        ~GenList();
        /**
         * @brief Returns the length of the list
         * @return the length
         */
        int len();
        /**
         * @brief Inserts an item into the list at the given index
         * @param item the item to be inserted
         * @param index the index where the item will be inserted
         * @throws std::out_of_range in case of invalid index
         */
        void insert(T item, int index = 0);
        /**
         * @brief Removes the item at the given index
         * @param index the index where the item will be removed
         * @throws std::out_of_range in case of invalid index
         */
        void pop(int index = 0);
        /**
         * @brief Runs a function on each member of the list
         * @param do_something the function, that will be executed
         * @param reverse start with the first, or last element
         */
        void for_each(void (*do_something)(T& item), bool reverse = false);
        /**
         * @brief Prints the list
         */
        void print_list();

    private:
        // Current length of the list
        int m_len;
        /**
         * @brief Pointer to the node(s) of the list
         * @param content the content of the first node
         * @param pointer to the previous node, or NULL
         * @param pointer to the next node, or NULL
         */
        struct Node{
            T content;
            Node *next;
            Node *prev;
        } *m_nodes;
};

}

#endif
