package com.tictactoebot.UI;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.io.File;
import java.io.IOException;

/**
 * Created by afcoplan on 2/13/17.
 */
public class DrawMove extends JLabel {
    public DrawMove(char XorY, Position p) throws IOException{
        super(new ImageIcon(ImageIO.read(new File("assets/" + XorY + ".png"))));
        setSize(getIcon().getIconWidth(), getIcon().getIconHeight());
        Frame.add(this);
        setLocation(p.x - getWidth() / 2, p.y - getHeight() / 2);
    }
}
