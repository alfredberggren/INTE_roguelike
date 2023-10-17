import java.util.Objects;

/**The Ability class represents an abstract concept of character abilities. It provides a foundation for creating various types of abilities */
public abstract class Ability {

    protected String name;
    protected int baseDamage;
    protected AbilityType type;
    protected int minimumLevel;

    /**Constructs an Ability object with the specified characteristics, such as name, base damage, minimum level to retain and type of ability */
    public Ability(String name, int baseDamage, AbilityType type, int minimumLevel) {
        this.name = name;
        this.baseDamage = baseDamage;
        this.minimumLevel = minimumLevel;
        this.type = type;
    }

    /**Calculates the damage inflicted by this ability when used by a character. */
    abstract public int calculateDamageOfAbility(Character character, Player player);

    /**Representing the type of ability*/
    public enum AbilityType {
        PHYSICAL, MAGICAL
    }

    /**Gets the type of ability*/
    public AbilityType getTypeOfAbility(){
        return type;
    }

    /**Checks whether the ability is learnable by a character based on their level*/
    public boolean isLearnable(Character character) {
        return character.getLevel() >= minimumLevel;
    }

    @Override
    public boolean equals(Object o) {
       if (this == o) {
           return true;
       }
       if (o == null || getClass() != o.getClass()) {
           return false;
       }
       Ability ability = (Ability) o;
       return baseDamage == ability.baseDamage && minimumLevel == ability.minimumLevel &&
               name.equals(ability.name) && type == ability.type;
    }
    @Override
    public int hashCode() {
        return Objects.hash(name, baseDamage, type, minimumLevel);
    }

    /**Returns a string representation of the ability, its type*/
    abstract public String toString();

}
