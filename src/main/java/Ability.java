
public abstract class Ability {

    protected String name;
    protected int baseDamage;
    protected int minimumLevel;

    public Ability(String name, int baseDamage, int minimumLevel) {
        this.name = name;
        this.baseDamage = baseDamage;
        this.minimumLevel = minimumLevel;
    }
    abstract public int calculateDamage(Character character);

    abstract public String typeOfAbility();

    public String getTypeOfAbility(){
        return typeOfAbility();
    }

    public boolean isLearnable(Character character) {
        return character.getLevel() >= minimumLevel;
    }

    public String toString(String s) {
        return s;
    }

}
