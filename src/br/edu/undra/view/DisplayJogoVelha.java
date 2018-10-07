package br.edu.undra.view;

import br.edu.undra.MVC.JogoVelhaView;
import br.edu.undra.interfaces.MVC.Controller;
import br.edu.undra.modelo.JogoDaVelha;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 * DisplayJogoVelha
 *
 * @author alexandre
 */
public class DisplayJogoVelha extends JPanel implements JogoVelhaView {

    
    
    //o controller
    private Controller controller;

    //os botoes do jogo
    private JButton um;
    private JButton dois;
    private JButton tres;
    private JButton quatro;
    private JButton cinco;
    private JButton seis;
    private JButton sete;
    private JButton oito;
    private JButton nove;
    private JButton clear;
    
    //a comunicacao com usuario
    private JButton mensagem = new JButton("Computador versus Computador");

    //dimensao do botao
    private final Dimension dimensaoBotao = new Dimension(100, 100);

    //a fonte do teclado
    private final Font font = new Font("Ubuntu", Font.BOLD, 50);

    //a dimensao do teclado
    private final Dimension dimension = new Dimension(500, 500);

    //a cor do fundo
    private final Color background = Color.WHITE;

    private Color corOndeVencer = Color.RED;
    
    private Color corNormal = Color.BLACK;

    private Font fontOndeVencer = new Font("Ubuntu", Font.BOLD, 80);

    //a cor de fundo dos botoes
    private final Color botaoBackground = Color.WHITE;

    public DisplayJogoVelha() {
        configure();
    }

    private void configure() {

        setLayout(new GridBagLayout());
        setBackground(background);
        //setPreferredSize(dimension);
        setSize(dimension);

        ActionListener posicaoClicada = (ActionEvent e) -> {

            try {

                //System.out.println("posicao " + ((JButton) e.getSource()).getName());
                //((JButton) e.getSource()).setForeground(Color.RED);
                //int posicao = Integer.parseInt((((JButton) e.getSource()).getName()));
                //if(posicao%2 == 0) set("0",posicao); else set("X", posicao);
                String[] args = new String[1];
                args[0] = "posicao " + ((JButton) e.getSource()).getName();

                controller.updateModel(null, args);

            } catch (Exception ex) {
                System.err.println("Algo excepcional ocorreu em TecladoManualUI.actionListener " + ex.getLocalizedMessage());
            }

        };

        GridBagConstraints gridConstraints = new GridBagConstraints();

        gridConstraints.gridx = 0;
        gridConstraints.gridy = 0;
        um = new JButton("");
        um.setFont(font);
        um.setPreferredSize(dimensaoBotao);
        um.setName("1");
        um.setBackground(botaoBackground);
        um.addActionListener(posicaoClicada);
        add(um, gridConstraints);

        gridConstraints.gridx = 1;
        gridConstraints.gridy = 0;
        dois = new JButton("");
        dois.setFont(font);
        dois.setPreferredSize(dimensaoBotao);
        dois.setName("2");
        dois.setBackground(botaoBackground);
        dois.addActionListener(posicaoClicada);
        add(dois, gridConstraints);

        gridConstraints.gridx = 2;
        gridConstraints.gridy = 0;
        tres = new JButton("");
        tres.setFont(font);
        tres.setPreferredSize(dimensaoBotao);
        tres.setName("3");
        tres.setBackground(botaoBackground);
        tres.addActionListener(posicaoClicada);
        add(tres, gridConstraints);

        gridConstraints.gridx = 0;
        gridConstraints.gridy = 1;
        quatro = new JButton("");
        quatro.setFont(font);
        quatro.setPreferredSize(dimensaoBotao);
        quatro.setName("4");
        quatro.setBackground(botaoBackground);
        quatro.addActionListener(posicaoClicada);
        add(quatro, gridConstraints);

        gridConstraints.gridx = 1;
        gridConstraints.gridy = 1;
        cinco = new JButton("");
        cinco.setFont(font);
        cinco.setPreferredSize(dimensaoBotao);
        cinco.setName("5");
        cinco.setBackground(botaoBackground);
        cinco.addActionListener(posicaoClicada);
        add(cinco, gridConstraints);

        gridConstraints.gridx = 2;
        gridConstraints.gridy = 1;
        seis = new JButton("");
        seis.setFont(font);
        seis.setPreferredSize(dimensaoBotao);
        seis.setName("6");
        seis.setBackground(botaoBackground);
        seis.addActionListener(posicaoClicada);
        add(seis, gridConstraints);

        gridConstraints.gridx = 0;
        gridConstraints.gridy = 2;
        sete = new JButton("");
        sete.setFont(font);
        sete.setPreferredSize(dimensaoBotao);
        sete.setName("7");
        sete.setBackground(botaoBackground);
        sete.addActionListener(posicaoClicada);
        add(sete, gridConstraints);

        gridConstraints.gridx = 1;
        gridConstraints.gridy = 2;
        oito = new JButton("");
        oito.setFont(font);
        oito.setPreferredSize(dimensaoBotao);
        oito.setName("8");
        oito.setBackground(botaoBackground);
        oito.addActionListener(posicaoClicada);
        add(oito, gridConstraints);

        gridConstraints.gridx = 2;
        gridConstraints.gridy = 2;
        nove = new JButton("");
        nove.setFont(font);
        nove.setPreferredSize(dimensaoBotao);
        nove.setName("9");
        nove.setBackground(botaoBackground);
        nove.addActionListener(posicaoClicada);
        add(nove, gridConstraints);
        
       
//        gridConstraints.gridx = 6;
//        gridConstraints.gridy = 8;
//        clear = new JButton("C");
//        clear.setFont(font);
//        clear.setPreferredSize(new Dimension(dimensaoBotao.width*3,dimensaoBotao.height));
//        clear.setName("C");
//        clear.addActionListener((ActionEvent e) -> {
//            valorAsString = "";
//            out.setValue(parseFloat(valorAsString));
//        });
//        add(clear, gridConstraints);
    }

