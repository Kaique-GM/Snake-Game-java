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
        do {
            int x = random.nextInt(larguraTabuleiro / largura) * largura;
            int y = random.nextInt(alturaTabuleiro / altura) * altura;
            setX(x);
            setY(y);
        } while (getX() == cobra.getX() && getY() == cobra.getY()); // alterar quando o crescimento da cobra for adicionado
    }
}
