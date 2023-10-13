
public abstract class Ability {

    protected String name;
    protected int baseDamage;
    protected AbilityType type;
    protected int minimumLevel;

    public Ability(String name, int baseDamage, AbilityType type, int minimumLevel) {
        this.name = name;
        this.baseDamage = baseDamage;
        this.minimumLevel = minimumLevel;
        this.type = type;
    }
    abstract public int calculateDamage(Character character);

    public enum AbilityType {
        PHYSICAL, MAGICAL
    }

    public AbilityType getTypeOfAbility(){
        return type;
    }

    public boolean isLearnable(Character character) {
        return character.getLevel() >= minimumLevel;
    }

    public String toString(String s) {
        return s;
    }

}
