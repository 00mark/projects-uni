/**
 *  @file Quaternion.cpp
 *
 *  @date 18.11.2018
 *  @author Thomas Wiemann
 * 
 *  Copyright (c) 2018 Thomas Wiemann.
 *  Restricted usage. Licensed for participants of the course "The C++ Programming Language" only.
 *  No unauthorized distribution.
 */

#include "Quaternion.hpp"

namespace asteroids
{

Quaternion::Quaternion()
{
	// Default Quaternion
	x = 1.0;
	y = 0.0;
	z = 0.0;
	w = 0.0; 
}

Quaternion::Quaternion(const Quaternion &qua)
{
	x = qua.x;
	y = qua.y;
	z = qua.z;
	w = qua.w;
}

Quaternion::~Quaternion()
{
	// Do nothing
}

Quaternion::Quaternion(const Vector& vec, float angle)
{
	// Calculate the quaternion
	fromAxis(vec, angle);
}

Quaternion::Quaternion(float _x, float _y, float _z, float _angle)
{
	// Set the values
	x = _x;
	y = _y;
	z = _z;
	w = _angle;
}

Quaternion::Quaternion(float* vec, float _w)
{
	// Set the values
	x = vec[0];
	y = vec[1];
	z = vec[2];
	w = _w;
}

void Quaternion::fromAxis(const Vector& axis, float angle)
{
	float sinAngle;
	angle *= 0.5f;

	// Create a copy of the given vector and normalize the new vector
	Vector vn(axis.x, axis.y, axis.z);
	vn.normalize();
 
	// Calculate the sinus of the given angle
	sinAngle = sin(angle);
 
	// Get the quaternion
	x = (vn.x * sinAngle);
	y = (vn.y * sinAngle);
	z = (vn.z * sinAngle);
	w = cos(angle);
}

void Quaternion::normalize()
{
	// Normalize the vector
	float mag2 = w * w + x * x + y * y + z * z; 
	if (fabs(mag2 - 1.0f) > 0.00001) 
	{ 
		float mag = sqrt(mag2); 
		w /= mag; 
		x /= mag; 
		y /= mag; 
		z /= mag; 
	}  
}

// Get conjugate of Quaternion
Quaternion Quaternion::getConjugate() 
{ 
	return Quaternion(-x, -y, -z, w); 
} 

// START Implementation of operators
Quaternion& Quaternion::operator*=(const Quaternion &qua)
{
	Quaternion temp(*this);
	x = temp.w * qua.x + temp.x * qua.w + temp.y * qua.z - temp.z * qua.y;
	y = temp.w * qua.y + temp.y * qua.w + temp.z * qua.x - temp.x * qua.z; 
	z = temp.w * qua.z + temp.z * qua.w + temp.x * qua.y - temp.y * qua.x; 
	w = temp.w * qua.w - temp.x * qua.x - temp.y * qua.y - temp.z * qua.z;
	return *this;
}

Quaternion Quaternion::operator*(const Quaternion &qua)
{
	Quaternion temp(*this);
	temp *= qua;
	return temp;
}

Vector Quaternion::operator*(const Vector &vec)
{
	Vector vn(vec); 
	vn.normalize();

	Quaternion vecQuat, resQuat; 
	vecQuat.x = vn.x; 
	vecQuat.y = vn.y;
	vecQuat.z = vn.z; 
	vecQuat.w = 0.0f;

	resQuat = vecQuat * getConjugate(); 
	resQuat = *this * resQuat;

	return (Vector(resQuat.x, resQuat.y, resQuat.z));
}
// END Implementation of operators
    
} // namespace asteroids
