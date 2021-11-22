/**
 * A Stack that holds Strings. Works after the LIFO (Last in first out)
 * principle.
 * 
 * @author Mathias Menninghaus (mathias.menninghaus@uos.de)
 * 
 */
public class StringStack {

    public StringStack() {
        this.first = null;
    }

    /**
     * Kopiert die Attribute der eingegebenen StringStack Instanz und erzeugt
     * eine neue unabhaengige Instanz
     * @param s zu kopierende StringStack Instanz
     */
    public StringStack(StringStack s){
        this.first = null;
        StringStack tmpStack = new StringStack();

        while(!s.empty())
            tmpStack.push(s.pop());

        while(!tmpStack.empty()){
            push(tmpStack.peek());
            s.push(tmpStack.peek());
            tmpStack.pop();
        }
    }

    private StringStackEntry first;

    /**
     * Tests, whether this StringStack is empty or not.
     * 
     * @return true if this StringStack is empty, else false
     */
    public boolean empty() {
        return this.first == null;
    }

    /**
     * Returns the first element in the stack. Throws Exception when stack is
     * empty
     * 
     * @return First element or null if stack is empty
     * @throws RuntimeException
     *            if stack is empty.
     */
    public String peek() {
        if (!this.empty()) {
            return first.getString();
        } else {
            throw new RuntimeException("Stack is empty");
        }
    }

    /**
     * Deletes the first element in the stack and returns it. Throws Exception
     * when stack is empty
     * 
     * @return first element in the stack
     * @throws RuntimeException
     *            if stack is empty.
     */
    public String pop() {
        if (!this.empty()) {
            String ret = first.getString();
            this.first = this.first.getNext();
            return ret;
        } else {
            throw new RuntimeException("Stack is empty");
        }
    }

    /**
     * Puts the String s on the stack.
     * 
     * @param s
     *           String to be added.
     */
    public void push(String s) {
        if (this.empty()) {
            first = new StringStackEntry(s, null);
        } else {
            first = new StringStackEntry(s, first);
        }
    }

}
