package com.tictactoebot.UI;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

/**
 * Created by Devin on 2/11/2017.
 */
public class Input implements MouseListener {    //Class created to abstract mouse clicks

    @Override
    public void mouseReleased(MouseEvent e) {
        if(GameStateHandler.playerTurn){
            GameStateHandler.onUserInput(e);
        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {
    }

    @Override
    public void mousePressed(MouseEvent e) {
    }

    @Override
    public void mouseEntered(MouseEvent e) {
    }

    @Override
    public void mouseExited(MouseEvent e) {
    }
}
