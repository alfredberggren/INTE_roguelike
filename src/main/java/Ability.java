
public abstract class Ability {

    protected String name;
    protected int baseDamage;

    public Ability(String name, int baseDamage) {
        this.name = name;
        this.baseDamage = baseDamage;
    }
    abstract public int calculateDamage(Player player);

    abstract public String typeOfAbility();

    public String getTypeOfAbility(){
        return typeOfAbility();
    }

    abstract public String toString();

}
