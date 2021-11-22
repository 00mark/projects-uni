import static java.lang.System.*;
/**
 * @version 04.20.17
 * @author Mark Hiltenkamp
 * Testet die Funktionalitaeten der StringStack Klasse, insbesondere die
 * des Copy-Constructors
 */
public class StackTest{
    /**
     * Legt mehrere StringStack Instanzen an, indem unter Anderem 
     * vorhandene StringStack Instanzen kopiert werden und gibt diese aus
     * @param args Kommandozeilen Eingabe(ohne Nutzung)
     */
    public static void main(String[] args){
        StringStack s1 = new StringStack();
        StringStack s2 = new StringStack(s1);
        s2.push("a");
        s2.push("b");
        s2.push("c");
        StringStack s3 = new StringStack(s2);
        s3.push("d");
        s3.push("e");
        s3.push("f");
        printStack(s1);
        printStack(s2);
        printStack(s3);
        StringStack s4 = new StringStack();
        if(s1.empty())
            out.println("Der erste StringStack wurde nicht veraendert, " +
                     "obwohl eine Kopie erzeugt und veraendert wurde\n " +
                     "=> Deep Copy");
        else
            out.println("Der erste StringStack hat sich durch Aenderung " +
                     "einer Kopie selbst veraendert\n => Shallow Copy");
    }
    /**
     * Gibt die einzelnen Strings des eingegebenen String Stack aus
     * @param StringStack der auszugebende Stack
     */
    public static void printStack(StringStack s){
        StringStack tmpStack = new StringStack();
        out.println("+------+");
        while(!s.empty()){
            tmpStack.push(s.peek());
            out.printf("|%6s|\n",s.pop());
        }
        out.println("+------+");
        while(!tmpStack.empty())
            s.push(tmpStack.pop());
    }
}
