package ChessGUI;

import com.sun.deploy.panel.JavaPanel;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.net.URL;
import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.*;
import Utils.*;

public class ChessGUI {

    private final JPanel gui = new JPanel( new BorderLayout(3,3));
    public JButton[][] boardSquares = new JButton[8][8];
    private JPanel chessBoard = new JPanel( );
    private Image[][] pieceImages = new Image[2][6];
    private final JLabel message = new JLabel("Lets Play!");   //Change Message
    JButton startButton = new JButton("START");
    JButton restartButton = new JButton("RESTART");
    JButton forfeitButton = new JButton("FORFEIT");
    JButton customStartButton = new JButton("Start Custom Game");
    JButton submitNames = new JButton("Submit Names");
    JButton undo = new JButton("Undo Move");
    public JTextField playerName1 = new JTextField(20);
    public JTextField playerName2 = new JTextField(20);
    public JTextField score1 = new JTextField(20);
    public JTextField score2 = new JTextField(20);

    /**
     * constructor: initialises GUI
     */
    public ChessGUI(){
        initialiseGUI();
    }

    /**
     * Sets up main Frame, panel, chessboard panel, menubar
     */
    public final void initialiseGUI(){
        gui.setBorder( new EmptyBorder(5,5,5,5));
        setUpMenu();
        setUpButtons();
        JFrame frame = new JFrame("Chess Game");
        frame.setVisible(true);
        chessBoard = new JPanel(new GridLayout(8,8)){
            /**
            * Trying to utilise maz space, if parent size is bigger than preffered size then make
            * preffered size as parents size
            * In order to create a sqaure board select the smaller of the two dimensions.
             **/
            @Override
            public final Dimension getPreferredSize() {
                Dimension d = super.getPreferredSize();
                Dimension prefSize ;
                Component c = getParent();
                //If this container has a parent larger than it
                if (c!=null && c.getWidth()>d.getWidth() && c.getHeight()>d.getHeight()) {
                    prefSize = c.getSize();
                }
                else {
                    prefSize = d;
                }
                int w = (int) prefSize.getWidth();
                int h = (int) prefSize.getHeight();
                // the smaller of the two sizes
                int s = (w>h ? h : w);
                //System.out.print(s);
                return new Dimension(s,s);
            }
        };
        setUpBoardSquares();
        createImages();
        //setupNewGame();
        /**Trying to see how to access the particular button with the mouse click event**/
//        boardSquares[0][1].addMouseListener(new MouseAdapter(){
//            @Override
//            public void mouseClicked(MouseEvent e) {
//                    super.mouseClicked(e);
//                JButton button = (JButton)(e.getSource());
//                Point location = button.getLocation();
//                JOptionPane.showMessageDialog(null, location);
//            }
//        });
        chessBoard.setBorder( new CompoundBorder(new EmptyBorder(20,20,20,20), new LineBorder(Color.BLACK)));
        Color ochre = new Color(204,119,34);
        chessBoard.setBackground(ochre);
        JPanel boardConstraint = new JPanel(new GridBagLayout());
        JPanel leftSide = new JPanel(new BorderLayout(3,3));
        JPanel rightSide = new JPanel(new BorderLayout(3,3));
        boardConstraint.add(chessBoard);
        leftSide.add(playerName1, BorderLayout.PAGE_START);
        rightSide.add(playerName2, BorderLayout.PAGE_START);
        leftSide.add(score1,BorderLayout.PAGE_END);
        rightSide.add(score2, BorderLayout.PAGE_END);
        gui.add(leftSide, BorderLayout.LINE_START);
        gui.add(rightSide, BorderLayout.LINE_END);
        gui.add(boardConstraint);
        frame.add(gui);
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        //frame.setUndecorated(true);
    }
    /**
     * add buttons to each board square
     */
    public void setUpBoardSquares() {
        Insets buttonMargin = new Insets(0, 0, 0, 0);
        for (int i = 0; i < boardSquares.length; i++) {                 //row
            for (int j = 0; j < boardSquares[i].length; j++) {          //column
                JButton b = new JButton();
                b.setMargin(buttonMargin);
                if ((i % 2 == 1 && j % 2 == 1) || (i % 2 == 0 && j % 2 == 0)) {
                    b.setBackground(Color.WHITE);
                } else {
                    b.setBackground(Color.BLACK);
                }
                boardSquares[i][j] = b;
                chessBoard.add(boardSquares[i][j]);
            }
        }
    }
    /**
     * get sub-images from an image URL
     */
    private void createImages( ){
        try {
            URL url = new URL("http://i.stack.imgur.com/memI0.png");
            BufferedImage bi = ImageIO.read(url);
            for (int i = 0; i < 2; i++) {
                for (int j = 0; j < 6; j++) {
                    pieceImages[i][j] = bi.getSubimage(
                            j * 64, i * 64, 64, 64);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(1);
        }


    }
    /**
     * Initializes the icons of the initial chess board piece places
     */
    public void setupNewGame() {
        //message.setText("Make your move!");
        boardSquares[0][0].setIcon(new ImageIcon(pieceImages[1][2]));
        boardSquares[0][1].setIcon(new ImageIcon(pieceImages[1][3]));
        boardSquares[0][2].setIcon(new ImageIcon(pieceImages[1][4]));
        boardSquares[0][3].setIcon(new ImageIcon(pieceImages[1][0]));
        boardSquares[0][4].setIcon(new ImageIcon(pieceImages[1][1]));
        boardSquares[0][5].setIcon(new ImageIcon(pieceImages[1][4]));
        boardSquares[0][6].setIcon(new ImageIcon(pieceImages[1][3]));
        boardSquares[0][7].setIcon(new ImageIcon(pieceImages[1][2]));
        boardSquares[7][0].setIcon(new ImageIcon(pieceImages[0][2]));
        boardSquares[7][1].setIcon(new ImageIcon(pieceImages[0][3]));
        boardSquares[7][2].setIcon(new ImageIcon(pieceImages[0][4]));
        boardSquares[7][3].setIcon(new ImageIcon(pieceImages[0][0]));
        boardSquares[7][4].setIcon(new ImageIcon(pieceImages[0][1]));
        boardSquares[7][5].setIcon(new ImageIcon(pieceImages[0][2]));
        boardSquares[7][6].setIcon(new ImageIcon(pieceImages[0][3]));
        boardSquares[7][7].setIcon(new ImageIcon(pieceImages[0][4]));
        for(int i = 0; i < 8; i++){
            boardSquares[1][i].setIcon(new ImageIcon(pieceImages[1][5]));
        }
        for(int i = 0; i < 8; i++){
            boardSquares[6][i].setIcon(new ImageIcon(pieceImages[0][5]));
        }
    }
    public void setupNewGameCustom() {
        //message.setText("Make your move!");
        boardSquares[0][0].setIcon(new ImageIcon(pieceImages[1][2]));
        boardSquares[0][1].setIcon(new ImageIcon(pieceImages[1][3]));
        boardSquares[0][2].setIcon(new ImageIcon("CustomIcons/knookW.png"));
        boardSquares[0][3].setIcon(new ImageIcon(pieceImages[1][0]));
        boardSquares[0][4].setIcon(new ImageIcon(pieceImages[1][1]));
        boardSquares[0][5].setIcon(new ImageIcon("CustomIcons/knookW.png"));
        boardSquares[0][6].setIcon(new ImageIcon(pieceImages[1][3]));
        boardSquares[0][7].setIcon(new ImageIcon(pieceImages[1][2]));
        boardSquares[7][0].setIcon(new ImageIcon(pieceImages[0][2]));
        boardSquares[7][1].setIcon(new ImageIcon(pieceImages[0][3]));
        boardSquares[7][2].setIcon(new ImageIcon("CustomIcons/knookB.png"));
        boardSquares[7][3].setIcon(new ImageIcon(pieceImages[0][0]));
        boardSquares[7][4].setIcon(new ImageIcon(pieceImages[0][1]));
        boardSquares[7][5].setIcon(new ImageIcon("CustomIcons/knookB.png"));
        boardSquares[7][6].setIcon(new ImageIcon(pieceImages[0][3]));
        boardSquares[7][7].setIcon(new ImageIcon(pieceImages[0][4]));
        for(int i = 0; i < 8; i++){
            boardSquares[1][i].setIcon(new ImageIcon(pieceImages[1][5]));
        }
        for(int i = 0; i < 8; i++){
            boardSquares[6][i].setIcon(new ImageIcon(pieceImages[0][5]));
        }
        boardSquares[1][2].setIcon(new ImageIcon("CustomIcons/strategy1.png"));
        boardSquares[1][5].setIcon(new ImageIcon("CustomIcons/strategy1.png"));
        boardSquares[6][2].setIcon(new ImageIcon("CustomIcons/pawn.png"));
        boardSquares[6][5].setIcon(new ImageIcon("CustomIcons/pawn.png"));



    }
    /**
     * Adds a Menu bar with "File" menu and "Reset" menu item, currently it does not have any function
     */
    private void setUpMenu(){
        JMenuBar menubar = new JMenuBar();
        JMenu file = new JMenu("File");
        JMenuItem open = new JMenuItem("Reset");
        //open.addActionListener();           //ADD FUNCTIONALITY
        file.add(open);
        menubar.add(file);
        gui.add(menubar, BorderLayout.PAGE_START);
    }
    /**
     *
     */
    public void setUpButtons() {
        JPanel buttonContainerLower = new JPanel(new GridLayout(1,3));
        buttonContainerLower.add(startButton);
        buttonContainerLower.add(customStartButton);
        buttonContainerLower.add(undo);
        buttonContainerLower.add(restartButton);
        buttonContainerLower.add(forfeitButton);
        gui.add(buttonContainerLower, BorderLayout.PAGE_END);
    }
    /**
     *
     * @param m
     */
    public void addStartButtonListener(MouseAdapter m) {
        startButton.addMouseListener(m);
    }
    /**
     *
     */
    public void addCustomStartButton(MouseAdapter m ){
        customStartButton.addMouseListener(m);
    }
    /**
     *
     */
    public void addUndoButtonListener(MouseAdapter m){
        undo.addMouseListener(m);
    }
    /**
     *
     * @param m
     */
    public void addRestartButtonListener(MouseAdapter m) {
        restartButton.addMouseListener(m);
    }
    /**
     *
     * @param m
     */
    public void addForfeitButtonListener(MouseAdapter m) {
        forfeitButton.addMouseListener(m);
    }
    /**
     *
     */
    public void addBoardSquareListener(MouseAdapter m){
        for( int i = 0; i < Util.NUM_ROWS; i++ ) {
            for( int j = 0; j < Util.NUM_COLS; j++ ) {
                boardSquares[i][j].addMouseListener(m);
            }

        }
    }
//    /**
//     *
//     * @param k
//     */
//    public void addNameListener(MouseAdapter m) {
//        submitNames.addMouseListener(m);
//    }
    /**
     * Adds a button "START" to the bottom of the screen.
     * On click it will show a dialog box with a message.
     */
    private void setUpStartButton(){
        JButton startButton = new JButton("START");
        startButton.addMouseListener(new MouseAdapter(){
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                JOptionPane.showMessageDialog(null, message);
            }
        });
        gui.add(startButton, BorderLayout.PAGE_END);
    }

    public static void main(String[] args){
        ChessGUI GUI = new ChessGUI();
    }
}

