package cynofsin.minesweeper;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Container;
import java.awt.Dimension;

import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;

public class Window {
    private JFrame window;

    public Window() {
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

        contentPane.setLayout(new BorderLayout(3, 3));

        MinefieldPanel minefieldPanel = new MinefieldPanel();
        JPanel minefieldPanelContainer = new JPanel();
        minefieldPanelContainer.add(minefieldPanel);

        contentPane.add(minefieldPanelContainer, BorderLayout.CENTER);
        contentPane.add(new JPanel(), BorderLayout.NORTH);
        contentPane.add(new JPanel(), BorderLayout.EAST);
        contentPane.add(new JPanel(), BorderLayout.SOUTH);
        contentPane.add(new JPanel(), BorderLayout.WEST);

        window.pack();
        Dimension minSize = new Dimension((minefieldPanel.getWidthInCells() + 8) * 50, (minefieldPanel.getHeightInCells() + 8) * 50);
        window.setMinimumSize(minSize);
        window.setSize(minSize);
        window.setVisible(true);
    }

    private void makeMenuBar(JFrame window) {
        JMenu fileMenu = new JMenu("File");
        JMenuBar menubar = new JMenuBar();
        menubar.add(fileMenu);
        window.setJMenuBar(menubar);
        // TODO: Add reset and exit options to file submenu
    }
}