    @Override
    public void set(String value, int posicao) {

        switch (Integer.parseInt(value)) {

            case 0:
                value = "";
                break;
            case 1:
                value = "X";
                break;
            case 2:
                value = "0";
                break;
            case 3:
                value = "X";
                break;
            case 4:
                value = "0";
                break;
            case 5:
                value = "X";
                break;
            case 6:
                value = "0";
                break;
            case 7:
                value = "X";
                break;
            case 8:
                value = "0";
                break;
            case 9:
                value = "X";
                break;

        }

        switch (posicao) {

            case 1:
                um.setText(value);
                break;
            case 2:
                dois.setText(value);
                break;
            case 3:
                tres.setText(value);
                break;
            case 4:
                quatro.setText(value);
                break;
            case 5:
                cinco.setText(value);
                break;
            case 6:
                seis.setText(value);
                break;
            case 7:
                sete.setText(value);
                break;
            case 8:
                oito.setText(value);
                break;
            case 9:
                nove.setText(value);
                break;

        }

    }

    public JButton getMensagem() {
        return mensagem;
    }
    

    public JButton getClearButton() {
        return clear;
    }

    @Override
    public void setController(Controller controller) {
        this.controller = controller;
    }

    @Override
    public void marcarOndeVenceu(List<Object> posicoes) {
        for (Object pos : posicoes) {

            switch ((Integer) pos) {
                case 1:
                    um.setForeground(corOndeVencer);
                    um.setFont(fontOndeVencer);
                    break;
                case 2:
                    dois.setForeground(corOndeVencer);
                    dois.setFont(fontOndeVencer);
                    break;
                case 3:
                    tres.setForeground(corOndeVencer);
                    tres.setFont(fontOndeVencer);
                    break;
                case 4:
                    quatro.setForeground(corOndeVencer);
                    quatro.setFont(fontOndeVencer);
                    break;
                case 5:
                    cinco.setForeground(corOndeVencer);
                    cinco.setFont(fontOndeVencer);
                    break;
                case 6:
                    seis.setForeground(corOndeVencer);
                    seis.setFont(fontOndeVencer);
                    break;
                case 7:
                    sete.setForeground(corOndeVencer);
                    sete.setFont(fontOndeVencer);
                    break;
                case 8:
                    oito.setForeground(corOndeVencer);
                    oito.setFont(fontOndeVencer);
                    break;
                case 9:
                    nove.setForeground(corOndeVencer);
                    nove.setFont(fontOndeVencer);
                    break;

            }

        }
    }

    @Override
    public void reconfigurar() {

        String value = "";

        um.setText(value);
        dois.setText(value);
        tres.setText(value);
        quatro.setText(value);
        cinco.setText(value);
        seis.setText(value);
        sete.setText(value);
        oito.setText(value);
        nove.setText(value);

        um.setForeground(corNormal);
        um.setFont(font);
        dois.setForeground(corNormal);
        dois.setFont(font);
        tres.setForeground(corNormal);
        tres.setFont(font);
        quatro.setForeground(corNormal);
        quatro.setFont(font);
        cinco.setForeground(corNormal);
        cinco.setFont(font);
        seis.setForeground(corNormal);
        seis.setFont(font);
        sete.setForeground(corNormal);
        sete.setFont(font);
        oito.setForeground(corNormal);
        oito.setFont(font);
        nove.setForeground(corNormal);
        nove.setFont(font);

    }

    @Override
    public void setMensagem(String mensagem) {
        this.mensagem.setText(mensagem);
    }

}
