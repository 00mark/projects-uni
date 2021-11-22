package minesweeper;

public class MineSweeper{

    public static void main(String[] args){
        int xLength, yLength, mines;
        xLength = yLength = mines = 0;
        try{
            xLength = Integer.parseInt(args[0]);
            yLength = Integer.parseInt(args[1]);
            mines = Integer.parseInt(args[2]);
        }catch(ArrayIndexOutOfBoundsException e){
            System.out.println("Ungueltige Eingabelaenge");
            System.exit(0);
        }catch(NumberFormatException e2){
            System.out.println("Ungueltige Eingabe(n)");
            System.exit(0);
        }
        MineSweepModel msm = new MineSweepModel(xLength, yLength, mines);
        MineSweepView msv = new MineSweepView(msm);
        msm.getArea().addObserver(msv);
        msv.draw();
        MineSweepController msc = new MineSweepController(msm, msv);
    }
}
