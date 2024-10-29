package ui;

import javax.swing.*;

import entities.Cobra;
import entities.Comida;
import entities.Quadrado;

import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class Modo1 extends JFrame {

    private JPanel tabuleiro;
    private JPanel menu;
    private JTextField placarField;
    private String direcao = "direita";
    private long tempoAtualizacao = 100;
    private int incremento = 20;
    private Cobra cobra;
    private Comida comida;
    private int larguraTabuleiro, alturaTabuleiro, quadradoXadrez;
    private int placar = 0;

    public Modo1() {

        larguraTabuleiro = alturaTabuleiro = 400;
        quadradoXadrez = 20;

        // Configurações
        setTitle("Jogo da Cobrinha");
        setSize(alturaTabuleiro, larguraTabuleiro + 30);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Cria a Cobra
        cobra = new Cobra(20, 20, Color.BLACK);
        cobra.setX(larguraTabuleiro / 2);
        cobra.setY(alturaTabuleiro / 2);

        // Cria a Comida e coloca em uma posição aleatoria
        comida = new Comida(20, 20, Color.red);
        comida.gerarComida(larguraTabuleiro, alturaTabuleiro, cobra);

        ///////////////////////////////////////////// Menu /////////////////////////////////////////////////////////

        menu = new JPanel();
        menu.setLayout(new FlowLayout());

        JButton playButton = new JButton("Play");
        JButton resetButton = new JButton("Reiniciar");
        JButton pauseButton = new JButton("Pausar");
        placarField = new JTextField("Placar: 0", 10);
        placarField.setEditable(false);

        menu.add(playButton);
        menu.add(resetButton);
        menu.add(pauseButton);
        menu.add(placarField);

        ///////////////////////////////////////////////////////////////////////////////////////////////////////////

        ///////////////////////////////////////////// Tabuleiro //////////////////////////////////////////////////


        tabuleiro = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                colorirTabuleiro(g);

                g.setColor(cobra.getCor());
                g.fillRect(cobra.getX(), cobra.getY(), cobra.getAltura(), cobra.getLargura());

                g.setColor(comida.getCor());
                g.fillRect(comida.getX(), comida.getY(), comida.getLargura(), comida.getAltura());
            }
        };

        add(menu, BorderLayout.NORTH);
        add(tabuleiro, BorderLayout.CENTER);

        ///////////////////////////////////////////////////////////////////////////////////////////////////////////

        setVisible(true);

        //////////////////////////////////////// Funções dos Botões //////////////////////////////////////////////

        // ActionListener para o botão Iniciar
        playButton.addActionListener(e -> {
            Play();
            tabuleiro.requestFocusInWindow(); // Devolve o foco para o painel
        });

        // ActionListener para o botão Reset
        resetButton.addActionListener(e -> {
            Reiniciar();

        });

        // ActionListener para o botão Pausar
        pauseButton.addActionListener(e -> {
            Pausar();

        });

        tabuleiro.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {

                switch (e.getKeyCode()) {
                    case KeyEvent.VK_LEFT:
                    case KeyEvent.VK_A:
                        if (!direcao.equals("direita")) {
                            direcao = "esquerda";
                        }
                        break;
                    case KeyEvent.VK_RIGHT:
                    case KeyEvent.VK_D:
                        if (!direcao.equals("esquerda")) {
                            direcao = "direita";
                        }
                        break;
                    case KeyEvent.VK_UP:
                    case KeyEvent.VK_W:
                        if (!direcao.equals("baixo")) {
                            direcao = "cima";
                        }
                        break;
                    case KeyEvent.VK_DOWN:
                    case KeyEvent.VK_S:
                        if (!direcao.equals("cima")) {
                            direcao = "baixo";
                        }
                        break;

                }
            }
        });

        ///////////////////////////////////////////////////////////////////////////////////////////////////////////

        tabuleiro.setFocusable(true);
        tabuleiro.requestFocusInWindow();

    }

    private void Play() {

        new Thread(() -> {
            while (true) {
                try {
                    Thread.sleep(tempoAtualizacao);

                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                switch (direcao) {
                    case "esquerda":
                        cobra.setX(cobra.getX() - incremento);
                        break;
                    case "direita":
                        cobra.setX(cobra.getX() + incremento);
                        break;
                    case "cima":
                        cobra.setY(cobra.getY() - incremento);
                        break;
                    case "baixo":
                        cobra.setY(cobra.getY() + incremento);
                        break;

                }
                if(cobra.comeuComida(comida)){
                    placar++;
                    placarField.setText("Placar: " + placar);

                    comida.gerarComida(larguraTabuleiro, alturaTabuleiro, cobra);
                    
                }

                tabuleiro.repaint();

            }
        }).start();
    }

    private void Reiniciar() {
        // Adicione aqui a lógica para reiniciar o jogo
        JOptionPane.showMessageDialog(this, "Jogo Reiniciado!", "Reset", JOptionPane.INFORMATION_MESSAGE);
    }

    private void Pausar() {
        // Interrompe o while(!reset) do método Iniciar() pausando o jogo.
        JOptionPane.showMessageDialog(this, "Jogo Pausado!", "Pause", JOptionPane.INFORMATION_MESSAGE);
    }

    //////////////////////////////////////////// Métodos ///////////////////////////////////////////////////////

    //Metódo para pintar o Tabuleiro
    private void colorirTabuleiro(Graphics g){
        for(int i = 0; i < larguraTabuleiro / quadradoXadrez; i++){
            for(int j = 0; j < alturaTabuleiro / quadradoXadrez; j++){
                if((i + j) % 2 == 0){
                    g.setColor(Color.GREEN.brighter());
                }else{
                    g.setColor(Color.GREEN.darker());
                }
                g.fillRect(i * quadradoXadrez, j * quadradoXadrez, quadradoXadrez, quadradoXadrez);
            }
        }
    }

    ///////////////////////////////////////////////////////////////////////////////////////////////////////////
}