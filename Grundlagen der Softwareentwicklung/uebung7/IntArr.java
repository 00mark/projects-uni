package wrapper;
import java.io.RandomAccessFile;
import java.io.File;

/**
 * Wrapper Klasse, welche persistente Integer-Arrays erzeugen kann
 * @version 06.11.17
 * @author Mark Hiltenkamp
 */
public class IntArr{

    private String name;
    private int[] content;
    private RandomAccessFile randAccF;

    /**
     * Erzeugung einer neuen Datei, oder ueberschreibung einer vorhanden 
     * Datei
     * @param name der Name der Datei
     * @param content der Inhalt der Datei
     */
    public IntArr(String name, int[] content) throws java.io.IOException{
        this.name = name;
        this.content = content;
        setRandAccF();
        writeContent();
    }

    /**
     * Erwartet, dass ein persistentes Integer-Array mit dem eingegebenen
     * Namen existiert
     * @param name der Name der Datei
     */
    public IntArr(String name) throws java.io.IOException{
        this(name, null);
    }

    /**
     * Gibt den RandomAccessFile wieder
     */
    public RandomAccessFile getRandAccF(){
        return randAccF;
    }

    /**
     * Gibt den eingegebenen Inhalt des Integer-Arrays wieder
     */
    public int[] getContent(){
        return content;
    }

    /**
     * Erstellt einen neuen RandomAccessFile
     * @throws IOException, falls die Datei existiert, aber ein Verzeichnis ist, 
     *                      oder bei der Erzeugung des IntArr nur ein Name
     *                      eingegeben wurde, aber keine Datei mit diesem
     *                      Namen existiert
     */
    private void setRandAccF() throws java.io.IOException{
        try{
            if(new File(name).isDirectory())
                throw new java.io.IOException("Die eingegebenen Datei ist ein"
                        + " Verzeichnis");
            if(!(new File(name).exists())){
                if(getContent() == null)
                    // content == null => Bei der Erzeugung wurde kein 
                    // Array eingegeben
                    throw new java.io.IOException("Die Datei kann"
                            + " nicht gefunden werden und es wurden kein"
                            + " Array eingegeben");
            }
            // RandomAccessfile mit Lese- und Schreibzugriff
            randAccF = new RandomAccessFile(name, "rw"); 
        }catch(java.io.FileNotFoundException e){
            throw new java.io.IOException("Die Datei kann nicht gefunden" +
                    " werden");
        }
    }

    /**
     * Schreibt den Inhalt des Arrays (falls ein Array bei der Erzeugung 
     * eingegeben wurde) in den RandomAccessFile
     */
    private void writeContent(){
        if(getContent() != null && getRandAccF() != null){
            try{
                for(int i : getContent()){
                    getRandAccF().writeInt(i);
                }
                // Falls schon eine Datei existiert hat, werden die
                // ueberfluessigen Elemente aus der Datei geloescht
                getRandAccF().setLength(getContent().length * 4);
            }catch(java.io.IOException e){ 
                e.printStackTrace();
                System.exit(0);
            }
        }
    }

    /**
     * Schliesst den RandomAccessFile
     */
    public void closeFile(){
        try{
            getRandAccF().close();
        }catch(java.io.IOException e){
            System.err.println("Konnte die Datei nicht schliessen");
        }
    }

    /**
     * Gibt die Anzahl der Elemente wieder
     * @return die Anzahl der Elemente. 0, falls es zu einer Exception kommt 
     */
    public int getLength(){
        try{
            return (int)(getRandAccF().length() / 4);
        }catch(java.io.IOException e){
            return 0;
        }
    }

    /**
     * Gibt die Integer von (einschliesslich) startPos bis (einschliesslich)
     * endpos wieder
     * @param startPos das Erste Element, das wiedergegeben werden soll
     * @param endpos das Letzte Element, das wiedergegeben werden soll
     * @throws IllegalArgumentException, falls startPos > endPos ist, oder
     *                                   sich mindestens eine der Positionen
     *                                   nicht im Array befindet
     * @return Integer-Array mit den Elementen, null, falls es zu einer
     *         Exception kommt
     */
    public int[] getInts(int startPos, int endPos){
        if(startPos > endPos || startPos < 0 || startPos > (getLength() - 1) ||
                endPos > (getLength() - 1))
            throw new IllegalArgumentException("ungueltige Eingabewerte");
        int[] a = new int[endPos - startPos + 1];
        try{
            getRandAccF().seek(startPos * 4);
            for(int i = 0; i <= (endPos - startPos); i++){
                a[i] = getRandAccF().readInt(); 
            }
        }catch(java.io.IOException e){
            return null;
        }
        return a;
    }

    /**
     * Gibt alle Elemente des RandomAccessFiles wieder
     * @return Integer-Array der Elemente des RandomAccessFiles
     */
    public int[] getAllInts(){
        return getInts(0, getLength() - 1);
    }

    /**
     * Gibt das Element an der Position pos des IntArr wieder
     * @param pos Die Position des zurueckzugebenden Elements
     * @return das jeweilige Element
     */
    public int getInt(int pos){
        return getInts(pos, pos)[0];
    }

    /**
     * Printet alle Integer des IntArr
     */
    public void printInts(){
        for(int i : getAllInts())
            System.out.print(i +"  ");
    }

    /**
     * Veraendert den Integer an der Stelle pos
     * @param pos Position des zu aendernden Integers
     * @param changedInt der neue Integer
     * @throws IllegalArgumentException, falls Das Array die eingegebene
     *                                   Position nicht hat
     */
    public void changeInt(int pos, int changedInt){
        if(pos > -1 && pos < getLength()){
            try{
                getRandAccF().seek(pos * 4);
                getRandAccF().writeInt(changedInt); 
            }catch(java.io.IOException e){
                System.err.println("Datei konnte nicht beschrieben werden");
            }
        }else
            throw new IllegalArgumentException("Die eingegebenen Position ist" +
                    " ungueltig");
    }
}
