
public abstract class Ability {

    public String ability;

    abstract public String typeOfAbility();

    public String getTypeOfAbility(){
        return typeOfAbility();
    }

    abstract public String toString();

}
