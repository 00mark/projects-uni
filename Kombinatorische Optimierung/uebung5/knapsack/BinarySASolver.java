package knapsack;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Random;

/**
 * Solves a given binary knapsack problem by using simulated annealing
 * @author Mark Hiltenkamp
 */
public class BinarySASolver implements SolverInterface<Solution>{

    private double[][] valForWeight;
    private byte[] solution;
    private int best;
    private Instance instance;
    private Random rand = new Random();

    /**
     * solves the given knapsack instance
     * @param instance binary knapsack instance
     */
    public Solution solve(Instance instance){
        Solution sol = new Solution(instance);
        this.instance = instance;
        solution = new byte[instance.getSize()];
        valForWeight = new double[instance.getSize()][2];
        fill();
        // calculate a good starting solution and set best value
        best = findStartSol1();
        simAnnealing(solution, 100);
        setSol(sol, solution);
        return sol; 
    }

    /**
     * calculates a good starting solution, writes the corresponding bits
     * into <code>solution</code> and returns the value
     * @return value of the calculated solution
     */
    private int findStartSol1(){
        int wCur = 0;
        int cCur = 0;
        for(int i = 0; i < instance.getSize(); i++){
            // item fits?
            if(instance.getCapacity() - (wCur + 
                        instance.getWeight((int)valForWeight[i][1])) >= 0){
                wCur += instance.getWeight((int)valForWeight[i][1]);
                cCur += instance.getValue((int)valForWeight[i][1]);
                solution[(int)valForWeight[i][1]] = 1;
                        }
        }
        return cCur;
    }

    /** 
     * calculates a random starting solution, writes the corresponding bits
     * into <code>solution</code> and returns the value
     * @return value of the calculated solution
     */
    private int findStartSol2(){
        do{
            for(int i = 0; i < solution.length; i++)
                solution[i] = rand.nextDouble() <= 0.5 ? (byte)1 : (byte)0;
        }while(!solFeasible(solution));
        return getSolValue(solution);
    }

    /**
     * fills <code>valForWeight</code> with value/weight and number of each item
     * and brings the array into descending value/weight order
     */
    private void fill(){
        for(int i = 0; i < instance.getSize(); i++){
            valForWeight[i][0] = ((double)instance.getWeight(i) /
                    instance.getValue(i));
            valForWeight[i][1] = i;
        }
        // sort by comparing value/weight (first element of the array) 
        // of each item
        Arrays.sort(valForWeight, new Comparator<double[]>(){
            public int compare(double[] a, double[] b){
                return Double.compare(a[0], b[0]);
            }
        });
    }

    /**
     * sets the occurance of each item in <code>sol</code>
     * @param sol the Solution instance
     * @param items occurance of each item
     */
    private void setSol(Solution sol, byte[] items){
        for(int i = 0; i < items.length; i++)
            sol.set(i, (int)items[i]);
    }

    /**
     * detects if <code>items</code> is a valid solution
     * @param items occurance of each item
     * @return true if feasible else false
     */
    private boolean solFeasible(byte[] items){
        Solution tmpSol = new Solution(instance);
        setSol(tmpSol, items);
        return tmpSol.isFeasible();
    }

    /**
     * calculates the current temperature
     * @param t temperature change counter
     * @param c1 constant 1
     * @param c2 constant 2
     * @return the new temperature
     */
    private double temp1(int t, double c1, double c2){
        return c1 / (1 + t * c2);
    }

    /**
     * calculates the current temperature
     * @param t temperature change counter
     * @param c constant
     * @return the new temperature
     */
    private double temp2(int t, double c){
        int val = 1;
        for(int i = 0; i < t; i++){
            val *= c;
        }
        return val;
    }

    /**
     * randomly flips a bit in <code>items</code> and makes sure that the
     * output is feasible
     * @param items occurance of each item
     * @return neighbour of <code>items</code> with one bit flipped
     */
    private byte[] flip(byte[] items){
        byte[] neighbour = Arrays.copyOf(items, items.length);
        while(true){
            int index = rand.nextInt(neighbour.length);
            neighbour[index] = neighbour[index] == 0 ? (byte)1 : (byte)0;
            if(solFeasible(neighbour))
                break;
            // neighbour is not feasible => revert changes
            neighbour[index] = neighbour[index] == 0 ? (byte)1 : (byte)0;
        }
        return neighbour;
    }

    /**
     * randomly swaps two bits in <code>items</code> and makes sure that the 
     * output is feasible
     * @param items occurance of each item
     * @return neighour of <code>items</code> with two bits swapped
     */
    private byte[] swap(byte[] items){
        byte[] neighbour = Arrays.copyOf(items, items.length);
        int i1, i2;
        while(true){
            // get two different, randomly selected indices
            do{
                i1 = rand.nextInt(neighbour.length);
                i2 = rand.nextInt(neighbour.length);
            }while(i1 == i2);
            byte tmp = neighbour[i2];
            neighbour[i2] = neighbour[i1];
            neighbour[i1] = tmp;
            // revert changes if neighbour is not feasible
            if(!solFeasible(neighbour)){
                neighbour[i1] = neighbour[i2];
                neighbour[i2] = tmp;
                continue;
            }
            break;
        }
        return neighbour;
    }

    /**
     * finds a neighbour of <code>items</code> by either flipping one bit
     * or swapping two bits
     * @param items occurance of each item
     * @return a neighbour of <code>items</code>
     */
    private byte[] findNeighbour(byte[] items){
        return rand.nextDouble() <= 0.5 ? swap(items) : flip(items);
    }

    /**
     * calculates the value of the given item occurances
     * @param items occurance of each item
     * @return the total value
     */
    private int getSolValue(byte[] items){
        int value = 0;
        for(int i = 0; i < items.length; i++)
            value += items[i] * instance.getValue(i);
        return value;
    }

    /**
     * attempts to improve the starting solution by making use of simulated 
     * annealing 
     * @param items occurance of each item in the starting solution
     * @param maxIter maximum number of iterations without a better solution
     */
    private void simAnnealing(byte[] items, int maxIter){
        int t = 1, curBest = best;
        for(int n = 0; n < maxIter; n++){
            for(int i = 0; i < items.length; i++){
                // find a neighour
                byte[] neighbour = findNeighbour(items);
                int delta = curBest - getSolValue(neighbour);
                // is the neighbour better than the previous best?
                if(delta < 0){
                    curBest = getSolValue(neighbour);
                    if(curBest > best){
                        solution = Arrays.copyOf(neighbour, neighbour.length);
                        best = curBest;
                        n = -1;
                    }
                    // randomly update the current best although its value is worse
                    // than the previous current best
                }else if(rand.nextDouble() <
                        Math.exp(-delta / temp1(t, delta, 1))){
                    curBest = getSolValue(neighbour);
                }
            }
            t++;
        }
    }
}
