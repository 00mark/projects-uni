package knapsack;

import org.chocosolver.solver.Model;
import org.chocosolver.solver.variables.IntVar;
import org.chocosolver.solver.variables.BoolVar;
import org.chocosolver.solver.Solver;
import org.chocosolver.solver.Solution;

/**
 * Solves a given binary knapsack problem with choco, without making use of
 * the supplied knapsack() method
 * @author Mark Hiltenkamp
 */
public class BinaryChocoSolver2 implements SolverInterface<knapsack.Solution>{

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
        IntVar[] totalWeight = new IntVar[instance.getSize()];
        IntVar[] totalValue = new IntVar[instance.getSize()];

        // iterate through all items and calculate total weight and value
        for(int i = 0; i < instance.getSize(); i++){
            totalWeight[i] = model.intScaleView(occurances[i], 
                    instance.getWeight(i));
            totalValue[i] = model.intScaleView(occurances[i],
                    instance.getValue(i));
        }

        // the sum of each individual weight (or value) * occurance has to be equal to
        // the total sum
        model.sum(totalValue, "=", valueSum).post();
        model.sum(totalWeight, "=", weightSum).post();
        Solution solution = solver.findOptimalSolution(valueSum, true);

        for(int i = 0; i < occurances.length; i++)
            sol.set(i, solution.getIntVal(occurances[i]));
        return sol;
    }
}
