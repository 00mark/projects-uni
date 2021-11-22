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
	 * @brief   Copies a given Vector object
	 */
	Vector(const Vector &vec);

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
	 * @brief      Assings a Vector to left handed Vector
	 * @param &vec Vector that is assigned
	 * @return     Assigned Vector
	 */
	Vector& operator=(const Vector &vec);
	
	/**
	 * @brief      Adds a Vector to the left handed Vector
	 * @param &vec Vector that is added
	 * @return     Calculated Vector
	 */
	Vector& operator+=(const Vector &vec);
	
	/**
	 * @brief      Subtracts a Vector from the left handed Vector
	 * @param &vec Vector that is subtracted
	 * @return     Calculated Vector
	 */
	Vector& operator-=(const Vector &vec);
	
	/**
	 * @brief      Add up the Vector vec and the this Vector
	 * @param &vec Vector to add to this
	 * @return     Calculated Vector
	 */
	Vector operator+(const Vector &vec);
	
	/**
	 * @brief      Subtracts Vector vec from the this Vector
	 * @param &vec Vector to subtract from this
	 * @return     Calculated Vector
	 */
	Vector operator-(const Vector &vec);
	
	/**
	 * @brief       Scales the left handed Vector by the factor scale
	 * @param scale Factor value
	 * @return      Calculated Vector
	 */
	Vector& operator*=(const float scale);
	
	/**
	 * @brief       Scales the left handed Vector by the dividend scale
	 * @param scale Dividend value
	 * @return      Calculated Vector
	 */
	Vector& operator/=(const float scale);
	
	/**
	 * @brief       Scales the Vector this by the factor scale
	 * @param scale Factor value
	 * @return      Calculated Vector
	 */
	Vector operator*(const float scale);
	
	/**
	 * @brief       cales the this Vector by the dividend scale
	 * @param scale Dividend value
	 * @return      Calculated vector
	 */
	Vector operator/(const float scale);
  
	/**
	 * @brief   The three values of a Vector
	 */
	float x, y, z;
};
    
} // namespace asteroids

#endif
