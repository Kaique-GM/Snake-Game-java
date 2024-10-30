package entities;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

public class Cobra extends Quadrado {

    // Lista para armazenar o corpo da cobra
    private List<Cobra> corpo = new ArrayList<>();

    public Cobra(int largura, int altura, Color cor) {
        super(largura, altura, cor);
    }

    public List<Cobra> getCorpo() {
        return corpo;
    }

    // Método para verificar se a cobra comeu a comida
    public boolean comeuComida(Comida comida) {
        if (this.getX() == comida.getX() && this.getY() == comida.getY()) {
            return true;
        }
        return false;
    }

    // Método que adicionar um quadrado ao corpo da cobra
    public void crescimentoCobra() {
        // Se a lista de corpos estiver vazia a primeira parte do corpo será adicionada na posição da cabeça
        if (corpo.isEmpty()) {
            Cobra corpinho = new Cobra(this.largura, this.altura, this.cor);
            corpinho.setX(this.getX());
            corpinho.setY(this.getY());
            corpo.add(corpinho);
        } else {
            // Se não estiver vazia, adiciona na posição da ultima parte da cobra
            Cobra ultimoCorpinho = corpo.get(corpo.size() - 1);
            Cobra corpinho = new Cobra(this.largura, this.altura, this.cor);
            corpinho.setX(ultimoCorpinho.getX());
            corpinho.setY(ultimoCorpinho.getY());
            corpo.add(corpinho);
        }
    }

    // Método para movimentar o corpo da cobra
    public void movimentoCorpo() {

        for (int i = corpo.size() - 1; i > 0; i--) {
            corpo.get(i).setX(corpo.get(i - 1).getX());
            corpo.get(i).setY(corpo.get(i - 1).getY());
        }
        // A primeira parte segue a cabeça da cobra
        if (!corpo.isEmpty()) {
            corpo.get(0).setX(this.getX());
            corpo.get(0).setY(this.getY());
        }
    }

    //Método para verificar se a cabeça da cobra colide com alguma parte do corpo
    public boolean colisao(){
        for(Cobra corpinho : corpo){
        if(this.getX() == corpinho.getX() && this.getY() == corpinho.getY()){
            return true;
        }
    }
        return false;
    }

}
