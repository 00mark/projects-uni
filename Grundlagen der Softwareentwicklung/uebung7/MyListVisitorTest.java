package util;

/**
 * Testet die accept Methode der MyList Klasse und die visit Methode der 
 * MyListVisitor Klasse
 * @version 06.04.17
 * @author Mark Hiltenkamp
 */
public class MyListVisitorTest{
    
    public static void main(String[] args){
        MyList<Integer> mLInt = new MyList<Integer>();
        MyList<Character> mLChar = new MyList<Character>();   
        MyListVisitor<Character> lVChar = new MyListVisitor<Character>();
        MyListVisitor<Integer> lVInt = new MyListVisitor<Integer>();
        Object[] passedElements;

        mLInt.add(new Integer(0));
        mLInt.add(new Integer(1));
        mLInt.add(new Integer(2));
        mLInt.add(new Integer(3));

        mLChar.add(new Character('a'));
        mLChar.add(new Character('b'));
        mLChar.add(new Character('c'));
        mLChar.add(new Character('d'));

        mLChar.accept(lVChar); 
        passedElements = lVChar.getPassedElements();
        // Character MyListVisitor sollte keine Elemente besucht haben
        System.out.print("Characters werden nicht besucht? (erwartet)\n> ");
        System.out.println(passedElements[0] == null);

        mLInt.accept(lVInt);
        passedElements = lVInt.getPassedElements();
        // Integer MyListVisitor sollte alle Elemente besucht haben
        System.out.print("Integers < 10 werden besucht?\n> ");
        System.out.println(passedElements[0].equals(3) &&
                passedElements[1].equals(2) && passedElements[2].equals(1) &&
                passedElements[3].equals(0));

        mLInt.add(new Integer(-2));
        mLInt.add(new Integer(11));
        mLInt.add(new Integer(-1));
        lVInt = new MyListVisitor<Integer>();
        mLInt.accept(lVInt);

        passedElements = lVInt.getPassedElements();
        System.out.print("Bei Integer >= 10 wird kein weiterer visit"
                + " durchgefuehrt?\n> ");
        System.out.println(passedElements[0].equals(3) &&
                passedElements[1].equals(2) && passedElements[2].equals(1) &&
                passedElements[3].equals(0) && passedElements[4].equals(-1) &&
                passedElements[5] == null);
    }
}
