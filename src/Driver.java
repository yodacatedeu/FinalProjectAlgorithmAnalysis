/*
    Driver.java
    by Aron Bauer
    May 7, 2020
    A driver for the implementations of Sudoku Puzzle Solver, 0/1 Knapsack Problem (best-first + branch and bound), and Traveling Salesman Problem (best-first + branch and bound)
 */
import java.io.IOException;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.*;

public class Driver {
    public static void main(String [] args) throws IOException {
        FileReader inFile;
        Scanner s;
        
        // --- Sudoku ----------------------------------------------------------------
        System.out.println("Task 1:\n");
        int [][] puzzle = new int[9][9];
        try { // get data from file
            inFile = new FileReader("Sudoku_Input.txt"); // Expecting each token to be seperated by space, and each row to end in "*"
            //inFile = new FileReader("Test.txt"); 
            s = new Scanner(inFile);
            for (int i = 0; s.hasNext(); i++) {
                boolean endOfLine = false;
                for (int j = 0; s.hasNext() && !endOfLine; j++) {
                    String temp = s.next();
                    if (temp.endsWith("*")) {
                        endOfLine = true;
                        puzzle[i][j] = Integer.parseInt(temp.substring(0, temp.length()-1));
                    }
                    else 
                        puzzle[i][j] = Integer.parseInt(temp);
                }
            }
            inFile.close();
        }
        catch (FileNotFoundException e) {
            System.err.println("File not found.");
            System.exit(-1);
        }
        
        System.out.println("Sudoku Puzzle:"); // print unsolved puzzle
        Sudoku.print(puzzle);
        
        System.out.println("\nSolution:"); // atempt to solve puzzle
        if (Sudoku.solveSudoku(puzzle, puzzle.length)) { // print if solved puzzle
            //System.out.println("");
            Sudoku.print(puzzle);
        }
        else // else state failure
            System.out.println("There is no solution");
        // --------------------------------------------------------------------------
        
        // --- 0/1 Knapsack w/ best first search and branch and bound ---------------
        System.out.println("\n*************************************************************\nTask 2:\n");
        System.out.println("Press ENTER for 0/1 Knapsack:");
        s = new Scanner(System.in);
        s.nextLine();
        int [] p;
        int [] w;
        ArrayList<Integer> pList = new ArrayList<>();
        ArrayList<Integer> wList = new ArrayList<>();

        try { // get data from file
            inFile = new FileReader("Knapsack_Input.txt"); // Expecting each token to be seperated by space, and each row to end in "*"
             
            s = new Scanner(inFile);
            for (int i = 0; s.hasNext(); i++) {
                String temp = s.next();
                if (temp.endsWith("*")) {
                   pList.add(Integer.parseInt(temp.substring(0, temp.length()-1)));
                   for (int j = 0; s.hasNext(); j++) {
                       temp = s.next();
                       if (temp.endsWith("*")) 
                            wList.add(Integer.parseInt(temp.substring(0, temp.length()-1)));
                       else
                            wList.add(Integer.parseInt(temp));
                   }
                }
                else 
                   pList.add(Integer.parseInt(temp));
            }
            inFile.close();
        }
        catch (FileNotFoundException e) {
            System.err.println("File not found.");
            System.exit(-1);
        }
        
        // put data in proper arrays
        w = new int[wList.size()];
        p = new int[pList.size()]; 
        for (int i = 0; i < pList.size() && i < wList.size(); i++) {
            w[i] = wList.get(i);
            p[i] = pList.get(i);
        }
        
        // print profits and weights
        System.out.println("Profits:");
        for (int a: p)
            System.out.print(a + " ");
        System.out.println();
        System.out.println("Weights:");
        for (int a: w)
            System.out.print(a + " ");
        System.out.println();
        
        // Get knapsack capacity
        System.out.print("\nEnter knapsack capacity: ");
        s = new Scanner(System.in);
        int W = s.nextInt();
        Integer maxProfit = -1;
        Knapsack ks = new Knapsack(p.length, W, p, w);
        ArrayList<Integer> solnList = new ArrayList<>();
        maxProfit = ks.solve(maxProfit, solnList); // solve 0/1 knapsack
        System.out.println("\nFinal Max Profit of Knapsack: " + maxProfit); // print max profit
        System.out.println("Soln list: "); // print solution list
        for (int i = 0; i < solnList.size(); i++) {
            System.out.print("Item: " + solnList.get(i));
            System.out.print(" (Profit: " + p[solnList.get(i) -1 ]);
            System.out.println(", Weight: " + w[solnList.get(i) - 1] + ")");
        }       
        // --------------------------------------------------------------------------
        
        // --- Traveling Salesman (Best First w/ Branch and bound) ------------------
        System.out.println("\n************************************************************\nTask 3\n");
        System.out.println("Press ENTER for Traveling Salesman:");
        s = new Scanner(System.in);
        s.nextLine();
        ArrayList<ArrayList<Integer>> adjMatrix = new ArrayList<>();

        try { // get data from file
            inFile = new FileReader("TSP_Input.txt"); // Expecting each token to be seperated by space, and each row to end in "*"
            //inFile = new FileReader("Test.txt"); 
            s = new Scanner(inFile);
            for (int i = 0; s.hasNext(); i++) {
                boolean endOfLine = false;
                ArrayList<Integer> tempList = new ArrayList<>();
                for (int j = 0; s.hasNext() && !endOfLine; j++) {
                    String temp = s.next();
                    if (temp.endsWith("*")) {
                        endOfLine = true;
                        tempList.add(Integer.parseInt(temp.substring(0, temp.length()-1)));
                    }
                    else 
                        tempList.add(Integer.parseInt(temp));
                }
                adjMatrix.add(tempList);
            }
            inFile.close();
        }
        catch (FileNotFoundException e) {
            System.err.println("File not found.");
            System.exit(-1);
        }
        // add the verteces in the 2d arrayList to a 2d array
        int [][] weights = new int[adjMatrix.size()][adjMatrix.size()];
        for (int i = 0; i < weights.length; i++) {
            ArrayList<Integer> tempList = adjMatrix.get(i);
            for (int j = 0; j < weights.length; j++) {
                weights[i][j] = tempList.get(j);
            }
        }
        
        // print the adjacency matrix
        System.out.println("Adjacency Matrix: ");
        for (int [] m: weights) {
            for (int j = 0; j < weights.length; j++) {
                System.out.print(m[j] + " ");
            }
            System.out.println();
        }
        TravelingSalesman tsp = new TravelingSalesman(weights.length, weights);
        int minlength = -2;
        ArrayList<Integer> optTour = new ArrayList<>();
        minlength = tsp.travel(optTour, minlength); // solve the TSP
        System.out.print("\nOptimal tour from node 1 length: ");
        System.out.println(minlength); // print min length
        System.out.println("Following the path:");
        for (int i = 0; i < optTour.size(); i++) // print optimum tour
            System.out.print(optTour.get(i) +  " ");
        System.out.println("");
        // --------------------------------------------------------------------------
    }
    
}
