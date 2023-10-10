import java.util.ArrayList;

public class Room {
    private Position pos;
    private ArrayList<Interactable> interactables;

    public Room(Position p, ArrayList<Interactable> interactables) {
        this.pos = p;
        this.interactables = interactables;
    }
}
