/**
 *  @file Vector.cpp
 *
 *  @date 18.11.2018
 *  @author Thomas Wiemann
 * 
 *  Copyright (c) 2018 Thomas Wiemann.
 *  Restricted usage. Licensed for participants of the course "The C++ Programming Language" only.
 *  No unauthorized distribution.
 */

#include "Vector.hpp"

namespace asteroids {
    
Vector::Vector()
{
	// Default values
	x = y = z = 0.0;
}


Vector::Vector(float _x, float _y, float _z)
{
	// Set the given values
	x = _x;
	y = _y;
	z = _z; 
}

Vector::Vector(const Vector &vec)
{
	x = vec.x;
	y = vec.y;
	z = vec.z;
}

void Vector::normalize()
{
	// Normalize the vector
	float mag2 = x * x + y * y + z * z;
	if (fabs(mag2 - 1.0f) > 0.00001)
	{
		float mag = sqrt(mag2);
		x /= mag;
		y /= mag;
		z /= mag;
	}
}

// START Implementation of operators
Vector& Vector::operator=(const Vector &vec)
{
	if(this == &vec)
    {
        return *this;
    }
	this->x = vec.x;
	this->y = vec.y;
	this->z = vec.z;
	return *this;
}

Vector& Vector::operator+=(const Vector &vec)
{
	this->x += vec.x;
	this->y += vec.y;
	this->z += vec.z;
	return *this;
}

Vector& Vector::operator-=(const Vector &vec)
{
	this->x -= vec.x;
	this->y -= vec.y;
	this->z -= vec.z;
	return *this;
}

Vector Vector::operator+(const Vector &vec)
{
	Vector temp(*this);
	temp += vec;
	return temp;
}

Vector Vector::operator-(const Vector &vec)
{
	Vector temp(*this);
	temp -= vec;
	return temp;
}

Vector& Vector::operator*=(const float scale)
{
	this->x *= scale;
	this->y *= scale;
	this->z *= scale;
	return *this;
}

Vector& Vector::operator/=(const float scale)
{
	this->x /= scale;
	this->y /= scale;
	this->z /= scale;
	return *this;
}

Vector Vector::operator*(const float scale)
{
	Vector temp(*this);
	temp *= scale;
	return temp;
}

Vector Vector::operator/(const float scale)
{
	Vector temp(*this);
	temp /= scale;
	return temp;
}
// END Implementation of operators
    
} // namespace asteroids
