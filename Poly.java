package se.liu.ida.ludno249.tddd78.lab4;

/**
 * Created by ludno249 on 2014-02-12.
 */
public class Poly
{
    private final SquareType[][] squares;

    public Poly(SquareType[][] s) {
	    this.squares = s;
    }

    public SquareType[][] getSquares() {
        return squares;
    }

    public SquareType getSquare(int x, int y) {
        return squares[x][y];
    }

    public void setSquare(int x, int y, SquareType s){
        squares[x][y] = s;
    }

    public int getSize(){
        return squares.length;
    }
}
