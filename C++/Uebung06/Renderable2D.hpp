#ifndef REND_2D
#define REND_2D

#include "Renderable.hpp"
#include "MainWindow.hpp"

namespace asteroids
{

class Renderable2D: public Renderable
{
    public:
        /**
         * @brief Constructs a new Renderable2D object with the given MainWindow,
         *        x,y position (0,0) and rgb values (1,1,1)
         * @param mainWindow    Pointer to a given MainWindow instance
         */
        Renderable2D(MainWindow* mainWindow);
        /**
         * @brief Constructs a new Renderable2D object with the given MainWindow,
         *        x,y position (x,y) and rgb values (1,1,1)
         * @param mainWindow    Pointer to a given MainWindow instance
         * @param x             X position  
         * @param y             Y position
         */
        Renderable2D(MainWindow* mainWindow, float x, float y);
        /**
         * @brief Constructs a new Renderable2D object with the given MainWindow,
         *        x,y position (0,0) and rgb values (r,g,b)
         * @param mainWindow    Pointer to a given MainWindow instance
         * @param r             Red value
         * @param g             Green value
         * @param b             Blue value
         */
        Renderable2D(MainWindow* mainWindow, float r, float g, float b);
        /**
         * @brief Constructs a new Renderable2D object with the given MainWindow,
         *        x,y position (x,y) and rgb values (r,g,b)
         * @param mainWindow    Pointer to a given MainWindow instance
         * @param x             X position  
         * @param y             Y position
         * @param r             Red value
         * @param g             Green value
         * @param b             Blue value
         */
        Renderable2D(MainWindow* mainWindow, float x, float y, float r, float g,
                float b);

        /**
         * @brief Renders the object
         */
        virtual void render() = 0;
        /**
         * @brief Starts rendering a 2D object
         */
        void render_start();
        /**
         * @brief Finishes rendering a 2D object
         */
        void render_end();

        /**
         * @brief Sets the current position
         */
        void setPos(float x, float y);

    protected:
        // MainWindow instance
        MainWindow* m_mainWindow;

        // X coordinate
        float m_x;
        // Y coordinate
        float m_y;
};

}

#endif
