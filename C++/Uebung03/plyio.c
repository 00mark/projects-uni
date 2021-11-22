/*
 *  plyio.c
 *
 *  Created on: Nov. 04 2018
 *      Author: Thomas Wiemann
 *
 *  Copyright (c) 2018 Thomas Wiemann.
 *  Restricted usage. Licensed for participants of the course "The C++ Programming Language" only.
 *  No unauthorized distribution.
 */
#include <stdlib.h>
#include <stdio.h>
#include <string.h>

#include "plyio.h"

void loadply(char *file, Model *model)
{
    FILE* fp = NULL;
    char* line = NULL;
    char* tmp1 = NULL;
    char* tmp2 = NULL;
    char is_ply[5];
    size_t len = 0;
    int i;
    int l;
    int err = 0;
    int num = 0;
    model->indexBuffer = NULL;
    model->vertexBuffer = NULL;
    model->numFaces = 0;
    model->numVertices = 0;

    /* Check if the file can be opened and validate if the file appears to be 
       a .ply file */
    if((fp = fopen(file, "r")) && (fgets(is_ply, 5, fp)) != NULL &&
                !strcmp(is_ply, "ply\n"))
    {
        /* Get lines until end of header of EOF */
        while(getline(&line, &len, fp) != -1 && strcmp(line, "end_header\n"))
        {
            /* Has numFaces and numVertices already been read ? */
            if(model->numFaces && model->numVertices)
            {
                free(line);
                line = NULL;
                continue;
            }
            /* Allocate enough space to fit the whole line, in case of 
               malformed file */
            tmp1 = (char *) malloc(strlen(line) + 1);
            tmp2 = (char *) malloc(strlen(line) + 1);
            if(sscanf(line, "%s %s %d", tmp1, tmp2, &num) == 3)
            {
                if(!strcmp(tmp1, "element"))
                {
                    if(!strcmp(tmp2, "vertex"))
                    {
                        model->numVertices = num;
                    }
                    else if(!strcmp(tmp2, "face"))
                    {
                        model->numFaces = num;
                    }
                }
            }
            free(tmp1);
            free(tmp2);
            free(line);
            line = tmp1 = tmp2 = NULL;
        }
        free(line);
        /* Did the header contain the necessary info ? */
        if(model->numFaces && model->numVertices)
        {
            model->vertexBuffer = (float *) malloc(sizeof(*model->vertexBuffer)
                    * model->numVertices * 3);
            model->indexBuffer = (int *) malloc(sizeof(*model->indexBuffer)
                    * model->numFaces * 3);
            for(i = 0, l = sizeof(float); !err && i < model->numVertices; i++)
            {
                /* Read three floats corresponding to x, y and z coordinates
                   of the current vertex */
                if(!fread(&(model->vertexBuffer[i * 3]), l, 1, fp) ||
                   !fread(&(model->vertexBuffer[i * 3 + 1]), l, 1, fp) ||
                   !fread(&(model->vertexBuffer[i * 3 + 2]), l, 1, fp))
                {
                    /* Reached EOF before the specified number of coordinates 
                       has been read => error */
                    err = 1;
                }
            }
            for(i = 0, l = sizeof(int); !err && i < model->numFaces; i++)
            {
                /* Skip the byte telling us how many indices the property
                   contains, since we only deal with triangles (i.e. three 
                   indices) */
                fgetc(fp);
                /* Read three ints corresponding to indices of the triangle */
                if(!fread(&(model->indexBuffer[i * 3]), l, 1, fp) ||
                   !fread(&(model->indexBuffer[i * 3 + 1]), l, 1, fp) ||
                   !fread(&(model->indexBuffer[i * 3 + 2]), l, 1, fp))
                {
                    /* Reached EOF before the specified number of indices 
                       has been read => error */
                    err = 1;
                }
            }
            if(err)
            {
                free(model->indexBuffer);
                free(model->vertexBuffer);
                model->indexBuffer = NULL;
                model->vertexBuffer = NULL;
                model->numFaces = model->numVertices = 0;
            }
        }
    }
    if(fp != NULL)
    {
        fclose(fp);
    }
}

void cleanup_model(Model* model)
{
    /* Free every non-NULL buffer in model */
    if(model != NULL)
    {
        if(model->indexBuffer != NULL)
        {
            free(model->indexBuffer);
        }
        if(model->vertexBuffer != NULL)
        {
            free(model->vertexBuffer);
        }
        free(model);
    }
}
