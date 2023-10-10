import java.util.ArrayList;

public class Room {
    private Position pos;
    private ArrayList<Entity> entities;

    public Room(Position p, ArrayList<Entity> entities) {
        this.pos = p;
        this.entities = entities;
    }
}
