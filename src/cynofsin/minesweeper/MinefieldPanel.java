package cynofsin.minesweeper;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Random;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
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
            reveal(this);
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
                    JLabel hintLabel = new JLabel(hint);
                    hintLabel.setVerticalAlignment(SwingConstants.CENTER);
                    hintLabel.setHorizontalAlignment(SwingConstants.CENTER);
                    this.setLayout(new GridLayout());
                    this.add(hintLabel);
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

    private static final int WIDTH = 10;
    private static final int HEIGHT = 15;
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

    private void reveal(HiddenCell cell) {
        Queue<HiddenCell> cellsToReveal = new LinkedList<>();
        cellsToReveal.add(cell);
        do {
            HiddenCell currentCell = cellsToReveal.poll();
            int x = currentCell.x;
            int y = currentCell.y;

            Integer hint = session.getHint(x, y);
            if (hint != null && hint.equals(0)) {
                for (int vertOffset = -1; vertOffset <= 1; vertOffset++) {
                    for (int horizOffset = -1; horizOffset <= 1; horizOffset++) {
                        if (!(vertOffset == 0 && horizOffset == 0)) {
                            int xOffset = x + horizOffset;
                            int yOffset = y + vertOffset;
                            if (xOffset < WIDTH && yOffset < HEIGHT && !(xOffset < 0)
                                    && !(yOffset < 0)) {
                                HiddenCell adjacentCell = getHiddenCell(xOffset, yOffset);
                                if (adjacentCell != null
                                        && !session.isMine(adjacentCell.x, adjacentCell.y)) {
                                    cellsToReveal.add(adjacentCell);
                                }
                            }
                        }
                    }
                }
            }

            revealCell(currentCell);
        }
        while (!cellsToReveal.isEmpty());
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

    private HiddenCell getHiddenCell(int x, int y) {
        int compIndex = (y * WIDTH) + x;
        if (!(compIndex < 0 || compIndex > (HEIGHT * WIDTH) - 1)) {
            Component component = getComponent(compIndex);
            HiddenCell toReturn = component instanceof HiddenCell
                    ? (HiddenCell) component
                    : null;
            return toReturn;
        }
        else {
            return null;
        }
    }
}
