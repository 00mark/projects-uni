/*
 *  Camera.cpp
 *
 *  Created on: Nov. 04 2018
 *      Author: Thomas Wiemann
 *
 *  Copyright (c) 2018 Thomas Wiemann.
 *  Restricted usage. Licensed for participants of the course "The C++ Programming Language" only.
 *  No unauthorized distribution.
 */

#include <stdio.h>
#include <math.h>
#include <GL/glu.h>

#include "Camera.hpp"

namespace asteroids
{

Camera::Camera() : m_up(0, 1, 0), m_trans(0, 0, 0), m_l(0, 0, -1),
        m_rot(0, 0, 0), m_initial(0, 0, 0)
{
    m_turnSpeed = 0.05;
    m_moveSpeed = 1;
    glLoadIdentity();
    gluLookAt(m_initial.x, m_initial.y, m_initial.z,
              m_l.x, m_l.y, m_l.z,
              m_up.x, m_up.y, m_up.z);
}

Camera::Camera(Vector position, float turnSpeed, float moveSpeed) :
        m_up(0, 1, 0), m_trans(0, 0, 0), m_l(0, 0, -1), m_rot(0, 0, 0),
        m_initial(position.x, position.y, position.z)
{
    m_turnSpeed = turnSpeed;
    m_moveSpeed = moveSpeed;
    glLoadIdentity();
    gluLookAt(m_initial.x, m_initial.y, m_initial.z,
              m_l.x, m_l.y, m_l.z,
              m_up.x, m_up.y, m_up.z);
}

void Camera::move(CameraMovement dir)
{
    switch(dir)
    {
        /* move forward relative to the current rotation */
        case FORWARD : m_trans.x = m_moveSpeed * sin(m_rot.y);
                       m_trans.z = -m_moveSpeed * cos(m_rot.y);
                       break;
        /* move backward relative to the current rotation */
        case BACKWARD: m_trans.x = -m_moveSpeed * sin(m_rot.y);
                       m_trans.z = m_moveSpeed * cos(m_rot.y);
                       break;
        /* move left relative to the current rotation */
        case LEFT    : m_trans.x = -m_moveSpeed * cos(m_rot.y);
                       m_trans.z = -m_moveSpeed * sin(m_rot.y);
                       break;
        /* move right relative to the current rotation */
        case RIGHT   : m_trans.x = m_moveSpeed * cos(m_rot.y); 
                       m_trans.z = m_moveSpeed * sin(m_rot.y);
                       break;
        /* move up */
        case UP      : m_trans.y = -m_moveSpeed * 0.01;
                       break;
        /* move down */
        case DOWN    : m_trans.y = m_moveSpeed * 0.01;
                       break;
    }
}

void Camera::turn(CameraMovement dir)
{
    switch(dir)
    {
        /* rotate counter-clockwise around y-axis */
        case LEFT    : m_rot.y -= m_turnSpeed;
                       break;
        /* rotate clockwise around y-axis */
        case RIGHT   : m_rot.y += m_turnSpeed;
                       break;
        case FORWARD :
        case BACKWARD:
        case UP      :
        case DOWN    : break;
    }
}

void Camera::apply()
{
    /* calculate new position */
    m_initial.x += m_trans.x;
    m_initial.y += m_trans.y;
    m_initial.z += m_trans.z;

    /* calculate new look at point */
    m_l.x = m_initial.x + sin(m_rot.y);
    m_l.z = m_initial.z - cos(m_rot.y);

    glLoadIdentity();
    /* update */
    gluLookAt(m_initial.x, m_initial.y, m_initial.z,
              m_l.x, m_l.y, m_l.z,
              m_up.x, m_up.y, m_up.z);

    m_trans.x = 0;
    m_trans.y = 0;
    m_trans.z = 0;
    m_l.x = 0;
    m_l.y = 0;
    m_l.z = 0;
}

}
