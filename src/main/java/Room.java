import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Room {
    private Position position;
    private HashMap<Interactable, Integer> interactables;

    public Room(Position position, HashMap<Interactable, Integer> interactables) {
        if (position == null)
            throw new NullPointerException("Position can't be null!");
        this.position = position;
        this.interactables = interactables;

    }

    public Position getPosition(){
        return position;
    }

    public HashMap<Interactable, Integer> getInteractables(){
        return interactables;
    }

}
