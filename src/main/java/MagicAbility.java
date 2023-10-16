import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

/**The MagicAbility class extends the abstract Ability class and represents a specific type of magical ability*/
public class MagicAbility extends Ability{
    private Character character;
    private Player player;
    private String description;
    private int castingTime;
    private int coolDown;

    /**Constructs an Ability object with the specified characteristics*/
    MagicAbility(String name, int baseDamage, int minimumLevel, String description, int castingTime, int coolDown) {
        super(name, baseDamage, AbilityType.MAGICAL, minimumLevel);
        this.description = description;
        this.castingTime = castingTime;
        this.coolDown = coolDown;
    }

    /**Sets the character associated with this magical ability*/
    public void setCharacter(Character character) {
        this.character = character;
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
    public int calculateDamageOfAbility(Character character) {
        int baseDamage = 10;
        int levelBonus = character.getLevel() * 5;
        int experienceBonus = player.getExperiencePoint() / 10;
        return baseDamage + levelBonus + experienceBonus;
    }

    /**Calculates the impact on the character's spellcasting ability, if the character does not meet the required conditions to retain the spell, the spell is forgotten*/
    public boolean calculateImpactOnAbility() {
        int minimumLevelRetain = 10; //om man dör så förlorar man spells
        if(!character.getSpell() && player.getExperiencePoint() < minimumLevelRetain) {
            character.forgetAbility(new MagicAbility("Fire", 10,1, "Shoots fire",1,2));
            return false;
        } else {
            return true;
        }
    }

    /**{@inheritDoc}*/
    @Override
    public String toString() {
        return String.valueOf(AbilityType.MAGICAL);
    }

}
