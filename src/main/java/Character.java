import java.util.ArrayList;
import java.util.Set;

public class Character {
    public CharacterType type;

    private String name;
    private int health;
    private int speed;
    private int level;
    private int experience;
    private Position pos;
    private boolean isDead = true;
    protected Set<Ability> possibleAbilities;
    private EquipmentInventory equipments;
    private PhysicalAbility physicalAbility;
    private MagicAbility magicAbility;

    public Character(CharacterType type, String name, int health, int speed){
        if (health < 0 || speed < 0) {
            throw new IllegalArgumentException("Speed and health needs to be 0 or more");
        }
        this.type = type;
        this.name = name;
        this.health = health;
        this.speed = speed;
        this.magicAbility = new MagicAbility("Hands",1,"No Magic"); //standard magisk förmåga
        this.pos = new Position(0, 0);
        if(health > 0) {
            isDead = false;
        }
    }

    public Character(CharacterType type, String name, int health, int speed, Position pos){
        if (health < 0 || speed < 0) {
            throw new IllegalArgumentException("Speed and health needs to be 0 or more");
        }
        this.type = type;
        this.name = name;
        this.health = health;
        this.speed = speed;
        this.pos = pos;
    }

    public CharacterType getType() {return type;}
    public String getName(){return name;}
    public Position getPosition() {return pos;}
    public int getHealth() {return health;}
    public int getSpeed() {return speed;}

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

    public void setLevel(int level) {
        this.level = level;
    }

    public int getLevel() {
        return level;
    }

    public void setExperience(int experience) {
        this.experience = experience;
    }

    public int getExperience() {
        return experience;
    }

    public void increaseExperience(int experiencePoints) {
        experience += experiencePoints;
        checkLevelUp();
    }

    public void checkLevelUp() {
        //kolla om spelaren ska gå upp i nivå baserat på erfarenhet
        int experiencePerLevelUp = 100;
        while(experience >= experiencePerLevelUp && level < 10) {
            experience -= experiencePerLevelUp;
            level++;
            System.out.println("Congratulations! You've reached level " + level + "!");
        }
    }

    public boolean isDead() {
        return isDead;
    }
}
