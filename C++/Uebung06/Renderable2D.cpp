#include "Renderable2D.hpp"
#include "MainWindow.hpp"

namespace asteroids
{

Renderable2D::Renderable2D(MainWindow* mainWindow) :
    Renderable2D(mainWindow, 0, 0, 1, 1, 1)
{
}

Renderable2D::Renderable2D(MainWindow* mainWindow, float x, float y) :
    Renderable2D(mainWindow, x, y, 1, 1, 1)
{
}

Renderable2D::Renderable2D(MainWindow* mainWindow, float r, float g, float b) :
    Renderable2D(mainWindow, 0, 0, r, g, b)
{
}

Renderable2D::Renderable2D(MainWindow* mainWindow, float x, float y, float r,
        float g, float b) : Renderable(r, g, b)
{
    m_mainWindow = mainWindow;
    m_x = x;
    m_y = y;
}

void Renderable2D::setPos(float x, float y)
{
    m_x = x;
    m_y = y;
}

void Renderable2D::render_start()
{
    // Enter modelview mode and save current view matrix. Set transformation to
    // identity to "undo current look at transformation
    glMatrixMode(GL_MODELVIEW);
    glPushMatrix();
    glLoadIdentity();
    // Enter projection mode and set ortho projection according to current
    // window size
    glMatrixMode(GL_PROJECTION);
    glPushMatrix();
    glLoadIdentity();
    glOrtho(0.0f, m_mainWindow->width(), m_mainWindow->height(), 0.0f, -10.0f,
            10.0f);
}

void Renderable2D::render_end()
{
    // Delete current ortho projection, enter model view mode and restore 
    // previous look at matrix
    glPopMatrix();
    glMatrixMode(GL_MODELVIEW);
    glPopMatrix();
}

}
