package cynofsin.minesweeper;

import java.awt.Color;
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

    private void reveal(HiddenCell cell) {
        Queue<HiddenCell> cellsToReveal = new LinkedList<>();
        cellsToReveal.add(cell);
        do {
            HiddenCell currentCell = cellsToReveal.poll();
            int x = currentCell.x;
            int y = currentCell.y;

            Integer hint = session.getHint(x, y);
            if (hint != null && hint.equals(0)) {
                HiddenCell[] adjacents = new HiddenCell[4];

                int indexAbove = (currentCell.y * WIDTH) + currentCell.x - WIDTH;
                if (!(indexAbove < 0 || indexAbove > (HEIGHT * WIDTH) - 1)) {
                    HiddenCell cellAbove = this
                            .getComponent(indexAbove) instanceof HiddenCell
                                    ? (HiddenCell) this.getComponent(indexAbove)
                                    : null;
                    // HiddenCell cellAbove = (HiddenCell)
                    // this.getComponent(indexAbove);
                    if (cellAbove != null
                            && !session.isMine(cellAbove.x, cellAbove.y)) {
                        cellsToReveal.add(cellAbove);
                    }
                }

                int indexBelow = (currentCell.y * WIDTH) + currentCell.x + WIDTH;
                if (!(indexBelow < 0 || indexBelow > (HEIGHT * WIDTH) - 1)) {
                    // HiddenCell cellBelow = (HiddenCell) this
                    // .getComponent(indexBelow);
                    HiddenCell cellBelow = this
                            .getComponent(indexBelow) instanceof HiddenCell
                                    ? (HiddenCell) this.getComponent(indexBelow)
                                    : null;
                    if (cellBelow != null
                            && !session.isMine(cellBelow.x, cellBelow.y)) {
                        cellsToReveal.add(cellBelow);
                    }
                }

                int indexLeft = (currentCell.y * WIDTH) + currentCell.x - 1;
                if (!(indexLeft < 0 || indexLeft > (HEIGHT * WIDTH) - 1)) {
                    // HiddenCell cellLeft = (HiddenCell) this
                    // .getComponent(indexLeft);
                    HiddenCell cellLeft = this
                            .getComponent(indexLeft) instanceof HiddenCell
                                    ? (HiddenCell) this.getComponent(indexLeft)
                                    : null;
                    if (cellLeft != null
                            && !session.isMine(cellLeft.x, cellLeft.y)) {
                        cellsToReveal.add(cellLeft);
                    }
                }

                int indexRight = (currentCell.y * WIDTH) + currentCell.x + 1;
                if (!(indexRight < 0 || indexRight > (HEIGHT * WIDTH) - 1)) {
                    // HiddenCell cellRight = (HiddenCell) this
                    //         .getComponent(indexRight);
                    HiddenCell cellRight = this
                            .getComponent(indexRight) instanceof HiddenCell
                                    ? (HiddenCell) this.getComponent(indexRight)
                                    : null;
                    if (cellRight != null && !session.isMine(cellRight.x, cellRight.y)) {
                        cellsToReveal.add(cellRight);
                    }
                }

                for (HiddenCell adjacent : adjacents) {
                    if(adjacent != null) {
                        cellsToReveal.add(adjacent);
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
}
