public class MagicAbility extends Ability{

    private String magicAbility;

    MagicAbility(String name, int baseDamage, String magicAbility){
        super(name, baseDamage);
        this.magicAbility = magicAbility;
    }

    @Override //ska lägga till bonus för Magic är starkare än physical
    public int calculateDamage(Character character) {
        int baseDamage = 10;
        int levelBonus = character.getLevel() * 5;
        int experienceBonus = character.getExperience() / 10;
        int totalDamage = baseDamage + levelBonus + experienceBonus;
        return totalDamage;
    }

    @Override
    public String typeOfAbility() {
        return magicAbility;
    }

    @Override
    public String toString() {
        return magicAbility;
    }

}
