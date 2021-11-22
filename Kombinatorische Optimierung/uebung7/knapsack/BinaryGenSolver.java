package knapsack;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Random;

/**
 * Solves a given binary knapsack problem by using simulated annealing
 * @author Mark Hiltenkamp
 */
public class BinaryGenSolver implements SolverInterface<Solution>{

    private int popSize;
    private byte[][] population;
    private byte[] solution;
    private int best;
    private Instance instance;
    private Random rand = new Random();

    /**
     * solves the given knapsack instance
     * @param instance binary knapsack instance
     */
    public Solution solve(Instance instance){
        if(instance.getSize() < 10)
            popSize = 2;
        else
            // don't let the population size get too large
            popSize = (int)Math.min(100, 0.1 * Math.pow(2,instance.getSize()));
        this.instance = instance;
        Solution sol = new Solution(instance);
        solution = new byte[instance.getSize()];
        population = new byte[popSize][instance.getSize()];
        genAlgo(100);
        setSol(sol, solution);
        return sol; 
    }

    /**
     * creates the initial population for the genetic algorithm
     */
    private void genInitPop(){
        do{
            for(int i = 0; i < instance.getSize(); i++)
                population[0][i] = rand.nextDouble() <= 0.5 ? (byte)1 :
                    (byte)0;
        }while(!solFeasible(population[0]));
        for(int i = 1; i < popSize; i++)
            population[i] = mutation(population[i-1]);
    }

    /**
     * checks if the given individual is present within the population
     * @param individual bit verctor
     * @param count number of the individual
     * @return true if present else false
     */
    private boolean individualPresent(byte[] individual, int count){
        if(count == 0)
            return false;
        return individual.equals(population[count - 1]) ||
            individualPresent(individual, count -1);
    }

    /**
     * calculates the value of the given item occurances
     * @param individual occurance of each item
     * @return the total value
     */
    private int getSolValue(byte[] individual){
        int value = 0;
        for(int i = 0; i < individual.length; i++)
            value += individual[i] * instance.getValue(i);
        return value;
    }

    /**
     * sets the occurance of each item in <code>sol</code>
     * @param sol the Solution instance
     * @param individual occurance of each item
     */
    private void setSol(Solution sol, byte[] individual){
        for(int i = 0; i < individual.length; i++)
            sol.set(i, (int)individual[i]);
    }

    /**
     * detects if <code>individual</code> is a valid solution
     * @param individual occurance of each item
     * @return true if feasible else false
     */
    private boolean solFeasible(byte[] individual){
        Solution tmpSol = new Solution(instance);
        setSol(tmpSol, individual);
        return tmpSol.isFeasible();
    }

    /**
     * randomly flips a bit in <code>individual</code> and makes sure that the
     * output is feasible
     * @param individual occurance of each item
     * @return mutation of <code>individual</code> with one bit flipped
     */
    private byte[] flip(byte[] individual){
        byte[] neighbour = Arrays.copyOf(individual, individual.length);
        while(true){
            int index = rand.nextInt(neighbour.length);
            neighbour[index] = neighbour[index] == 0 ? (byte)1 : (byte)0;
            if(solFeasible(neighbour))
                break;
            // mutation is not feasible => revert changes
            neighbour[index] = neighbour[index] == 0 ? (byte)1 : (byte)0;
        }
        return neighbour;
    }

