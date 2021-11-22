#ifndef SHARED_ARRAY
#define SHARED_ARRAY

#include <memory>

namespace asteroids
{

template<class T> class shared_array : public std::shared_ptr<T>
{
    public:
        shared_array() = delete;
        /**
         * @brief Creates a new shared_array instance by calling the shared_ptr
         *        constructor with a different deleter
         * @param ptr Pointer to a dynamic array of type T
         */
        shared_array(T* ptr);
        /**
         * @brief Allows reading access to an element of the array
         * @param index Index of the element
         */
        T operator[](const int& index) const;
        /**
         * @brief Allows writing access to an element of the array
         * @param index Index of the element
         */
        T& operator[](const int& index);
};

}

#include "shared_array.tcc"

#endif
