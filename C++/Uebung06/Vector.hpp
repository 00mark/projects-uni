/**
 *  @file Vector.hpp
 *
 *  @date 18.11.2018
 *  @author Thomas Wiemann
 * 
 *  Copyright (c) 2018 Thomas Wiemann.
 *  Restricted usage. Licensed for participants of the course "The C++ Programming Language" only.
 *  No unauthorized distribution.
 */

#ifndef __Vector_HPP__
#define __Vector_HPP__

#include <iostream>
#include <cmath>

namespace asteroids
{

/**
 * @brief   Vector representation with three floats for OpenGL
 *
 */
class Vector {

public:

	/**
	 * @brief   Construcs a default Vector object
	 */
	Vector();

	/**
	 * @brief   Construcs a Vector object with given values
	 * @param x x-value
	 * @param y y-value
	 * @param z z-value
	 */
	Vector(float x, float y, float z);

	/**
	 * @brief   Normalize a Vector
	 */
	void normalize();
  
	/**
	 * @brief   Defines the vector addition
	 * @param vec vector
	 * @return vector
	 */
	Vector operator+ (const Vector vec) const;

	/**
	 * @brief   Defines the vector subtraction
	 * @param vec vector
	 * @return vector
	 */
	Vector operator- (const Vector vec) const;

	/**
	 * @brief   Construcs the scalar multiplication
	 * @param scale scalar
	 * @return vector
	 */
	Vector operator* (const float scale) const;

	/**
	 * @brief   Defines the vector multiplication
	 * @param vec vector
	 * @return result (as a float)
	 */
	float  operator* (const Vector vec) const;

	/**
	 * @brief   Defines the access to a Vector value
	 * @param index wanted value
	 * @return vectorentry (as a float)
	 */
	float operator[] (const int& index) const throw();
    
    /**
     * @brief   Defines the access to a Vector value
     * @param index wanted value
     * @return vectorentry (as a float)
     */
    float& operator[] (const int& index) throw();

	/**
	 * @brief   Defines the fast notation of vector addition
	 * @param v vector
	 */
	void operator+= (Vector v);

	/**
	 * @brief   The three values of a vector
	 */
	float x, y, z;
};
    
} // namespace asteroids

#endif
