public class MagicAbility extends Ability{

    private String magicAbility; //representerar typen av magic ability
    private Character character;
    private final int MINIMUM_XP_TO_RETAIN_SPELL = 100; //man glömmer spells
    //ifall man förlorar XP eftersom man går ju inte ner i level
    //bara för att man förlorar ex en fight men XP kan ju minska?

    MagicAbility(String name, int baseDamage, String magicAbility){
        super(name, baseDamage);
        this.magicAbility = magicAbility;
    }

    public void setCharacter(Character character) {
        this.character = character;
    }

    @Override
    public int calculateDamage(Character character) {
        int baseDamage = 10;
        int levelBonus = character.getLevel() * 5;
        int experienceBonus = character.getExperiencePoint() / 10;
        return baseDamage + levelBonus + experienceBonus;
    } //har player här ist för
    // character då player är den som har level/XP, en character är både players eller NPCs
    //annars måste level/XP finnas i character klassen

    public boolean isLearnable(Character character) {
        return true;
    }

    @Override
    public String typeOfAbility() {
        return magicAbility;
    }

    public boolean calculateAffect() {
        if(!character.getSpell() && character.getExperiencePoint() < MINIMUM_XP_TO_RETAIN_SPELL) {
            character.forgetSpell(new Spell("Fire"));
            return false;
        } else {
            return true;
        }
    }

    @Override
    public String toString() {
        return magicAbility;
    }

}
