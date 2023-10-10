import java.util.ArrayList;
import java.util.List;

public class Room {
    private Position position;
    private List<Interactable> interactables;

    public Room(Position position, List<Interactable> interactables) {
        this.position = position;
        this.interactables = interactables;

    }

    public Position getPosition(){
        return position;
    }

    public List<Interactable> getInteractables(){
        return interactables;
    }

}
