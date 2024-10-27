package ui;

import java.awt.*;
import javax.swing.*;

public class TelaInicial extends JFrame {

    private int larguraTabuleiro, alturaTabuleiro;

    public TelaInicial() {
        // Tamanho da tela
        larguraTabuleiro = alturaTabuleiro = 400;

        // Configurações
        setTitle("Tela Inicial");
        setSize(alturaTabuleiro, larguraTabuleiro + 30);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null); // Centraliza
        setLayout(new BorderLayout());

        /////////////////////////////////////////// Titulo ///////////////////////////////////////////////////////

        // Cria e adiciona um painel para o Titulo da Tela inicial
        JPanel painelTitulo = new JPanel();
        painelTitulo.setLayout(new FlowLayout());
        painelTitulo.add(Box.createVerticalStrut(100), BorderLayout.NORTH); // Adiciona espaço
        JLabel titulo = new JLabel("Bem-vindo ao Jogo da Cobrinha!", JLabel.CENTER);
        titulo.setFont(new Font("Arial", Font.BOLD, 15)); // Aumenta o tamanho do titulo e troca a fonte
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
        JButton Button1 = new JButton("Iniciar Jogo"); // alterar nome
        JButton sairButton = new JButton("Sair");

        // Define o tamanho dos Botões
        Dimension tamanhoBotao = new Dimension(150, 30);
        Button1.setPreferredSize(tamanhoBotao);
        sairButton.setPreferredSize(tamanhoBotao);

        // Adiciona os botões ao painel
        gbc.gridx = 0; // Coluna 0
        gbc.gridy = 0; // Linha 0
        botoesCentro.add(Button1, gbc);

        gbc.gridy = 1; // Linha 1
        botoesCentro.add(sairButton, gbc);

        // Adiciona o Painel de Botões no centro da tela
        add(botoesCentro, BorderLayout.CENTER);

        ///////////////////////////////////////////////////////////////////////////////////////////////////////////

        //////////////////////////////////////// Funções dos Botões///////////////////////////////////////////////
        // ActionListener para o "botão 1"
        Button1.addActionListener(e -> {
            new Tabuleiro();
            dispose();
        });

        // ActionListener para o Botão "Sair"
        sairButton.addActionListener(e -> {
            System.exit(0); // Encerra o programa
        });

        setVisible(true); // Torna a tela visível
    }

        /////////////////////////////////////////////////////////////////////////////////////////////////////////// 
}
