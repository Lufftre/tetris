/**
 * Created by ludno249 on 2014-02-12.
 */
class TetrisTextView //Was used earlier.
{
    public static String convertToText(Board b){
	String output = "";
	for (int y = 0; y < b.getActualHeight(); y++) {
	    for (int x = 0; x < b.getActualWidth(); x++) {
            SquareType s = b.getSquare(x,y);
            if (b.getFalling() != null && b.getFalling_x()  == x && b.getFalling_y() == y){
                output += b.getFalling().toString();
            } else {
                switch (s){
                    case EMPTY:
                    output += "-";
                    break;
                    case OUTSIDE:
                    output += "#";
                    break;
                    case I:
                    output += "I";
                    break;
                    case O:
                    output += "O";
                    break;
                    case T:
                    output += "T";
                    break;
                    case S:
                    output += "S";
                    break;
                    case Z:
                    output += "Z";
                    break;
                    case J:
                    output += "J";
                    break;
                    case L:
                    output += "L";
                    break;
                    default:
                    output += "@";
                    break;
                }
            }
	    }
	    output += "\n";
	}
	return output;
    }

}
