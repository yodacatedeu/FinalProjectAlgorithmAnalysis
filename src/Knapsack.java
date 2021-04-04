/*
    Knapsack.java
    by Aron Bauer
    May 6, 2020
    Class fo solving 0/1 knapsack w/ best first search and branch and bound
 */
import java.util.*;

public class Knapsack {
    private final int n, W;
    private final int [] p, w;
    
    public Knapsack(int n, int W, int [] p, int [] w) {
        this.n = n;
        this.W = W;
        this.p = p;
        this.w = w;
    }
    
    // Branch and Bound w/ best search 0/1 Knapsack algorithm
    public int solve(Integer maxProfit, ArrayList<Integer> solnList) {
        PriorityQueue<Node> PQ;
        Node u = new Node();
        Node v = new Node();
        PQ = new PriorityQueue<>(new NodeComparator()); // Initialize PQ to be empty
        v.level = -1; v.profit = 0; v.weight = 0; // Initialize v to the root
        maxProfit = 0;
        v.bound = bound(v);
        PQ.add(v);
        while (!PQ.isEmpty()){
            // Print PQ and current node -------------------------------------------
            System.out.println("\nPriority Queue (P = node's profit, W = node's weight, B = node's bound): ");
            ArrayList<Node> nArr = new ArrayList<>(); // will hold the items of PQ as they are removed
            System.out.print("\t{ "); // printing PQ
            while (!PQ.isEmpty()) {
                Node temp = PQ.remove(); // remove item
                nArr.add(temp); // store it
                System.out.print("{P: " + (temp.profit));
                System.out.print(", W: " + (temp.weight));
                System.out.print(", B: " + temp.bound + "}");
                if (PQ.isEmpty())
                    System.out.print(" }");
                else
                    System.out.print(", ");
            }
            for (int i = 0; i < nArr.size(); i++) { // put the items back where they were in PQ
                PQ.add(nArr.get(i));
            }        
            v = PQ.remove(); // Remove node with the best bound
            // Prining current Node
            System.out.println("\nCurrent Node:");
            //System.out.println("\tLevel: " + (v.level + 1));
            System.out.println("\tProfit: " + v.profit);
            System.out.println("\tWeight: " + v.weight);
            System.out.println("\tBound: " + v.bound);
            System.out.println("Max profit: " + maxProfit);
            // -------------------------------------------------------
            if (v.bound > maxProfit){               // Check if node is still promising
                u.level = v.level + 1;              // Set u to the child
                u.weight = v.weight + w[u.level];   // that includes the
                u.profit = v.profit + p[u.level];   // next item.
                
                u.solnList.clear(); // copy soln list from parent
                for (int i = 0; i < v.solnList.size(); i++) {
                    u.solnList.add(v.solnList.get(i));
                }
                
//                System.out.println("For U (including next item:\nProfit: " + u.profit);
//                System.out.println("Weight: " + u.weight);
                if (u.weight <= W && u.profit > maxProfit) { // we've possibly found the max profit
                    maxProfit = u.profit;
                    solnList.clear(); // update true soln list (level corresponds to the item in the knapsack)
                    for (int i = 0; i < u.solnList.size(); i++) {
                        solnList.add(u.solnList.get(i));
                    }
                    solnList.add(u.level + 1);
                }
                //System.out.println("New max profit: " + maxProfit);
                u.bound = bound(u); // u[date u's bound
                //System.out.println("Bound: " + u.bound);
                if (u.bound > maxProfit) { // add a copy of u to PQ if it's promising
                    u.solnList.add(u.level + 1); // add the node's level to its soln list (for itself is part of the promising soln) 
                    Node temp = new Node();
                    temp.bound = u.bound;
                    temp.level = u.level;
                    temp.profit = u.profit;
                    temp.weight = u.weight;
                    for (int i = 0; i < u.solnList.size(); i++) {
                        temp.solnList.add(u.solnList.get(i));
                    }
                    PQ.add(temp);
                }
                
                // Print updated priority Queue
                System.out.println("\nPriority Queue (P = node's profit, W = node's weight, B = node's bound): ");
                nArr = new ArrayList<>(); // will hold the items of PQ as they are removed
                System.out.print("\t{ "); // printing PQ
                while (!PQ.isEmpty()) {
                    Node temp = PQ.remove(); // remove item
                    nArr.add(temp); // store it
                    System.out.print("{P: " + (temp.profit));
                    System.out.print(", W: " + (temp.weight));
                    System.out.print(", B: " + temp.bound + "}");
                    if (PQ.isEmpty())
                        System.out.print(" }");
                    else
                        System.out.print(", ");
                }
                for (int i = 0; i < nArr.size(); i++) { // put the items back where they were in PQ
                    PQ.add(nArr.get(i));
                }
                
                // Prining current Node
                System.out.println("\nChild Node including next item:");
                //System.out.println("\tLevel: " + (u.level));
                System.out.println("\tProfit: " + u.profit);
                System.out.println("\tWeight: " + u.weight);
                System.out.println("\tBound: " + u.bound);
                System.out.println("Max profit: " + maxProfit);
                
                u.weight = v.weight; // Set u to the child
                u.profit = v.profit; // that does not include
                u.bound = bound(u);  // the next item.
                //System.out.println("For U (excluding next item:\nProfit: " + u.profit);
                //System.out.println("Weight: " + u.weight);
                //System.out.println("Bound: " + u.bound);
                u.solnList.clear(); // restore u's soln list back to that of v
                for (int i = 0; i < v.solnList.size(); i++) {
                    u.solnList.add(v.solnList.get(i));
                }
                if (u.bound > maxProfit) { // if u (without the item at this level) is promising, add a copy of it to PQ
                    Node temp = new Node();
                    temp.bound = u.bound;
                    temp.level = u.level;
                    temp.profit = u.profit;
                    temp.weight = u.weight;
                    for (int i = 0; i < u.solnList.size(); i++) {
                        temp.solnList.add(u.solnList.get(i));
                    }
                    PQ.add(temp);
                }
                
                // Print updated Priority Queue
                System.out.println("\nPriority Queue (P = node's profit, W = node's weight, B = node's bound): ");
                nArr = new ArrayList<>(); // will hold the items of PQ as they are removed
                System.out.print("\t{ "); // printing PQ
                while (!PQ.isEmpty()) {
                    Node temp = PQ.remove(); // remove item
                    nArr.add(temp); // store it
                    System.out.print("{P: " + (temp.profit));
                    System.out.print(", W: " + (temp.weight));
                    System.out.print(", B: " + temp.bound + "}");
                    if (PQ.isEmpty())
                        System.out.print(" }");
                    else
                        System.out.print(", ");
                }
                for (int i = 0; i < nArr.size(); i++) { // put the items back where they were in PQ
                    PQ.add(nArr.get(i));
                }
                
                // Prining current Node
                System.out.println("\nChild Node excluding next item:");
                //System.out.println("\tLevel: " + (u.level));
                System.out.println("\tProfit: " + u.profit);
                System.out.println("\tWeight: " + u.weight);
                System.out.println("\tBound: " + u.bound);
                System.out.println("Max profit: " + maxProfit);
            }
        }   
        return maxProfit;
    }
    
