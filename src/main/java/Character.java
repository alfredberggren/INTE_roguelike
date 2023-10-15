/**The Character class represents a game character that can interact with the game world. It implements Interactable.*/
import java.util.ArrayList;
import java.util.Set;

public class Character implements Interactable{

    //variabel för turnsystem

    //private String name;
    private int health;
    private int speed;
    private int mana;
    private int experiencePoint;
    private int level;
    private Position pos;
    private boolean isDead = false;
    protected Set<Ability> possibleAbilities;
    private PhysicalAbility physicalAbility;
    private MagicAbility magicAbility;
    private boolean spell;
    private ArrayList<Spell> knownSpell = new ArrayList<>();
    private boolean canUseMagic = true;

    public Character(int health, int speed, int experiencePoint){
        if (health < 0 || speed < 0) {
            throw new IllegalArgumentException("Speed and health needs to be 0 or more");
        }
        this.health = health;
        this.speed = speed;
        this.experiencePoint= experiencePoint;
        mana = 100;
        pos = new Position(0, 0);
        if(health > 0) {
            isDead = false;
        }
    }

    public Character(int health, int speed, Position pos){
        if (health < 0 || speed < 0) {
            throw new IllegalArgumentException("Speed and health needs to be 0 or more");
        }
        this.health = health;
        this.speed = speed;
        this.pos = pos;
        experiencePoint=0;
        if(health > 0) {
            isDead = false;
        }
    }


    public Position getPosition() {return pos;}
    public int getHealth() {return health;}
    public int getSpeed() {return speed;}

    public int getMana() {return mana;}

    public boolean getSpell() {
        return spell;
    }

    /**Checks if Arraylist is not empty and if so removes the spell*/
    public void forgetSpell(Spell spell) {
        if(!knownSpell.isEmpty()) {
            knownSpell.remove(spell);
        }
    }

    /**Adds a spell to the Arraylist*/
    public void addSpell(Spell spell) {
        knownSpell.add(spell);
    }
    public ArrayList<Spell> getKnownSpell() {
        return knownSpell;
    }

    public MagicAbility getMagicAbility() {
        return magicAbility;
    }
    public void setMagicAbility(MagicAbility magicAbility) {
        this.magicAbility = magicAbility;
    }

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

    /**Checks if the character has gained enough experience points to level up. If the character's experience points are greater than or equal to the experience required for the next level and the character's level is less than 10, the character levels up*/
    public void checkLevelUp() {
        int experiencePerLevelUp = 100;
        while(experiencePoint >= experiencePerLevelUp && level < 10) {
            experiencePoint -= experiencePerLevelUp;
            level++;
            //this.magicAbility = new MagicAbility("New magic ability", 10, "magic ability");
            System.out.println("Congratulations! You've reached level " + level + "!");
        }
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public int getLevel() {
        return level;
    }
    public int getExperiencePoint() {
        return experiencePoint;
    }

    public void setExperiencePoint(int experiencePoint) {
        this.experiencePoint = experiencePoint;
    }

    /**Increases the character's experience points*/
    public void increaseXP(int add){
        int result = experiencePoint + add;
        setExperiencePoint(result);
    }

    /**Decreases the character's experience points*/
    public void decreaseXP(int decrease){
        int result = experiencePoint - decrease;
        if(result<0){
            setExperiencePoint(0);
        }else {
            setExperiencePoint(result);
        }
    }

    @Override
    public Set<InteractableAction> getPossibleActions() {
        return null;
    }
}
