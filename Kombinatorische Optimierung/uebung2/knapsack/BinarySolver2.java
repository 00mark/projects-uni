package knapsack;
import java.util.Arrays;
import java.util.Comparator;

/**
 * Solves a binary knapsack problem
 * @author Mark Hiltenkamp
 */
public class BinarySolver2 implements SolverInterface<Solution>{

    private Solution sol;
    private double[][] valueForWeight;

    /**
     * Solves the given knapsack instance
     * @param instance the knapsack instance
     * @return (possibly non-optimal) solution of the binary knapsack problem
     */
    public Solution solve(Instance instance){
        double wCurrent = 0;
        sol = new Solution(instance);
        valueForWeight = new double[instance.getValueArray().length][2];
        fill(instance);
        for(int i = valueForWeight.length-1;
                i >= 0 && wCurrent < instance.getCapacity(); i--){
            // item fits
            if(wCurrent + instance.getWeight((int)valueForWeight[i][1]) <
                    instance.getCapacity()){
                sol.set((int)valueForWeight[i][1], 1);
                wCurrent += instance.getWeight((int)valueForWeight[i][1]);
            }
        }
        return sol;
    }

    /**
     * fills <code>valueForWeight</code> with value/weight and number
     * of each item
     * @param instance the knapsack instance
     */
    private void fill(Instance instance){
        for(int i = 0; i < instance.getValueArray().length; i++){
            valueForWeight[i][0] = ((double)instance.getValueArray()[i] /
                    instance.getWeightArray()[i]);
            valueForWeight[i][1] = i;
        }
        // sort the array by comparing the value/weight of each item
        Arrays.sort(valueForWeight, new Comparator<double[]>(){
            public int compare(double a[], double b[]){
                return Double.compare(a[0], b[0]);
            }
        });
    }
}
