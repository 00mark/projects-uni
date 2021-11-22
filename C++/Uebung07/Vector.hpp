#ifndef GENVECTOR
#define GENVECTOR

namespace asteroids
{

template<typename T, int L> class Vector
{
    public:
        /**
         * @brief Constructs a new Vector, expects at least 2 and at most 3 
         *        values
         * @param x First value
         * @param y Fecond value
         * @param z Fhird value (ignored if L = 3)
         */
        Vector(T x = 0, T y = 0, T z = 0);
        /**
         * @brief Normalizes the given vector
         */
        void normalize();
        /**
         * @brief Adds a vector to this vector and returns the new vector
         * @param vec The other vector
         * @return The resulting vector
         */
        Vector operator+(const Vector& vec) const;
        /**
         * @brief Adds a vector to this vector
         * @param vec The other vector
         */
        void operator+=(const Vector& vec);
        /**
         * @brief Subtracts a vector from this vector and returns the resulting
         *        vector
         * @param vec The other vector
         */
        Vector operator-(const Vector& vec) const;
        /**
         * @brief Multiplies this vector with another vector and returns the
         *        resulting value
         * @param vec The other vector
         */
        T operator*(const Vector& vec) const;
        /**
         * @brief Multiplies this vector with a scale and returns the
         *        resulting vector
         * @param scale The scale to multiply by
         */
        Vector operator*(const T scale) const;
        /**
         * @brief Returns the element at the given index for reading
         * @param index The index
         * @throw std::invalid_argument in case of invalid index
         * @return the element at the given index
         */
        T operator[](const int& index) const;
        /**
         * @brief Returns the element at the given index for writing
         * @param index The index
         * @throw std::invalid_argument in case of invalid index
         * @return the element at the given index
         */
        T& operator[](const int& index);
    private:
        // Internal value array of generic type T and length L
        T m_vals[L];
};

typedef Vector<int, 2> Vector2i;
typedef Vector<float, 3> Vector3f;

}

#endif
