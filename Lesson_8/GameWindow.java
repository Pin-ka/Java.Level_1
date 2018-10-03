package Lesson_8;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GameWindow extends JFrame {

    //1. Данные основного окна
    private static final int WIN_HEIGHT=555;
    private static final int WIN_WIDTH=507;
    private static final int WIN_POS_X=500;
    private static final int WIN_POS_Y=170;

    private static Map field;
    private static StartNewGameWindow startNewGameWindow;

    public GameWindow()  {
        setTitle("TicTacToe");
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setBounds(WIN_POS_X,WIN_POS_Y,WIN_WIDTH,WIN_HEIGHT);
        setResizable(false);

        JPanel buttomPanel=new JPanel(new GridLayout(1,2));

        JButton btnNewGame=new JButton("Start new game");
        JButton btnExit=new JButton("Exit");

        startNewGameWindow=new StartNewGameWindow(this);

       buttomPanel.add(btnNewGame);
       buttomPanel.add(btnExit);

        btnNewGame.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                startNewGameWindow.setVisible(true);
            }
        });

        btnExit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });

        field=new Map();
        add(field,BorderLayout.CENTER);

        add(buttomPanel,BorderLayout.SOUTH);
        setVisible(true);
    }

    //передатчик!
    void startNewGame(int mode,int fieldSizeX,int fieldSizeY,int vinLen){
        field.startNewGame(mode,fieldSizeX,fieldSizeY,vinLen);
    }

    public static Map getField() {
        return field;
    }
}
