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
        do {
            x = random.nextInt(larguraTabuleiro / getLargura()) * getLargura();
            y = random.nextInt(alturaTabuleiro / getAltura()) * getAltura();

        } while (getX() == cobra.getX() && getY() == cobra.getY());// alterar quando o crescimento da cobra for adicionado
                                                                 
        setX(x);
        setY(y);
    }
}
