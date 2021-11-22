package knapsack;

/**
 * Solves a binary knapsack problem
 *
 * @author Mark Hiltenkamp
 */
public class BinarySolver implements SolverInterface<Solution>{

    private int[][] possibilities;

    public BinarySolver(Instance instance){
        int options = 1;
        for(int i = instance.getSize(); i > 0; i--)
            options *= 2;
        possibilities = new int[options][instance.getSize()];
        getBits(0, 0, options);
    }

    /**
     * Solves the binary knapsack problem of <code>instance</code>
     * @param instance the knapsack instance
     * @return an optimal solution for the given problem
     */
    public Solution solve(Instance instance){
        int best = 0, bestNum = 0;
        Solution sol = new Solution(instance);
        for(int i = possibilities.length-1; i >= 0; i--){
            for(int j = 0; j < possibilities[0].length; j++)
                sol.set(j, possibilities[i][j]);
            if(sol.isFeasible() && sol.getValue() > best){
                best = sol.getValue();
                bestNum = i;
            }
            //printSolution(sol);
            sol = new Solution(instance);
        }
        for(int i = 0; i < possibilities[0].length; i++)
            sol.set(i, possibilities[bestNum][i]);
        return sol;
    }

    /**
     * Creates all possible bit-variations for the given instance size
     * and writes them into <code>possibilities</code>
     * @param i row number
     * @param j column number
     * @param size number of variations (total rows)
     */
    private void getBits(int i, int j, int size){
        if(size-i > 1){
            int start = i;
            int mid = (i + size) / 2;
            for(; i < mid; i++)
                possibilities[i][j] = 0; 
            getBits(start, j+1, mid);
            for(; i < size; i++)
                possibilities[i][j] = 1; 
            getBits(mid, j+1, size);
        }
    }

    /**
     * Prints value, weight and feasibility of a given Solution
     */
    public void printSolution(Solution sol){
        Logger l = new Logger();
        l.enable();
        l.print("Value: ");
        l.print(sol.getValue());
        l.print(", Weight: ");
        l.print(sol.getWeight());
        l.print(", isFeasible?: ");
        l.print(sol.isFeasible());
        l.println();
    }
}
