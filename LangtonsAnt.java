import java.util.*;
import java.io.*;
import java.awt.*;
import javax.swing.*;
import java.awt.event.*;

/**
 * Langton's Ant in Java
 * TODO Add GUI options (and make the GUI nicer)
 * TODO Mark current square red, so it easier to follow the steps
 * TODO Add step counter
 */

public class LangtonsAnt {
    public static void main(String[] args) {
        final int DIM = 80;
        SwingUtilities.invokeLater(new Runnable() {
                public void run() {
                    AntGUI g = new AntGUI(DIM);
                }
        });
    }
}

class Main {

    Ant a;
    AntGUI g;
    int dim;

    public Main(AntGUI gui, int d) {
        g = gui;
        dim = d;
        a = new Ant('r', dim);
    }

    public void startAnt() {

        //place ant on starting square
        g.updateSquareColor(a.getXPos(), a.getYPos(), Color.BLACK);
        Color c;
        int x, y;

        /** main loop */
        while (true) {
            try {
                x = a.getXPos();
                y = a.getYPos();
                c = g.getSquareColor(x, y);
                g.setTempColor(x, y);
                Thread.currentThread().sleep(10);
                a.move(c);
                g.updateSquareColor(x, y, c); //WHITE / BLACK
                Thread.currentThread().sleep(5);

            } catch (InterruptedException e) {}
        }
    }
}

class Ant {


    char direction;
    int xPos;
    int yPos;
    int dim;

    public Ant(char d, int dim) {
        direction = d;
        xPos = dim / 2;
        yPos = dim / 2;
        this.dim = dim;
    }

    public void move(Color c) {

        //Is it a better way to do this?
        if (c == Color.WHITE) {
            if (direction == 'u') {
                direction = 'r';
                moveRight();
            } else if (direction == 'r') {
                direction = 'd';
                moveDown();
            } else if (direction == 'd') {
                direction = 'l';
                moveLeft();
            } else if (direction == 'l') {
                direction = 'u';
                moveUp();
            }
        } else if (c == Color.BLACK) {
            if (direction == 'u') {
                direction = 'l';
                moveLeft();
            } else if (direction == 'l') {
                direction = 'd';
                moveDown();
            } else if (direction == 'd') {
                direction = 'r';
                moveRight();
            } else if (direction == 'r') {
                direction = 'u';
                moveUp();
            }
        }
    }

    public void moveUp() {

        if ((yPos - 1) == -1) 
            yPos = dim-1;
        else 
            yPos -= 1;

    }

    public void moveDown() {

        if ((yPos + 1) == dim)
            yPos = 0;
        else
            yPos += 1;
    }

    public void moveLeft() {

        if ((xPos - 1) == -1)
            xPos = dim-1;
        else
            xPos -= 1;
    }

    public void moveRight() {

        if ((xPos + 1) == dim)
            xPos = 0;
        else
            xPos += 1;
    }

    public void setXPos(int i) {
        xPos = i;
    }

    public void setYPos(int i) {
        yPos = i;
    }

    public int getXPos() {
        return xPos;
    }

    public int getYPos() {
        return yPos;
    }
}

class AntGUI extends JFrame {

    private final int SQUARE_SIZE = 10;
    private final int BOTTOM_SPACE = 30;
    private int boardDim;
    Main m;

    JTextField[][] squares;
    JPanel boardPanel;
    JPanel bottomPanel;
    JPanel topPanel;
    private JButton start;
    private JButton stop;
    private JButton nextStep;
    private JButton reset;


    public AntGUI(int dim) {
        m = new Main(this, dim);
        boardDim = dim;
        squares = new JTextField[boardDim][boardDim];
        setPreferredSize(new Dimension(boardDim * SQUARE_SIZE,
                    boardDim * SQUARE_SIZE));
        setTitle("Langton's Ant");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        boardPanel = createBoard();
        bottomPanel = createBottomPanel();
        topPanel = createTopPanel();

        getContentPane().add(boardPanel, BorderLayout.CENTER);
        getContentPane().add(bottomPanel, BorderLayout.SOUTH);
        getContentPane().add(topPanel, BorderLayout.NORTH);
        pack();
        setVisible(true);
    }

    private JPanel createBoard() {

        boardPanel = new JPanel();
        boardPanel.setLayout(new GridLayout(boardDim, boardDim));
        boardPanel.setAlignmentX(CENTER_ALIGNMENT);
        boardPanel.setAlignmentY(CENTER_ALIGNMENT);
        setPreferredSize(new Dimension(new Dimension(boardDim * SQUARE_SIZE,
                        boardDim * SQUARE_SIZE)));

        for(int i = 0; i < boardDim; i++) {
            for(int j = 0; j < boardDim; j++) {
                JTextField square = new JTextField();
                square.setHorizontalAlignment(SwingConstants.CENTER);
                square.setPreferredSize(new Dimension(SQUARE_SIZE, SQUARE_SIZE));
                square.setBackground(Color.WHITE);
                squares[i][j] = square;
                squares[i][j].setEditable(false);
                boardPanel.add(square);
            }
        }
        return boardPanel;
    }

    private JPanel createBottomPanel() {

        JPanel bottomPanel = new JPanel();
        JLabel steps = new JLabel("Steps");
        bottomPanel.setLayout(new GridLayout(1,0));
        bottomPanel.add(steps);
        return bottomPanel;

    }

    private JPanel createTopPanel() {
        //TODO Add actionlistener
        JPanel topPanel = new JPanel();
        topPanel.setBorder(BorderFactory.createTitledBorder
                (BorderFactory.createEtchedBorder(), "Options"));
        topPanel.setLayout(new GridLayout(1, 0));
        start = new JButton("Start");
        stop = new JButton("Stop");
        nextStep = new JButton("Next step");
        reset = new JButton("Reset");
        ActionListener listener = new ButtonListener();
        stop.addActionListener(listener);
        start.addActionListener(listener);
        nextStep.addActionListener(listener);
        reset.addActionListener(listener);
        topPanel.add(start);
        topPanel.add(stop);
        topPanel.add(nextStep);
        topPanel.add(reset);

        return topPanel;
    }

    public void updateSquareColor(int i, int j, Color c) {

        if (c == Color.WHITE)
            squares[i][j].setBackground(Color.BLACK);
        else if (c == Color.BLACK)
            squares[i][j].setBackground(Color.WHITE);

        repaint();
    }

    public void setTempColor(int i, int j) {
        squares[i][j].setBackground(Color.RED);
        repaint();
    }

    public Color getSquareColor(int i, int j) {
        return squares[i][j].getBackground();
    }

    class ButtonListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            if (e.getSource() == start) {
                new Thread(new Runnable() {
                    public void run() {
                        start.setEnabled(false);
                        m.startAnt();
                    }
                }).start();

            } else if (e.getSource() == stop) {
                System.out.println("stop");
            } else if (e.getSource() == nextStep) {
                System.out.println("Next step");
            } else if (e.getSource() == reset) {
                System.out.println("reset");
            }
        }
    }
}
