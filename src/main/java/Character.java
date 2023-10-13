import java.util.ArrayList;
import java.util.List;
import java.util.Random;
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
    private ArrayList<Spell> knownSpells = new ArrayList<>();
    private boolean canUseMagic = true;

    public Character(int health, int speed, int experiencePoint){
        if (health < 0 || speed < 0) {
            throw new IllegalArgumentException("Speed and health needs to be 0 or more");
        }
        this.health = health;
        this.speed = speed;
        experiencePoint=0;
        mana = 100;
        this.pos = new Position(0, 0);
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

    public void forgetSpell(Spell spell) {
        if(!knownSpells.isEmpty()) {
            knownSpells.remove(spell);
        }
    }

    public void addSpell(Spell spell) {
        knownSpells.add(spell);
    }

    public Set<Ability> getPossibleAbilities() {return possibleAbilities;}

    public MagicAbility getMagicAbility() {
        return magicAbility;
    }
    public void setMagicAbility(MagicAbility magicAbility) {
        this.magicAbility = magicAbility;
    }
    public PhysicalAbility getPhysicalAbility() {return physicalAbility;}
    public void setHealth(int health) {this.health = health;}
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

    public int increaseHealth(int add){
        int result = health + add;
        setHealth(result);
        return result;
    }

    public void decreaseHealth(int decrease) {
        int result = health - decrease;
        if(result<=0) {
            isDead = true;

            //nollställa allt
            setHealth(0);
        }else {
            setHealth(result);
        }
    }

    public boolean isDead() {
        return isDead;
    }
    public boolean canUseMagic(){return canUseMagic;}
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

    public void increaseXP(int add){
        int result = experiencePoint + add;
        setExperiencePoint(result);
    }

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
