package skyscrapers;

import org.chocosolver.solver.Model;
import org.chocosolver.solver.Solution;
import org.chocosolver.solver.Solver;
import org.chocosolver.solver.constraints.Constraint;
import org.chocosolver.solver.variables.BoolVar;
import org.chocosolver.solver.variables.IntVar;
import org.chocosolver.util.tools.ArrayUtils;

import java.io.IOException;
import java.util.List;


import org.chocosolver.solver.search.limits.TimeCounter;
/**
 * A skyscrapers solver.
 *
 * @author Mark Hiltenkamp
 */
public class SkyscSolver {

    public static void main(String args[]) throws IOException {
        if(args != null && args.length > 0) {
            System.out.println("Read file "+ args[0] + ".");
            Instance instance = Reader.readSkyscInstance(args[0]);
            System.out.println("Gamefield:");
            instance.printGamefield();
            long start = System.currentTimeMillis();
            SkyscSolver.solve(instance);
            long end = System.currentTimeMillis();
            System.out.printf("time = %.3fs\n", (end - start) / 1000.0);
        } else {
            System.out.println("Please enter a skyscrapers file.");
        }
    }

    private static boolean allSmaller(IntVar[] values, int from, int to){
        for(; from != to; from = from + (from < to ? 1 : -1))
            if(values[from].getValue() >= values[to].getValue()){
                return false;
            }
        return true;
    }

    public static void solve(Instance instance) {
        // 1. create model
        Model model = new Model("skyscrapers");

        // 2. create variables
        IntVar[][] x = model.intVarMatrix(instance.getGamefieldSize(),
                instance.getGamefieldSize(), 1, 10000);

        BoolVar[][] vn = model.boolVarMatrix(instance.getGamefieldSize(),
                instance.getGamefieldSize()); 
        BoolVar[][] vo = model.boolVarMatrix(instance.getGamefieldSize(),
                instance.getGamefieldSize());
        BoolVar[][] vs = model.boolVarMatrix(instance.getGamefieldSize(),
                instance.getGamefieldSize());
        BoolVar[][] vw = model.boolVarMatrix(instance.getGamefieldSize(),
                instance.getGamefieldSize());
        BoolVar smaller = model.boolVar(); 
        IntVar[] tmp;

        // 3. add constraints
        for(int i = 0; i < x.length; i++){
            for(int j = 0; j < x.length; j++){
                if(instance.getGamefield()[i][j] > 0)
                    model.arithm(x[i][j], "=",
                            instance.getGamefield()[i][j]).post();
               /* for(int a = j+1; a < x.length; a++)
                    model.arithm(x[i][j], "!=", x[i][a]).post();
                for(int b = i+1; b < x.length; b++)
                    model.arithm(x[i][j], "!=", x[b][j]).post();
                    */
                if(i == 0)
                    model.allDifferent(x[j]).post();
                if(i == 0){
                    model.sum(vn[j], "=", instance.getNorth()[j]).post();
                    model.sum(vs[j], "=", instance.getSouth()[j]).post();
                }
                model.arithm(smaller, "=", model.boolVar(allSmaller(x[j], 0, i))).post();
                tmp = model.intVarArray(i, x[j].getValue());
                model.ifOnlyIf(model.lexChainLess(tmp),
                        model.arithm(vn[i][j], "=", 1));

                model.arithm(smaller, "=", model.boolVar(allSmaller(x[i], x.length-1, j))).post();
                tmp = model.intVarArray(x.length-1-j, x[i].getValue().reverse());
                model.ifOnlyIf(model.lexChainLess(tmp),
                        model.arithm(vo[i][j], "=", 1));

                model.arithm(smaller, "=", model.boolVar(allSmaller(x[j], x.length-1, i))).post();
                tmp = model.intVarArray(x.length-1-i, x[i].getValue().reverse());
                model.ifOnlyIf(model.lexChainLess(tmp),
                        model.arithm(vs[i][j], "=", 1));

                model.arithm(smaller, "=", model.boolVar(allSmaller(x[i], 0, j))).post();
                tmp = model.intVarArray(j, x[i].getValue());
                model.ifOnlyIf(model.lexChainLess(tmp),
                        model.arithm(vw[i][j], "=", 1));
            }
            model.allDifferent(x[i]).post();
            model.sum(vo[i], "=", instance.getEast()[i]).post();
            model.sum(vw[i], "=", instance.getWest()[i]).post();
        }
        
        // 4. get solver and solve model
        Solver solver = model.getSolver();
        List<Solution> solutions = solver.findAllSolutions();

        // 5. print solutions
        int size = instance.getGamefieldSize();
        System.out.println("Number of solutions: " + solutions.size());
        int cnt = 1;
        for (Solution solution : solutions) {
            int[][] solutionArray = new int[size][size];
            for (int i = 0; i < size; ++i) {
                for (int j = 0; j < size; ++j) {
                    solutionArray[i][j] = solution.getIntVal(x[i][j]);
                }
            }
            instance.setSolution(solutionArray);
            System.out.println("------- solution number " + cnt + "-------");
            instance.printSolution();

            ++cnt;
        }
    }
}
