/**The Character class represents a game character that can interact with the game world. It implements Interactable.*/
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Character implements Interactable{
    private static final Set<InteractableAction> STANDARD_CHARACTER_INTERACTABLE_ACTIONS = new HashSet<>(Arrays.asList(
            InteractableAction.TALK,
            InteractableAction.FIGHT)
    );
    private Set<InteractableAction> possibleInteractableActions;


    private String name;
    private static final String NAME_PATTERN = "^[A-Za-z]\\w{1,11}$";

    private int health;
    private int speed;
    private int mana; 
    protected int level;
    private Position pos;
    private boolean isDead = false;
    private MagicAbility magicAbility;
    private boolean spell;
    private Set<Ability> abilities = new HashSet<>();
    private InteractableInventory inventory = new InteractableInventory();
    private boolean canUseMagic = true; // remove
    private EquipmentOnBody equipmentOnBody;
    private TurnSystem turnSystem;

    public Character(String name, int health, int speed, IO io){
        if (health < 0 || speed < 0) {
            throw new IllegalArgumentException("Speed and health needs to be 0 or more");
        }
        this.name = name;
        this.health = health;
        this.speed = speed;
        mana = 100;
        pos = new Position(0, 0);
        if(health > 0) {
            isDead = false;
        }
        possibleInteractableActions = STANDARD_CHARACTER_INTERACTABLE_ACTIONS;
        turnSystem = new TurnSystem(io);
    }

    public Character(String name, int health, int speed, Position pos, IO io){
        if (health < 0 || speed < 0) {
            throw new IllegalArgumentException("Speed and health needs to be 0 or more");
        }
        this.name = name;
        this.health = health;
        this.speed = speed;
        this.pos = pos;
        if(health > 0) {
            isDead = false;
        }
        possibleInteractableActions = STANDARD_CHARACTER_INTERACTABLE_ACTIONS;
        turnSystem = new TurnSystem(io);
    }

    public void setName(String name) {
        // user input
        //the username consists of 2 to 10 characters. If less - invalid username
        //the username can only contain alphanumeric characters and underscores(_)
        //uppercase, lowercase and digits (0-9)
        //the first character must be an alphabetic character
        if (name == null){
            throw new NullPointerException("Error: name can't be null");
        }
        if (name.isEmpty()){
            throw new IllegalArgumentException("Error: name can't be empty");
        }
        if(!matchesPattern(NAME_PATTERN, name)){
            throw new IllegalArgumentException("Name doesn't match the pattern");
        }
        else
            this.name = name;
    }

    boolean matchesPattern(String pattern, String name){
        Pattern p = Pattern.compile(pattern);
        Matcher m = p.matcher(name);
        return m.find();
    }

    public String getName(){return name;}

    public Position getPosition() {return pos;}
    public int getHealth() {return health;}
    public int getSpeed() {return speed;}

    public int getMana() {return mana;}

    public InteractableInventory getInventory() {
        return inventory;
    }
    public EquipmentOnBody getEquipmentOnBody(){
        return equipmentOnBody;
    }

    public TurnSystem getTurnSystem(){return turnSystem}

    public boolean getSpell() {
        return spell;
    }

    /**Checks if Arraylist is not empty and if so removes the spell*/
    public void forgetAbility(MagicAbility spell) {
        if(!abilities.isEmpty()) {
            abilities.remove(spell);
        }
    }

    /**Adds a spell to the Arraylist*/
    public void addAbility(Ability ability) {
        abilities.add(ability);
    }
    public Set<Ability> getAbilities() {
        return abilities;
    }

    /*public MagicAbility getMagicAbility() {
        return magicAbility;
    }*/
    /*public void setMagicAbility(MagicAbility magicAbility) {
        this.magicAbility = magicAbility;
    }*/

    public void setHealth(int health) {
        if(health<0){
            throw new IllegalArgumentException("Health cannot be negative!");
        }
        if(health==0){
            isDead = true;
        }
        this.health = health;}

    public void setPos(Position pos) {this.pos = pos;}

    public void setMana(int mana) {this.mana = mana;}

    public void increaseMana(int add){
        setMana(mana += add);
    }

    public void decreaseMana(int decrease){
        int result = mana -= decrease;
        if(result<=0){
            setMana(0);
            canUseMagic = false;
        }
        setMana(result);
    }

    public void increaseHealth(int add){
        int result = health + add;
        setHealth(result);
    }

    public void decreaseHealth(int decrease) {
        int result = health - decrease;
        if(result<=0) {
            isDead = true;
            setHealth(0);
        }else {
            setHealth(result);
        }
    }

    public boolean isDead() {
        return health <= 0;
    }
    public boolean canUseMagic(){
        return mana == 0;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public int getLevel() {
        return level;
    }

    public void unEquip(EquipmentSlot slot){
        if(equipmentOnBody.slotContainsEquipment(slot)){
            Equipment e = equipmentOnBody.getEquipment(slot);
            e.setPos(getPosition());
            equipmentOnBody.removeEquipment(slot);
            inventory.remove(e);
        }
    }

    public void equip(EquipmentSlot slot, Equipment equipment) {
        //testa!!
        if (!equipmentOnBody.slotContainsEquipment(slot) && inventory.contains(equipment))
            equipmentOnBody.putEquipment(slot, equipment);
    }


    @Override
    public Set<InteractableAction> getPossibleActions() {
        return possibleInteractableActions;
    }
}
