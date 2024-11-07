package ui.mode;

import javax.swing.*;

import entities.Cobra;
import entities.Comida;
import ui.utils.Game;
import ui.utils.SoundManager;
import ui.views.TelaDerrota;
import ui.views.TelaInicial;
import ui.views.TelaPause;
import ui.views.TelaPlay;

import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;

public class Easy extends JFrame implements Game {

    private JPanel tabuleiro;
    private JPanel menu;
    private JTextField placarField;
    private Cobra cobra;
    private Comida comida;
    private String direcao = "direita";
    private long tempoAtualizacao = 100;
    private int incremento = 20;
    private int larguraTabuleiro, alturaTabuleiro, quadradoXadrez;
    private int placar = 0;
    private Thread threadDoJogo;
    private boolean rodando;
    private boolean jogoPausado;

    // Objeto para manipular a música
    SoundManager sound = new SoundManager();

    public Easy() {

        // Música de fundo
        sound.loadAndPlayMusic("/resources/sounds/easy.wav");
        sound.playMusicInLoop();

        larguraTabuleiro = alturaTabuleiro = 400;
        // tamanho de cada quadrado
        quadradoXadrez = 20;

        // Configurações
        setTitle("Snake Game");
        setSize(alturaTabuleiro + 14, alturaTabuleiro + 80);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Cria a Cobra
        cobra = new Cobra(20, 20, new Color(5, 99, 26));
        cobra.setX(larguraTabuleiro / 2);
        cobra.setY(alturaTabuleiro / 2);

        // Cria a Comida e coloca em uma posição aleatoria
        comida = new Comida(20, 20, Color.red.darker());
        comida.gerarComida(larguraTabuleiro, alturaTabuleiro, cobra);

        ///////////////////////////////////////////// Menu /////////////////////////////////////////////////////////

        menu = new JPanel();
        menu.setLayout(new FlowLayout());
        menu.setBackground(new Color(255, 224, 126));

        JButton resetButton = new JButton("Restart");
        resetButton.setFont(loadFont("/resources/fonts/pricedown.ttf", 20)); // Fonte para as letras
        JButton pauseButton = new JButton("Pause");
        pauseButton.setFont(loadFont("/resources/fonts/pricedown.ttf", 20)); // Fonte para as letras
        placarField = new JTextField("Score: 0", 10);
        placarField.setOpaque(false);
        placarField.setBorder(null);
        placarField.setEditable(false);

        menu.add(resetButton);
        menu.add(pauseButton);
        menu.add(placarField);

        ///////////////////////////////////////////////////////////////////////////////////////////////////////////

        ///////////////////////////////////////////// Tabuleiro //////////////////////////////////////////////////

        tabuleiro = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                // Desenha em o tabuleiro em xadrez
                colorirTabuleiro(g);

                // Desenha somente a Cabeça da Cobra
                g.setColor(cobra.getCor());
                g.fillRect(cobra.getX(), cobra.getY(), cobra.getAltura(), cobra.getLargura());

                // Desenha os olhos da cobra
                desenharOlhos(g, cobra.getX(), cobra.getY());

                g.setColor(cobra.getCor());
                // Desenha o corpo
                for (Cobra corpoCobra : cobra.getCorpo()) {
                    g.fillRect(corpoCobra.getX(), corpoCobra.getY(), corpoCobra.getLargura(), corpoCobra.getAltura());
                }

                g.setColor(comida.getCor());
                g.fillRect(comida.getX(), comida.getY(), comida.getLargura(), comida.getAltura());
            }
        };

        add(menu, BorderLayout.NORTH);
        add(tabuleiro, BorderLayout.CENTER);

        ///////////////////////////////////////////////////////////////////////////////////////////////////////////

        setVisible(true);

        // abre a tela para dar inicio ao jogo
        abrirTelaPlay();

        //////////////////////////////////////// Funções dos Botões //////////////////////////////////////////////

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
                    case KeyEvent.VK_P:
                        Pausar();
                        break;
                    case KeyEvent.VK_R:
                        Reiniciar();
                        break;
                }
            }
        });

        ///////////////////////////////////////////////////////////////////////////////////////////////////////////

        tabuleiro.setFocusable(true);
        tabuleiro.requestFocusInWindow();

    }

    //////////////////////////////////////////// Métodos ///////////////////////////////////////////////////////

    // Método para abrir a tela de dar início ao jogo
    private void abrirTelaPlay() {
        new TelaPlay(this);
    }

    // Método para voltar para Home
    @Override
    public void Home() {
        sound.stopMusic();
        dispose();

        new TelaInicial();
    }

    @Override
    // Metódo para iniciar o jogo
    public void Play() {

        // Se já está em execução, para a thread atual
        if (threadDoJogo != null && rodando) {
            rodando = false; // Para o jogo atual
            try {
                threadDoJogo.join(); // Aguarda a thread terminar
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        rodando = true;

        threadDoJogo = new Thread(() -> {
            while (rodando) {
                // Se o jogo estiver pausado
                while (jogoPausado) {
                    try {
                        Thread.sleep(100); // Espera enquanto estiver pausado
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }

                try {
                    Thread.sleep(tempoAtualizacao);

                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                // Move o corpo da cobra
                cobra.movimentoCorpo();

                switch (direcao) {
                    case "esquerda":
                        cobra.setX(cobra.getX() - incremento);
                        if (cobra.getX() < 0) {
                            cobra.setX(larguraTabuleiro - quadradoXadrez); // Realoca para o lado direito
                        }
                        break;
                    case "direita":
                        cobra.setX(cobra.getX() + incremento);
                        if (cobra.getX() >= larguraTabuleiro) {
                            cobra.setX(0); // Realoca para o lado esquerdo
                        }
                        break;
                    case "cima":
                        cobra.setY(cobra.getY() - incremento);
                        if (cobra.getY() < 0) {
                            cobra.setY(alturaTabuleiro - quadradoXadrez); // Realoca para o lado inferior
                        }
                        break;
                    case "baixo":
                        cobra.setY(cobra.getY() + incremento);
                        if (cobra.getY() >= alturaTabuleiro) {
                            cobra.setY(0); // Realoca para o lado superior
                        }
                        break;

                }
                // Verifica se houve colisão da cabeça com o corpo
                if (cobra.colisao()) {
                    rodando = false;

                    sound.stopMusic(); // Para a musica de fundo
                    sound.playSoundEffect("/resources/sounds/death.wav"); // Toca a música de morte
                    new TelaDerrota(this); // chama a tela reiniciar desse modo de jogo
                }
                // Verifica se a Cobra passou por cima da comida
                if (cobra.comeuComida(comida)) {
                    placar++;
                    placarField.setText("Score: " + placar);

                    sound.playSoundEffect("/resources/sounds/eat.wav");

                    cobra.crescimentoCobra();
                    comida.gerarComida(larguraTabuleiro, alturaTabuleiro, cobra);
                }

                tabuleiro.repaint();

            }
        });
        threadDoJogo.start(); // Inicia a nova thread
    }

    // Metódo para Reiniciar
    @Override
    public void Reiniciar() {
        rodando = false; // para a execuçao

        try {
            if (threadDoJogo != null) {
                threadDoJogo.join(); // Espera a thread terminar
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // Reposiciona a cobra no meio
        cobra.setX(larguraTabuleiro / 2);
        cobra.setY(alturaTabuleiro / 2);
        cobra.getCorpo().clear(); // Limpa a lista que guarda cada pedaço do corpo

        // Gera a comida novamente
        comida.gerarComida(larguraTabuleiro, alturaTabuleiro, cobra);

        // Zera o placar
        placar = 0;
        placarField.setText("Score: " + placar);

        // Reinicializa a direção
        direcao = "direita";

        sound.playMusicInLoop(); // Reinicia a musica
        Play();

        tabuleiro.requestFocusInWindow();
    }

    // Metódo para Pausar
    @Override
    public void Pausar() {
        jogoPausado = true; // Pausa o jogo
        new TelaPause(this); // Chama a tela de pause
    }

    // Metódo para Despausar
    @Override
    public void desPausar() {
        jogoPausado = true; // True Temporario

        // Aguardar um pequeno delay
        new Thread(() -> {
            try {
                Thread.sleep(500); // Delay de 500 milissegundos
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            // Após o delay, despausa o jogo
            jogoPausado = false;
        }).start();

        tabuleiro.requestFocusInWindow();
    }

    // Metódo para pintar o Tabuleiro
    private void colorirTabuleiro(Graphics g) {
        for (int i = 0; i < larguraTabuleiro / quadradoXadrez; i++) {
            for (int j = 0; j < alturaTabuleiro / quadradoXadrez; j++) {
                if ((i + j) % 2 == 0) {
                    g.setColor(new Color(255, 245, 224));
                } else {
                    g.setColor(new Color(255, 220, 135));
                }
                g.fillRect(i * quadradoXadrez, j * quadradoXadrez, quadradoXadrez, quadradoXadrez);
            }
        }
    }

    // Método para ler a fonte de dentro do .jar
    private static Font loadFont(String path, float size) {
        try {
            // Carrega a fonte usando o getResourceAsStream para encontrar a fonte no pacote
            InputStream is = TelaInicial.class.getResourceAsStream(path);
            Font font = Font.createFont(Font.TRUETYPE_FONT, is);
            GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
            ge.registerFont(font);
            return font.deriveFont(size); // Retorna a fonte com o tamanho especificado
        } catch (FontFormatException | IOException e) {
            e.printStackTrace();
            return new Font("Arial", Font.BOLD, 15); // Fonte padrão se falhar ao carregar
        }
    }

    // Método para desenhar os olhos da cobra
    private void desenharOlhos(Graphics g, int x, int y) {
        // Coordenadas dos olhos
        int olhoLadoX = x + 5; // Ajuste conforme necessário
        int olhoLadoY = y + 5; // Ajuste conforme necessário
        int olhoTamanho = 5; // Tamanho dos olhos

        // Desenha o olho esquerdo
        g.setColor(Color.WHITE); // Cor do olho
        g.fillOval(olhoLadoX - olhoTamanho, olhoLadoY - olhoTamanho, olhoTamanho * 2, olhoTamanho * 2);

        // Desenha o olho direito
        g.fillOval(olhoLadoX + 5, olhoLadoY - olhoTamanho, olhoTamanho * 2, olhoTamanho * 2);

        // Desenha a pupila esquerda
        g.setColor(Color.BLACK); // Cor da pupila
        g.fillOval(olhoLadoX - olhoTamanho / 2 - 1, olhoLadoY - olhoTamanho / 2 - 1, olhoTamanho, olhoTamanho);

        // Desenha a pupila direita
        g.fillOval(olhoLadoX + 10 - olhoTamanho / 2 - 1, olhoLadoY - olhoTamanho / 2 - 1, olhoTamanho, olhoTamanho);
    }

    ///////////////////////////////////////////////////////////////////////////////////////////////////////////
}