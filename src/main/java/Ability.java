import java.util.Objects;

/**The Ability class represents an abstract concept of character abilities. It provides a foundation for creating various types of abilities */
public abstract class Ability {

    /**Representing the type of ability*/
    public enum AbilityType {
        PHYSICAL, MAGICAL
    }
    private static final int MINIMUM_LEVEL_REQUIRED_TO_USE_MAGIC = 1;
    private static final int MINIMUM_LEVEL_REQUIRED_TO_USE_PHYSICAL = 0;
    private String name;
    private int baseDamage;
    private AbilityType type;
    private int requiredLevel;
    private String description;

    /**Constructs an Ability object with the specified characteristics, such as name, base damage, minimum level to retain and type of ability */
    protected Ability(String name, int baseDamage, AbilityType type, int requiredLevel, String description) {
        if (baseDamage < 0) {
            throw new IllegalArgumentException("Base damage cannot be negative");
        }
        if (type == null) {
            throw new IllegalArgumentException("Type cannot be null");
        }
        if (requiredLevel < 0) {
            throw new IllegalArgumentException("Required level cannot be negative");
        }
        if (type == AbilityType.PHYSICAL) {
            this.requiredLevel = MINIMUM_LEVEL_REQUIRED_TO_USE_PHYSICAL;
        } else if (type == AbilityType.MAGICAL){
            this.requiredLevel = MINIMUM_LEVEL_REQUIRED_TO_USE_MAGIC;
        }
        this.setName(name);
        this.baseDamage = baseDamage;
        this.type = type;
        this.setDescription(description);
    }

    protected void setName(String name){
        if(name == null) {
            throw new IllegalArgumentException("Name cannot be null");
        } else {
            this.name = name;
        }
    }

    protected String getName() {
        return name;
    }

    protected int getRequiredLevel() {
        return requiredLevel;
    }

    protected String getDescription() {
        return description;
    }

    protected void setDescription(String description) {
        if (description == null) {
            throw new IllegalArgumentException("Description cannot be null");
        } else {
            this.description = description;
        }
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
       return name.equals(ability.name) && type == ability.type;
    }
    @Override
    public int hashCode() {
        return Objects.hash(name, baseDamage, type, requiredLevel);
    }

    /**Returns a string representation of the ability, its type*/
    public abstract String toString();

}
