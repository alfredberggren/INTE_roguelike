public class MagicAbility extends Ability{

    private String magicAbility;

    MagicAbility(String name, int baseDamage, String magicAbility){
        super(name, baseDamage);
        this.magicAbility = magicAbility;
    }

    @Override
    public int calculateDamage(int playerLevel, int playerExperience) {
        return 0;
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
