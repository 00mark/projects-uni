#include <stdio.h>

#include "SudokuFunctions.h"

/**
 * @brief Checks if a number can be inserted into a sudoku array
 *
 * Determines whether @p z can be inserted into @p sudoku on index
 * [@p i][@p j]
 *
 * @param z Number to be added
 * @param i Line where number is supposed to be added
 * @param j Column where number is supposed to be added
 * @param sudoku Contains the numbers (0 indicates an empty field)
 *
 * @return 1 if adding the number causes no conflicts, else 0
 */
int is_valid(int z, int i, int j, int sudoku[9][9])
{
    int n, m, x, y;

    /* determine the subregion */
    y = i / 3 * 3;
    x = j / 3 * 3;
    /* check for occurances of z in the same line */
    for(n = (i + 1) % 9; n != i; n = (n + 1) % 9)
    {
        if(sudoku[n][j] == z)
        {
            return 0;
        }
    }
    /* check for occurances of z in the same collum */
    for(m = (j + 1) % 9; m != j; m = (m + 1) % 9)
    {
        if(sudoku[i][m] == z)
        {
            return 0;
        }
    }
    /* check of occurances of z in the same subregion */
    for(n = y; n < y + 3; n++)
    {
        for(m = x; m < x + 3; m++)
        {
            if(sudoku[n][m] == z)
            {
                return 0;
            }
        }
    }

    return 1;
}

/**
 * @brief Tries to recursively solve a sudoku array
 *
 * Solves @p sudoku -if possible- by trying every combination and backtracking
 * if needed. It is assumed that up until index [@p i][@p j] @p sudoku contains
 * no empty fields.
 *
 * @param i The current line
 * @param j The current column
 * @param sudoku Contains the numbers (0 indicates an empty field)
 *
 * @return 1 if solved, else 0
 */
int solve_sudoku(int i, int j, int sudoku[9][9])
{
    int z; 

    /* locate the next empty field */
    while(i < 9 && sudoku[i][j] != 0)
    {
        if(j == 8)
        {
            j = 0;
            i++;
        }
        else
        {
            j++;
        }
    }
    /* the end has been reached => solved */
    if(i == 9)
    {
        return 1;
    }
    /* try every possibility in a depth-first manner */
    for(z = 1; z < 10; z++)
    { 
        if(is_valid(z, i, j, sudoku))
        {
            sudoku[i][j] = z;
            if(solve_sudoku(i, j, sudoku))
            {
                return 1;
            }
            /* sudoku was not solved => revert changes */
            sudoku[i][j] = 0;
        }
    }

    return 0;
}

/**
 * @brief Prints a sudoku array
 *
 * Prints @p sudoku in a typical sudoku grid, while displaying empty fields in
 * @p sudoku (indicated by 0) as actual empty strings
 *
 * @param sudoku Contains the numbers (0 indicates an empty field)
 */
void print_sudoku(int sudoku[9][9])
{
    int i, j;

    printf("\n");
    for(i = 0; i < 9; i++)
    {
        printf((i % 3) ? "|-+-+-|-+-+-|-+-+-|\n" : "+-----+-----+-----+\n");
        for(j = 0; j < 9; j++)
        {
            if(sudoku[i][j] != 0)
            {
                printf((j % 3) ? " %d" : "|%d", sudoku[i][j]);
            }
            else
            {
                printf((j % 3) ? "  " : "| ");
            }
        }
        printf("|\n");
    }
    printf("+-----+-----+-----+\n");
}