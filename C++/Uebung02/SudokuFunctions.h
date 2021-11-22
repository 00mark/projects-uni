#ifndef SUDOKU_FUNC
#define SUDOKU_FUNC

int is_valid(int z, int i, int j, int sudoku[9][9]);
int solve_sudoku(int i, int j, int sudoku[9][9]);
void print_sudoku(int sudoku[9][9]);

#endif
