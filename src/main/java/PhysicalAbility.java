public class PhysicalAbility extends Ability{

    PhysicalAbility(String name, int baseDamage, int minimumLevel){
        super(name, baseDamage, AbilityType.PHYSICAL, minimumLevel);
    }

    @Override
    public int calculateDamageOfAbility(Character character) {
        int baseDamage = 5;
        int levelBonus = character.getLevel() * 2;
        int experienceBonus = character.getExperiencePoint() / 10;
        return baseDamage + levelBonus + experienceBonus;
    }

    @Override
    public String toString() {
        return String.valueOf(AbilityType.PHYSICAL);
    }
}
