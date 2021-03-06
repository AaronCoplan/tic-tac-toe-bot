/*
MIT License

Copyright (c) 2017 Aaron Coplan
Copyright (c) 2017 Andrew Adalian
Copyright (c) 2017 Devin Kopp

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all
copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
SOFTWARE.
*/

package com.tictactoebot.UI;

import com.tictactoebot.gameEngine.handlers.GameStateHandler;

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

    public static void close(){
        frame.setVisible(false);
        frame.dispose();
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

        if(selectedValue == null) {
            System.exit(1);
        }

        if (selectedValue.equals(possibleValues[0])) {
            return 0;
        } else {
            return 1;
        }

    }

    public static boolean askContinuous(){
        Object[] possibleValues = {"Continuous", "Single-play"};
        Object selectedValue = JOptionPane.showInputDialog(null,
                "Do you want to play continuously?", "Tic-tac-toe",
                JOptionPane.INFORMATION_MESSAGE, null,
                possibleValues, possibleValues[0]);

        if(selectedValue == null) {
            System.exit(1);
        }

        if (selectedValue.equals(possibleValues[0])) {
            return true;
        } else {
            return false;
        }
    }

    public static Position[] getCornerCoords(){
        return cornerCoords;
    }
}

