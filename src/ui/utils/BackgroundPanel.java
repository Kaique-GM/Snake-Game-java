package ui.utils;

import java.net.URL;

import java.awt.*;
import javax.swing.*;

//Classe para adicionar Backgrounds
public class BackgroundPanel extends JPanel {
    private Image backgroundImage;

    public BackgroundPanel(URL imageUrl) {
        if (imageUrl != null) {
            backgroundImage = new ImageIcon(imageUrl).getImage();
        } else {
            System.out.println("Imagem não encontrada");
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (backgroundImage != null) {
            g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
        }
    }
}
