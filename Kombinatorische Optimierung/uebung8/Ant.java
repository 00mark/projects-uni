package tsp;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class Ant {

    private static Random rand = new Random();

    private static class TspInstance {
        private double[][] distances;
        private double[][] tau;
        private double[][] eta;
        private int n;

        public TspInstance(String filename) {
            this.initializeDistances(filename);
            this.initalizeTauEta();
        }

        /**
         * This method initializes the distances between all nodes.
         *
         * @param filename filename of the tsp instance
         */
        private void initializeDistances(String filename) {
            try (BufferedReader reader = new BufferedReader(
                        new FileReader(filename))) {
                // first line is number of nodes
                n = Integer.parseInt(reader.readLine());
                distances = new double[n][n];

                /*
                 * read from file
                 * first entry is starting node,
                 * second entry ending node,
                 * third entry is distance
                 */
                int start, end;
                double distance;
                String line;
                while ((line = reader.readLine()) != null) {
                    String[] tokens = line.split("\\s+");
                    start = Integer.parseInt(tokens[0]);
                    end = Integer.parseInt(tokens[1]);
                    distance = Double.parseDouble(tokens[2]);

                    // dinstances are symmetric
                    distances[start-1][end-1] = distance;
                    distances[end-1][start-1] = distance;
                }
            } catch (IOException e) {
                System.err.println("File " + filename + " not found.");
            }
        }

        /**
         * This method initializes the auxiliary tau and eta values
         */
        private void initalizeTauEta() {
            tau = new double[n][n];
            eta = new double[n][n];
            for(int i = 0; i < n; i++){
                for(int j = 0; j < n; j++){
                    tau[i][j] = 0;
                    eta[i][j] = 1 / distances[i][j];
                }
            }
        }

        public double[][] getDistances() {
            return this.distances;
        }

        public double[][] getTau() {
            return this.tau;
        }

        public void setTau(int i, int j, double value) {
            this.tau[i][j] = value;
        }

        public double[][] getEta() {
            return this.eta;
        }

        public int getSize(){
            return n;
        }
    }

    /**
     * finds a valid new node for the current tour with likeliness depending
     * on tau and eta of each edge
     * @param inst the TSP instance
     * @param node the current node
     * @param visited the nodes that have been previously visited
     * @param numVisited number of already visited nodes
     * @param alpha used to adjust the importance of tau
     * @param beta used to adjust the importance of eta
     * @return index of the new node
     */
    private static int getNextNode(TspInstance inst, int node, int[] visited,
            int numVisited, int alpha, int beta){
        double[][] likelihood = new double[inst.getSize()
            - numVisited][2];
        double etaTauSum = 0;
        for(int i = 0, j = 0; i < inst.getSize(); i++){
            // is the node already in the tour?
            if(nodeVisited(i, visited, numVisited));
            else{
                likelihood[j][0] = i;
                j++;
                etaTauSum += Math.pow(inst.getTau()[node][i], alpha) *
                    Math.pow(inst.getEta()[node][i], beta);
            }
        }
        double rNodeVal = rand.nextDouble();
        int nextNode;
        for(nextNode = 0; nextNode < likelihood.length; nextNode++){
            // calculate the likelihood of each valid node based on tau and eta
            likelihood[nextNode][1] = ((Math.pow(inst.getTau()[node][nextNode],
                            alpha) * Math.pow(inst.getEta()[node][nextNode],
                            beta)) / etaTauSum +
                            (nextNode > 0 ? likelihood[nextNode-1][1] : 0));
            if(rNodeVal <= likelihood[nextNode][1])
                // new node has been found => stop
                break;
        }
        // rNodeVal > every likelihood => last node is new node
        return (int)likelihood[(nextNode < likelihood.length ? nextNode :
                nextNode-1)][0];
    }

    /**
     * detects if <code>node</code> is already part of the tour
     * @param node the node to be checked
     * @param visited nodes that have been previously visited
     * @param numVisited number of visited nodes
     * @return true if node has been visited else false
     */
    private static boolean nodeVisited(int node, int[] visited, int numVisited){
        for(int i = 0; i < numVisited; i++){
            if(node == visited[i])
                return true;
        }
        return false;
    }

    /**
     * updates the tau value of each edge
     * @param inst the TSP instance
     * @param delta sum of calculated deltas for every edge
     * @param evap evaporation constant
     */
    private static void updateTau(TspInstance inst, double[][] delta,
            double evap){
        for(int i = 0; i < delta.length; i++){
            for(int j = 0; j < delta.length; j++)
                inst.setTau(i, j, (1-evap) * inst.getTau()[i][j] + delta[i][j]); 
        }
    }

    /**
     * creats a string based on the given tour
     * @param tour the TSP tour
     * @return the tour string
     */
    private static String getTourString(int[] tour){
        String tourString = "[" + tour[0];
        for(int i = 1; i < tour.length; i++)
            tourString += " -> " + tour[i] + (i == tour.length-1 ? "]" : "");
        return tourString;
    }

    /**
     * calculates the cost of the given tour
     * @param inst the TSP instance
     * @param tour the TSP tour
     * @return the total cost of <code>tour</code>
     */
    private static double getTourCost(TspInstance inst, int[] tour){
        double tourCost = 0;
        for(int i = 0; i < tour.length-1; i++)
            tourCost += inst.getDistances()[tour[i]][tour[i+1]];
        return tourCost;
    }

    /**
     * solves the given TSP instance by making use of an ant algorithm
     * @param inst the TSP instance
     * @param maxIter number of iterations
     * @param numAnts number of Ants
     * @return the TSP tour
     */
    public static int[] solve(TspInstance inst, int maxIter, int numAnts){
        int[][] antTours = new int[numAnts][inst.getSize()+1];
        int[] bestTour = new int[inst.getSize()+1];
        int startNode, nextNode, lambda; 
        double bestDistance;
        // instantiate bestDistance with the wort possible value
        bestDistance = Double.MAX_VALUE;
        for(int iter = maxIter; iter > 0; iter--){
            for(int m = 0; m < numAnts; m++){
                int[] visited = new int[inst.getSize()+1];
                // get a random start node
                startNode = rand.nextInt(inst.getSize());
                visited[0] = startNode;
                for(lambda = 1; lambda < inst.getSize(); lambda++){
                    // get a new node
                    nextNode = getNextNode(inst, visited[lambda-1], visited,
                            lambda, 1, 1);
                    visited[lambda] = nextNode;
                }
                visited[lambda] = startNode;
                antTours[m] = visited;
            }
            double[] tourLength = new double[numAnts];
            // get the cost of each tour and check if the tour is the best 
            // thus far
            for(int i = 0; i < numAnts; i++){
                for(int j = 0; j < inst.getSize(); j++)
                    tourLength[i] += 
                        inst.getDistances()[antTours[i][j]][antTours[i][j+1]];
                if(tourLength[i] < bestDistance){
                    bestDistance = tourLength[i];
                    bestTour = antTours[i];
                    // a better tour has been found => reset iter
                    iter = maxIter;
                }
            }
            // calculate the deltas
            double[][] delta = new double[inst.getSize()][inst.getSize()];
            for(int i = 0; i < numAnts; i++){
                for(int j = 0; j < inst.getSize(); j++)
                    delta[antTours[i][j]][antTours[i][j+1]] += 1/tourLength[i];
            }
            updateTau(inst, delta, 0.05);
            double totalCost = 0;
            for(int i = 0; i < antTours.length; i++)
                totalCost += getTourCost(inst, antTours[i]);
            System.out.println("AVG: " + totalCost / antTours.length);
        }
        return bestTour;
    }

    public static void main(String[] args) {
        if(args.length != 1) {
            System.err.println("Wrong number of arguments.");
        } else {
            TspInstance inst = new TspInstance(args[0]);

            long start, end;
            start = System.currentTimeMillis();

            int[] solution = solve(inst, 50, 3000);

            System.out.println("best objective: " + getTourCost(inst,solution));
            System.out.println("best tour: " + getTourString(solution));

            end = System.currentTimeMillis();

            System.out.printf("Benoetigte Zeit: %.3fs\n", (end - start) / 
                    1000.0);
        }
    }
}
