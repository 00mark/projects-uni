package minesweeper;

import java.awt.event.WindowAdapter;
import java.awt.event.MouseAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.MouseEvent;
import javax.swing.JFrame;
import javax.swing.JButton;
import java.util.LinkedList;
import java.util.HashMap;
import java.util.Set;

public class MineSweepController{

    private MineSweepModel msm = null;
    private JFrame frame = null;
    private HashMap<JButton, int[]> buttonMap = null;

    public MineSweepController(MineSweepModel msm, MineSweepView msv){
        this.msm = msm;
        frame = msv.getFrame();
        //buttonList = msv.getButtons();
        buttonMap = msv.getButtons();
        addwListener();
        addmListener();
    }

    public void addwListener(){
        frame.addWindowListener(new WindowAdapter(){
            public void windowClosing(WindowEvent e){
                System.exit(0);
            }
        });
    }

    public void addmListener(){
        for(JButton b : buttonMap.keySet()){
            b.addMouseListener(new MouseAdapter(){
                public void mouseClicked(MouseEvent e){
                    int[] i = buttonMap.get(b);
                    if(e.getButton() == e.BUTTON1){
                        if(!msm.isMarked(i[0], i[1]))
                            msm.play(i[0], i[1]);
                    }
                    if(e.getButton() == e.BUTTON3){
                        if(msm.getArea().isHidden(i[0], i[1])){
                            if(msm.isMarked(i[0], i[1]))
                                msm.setMarked(i[0], i[1], false);
                            else
                                msm.setMarked(i[0], i[1], true);
                        }
                    }
                }
            });
        }
    }
}
