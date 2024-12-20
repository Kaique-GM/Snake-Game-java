package ui.views;

import java.awt.*;
import javax.swing.*;

import ui.mode.Easy;
import ui.mode.Hard;
import ui.mode.Medium;
import ui.utils.BackgroundPanel;
import ui.utils.ShadowLabel;
import ui.utils.SoundManager;

import java.io.IOException;
import java.io.InputStream;

public class TelaInicial extends JFrame {

    private int larguraTabuleiro, alturaTabuleiro;

    SoundManager sound = new SoundManager();

    public TelaInicial() {
        // Tamanho da tela
        larguraTabuleiro = alturaTabuleiro = 400;

        // Configurações
        setTitle("Home");
        setSize(larguraTabuleiro, alturaTabuleiro);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null); // Centraliza
        setLayout(new BorderLayout());

        // Musica de Fundo
        sound.loadAndPlayMusic("/resources/sounds/home.wav");
        sound.playMusicInLoop();

        ////////////////////////////////////////// Background /////////////////////////////////////////////////////

        // Cria o painel de fundo
        BackgroundPanel backgroundPanel = new BackgroundPanel(getClass().getResource("/resources/img/Background.jpeg"));
        setContentPane(backgroundPanel); // Definindo o painel de fundo como o conteúdo da janela

        ///////////////////////////////////////////////////////////////////////////////////////////////////////////

        /////////////////////////////////////////// Titulo ///////////////////////////////////////////////////////

        // Cria e adiciona um painel para o Titulo da Tela inicial
        JPanel painelTitulo = new JPanel();
        painelTitulo.setOpaque(false);
        painelTitulo.setLayout(new FlowLayout());
        painelTitulo.setBorder(BorderFactory.createEmptyBorder(0, 0, 80, 0)); // Adiciona espaço no topo
        ShadowLabel titulo = new ShadowLabel("Snake Game", JLabel.CENTER);
        titulo.setFont(loadFont("/resources/fonts/pricedown.ttf", 48)); // Aumenta o tamanho do titulo e troca a fonte
        titulo.setForeground(Color.white);
        painelTitulo.add(titulo, BorderLayout.NORTH);
        add(painelTitulo, BorderLayout.NORTH);

        ///////////////////////////////////////////////////////////////////////////////////////////////////////////

        ////////////////////////////////////////// Painel Centro /////////////////////////////////////////////////

        // Cria painel no centro com botões
        JPanel botoesCentro = new JPanel();
        botoesCentro.setOpaque(false); // Tornar o painel transparente
        botoesCentro.setLayout(new GridBagLayout());

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(0, 0, 5, 0); // Espaçamento entre os botões

        // Cria os botões
        JButton easyButton = new JButton("Easy");
        easyButton.setFont(loadFont("/resources/fonts/pricedown.ttf", 20));
        JButton mediumButton = new JButton("Medium");
        mediumButton.setFont(loadFont("/resources/fonts/pricedown.ttf", 20));
        JButton hardButton = new JButton("Hard");
        hardButton.setFont(loadFont("/resources/fonts/pricedown.ttf", 20));
        JButton sairButton = new JButton("Exit");
        sairButton.setFont(loadFont("/resources/fonts/pricedown.ttf", 20));

        // Define o tamanho dos Botões
        Dimension tamanhoBotao = new Dimension(150, 30);
        easyButton.setPreferredSize(tamanhoBotao);
        sairButton.setPreferredSize(tamanhoBotao);
        mediumButton.setPreferredSize(tamanhoBotao);
        hardButton.setPreferredSize(tamanhoBotao);

        // Adiciona os botões ao painel
        gbc.gridx = 0; // Coluna 0
        gbc.gridy = 0; // Linha 0
        botoesCentro.add(easyButton, gbc);

        gbc.gridy = 1; // Linha 1
        botoesCentro.add(mediumButton, gbc);

        gbc.gridy = 2; // Linha 2
        botoesCentro.add(hardButton, gbc);

        gbc.gridy = 3; // Linha 3
        botoesCentro.add(sairButton, gbc);

        // Adiciona o Painel de Botões no centro da tela
        add(botoesCentro, BorderLayout.CENTER);

        ///////////////////////////////////////////////////////////////////////////////////////////////////////////

        //////////////////////////////////////// Funções dos Botões///////////////////////////////////////////////

        // ActionListener para o "botão 1"
        easyButton.addActionListener(e -> {
            sound.stopMusic(); // Para a Musica
            new Easy();
            dispose();
        });

        mediumButton.addActionListener(e -> {
            sound.stopMusic(); // Para a Musica
            new Medium();
            dispose();
        });

        hardButton.addActionListener(e -> {
            sound.stopMusic(); // Para a Musica
            new Hard();
            dispose();
        });

        // ActionListener para o Botão "Sair"
        sairButton.addActionListener(e -> {
            System.exit(0); // Encerra o programa
        });

        /////////////////////////////////////////////////////////////////////////////////////////////////////////// 
        setVisible(true); // Torna a tela visível

    }

    //////////////////////////////////////////// Métodos ///////////////////////////////////////////////////////

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
    ///////////////////////////////////////////////////////////////////////////////////////////////////////////

}
