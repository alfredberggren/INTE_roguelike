public class PhysicalAbility extends Ability{

    private String physicalAbility;
    PhysicalAbility(String name, int baseDamage, String physicalAbility){
        super(name, baseDamage);
        this.physicalAbility = physicalAbility;
    }

    @Override
    public int calculateDamage(int playerLevel, int playerExperience) {
        return 0;
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
