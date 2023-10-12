public class PhysicalAbility extends Ability{

    private String physicalAbility;
    PhysicalAbility(String name, int baseDamage, String physicalAbility){
        super(name, baseDamage);
        this.physicalAbility = physicalAbility;
    }

    @Override
    public int calculateDamage(Player player) {
        int baseDamage = 5;
        int levelBonus = player.getLevel() * 2;
        int experienceBonus = player.getExperiencePoint() / 10;
        return baseDamage + levelBonus + experienceBonus;
    }

    @Override
    public String typeOfAbility() {
        return physicalAbility;
    }

    @Override
    public String toString() {
        return "Physical";
    }
}
