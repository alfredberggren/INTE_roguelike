import java.util.Set;

public abstract class Ability {

    protected String name;
    protected int baseDamage; //skadan abilityn gör

    public Ability(String name, int baseDamage) {
        this.name = name;
        this.baseDamage = baseDamage;
    }
    abstract public int calculateDamage(Player player);

    abstract public String typeOfAbility();

    abstract public String affectAbility();

    public String getTypeOfAbility(){
        return typeOfAbility();
    }

    abstract public String toString();

}
