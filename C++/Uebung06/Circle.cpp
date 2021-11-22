#include "Circle.hpp"
#include "Renderable2D.hpp"
#include "MainWindow.hpp"

namespace asteroids
{

Circle::Circle(MainWindow* mw, float x, float y, float radius, int segments) :
    Renderable2D(mw, x, y)
{
    m_radius = radius;
    m_segments = segments;
}

void Circle::render()
{
    float theta = 2 * 3.1415926 / float(m_segments);
    float tangetial_factor = tanf(theta);//calculate the tangential factor

    float radial_factor = cosf(theta);//calculate the radial factor

    float x = m_radius;//we start at angle = 0

    float y = 0;

    render_start();
    glBegin(GL_LINE_LOOP);
    for(int ii = 0; ii < m_segments; ii++)
    {
        glColor3f(m_r, m_g, m_b);
        glVertex2f(x + m_x, y + m_y);//output vertex

        //calculate the tangential vector
        //remember, the radial vector is (x, y)
        //to get the tangential vector we flip those coordinates and negate one of them

        float tx = -y;
        float ty = x;

        //add the tangential vector

        x += tx * tangetial_factor;
        y += ty * tangetial_factor;

        //correct using the radial factor

        x *= radial_factor;
        y *= radial_factor;
    }

    glEnd();
    render_end();
}

}
