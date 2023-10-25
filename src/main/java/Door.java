import java.util.HashSet;
import java.util.Set;

/**
 * A class representing a door. A door has a keyType, which decides if it can be opened.
 * It can also be set to consume the key, which will set it's type to Broken.
 * There are two special types, BROKEN and NONE. If BROKEN, the door can't be opened or closed. If NONE, the door do
 * not require a key.
 * @author Kristian
 */
public class Door extends Prop{

    private boolean open;

    /**
     * The KeyType Required to open/close door. If type is BROKEN, door can't be opened/closed. If type is NONE, no key
     * is required.
     */
    private Key.Type requiredKeyType;

    private boolean breaksKeyAfterUse;

    private static final Set<InteractableAction> DEFAULT_POSSIBLE_ACTIONS = new HashSet<>() {{
        add(InteractableAction.USE);
    }};

    /**
     * Creates a fully customized door.
     * @param name
     * The name of the door
     * @param possibleInteractableActions
     * The interactions possible to do with door.
     * @param keyRequired
     * The key.type required to unlock door. If BROKEN, door won't be able to open/close. If NONE, no keyType is required.
     * @param open
     * boolean that signifies if door is open.
     * @param breaksKeyAfterUse
     * boolean that if true, signifies that door will break key after use.
     */
    public Door(String name, Set<InteractableAction> possibleInteractableActions, Key.Type keyRequired, boolean open, boolean breaksKeyAfterUse) {
        super(name, possibleInteractableActions);
        setRequiredKeyType(keyRequired);
        this.open = open;
        this.breaksKeyAfterUse = breaksKeyAfterUse;
    }

    public Door(String name, Key.Type keyRequired, boolean open, boolean breaksKeyAfterUse) {
        super(name, DEFAULT_POSSIBLE_ACTIONS);
        setRequiredKeyType(keyRequired);
        this.open = open;
        this.breaksKeyAfterUse = breaksKeyAfterUse;
    }

    /**
     * Creates an open door, with name "Open door", keyType as NONE, and Use as only possible interaction
     */
    public Door() {
        super("Open door", DEFAULT_POSSIBLE_ACTIONS);
        this.open = true;
        this.breaksKeyAfterUse = false;
        this.requiredKeyType = Key.Type.NONE;
    }

    /**
     * Creates a LOCKED door, with name Door and Use as only possible interaction.
     * @param requiredKeyType
     * The Key.Type able to unlock door.
     * @param breaksKeyAfterUse
     * boolean that if true, signifies that key will be "broken" and destroyed after use.
     */
    public Door(Key.Type requiredKeyType, boolean breaksKeyAfterUse) {
        super("Door", DEFAULT_POSSIBLE_ACTIONS);
        setRequiredKeyType(requiredKeyType);
        this.open = false;
        this.breaksKeyAfterUse = breaksKeyAfterUse;
    }

    public boolean isOpen() {
        return open;
    }

    public void setOpen(boolean open) {
       this.open = open;
    }

    public Key.Type getRequiredKeyType() {
        return requiredKeyType;
    }

    public void setRequiredKeyType(Key.Type k) {
        this.requiredKeyType = k;
    }

    /**
     * Indicates if door will set the key to BROKEN after it has been used on door.
     * @return
     * True if Door will break key, else false.
     */
    public boolean breaksKeyAfterUse(){
        return breaksKeyAfterUse;
    }

    public void setBreaksKeyAfterUse(boolean breaksKeyAfterUse) {
        this.breaksKeyAfterUse = breaksKeyAfterUse;
    }

    /**
     * The method to be used in game. Will Only open if Door has NONE as keytype (no lock)
     * @return
     * True if door could be opened, false otherwise. Also false if door was already open.
     */
    public boolean open(){
        //This seems counterintuitive, but if door is already opened, you can't open a door even more, the move fails.
        if (open) {
            return false;
        }

        if (requiredKeyType == Key.Type.NONE) {
            setOpen(true);
            return true;
        }

        return false;
    }

    /**
     * The method to be used in game for opening door with key.
     * The door will open if the key passed as argument has the right type. If so, the key's uses will decrease.
     * If the key is broken, the door won't open.
     * If the door is broken, the door won't open.
     * @param key
     * The key to be used.
     * @return
     * True if the door was opened, false if not (If key broken, if door broken, if key's type did not match door's).
     */
    public Boolean open(Key key){
        if (open) {
            return false;
        }

        if (requiredKeyType == Key.Type.BROKEN){
            return false;
        }

        if (requiredKeyType == key.getKeyType()){
            setOpen(true);
            key.use();
            if (breaksKeyAfterUse){
                key.setBroken();
            }
            return true;
        }

        return false;
    }



    /**
     * The method to be used in game.
     * @return
     * True if door could be closed, false otherwise. Also false if door was already closed.
     */
    public boolean close(){
        if(isClosedOrBroken()){
            return false;
        }

        if (requiredKeyType == Key.Type.NONE){
            setOpen(false);
            return true;
        }

        return false;

    }


    /**
     * The method to be used in game for closing a door with a key.
     * The door will be closed if the key passed as argument has the right type. If so, the key's uses will decrease.
     * If the key is broken, the door won't close.
     * If the door is broken, the door won't close.
     * @param key
     * The key to be used.
     * @return
     * True if the door was closed, false if not (If key broken, if door broken, if key's type did not match door's).
     */
    public boolean close(Key key){

        if(isClosedOrBroken())
            return false;

        if(requiredKeyType == Key.Type.NONE){
            setOpen(false);
            return true;
        }
        if (requiredKeyType == key.getKeyType()) {
            setOpen(false);
            key.use();
            if (breaksKeyAfterUse){
                key.setBroken();
            }
            return true;
        }
        return false;
    }

    private boolean isClosedOrBroken(){
        if(!open) {
            return true;
        }

        if(requiredKeyType == Key.Type.BROKEN) {
            return true;
        }

        return false;
    }



    /**
     * VERY not sure about this implementation. Not sure in what way it will be used.
     * @param o
     * The Object to check
     * @return
     * A not very definite boolean. Do not trust this method.
     */
    @Override
    public boolean equals(Object o){
        if (o == null) {
            return false;
        }

        if (o instanceof Door d){
            return (d.name.equals(this.name) &&
                    d.requiredKeyType.equals(this.requiredKeyType) &&
                    d.open == this.open &&
                    d.breaksKeyAfterUse == this.breaksKeyAfterUse &&
                    d.getPossibleActions().equals(this.getPossibleActions()));
        }
        return false;
    }
}
