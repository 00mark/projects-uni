import java.util.HashMap;

/**
 * Class to calculate the Fibonacci number. Uses a HashMap to reduce the
 * calculation cost.
 * 
 * @author Mathias Menninghaus (mathias.menninghaus@uos.de)
 * 
 */
public class Fibonacci {

    private final static HashMap<Integer, Long> fibonacciHash;
    private static String FilePath = System.getProperty("user.dir") + 
        "/HashMap.ser";

    /**
     * Fill HashMap with initial key-value-pairs.
     */
    static {
        if(!(new java.io.File(FilePath).exists())){
            fibonacciHash = new HashMap<>();
            fibonacciHash.put(0, 0L);
            fibonacciHash.put(1, 1L);
        }
        // Es existiert eine serialisierte Version der HashMap
        else{
            HashMap<Integer, Long> fibonacciHashTmp;
            try(java.io.ObjectInputStream ois = new java.io.ObjectInputStream(
                        new java.io.FileInputStream(FilePath))){
                fibonacciHashTmp = (HashMap<Integer, Long>)ois.readObject();      
            }catch(java.io.FileNotFoundException e1){
                e1.printStackTrace();
                fibonacciHashTmp = null;
                System.out.println("Die Datei konnte nicht gefunden werden");
            }catch(java.io.IOException e2){
                e2.printStackTrace();
                fibonacciHashTmp = null;
            }catch(ClassNotFoundException e3){
                e3.printStackTrace();
                fibonacciHashTmp = null;
                System.out.println("Die Klasse kann nicht gefunden werden. " +
                        "Wurde die urspruengliche Klasse veraendert?");
            }
            fibonacciHash = fibonacciHashTmp;
            fibonacciHashTmp = null;
        }
    }

    /**
     * Calculates the fibonacci value of n.
     * 
     * @param n
     *           a natural number >= 0 to calculate the fibonacci value of
     * 
     * @return fibonacci value of n
     */
    public static long fibonacci(int n) {
        if (n < 0) {
            throw new IllegalArgumentException("n = " + n);
        }
        return getFibonacci(n);
    }

    private static long getFibonacci(int n) {
        if (fibonacciHash.containsKey(n)) {
            return fibonacciHash.get(n);
        } else {
            long nMinus1 = getFibonacci(n - 1);
            long nMinus2 = getFibonacci(n - 2);
            long fibonacci = nMinus1 + nMinus2;

            fibonacciHash.put(n, fibonacci);
            return fibonacci;
        }
    }

    public static void main(String[] args) {
        if (args.length != 1) {
            printUsage();
        }
        try {
            System.out.println(fibonacci(Integer.parseInt(args[0])));

        } catch (IllegalArgumentException ex) {
            printUsage();
        }
        // Serialisiere die HashMap
        try(java.io.ObjectOutputStream oos = new java.io.ObjectOutputStream(new 
                    java.io.FileOutputStream(FilePath))){
            oos.writeObject(fibonacciHash);
        }catch(java.io.FileNotFoundException e4){
            e4.printStackTrace();
            System.out.println("Die Datei konnte nicht gefunden werden");
        }catch(java.io.IOException e5){
            e5.printStackTrace();
        }
    }

    private static void printUsage() {
        System.out.println("java calc/Fiboncci n");
        System.out.println("n must be a natural number >= 0");
    }

}
