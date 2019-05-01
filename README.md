# CS5700-HW4

## Improvemention change over previous homework
- Shortened code
- Changed implementation of OnlyChoice strategy to make it shorter
- Changed missingInBlock(quadRow, quadCol, missingValueSize) to missingInBlock(quadRow, quadCol)

## Scrutinized the whole code couple of times to determine what was not essential
- Removed variables that I thought were not necessary. It helps with Object Oriented Software Practice of Removing Temporary Variables.
- Altogther algorithm change and variable removal contribute about 150 lines of code.
- The tests that I had in place helped me ensure that removing something did not (visibly) break the code.

## Implemented iterator pattern.
- It makes the encapsulation stronger.
- Now you can loop through Sudoku like an iterator
- Wrote tests for these

## Implemented Command Pattern
- ReadCommand to read sudokus from a file.
- ReadWriteCommand to read sudokus from a file, solve it and write it back to output file.
- Invoker keeps track of maximum N command history to avoid overwhelming memory.
- Wrote tests for these

## Tested the 36*36 sudoku
- Tested the backtracking manually. The backtracking works as intended.
- Works for "Puzzle-36x36-01-B001.txt" The other two require too many guesses and thus too much recursion. 

## UML Diagrams
Original UML diagram with incomplete command pattern
![](https://github.com/wasimusu/CS5700-HW4/blob/master/soduko.solver/Sudoku.png)

Updated UML diagram with command pattern and iterator pattern
![](https://github.com/wasimusu/CS5700-HW4/blob/master/soduko.solver/Blocks.jpg)

Sequence diagram did not change
![](https://github.com/wasimusu/CS5700-HW4/blob/master/soduko.solver/System%20Sequence%20Diagram.jpeg)
