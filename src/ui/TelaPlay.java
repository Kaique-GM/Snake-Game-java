package ui;

import java.awt.*;
import javax.swing.*;
import java.io.File;
import java.io.IOException;

public class TelaPlay extends JFrame {

    private int larguraTabuleiro, alturaTabuleiro;

    public TelaPlay(Game game) {
        // Tamanho da tela
        larguraTabuleiro = 200;
        alturaTabuleiro = 300;

        // Configurações
        setTitle("");
        setSize(larguraTabuleiro, alturaTabuleiro);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null); // Centraliza
        setLayout(new BorderLayout());

        /////////////////////////////////////////// Titulo ///////////////////////////////////////////////////////

        // Cria e adiciona um painel para o Titulo da Tela inicial
        JPanel painelTitulo = new JPanel();
        painelTitulo.setLayout(new FlowLayout());
        painelTitulo.add(Box.createVerticalStrut(100), BorderLayout.NORTH); // Adiciona espaço
        JLabel titulo = new JLabel("Ready?", JLabel.CENTER);
        titulo.setFont(loadFont("resources/fonts/pricedown.ttf", 48)); // Aumenta o tamanho do titulo e troca a fonte
        painelTitulo.add(titulo, BorderLayout.NORTH);
        add(painelTitulo, BorderLayout.NORTH);

        ///////////////////////////////////////////////////////////////////////////////////////////////////////////

        ////////////////////////////////////////// Painel Centro /////////////////////////////////////////////////

        // Cria painel no centro com botões
        JPanel botoesCentro = new JPanel();
        botoesCentro.setLayout(new GridBagLayout());

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(0, 0, 5, 0); // Espaçamento entre os botões

        // Cria os botões
        JButton playButton = new JButton("Play");
        playButton.setFont(loadFont("resources/fonts/pricedown.ttf", 20));
        JButton homeButton = new JButton("Home");
        homeButton.setFont(loadFont("resources/fonts/pricedown.ttf", 20));
        JButton sairButton = new JButton("Exit");
        sairButton.setFont(loadFont("resources/fonts/pricedown.ttf", 20));

        // Define o tamanho dos Botões
        Dimension tamanhoBotao = new Dimension(150, 30);
        playButton.setPreferredSize(tamanhoBotao);
        sairButton.setPreferredSize(tamanhoBotao);
        homeButton.setPreferredSize(tamanhoBotao);

        // Adiciona os botões ao painel
        gbc.gridx = 0; // Coluna 0
        gbc.gridy = 0; // Linha 0
        botoesCentro.add(playButton, gbc);

        gbc.gridy = 1; // Linha 1
        botoesCentro.add(homeButton, gbc);

        gbc.gridy = 3; // Linha 3
        botoesCentro.add(sairButton, gbc);

        // Adiciona o Painel de Botões no centro da tela
        add(botoesCentro, BorderLayout.CENTER);

        ///////////////////////////////////////////////////////////////////////////////////////////////////////////

        //////////////////////////////////////// Funções dos Botões///////////////////////////////////////////////

        // ActionListener para o "botão 1"
        playButton.addActionListener(e -> {
            game.Play();
            dispose();
        });

        homeButton.addActionListener(e -> {
            game.Home();
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

    // Método para ler a fonte
    private static Font loadFont(String path, float size) {
        try {
            Font font = Font.createFont(Font.TRUETYPE_FONT, new File(path));
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
