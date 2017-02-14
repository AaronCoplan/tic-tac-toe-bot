package com.tictactoebot.UI;

import com.tictactoebot.gameEngine.GameStateHandler;

import javax.swing.*;
import java.awt.*;

import static com.tictactoebot.UI.Frame.MARGIN;

/**
 * Created by afcoplan on 2/13/17.
 */
public class DrawBoard extends JComponent {
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
