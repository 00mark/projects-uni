package knapsack;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Random;

/**
 * Solves a given binary knapsack problem by using taboo search
 * @author Mark Hiltenkamp
 */
public class BinaryTabuSolver implements SolverInterface<Solution>{

    private final byte DEC = -1;
    private final byte DEFAULT = 0;
    private final byte INC = 1;
    private byte[] tabuList;
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
        tabuList = new byte[instance.getSize()];
        valForWeight = new double[instance.getSize()][2];
        fill();
        // calculate a good starting solution and set best value
        best = findStartSol1();
        tabuSearch(Arrays.copyOf(solution, solution.length), 100);
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
     * detects if <code>operation</code> is taboo
     * @param index the item index
     * @param operation can be either <code>INC</code> (+1)
     *                             or <code>DEC</code> (-1)
     * @return true if taboo else false
     */
    private boolean isTaboo(int index, byte operation){
        return tabuList[index] + operation == DEFAULT ? true : false;
    }

    /**
     * uses taboo search in an attempt to improve the starting solution
     * @param items the starting item occurances
     * @param iterations max number of iterations without improvement
     */
    private void tabuSearch(byte[] items, int iterations){
        for(int i = 0; i < iterations; i++){
            int curBest = ~0;
            int curValue = getSolValue(items);
            // first param is the item index and second is the operation
            int[] bestOperation = {0, 0};
            for(int j = 0; j < items.length; j++){
                byte[] tmpItems = Arrays.copyOf(items, items.length);
                tmpItems[j]++;
                // valid item occurances?
                if((solFeasible(tmpItems) && items[j] != 1) && 
                        (!isTaboo(j, INC) || getSolValue(tmpItems) > best)){
                    if(curValue + instance.getValue(j) > curBest){
                        curBest = curValue + instance.getValue(j);
                        bestOperation[0] = j;
                        bestOperation[1] = INC;
                    }
                }
                if(!isTaboo(j, DEC) && items[j] != 0){
                    if(curValue - instance.getValue(j) > curBest){
                        curBest = curValue - instance.getValue(j);
                        bestOperation[0] = j;
                        bestOperation[1] = DEC;
                    }
                }
                // all operations are taboo -> remove one random element of
                // the taboo list
                if(j == items.length -1 && curBest == ~0){
                    j = -1;
                    tabuList[rand.nextInt(tabuList.length)] = DEFAULT;
                }
            }
            
            if(bestOperation[1] == INC)
                items[bestOperation[0]]++;
            else
                items[bestOperation[0]]--;
            tabuList[bestOperation[0]] = (byte)bestOperation[1];
            // is the current solution the best thus far?
            if(getSolValue(items) > best && solFeasible(items)){
                best = getSolValue(items);
                solution = Arrays.copyOf(items, items.length);
                iterations = 0;
            }
        }
    }
}
