#include "Rectangle.hpp"
#include "Renderable2D.hpp"
#include "MainWindow.hpp"

namespace asteroids
{

Rectangle::Rectangle(MainWindow* mw, float x, float y, float w, float h) :
    Renderable2D(mw, x, y)
{
    m_w = w;
    m_h = h;
}

void Rectangle::render()
{
    render_start();
    glBegin(GL_LINE_LOOP);
    glColor3f(m_r, m_g, m_b);
    glVertex2d(m_x, m_y);
    glVertex2d(m_x + m_w, m_y);
    glVertex2d(m_x + m_w, m_y + m_h);
    glVertex2d(m_x, m_y + m_h);
    glEnd();
    render_end();
}

}
