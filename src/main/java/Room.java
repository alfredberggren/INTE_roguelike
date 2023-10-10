import java.util.ArrayList;

public class Room {
    private Position pos;
    private Encounter encounter;
    private ArrayList<Entity> entities;

    public Room(Position p, Encounter e, ArrayList<Entity> entities) {
        this.pos = p;
        this.encounter = e;
        this.entities = entities;
    }
}
