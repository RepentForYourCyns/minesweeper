package cynofsin.minesweeper;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;

public class Window {
    private JFrame window;

    public Window()
    {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                makeGUI();
            }
        });
    }

    private void makeGUI() {
        window = new JFrame("Minesweeper");
        window.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        makeMenuBar(window);
        Container contentPane = window.getContentPane();
        contentPane.setLayout(new BorderLayout(6, 6));
        JPanel minefieldPanel = new JPanel();
        contentPane.add(minefieldPanel, BorderLayout.CENTER);
        
        window.setMinimumSize(new Dimension(400, 600));
        window.setVisible(true);
    }

    private void makeMenuBar(JFrame window) {
        JMenu fileMenu = new JMenu("File");
        JMenuBar menubar = new JMenuBar();
        menubar.add(fileMenu);
        window.setJMenuBar(menubar);
        //TODO: Add reset and exit options to file submenu
    }
}
