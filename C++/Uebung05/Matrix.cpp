/*
 * Matrix.cpp
 *
 *  @date 18.11.2018
 *  @author Thomas Wiemann
 * 
 *  Copyright (c) 2018 Thomas Wiemann.
 *  Restricted usage. Licensed for participants of the course "The C++ Programming Language" only.
 *  No unauthorized distribution.
 */

#include "Matrix.hpp"

namespace asteroids
{

// Default constuctor
Matrix::Matrix()
{
    for(int i = 0; i < 16; i++) m[i] = 0;
    m[0] = m[5] = m[10] = m[15] = 1;
}

// Copy Constructor
Matrix::Matrix(const Matrix &mat)
{
    for(int i = 0; i < 16; i++) m[i] = mat.m[i];
}

// Construct Matrix from angle and axis
Matrix::Matrix(Vector axis, float angle)
{
    for(int i = 0; i < 16; i++) m[i] = 0;
    m[0] = axis.x * axis.x * (1-cos(angle)) + cos(angle);
    m[1] = axis.x * axis.y * (1-cos(angle)) - axis.z * sin(angle);
    m[2] = axis.x * axis.z * (1-cos(angle)) + axis.y * sin(angle);
    m[4] = axis.x * axis.y * (1-cos(angle)) + axis.z * sin(angle);
    m[5] = axis.y * axis.y * (1-cos(angle)) + cos(angle);
    m[6] = axis.y * axis.z * (1-cos(angle)) - axis.x * sin(angle);
    m[8] = axis.x * axis.z * (1-cos(angle)) - axis.y * sin(angle);
    m[9] = axis.z * axis.y * (1-cos(angle)) + axis.x * sin(angle);
    m[10] = axis.z * axis.z * (1-cos(angle)) + cos(angle);
    m[15] = 1;
}

// START Implementation of operators
Matrix& Matrix::operator=(const Matrix &mat)
{
    if(this == &mat)
    {
        return *this;
    }
    for(int i = 0; i < 16; i++) this->m[i] = mat.m[i];
    return *this;
}

Matrix& Matrix::operator+=(const Matrix &mat)
{
    for(int i = 0; i < 16; i++) this->m[i] += mat.m[i];
    return *this;
}

Matrix& Matrix::operator-=(const Matrix &mat)
{
    for(int i = 0; i < 16; i++) this->m[i] -= mat.m[i];
    return *this;
}

Matrix& Matrix::operator*=(const Matrix &mat)
{
    Matrix temp(*this);
    for(int i = 0; i < 4; i++) 
    {
        for(int j = 0; j < 4; j++)
        {
            int gatherer = 0;
            for(int k = 0; k < 4; k++)
            {
                gatherer += temp.m[i*4+k] + mat.m[k*4+j];
            }
            this->m[i*4+j] = gatherer;
        }
    }
    return *this;
}

Matrix& Matrix::operator*=(const Vector &vec)
{
    for(int j = 0; j < 4; j++)
    {
        this->m[0+j] *= vec.x;
        this->m[4+j] *= vec.y;
        this->m[8+j] *= vec.z;
        // this->m[12+j] *= 1; 4th Coordinate of Vector vec is 1
    }
    return *this;
}

Matrix Matrix::operator+(const Matrix &mat)
{
    Matrix temp(*this);
    temp += mat;
    return temp;
}

Matrix Matrix::operator-(const Matrix &mat)
{
    Matrix temp(*this);
    temp -= mat;
    return temp;
}

Matrix Matrix::operator*(const Matrix &mat)
{
    Matrix temp(*this);
    temp *= mat;
    return temp;
}

Matrix Matrix::operator*(const Vector &vec)
{
    Matrix temp(*this);
    temp *= vec;
    return temp;
}

Matrix& Matrix::operator*=(const float scale)
{
    for(int i = 0; i < 16; i++) this->m[i] *= scale;
    return *this;
}

Matrix& Matrix::operator/=(const float scale)
{
    for(int i = 0; i < 16; i++) this->m[i] /= scale;
    return *this;
}

Matrix Matrix::operator*(const float scale)
{
    Matrix temp(*this);
    temp *= scale;
    return temp;
}

Matrix Matrix::operator/(const float scale)
{
    Matrix temp(*this);
    temp /= scale;
    return temp;
}

float* Matrix::operator[](const int index)
{
    if(index >= 0 && index <= 16) 
    {
        return &this->m[index];
    } else {
        throw std::invalid_argument( "ArrayIndexOutOfBoundException when trying to access Matrix" );
    }
}

// END Implementation of operators

// Destruktor
Matrix::~Matrix()
{
    
}


} // namespace asteroids

