import java.util.Objects;

public class Position {
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

}
