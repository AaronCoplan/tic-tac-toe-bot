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
        if(GameStateHandler.isGameOver()) {
            if(GameStateHandler.getWinnerNum() == -1){
                g.drawString("Tie!",Frame.WIDTH/2 - 10, margin);
            } else {
                g.drawString((GameStateHandler.getWinnerNum() == 0 ? 'X' : 'O') + " wins", Frame.WIDTH / 2 - 10, margin);
            }
        }
    }
}
