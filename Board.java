package se.liu.ida.ludno249.tddd78.lab4;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by ludno249 on 2014-02-12.
 */
class Board
{
    private final SquareType[][] squares;
    private final int height;
    private final int width;
    private final int actualHeight;
    private final int actualWidth;
    private Poly falling;
    private int falling_x, falling_y;
    private final List<BoardListener> listeners;
    private final TetrominoMaker tetrominoMaker;
    private final Timer timer;
    private int score;

    //Width and Height are always the same value but I still want to easily be able to change them.
    public Board(int width, int height, Timer timer) {
        this.actualHeight = height + 2;
        this.actualWidth = width + 2;
        this.height = height;
        this.width = width;
        squares = new SquareType[actualWidth][actualHeight];
        listeners = new ArrayList<>();
        this.tetrominoMaker = new TetrominoMaker();
        this.timer = timer;
        this.score = 0;

        for (int x = 0; x < actualWidth; x++) {
            for (int y = 0; y < actualHeight; y++) {
            squares[x][y] = SquareType.OUTSIDE;
            }
        }

        for (int x = 1; x <= width; x++) {
            for (int y = 0; y <= height; y++) {
            squares[x][y] = SquareType.EMPTY;
            }
        }
    }

    //Getters never hurt and might be used later in development.
    public int getHeight() {
	return height;
    }
    public int getWidth() {
	return width;
    }

    public int getActualHeight() {
	return actualHeight;
    }

    public int getActualWidth() {
	return actualWidth;
    }

    public SquareType getSquare(int x, int y){
        if(x > -1 && x < actualWidth && y > -1 && y < actualHeight){
	        return squares[x][y];
        } else {
            return SquareType.OUTSIDE;
        }
    }

    public Poly getFalling() {
        return falling;
    }

    public int getFalling_x() {
        return falling_x;
    }

    public int getFalling_y() {
        return falling_y;
    }

    public int getScore() {
        return score;
    }

    void setSquare(int x, int y, SquareType s){
	    squares[x][y] = s;
        notifyListeners();
    }

    //Setters never hurt and might be used later in development.
    public void setFalling(Poly falling) {
        this.falling = falling;
    }

    public void setFalling_x(int falling_x) {
        this.falling_x = falling_x;
        notifyListeners();
    }

    void setFalling_y(int falling_y) {
        this.falling_y = falling_y;
        notifyListeners();
    }

    //Was used earlier but is now commented out in BoardTest.
    public void randomizeBoard(){
        Random rnd = new Random();
        for (int x = 1; x <= width; x++) {
            for (int y = 1; y <= height; y++) {
            setSquare(x, y, SquareType.values()[rnd.nextInt(8)]);
            }
        }
        notifyListeners();
    }

    public void addBoardListener(BoardListener bl){
        listeners.add(bl);
    }

    private void notifyListeners(){
        if (listeners != null){ //Safety first.
            for (BoardListener listener : listeners) {
                listener.boardChanged();
            }
        }
    }

    public void tick(){
        if(falling == null){
            createFalling();
            timer.setDelay(500);
            if(checkGameOver()){ gameOver();}
        } else if (checkCollide_y(falling) || getBotY(falling) >= height){
            convertFalling(falling);
            score++;
        } else {
            setFalling_y(getFalling_y() + 1);
        }
        notifyListeners();
    }

    private int getBotY(Poly poly){
        int bot_y = 0;
        SquareType[][] s = poly.getSquares();
        for (int y = 0; y < s[0].length; y++) {
            for (int x = 0; x < s.length; x++) {
                if(s[x][y] != SquareType.EMPTY){
                    bot_y = y;
                    break;
                }
            }
        }
        return bot_y +falling_y;
    }

    private void convertFalling(Poly poly){
        SquareType[][] polySquares = poly.getSquares();
        for (int x = 0; x < polySquares.length; x++) {
            for (int y = 0; y <polySquares[0].length; y++) {
                if(poly.getSquare(x,y) != SquareType.EMPTY){
                    setSquare(x+falling_x, y+falling_y, poly.getSquare(x,y));
                }
            }
        }
        falling = null;
        removeRows();
    }

    private void removeRow(int y_start){
        for (int y = y_start; y > 0; y--) {
            for (int x = 1; x < squares.length - 1; x++) {
                setSquare(x,y,getSquare(x,y-1));
            }
        }
    }

    private void removeRows(){
        boolean rowFilled = true;
        for (int y = 1; y < squares[0].length - 1; y++) {
            for (int x = 1; x < squares.length - 1; x++) {
                if(getSquare(x,y) == SquareType.EMPTY) rowFilled = false;
            }
            if(rowFilled){
                removeRow(y);
            }
            rowFilled = true;
        }
        notifyListeners();
    }

    private void createFalling(){
        falling = tetrominoMaker.getRandomPoly();
        //falling = tetrominoMaker.getPoly(3);
        falling_x = width / 2;
        falling_y = 1;
    }

    private boolean checkCollide_y(Poly poly){
        SquareType[][] polySquares = poly.getSquares();
        for (int x = 0; x < polySquares.length; x++) {
            for (int y = 0; y <polySquares[0].length; y++) {
                if(poly.getSquare(x,y) != SquareType.EMPTY){
                    if(getSquare(falling_x + x, falling_y + y + 1) != SquareType.EMPTY){return true;}
                }
            }
        }
        return false;
    }

    private boolean checkCollide_x(Poly poly, int direction){
        SquareType[][] polySquares = poly.getSquares();
        for (int x = 0; x < polySquares.length; x++) {
            for (int y = 0; y <polySquares[0].length; y++) {
                if(poly.getSquare(x,y) != SquareType.EMPTY){
                    if(getSquare(falling_x + x + direction, falling_y + y) != SquareType.EMPTY){return true;}
                }
            }
        }
        return false;
    }

    private boolean checkGameOver(){
        return checkCollide_y(falling);
    }

    public void leftPress(){
        if(falling!=null){
            if(!checkCollide_x(falling, -1)) falling_x -= 1;
        }
        notifyListeners();
    }

    public void rightPress(){
        if(falling!=null){
            if(!checkCollide_x(falling, 1)) falling_x += 1;
        }
        notifyListeners();
    }

    public void upPress(){
        if(falling!=null){
            Poly rotated = rotateRight();
            if (!checkCollide_x(rotated, 1) &&
                !checkCollide_x(rotated, -1) &&
                !checkCollide_y(rotated)) falling = rotateRight();
        }
        notifyListeners();
    }

    public void downPress(){
        timer.setDelay(50);
    }

    Poly rotateRight(){

        Poly newPoly = tetrominoMaker.getEmptyPoly(falling.getSize());

        for (int r = 0; r < falling.getSize(); r++) {
            for (int c = 0; c < falling.getSize(); c++){
                newPoly.setSquare(r,c,falling.getSquare(c, falling.getSize() - 1 - r));
            }
        }

        return newPoly;
    }

    private void gameOver(){
        score = 0;
        for (int x = 1; x <= width; x++) {
            for (int y = 1; y <= height; y++) {
                squares[x][y] = SquareType.EMPTY;
            }
        }
    }
}