    /**
     * randomly swaps two bits in <code>individual</code> and makes sure that 
     * the output is feasible
     * @param individual occurance of each item
     * @return mutation of <code>individual</code> with two bits swapped
     */
    private byte[] swap(byte[] individual){
        byte[] neighbour = Arrays.copyOf(individual, individual.length);
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
            // revert changes if mutation is not feasible
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
     * mutates <code>individual</code> by either flipping one bit or swapping two
     * bits
     * @param individual occurance of each item
     * @return a mutation of <code>individual</code>
     */
    private byte[] mutation(byte[] individual){
        return rand.nextDouble() <= 0.5 ? swap(individual) : flip(individual);
    }

    /**
     * perfroms a one-point crossover with two parents
     * @param parent1 the first parent
     * @param parent2 the second parent
     * @throws IllegalArgumentException if the parents have 1 or less items
     * @return the child
     */
    private byte[] onePCrossover(byte[] parent1, byte[] parent2){
        byte[] child = new byte[parent1.length];
        int crossP;
        if(parent1.length <= 1 || parent1.length != parent2.length)
            throw new IllegalArgumentException("invalid length");
        else if(parent1.length == 2)
            crossP = 1;
        else
            crossP = rand.nextInt(parent1.length-2) + 1;
        for(int i = 0; i < parent1.length; i++)
            child[i] = i < crossP ? parent1[i] : parent2[i];
        return child;
    }

    /**
     * performs a two-point crossover with two parents
     * @param parent1 the first parent
     * @param parent2 the second parent
     * @throws IllegalArgumentException if the parents have 2 or less items
     * @return the child
     */
    private byte[] twoPCrossover(byte[] parent1, byte[] parent2){
        byte[] child = new byte[parent1.length];
        int crossP1 = rand.nextInt(parent1.length-2) + 1;
        int crossP2;
        if(parent1.length <= 2 || parent1.length != parent2.length)
            throw new IllegalArgumentException("invalid length");
        do{
            crossP2 = rand.nextInt(parent1.length-2) + 1;
        }while(crossP1 == crossP2);
        if(crossP1 > crossP2){
            int tmp = crossP1;
            crossP1 = crossP2;
            crossP2 = tmp;
        }
        for(int i = 0; i < parent1.length; i++)
            child[i] = i >= crossP1 && i < crossP2 ? parent1[i] : parent2[i];
        return child;
    }

    /**
     * performs a uniform crossover with two parents
     * @param parent1 the first parent
     * @param parent2 the second parent
     * @return the child
     */
    private byte[] uniCrossover(byte[] parent1, byte[] parent2){
        byte[] child = new byte[parent1.length];
        if(parent1.length != parent2.length)
            throw new IllegalArgumentException("invalid length");
        for(int i = 0; i < parent1.length; i++)
            child[i] = rand.nextDouble() <= 0.5 ? parent1[i] : parent2[i];
        return child;
    }

    /**
     * updates the population by replacing each parent with a child 
     * (assumes that more children than parents are available)
     * @param children the children
     */
    private void updatePopReplace(byte[][] children){
        int[][] childValue = new int[children.length][2];
        for(int i = 0; i < children.length; i++){
            childValue[i][0] = i;
            childValue[i][1] = getSolValue(children[i]);
        }
        Arrays.sort(childValue, new Comparator<int[]>(){
            public int compare(int[] child1, int[] child2){
                return Integer.compare(child1[1], child2[1]);
            }
        });
        for(int i = 0, j = children.length-1; i < popSize; i++, j--)
            population[i] = children[childValue[j][0]];
    }

    /**
     * updates the population by sorting out the worst children and parents
     * until <code>popSize</code> is achieved
     * @param children the children
     */
    private void updatePopMix(byte[][] children){
        byte[][] cAndParents = new byte[children.length + popSize][
            children[0].length];
        for(int i = 0; i < popSize; i++)
            cAndParents[i] = population[i];
        for(int i = popSize, j = 0; i < popSize + children.length; i++, j++)
            cAndParents[i] = children[j];
        int[][] cAndPValue = new int[cAndParents.length][2];
        for(int i = 0; i < cAndPValue.length; i++){
            cAndPValue[i][0] = i;
            cAndPValue[i][1] = getSolValue(cAndParents[i]);
        }
        Arrays.sort(cAndPValue, new Comparator<int[]>(){
            public int compare(int[] ind1, int[] ind2){
                return Integer.compare(ind1[1], ind2[1]);
            }
        });
        for(int i = 0, j = cAndParents.length-1; i < popSize; i++, j--)
            population[i] = cAndParents[cAndPValue[j][0]];
    }

    /**
     * returns two random parents from the population
     * @return the parents
     */
    private byte[][] getRandParents(){
        byte[][] parents = new byte[2][population[0].length];
        int pIndex1 = rand.nextInt(population.length);
        int pIndex2;
        do{
            pIndex2 = rand.nextInt(population.length);
        }while(pIndex1 == pIndex2);
        parents[0] = population[pIndex1];
        parents[1] = population[pIndex2];
        return parents;
    }

    /**
     * finds the best solution in the population
     * @return the best solution
     */
    private byte[] getBest(){
        int best = 0, index = 0;
        for(int i = 0; i < popSize; i++){
            if(getSolValue(population[i]) > best){
                best = getSolValue(population[i]);
                index = i;
            }
        }
        return population[index];
    }

    /**
     * genetic Algorithm to find a good solution for the binary knapsack problem
     * @param maxIter maximun number of iterations
     */
    private void genAlgo(int maxIter){
        genInitPop();
        for(; maxIter > 0; maxIter--){
            byte[][] children = new byte[(int)(popSize * 1.5)][
                population[0].length]; 
            for(int i = 0; i < children.length; i++){
                byte[][] parents;
                do{
                    parents = getRandParents();
                    children[i] = uniCrossover(parents[0], parents[1]);
                }while(!solFeasible(children[i]));
                if(rand.nextDouble() <= 0.1)
                    children[i] = mutation(children[i]);
            }
            updatePopMix(children);
        }
        solution = getBest();
    }
}
