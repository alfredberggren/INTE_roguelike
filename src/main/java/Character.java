import java.util.ArrayList;

public class Character {

    private String name;
    private int health;
    private int speed;
    private Position pos;
    private boolean isDead = true;
    private ArrayList <Ability> abilities;
    private EquipmentInventory equipments;
    private PhysicalAbility physicalAbility;
    private MagicAbility magicAbility;

    public Character(String name, int health, int speed){
        if (speed < 0 || health < 0 || speed < 0) {
            throw new IllegalArgumentException("Speed, health and speed needs to be 0 or more");
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
        if (speed < 0 || health < 0 || speed < 0) {
            throw new IllegalArgumentException("Speed, health and speed needs to be 0 or more");
        }
        this.name = name;
        this. health = health;
        this.speed = speed;
        this.pos = pos;
    }

    public String getName(){
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

    public ArrayList<Ability> getAbilities() {
        return abilities;
    }

    public EquipmentInventory getEquipments() {
        return equipments;
    }

    public MagicAbility getMagicAbility() {
        return magicAbility;
    }

    public PhysicalAbility getPhysicalAbility() {
        return physicalAbility;
    }

    public boolean isDead() {
        return isDead;
    }
}
