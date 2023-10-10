import java.util.ArrayList;
import java.util.Set;

public class Equipment extends NonLivingEntity{

    private String name;
    private Effect effect;
    private int damage;



    public Equipment(String name, Set<Action> possibleActions, Effect effect, int damage) {
        super(name, possibleActions);
        this.effect = effect;
        this.damage = damage;
    }

    public String getName(){
        return name;
    }

    public Effect getEffect() {
        return effect;
    }

    public Set<Action> getPossibleActions() {
        return possibleActions;
    }

    public enum Effect {
        SPEED, HEALTH, DAMAGE
    }

    public String toString() {
        String s = name + " + " + damage + " + " + effect;
        return s;
    }


}
