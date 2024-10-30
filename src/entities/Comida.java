package entities;

import java.awt.Color;
import java.util.Random;

public class Comida extends Quadrado {

    private Random random;

    public Comida(int largura, int altura, Color cor) {
        super(largura, altura, cor);
        random = new Random();
    }

    // Método para gerar comida aleatoriamente
    public void gerarComida(int larguraTabuleiro, int alturaTabuleiro, Cobra cobra) {
        int x, y;
        boolean colisao;

        do {
            // Gera uma posição aleatória para x e y
            x = random.nextInt(larguraTabuleiro / getLargura()) * getLargura();
            y = random.nextInt(alturaTabuleiro / getAltura()) * getAltura();

            colisao = false;

            // Verifica se a nova posição da comida colide com a cabeça da cobra
            if (x == cobra.getX() && y == cobra.getY()) {
                colisao = true;
            }

            // Verifica se a nova posição da comida colide com alguma parte do corpo da cobra
            for (Cobra corpinho : cobra.getCorpo()) {
                if (x == corpinho.getX() && y == corpinho.getY()) {
                    colisao = true;
                    break;
                }
            }

        } while (colisao); // Continue até que a nova comida não colida com a cobra

        setX(x);
        setY(y);
    }
}
