package knapsack;
import java.util.Arrays;
import java.util.Comparator;

/**
 * Solves a given binary knapsack problem by making use of branching and
 * bounding to reduce runtime
 * @author Mark Hiltenkamp
 */
public class BinaryBandBSolver implements SolverInterface<Solution>{

    private double[][] valForWeight;
    private byte[] solution;
    private int ub;
    private Instance instance;

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
        // calculate a good starting solution and set initial upper bound
        ub = findStartSol();
        byte[] bits = new byte[instance.getSize()];
        // start with no elements set
        bounding(0, 0, 0, bits);
        for(int i = 0; i < solution.length; i++)
            sol.set(i, (int)solution[i]);
        return sol; 
    }

    /**
     * recursively goes thru every possible solution with UB > <code>ub+1<code>
     * @param i number of the bit to be inspected
     * @param c total value thus far
     * @param w total weight thus far
     * @param bits solution thus far
     */
    private void bounding(int i, int c, int w, byte[] bits){
        // not all bits have been set and possible solution? 
        if(i < instance.getSize() &&
                    (c + (((double)(instance.getCapacity() - w) /
                    instance.getWeight((int)valForWeight[i][1])) *
                    instance.getValue((int)valForWeight[i][1]))) >= ub+1){
            // insert the item if enough space
            if(instance.getCapacity() - (w + 
                        instance.getWeight((int)valForWeight[i][1])) >= 0){
                byte[] tmp = Arrays.copyOf(bits, bits.length);
                tmp[(int)valForWeight[i][1]] = 1;
                bounding(i+1, c + instance.getValue((int)valForWeight[i][1]),
                        w + instance.getWeight((int)valForWeight[i][1]), tmp);
            }
            // do not insert the item
            bounding(i+1, c, w, Arrays.copyOf(bits, bits.length));
        }else{
            // new solution
            if(c >= ub+1){
                ub = c;
                solution = Arrays.copyOf(bits, bits.length);
            }
        }
    }

    /**
     * calculates a good starting solution, writes the corresponding bits
     * into <code>valForWeight</code> and returns the value
     * @return value of the calculated solution
     */
    private int findStartSol(){
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
}
