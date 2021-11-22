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
#include "OutOfBoundsException.hpp"

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

Vector Vector::operator+(const Vector vec) const
{
	// Add value to value
	float tx = x + vec.x;
	float ty = y + vec.y;
	float tz = z + vec.z;
	return Vector(tx, ty, tz);
}

Vector Vector::operator-(const Vector vec) const
{
	// Subtract value from value
	float tx = x - vec.x;
	float ty = y - vec.y;
	float tz = z - vec.z;
	return Vector(tx, ty, tz);
}


float Vector::operator[](const int &index) const throw()
{
	
	// Get the wanted value
	if(index == 0)
	{
		return x;
	}

	if(index == 1)
	{
		return y;
	}

	if(index == 2)
	{
		return z;
	}
    throw OutOfBoundsException();
}
    
    
float& Vector::operator[](const int &index) throw()
{

    if(index == 0)
    {
        return x;
    }
        
    if(index == 1)
    {
        return y;
    }
        
    if(index == 2)
    {
        return z;
    }
    throw OutOfBoundsException();
}


    

float Vector::operator*(const Vector vec) const
{
  // Calculate the result
  return (x * vec.x + y * vec.y + z * vec.z); 
}

Vector Vector::operator*(float scale) const
{
	// Calculate the result
	float tx = x * scale;
	float ty = y * scale;
	float tz = z * scale;
	return Vector(tx, ty, tz);
}

void Vector::operator+=(Vector v)
{
	// Add value to value
	x += v.x;
	y += v.y;
	z += v.z;
}
    
} // namespace asteroids
