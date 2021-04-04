/*
    Sudoku.java
    by Aron Bauer
    May 4, 2020
    Class contains the functions for solving the sudoku puzzle by bactracking
 */
class Sudoku
{ 
    // Check if placing this particular number at this position is promising
    private static boolean isPromising(int[][] board, int row, int col, int num) 
    { 
        // check if this number is unique in this row, return false if it isn't
        	for (int c = 0; c < board.length; c++) { 
            if (board[row][c] == num)  
                return false; 
	} 
	
	// check if this number is unique in this row, return false if it isn't
        	for (int r = 0; r < board.length; r++) {  
            if (board[r][col] == num) 
		return false; 
	} 

	// The number is unique in the current row and column but it may not be unique 
        // in the sub grid (a 3 x 3 section of the 9 x 9 ).  Check the entire subgrid.  
	int sqrt = (int) Math.sqrt(board.length); 
	int rowStart = row - row % sqrt; // find starting row of the square
	int colStart = col - col % sqrt; // find starting column of the square

	for (int r = rowStart; r < rowStart + sqrt; r++) { 
            for (int c = colStart; c < colStart + sqrt; c++) { 
                if (board[r][c] == num) { 
                    return false; 
		} 
            } 
	} 

	// if number is unique it is promising 
	return true; 
    } 

    // solves the sudoku puzzle given a board and size n (n x n)
    public static boolean solveSudoku(int[][] board, int n) { 
        int row = -1; 
        int col = -1; 
        boolean isEmpty = true; 
        // Find the next occurence of an empty spot in the board
        for (int i = 0; i < n && isEmpty; i++) { 
            for (int j = 0; j < n && isEmpty; j++) { 
                if (board[i][j] == 0) { 
                    // mark the row & col of the empty spot
                    row = i; 
                    col = j; 
			
                    // set the boolean that an empty spot was found
                    // boolean will be false representing not all spaces have been filled yet
                    isEmpty = false; 
                } 
            } 
        } 

        // if all spaces have been filled we are done
	if (isEmpty) { 
            return true; 
	} 

	// else try numbers 1 - 9 (starting with 9) at the found empty spot
	for (int num = n; num > 0; num--) {
            if (isPromising(board, row, col, num)) { 
                board[row][col] = num; // promising num and spot found
                if (solveSudoku(board, n)) { // check if this addition to board solves puzzle
                    //System.out.println("");
                    //print(board, n); 
                    return true; 
                } 
                else { // not a solution, back track and try the next number
                    board[row][col] = 0; // reset spot back to empty
                } 
            } 
	} 
	return false; // return false if all else fails
    } 

    // Print the board function for convienience and clear display of grid
    public static void print(int[][] board) {   
        for (int r = 0; r < board.length; r++) { 
            if ((r) % (int) Math.sqrt(board.length) == 0) {
                for (int i = 0; i < board.length * 2 + 2 * ((int) Math.sqrt(board.length)) + 1; i++) {
                    System.out.print("-");
                }
                System.out.println("");
            }
            System.out.print("| ");
            for (int c = 0; c < board[r].length; c++) { 
                if ((c + 1) % (int) Math.sqrt(board.length) == 0) { 
                   System.out.print(board[r][c] + " | "); 
                }
                else 
                    System.out.print(board[r][c] + " "); 
            } 
            System.out.println(); 
	} 
        for (int i = 0; i < board.length * 2 + 2 * ((int) Math.sqrt(board.length)) + 1; i++) {
            System.out.print("-");
        }
        System.out.println("");
    } 

} 
 
