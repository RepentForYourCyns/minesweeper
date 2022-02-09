package cynofsin.minesweeper;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Random;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.BevelBorder;

public class MinefieldPanel extends JPanel {
    class HiddenCell extends JButton implements MouseListener {
        private int x, y;

        public HiddenCell(int x, int y) {
            this.x = x;
            this.y = y;
            this.setBorder(new BevelBorder(BevelBorder.RAISED));
            this.addMouseListener(this);
        }

        @Override
        public void mouseClicked(MouseEvent e) {
            revealCell(this);
        }

        @Override
        public void mousePressed(MouseEvent e) {
            this.setBorder(new BevelBorder(BevelBorder.LOWERED));
        }

        @Override
        public void mouseReleased(MouseEvent e) {
            this.setBorder(new BevelBorder(BevelBorder.RAISED));
        }

        @Override
        public void mouseEntered(MouseEvent e) {}

        @Override
        public void mouseExited(MouseEvent e) {}
    }

    class RevealedCell extends JPanel {
        private int x, y;
        boolean mine = false;

        public RevealedCell(int x, int y) {
            this.x = x;
            this.y = y;
            this.mine = session.isMine(this.x, this.y);
            if (!mine) {
                String hint = session.getHint(x, y).toString();
                if (!hint.equals("0")) {
                    this.add(new JLabel(hint));
                }
            }
        }

        @Override
        protected void paintComponent(Graphics g) {
            if (!mine) {
                super.paintComponent(g);
            }
            else {
                g.setColor(Color.red);
                g.fillRect(0, 0, getWidth(), getHeight());
            }
        }
    }

    private static final int WIDTH = 30;
    private static final int HEIGHT = 40;
    private GameSession session = new GameSession(
            new Minefield(WIDTH, HEIGHT, new Random()));

    public MinefieldPanel() {
        super();
        this.setLayout(new GridLayout(HEIGHT, WIDTH));
        for (int j = 0; j < HEIGHT; j++) {
            for (int i = 0; i < WIDTH; i++) {
                this.add(new HiddenCell(i, j));
            }
        }
    }

    private void revealCell(HiddenCell cell) {
        int x = cell.x;
        int y = cell.y;
        session.reveal(x, y);
        this.remove((y * WIDTH) + x);
        this.add(new RevealedCell(x, y), (y * WIDTH) + x);
        this.revalidate();
        this.repaint();
    }

    @Override
    public Dimension getPreferredSize() {
        // Ratio
        int width = getParent().getWidth();
        int height = getParent().getHeight();
        double desiredRatio = (float) WIDTH / HEIGHT;
        double actualRatio = (float) width / height;
        if (actualRatio < desiredRatio) {
            height = (int) (width / desiredRatio);
        }
        else {
            width = (int) (height * desiredRatio);
        }

        this.repaint();
        return new Dimension(width, height);
    }

    public int getWidthInCells() {
        return WIDTH;
    }

    public int getHeightInCells() {
        return HEIGHT;
    }
}
