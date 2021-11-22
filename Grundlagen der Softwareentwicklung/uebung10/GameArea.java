package minesweeper;

import java.util.Observable;
import java.util.Observer;
import java.util.LinkedList;

public class GameArea extends Observable{
    
    private Field[][] area;
    private LinkedList<Observer> l = new LinkedList<>();

    public GameArea(Field[][] area){
        this.area = area;
    }
    
    public Field[][] getArea(){
        return area;
    }

    public boolean isHidden(int x, int y){
        if(inPlayArea(x, y))
            return area[x - 1][y - 1].isHidden();
        return false;
    }

    public boolean isMine(int x, int y){
        if(inPlayArea(x, y))
            return area[x - 1][y - 1].isMine();
        return false;
    }

    public boolean isMarked(int x, int y){
        if(inPlayArea(x, y))
            return area[x - 1][y - 1].isMarked();
        return false;
    }

    public int[][] neighbors(int x, int y){
        int[][] n = {{x + 1, y}, {x + 1, y + 1}, {x, y + 1}, {x - 1, y + 1},
            {x - 1, y}, {x - 1, y - 1}, {x, y - 1}, {x + 1, y -1}};
        return n;
    }

    public int getNumberOfMines(int x, int y){
        int mineCount = 0;
        for(int[] neighbor : neighbors(x, y)){
            try{
                if(isMine(neighbor[0], neighbor[1]))
                        mineCount++;
            }catch(ArrayIndexOutOfBoundsException e){
            }
        }
        return mineCount;
    }

    public void setHidden(int x, int y, boolean b){
        if(inPlayArea(x, y))
            area[x - 1][y - 1].setHidden(b);
    }

    public void setMarked(int x, int y, boolean b){
        if(inPlayArea(x, y))
            area[x - 1][y - 1].setMarked(b);
        notifyObservers(0);
    }

    public boolean inPlayArea(int x, int y){
        if(x < 1 || x > area.length || y > area[0].length || y < 1) 
            return false;
        return true;
    }

    public boolean allUncovered(){
        boolean uncov = true;
        for(Field[] fields : area){
            for(Field field : fields){
                if(!field.isMine())
                    uncov = uncov && !field.isHidden();
            }
        }
        return uncov;
    }

    public void addObserver(Observer ob){
        l.add(ob);
    }

    public void deleteObserver(Observer ob){
        if(l.size() == 0)
            throw new RuntimeException("RuntimeException");
        l.remove(ob);
    }

    public int countObservers(){
        return l.size();
    }

    public void notifyObservers(int won){
        for(Observer ob : l){
            ob.update(this, won);
        }
    }
}
