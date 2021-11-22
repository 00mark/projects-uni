#ifndef REND_3D
#define REND_3D

#include "Renderable.hpp"
#include "Vector.hpp"

namespace asteroids
{

class Renderable3D: public Renderable
{
    public:
        /**
         * @brief Constructs a new Renderable3D object with the Vector (0,0,0)
         *        as position and rgb values (1,1,1)
         */
        Renderable3D();
        /**
         * @brief Constructs a new Renderable3D object with the Vector pos 
         *        as position and rgb values (1,1,1)
         * @param pos   The position of the object
         */
        Renderable3D(const Vector& pos);
        /**
         * @brief Constructs a new Renderable3D object with the Vector (0,0,0)
         *        as position and rgb values (r,g,b)
         * @param r     Red value
         * @param g     Green value
         * @param b     Blue value
         */
        Renderable3D(float r, float g, float b);
        /**
         * @brief Constructs a new Renderable3D object with the Vector pos
         *        as position and rgb values (r,g,b)
         * @param pos   The position of the object
         * @param r     Red value
         * @param g     Green value
         * @param b     Blue value
         */
        Renderable3D(const Vector& pos, float r, float g, float b);
        /**
         * @brief Sets a new position
         * @param pos   The new position for the object
         */
        void setPos(const Vector& pos);
    protected:
        Vector m_position;
};

}
#endif
