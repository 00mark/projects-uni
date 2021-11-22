#ifndef CIRCLE
#define CIRCLE

#include "MainWindow.hpp"
#include "Renderable2D.hpp"

namespace asteroids
{

/**
 * @brief class to create a new Circle object
 */
class Circle: public Renderable2D
{
    public:
        /**
         * @brief Creates a new Circle object
         * @param mw        MainWindow instance
         * @param x         X coordinate of the origin
         * @param y         Y coordinate of the origin
         * @param radius    Radius
         * @param segments  Number of line segments to approximate the circle
         */
        Circle(MainWindow* mw, float x, float y, float radius,
                int segments = 10);

        /**
         * @brief Renders the Circle at the specified position
         */
        virtual void render();

    private:
        // Radius of the circle
        float m_radius;
        // Number of segments
        float m_segments;
};

}

#endif
