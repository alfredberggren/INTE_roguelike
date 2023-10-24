import java.util.Comparator;
import java.util.Objects;

public class Position implements Comparable<Position> {
    private int x;
    private int y;

    public Position(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    @Override
    public boolean equals(Object o){
        if (o instanceof Position p) {
            if (p.getX() == x && p.getY() == y)
                return true;
        }
        return false;
    }
    @Override
    public int hashCode() {
        String codeString = Integer.toString(x) + y;
        return Objects.hashCode(codeString);
    }


    //TODO: Decide on which of the toStrings are the best ??????
    @Override
    public String toString(){
        return "Position [" + x + "." + y +"]";
    }

    @Override
    public int compareTo(Position o) {
        if (x > o.getX()) {
            return 1;
        } else if (x < o.getX()) {
            return -1;
        } else if (y > o.getY()){
            return 1;
        } else if (y < o.getY()) {
            return -1;
        }
        return 0;
    }

//    @Override
//    public String toString(){
//        return "Position=[ " + x + ", " + y + " ]";
//    }

}
