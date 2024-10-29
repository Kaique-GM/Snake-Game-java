package entities;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

public class Cobra extends Quadrado {

    private List<Cobra> corpo = new ArrayList<>();

    public Cobra(int largura, int altura, Color cor) {
        super(largura, altura, cor);
        
    }

    public boolean comeuComida(Comida comida){
        if(this.getX() == comida.getX() && this.getY() == comida.getY()){
            return true;
        }
        return false;
    }

}
