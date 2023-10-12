import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Set;

public class Character implements Interactable{

    //variabel för turnsystem

    //private String name;
    private int health;
    private int speed;
    private Position pos;
    private boolean isDead = false;
    protected Set<Ability> possibleAbilities;
    private EquipmentInventory equipments;
    private PhysicalAbility physicalAbility;
    private MagicAbility magicAbility;
    private boolean spell;
    private ArrayList<Spell> knownSpells = new ArrayList<>();

    public Character(int health, int speed){
        if (health < 0 || speed < 0) {
            throw new IllegalArgumentException("Speed and health needs to be 0 or more");
        }

        this.health = health;
        this.speed = speed;
        this.magicAbility = new MagicAbility("Hands",1,"No Magic"); //standard magisk förmåga
        this.pos = new Position(0, 0);
        //health = 100;     //fixa
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
    }


    public Position getPosition() {return pos;}
    public int getHealth() {return health;}
    public int getSpeed() {return speed;}
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

    public EquipmentInventory getEquipments() {return equipments;}
    public MagicAbility getMagicAbility() {
        return magicAbility;
    }
    public void setMagicAbility(MagicAbility magicAbility) {
        this.magicAbility = magicAbility;
    }
    public PhysicalAbility getPhysicalAbility() {return physicalAbility;}
    public void setHealth(int health) {this.health = health;}
    public void setPos(Position pos) {this.pos = pos;}

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

    @Override
    public Set<InteractableAction> getPossibleActions() {
        return null;
    }
}
