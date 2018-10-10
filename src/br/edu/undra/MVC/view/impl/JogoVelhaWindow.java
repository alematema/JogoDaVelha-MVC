package br.edu.undra.MVC.view.impl;

import br.edu.undra.MVC.interfaces.View;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.HeadlessException;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.concurrent.atomic.AtomicInteger;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JSlider;
import javax.swing.event.ChangeEvent;

/**
 * O window do jogo da velha.
 *
 * @author alexandre
 */
public class JogoVelhaWindow extends JFrame {

    // menu structure
    JMenuBar mainMenuBar = new JMenuBar();
    JMenu jogosMenu = new JMenu("JOGOS");
    JMenu tituloMenu = new JMenu(getTitle());
    JCheckBox computadorVsComputadorMenuItem = new JCheckBox("Jogar Computador versus computador");
    JCheckBox humanoVsComputadorMenuItem = new JCheckBox("Jogar Humano versus computador");
    JCheckBox humanoVsHumanoMenuItem = new JCheckBox("Jogar Humano versus humano");
    //separacao de menu de configuracoes
    JCheckBox nivelFacilMenuItem = new JCheckBox("Fácil De Vencer O Computador", true);
    JCheckBox nivelDificilMenuItem = new JCheckBox("Difícil De Vencer O Computador", false);
    JCheckBox velocidadeBaixaMenuItem = new JCheckBox("Computador Joga Devagar", false);
    JCheckBox velocidadeMediaMenuItem = new JCheckBox("Computador Joga Não Tão Rápido", true);
    JCheckBox velocidadeAltaMenuItem = new JCheckBox("Computador Joga Rapidíssimo", false);

    ButtonGroup versaoJogoGroup = new ButtonGroup();
    ButtonGroup nivelGroup = new ButtonGroup();
    ButtonGroup velocidadeGroup = new ButtonGroup();
    //fim menu configuracoes

    JMenuItem exitMenuItem = new JMenuItem("Sair");

    Font menuFont = new Font("Ubuntu", Font.PLAIN, 15);

    JLabel tituloFake = new JLabel(getTitle());

    //as tres versoes do jogo da velhas
    String compVSComp = "compVSComp";
    String humanoVSComp = "humanoVSComp";
    String humanoVSHumano = "humanoVSHumano";
    String versaoJogo;

    //as duas impleentacoes do calculador da proxima jogada
    String calculadorProximaJogadaSimples = "calculadorSimples";
    String calculadorProximaJogadaIA = "calculadorIA";

    String versaoCalculadorProximaJogada;

    private JCheckBox nivelFacilDificil = new JCheckBox("Nível Difícil");

    //a comunicacao com usuario
    private JButton mensagem;

    static public AtomicInteger numeroDeInstancias = new AtomicInteger(0);

    private DisplayJogoVelha view;
    private int x;
    private int y;
    private int numeroDaInstancia;

    private JSlider velocidadeCompVsCompJSlider = new JSlider(JSlider.HORIZONTAL);

    public JogoVelhaWindow() throws HeadlessException {
        this.numeroDaInstancia = numeroDeInstancias.addAndGet(1);
    }

    public JogoVelhaWindow(View view) {
        this();
        this.view = (DisplayJogoVelha) view;
        versaoJogo = "compVSComp";
        versaoCalculadorProximaJogada = calculadorProximaJogadaSimples;
        this.view.setWindow(this);

    }

