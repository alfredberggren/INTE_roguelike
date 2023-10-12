import java.util.HashMap;
import java.util.Map;

public class Room {

    private static final Integer INTERACTABLE_ADDEND = 1;
    private Position position;
    private InteractableInventory interactables;

    private

    //TODO: Implementera "possible routes", set och get

    public Room(Position position, InteractableInventory interactables) {
        if (position == null)
            throw new NullPointerException("Position can't be null!");
        if (interactables == null)
            throw new NullPointerException("Interactables can't be null!");

        this.position = position;
        this.interactables = interactables;

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

    public void addInteractable(Interactable i, Integer amount){
        interactables.add(i, amount);
    }

    public void addInteractable(Interactable i) {
        addInteractable(i, INTERACTABLE_ADDEND);
    }

    /**
     * Removes the Interactable object including its amount from room
     * @param i
     * The interactable to remove
     */
    public void removeInteractable(Interactable i) {
        interactables.remove(i);
    }

    /**
     * Removes the amount specified of Interactable object
     * @param i
     * The interactable item to subtract from
     * @param amount
     * Amount to subtract
     */
    public void removeInteractable(Interactable i, Integer amount){
        interactables.remove(i, amount);
    }



}
