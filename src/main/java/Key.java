import java.util.HashSet;

public class Key extends Equipment{
    public enum Type {
        BLUE, RED, YELLOW;
    }

    private Type type;

    private static final HashSet<InteractableAction> actions = new HashSet<>(){{
            add(InteractableAction.DROP);
            add(InteractableAction.USE);
    }};


    public Key(Type type) {
        super(type.toString() + " key", actions);
        this.type = type;
    }

    public Type getKeyType() {
        return type;
    }
}
