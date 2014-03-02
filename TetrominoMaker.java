package se.liu.ida.ludno249.tddd78.lab4;

import java.util.Random;

/**
 * Created by ludno249 on 2014-02-21.
 */
class TetrominoMaker
{
    private SquareType[][] s;
    int getNumberOfTypes(){return SquareType.values().length - 2;}

    Poly getPoly(int n){
        switch(n){
            case 0:
                return makeIblock();
            case 1:
                return makeJblock();
            case 2:
                return makeLblock();
            case 3:
                return makeOblock();
            case 4:
                return makeSblock();
            case 5:
                return makeTblock();
            case 6:
                return makeZblock();
            default:
            throw new IllegalArgumentException("Invalid SquareType. " + String.valueOf(n));

        }
    }

    public Poly getRandomPoly(){
        Random random = new Random();
        return getPoly(random.nextInt(getNumberOfTypes()));
    }

    public Poly getEmptyPoly(int size){
        s = new SquareType[size][size];
        fillEmpty(s);
        return new Poly(s);
    }

    private void fillEmpty(SquareType[][] s){
        for (int x = 0; x < s.length; x++) {
            for (int y = 0; y < s[0].length; y++) {
                s[x][y] = SquareType.EMPTY;
            }
        }
    }

    private Poly makeIblock(){
        s = new SquareType[4][4];
        fillEmpty(s);
        s[1][0] = SquareType.I;
        s[1][1] = SquareType.I;
        s[1][2] = SquareType.I;
        s[1][3] = SquareType.I;
        return new Poly(s);
    }
    private Poly makeJblock(){
        s = new SquareType[3][3];
        fillEmpty(s);
        s[0][0] = SquareType.J;
        s[0][1] = SquareType.J;
        s[1][1] = SquareType.J;
        s[2][1] = SquareType.J;
        return new Poly(s);
    }
    private Poly makeLblock(){
        s = new SquareType[3][3];
        fillEmpty(s);
        s[2][0] = SquareType.L;
        s[0][1] = SquareType.L;
        s[1][1] = SquareType.L;
        s[2][1] = SquareType.L;
        return new Poly(s);
    }
    private Poly makeOblock(){
        s = new SquareType[2][2];
        fillEmpty(s);
        s[0][0] = SquareType.O;
        s[0][1] = SquareType.O;
        s[1][0] = SquareType.O;
        s[1][1] = SquareType.O;
        return new Poly(s);
    }
    private Poly makeSblock(){
        s = new SquareType[3][3];
        fillEmpty(s);
        s[1][0] = SquareType.S;
        s[2][0] = SquareType.S;
        s[0][1] = SquareType.S;
        s[1][1] = SquareType.S;
        return new Poly(s);
    }
    private Poly makeTblock(){
        s = new SquareType[3][3];
        fillEmpty(s);
        s[1][0] = SquareType.T;
        s[0][1] = SquareType.T;
        s[1][1] = SquareType.T;
        s[2][1] = SquareType.T;
        return new Poly(s);
    }
    private Poly makeZblock(){
        s = new SquareType[3][3];
        fillEmpty(s);
        s[0][0] = SquareType.Z;
        s[1][0] = SquareType.Z;
        s[1][1] = SquareType.Z;
        s[2][1] = SquareType.Z;
        return new Poly(s);
    }

    @Override
    public String toString() {
        return "?";
    }
}
