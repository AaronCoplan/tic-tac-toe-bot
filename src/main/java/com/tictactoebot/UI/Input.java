package com.tictactoebot.UI;

import com.tictactoebot.gameEngine.handlers.GameStateHandler;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

/**
 * Created by Devin on 2/11/2017.
 */
public class Input implements MouseListener, KeyListener {    //Class created to abstract mouse clicks

    @Override
    public void mouseReleased(MouseEvent e) {
        if(GameStateHandler.isPlayerTurn() && !GameStateHandler.isGameOver()){
            GameStateHandler.onUserInput(e.getX(), e.getY());
        }else{
            System.out.println("NOT ENABLED");
        }
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if(e.getKeyCode() == KeyEvent.VK_SPACE){
            //GameStateHandler.restartGame = true;
            // TODO: reimplement this
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

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyReleased(KeyEvent e) {
    }
}
