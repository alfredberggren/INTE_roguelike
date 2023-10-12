public class MagicAbility extends Ability{

    private String magicAbility;
    private Character character;
    private Player player;
    private int MINIMUM_XP_TO_RETAIN_SPELL = 100;

    MagicAbility(String name, int baseDamage, String magicAbility){
        super(name, baseDamage);
        this.magicAbility = magicAbility;
    }

    MagicAbility(String name, int baseDamage, String magicAbility, Character character, Player player){
        super(name, baseDamage);
        this.magicAbility = magicAbility;
        this.character = character;
        this.player = player;
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
        if(character.getSpell() && player.getExperiencePoint() < MINIMUM_XP_TO_RETAIN_SPELL) {
            character.forgetSpell(new Spell("Fire"));
            return "You have forgotten a spell due to losing experience points";
        } else {
            return "You have not forgotten any spells";
        }
    }



    @Override
    public String toString() {
        return magicAbility;
    }

}
