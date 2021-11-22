/*
 * Matrix.hpp
 *
 *  @date 18.11.2018
 *  @author Thomas Wiemann
 * 
 *  Copyright (c) 2018 Thomas Wiemann.
 *  Restricted usage. Licensed for participants of the course "The C++ Programming Language" only.
 *  No unauthorized distribution.
 */

#ifndef MATRIX_H_
#define MATRIX_H_

#include <iostream>
#include <fstream>
#include <iomanip>

#include "Vector.hpp"

#define _USE_MATH_DEFINES
#include <cmath>

#ifndef M_PI
#define M_PI 3.141592654
#endif
using namespace std;

namespace asteroids{

/**
 * @brief	A 4x4 matrix class implementation for use with the provided
 * 			Vector types.
 */

class Matrix {
public:

	/**
	 * @brief 	Default constructor. Initializes a identity matrix.
	 */
    Matrix();

	/**
	 * @brief 	Copies a given identity matrix.
	 */
    Matrix(const Matrix &mat);

	/**
	 * @brief	Constructs a matrix from given axis and angle. Trys to
	 * 			avoid a gimbal lock.
	 */
    Matrix(Vector axis, float angle);
	
	/// Destructor
    ~Matrix();

    
	/**
	 * @brief	Returns the internal data array. Unsafe. Will probably
	 * 			removed in one of the next versions.
	 */
	float* getData() { return m; };

	/**
	 * @brief      Assigns a Matrix to the left handed Matrix
	 * @param &mat Matrix to be assigned
	 * @return     Assigned Matrix
	 */
	Matrix& operator=(const Matrix &mat);
	
	/**
	 * @brief      Adds a Matrix to the left handed Matrix
	 * @param &mat Matrix to be added
	 * @return     Calculated Matrix
	 */
	Matrix& operator+=(const Matrix &mat);
	
	/**
	 * @brief      Subtracts a Matrix from the left handed Matrix
	 * @param &mat Matrix to be subtracted
	 * @return     Calculated Matrix
	 */
	Matrix& operator-=(const Matrix &mat);
	
	/**
	 * @brief      Multiplies a Matrix to the left handed Matrix
	 * @param &mat Matrix to be mutliplied
	 * @return     Calculated Matrix
	 */
	Matrix& operator*=(const Matrix &mat);
	
	/**
	 * @brief      Multiplies a Vector to the left handed Matrix
	 * @param &vec Vector to be multiplied
	 * @return     Calculated Matrix
	 */
	Matrix& operator*=(const Vector &vec);
	
	/**
	 * @brief      Adds up the Matrix mat and the this Matrix
	 * @param &mat Matrix to add to this
	 * @return     Calculated Matrix
	 */
	Matrix operator+(const Matrix &mat);
	
	/**
	 * @brief      Subtracts the Matrix mat from the this Matrix
	 * @param &mat Matrix to subtract from this
	 * @return     Calculated Matrix
	 */
	Matrix operator-(const Matrix &mat);
	
	/**
	 * @brief      Multiplies the Matrix mat to the this Matrix
	 * @param &mat Matrix to multiply to this
	 * @return     Calculated Matrix
	 */
	Matrix operator*(const Matrix &mat);
	
	/**
	 * @brief      Multiplies the Vector vec to the this Matrix
	 * @param &vec Vector to multiply to this
	 * @return     Calculated Matrix
	 */
	Matrix operator*(const Vector &vec);
	
	/**
	 * @brief       Scales the Matrix by the factor scale
	 * @param scale Factor value
	 * @return      Calculated Matrix
	 */
	Matrix& operator*=(const float scale);
	
	/**
	 * @brief       Scales the Matrix by the dividend scale
	 * @param scale Dividend scale
	 * @return      Calculated Matrix
	 */
	Matrix& operator/=(const float scale);
	
	/**
	 * @brief       Scales the Matrix this by the factor scale
	 * @param scale Factor value
	 * @return      Calculated Matrix
	 */
	Matrix operator*(const float scale);
	
	/**
	 * @brief       Scales the Matrix this by the dividend scale
	 * @param scale Dividend value
	 * @return      Calculated Matrix
	 */
	Matrix operator/(const float scale);
	
	/**
	 * @brief       Gets an entry of the Matrix
	 * @param index Index to be accessed
	 * @throw       std::invalid_argument when index out of range
	 * @return      Pointer to the address of the entry
	 */
	float* operator[](const int index);

private:

    /// Internal data array
	float m[16];
};



} // namespace asteroids
#endif /* MATRIX_H_ */
