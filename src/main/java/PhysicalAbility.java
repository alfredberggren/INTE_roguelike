public class PhysicalAbility extends Ability{

    private String physicalAbility;

    PhysicalAbility(String physicalAbility){
        this.physicalAbility = physicalAbility;
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
