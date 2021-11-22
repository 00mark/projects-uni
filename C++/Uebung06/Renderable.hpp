#ifndef REND
#define REND

namespace asteroids
{

class Renderable
{
    public:
        /**
         * @brief Constructs a new Renderable object with rgb values (1,1,1)
         */
        Renderable();
        /**
         * @brief Constructs a new Renderable object with rgb values (r,g,b)
         */
        Renderable(float r, float g, float b);
        /**
         * @brief Specifies a new color for the object
         * @param r     Red value
         * @param g     Green value
         * @param b     Blue value
         */
        void setColor(float r, float g, float b);
        virtual void render() = 0;
        virtual ~Renderable(){}

    protected:
        float m_r;
        float m_g;
        float m_b;
};

}

#endif
