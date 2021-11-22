#ifndef TRI_MESH
#define TRI_MESH

#include <string>

#include "Renderable3D.hpp"
#include "Vector.hpp"
#include "Matrix.hpp"
#include "Quaternion.hpp"

namespace asteroids
{
    
class TriangleMesh: public Renderable3D
{
public:

    enum ACTION{ACCEL, STRAFE, LIFT, YAW, PITCH, ROLL};

    TriangleMesh();

    /**
     * @brief Construct a new TriangleMesh object from another instance
     * 
     * @param other         Instance to clone
     */
    TriangleMesh(const TriangleMesh& other);

    /**
     * @brief Construct a new TriangleMesh object from the given PLY file
     * 
     * @param filename      A .ply file containing the model definition
     */
    TriangleMesh(const std::string& filename);

    /**
	 * @brief   Contructs a triangle mesh from given buffers
	 * @param indexBuffer indexbuffer
	 * @param vertexBuffer vertexbuffer
	 * @param numFaces number of faces
	 * @param numVertices number of vertices
	 */
	TriangleMesh(int* indexBuffer, float* vertexBuffer, int numFaces, int numVertices);

    /**
	 * @brief   Rotates a triangle mesh at the given axis with the given speed
	 * @param axis axis to rotate
	 * @param speed speed of rotation
	 */
	void rotate(ACTION axis, float speed);

	/**
	 * @brief   Moves a triangle mesh at the given axis with the given speed
	 * @param axis axis to move
	 * @param speed speed of movement
	 */
	void move(ACTION axis, float speed);

    /**
     * @brief Prints general information (number of vertices and faces) 
     *        to stdout.
     * 
     */
    void printTriangleMeshInformation();

    /**
     * @brief Prints the contends of the internal buffers to stdout.
     * 
     */
    void printBuffers();

    /**
     * @brief Renders the model
     * 
     */
    virtual void render();

    /**
     * @brief Destroys the TriangleMesh object
     * 
     */
    ~TriangleMesh();

protected:

    void initTransformations();

    /**
	 * @brief Computes the 4x4 transformation matrix for 
     * this object (needed for OpenGL)
	 */
	void computeMatrix();

    /// Number of vertices
    int         m_numVertices;

    /// Number of faces
    int         m_numFaces;

    /// Vertexbuffer
    float*      m_vertexBuffer;

    /// Indexbuffer
    int*        m_indexBuffer;

    /// X axis of local coordinate system
    Vector      m_xAxis;

    /// Y axis of local coordinate system
    Vector      m_yAxis;

    /// Z axus of local coordinate system
    Vector      m_zAxis;

    /// Transformation matrix
    Matrix      m_transformation;

    /// Current rotation
    Quaternion  m_rotation;
};

} // namespace asteroids

#endif
