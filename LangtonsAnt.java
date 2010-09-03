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

        //start GUI
        //lag Ant object
    }
}

class AntGUI extends JFrame {

    private final int SQUARE_SIZE = 40;
    private final int BOTTOM_SPACE = 30;
    private int boardDim;

    private JTextField[][] squares;
    JPanel boardPanel;
    JPanel bottomPanel;

    public AntGUI() {
        boardDim = 10;
        squares = new JTextField[boardDim][boardDim];
        setPreferredSize(new Dimension(boardDim * SQUARE_SIZE,
                    boardDim * SQUARE_SIZE + BOTTOM_SPACE));
        setTitle("Langton's Ant");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

    }
}
