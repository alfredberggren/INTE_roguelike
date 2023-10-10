public class MagicAbility extends Ability{

    private String magicAbility;

    MagicAbility(String magicAbility){
        this.magicAbility = magicAbility;
    }
    @Override
    public String typeOfAbility() {
        return magicAbility;
    }

}
