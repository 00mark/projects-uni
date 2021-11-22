#include "Renderable.hpp"
#include "Renderable3D.hpp"
#include "Vector.hpp"

namespace asteroids
{

Renderable3D::Renderable3D() : Renderable3D(Vector(0, 0, 0), 1, 1, 1)
{
}

Renderable3D::Renderable3D(const Vector& pos) : Renderable3D(pos, 1, 1, 1)
{
}

Renderable3D::Renderable3D(float r, float g, float b) :
    Renderable3D(Vector(0, 0, 0), r, g, b)
{
}

Renderable3D::Renderable3D(const Vector& pos, float r, float g, float b) :
    Renderable(r, g ,b), m_position(pos)
{
}

void Renderable3D::setPos(const Vector& pos)
{
    m_position.x = pos.x;
    m_position.y = pos.y;
    m_position.z = pos.z;
}

}
