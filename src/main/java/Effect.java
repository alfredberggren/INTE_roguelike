/**Represents the possible effects of the equipment*/
public enum Effect {

    SPEED(100), HEALTH(100), DAMAGE(50), NONE(0);

    private int durability;

    Effect(int i) {
    }

    protected int durabilityOfEffect() {
        return durability;
    }



}
