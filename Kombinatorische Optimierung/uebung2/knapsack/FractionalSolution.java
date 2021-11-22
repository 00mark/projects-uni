package knapsack;

/**
 * Solution of a fractional knapsack problem
 *
 * @author Stephan Beyer
 */
public class FractionalSolution extends GenericSolution<Double> {
	private double epsilon = 1e-6;

	public FractionalSolution(Instance instance) {
		super(instance);
	}

	/**
	 * Copy a solution (copy constructor)
	 */
	public FractionalSolution(FractionalSolution solution) {
		super(solution);
	}

	@Override
	public void set(int item, Double quantity) {
		assert sol.size() > item : "Item number " + item + " not found!";
		assert sol.get(item) != null : "Item " + item + " not initialized in solution.";
        sol.set(item, quantity);
        solWeight += quantity * getInstance().getWeight(item);
        solValue += quantity * getInstance().getValue(item);
	}

	@Override
	public boolean isFeasible() {
        return getWeight() <= super.getInstance().getCapacity();
	}

	@Override
	public boolean isBinary() {
		for (double quantity : sol) {
			if (quantity > epsilon
			 && quantity < 1 - epsilon) {
				return false;
			}
		}
		return true;
	}

	@Override
	protected Double zero() {
		return 0.0;
	}
}
