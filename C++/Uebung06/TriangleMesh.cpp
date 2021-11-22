#include <iostream>
#define GL3_PROTOTYPES 1
#include <GL/glew.h>

#include "Renderable3D.hpp"
#include "TriangleMesh.hpp"
#include "PLYIO.hpp"
#include "Vector.hpp"

namespace asteroids
{

TriangleMesh::TriangleMesh() : Renderable3D(Vector(0, 0, 0))
{
    // Init member variables
	m_numFaces      = 0;
	m_numVertices   = 0;
	m_vertexBuffer  = 0;
	m_indexBuffer   = 0;

	// Setup rotation and position
	m_xAxis 		= Vector(1.0, 0.0, 0.0);
	m_yAxis 		= Vector(0.0, 1.0, 0.0);
	m_zAxis 		= Vector(0.0, 0.0, 1.0);
	//m_position		= Vector(0.0, 0.0, 0.0);

    // Init initial position
    initTransformations();
}

TriangleMesh::TriangleMesh(const TriangleMesh& other) : Renderable3D()
{
    m_numFaces = other.m_numFaces;
    m_numVertices = other.m_numVertices;
    m_vertexBuffer = new float[3 * m_numVertices];
    m_indexBuffer = new int[3 * m_numFaces];

    m_xAxis = other.m_xAxis;
    m_yAxis = other.m_yAxis;
    m_zAxis = other.m_zAxis;
    m_position = other.m_position;
    m_rotation = other.m_rotation;
    m_transformation = other.m_transformation;
}

TriangleMesh::TriangleMesh(int* faces, float* vertices, int a, int b) :
    Renderable3D()
{
	// Save mesh information and buffers
	m_numFaces      = a;
	m_numVertices   = b;
	m_vertexBuffer  = vertices;
	m_indexBuffer   = faces;

	// Init initial position
    initTransformations();
}

TriangleMesh::TriangleMesh(const std::string& filename) : Renderable3D()
{
    LoadPLY(
        filename, 
        m_vertexBuffer, m_indexBuffer, 
        m_numVertices, m_numFaces);

    // Init initial position
    initTransformations();
}

void TriangleMesh::initTransformations()
{
    int i;
    int j;
    // Setup rotation and position
	m_xAxis 		= Vector(1.0, 0.0, 0.0);
	m_yAxis 		= Vector(0.0, 1.0, 0.0);
	m_zAxis 		= Vector(0.0, 0.0, 1.0);
	m_position		= Vector(0.0, 0.0, 0.0);
	m_rotation.fromAxis(Vector(0.0, 0.0, 1.0), 0.0f);

	// Set tranformation matrix to unit matrix
	for(i = 0; i < 4; i++)
	{
	    for(j = 0; j < 4; j++)
        {
            m_transformation[i][j] = 0.0f;
        }
	}
	m_transformation[0][0] = 1.0f;
	m_transformation[1][1] = 1.0f;
	m_transformation[2][2] = 1.0f;
	m_transformation[3][3] = 1.0f;
}

void TriangleMesh::rotate(ACTION axis, float s)
{
    Quaternion nq;

	// Get the wanted operation and calculate the new coordinates
	switch(axis)
	{
	case YAW:
		nq.fromAxis(m_yAxis, s);
		m_xAxis = nq * m_xAxis;
		m_zAxis = nq * m_zAxis;
		break;

	case PITCH:
		nq.fromAxis(m_zAxis, s);
		m_xAxis = nq * m_xAxis;
		m_yAxis = nq * m_yAxis;
		break;

	case ROLL:
		nq.fromAxis(m_xAxis, s);
		m_yAxis = nq * m_yAxis;
		m_zAxis = nq * m_zAxis;
		break;
    default:
        return;
	}

	// Update mesh rotation 
	m_rotation = m_rotation * nq;
}

void TriangleMesh::move(ACTION axis, float speed)
{
    Vector direction;

	// Determine the correct axis of the local system
	switch(axis)
	{
	case ACCEL:
		direction = m_xAxis;
		break;
	case STRAFE:
		direction = m_yAxis;
		break;
	case LIFT:
		direction = m_zAxis;
        break;
    default:
        return;
	}

	// Update mesh position
	m_position = m_position + direction * speed;
}

void TriangleMesh::computeMatrix()
{
    // Compute the transformation matrix for this object
	// according to the current position and rotation
	// state
	m_transformation[0][0] = m_yAxis[0];
	m_transformation[0][1] = m_yAxis[1];
	m_transformation[0][2] = m_yAxis[2];

	m_transformation[1][0] = m_xAxis[0];
	m_transformation[1][1] = m_xAxis[1];
	m_transformation[1][2] = m_xAxis[2];

	m_transformation[2][0] = m_zAxis[0];
	m_transformation[2][1] = m_zAxis[1];
	m_transformation[2][2] = m_zAxis[2];

	m_transformation[3][0] = m_position[0];
	m_transformation[3][1] = m_position[1];
	m_transformation[3][2] = m_position[2];
}

void TriangleMesh::printTriangleMeshInformation()
{
    std::cout << "TriangleMesh statistics: " << std::endl;
    std::cout << "Num vertices: " << m_numVertices << std::endl;
    std::cout << "Num faces:    " << m_numFaces << std::endl;
}

void TriangleMesh::printBuffers()
{
    for(int i = 0; i < m_numVertices; i++)
    {
        std::cout << "v: " << m_vertexBuffer[3 * i]     << " "
                           << m_vertexBuffer[3 * i + 1] << " " 
                           << m_vertexBuffer[3 * i + 2] << std::endl;  
    }

    for(int i = 0; i < m_numFaces; i++)
    {
        std::cout << "f: " << m_indexBuffer[3 * i]     << " "
                           << m_indexBuffer[3 * i + 1] << " " 
                           << m_indexBuffer[3 * i + 2] << std::endl;  
    }
}

void TriangleMesh::render()
{
    // Compute transformation matrix
	computeMatrix();

	// Push old transformation of the OpenGL matrix stack and
	// start rendering the mesh in according to the
	// internal transformation matrix
	glPushMatrix();
	glMultMatrixf(m_transformation.getData());

	// Render mesh
	for(int i = 0; i < m_numFaces; i++)
	{
		// Get position og current triangle in buffer
		int index = 3 * i;

		// Get vertex indices of triangle vertices
		int a = 3 * m_indexBuffer[index];
		int b = 3 * m_indexBuffer[index + 1];
		int c = 3 * m_indexBuffer[index + 2];

		// Render wireframe model
		glBegin(GL_LINE_LOOP);
		glColor3f(m_r, m_g, m_b);
		glVertex3f(m_vertexBuffer[a], m_vertexBuffer[a + 1], m_vertexBuffer[a + 2]);
		glVertex3f(m_vertexBuffer[b], m_vertexBuffer[b + 1], m_vertexBuffer[b + 2]);
		glVertex3f(m_vertexBuffer[c], m_vertexBuffer[c + 1], m_vertexBuffer[c + 2]);
		glEnd();

	}

	// Pop transformation matrix of this object
	// to restore the previous state of the OpenGL
	// matrix stack
	glPopMatrix();
}

TriangleMesh::~TriangleMesh()
{
    if(m_vertexBuffer)
    {
        delete[] m_vertexBuffer;
    }

    if(m_indexBuffer)
    {
        delete[] m_indexBuffer;
    }
}

} // namespace asteroids 
