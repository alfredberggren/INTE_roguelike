import java.util.Set;

public abstract class Ability {

    protected String name;
    protected int baseDamage; //skadan abilityn gör

    public Ability(String name, int baseDamage) {
        this.name = name;
        this.baseDamage = baseDamage;
    }
    abstract public int calculateDamage(Character character);

    abstract public String typeOfAbility();

    public String getTypeOfAbility(){
        return typeOfAbility();
    }

    abstract public String toString();

}
