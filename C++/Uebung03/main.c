/*
 *  main.c
 *
 *  Created on: Nov. 04 2018
 *      Author: Thomas Wiemann
 *
 *  Copyright (c) 2018 Thomas Wiemann.
 *  Restricted usage. Licensed for participants of the course "The C++ Programming Language" only.
 *  No unauthorized distribution.
 */

#include "mainwindow.h"
#include "plyio.h"

int main(int argc, char *argv[])
{
    if(argc < 2)
    {
        fprintf(stderr, "Insufficient number of arguments\n");
        return -1;
    }
	/* Our SDL_Window ( just like with SDL2 wihout OpenGL) */
	SDL_Window *mainWindow;

	/* Our opengl context handle */
	SDL_GLContext mainContext;

	if (!createSDLWindow(&mainWindow, &mainContext))
    {
        fprintf(stderr, "Unable to open the window\n");
		return -1;
    }

	/* Init model struct with default values */
	Model* model = (Model*) malloc(sizeof(Model));
	model->numFaces = 0;
	model->numVertices = 0;
	model->indexBuffer = NULL;
	model->vertexBuffer = NULL;
    loadply(argv[1], model);

	/* Call main rendering loop */
	mainLoop(mainWindow, model);

	/* Free resources */
	cleanupSDL(mainWindow, mainContext);
    cleanup_model(model);

	return 0;
	
}

