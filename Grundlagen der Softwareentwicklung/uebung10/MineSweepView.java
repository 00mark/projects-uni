package minesweeper;

import java.awt.Color;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.JPanel;
import javax.swing.JFrame;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import java.util.Observable;
import java.util.Observer;
import java.util.HashMap;
import java.util.Timer;
import java.util.TimerTask;

public class MineSweepView implements Observer{

    private MineSweepModel msm;
    private GameArea area = null;
    private JFrame frame;
    private JLabel minesToFind;
    private static JLabel passedTime;
    private JPanel panel;
    private HashMap<JButton, int[]> h = new HashMap<>();
    private Timer t;

    public MineSweepView(MineSweepModel msm){
        this.msm = msm;
        area = msm.getArea();
    }

    public void update(Observable oAble, Object o){
        area = (GameArea)oAble;
        switch((int)o){
            case -1 : drawLost(); break;
            case 0  : drawGoing(); break;
            case 1  : drawWon(); 
        }
    }

    public void drawGoing(){
        minesToFind.setText(" Mines to find : " + msm.minesToFind());
        for(JButton b : h.keySet()){
            int x = h.get(b)[0];
            int y = h.get(b)[1];
            String buttonStr = " ";
            if(area.isHidden(x, y)){
                if(area.isMarked(x, y)){
                    buttonStr = "!";
                }
                b.setText(buttonStr);
            }else{
                if(area.getNumberOfMines(x, y) > 0){
                    buttonStr = Integer.toString(area.getNumberOfMines(x,
                                y));
                    b.setText(buttonStr);
                    b.setBorderPainted(false);
                    setColor(b, area.getNumberOfMines(x , y));
                    b.setContentAreaFilled(false);
                }else
                    b.setVisible(false);
            }
        }
    }

    public void draw(){
        try{
            UIManager.setLookAndFeel(
                    "com.sun.java.swing.plaf.motif.MotifLookAndFeel");
        } 
        catch (UnsupportedLookAndFeelException e) {
        }
        catch (ClassNotFoundException e) {
        }
        catch (InstantiationException e) {
        }
        catch (IllegalAccessException e) {
        }
        int xLength = area.getArea().length;
        int yLength = area.getArea()[0].length;
        frame = new JFrame("MineSweeper");    
        frame.setLayout(new java.awt.BorderLayout(2, 2));
        frame.setSize(xLength * 30, yLength * 30 + 15);
        panel = new JPanel();
        panel.setLayout(new java.awt.GridLayout(yLength, xLength, -10, -10));
        panel.setSize(xLength * 40, yLength * 40);
        minesToFind = new JLabel(" Mines to find : " + msm.minesToFind());
        minesToFind.setSize(xLength * 40, 10);
        passedTime = new JLabel("0:00 ", JLabel.RIGHT);
        passedTime.setSize(xLength * 40, 10);
        for(int y = 1; y <= yLength; y++){
            for(int x = 1; x <= xLength; x++){
                JButton b = new JButton("");
                int[] i = {x, y};
                h.put(b, i);
                panel.add(b);
            }
        }
        frame.add(panel, java.awt.BorderLayout.CENTER);
        frame.add(minesToFind, java.awt.BorderLayout.NORTH);
        frame.add(passedTime, java.awt.BorderLayout.NORTH);
        frame.setVisible(true);
        t = new Timer();
        t.schedule(new TimeTask(), 1000, 1000);
    }

    public void drawLost(){
        t.cancel();
        for(JButton b : h.keySet()){
            int x = h.get(b)[0];
            int y = h.get(b)[1];
            if(msm.getArea().isMine(x, y)){
                b.setBackground(Color.BLACK);
                b.setForeground(Color.RED);
                b.setText("!");
            }
        }
        JOptionPane.showMessageDialog(frame, "Got hit by a mine",
                "Too Bad", JOptionPane.ERROR_MESSAGE);
        frame.setEnabled(false);
    }

    public void drawWon(){
        t.cancel();
        drawGoing();
        JOptionPane.showMessageDialog(frame, "Good job!", "Success",
                JOptionPane.PLAIN_MESSAGE);
        frame.setEnabled(false);
    }

    public JFrame getFrame(){
        return frame;
    }

    public JPanel getPanel(){
        return panel;
    }

    public HashMap<JButton, int[]> getButtons(){
        return h;
    }

    public void setColor(JButton b, int mines){
        switch(mines){
            case 1 : b.setForeground(Color.RED); break;
            case 2 : b.setForeground(Color.BLUE); break;
            case 3 : b.setForeground(Color.ORANGE); break;
            case 4 : b.setForeground(Color.YELLOW); break;
            case 5 : b.setForeground(Color.CYAN); break;
            case 6 : b.setForeground(Color.GREEN); break;
            case 7 : b.setForeground(Color.MAGENTA); break;
            case 8 : b.setForeground(Color.PINK); break;
            default : throw new IllegalArgumentException("mines has to be" +
                              " between 1 and 8");
        }
    }

    static class TimeTask extends TimerTask{

        private static int time = 0;

        public void run(){
            time++;
            passedTime.setText((time / 60) + ":" + ((time % 60) < 10 ? "0" + 
                        (time % 60) : (time % 60)) + " ");
        }
    }
}
