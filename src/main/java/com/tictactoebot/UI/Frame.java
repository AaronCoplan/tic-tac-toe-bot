package com.tictactoebot.UI;

import javax.swing.*;
import java.awt.*;

/**
 * Created by Devin on 2/11/2017.
 */

public class Frame {
    public static JFrame frame;
    public static JPanel panel;
    public static final int WIDTH = 800;
    public static final int HEIGHT = 800;
    public static final int MARGIN = 50;
    private static Input input = new Input();
    public static Position[] cornerCoords = new Position[] {    //Coordinates where the lines in the board cross
            new Position((WIDTH + MARGIN)/3, (HEIGHT + MARGIN)/3),
            new Position((2 * WIDTH - MARGIN)/3, (HEIGHT + MARGIN)/3),
            new Position((WIDTH + MARGIN)/3, (2 * HEIGHT - MARGIN)/3),
            new Position((2 * WIDTH - MARGIN)/3, (2 * HEIGHT - MARGIN)/3)
    };

    private Frame(){
    }



    public static void createFrame(){
        frame = new JFrame();
        frame.setSize(WIDTH, HEIGHT);
        panel = new JPanel();
        panel.setPreferredSize(new Dimension(WIDTH, HEIGHT));

        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setResizable(false);
        frame.setTitle("Tic-tac-toe");
        //frame.setLocationRelativeTo(null);

        panel.setBackground(Color.WHITE);
        panel.setOpaque(true);

        frame.add(panel);
        panel.add(new DrawBoard());

        frame.pack();

        frame.addMouseListener(input);
        frame.addKeyListener(input);

        frame.setVisible(true);

        frame.repaint(0,0,WIDTH, HEIGHT);
    }

    public static void recreatePanel(){
        frame.remove(panel);
        panel = new JPanel();
        panel.setPreferredSize(new Dimension(WIDTH, HEIGHT));
        panel.setBackground(Color.WHITE);
        panel.setOpaque(true);

        frame.add(panel);
        panel.add(new DrawBoard());

        frame.pack();
        frame.repaint(0,0,WIDTH, HEIGHT);
    }

    public static void repaint(){
        panel.repaint(0,0, WIDTH, HEIGHT);
    }

    public static void add(JComponent c){
        panel.add(c);
    }

    public static int askXO(){
        Object[] possibleValues = {"X", "O"};
        Object selectedValue = JOptionPane.showInputDialog(null,
                "Do you want to be X or O?", "Tic-tac-toe",
                JOptionPane.INFORMATION_MESSAGE, null,
                possibleValues, possibleValues[0]);

        if (selectedValue.equals(possibleValues[0])) {
            return 0;
        } else {
            return 1;
        }
    }

    public static Position[] getCornerCoords(){
        return cornerCoords;
    }
}

