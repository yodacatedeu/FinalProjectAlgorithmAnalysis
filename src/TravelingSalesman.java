/*
    TravelingSalesman.java
    by Aron Bauer
    May 7, 2020
    Class for solving TravelingSalesman problem w/ best first search and branch and bound
 */
import java.util.*;

public class TravelingSalesman {
    private final int n; // number vertices
    private final int [][] W; // adjacency matrix
    
    
    public TravelingSalesman(int n, int[][] W) {
        this.n = n;
        this.W = W;
    }
    
    public int travel(ArrayList<Integer> opttour, int minlength) {
        PriorityQueue<Node> PQ; 
        Node u = new Node();
        Node v = new Node();
        PQ = new PriorityQueue(new NodeComparator());
        v.level = 0;
        v.path.add(1);
        //v.path.add(4); // testing bound function
        //v.path.add(5);
        //v.path.add(5);
        //v.path.add(4);
        //v.path.add(1);
        v.bound = bound(v);
        //System.out.println("Initial length test: " + length(v));
        //System.out.println("Initial bound test: " + v.bound);

        minlength = Integer.MAX_VALUE;
        PQ.add(v);
        while (!PQ.isEmpty()){
            // Priority Queue Test
//            System.out.println("\nPriority Queue (L = level, B = bound, P = path): ");
//            ArrayList<Node> nArr = new ArrayList<>(); // will hold the items of PQ as they are removed
//            System.out.print("\t{ "); // printing PQ
//            while (!PQ.isEmpty()) {
//                Node temp = PQ.remove(); // remove item
//                nArr.add(temp); // store it
//                System.out.print("{L: " + (temp.level));
//                System.out.print(", B: " + (temp.bound));
//                System.out.print(", P: ");
//                for (int i = 0; i < temp.path.size(); i++) {
//                    System.out.print(temp.path.get(i) + " ");
//                }
//                System.out.print("}");
//                
//                if (PQ.isEmpty())
//                    System.out.print(" }");
//                else
//                    System.out.print(", ");
//            }
//            for (int i = 0; i < nArr.size(); i++) { // put the items back where they were in PQ
//                PQ.add(nArr.get(i));
//            }
            v = PQ.remove(); // remove node from PQ with lowest bound
            // Display current node
//            System.out.println("\nCurrent minlength: " + minlength);
//            System.out.println("V:");
//            System.out.println("v.bound: " + v.bound);
//            System.out.println("v.level: " + v.level);
//            System.out.print("Path: ");
//            for (int i = 0; i < v.path.size(); i++)
//                System.out.print(v.path.get(i) + " ");
//            System.out.println("");
//            System.out.println("length: "+ length(v));
            
            if (v.bound < minlength) {
                u.level = v.level + 1; // set u to a child of v
                for (/*all i such that 2 <= i <= n && i is not in v.path*/
                        int i = 1; i < n; i++) {
                    //System.out.println("i = " + i);
                    //u.path = v.path 
                    if (!v.path.contains(i+1)) {
                        u.path.clear(); // copy v's path into u
                        for (int j = 0; j < v.path.size(); j++) {
                            u.path.add(v.path.get(j));
                        }
                        //put i at the end of u.path
                        u.path.add(i+1); 
                        // printing intial u
//                      System.out.println("Initial u: ");
//                      System.out.println("u.level: " + u.level);
//                      System.out.print("Path: ");
//                      for (int j = 0; j < u.path.size(); j++)
//                          System.out.print(u.path.get(j) + " ");
//                      System.out.println("");
                    
                        if (u.level == n - 2) { // check if next vertex completes the tour
                            //put index of only vertex not in u.path at end of u.path;
                            boolean [] inPath = new boolean[n];
                            for (boolean b: inPath) 
                                b = false;
                            for (int j = 0; j < u.path.size(); j++) { // set vertices in the path to true (should be all but one)
                                int index = u.path.get(j)-1;
                                inPath[index] = true;
                            }
                            for (int j = 0; j < inPath.length; j++) { // add the only vertex not in path to end of path
                                if (!inPath[j])
                                    u.path.add(j+1);
                            }
                            //put 1 at end of u.path // make first vertex last one
                            u.path.add(1);
                            // print this possible final u
//                            System.out.println("\nA possible final u: ");
//                            System.out.println("u.level: " + u.level);
//                            System.out.print("Path: ");
//                            for (int j = 0; j < u.path.size(); j++)
//                                System.out.print(u.path.get(j) + " ");
//                            System.out.println("");
//                            System.out.println("Length: " + length(u));
                            if (length(u) < minlength){//Function Length computes the length of the tour
                                minlength = length(u);
                                //opttour = u.path
//                                System.out.println("New opttour: ");
                                opttour.clear(); // copy u's path into optour
                                for (int j = 0; j < u.path.size(); j++) {
                                    opttour.add(u.path.get(j));
                                    //System.out.print(opttour.get(j) + " ");
                                }
                                //System.out.println();
                            } 
                        }
                        else { // a non final u
                            u.bound = bound(u);
                            // print the non final u
//                            System.out.println("A non final u: ");
//                            System.out.println("u.bound: " + u.bound);
//                            System.out.println("u.level: " + u.level);
//                            System.out.print("Path: ");                        
//                            for (int j = 0; j < u.path.size(); j++)
//                                System.out.print(u.path.get(j) + " ");
//                            System.out.println("");
                            // add u to the priority queue if its bound is less than minlength
                            if (u.bound < minlength) {
                                Node temp = new Node();
                                temp.bound = u.bound;
                                temp.level = u.level;
                                for (int j = 0; j < u.path.size(); j++) {
                                    temp.path.add(u.path.get(j));
                                }
                                PQ.add(temp);
                            }
                        }
                    }   
                }
            }
        }
        // final opttour test
//        for (int i = 0; i < opttour.size(); i++) {
//            System.out.print(opttour.get(i) + " ");
//        }
//        System.out.println(opttour.size());
        
        return minlength;
    }
    
