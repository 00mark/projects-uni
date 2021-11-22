/*
 *  Camera.hpp
 *
 *  Created on: Nov. 04 2018
 *      Author: Thomas Wiemann
 *
 *  Copyright (c) 2018 Thomas Wiemann.
 *  Restricted usage. Licensed for participants of the course "The C++ Programming Language" only.
 *  No unauthorized distribution.
 */

#ifndef __CAMERA_HPP__
#define __CAMERA_HPP__

#define PI 3.14159265
#define PH 1.57079632

namespace asteroids
{

/**
 * @brief Class to represent 3D vertices with float coordinates
 */
class Vector
{
public:
    /**
     * @brief Construct a new Vector object
     * 
     * @param ix    Initial x value (default: 0.0)
     * @param iy    Initial y value (default: 0.0)
     * @param iz    Initial z value (default: 0.0)
     */
    Vector(float ix = 0.0, float iy = 0.0, float iz = 0.0)
        : x(ix), y(iy), z(iz) {}

    float x;
    float y;
    float z;
};


/**
 * @brief Class to represent a virtual camera using gluLookAt
 * 
 */
class Camera
{
public:
    /**
     * @brief Enumeration to encode types of camera movements
     */
    enum CameraMovement 
    {
	    FORWARD,
	    BACKWARD,
	    LEFT,
	    RIGHT,
	    UP,
	    DOWN
    };

    /**
     * @brief Construct a new Camera object at (0, 0, 0) with
     *        upward orientation and lookAt at (0, 0, -1)
     * 
     */
    Camera();

    /**
     * @brief Construct a new Camera object with upward orientation
     *        and lookAt at (0, 0, -1)
     * 
     * @param position      Initial position
     * @param turnSpeed     Turning speed in radians per call
     * @param moveSpeed     Move speed in world units per call
     */
    Camera(Vector position, float turnSpeed, float moveSpeed);

    /**
     * @brief Moves the camera according to given direction
     * 
     * @param dir           Moving direction
     */
    void move(CameraMovement dir);

     /**
     * @brief turns the camera according to given direction
     * 
     * @param dir           Moving direction
     */
    void turn(CameraMovement dir);

    /**
     * @brief Calls gluLookAt with the internal parameters
     * 
     */
    void apply();

    /**
     * @brief Set the turn speed  of the camera
     * 
     * @param speed         The new turn speed
     */
    void setTurnSpeed(float speed) { m_turnSpeed = speed;}

    /**
     * @brief Set the move speed  of the camera
     * 
     * @param speed         The new move speed
     */
    void setMoveSpeed(float speed) { m_moveSpeed = speed;}
    
private:
    /// View up vector
    Vector  m_up;

    /// Translation
    Vector  m_trans;

    /// Look at vector
    Vector  m_l;

    /// Rotation angles encoded in vector, i.e., x is the
    /// rotation around the x-axis and so on
    Vector  m_rot;

    /// Initial position of the camera
    Vector  m_initial;

    /// Turn speed in radians per call
    float   m_turnSpeed;

    /// Move speed in world units per call
    float   m_moveSpeed;
};

} // namespace asteroids

#endif
