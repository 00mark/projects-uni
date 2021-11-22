package wrapper;

/**
 * Testet die Funktionen der IntArr Klasse
 * @version 06.11.17
 * @author Mark Hiltenkamp
 */
public class IntArrTest{
    
    public static void main(String[] args){
        IntArr iA = null;
        System.out.print("Kein Array eingegeben, File existiert nicht" +
                " fuehrt zu einer IOException?\n> ");
        try{
            iA = new IntArr("test1");
            System.out.println(false);
        }catch(java.io.IOException e1){
            System.out.println(true);
        }

        System.out.print("Array und Name eingegeben fuehrt zu keiner" +
                " IOException?\n> ");
        try{
            int[] a = {0, -1, 10, 3};
            iA = new IntArr("test2", a);
            System.out.println(true);
        }catch(java.io.IOException e2){
            System.out.println(false);
        }

        System.out.print("Kein Array eingegeben, File existiert fuehrt" +
                " zu keiner IOException?\n> ");
        try{
            iA = new IntArr("test2");
            System.out.println(true);
        }catch(java.io.IOException e3){
            System.out.println(false);
        }

        System.out.println("Datei wurde erstellt?\n> " 
                + new java.io.File(System.getProperty("user.dir")
                    + "/test2").exists());

        System.out.println("Das persistente Array beinhaltet die erwarteten" +
                " Elemente?\n> " + (iA.getInt(0) == 0 && iA.getInt(1) == -1
                && iA.getInt(2) == 10 && iA.getInt(3) == 3));

        System.out.print("Der Versuch auf eine Position zuzugreifen, die" +
                " sich nicht im persistenten Array befindet fuehrt zu einer" +
                " IllegalArgumentException?\n> ");
        try{
            iA.getInt(10);
            System.out.println(false);
        }catch(IllegalArgumentException e4){
            System.out.println(true);
        }

        System.out.println("Das persistente Array hat die erwartete Laenge?\n> "
                + (iA.getLength() == 4));

        System.out.print("Elemente des persistenten Arrays koennen geaendert" +
                " werden?\n> ");
        iA.changeInt(0, 100);
        iA.changeInt(3, 13);
        System.out.println(iA.getInt(0) == 100 && iA.getInt(1) == -1
                && iA.getInt(2) == 10 && iA.getInt(3) == 13);

        System.out.print("Der Versuch Elemente zu aendern, die sich nicht im" +
                " persistenten Array befinden fuehrt zu einer" +
                " IllegalArgumentException?\n> ");
        try{
            iA.changeInt(-1, 1);
            System.out.println(false);
        }catch(IllegalArgumentException e5){
            System.out.println(true);
        }

        System.out.print("Anlagen eines neuen persistenten Arrays mit Array" +
                " bei schon vorhandener Datei, ueberschreibt diese?\n> ");
        int[] a2 = {1, 2, 3};
        try{
            iA = new IntArr("test2", a2);
        }catch(java.io.IOException e6){
            System.err.println("Die Datei konnte nicht angelegt werden");
        }
        int[] content = iA.getAllInts();
        System.out.println(content[0] == 1 && content[1] == 2
                && content[2] == 3 && content.length == 3);

        System.out.print("Nachdem die Datei geschlossen wurde, kann nicht" +
                " mehr auf sie zugegriffen werden?\n> ");
        iA.closeFile();
        try{
            iA.getInt(1);
            System.out.println(false);
        }catch(IllegalArgumentException e7){
            System.out.println(true);
        }   
    }
}