    // find the bound of a node
    private float bound(Node u) {
        int j, k;
        int totweight;
        float result;
        if (u.weight >= W)
            return 0; // return 0 for bound if the node is nonpromising
        else {
            result = u.profit;
            j = u.level + 1;
            totweight = u.weight;
            while (j < n && totweight + w[j] <= W) {
                totweight = totweight + w[j]; //Grab as many items as possible
                result = result + p[j];
                j++;
            }
            k = j; // Use k for consistency with formula in text
            if (k < n)
                result = result + (W - totweight) * ((float) p[k] / (float) w[k]); //Grab fraction of kth item
            return result;
        }
    }
    
    // inner class Node
    protected class Node {
        public int level, profit, weight;
        public float bound;
        ArrayList<Integer> solnList; // contains the nodes above it that are part of the solution
        public Node() {
            level = 0;
            profit = 0;
            weight = 0;
            bound = 0;
            solnList = new ArrayList<>();
        }
    }
    
    // comparator for Node: required for using Java.util.PriorityQueue
    // the higher the bound the higher the priority
    protected class NodeComparator implements Comparator<Node>{
        @Override
        public int compare(Node arg0, Node arg1) {
            if (arg0.bound < arg1.bound)
                return 1;
            else if (arg0.bound > arg1.bound)
                return -1;
            return 0;
        }
    }
}
