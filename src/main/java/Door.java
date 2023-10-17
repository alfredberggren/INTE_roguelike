import java.util.HashSet;
import java.util.Set;

public class Door extends Prop{

    private boolean open;

    private Key.Type requiredKeyType;

    private static final Set<InteractableAction> DEFAULT_POSSIBLE_ACTIONS = new HashSet<>() {{
        add(InteractableAction.USE);
    }};

    public Door(String name, Set<InteractableAction> possibleInteractableActions) {
        super(name, possibleInteractableActions);
    }

    /**
     * Creates an open door, with name "Door", and Use as only possible interaction
     */
    public Door() {
        super("Door", DEFAULT_POSSIBLE_ACTIONS);
        this.open = true;
    }

    /**
     * Creates a locked door, with name Door and Use as only possible interaction.
     * @param requiredKeyType
     * The Key.Type able to unlock door.
     */
    public Door(Key.Type requiredKeyType) {
        super("Door", DEFAULT_POSSIBLE_ACTIONS);
        this.open = false;
        this.requiredKeyType = requiredKeyType;
    }

    public boolean isOpen() {
        return open;
    }

    public Key.Type getRequiredKeyType() {
        return requiredKeyType;
    }
}
