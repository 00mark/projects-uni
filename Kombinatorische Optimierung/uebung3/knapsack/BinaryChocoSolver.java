package knapsack;

import org.chocosolver.solver.Model;
import org.chocosolver.solver.variables.IntVar;
import org.chocosolver.solver.variables.BoolVar;
import org.chocosolver.solver.Solver;
import org.chocosolver.solver.Solution;

/**
 * Solves the given binary knapsack problem by making use of choco
 * @author Mark Hiltenkamp
 */
public class BinaryChocoSolver implements SolverInterface<knapsack.Solution>{

    /**
     * solves the given knapsack instance
     * @param instance binary knapsack instance
     */ 
    public knapsack.Solution solve(Instance instance){
        knapsack.Solution sol = new knapsack.Solution(instance);

        final Model model = new Model("knapsack");
        // number of each item (0 or 1)
        final BoolVar[] occurances = model.boolVarArray("occurances",
                  instance.getSize());
        // total weight
        final IntVar weightSum = model.intVar("weightSum", 0,
                instance.getCapacity());
        // total value
        final IntVar valueSum = model.intVar("valueSum", 0,
                IntVar.MAX_INT_BOUND);
        final Solver solver = model.getSolver();

        // knapsack constraint with number of items, total weight, total value,
        // corresponding weight and value of each item
        model.knapsack(occurances, weightSum, valueSum,
                instance.getWeightArray(), instance.getValueArray()).post();
        
        // find a solution with total value >= all other solutions
        Solution solution = solver.findOptimalSolution(valueSum, true);
        for(int i = 0; i < occurances.length; i++)
            sol.set(i, solution.getIntVal(occurances[i]));
        return sol;
    }
}
