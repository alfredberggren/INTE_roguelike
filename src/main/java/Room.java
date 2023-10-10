import java.util.HashMap;
import java.util.Map;

public class Room {
    private Position position;
    private Map<Interactable, Integer> interactables;

    public Room(Position position, Map<Interactable, Integer> interactables) {
        if (position == null)
            throw new NullPointerException("Position can't be null!");
        this.position = position;
        this.interactables = interactables;

    }

    public Position getPosition(){
        return position;
    }

    public Map<Interactable, Integer> getInteractables(){
        return interactables;
    }

}
