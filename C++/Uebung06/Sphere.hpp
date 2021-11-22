#ifndef SPHERE
#define SPHERE

#include "Renderable3D.hpp"
#include "Vector.hpp"

namespace asteroids
{

class Sphere: public Renderable3D
{
    public:
        /**
         * @brief Construct a new Sphere object
         *
         * @param position  Initial position of the sphere
         * @param radius    Radius
         * @param numSides  Number of horizontal intersections
         * @param numStack  Number of vertical intersections
         */
        Sphere(const Vector& position, float radius, int numSides = 10,
                int numStack = 10);

        // Renders the sphere at the given position
        virtual void render();

    private:
        float m_radius;
        int m_numSides;
        int m_numStack;
};

}

#endif 