    public void configureAndShow(String titulo) {
        setTitle(titulo);
//        for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
//            if ("GTK+".equals(info.getName())) {
//                try {
//                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
//                } catch (ClassNotFoundException ex) {
//                    Logger.getLogger(JogoVelhaWindow.class.getName()).log(Level.SEVERE, null, ex);
//                } catch (InstantiationException ex) {
//                    Logger.getLogger(JogoVelhaWindow.class.getName()).log(Level.SEVERE, null, ex);
//                } catch (IllegalAccessException ex) {
//                    Logger.getLogger(JogoVelhaWindow.class.getName()).log(Level.SEVERE, null, ex);
//                } catch (UnsupportedLookAndFeelException ex) {
//                    Logger.getLogger(JogoVelhaWindow.class.getName()).log(Level.SEVERE, null, ex);
//                }
//                break;
//            }
//        }

        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent evt) {
                exitForm(evt);
            }

        });

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        getContentPane().setBackground(new Color(192, 192, 255));

        setResizable(false);

        //sets frame's size to 1/4 screen area
        //setSize(new Dimension(Toolkit.getDefaultToolkit().getScreenSize().width / 2, Toolkit.getDefaultToolkit().getScreenSize().height / 2));
        //centralizes the frame
        // setBounds((Toolkit.getDefaultToolkit().getScreenSize().width - getWidth()) / 2, (Toolkit.getDefaultToolkit().getScreenSize().height - getHeight()) / 2, getWidth(), getHeight());
        setUndecorated(true);

        addMouseListener(new MouseAdapter() {//moves the frame to mouse released positions

            Point fromPosition = null;
            Point toPosition = null;

            @Override
            public void mousePressed(MouseEvent e) {
                super.mouseClicked(e);
                fromPosition = new Point(e.getXOnScreen(), e.getYOnScreen());
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                super.mouseReleased(e);
                toPosition = new Point(e.getXOnScreen(), e.getYOnScreen());
                setBounds(getBounds().x + (-fromPosition.x + toPosition.x), getBounds().y + (-fromPosition.y + toPosition.y), getWidth(), getHeight());
            }
        });

        placeComponentsAtFrame();

        pack();

        //centralizes the frame
        setBounds((Toolkit.getDefaultToolkit().getScreenSize().width - getWidth()) / 2, (Toolkit.getDefaultToolkit().getScreenSize().height - getHeight()) / 2, getWidth(), getHeight());

        setVisible(true);

        desabilitarMenuJogos();
        
    }

    private void placeComponentsAtFrame() {

        getContentPane().setLayout(new GridBagLayout());

        // build menu structure
        setJMenuBar(mainMenuBar);
        mainMenuBar.add(jogosMenu);
        mainMenuBar.add(tituloMenu);

        tituloMenu.setText(getTitle());
        tituloMenu.setFont(new Font("Ubuntu", Font.PLAIN, 15));
//        tituloMenu.setIcon(new javax.swing.ImageIcon(getClass().getResource("/rsz_1puzzle-512.png")));

        jogosMenu.setMnemonic(KeyEvent.VK_A);
        jogosMenu.setFont(new Font("Ubuntu", Font.BOLD, 16));

        jogosMenu.setIcon(new javax.swing.ImageIcon(getClass().getResource("/configuration.20.20.png")));

        jogosMenu.add(computadorVsComputadorMenuItem);
//        computadorVsComputadorMenuItem.setIcon(new javax.swing.ImageIcon(getClass().getResource("/abrir.png")));
        computadorVsComputadorMenuItem.setSelectedIcon(new javax.swing.ImageIcon(getClass().getResource("/switch-on-icon.png")));
        computadorVsComputadorMenuItem.setIcon(new javax.swing.ImageIcon(getClass().getResource("/switch-off-icon.png")));
        computadorVsComputadorMenuItem.setIconTextGap(0);
        computadorVsComputadorMenuItem.setFont(menuFont);
        jogosMenu.add(humanoVsComputadorMenuItem);
        //humanoVsComputadorMenuItem.setIcon(new javax.swing.ImageIcon(getClass().getResource("/abrir.png")));
        humanoVsComputadorMenuItem.setSelectedIcon(new javax.swing.ImageIcon(getClass().getResource("/switch-on-icon.png")));
        humanoVsComputadorMenuItem.setIcon(new javax.swing.ImageIcon(getClass().getResource("/switch-off-icon.png")));
        humanoVsComputadorMenuItem.setIconTextGap(0);
        humanoVsComputadorMenuItem.setFont(menuFont);
        jogosMenu.add(humanoVsHumanoMenuItem);
        //humanoVsHumanoMenuItem.setIcon(new javax.swing.ImageIcon(getClass().getResource("/abrir.png")));
        humanoVsHumanoMenuItem.setSelectedIcon(new javax.swing.ImageIcon(getClass().getResource("/switch-on-icon.png")));
        humanoVsHumanoMenuItem.setIcon(new javax.swing.ImageIcon(getClass().getResource("/switch-off-icon.png")));
        humanoVsHumanoMenuItem.setIconTextGap(0);
        humanoVsHumanoMenuItem.setFont(menuFont);
        versaoJogoGroup.add(computadorVsComputadorMenuItem);
        versaoJogoGroup.add(humanoVsComputadorMenuItem);
        versaoJogoGroup.add(humanoVsHumanoMenuItem);

        jogosMenu.addSeparator();
        jogosMenu.add(nivelFacilMenuItem);
        jogosMenu.add(nivelDificilMenuItem);
        nivelFacilMenuItem.setFont(menuFont);
        nivelDificilMenuItem.setFont(menuFont);
        nivelGroup.add(nivelFacilMenuItem);
        nivelGroup.add(nivelDificilMenuItem);
        nivelFacilMenuItem.addActionListener(this::nivelFacilMenuItemActionPerformed);
        nivelDificilMenuItem.addActionListener(this::nivelDificilMenuItemActionPerformed);
        jogosMenu.addSeparator();
        jogosMenu.add(velocidadeBaixaMenuItem);
        jogosMenu.add(velocidadeMediaMenuItem);
        jogosMenu.add(velocidadeAltaMenuItem);
        velocidadeBaixaMenuItem.setFont(menuFont);
        velocidadeMediaMenuItem.setFont(menuFont);
        velocidadeAltaMenuItem.setFont(menuFont);
        velocidadeGroup.add(velocidadeBaixaMenuItem);
        velocidadeGroup.add(velocidadeMediaMenuItem);
        velocidadeGroup.add(velocidadeAltaMenuItem);
        velocidadeBaixaMenuItem.addActionListener(this::velocidadeBaixaMenuItemActionPerformed);
        velocidadeMediaMenuItem.addActionListener(this::velocidadeMediaMenuItemActionPerformed);
        velocidadeAltaMenuItem.addActionListener(this::velocidadeAltaMenuItemActionPerformed);
        jogosMenu.addSeparator();
        exitMenuItem.setIcon(new javax.swing.ImageIcon(getClass().getResource("/closepreto.png")));
        jogosMenu.add(exitMenuItem);
        exitMenuItem.setFont(menuFont);
        computadorVsComputadorMenuItem.addActionListener(this::computadorVsComputadorMenuItemActionPerformed);
        humanoVsComputadorMenuItem.addActionListener(this::humanoVsComputadorMenuItemActionPerformed);
        humanoVsHumanoMenuItem.addActionListener(this::humanoVsHumanoMenuItemActionPerformed);
        exitMenuItem.addActionListener(this::exitMenuItemActionPerformed);

        GridBagConstraints gridConstraints = new GridBagConstraints();

        gridConstraints.gridx = 0;
        gridConstraints.gridy = 0;
        tituloFake.setText(getTitle());
        // getContentPane().add(tituloFake, gridConstraints);

        gridConstraints.gridx = 0;
        gridConstraints.gridy = 1;
        getContentPane().add(view, gridConstraints);

        gridConstraints.gridx = 0;
        gridConstraints.gridy = 2;

        mensagem = view.getMensagem();

        mensagem.setFont(new Font("Ubuntu", Font.BOLD, 16));
        mensagem.setPreferredSize(new Dimension((int) view.getPreferredSize().getWidth(), (int) view.getPreferredSize().getHeight() / 8));

        getContentPane().add(mensagem, gridConstraints);

    }

    public void setVersaoJogo(String versaoJogo) {
        this.versaoJogo = versaoJogo;
    }

    public void setVersaoCalculador(String versao) {
        this.versaoCalculadorProximaJogada = versao;
    }

    public void setUpItensMenuJogo() {
        //set item do menu com a versao do jogo correspondente 

        if (versaoJogo.equals("compVSComp")) {
            computadorVsComputadorMenuItem.setSelected(true);
        } else if (versaoJogo.equals("humanoVSComp")) {
            humanoVsComputadorMenuItem.setSelected(true);
        } else if (versaoJogo.equals("humanoVSHumano")) {
            humanoVsHumanoMenuItem.setSelected(true);
        }

        //set item do menu com a dificuldade do jogo correspondente 
        if (versaoCalculadorProximaJogada.equals("calculadorSimples")) {
            nivelFacilMenuItem.setSelected(true);
            nivelDificilMenuItem.setSelected(false);
        } else {
            nivelFacilMenuItem.setSelected(false);
            nivelDificilMenuItem.setSelected(true);
        }
    }

    private void computadorVsComputadorJSliderChangePerformed(ChangeEvent e) {

        Object[] args1 = {Integer.toString((Integer) ((JSlider) e.getSource()).getValue()) + "/" + velocidadeCompVsCompJSlider.getMaximum()};
        view.getController().updateView("setMensagem", args1);

        Object[] args = {((JSlider) e.getSource()).getValue()};
        view.getController().updateModel("setVelocity", args);

    }

    private void computadorVsComputadorMenuItemActionPerformed(ActionEvent e) {

        versaoJogo = "compVSComp";
        habilitarDesabilitarItensVelocidadeENivelMenuJogo();

        Object[] args = new Object[1];
        args[0] = "compVSComp";

        view.getController().updateModel("liberarJogada", null);
        view.getController().updateModel("setVersaoJogoVelha", args);

        //humanoVSHumano;humanoVSComp;
        //TODO usar REFLEXION para recuperar do MODEL o nome da versao
        tituloMenu.setText("Jogo da Velha : Computador X computador");
        tituloMenu.setFont(new Font("Ubuntu", Font.PLAIN, 13));
    }

    private void humanoVsComputadorMenuItemActionPerformed(ActionEvent e) {

        versaoJogo = humanoVSComp;
        habilitarDesabilitarItensVelocidadeENivelMenuJogo();

        Object[] args = new Object[1];
        args[0] = humanoVSComp;
        view.getController().updateModel("liberarJogada", null);
        view.getController().updateModel("setVersaoJogoVelha", args);

        //TODO usar REFLEXION para recuperar do MODEL o nome da versao
        tituloMenu.setText("Jogo da Velha : Humano X computador");
        tituloMenu.setFont(new Font("Ubuntu", Font.PLAIN, 13));

    }

    private void humanoVsHumanoMenuItemActionPerformed(ActionEvent e) {

        versaoJogo = humanoVSHumano;
        habilitarDesabilitarItensVelocidadeENivelMenuJogo();

        Object[] args = new Object[1];
        args[0] = humanoVSHumano;
        view.getController().updateModel("setVersaoJogoVelha", args);

        //TODO usar REFLEXION para recuperar do MODEL o nome da versao
        tituloMenu.setText("Jogo da Velha : Humano X humano");
        tituloMenu.setFont(new Font("Ubuntu", Font.PLAIN, 15));

    }

    private void nivelFacilMenuItemActionPerformed(ActionEvent e) {

        versaoCalculadorProximaJogada = calculadorProximaJogadaSimples;
        Object[] args = {versaoCalculadorProximaJogada};
        view.getController().updateModel("setCalculadorProximaJogada", args);

    }

    private void nivelDificilMenuItemActionPerformed(ActionEvent e) {

        versaoCalculadorProximaJogada = calculadorProximaJogadaIA;
        Object[] args = {versaoCalculadorProximaJogada};
        view.getController().updateModel("setCalculadorProximaJogada", args);

    }

    private void velocidadeBaixaMenuItemActionPerformed(ActionEvent e) {

        Object[] args1 = {"1/100"};
        view.getController().updateView("setMensagem", args1);

        Object[] args = {10};
        view.getController().updateModel("setVelocity", args);

    }

    private void velocidadeMediaMenuItemActionPerformed(ActionEvent e) {

        Object[] args1 = {"50/100"};
        view.getController().updateView("setMensagem", args1);

        Object[] args = {50};
        view.getController().updateModel("setVelocity", args);

    }

    private void velocidadeAltaMenuItemActionPerformed(ActionEvent e) {

        Object[] args1 = {"100/100"};
        view.getController().updateView("setMensagem", args1);

        Object[] args = {100};
        view.getController().updateModel("setVelocity", args);

    }

    private void exitMenuItemActionPerformed(ActionEvent e) {
        System.exit(JFrame.NORMAL);
    }

    private void exitForm(WindowEvent evt) {
        System.exit(JFrame.NORMAL);
    }

    public void habilitarDesabilitarItensVelocidadeENivelMenuJogo() {

        velocidadeBaixaMenuItem.setEnabled(false);
        velocidadeMediaMenuItem.setEnabled(false);
        velocidadeAltaMenuItem.setEnabled(false);

        nivelFacilMenuItem.setEnabled(false);
        nivelDificilMenuItem.setEnabled(false);

        if (versaoJogo.equals("compVSComp")) {

            velocidadeBaixaMenuItem.setEnabled(true);
            velocidadeMediaMenuItem.setEnabled(true);
            velocidadeAltaMenuItem.setEnabled(true);

            nivelFacilMenuItem.setEnabled(true);
            nivelDificilMenuItem.setEnabled(true);

        }

        if (versaoJogo.equals("humanoVSComp")) {

            nivelFacilMenuItem.setEnabled(true);
            nivelDificilMenuItem.setEnabled(true);

        }

    }

    void desabilitarMenuJogos() {

        humanoVsHumanoMenuItem.setEnabled(false);
        humanoVsComputadorMenuItem.setEnabled(false);
        computadorVsComputadorMenuItem.setEnabled(false);
        nivelFacilMenuItem.setEnabled(false);
        nivelDificilMenuItem.setEnabled(false);
        velocidadeBaixaMenuItem.setEnabled(false);
        velocidadeMediaMenuItem.setEnabled(false);
        velocidadeAltaMenuItem.setEnabled(false);
        
        exitMenuItem.setText("Configurando. Aguarde para obter opcoes... ");

    }

    void habilitarMenuJogos() {
        
        humanoVsHumanoMenuItem.setEnabled(true);
        humanoVsComputadorMenuItem.setEnabled(true);
        computadorVsComputadorMenuItem.setEnabled(true);
        nivelFacilMenuItem.setEnabled(true);
        nivelDificilMenuItem.setEnabled(true);
        velocidadeBaixaMenuItem.setEnabled(true);
        velocidadeMediaMenuItem.setEnabled(true);
        velocidadeAltaMenuItem.setEnabled(true);
        
        exitMenuItem.setText("Sair");
        
        setUpItensMenuJogo();
        habilitarDesabilitarItensVelocidadeENivelMenuJogo();
    }

}
