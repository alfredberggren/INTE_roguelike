/**The PhysicalAbility class extends the abstract Ability class and represents a specific type of physical ability*/
public class PhysicalAbility extends Ability{

    private static final int BASE_DAMAGE_FOR_PHYSICAL = 5;
    private static final int LEVEL_BONUS_FOR_PHYSICAL = 2;
    private static final int XP_BONUS_FOR_PHYSICAL = 10;
    private static final int DEFAULT_XP = 0;

    /**Constructs an Ability object with the specified characteristics*/
    public PhysicalAbility(String name, int baseDamage, int requiredLevel, String description){
        super(name, baseDamage, AbilityType.PHYSICAL, requiredLevel, description);
    }

    /** {@inheritDoc} Calculates the damage inflicted by this specific physical ability*/
    public int calculateDamageOfPhysicalAbility(Character character) {
        int baseDamage = BASE_DAMAGE_FOR_PHYSICAL;
        int levelBonus = character.getLevel() * LEVEL_BONUS_FOR_PHYSICAL;
        int experienceBonus = DEFAULT_XP;
        if(character instanceof Player player) {
            experienceBonus = player.getAmountOfExperience() / XP_BONUS_FOR_PHYSICAL;
        }
        return baseDamage + levelBonus + experienceBonus;
    }

    /**{@inheritDoc}*/
    @Override
    public String toString() {
        return String.valueOf(AbilityType.PHYSICAL);
    }
}
