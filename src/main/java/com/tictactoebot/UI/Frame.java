package com.tictactoebot.UI;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.ImageObserver;
import java.io.File;
import java.io.IOException;

import static com.tictactoebot.UI.Frame.MARGIN;

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

    public static void add(JComponent c){
        panel.add(c);
    }
}

class DrawBoard extends JComponent{
    int margin = MARGIN;

    public Dimension getPreferredSize(){
        return new Dimension(Frame.WIDTH, Frame.HEIGHT);
    }

    public void paintComponent(Graphics g){
        super.paintComponent(g);
        g.setColor(Color.black);
        g.fillRect(Frame.cornerCoords[0].x, margin, 10, Frame.HEIGHT - 2 * margin);
        g.fillRect(Frame.cornerCoords[1].x, margin, 10, Frame.HEIGHT - 2 * margin);
        g.fillRect(margin, Frame.cornerCoords[0].y, Frame.WIDTH - 2 * margin, 10);
        g.fillRect(margin, Frame.cornerCoords[2].y, Frame.WIDTH - 2 * margin, 10);
        if(GameStateHandler.gameOver) {
            if(GameStateHandler.winnerNum == -1){
                g.drawString("Tie!",Frame.WIDTH/2 - 10, margin);
            } else {
                g.drawString((GameStateHandler.winnerNum == 0 ? 'X' : 'O') + " wins", Frame.WIDTH / 2 - 10, margin);
            }
        }
    }
}

class DrawMove extends JLabel {
    public DrawMove(char XorY, Position p) throws IOException {
        super(new ImageIcon(ImageIO.read(new File("./assets/" + XorY + ".png"))));
        setSize(getIcon().getIconWidth(), getIcon().getIconHeight());
        Frame.add(this);
        setLocation(p.x - getWidth() / 2, p.y - getHeight() / 2);
    }
}