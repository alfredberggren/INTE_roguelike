import java.util.HashSet;

public class Key extends Equipment{

    //MERGEMERGE
    public enum Type {
        BLUE, RED, YELLOW, BROKEN, NONE;
    }

    private static final int DEFAULT_AMOUNT_OF_USES = 3;

    private static final HashSet<InteractableAction> DEFAULT_ACTIONS = new HashSet<>(){{
        add(InteractableAction.DROP);
        add(InteractableAction.USE);
        add(InteractableAction.LOOT);
    }};

    private static final PhysicalAbility DEFAULT_ABILITY = new PhysicalAbility("Behold key", 0, 1, "Look at the key, it is beautiful");

    private static final int DEFAULT_DURABILITY = 100;

    private static final Effect DEFAULT_EFFECT = Effect.NONE;

    private Type type;

    private int amountOfUses;


    /**
     * Creates a key with given Type. Sets uses to default (3).
     * @param type
     * The type of the key.
     */
    public Key(Type type) {
        super(type.toString() + " key", EquipmentSlot.BELT, DEFAULT_ACTIONS, DEFAULT_EFFECT, DEFAULT_DURABILITY, DEFAULT_ABILITY);
        setKeyType(type);
        this.amountOfUses = DEFAULT_AMOUNT_OF_USES;
    }


    /**
     * Creates a key with given type and given amount of uses.
     * @param type
     * The type of the key to be created
     * @param amountOfUses
     * How many uses until the key breaks. If 0, the key will break.
     */
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


    /**
     * Will decrease the keys' amount of uses by 1. If it reaches 0, the keys' type will be set to BROKEN.
     */
    public void use() {
        amountOfUses--;
        if (amountOfUses == 0){
            setBroken();
        }
    }

    public void setBroken() {
        this.type = Type.BROKEN;
    }

    private void setKeyType(Type keyType){
        if (keyType == Type.NONE){
            throw new IllegalArgumentException("A key cannot have none as Type!");
        } else {
            this.type = keyType;
        }

    }




}
