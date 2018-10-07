package br.edu.undra.view;

import br.edu.undra.MVC.JogoVelhaView;
import br.edu.undra.interfaces.MVC.View;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.HeadlessException;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFrame;
import javax.swing.UnsupportedLookAndFeelException;

/**
 *
 * @author alexandre
 */
public class JogoVelhaWindow extends JFrame{
    
    private DisplayJogoVelha view;
    
    public static void main(String[] args) {
        new JogoVelhaWindow().configureAndShow();
    }

    public JogoVelhaWindow() throws HeadlessException {
    }

    
    public JogoVelhaWindow(View view) {
        this.view = (DisplayJogoVelha) view;
    }
    
    public void configureAndShow() {

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

        setTitle(getClass().getSimpleName() + " App");

        getContentPane().setBackground(new Color(192, 192, 255));

        setResizable(false);

        //sets frame's size to 1/4 screen area
        setSize(new Dimension(Toolkit.getDefaultToolkit().getScreenSize().width / 2, Toolkit.getDefaultToolkit().getScreenSize().height / 2));

        //centralizes the frame
        setBounds((Toolkit.getDefaultToolkit().getScreenSize().width - getWidth()) / 2, (Toolkit.getDefaultToolkit().getScreenSize().height - getHeight()) / 2, getWidth(), getHeight());

        setUndecorated(false);

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

    private void placeComponentsAtFrame() {
        
        getContentPane().setLayout(new GridBagLayout());
        GridBagConstraints gridConstraints;
        getContentPane().add(view);
        
    }

    private void exitForm(WindowEvent evt) {
        System.exit(JFrame.NORMAL);
    }

    
}
