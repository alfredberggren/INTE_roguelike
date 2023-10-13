public class PhysicalAbility extends Ability{

    private String physicalAbility; //representerar typen av physical ability
    PhysicalAbility(String name, int baseDamage, String physicalAbility){
        super(name, baseDamage);
        this.physicalAbility = physicalAbility;
    }

    @Override
    public int calculateDamage(Character character) {
        int baseDamage = 5;
        int levelBonus = character.getLevel() * 2;
        int experienceBonus = character.getExperiencePoint() / 10;
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
