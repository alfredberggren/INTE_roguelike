/**The PhysicalAbility class extends the abstract Ability class and represents a specific type of physical ability*/
public class PhysicalAbility extends Ability{

    /**Constructs an Ability object with the specified characteristics*/
    PhysicalAbility(String name, int baseDamage, int minimumLevel){
        super(name, baseDamage, AbilityType.PHYSICAL, minimumLevel);
    }

    /** {@inheritDoc} Calculates the damage inflicted by this specific physical ability*/
    @Override
    public int calculateDamageOfAbility(Character character) {
        int baseDamage = 5;
        int levelBonus = character.getLevel() * 2;
        int experienceBonus = character.getExperiencePoint() / 10;
        return baseDamage + levelBonus + experienceBonus;
    }

    /**{@inheritDoc}*/
    @Override
    public String toString() {
        return String.valueOf(AbilityType.PHYSICAL);
    }
}
