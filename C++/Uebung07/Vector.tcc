#ifndef VECTOR_TCC
#define VECTOR_TCC

#include <cmath>
#include <stdexcept>

#include "Vector.hpp"

namespace asteroids
{

template<typename T, int L>
Vector<T, L>::Vector(T x, T y, T z)
{
    static_assert(L == 2 || L == 3, "Vector must be of length 2 or 3");
    if(L == 3)
    {
        m_vals[0] = x;
        m_vals[1] = y;
        m_vals[2] = z;
    }
    else
    {
        m_vals[0] = x;
        m_vals[1] = y;
    }
}

template<typename T, int L>
void Vector<T, L>::normalize()
{
    // Normalize the Vector
    float mag;
    float mag2 = (L == 3 ? m_vals[0] * m_vals[0] + m_vals[1] * m_vals[1] +
            m_vals[2] * m_vals[2] :
            m_vals[0] * m_vals[0] + m_vals[1] * m_vals[1]);
    if (fabs(mag2 - 1.0f) > 0.00001)
    {
        mag = sqrt(mag2);
        m_vals[0] /= mag;
        m_vals[1] /= mag;
        if(L == 3)
        {
            m_vals[2] /= mag;
        }
    }
}

template<typename T, int L>
Vector<T, L> Vector<T, L>::operator+(const Vector& vec) const
    {
        // Add value to value
        return Vector(m_vals[0] + vec[0], m_vals[1] + vec[1], (L == 3 ? 
                    m_vals[2] + vec[2] : 0));
    }

template<typename T, int L>
Vector<T, L> Vector<T, L>::operator-(const Vector& vec)
    const
    {
        // Subract value from value
        return Vector(m_vals[0] - vec[0], m_vals[1] - vec[1], (L == 3 ?
                    m_vals[2] - vec[2] : 0));
    }

template<typename T, int L>
T Vector<T, L>::operator[](const int& index) const
{
    // Is the index valid ? 
    if(index < 0 || index >= L)
    {
        throw std::invalid_argument("Invalid Index");
    }
    // Get the wanted value
    return m_vals[index];

}

template<typename T, int L>
T& Vector<T, L>::operator[](const int &index)
{
    // Is the index valid ?
    if(index < 0 || index >= L)
    {
        throw std::invalid_argument("Invalid Index");
    }
    // Get the wanted value
    return m_vals[index];
}

template<typename T, int L>
T Vector<T, L>::operator*(const Vector& vec) const
{
    // Calculate the result
    return m_vals[0] * vec[0] + m_vals[1] * vec[1] + (L == 3 ?
            m_vals[2] * vec[2] : 0);

}

template<typename T, int L>
Vector<T, L> Vector<T, L>::operator*(const T scale) const
{
    // Calculate the result
    return Vector(m_vals[0] * scale, m_vals[1] * scale, (L == 3 ? 
                m_vals[2] * scale : 0));
}

template<typename T, int L>
void Vector<T, L>::operator+=(const Vector& vec)
{
    // Add value to value
    m_vals[0] += vec[0];
    m_vals[1] += vec[1];
    if(L == 3)
    {
        m_vals[2] += vec[2];
    }
}

}

#endif
