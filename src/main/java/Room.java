import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Room {
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

    public void setPossibleRoutes(List<CardinalDirection> directions) {
        if (possibleRoutes.equals(directions)) {
            return;
        }
        possibleRoutes = directions;
    }

    /**
     * @return
     * Returns an empty list if room has no possible routes.
     */
    public List<CardinalDirection> getPossibleRoutes(){
        return Collections.unmodifiableList(possibleRoutes);
    }

    public void addPossibleRoute(CardinalDirection... routes){
        for (CardinalDirection r: routes){
            if (!possibleRoutes.contains(r))
                possibleRoutes.add(r);
        }
    }


    //Implementation with no uses at the moment
    public void removePossibleRoute(CardinalDirection... routes){
        for (CardinalDirection r: routes){
            possibleRoutes.remove(r);
        }
    }

    @Override
    public String toString(){
        return "Room at pos " + position + " with interactables: " + interactables.toString() + " and directions: " + possibleRoutes;
    }

    public String toLog() {
        StringBuilder sb = new StringBuilder();
        sb.append(possibleRoutes.size()).append(";");
        for (CardinalDirection c : getPossibleRoutes()) {
            sb.append(c.toString()).append(";");
        }
        return sb.toString();
    }
}
