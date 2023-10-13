import java.util.ArrayList;
import java.util.List;

public class Room {

    private static final int INTERACTABLE_DEFAULT_ADDEND = 1;
    private final Position position;
    private final InteractableInventory interactables;

    private List<CardinalDirection> possibleRoutes;

    public Room(Position position, InteractableInventory interactables) {
        if (position == null)
            throw new NullPointerException("Position can't be null!");
        if (interactables == null)
            throw new NullPointerException("Interactables can't be null!");

        this.position = position;
        this.interactables = interactables;
        possibleRoutes = new ArrayList<>();

    }
    public Room(Position position){
        this(position, new InteractableInventory());
    }

    public Position getPosition(){
        return position;
    }

    public InteractableInventory getInteractables(){
        return interactables;
    }

    public void setPossibleRoutes(List<CardinalDirection> directions){
        possibleRoutes = directions;
    }

    /**
     * @return
     * Returns an empty list if room has no possible routes.
     */
    public List<CardinalDirection> getPossibleRoutes(){
        return possibleRoutes;
    }


}
