SHELL:=/bin/bash
sudoku_solver: SudokuSolver.o libSudokuFunctions.a
	gcc -Wall SudokuSolver.o -L. -lSudokuFunctions -o sudoku_solver
SudokuSolver.o: SudokuSolver.c
	gcc -Wall -c SudokuSolver.c -o SudokuSolver.o
libSudokuFunctions.a: SudokuFunctions.o
	ar rcs libSudokuFunctions.a SudokuFunctions.o
SudokuFunctions.o: SudokuFunctions.c SudokuFunctions.h
	gcc -Wall -c SudokuFunctions.c -o SudokuFunctions.o
clean:
	rm sudoku_solver ./*.{o,a}
