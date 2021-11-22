/**
 *  @file Quaternion.hpp
 *
 *  @date 18.11.2018
 *  @author Thomas Wiemann
 * 
 *  Copyright (c) 2018 Thomas Wiemann.
 *  Restricted usage. Licensed for participants of the course "The C++ Programming Language" only.
 *  No unauthorized distribution.
 */



#ifndef __QUATERNION_HPP__
#define __QUATERNION_HPP__

#include "Vector.hpp"

#include <iostream>
#include <cmath>

namespace asteroids
{

/**
 * @brief   Quaternion representation for OpenGL. Based on: 
 *          http://gpwiki.org/index.php/OpenGL:Tutorials:Using_Quaternions_to_represent_rotation
 *
 */
class Quaternion {

public:

	/**
	 * @brief   Construcs a default quaternion object
	 */
	Quaternion();

	/**
	 * @brief   Copies a given quaternion object
	 */
	Quaternion(const Quaternion &qua);

	/**
	 * @brief   Destructor
	 */
	~Quaternion();

	/**
	 * @brief   Construcs (with fromAxis()) a quaternion with a given Vector and an angle
	 * @param vec vector
	 * @param angle angle
	 */
	Quaternion(const Vector& vec, float angle);

	/**
	 * @brief   Constructs a quaternion with three given values and an angle
	 * @param x x-value
	 * @param y y-value
	 * @param z z-value
	 * @param w angle
	 */
	Quaternion(float x, float y, float z, float w);

	/**
	 * @brief   Constructs a quaternion with a given float-pointer and an angle
	 * @param vec vector(pointer)
	 * @param w angle
	 */
	Quaternion(float* vec, float w);
  
	/**
	 * @brief   Calculates a quaternion with a given vector and an angle
	 * @param axis vector
	 * @param angle angle
	 */
	void fromAxis(const Vector& axis, float angle);

	/**
	 * @brief   Normalizes the Quaternion
	 */
	void normalize();

	/**
	 * @brief   Calculates the conjugate of the Quaternion
	 * @return  Complex conjugate of the Quaternion
	 */
	Quaternion getConjugate();

	/**
	 * @brief      Rotates a vector using the this Quaternion
	 * @param &vec Vector to rotate
	 * @return     Rotated vector
	 */
	Vector operator*(const Vector &vec);
	
	/**
	 * @brief      Multiplies a Quaternion to the left handed Quaternion
	 * @param &qua Quaternion to multiply
	 * @return     Calculated Quaternion
	 */
	Quaternion& operator*=(const Quaternion &qua);
	
	/**
	 * @brief      Multiplies a Quaternion to the this Quaternion
	 * @param &qua Quaternion to multiply
	 * @return     Calculated Quaternion
	 */
	Quaternion operator*(const Quaternion &qua);
  
private:

	/**
	 * @brief   Value of angle, x, y and z
	 */
	float w, x, y, z;
};
    
} // namespace asteroids

#endif

