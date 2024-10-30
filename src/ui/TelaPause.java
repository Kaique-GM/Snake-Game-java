package ui;

import java.awt.*;
import javax.swing.*;

public class TelaPause extends JFrame {
    private int larguraTela, alturaTela;

    public TelaPause(Game game) {

        // Tamanho da tela
        larguraTela = 200;
        alturaTela = 200;

        // Configurações
        setTitle("Pause");
        setSize(alturaTela, larguraTela);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null); // Centraliza
        setLayout(new BorderLayout());

        /////////////////////////////////////////// Titulo ///////////////////////////////////////////////////////

        // Cria e adiciona um painel para o Titulo da Tela inicial
        JPanel painelTitulo = new JPanel();
        painelTitulo.setLayout(new FlowLayout());
        painelTitulo.add(Box.createVerticalStrut(80), BorderLayout.NORTH); // Adiciona espaço
        JLabel titulo = new JLabel("Jogo Pausado!", JLabel.CENTER);
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
        JButton continueButton = new JButton("Continuar");

        // Define o tamanho dos Botões
        Dimension tamanhoBotao = new Dimension(150, 30);
        continueButton.setPreferredSize(tamanhoBotao);

        // Adiciona os botões ao painel
        gbc.gridx = 0; // Coluna 0
        gbc.gridy = 0; // Linha 0
        botoesCentro.add(continueButton, gbc);

        // Adiciona o Painel de Botões no centro da tela
        add(botoesCentro, BorderLayout.CENTER);

        ///////////////////////////////////////////////////////////////////////////////////////////////////////////

        //////////////////////////////////////// Funções dos Botões///////////////////////////////////////////////

        // ActionListener para o "botão 1"
        continueButton.addActionListener(e -> {
            game.desPausar(); // Chama o método para Despausar de qualquer modo de Jogo
            dispose();
        });

        /////////////////////////////////////////////////////////////////////////////////////////////////////////// 

        setVisible(true); // Torna a tela visível
    }
}
