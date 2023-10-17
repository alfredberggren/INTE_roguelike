import java.util.HashSet;

public class Key extends Equipment{
    public enum Type {
        BLUE, RED, YELLOW, BROKEN, NONE;
    }

    private static final int DEFAULT_AMOUNT_OF_USES = 3;

    private Type type;

    private int amountOfUses;


    private static final HashSet<InteractableAction> DEFAULT_ACTIONS = new HashSet<>(){{
            add(InteractableAction.DROP);
            add(InteractableAction.USE);
    }};


    public Key(Type type) {
        super(type.toString() + " key", DEFAULT_ACTIONS);
        this.type = type;
        this.amountOfUses = DEFAULT_AMOUNT_OF_USES;
    }

    public Key(Type type, int amountOfUses){
        this(type);
        setUses(amountOfUses);
    }

    public Type getKeyType() {
        return type;
    }

    public int getUses() {
        return amountOfUses;
    }

    public void setUses(int amountOfUses){
        if (amountOfUses < 0){
            throw new IllegalArgumentException("amountOfUses can not be negative");
        }
        else {
            this.amountOfUses = amountOfUses;
        }
    }


    public void use() {
        amountOfUses--;
        if (amountOfUses == 0){
            setBroken();
        }
    }

    public void setBroken() {
        this.type = Type.BROKEN;
    }




}
