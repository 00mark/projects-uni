package minesweeper;

public class Field extends java.util.Observable{

    private int x, y;
    private boolean hidden, mine, marked;

    public Field(int x, int y, boolean hidden, boolean mine, boolean marked){
        this.x = x;
        this.y = y;
        this.hidden = hidden;
        this.mine = mine;
        this.marked = marked;
    }
    public int getX(){
        return x;
    } 

    public int getY(){
        return y;
    }
    
    public boolean isHidden(){
        return hidden;
    }

    public boolean isMine(){
        return mine;
    }

    public boolean isMarked(){
        return marked;
    }

    public void setHidden(boolean b){
        hidden = b;
    }
    
    public void setMarked(boolean b){
        marked = b;
    }
}
