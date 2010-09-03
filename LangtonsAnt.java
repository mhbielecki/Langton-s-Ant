import java.util.*;
import java.io.*;
import java.awt.*;
import javax.swing.*;
import java.awt.event.*;

/**
 * Langton's Ant in Java
 * TODO Add GUI options (and make the GUI nicer)
 * TODO Mark curren square red, so it easier to follow the steps
 * TODO Add step counter
 */

public class LangtonsAnt {
    public static void main(String[] args) {
        Main m = new Main();
        m.start();
    }
}

class Main {

    int dim = 80;
    AntGUI g = new AntGUI(dim);
    Ant a = new Ant('u', dim);

    public void start() {

        //place ant on starting square
        g.updateSquareColor(a.getXPos(), a.getYPos());
        Color c;

        /** main loop */
        while (true) {
            try {
                //TODO Get rid of this. Not good practice to catch runtime exceptions
                try {
                     c = g.getSquareColor(a.getXPos(), a.getYPos());
                } catch (ArrayIndexOutOfBoundsException e) {
                    break;
                }

                g.updateSquareColor(a.getXPos(), a.getYPos());
                a.move(c);
                Thread.currentThread().sleep(5);
            } catch (InterruptedException e) {}
        }
    }

}

class Ant {


    char direction;
    int xPos;
    int yPos;

    public Ant(char d, int dim) {
        direction = d;
        xPos = dim / 2;
        yPos = dim / 2;
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
        yPos += 1;
    }

    public void moveDown() {
        yPos -= 1;
    }

    public void moveLeft() {
        xPos -= 1;
    }

    public void moveRight() {
        xPos += 1;
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

    private JTextField[][] squares;
    JPanel boardPanel;
//    JPanel bottomPanel;

    public AntGUI(int dim) {
        boardDim = dim;
        squares = new JTextField[boardDim][boardDim];
        setPreferredSize(new Dimension(boardDim * SQUARE_SIZE,
                    boardDim * SQUARE_SIZE));
        setTitle("Langton's Ant");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        boardPanel = createBoard();
//        bottomPanel = createBottomPanel();

        getContentPane().add(boardPanel, BorderLayout.CENTER);
//        getContentPane().add(bottomPanel, BorderLayout.SOUTH);
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

    public void updateSquareColor(int i, int j) {

        if (squares[i][j].getBackground() == Color.BLACK) 
            squares[i][j].setBackground(Color.WHITE);
        else if (squares[i][j].getBackground() == Color.WHITE)
            squares[i][j].setBackground(Color.BLACK);

        repaint();

    }

    public Color getSquareColor(int i, int j) {
        return squares[i][j].getBackground();
    }
}
