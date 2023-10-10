import java.util.ArrayList;

public class Character {

    protected String name;
    private int health;
    private int speed;
    private Position pos;
    private boolean isDead = true;
    private ArrayList <Ability> abilities;
    private EquipmentInventory equipments;
    private PhysicalAbility physicalAbility;
    private MagicAbility magicAbility;

    public Character(String name, int health, int speed){
        if (health < 0 || speed < 0) {
            throw new IllegalArgumentException("Speed and health needs to be 0 or more");
        }
        this.name = name;
        this.health = health;
        this.speed = speed;
        this.pos = new Position(0, 0);
        if(health > 0) {
            isDead = false;
        }
    }

    public Character(String name, int health, int speed, Position pos){
        if (health < 0 || speed < 0) {
            throw new IllegalArgumentException("Speed and health needs to be 0 or more");
        }
        this.name = name;
        this. health = health;
        this.speed = speed;
        this.pos = pos;
    }

    public String getName(){return name;}
    public Position getPosition() {return pos;}
    public int getHealth() {return health;}
    public int getSpeed() {return speed;}
    public ArrayList<Ability> getAbilities() {return abilities;}
    public EquipmentInventory getEquipments() {return equipments;}
    public MagicAbility getMagicAbility() {return magicAbility;}
    public PhysicalAbility getPhysicalAbility() {return physicalAbility;}
    public void setHealth(int health) {this.health = health;}
    public void setPos(Position pos) {this.pos = pos;}

    public int increaseHealth(int add){
        int result = health + add;
        setHealth(result);
        return result;
    }

    public int decreaseHealth(int decrease) {
        int result = health - decrease;
        if(result<=0) {
            isDead = true;
            setHealth(0);
            return 0;
        }
        setHealth(result);
        return result;
    }

    public boolean isDead() {
        return isDead;
    }
}
