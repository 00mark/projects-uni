#include "Renderable.hpp"

namespace asteroids
{

Renderable::Renderable()
{
    m_r = 1;
    m_g = 1;
    m_b = 1;
}

Renderable::Renderable(float r, float g, float b)
{
    m_r = r;
    m_g = g;
    m_b = b;
}

void Renderable::setColor(float r, float g, float b)
{
    m_r = r;
    m_g = g;
    m_b = b;
}

}
