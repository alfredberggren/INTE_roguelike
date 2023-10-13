public class MagicAbility extends Ability{
    private Character character;
    //ifall man förlorar XP eftersom man går ju inte ner i level
    //bara för att man förlorar ex en fight men XP kan ju minska?

    MagicAbility(String name, int baseDamage, int minimumLevel){
        super(name, baseDamage, AbilityType.MAGICAL, minimumLevel);
    }

    public void setCharacter(Character character) {
        this.character = character;
    }

    @Override
    public int calculateDamageOfAbility(Character character) {
        int baseDamage = 10;
        int levelBonus = character.getLevel() * 5;
        int experienceBonus = character.getExperiencePoint() / 10;
        return baseDamage + levelBonus + experienceBonus;
    }

    public boolean calculateImpactOnAbility() {
        int MINIMUM_XP_TO_RETAIN_SPELL = 100;
        if(!character.getSpell() && character.getExperiencePoint() < MINIMUM_XP_TO_RETAIN_SPELL) {
            character.forgetSpell(new Spell("Fire"));
            return false;
        } else {
            return true;
        }
    }

    @Override
    public String toString() {
        return String.valueOf(AbilityType.MAGICAL);
    }

}
