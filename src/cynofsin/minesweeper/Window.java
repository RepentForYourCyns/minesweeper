package cynofsin.minesweeper;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.RenderingHints;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.WindowConstants;

public class Window {
    public Window() {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                makeGUI();
            }
        });
    }

    private void makeGUI() {
        final JFrame window = new JFrame("Minesweeper");
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

        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        }
        catch (ClassNotFoundException | InstantiationException
                | IllegalAccessException | UnsupportedLookAndFeelException e) {
            e.printStackTrace();
        }

        window.pack();
        Dimension minSize = new Dimension((minefieldPanel.getWidthInCells() + 4) * 20, (minefieldPanel.getHeightInCells() + 4) * 20);
        window.setMinimumSize(minSize);
        window.setSize(minSize);
        window.setVisible(true);

        Graphics2D graphics = (Graphics2D) contentPane.getGraphics();
        graphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        graphics.setRenderingHint(RenderingHints.KEY_RESOLUTION_VARIANT, RenderingHints.VALUE_RESOLUTION_VARIANT_SIZE_FIT);
    }

    private void makeMenuBar(JFrame window) {
        JMenu fileMenu = new JMenu("File");
        JMenuBar menubar = new JMenuBar();
        menubar.add(fileMenu);
        window.setJMenuBar(menubar);

        // TODO: Add reset and exit options to file submenu
    }
}
