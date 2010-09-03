import java.util.*;
import java.io.*;
import java.awt.*;
import javax.swing.*;
import java.awt.event.*;

/**
 * Langton's Ant in Java
 */

public class LangtonsAnt {
    public static void main(String[] args) {


        Main m = new Main();
        m.start();
    }
}

class Main {

    int dim = 20;
    AntGUI g = new AntGUI(dim);
    Ant a = new Ant('u');


    public void start() {

        
//            try {
//                for (int i = 0; i < dim; i++) {
//                    for (int j = 0; j < dim; j++) {
//                        g.updateSquareColor(i, j);
//                        Thread.currentThread().sleep(10);
//                    }
//                }
//            } catch (InterruptedException e) {}
        

    }


    //    while(true) {
    //        // move ant around
    //        // update tile color
    //    }
}

class Ant {


    char direction;  //start direction

    public Ant(char d) {
        direction = d;
    }

//    public void move() {
//        if (square == black){
//            square.flipColor(Color.WHITE);
//        } else if (square == white) {
//            square.flipColor(Color.BLACK);
//        }
//    }

    public void moveUp() {

    }

    public void moveDown() {

    }

    public void moveLeft() {

    }

    public void moveRight() {

    }
}

class AntGUI extends JFrame {

    private final int SQUARE_SIZE = 30;
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
}
