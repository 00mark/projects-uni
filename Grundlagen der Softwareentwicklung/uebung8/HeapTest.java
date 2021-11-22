package util;

/**
 * Testet das Serialisieren und Deserialisieren eines Heaps
 * @version 06.20.17
 * @author Mark Hiltenkamp
 */
public class HeapTest{

    public static void main(String[] args){
        // Aktueller Pfad
        String FilePath = System.getProperty("user.dir") + "/HeapContent.ser";
        Heap<Integer> h = new Heap<>();
        h.insert(1);
        h.insert(-1);
        h.insert(2);
        h.insert(-2);
        h.insert(0);
        // Serialisiere 
        try(java.io.ObjectOutputStream oos = new java.io.ObjectOutputStream(
                    new java.io.FileOutputStream(FilePath))){
            oos.writeObject(h);
        }catch(java.io.IOException e1){
            e1.printStackTrace();
        }
        Heap<Integer> h2 = null;
        // Deserialisiere 
        try(java.io.ObjectInputStream ois = new java.io.ObjectInputStream(
                    new java.io.FileInputStream(FilePath))){
            h2 = (Heap<Integer>)ois.readObject();
        }catch(java.io.IOException e2){
            e2.printStackTrace();
        }catch(ClassNotFoundException e3){
            e3.printStackTrace();
        }
        Integer[] a1 = new Integer[5];
        Integer[] a2 = new Integer[5];
        for(int i = 0; !h.empty(); i++)
            a1[i] = h.deleteFirst();
        for(int i = 0; !h2.empty(); i++)
            a2[i] = h2.deleteFirst();

        System.out.print("Normaler und deseriealisierter "
                + "Heap sind gleich?\n> ");
        // Haben die Heaps gleich viele Elemente und sind diese gleich?
        System.out.println(a1[0].equals(a2[0]) && a1[1].equals(a2[1]) &&
                a1[2].equals(a2[2]) && a1[3].equals(a2[3]) &&
                a1[4].equals(a2[4]) && a1.length == a2.length);
    }
}
