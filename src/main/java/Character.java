/**
 * The Character class represents a game character that can interact with the game world. It implements Interactable.
 */

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Character implements Interactable {
    private static final Set<InteractableAction> STANDARD_CHARACTER_INTERACTABLE_ACTIONS = new HashSet<>(Arrays.asList(
            InteractableAction.TALK,
            InteractableAction.FIGHT,
            InteractableAction.LOOT,
            InteractableAction.DROP)
    );
    private static final String DEFAULT_NAME_PATTERN = "^[A-Za-z]\\w{1,11}$";
    private static final int MAX_LEVEL = 10;
    private static final int MAX_HEALTH = 100;
    private static final int MAX_MANA = 100;
    private Set<InteractableAction> possibleInteractableActions;
    private String name;
    private int health;
    private int speed;
    private int mana;
    private int level;
    private Position pos;
    private Set<Ability> abilities;
    private InteractableInventory inventory;
    private EquipmentOnBody equipmentOnBody;
    private TurnSystem turnSystem;

    public Character(String name, int health, int speed, int level, IO io) {
        this.setHealth(health);
        if (speed < 0) {
            throw new IllegalArgumentException("Speed needs to be 0 or more");
        }
        if (io == null) {
            throw new IllegalArgumentException("IO Cannot be null");
        }
        this.setLevel(level);
        this.speed = speed;
        this.setName(name);
        mana = 0;
        pos = new Position(0, 0);
        possibleInteractableActions = STANDARD_CHARACTER_INTERACTABLE_ACTIONS;
        turnSystem = new TurnSystem(io);
        inventory = new InteractableInventory();
        abilities = new HashSet<>();
        equipmentOnBody = new EquipmentOnBody();


    }

    public Character(String name, int health, int speed, int level, Position pos, IO io) {
        this.setHealth(health);
        if (speed < 0) {
            throw new IllegalArgumentException("Speed needs to be 0 or more");
        }
        if (io == null) {
            throw new IllegalArgumentException("IO Cannot be null");
        }
        this.setLevel(level);
        this.speed = speed;
        this.setName(name);
        mana = 0;
        this.setPos(pos);
        possibleInteractableActions = STANDARD_CHARACTER_INTERACTABLE_ACTIONS;
        turnSystem = new TurnSystem(io);
        inventory = new InteractableInventory();
        abilities = new HashSet<>();
        equipmentOnBody = new EquipmentOnBody();
    }

    public String getName() {
        return name;
    }

    public Position getPosition() {
        return pos;
    }

    public int getHealth() {
        return health;
    }

    public int getSpeed() {
        return speed;
    }

    public int getMana() {
        return mana;
    }

    public InteractableInventory getInventory() {
        return inventory;
    }

    public EquipmentOnBody getEquipmentOnBody() {
        return equipmentOnBody;
    }

    public TurnSystem getTurnSystem() {
        return turnSystem;
    }

    public Set<Ability> getAbilities() {
        return abilities;
    }

    public int getLevel() {
        return level;
    }


    /**
     * user input
     * the username consists of 2 to 10 characters. If less - invalid username
     * the username can only contain alphanumeric characters and underscores(_)
     * uppercase, lowercase and digits (0-9)
     * the first character must be an alphabetic character
     */
    public void setName(String name) {

        if (name == null) {
            throw new NullPointerException("Error: name can't be null");
        }
        if (name.isEmpty()) {
            throw new IllegalArgumentException("Error: name can't be empty");
        }
        if (matchesPattern(DEFAULT_NAME_PATTERN, name)) this.name = name;
        else {
            throw new IllegalArgumentException("Name doesn't match the pattern");
        }
    }

    private boolean matchesPattern(String pattern, String name) {
        Pattern p = Pattern.compile(pattern);
        Matcher m = p.matcher(name);
        return m.find();
    }

    public void setHealth(int health) {
        if (health < 0) {
            throw new IllegalArgumentException("Health cannot be negative!");
        }
        if (health > MAX_HEALTH)
            this.health = MAX_HEALTH;
        else
            this.health = health;
    }

    public void setPos(Position pos) {
        if (pos == null) {
            throw new IllegalArgumentException("Position cannot be null");
        }
        this.pos = pos;
    }

    public void setMana(int mana) {
        if (mana < 0) {
            throw new IllegalArgumentException("Mana cannot be negative!");
        }
        if (mana > MAX_MANA)
            this.mana = MAX_MANA;
        else
            this.mana = mana;
    }

    public void setLevel(int level) {
        if (level >= 0 && level <= MAX_LEVEL) {
            this.level = level;
        } else if (level < 0) {
            throw new IllegalArgumentException("Level cannot be negative");
        } else {
            this.level = MAX_LEVEL;
        }
    }

    /**
     * Checks if Set is not empty and...
     */
    public void removeAbility(Ability ability) {
        int characterLevel = getLevel();
        if (!abilities.contains(ability) && characterLevel < ability.getRequiredLevel()) {
            abilities.remove(ability);
        }
    }

    /**
     * Adds an ability to the set
     */
    public void addAbility(Ability ability) {
        abilities.add(ability);
    }

    public void increaseMana(int add) {
        if (add <= 0) {
            throw new IllegalArgumentException("Mana increase value can not to be equal zero or be negative!");
        }
        else
            setMana(mana + add);
    }

    public void decreaseMana(int decrease) {
        if (decrease <= 0) {
            throw new IllegalArgumentException("Mana decrease value can not to be equal zero or be negative!");
        }
        else
            setMana(mana - decrease);
    }

    public void increaseHealth(int add) {
        if (add <= 0) {
            throw new IllegalArgumentException("Health increase value can not to be equal zero or be negative!");
        }
        else
            setHealth(health+add);
    }

    public void decreaseHealth(int decrease) {
        if (decrease <= 0) {
            throw new IllegalArgumentException("Health decrease value can not to be equal zero or be negative!");
        }
        else {
            setHealth(health - decrease);
        }
    }

    public boolean isDead() {
        return health == 0;
    }

    public boolean canUseMagic() {
        return mana != 0;
    }


    /**
     * For unequip we check if this equipment is "on body".
     * Then remove that ability which equipment had from list with abilities which character has.
     * And add the equipment to the inventory
     */
    public void unEquip(Equipment equipment) {
        if (equipment != null) {
            if (equipment.equals(equipmentOnBody.getEquipment(equipment.getCanBePlacedInSlot()))) {
                equipmentOnBody.removeEquipment(equipment.getCanBePlacedInSlot());
                inventory.add(equipment);
                removeAbility(equipment.getAbility());
            }
        }
    }

    /**
     * For equip we check if this equipment is not already "on body"
     * and that it is in inventory. Then add that ability which equipment has to list with abilities which character has.
     * And remove the equipment from the inventory
     */
    public void equip(Equipment equipment) {
        if (equipment != null) {
            if (inventory.contains(equipment)) {
                equipmentOnBody.putEquipment(equipment.getCanBePlacedInSlot(), equipment);
                addAbility(equipment.getAbility());
                inventory.remove(equipment);
            }

        }
    }

    @Override
    public Set<InteractableAction> getPossibleActions() {
        return possibleInteractableActions;
    }

    public Object getEffect() {
        return null;
    }
}
