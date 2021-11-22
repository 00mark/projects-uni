#ifndef RECTANGLE
#define RECTANGLE

#include "Renderable2D.hpp"
#include "MainWindow.hpp"

namespace asteroids
{

class Rectangle: public Renderable2D
{
    public:
        /**
         * @brief Creates a rectangle object
         * @param x     X coordinate of upper left corner
         * @param y     Y coordinate of upper left corner
         * @param w     Width
         * @param h     Height
         */
        Rectangle(MainWindow* mw, float x, float y, float w, float h);

        /**
         * @brief Renders the rectangle at the supplied position
         */
        virtual void render();

    private:
        // Width
        float m_w;
        // Height
        float m_h;
};

}

#endif
