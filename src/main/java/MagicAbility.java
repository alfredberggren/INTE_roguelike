public class MagicAbility extends Ability{

    private String magicAbility;
    private Character character = new Character(100,10,new Position(1, 1));

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
    public String affectAbility() {
        if(!character.getSpell()) {
            return "Forgotten spell";
        } else {
            return "The death spell";
        }
    }

    public String calculateAffect() {
        return "You have forgotten spell";
    }

    @Override
    public String toString() {
        return magicAbility;
    }

}
