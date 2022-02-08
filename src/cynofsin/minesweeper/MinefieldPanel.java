package cynofsin.minesweeper;

import java.awt.*;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.border.BevelBorder;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Random;

public class MinefieldPanel extends JPanel {
    class Cell extends JButton implements MouseListener {
        int x, y;

        public Cell(int x, int y) {
            this.x = x;
            this.y = y;
            this.setBorder(new BevelBorder(BevelBorder.RAISED));
            this.addMouseListener(this);
        }

        @Override
        protected void paintComponent(Graphics g) {
            this.setVisible(!session.isRevealed(x, y));
            super.paintComponent(g);
        }

        @Override
        public void mouseClicked(MouseEvent e) {
            session.reveal(x, y);
        }

        @Override
        public void mousePressed(MouseEvent e) {
            this.setBorder(new BevelBorder(BevelBorder.LOWERED));
            // this.repaint();
        }

        @Override
        public void mouseReleased(MouseEvent e) {
            this.setBorder(new BevelBorder(BevelBorder.RAISED));
            // this.repaint();
        }

        @Override
        public void mouseEntered(MouseEvent e) {
        }

        @Override
        public void mouseExited(MouseEvent e) {}
    }

    private static final int WIDTH = 30;
    private static final int HEIGHT = 40;
    private GameSession session = new GameSession(new Minefield(WIDTH, HEIGHT, new Random()));

    public MinefieldPanel() {
        super();
        this.setLayout(new GridLayout(HEIGHT, WIDTH));
        for (int i = 0; i < WIDTH; i++) {
            for (int j = 0; j < HEIGHT; j++) {
                this.add(new Cell(i, j));
            }
        }
    }

    @Override
    public Dimension getPreferredSize() {
        // Ratio
        int width = getParent().getWidth();
        int height = getParent().getHeight();
        double desiredRatio = (float) WIDTH / HEIGHT;
        double actualRatio = (float) width / height;
        if(actualRatio < desiredRatio) {
            height = (int) (width / desiredRatio);
        }
        else {
            width = (int) (height * desiredRatio);
        }

        this.repaint();
        return new Dimension(width, height);
    }

    private void drawSpacedLines(Graphics g, Color c, int spacesX, int spacesY, int lineThickness) {
        Color before = g.getColor();
        g.setColor(c);
        int width = this.getWidth();
        int height = this.getHeight();
        for (int i = 0; i < spacesX; i++) {
            float offset = (((float) (width - (lineThickness * (spacesX - 1))))
                    / spacesX);
            g.fillRect((int) ((offset * (i + 1)) + (lineThickness * i)), 0,
                    lineThickness, height);
        }
        for (int i = 0; i < spacesY; i++) {
            float offset = (((float) (height - (lineThickness * (spacesY - 1))))
                    / spacesY);
            g.fillRect(0, (int) ((offset * (i + 1)) + (lineThickness * i)),
                    width, lineThickness);
        }
        g.setColor(before);
    }

    public int getWidthInCells() {
        return WIDTH;
    }

    public int getHeightInCells() {
        return HEIGHT;
    }
}
