import java.util.ArrayList;

public class Equipment {

    private String name;
    private Effect effect;
    private int damage;



    public Equipment(String name, Effect effect, int damage) {
        this.name = name;
        this.effect = effect;
        this.damage = damage;
    }

    public String getName(){
        return name;
    }

    public Effect getEffect() {
        return effect;
    }

    public enum Effect {
        SPEED, HEALTH, DAMAGE
    }

    public String toString() {
        String s = name + " + " + damage + " + " + effect;
        return s;
    }


}
