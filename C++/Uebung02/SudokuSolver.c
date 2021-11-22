#include <stdio.h>

#include "SudokuFunctions.h"

/**
 * @brief Creates a sudoku array prints it, tries to solve it and prints again
 * if successful
 *
 * Tries to solve a predefined sudoku array by using functions defined in
 * "SudokuFunctions.h" 
 *
 * @return 0
 */
int main()
{
    int sudoku[9][9] = {{0, 0, 0, 0, 0, 8, 0, 3, 0},
                        {0, 3, 0, 5, 0, 0, 4, 7, 1},
                        {2, 0, 0, 1, 0, 0, 6, 9, 0},
                        {5, 0, 0, 0, 0, 2, 1, 0 ,0},
                        {1, 2 ,4, 0, 0, 0, 9, 6, 3},
                        {0, 0, 6, 4, 0, 0, 0, 0, 2},
                        {0, 8, 9, 0 ,0, 5, 0, 0, 7},
                        {3, 5, 2, 0, 0, 9, 0, 4, 0},
                        {0, 1, 0, 3, 0, 0, 0, 0, 0}};

    print_sudoku(sudoku);
    /* try to solve */
    if(solve_sudoku(0, 0, sudoku))
    {
        print_sudoku(sudoku);
    }
    else
    {
        printf("\nCould not find a solution\n");
    }
    
    return 0;
}
