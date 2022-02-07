package cynofsin.minesweeper;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JPanel;

public class MinefieldPanel extends JPanel {
    class Cell extends JPanel implements MouseListener {
        
        public Cell() {
            super();
            this.setOpaque(false);
        }

        @Override
        public void mouseClicked(MouseEvent e) {
            // TODO Auto-generated method stub
        }

        @Override
        public void mousePressed(MouseEvent e) {}

        @Override
        public void mouseReleased(MouseEvent e) {}

        @Override
        public void mouseEntered(MouseEvent e) {
            // TODO Auto-generated method stub
        }

        @Override
        public void mouseExited(MouseEvent e) {
            // TODO Auto-generated method stub
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
        }

    }

    private static final int WIDTH = 10;
    private static final int HEIGHT = 12;

    public MinefieldPanel() {
        super();
        this.setLayout(new GridLayout(6, 6, HEIGHT, WIDTH));
        for (int i = 0; i < WIDTH * HEIGHT; i++) {
            this.add(new Cell());
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        g.setColor(new Color(200, 200, 200));
        g.fillRect(0, 0, this.getSize().width, this.getSize().height);
        drawSpacedLines(g, Color.DARK_GRAY, WIDTH, HEIGHT, 2);
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
