package knapsack;

import org.chocosolver.solver.Model;
import org.chocosolver.solver.variables.IntVar;
import org.chocosolver.solver.variables.BoolVar;
import org.chocosolver.solver.Solver;
import org.chocosolver.solver.Solution;

/**
 * Solves a given binary knapsack problem with choco, while not making use of
 * setObjective() and findOptimalSolution()
 * @author Mark Hiltenkamp
 */
public class BinaryChocoSolver3 implements SolverInterface<knapsack.Solution>{

    /**
     * solves the given knapsack instance
     * @param instance binary knapsack instance
     */
    public knapsack.Solution solve(Instance instance){
        knapsack.Solution sol = new knapsack.Solution(instance);

        final Model model = new Model("knapsack");
        // number of each item
        final BoolVar[] occurances = model.boolVarArray("occurances",
                  instance.getSize());
        // total weight
        final IntVar weightSum = model.intVar("weightSum", 0,
                instance.getCapacity());
        // total value
        final IntVar valueSum = model.intVar("valueSum", 0,
                IntVar.MAX_INT_BOUND);
        final Solver solver = model.getSolver();

        // used to save the best solution
        byte[] bestBits = new byte[instance.getSize()];

        IntVar bestVal = model.intVar("bestVal", 0);
        // total value has to be greater than the value of the best solution
        // thus far
        model.arithm(valueSum, ">", bestVal).post();
        model.knapsack(occurances, weightSum, valueSum,
                instance.getWeightArray(), instance.getValueArray()).post();
        
        // while there are still possible solutions
        while(solver.solve()){
            // solution is better?
            if(valueSum.getValue() > bestVal.getValue()){
                // save value and bits
                bestVal = model.intVar("bestVal", valueSum.getValue());
                for(int i = 0; i < bestBits.length; i++)
                    bestBits[i] = (byte)occurances[i].getValue();
            }
        }

        for(int i = 0; i < bestBits.length; i++)
            sol.set(i, (int)bestBits[i]);
        return sol;
    }
}
