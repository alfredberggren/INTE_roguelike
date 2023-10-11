import java.util.HashMap;
import java.util.Map;

public class Room {

    private static final Integer INTERACTABLE_ADDEND = 1;
    private Position position;
    private Map<Interactable, Integer> interactables;



    public Room(Position position, Map<Interactable, Integer> interactables) {
        if (position == null)
            throw new NullPointerException("Position can't be null!");
        if (interactables == null)
            throw new NullPointerException("Interactables can't be null!");

        this.position = position;
        this.interactables = interactables;

    }

    public Room(Position position){
        this(position, new HashMap<>());
    }



    public Position getPosition(){
        return position;
    }

    public Map<Interactable, Integer> getInteractables(){
        return interactables;
    }

    public void addInteractable(Interactable i, Integer amount){
        if (i == null)
            throw new NullPointerException("Interactable can not be null!");

        if (interactables.containsKey(i))
            interactables.put(i, interactables.get(i)+amount);
        else
            interactables.put(i, amount);
    }

    public void addInteractable(Interactable i) {
        addInteractable(i, INTERACTABLE_ADDEND);
    }



}
