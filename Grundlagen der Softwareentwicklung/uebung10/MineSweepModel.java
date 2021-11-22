package minesweeper;

import java.util.Random;
import java.util.Scanner;

public class MineSweepModel{

    private int xLength, yLength, mineCount, minesFound;
    private GameArea area;

    public MineSweepModel(int xLength, int yLength, int mineCount){
        minesFound = 0;
        this.xLength = xLength;
        this.yLength = yLength;
        this.mineCount = mineCount; 
        createGameArea();
    }

    public void createGameArea(){
        Field[][] fields = new Field[xLength][yLength];
        Random r = new Random();
        int[] minePlacement = new int[mineCount];
        for(int i = 0; i < mineCount; i++)
            minePlacement[i] = -1;
        for(int mines = 0; mines < minePlacement.length; mines++){
            int mineField = r.nextInt(xLength * yLength); 
            boolean containsMine = false;
            for(int m = 0; m < mines; m++){
                if(mineField == minePlacement[m]){
                    mines--;
                    containsMine = true;
                    break;
                }
            }
            if(!containsMine)
                minePlacement[mines] = mineField;
        }
        for(int x = 0, total = 0; x < xLength; x++){
            for(int y = 0; y < yLength; y++, total++){
                boolean mineFound = false;
                for(int mine : minePlacement){
                    if(mine == total){
                        mineFound = true;
                    }
                }
                if(mineFound){
                    fields[x][y] = new Field(x, y, true, true, false); 
                }
                else
                    fields[x][y] = new Field(x, y, true, false, false);
            }
        }
        area = new GameArea(fields);
    }

    public GameArea getArea(){
        return area;
    }

    public void play(int x, int y){
        boolean lost = uncover(x, y);
        if(lost)
            area.notifyObservers(-1);
        else{
            if(area.allUncovered())
                area.notifyObservers(1);
            else 
                area.notifyObservers(0);
        }
    }

    public boolean uncover(Field f){
        return uncover(f.getX(), f.getY());
    }

    public boolean uncover(int x, int y){
        if(!area.inPlayArea(x, y));
        else{
            area.setHidden(x, y, false); 
            if(area.isMine(x, y))
                return true;
            else{
                if(area.getNumberOfMines(x, y) == 0){
                    for(int[] neighbor : area.neighbors(x, y)){
                        if(area.isHidden(neighbor[0], neighbor[1]))
                            uncover(neighbor[0], neighbor[1]);
                    }
                }
            }
        }
        return false;
    }

    public void setMarked(int x, int y, boolean b){
        if(b)
            minesFound++;
        else
            minesFound--;
        area.setMarked(x, y, b);
    }

    public boolean isMarked(int x, int y){
        return area.isMarked(x, y);
    }

    public int minesToFind(){
        return mineCount - minesFound;
    }
}
