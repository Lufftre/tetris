import javax.swing.*;
import java.awt.*;

/**
 * Created by Asa on 2014-02-23.
 */
class TetrisFrame extends JFrame {
    private JTextArea textarea;
    private Board board;
    public TetrisFrame(Board b) {
        super("Tetris");
        Board board = b;
        textarea = new JTextArea(b.getActualHeight(), b.getActualWidth());
        textarea.setText(TetrisTextView.convertToText(board));
        this.setLayout(new BorderLayout());
        this.add(textarea,BorderLayout.CENTER);
    }

    public void update(){
        textarea.setText((TetrisTextView.convertToText(board)));
    }

}
