package antRace;

import java.util.Iterator;
import java.util.LinkedList;

/**
 * An {@code Ant} is created at a specific position of an {@link AntField} with
 * an initial {@code stepCount}. When running an Ant, it will lookup the values
 * on the current and all surrounding {@link Field}
 * (Moore-neighborhood) instances and test if the position is free, i.e. has a
 * value of {@code 0}, or if the value is greater than the {@code stepCount} of
 * this Ant. For both cases, the Ant will set the value of the {@code Field} at
 * the visited position to its own {@code stepCount+1}. After an {@code Ant} has
 * successfully visited one field, it will create new {@code Ant} instances with
 * an incremented {@code stepCount} to visit the other available {@code Field}
 * elements. The Ant will run until it finds no more {@code Field} elements in
 * its neighborhood to be altered.
 * 
 * @author Mathias Menninghaus (mathias.menninghaus@uos.de)
 * 
 */
public class Ant implements Runnable{

    private AntField fields;
    private int stepCount;
    private int x;
    private int y;
    private int neighbors[][];

    /**
     * 
     * @param fields
     *           the {@code AntField} on which this {@code Ant} operates
     * @param x
     *           x-axis value of the starting position
     * @param y
     *           y-axis value of the starting position
     * @param stepCount
     *           initial stepCount of this {@code Ant}.
     * 
     * @throws IllegalArgumentException
     *            If the {@code Field} at position {@code x,y} does not exist, or
     *            if its value is < 0
     */
    public Ant(AntField fields, int x, int y, int stepCount){
        this.fields = fields;
        this.stepCount = stepCount;
        this.x = x;
        this.y = y;
        try{
            fields.getField(x, y).setValue(stepCount);
        }catch(NullPointerException nullpointerexception){
            throw new IllegalArgumentException("Das Feld existiert nicht");
        }
    }

    /**
     * Findet die NachbarPositionen der aktuellen Position
     * @return 2-dimensionales Integer Array mit den x und y Werten
     */
    public int[][] findNeighbors(){
        int neighbors[][] = {{ x + 1, y}, {x + 1, y + 1}, {x, y + 1},
            {x - 1, y + 1}, {x - 1, y}, {x - 1, y - 1}, {x, y - 1},
            {x + 1, y - 1}};
        return neighbors;
    }

    /**
     * Gibt die Nachbarn des aktuellen Feldes wieder
     * @return die Nachbarn
     */
    public int[][] getNeighbors(){
        return neighbors;
    }

    /**
     * Geht jeweils die 8 Nachbarn des aktuellen Feldes ab und prueft, ob
     * diese schon besucht wurden, bzw. ob die aktuelle Schrittzahl + 1 kleiner
     * als die Schrittzahl des jeweiligen Feldes ist. Ist dies der Fall wurde
     * ein neuer Weg gefunden. Den ersten neuen Weg betritt diese Instanz 
     * selbst, fuer jeden weiteren Weg wird ein neuer Thread angelegt
     */
    public void run(){
        LinkedList<Thread> l = new LinkedList<Thread>();
        boolean found = true;
        while(found){
            found = false;
            boolean first = true;
            // Finde die aktuellen Nachbarn
            neighbors = findNeighbors();
            for(int i = 0; i < 8; i++){
                // Ist das Feld ausserhalb?
                if(!(fields.getField(getNeighbors()[i][0],
                                getNeighbors()[i][1]) == null)){
                    synchronized(fields.getField(getNeighbors()[i][0],
                                getNeighbors()[i][1])){
                        int steps = fields.getField(getNeighbors()[i][0],
                                getNeighbors()[i][1]).getValue();
                        // Wurde ein neuer Weg gefunden?
                        if(steps == 0 || steps > stepCount + 1){
                            if(first){
                                found = true;
                                first = false;
                                x = getNeighbors()[i][0];
                                y = getNeighbors()[i][1];
                                fields.getField(x, y).setValue(stepCount + 1);
                            }else{
                                Ant ant = new Ant(
                                        fields, getNeighbors()[i][0],
                                        getNeighbors()[i][1], stepCount + 1);
                                Thread t = new Thread(ant);
                                t.start();
                                l.add(t);
                            }
                        }
                    }
                }
            }
            stepCount++;
        }
        // Warte auf die Beendigung aller erstellten Threads
        for(Thread t : l){
            try{
                t.join();
            }catch(InterruptedException interruptedexception){
                System.out.println("Interrupted");
            }
        }
    }
}
