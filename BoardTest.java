package se.liu.ida.ludno249.tddd78.lab4;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by ludno249 on 2014-02-12.
 */
public class BoardTest
{
    private Board board;
    //private TetrisFrame frame;
    private JFrame frame;
    private Action tick;
    private Timer timer;
    private TetrisComponent component;
    private Action leftPress, rightPress, upPress, downPress;

    private BoardTest(){
        createActions();
        createTimer();
        createBoard();
        createFrame();
        createMenus();
        createComponent();
        createKeybinds();

    }

    private void createBoard(){
       this.board = new Board(10,20, timer);
    }

    private void createFrame(){
        //this.frame = new TetrisFrame(this.board);
        //frame.pack();
        this.frame = new JFrame();
        frame.setVisible(true);
    }

    private void createComponent(){
        this.component = new TetrisComponent(this.board);
        board.addBoardListener(component);
        frame.add(component);
        component.setPreferredSize(component.getPreferredSize());
        frame.pack();
    }

    private void createActions(){
        this.tick = new AbstractAction() {
            public void actionPerformed(ActionEvent e) {
                //board.randomizeBoard();
                board.tick();
            }
        };

        this.leftPress = new AbstractAction() {
            public void actionPerformed(ActionEvent e) {
                board.leftPress();
            }
        };

        this.rightPress = new AbstractAction() {
            public void actionPerformed(ActionEvent e) {
                board.rightPress();
            }
        };

        this.upPress = new AbstractAction() {
            public void actionPerformed(ActionEvent e) {
                board.upPress();
            }
        };

        this.downPress = new AbstractAction() {
            public void actionPerformed(ActionEvent e) {
                board.downPress();
            }
        };
    }

    private void createKeybinds(){
        component.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("LEFT"), "leftPress");
        component.getActionMap().put("leftPress", this.leftPress);

        component.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("RIGHT"), "rightPress");
        component.getActionMap().put("rightPress", this.rightPress);

        component.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("UP"), "upPress");
        component.getActionMap().put("upPress", this.upPress);

        component.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("DOWN"), "downPress");
        component.getActionMap().put("downPress", this.downPress);
    }

    private void createTimer(){
        this.timer = new Timer(500, this.tick);
        timer.setCoalesce(true);
        timer.start();
    }

    private void createMenus(){
        final JMenu file = new JMenu("File");
        final JMenuItem quit = new JMenuItem("Quit", 'Q');
        file.add(quit);

        final JMenuBar bar = new JMenuBar();
        bar.add(file);
        frame.setJMenuBar(bar);

        quit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
               int answer = JOptionPane.showConfirmDialog(frame, "Quit?","title", JOptionPane.YES_NO_OPTION);
                if(answer == JOptionPane.YES_OPTION){System.exit(0);}
            }
        });
    }

    public static void main(String[] args) {
	new BoardTest();
    }
}