    // calculates the bound of a node
    private int bound(Node u) {
        int result = 0;
        ArrayList<Integer> minimums = new ArrayList<>(n); // store all the minimums: first the current path, then the minimum edges of every other vertex not in the path
              
        // get the weights of the previous nodes up to the current one in the path (the length)// and add that to the list of minimums
        if (u.path.size() > 1)
            minimums.add(length(u));
        
        boolean visited[] = new boolean[n];
        for (boolean b: visited)
            b = false;
        
        for (int i = 1; i < u.path.size(); i++) { // set the verticies in path (except first verctex) to visited
            visited[u.path.get(i)-1] = true;
        }
        
        //System.out.println("");
        for (int i = 0; i < n; i++) { // find the minimum edges of every vertex (except for length 0) that is not in the path with the excpeption of the last vertex in the path
          //  System.out.print("v" + (i+1) + ": minimum(");
            boolean newMinFound = false;
            int min = Integer.MAX_VALUE;
          
            if (u.path.contains(i+1) && (i+1 != u.path.get(u.path.size()-1))) // found a visited vertex excluding the last one, do nothing
                ; // do nothing
            else { 
                for (int j = 0; j < n; j++) { // find the min edge of the vertex excluding and edge to an already visited vertex
                    if (visited[i] && j == 0) // at the last vertex in path with an edge to the first vertex, ignore this edge
                        ;
                    else if (visited[j]) // at a nonvisited vertex with an edge to a visited vertex, ignore this edge
                        ;//if do nothing    
                    else if (W[i][j] != 0) { // found a valid edge that doesn't == 0
                        //System.out.print(W[i][j] + " ");
                        if (min > W[i][j]) { // make this edge the min edge of the vertex if it is less than min
                            min = W[i][j];
                            newMinFound = true;
                        }
                    }
                    
                }
            }
            
            if (newMinFound) { // add the new found min to the list of minimums
            //    System.out.println(") = "+ min);
                minimums.add(min);
            }
            //else 
              //  System.out.println(") = ");
        }
        
        //System.out.println("Bound minimums: ");
        // add all the minimums to create the bound
        for (int i = 0; i < minimums.size(); i++) { // sum all the minimums in the list to caclulate the bound
            //System.out.println(minimums.get(i));
            result += minimums.get(i);
        }
               
        return result;
    }
    
    // calculates the length of a node
    private int length(Node u) {
        int result = 0;
        if (u.path.size() < 2) // if less then 2 verticies in the path, the length is zero
            return 0;
        for (int i = 0; i < u.path.size()-1; i++) { // search the path array (but will top before reaching last index)
            int temp = W[u.path.get(i)-1][u.path.get(i+1)-1]; // find the weight of the edge in the path
            if (temp > 0) // add to sum (result) if temp > 0 // add it to the sum (the length) if it is greater than zero
                result += temp;
        }
             
        return result;
    }
    
    // inner class Node
    protected class Node {
        public int level, bound;
        public ArrayList<Integer> path; // contains the nodes above it that are part of the solution
        
        public Node() {
            level = 0;
            bound = 0;
            path = new ArrayList<>();
        }
    }
    
    // comparator for Node: required for using Java.util.PriorityQueue
    // the lower the bound the higher the priority
    protected class NodeComparator implements Comparator<Node>{
        @Override
        public int compare(Node arg0, Node arg1) {
            if (arg0.bound < arg1.bound)
                return -1;
            else if (arg0.bound > arg1.bound)
                return 1;
            return 0;
        }
    }
    
    
}
