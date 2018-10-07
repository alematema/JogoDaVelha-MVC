package br.edu.undra.view;

import br.edu.undra.interfaces.MVC.View;
import br.edu.undra.modelo.versoes.AbstracaoVersaoJogoVelha;
import br.edu.undra.modelo.versoes.VersaoComputadorVersusComputadorImpl;
import br.edu.undra.modelo.versoes.VersaoHumanoVersusComputadorImpl;
import br.edu.undra.modelo.versoes.VersaoHumanoVersusHumanoImpl;
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
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.UnsupportedLookAndFeelException;

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
    JMenuItem computadorVsComputadorMenuItem = new JMenuItem("Jogar Computador versus computador");
    JMenuItem humanoVsComputadorMenuItem = new JMenuItem("Jogar Humano versus computador");
    JMenuItem humanoVsHumanoMenuItem = new JMenuItem("Jogar Humano versus humano");
    JMenuItem exitMenuItem = new JMenuItem("Sair");

    Font menuFont = new Font("Ubuntu", Font.PLAIN, 15);

    JLabel tituloFake = new JLabel(getTitle());

    //as tres versoes do jogo da velhas
    AbstracaoVersaoJogoVelha compVxComp = new VersaoComputadorVersusComputadorImpl();
    AbstracaoVersaoJogoVelha humanoVxComp = new VersaoHumanoVersusComputadorImpl();
    AbstracaoVersaoJogoVelha humanoVxHumano = new VersaoHumanoVersusHumanoImpl();

    //a comunicacao com usuario
    private JButton mensagem;

    static public AtomicInteger numeroDeInstancias = new AtomicInteger(0);

    private DisplayJogoVelha view;
    private int x;
    private int y;
    private int numeroDaInstancia;

    public static void main(String[] args) {
        new JogoVelhaWindow().configureAndShow();
    }

    public JogoVelhaWindow() throws HeadlessException {
        this.numeroDaInstancia = numeroDeInstancias.addAndGet(1);
    }

    public JogoVelhaWindow(View view) {
        this();
        this.view = (DisplayJogoVelha) view;
    }

    public void configureAndShow(String title) {
        setTitle(title);
        for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
            if ("GTK+".equals(info.getName())) {
                try {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                } catch (ClassNotFoundException ex) {
                    Logger.getLogger(JogoVelhaWindow.class.getName()).log(Level.SEVERE, null, ex);
                } catch (InstantiationException ex) {
                    Logger.getLogger(JogoVelhaWindow.class.getName()).log(Level.SEVERE, null, ex);
                } catch (IllegalAccessException ex) {
                    Logger.getLogger(JogoVelhaWindow.class.getName()).log(Level.SEVERE, null, ex);
                } catch (UnsupportedLookAndFeelException ex) {
                    Logger.getLogger(JogoVelhaWindow.class.getName()).log(Level.SEVERE, null, ex);
                }
                break;
            }
        }

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

    }

    public void configureAndShow() {
        configureAndShow(getClass().getSimpleName() + " " + this.numeroDaInstancia + " App");
    }

    private void placeComponentsAtFrame() {

        getContentPane().setLayout(new GridBagLayout());

        // build menu structure
        setJMenuBar(mainMenuBar);
        mainMenuBar.add(jogosMenu);
        mainMenuBar.add(tituloMenu);

        tituloMenu.setText(getTitle());
        tituloMenu.setFont(new Font("Ubuntu", Font.PLAIN, 15));

        jogosMenu.setMnemonic(KeyEvent.VK_A);
        jogosMenu.setFont(new Font("Ubuntu", Font.BOLD, 16));

        jogosMenu.setIcon(new javax.swing.ImageIcon(getClass().getResource("/configuration.png")));

        jogosMenu.add(computadorVsComputadorMenuItem);
        computadorVsComputadorMenuItem.setIcon(new javax.swing.ImageIcon(getClass().getResource("/abrir.png")));
        computadorVsComputadorMenuItem.setFont(menuFont);
        jogosMenu.add(humanoVsComputadorMenuItem);
        humanoVsComputadorMenuItem.setIcon(new javax.swing.ImageIcon(getClass().getResource("/abrir.png")));
        humanoVsComputadorMenuItem.setFont(menuFont);
        jogosMenu.add(humanoVsHumanoMenuItem);
        humanoVsHumanoMenuItem.setIcon(new javax.swing.ImageIcon(getClass().getResource("/abrir.png")));
        humanoVsHumanoMenuItem.setFont(menuFont);
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
        System.out.println(getTitle());
        tituloFake.setText(getTitle());
        // getContentPane().add(tituloFake, gridConstraints);

        gridConstraints.gridx = 0;
        gridConstraints.gridy = 2;
        getContentPane().add(view, gridConstraints);

        gridConstraints.gridx = 0;
        gridConstraints.gridy = 3;

        mensagem = view.getMensagem();

        mensagem.setFont(new Font("Ubuntu", Font.BOLD, 16));
        mensagem.setPreferredSize(new Dimension((int) view.getPreferredSize().getWidth(), (int) view.getPreferredSize().getHeight() / 8));

        getContentPane().add(mensagem, gridConstraints);

    }

    private void computadorVsComputadorMenuItemActionPerformed(ActionEvent e) {

        Object[] args = new Object[1];
        args[0] = compVxComp;
        view.getController().updateModel("setAbstracaoVersaoJogoVelha", args);
        tituloMenu.setText(compVxComp.getVersao());
        tituloMenu.setFont(new Font("Ubuntu", Font.PLAIN, 13));

    }

    private void humanoVsComputadorMenuItemActionPerformed(ActionEvent e) {

        Object[] args = new Object[1];
        args[0] = humanoVxComp;
        view.getController().updateModel("setAbstracaoVersaoJogoVelha", args);
        tituloMenu.setText(humanoVxComp.getVersao());
        tituloMenu.setFont(new Font("Ubuntu", Font.PLAIN, 13));

    }

    private void humanoVsHumanoMenuItemActionPerformed(ActionEvent e) {

        Object[] args = new Object[1];
        args[0] = humanoVxHumano;
        view.getController().updateModel("setAbstracaoVersaoJogoVelha", args);
        tituloMenu.setText(humanoVxHumano.getVersao());
        tituloMenu.setFont(new Font("Ubuntu", Font.PLAIN, 15));

    }

    private void exitMenuItemActionPerformed(ActionEvent e) {
        System.exit(JFrame.NORMAL);
    }

    private void exitForm(WindowEvent evt) {
        System.exit(JFrame.NORMAL);
    }

}
