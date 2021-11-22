/*
 *  MainWindow.cpp
 *
 *  Created on: Nov. 04 2018
 *      Author: Thomas Wiemann
 *
 *  Copyright (c) 2018 Thomas Wiemann.
 *  Restricted usage. Licensed for participants of the course "The C++ Programming Language" only.
 *  No unauthorized distribution.
 */

#include "MainWindow.hpp"
#include "Camera.hpp"

#include <iostream>

namespace asteroids
{

MainWindow::MainWindow(
    std::string title, 
    std::string plyname, int w, int h) : m_model(plyname)
{
	/* Initialize SDL's Video subsystem */
	if (SDL_Init(SDL_INIT_VIDEO) < 0)
	{
		throw "Failed to init SDL";
	}

	/* Create our window */
	m_window = SDL_CreateWindow(title.c_str(), SDL_WINDOWPOS_CENTERED,
        SDL_WINDOWPOS_CENTERED, w, h, SDL_WINDOW_OPENGL);

	/* Check that everything worked out okay */
	if (!m_window)
	{
		throw "Unable to create window";
	}

	/* Create our opengl context and attach it to our window */
	m_context = SDL_GL_CreateContext(m_window);
	
	/* Set our OpenGL version.
	   SDL_GL_CONTEXT_CORE gives us only the newer version, deprecated functions are disabled */
	SDL_GL_SetAttribute(SDL_GL_CONTEXT_PROFILE_MASK,
	        SDL_GL_CONTEXT_PROFILE_CORE);

	/* 3.2 is part of the modern versions of OpenGL, but most video cards whould be able to run it */
	SDL_GL_SetAttribute(SDL_GL_CONTEXT_MAJOR_VERSION, 3);
	SDL_GL_SetAttribute(SDL_GL_CONTEXT_MINOR_VERSION, 2);

	/* Turn on double buffering with a 24bit Z buffer.
	   You may need to change this to 16 or 32 for your system */
	SDL_GL_SetAttribute(SDL_GL_DOUBLEBUFFER, 1);

	/* This makes our buffer swap syncronized with the monitor's vertical refresh */
	SDL_GL_SetSwapInterval(1);

	/* Init GLEW */
	#ifndef __APPLE__
	glewExperimental = GL_TRUE;
	glewInit();
	#endif
	
	SDL_GL_SwapWindow(m_window);

	/* Init OpenGL projection matrix */
	glClearColor(0.0, 0.0, 0.0, 1.0);
	glMatrixMode(GL_PROJECTION);
	glLoadIdentity();
	glViewport(0, 0, w, h);
	gluPerspective(45, w * 1.0 / h, 1, 10000);

	/* Ender model view mode */

	glMatrixMode(GL_MODELVIEW);
}

void MainWindow::execute()
{
    /* create initial positon vector and camera */
    Vector vec(0, 0, 750);
    Camera cam(vec, 0.05, 10);
    bool loop = true;

	while (loop)
	{
		glClear(GL_COLOR_BUFFER_BIT);

		SDL_Event event;
		while (SDL_PollEvent(&event))
		{
		    switch(event.type)
            {
                /* handle exit */
                case SDL_QUIT:
                    loop = false;
                    break;
                case SDL_KEYDOWN:
                    /* handle movement inputs */
                    switch(event.key.keysym.sym)
                    {
                        case SDLK_w: cam.move(Camera::FORWARD); break;
                        case SDLK_s: cam.move(Camera::BACKWARD); break;
                        case SDLK_a: cam.move(Camera::LEFT); break;
                        case SDLK_d: cam.move(Camera::RIGHT); break;
                        case SDLK_j: cam.move(Camera::DOWN); break;
                        case SDLK_k: cam.move(Camera::UP); break;
                        case SDLK_h: cam.turn(Camera::LEFT); break;
                        case SDLK_l: cam.turn(Camera::RIGHT); break;
                    }
                    break;
                default:
                    break;
            }
            cam.apply();
		}

		/* Render model */
        m_model.render();

		/* Bring up back buffer */
		SDL_GL_SwapWindow(m_window);
	}
}

MainWindow::~MainWindow()
{
	/* Delete our OpengL context */
	SDL_GL_DeleteContext(m_context);

	/* Destroy our window */
	SDL_DestroyWindow(m_window);

	/* Shutdown SDL 2 */
	SDL_Quit();
}

} // namespace asteroids
