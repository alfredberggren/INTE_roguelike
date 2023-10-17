import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

/**The MagicAbility class extends the abstract Ability class and represents a specific type of magical ability*/
public class MagicAbility extends Ability{

    private Set<Ability> abilities = new HashSet<>();

    private String description;
    private int castingTime;
    private int coolDown;

    /**Constructs an Ability object with the specified characteristics*/
    MagicAbility(String name, int baseDamage, int minimumLevel, String description, int castingTime, int coolDown) {
        super(name, baseDamage, AbilityType.MAGICAL, minimumLevel >= 1 ? minimumLevel:1);
        this.description = description;
        this.castingTime = castingTime >= 1 ? castingTime:1;
        this.coolDown = coolDown >= 1 ? coolDown:1;

    }

    public String getName() {
        return name;
    }

    public void setName(String name){
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getCastingTime() {
        return castingTime;
    }

    public void setCastingTime(int castingTime){
        this.castingTime = castingTime;
    }

    public int getCoolDown() {
        return coolDown;
    }

    public void setCoolDown(int coolDown) {
        this.coolDown = coolDown;
    }

    @Override
    public boolean equals(Object o) {
        if(this == o) {
            return true;
        }
        if(o == null || getClass() != o.getClass()) {
            return false;
        }
        MagicAbility spell = (MagicAbility) o;
        return Objects.equals(name, spell.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }

    /** {@inheritDoc} Calculates the damage inflicted by this specific magical ability*/
    @Override
    public int calculateDamageOfAbility(Character character, Player player) {
        int baseDamage = 10;
        int levelBonus = character.getLevel() * 5;
        int experienceBonus = player.getExperiencePoint() / 10;
        return baseDamage + levelBonus + experienceBonus;
    }

    /**Calculates the impact on the character's spell casting ability, if the character does not meet the required conditions to retain the spell, the spell is forgotten*/
    public boolean calculateImpactOnAbility(Character character) {
        int characterLevel = character.getLevel();
        if(!containMagic() && characterLevel < minimumLevel) {
            character.removeAbility(new MagicAbility("Fire", 10,1, "Shoots fire",1,2));
            return true;
        } else {
            return false;
        }
    }

    /**Checks if magical ability is in the Set*/
    private boolean containMagic() {
        if(abilities.contains(name)) {
            return true;
        } else {
            return false;
        }
    }

    /**{@inheritDoc}*/
    @Override
    public String toString() {
        return String.valueOf(AbilityType.MAGICAL);
    }

}
