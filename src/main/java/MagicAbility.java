public class MagicAbility extends Ability{

    private String magicAbility;

    MagicAbility(String name, int baseDamage, String magicAbility){
        super(name, baseDamage);
        this.magicAbility = magicAbility;
    }

    @Override
    public int calculateDamage(Player player) {
        int baseDamage = 10;
        int levelBonus = player.getLevel() * 5;
        int experienceBonus = player.getExperiencePoint() / 10;
        return baseDamage + levelBonus + experienceBonus;
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
