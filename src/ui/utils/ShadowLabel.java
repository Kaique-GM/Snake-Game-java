package ui.utils;

import java.awt.*;
import javax.swing.*;

// Classe personalizada de JLabel com sombra
public class ShadowLabel extends JLabel {

    private Color shadowColor = Color.black; // Cor da sombra
    private int shadowOffsetX = 4;           // Deslocamento horizontal da sombra
    private int shadowOffsetY = 4;           // Deslocamento vertical da sombra

    public ShadowLabel(String text, int horizontalAlignment) {
        super(text, horizontalAlignment);
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2d = (Graphics2D) g.create();
        
        // Desenha a sombra
        g2d.setColor(shadowColor);
        g2d.drawString(getText(), shadowOffsetX, shadowOffsetY + g.getFontMetrics().getAscent());

        // Desenha o texto original
        g2d.setColor(getForeground());
        g2d.drawString(getText(), 0, g.getFontMetrics().getAscent());

        g2d.dispose();
    }
}