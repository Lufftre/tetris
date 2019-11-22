
import javax.swing.*;
import java.awt.*;
import java.util.EnumMap;

/**
 * Created by Asa on 2014-02-23.
 */
public class TetrisComponent extends JComponent implements BoardListener{
    private final Board board;
    private final int tileSize;
    private EnumMap<SquareType, Color> colorMap;
    private final Font myFont;

    public TetrisComponent(Board board) {
        this.board = board;
        this.tileSize = 40;
        createEnumMap();
        this.setBackground(Color.BLACK);
        myFont = new Font("Arial", Font.BOLD,30);


    }

    public Dimension getPreferredSize(){
        int xsize = board.getActualWidth()*tileSize;
        int ysize = board.getActualHeight()*tileSize;
        return new Dimension(xsize,ysize);
    }

    private void createEnumMap(){
        colorMap = new EnumMap<>(SquareType.class);
        colorMap.put(SquareType.OUTSIDE, Color.GRAY);
        colorMap.put(SquareType.EMPTY, Color.BLACK);
        colorMap.put(SquareType.I, Color.CYAN);
        colorMap.put(SquareType.J, Color.BLUE);
        colorMap.put(SquareType.L, Color.ORANGE);
        colorMap.put(SquareType.O, Color.YELLOW);
        colorMap.put(SquareType.S, Color.GREEN);
        colorMap.put(SquareType.T, Color.MAGENTA);
        colorMap.put(SquareType.Z, Color.RED);
    }

    @Override
    public void paintComponent(Graphics g){
        super.paintComponent(g);
        final Graphics2D g2d = (Graphics2D) g;

        g2d.setColor(Color.BLACK);
        g2d.fillRect(0,0,tileSize*board.getActualWidth(),tileSize*board.getActualHeight());

        for (int y = 0; y < board.getActualHeight(); y++) {
            for (int x = 0; x < board.getActualWidth(); x++) {
                g2d.setColor(colorMap.get(board.getSquare(x,y)));

                g2d.fillRect(x*tileSize,y*tileSize,tileSize-1,tileSize-1);
                }
            }

        if(board.getFalling() != null){
            for (int i = 0; i < board.getFalling().getSquares().length ; i++) {
                for (int j = 0; j < board.getFalling().getSquares()[0].length; j++) {
                    if(board.getFalling().getSquares()[i][j] != SquareType.EMPTY){
                        g2d.setColor(colorMap.get(board.getFalling().getSquares()[i][j]));
                        g2d.fillRect((board.getFalling_x() + i)*tileSize,
                                     (board.getFalling_y() + j)*tileSize,
                                      tileSize-1,tileSize-1);
                    }

                }
            }
        }

    g2d.setFont(myFont);
    g2d.drawString(String.valueOf(board.getScore()),
            (board.getActualWidth()*tileSize) - 30,
            110);

    }

    @Override
    public void boardChanged() {
        repaint();
    }
}
