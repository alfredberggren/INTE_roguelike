/**The MagicAbility class extends the abstract Ability class
 * and represents a specific type of magical ability*/
public class MagicAbility extends Ability{
    private Character character;

    /**Constructs an Ability object with the specified characteristics*/
    MagicAbility(String name, int baseDamage, int minimumLevel){
        super(name, baseDamage, AbilityType.MAGICAL, minimumLevel);
    }
    /**Sets the character associated with this magical ability*/
    public void setCharacter(Character character) {
        this.character = character;
    }
    /** {@inheritDoc}
     * Calculates the damage inflicted by this specific magical ability*/
    @Override
    public int calculateDamageOfAbility(Character character) {
        int baseDamage = 10;
        int levelBonus = character.getLevel() * 5;
        int experienceBonus = character.getExperiencePoint() / 10;
        return baseDamage + levelBonus + experienceBonus;
    }
    /**Calculates the impact on the character's spellcasting ability,
     * if the character does not meet the required conditions to
     * retain the spell, the spell is forgotten*/
    public boolean calculateImpactOnAbility() {
        int MINIMUM_XP_TO_RETAIN_SPELL = 100;
        if(!character.getSpell() && character.getExperiencePoint() < MINIMUM_XP_TO_RETAIN_SPELL) {
            character.forgetSpell(new Spell("Fire"));
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
