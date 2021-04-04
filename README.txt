There is one program in this package: "FinalProject":
Contains files "Driver.java", "Knapsack.java", "Sudoku.java", "TravelingSalesman.java", "Knapsack_Input.txt", "Sudoku_Input.txt", "TSP_Input.txt", and "Final_Project.jar". 

To run this program, please install Java SE Development Kit 8 (JDK 8) or higher on your system, 
	or run each program's source code as its own project on your prefered compiler; however, this README's instructions
	only pretain to running this program through Windows Command Prompt with a JDK installed.

Link here for more info on JDK 8: 
	https://docs.oracle.com/javase/8/docs/technotes/guides/install/install_overview.html 

1. Method 1 (compile and run source code):
	To run this program:
		1. In the command prompt enter the directory of "FinalProject".
		2. Type "javac Driver.java" then press ENTER to compile the source code.
		3. Type "java Driver" then press ENTER to run the program.

2. Method 2 (Run the .jar file)
	To run this program:
		1. In the command prompt enter the directory of "FinalProject".
		2. Type "java -jar Final_Project.jar" then press ENTER to run the program.

--Note: for all input .txt files used by this program, if you wish to change them, the program expects each token to be seperated by a space, and 
  end of lines are to be signified by an "*" (view any of the three input .txt files given in this folder).  
	
	Sudoku_Input.txt: must be a 9 x 9 matrix.
	
	Knapsack_Input.txt: Profits are the first line, and weights are the second.  The number of profit tokens and weight tokens should be the 
		same.

	TSP_Input.txt: must be an n x n matirx.