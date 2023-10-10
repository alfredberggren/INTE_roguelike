import java.util.ArrayList;
import java.util.Set;

public class Equipment extends NonLivingEntity{
    private Effect effect;
    private int damage;

    private Ability ability;
    private Position pos;

    public Equipment(String name, Set<InteractableAction> possibleInteractableActions, Effect effect, int damage) {
        super(name, possibleInteractableActions);
        this.effect = effect;
        this.damage = damage;
        this.ability = ability;
    }

    public String getName(){
        return name;
    }

    public Effect getEffect() {
        return effect;
    }

    public Set<InteractableAction> getPossibleActions() {
        return possibleInteractableActions;
    }

    public Position getPos() {
        return pos;
    }

    public void setPos(Position pos) {
        this.pos = pos;
    }

    public enum Effect {
        SPEED, HEALTH, DAMAGE
    }

    public String getAbility(){
         return ability.getTypeOfAbility();
    }

    public double damageModifier(double percentage) {
        return percentage;
    }

    public String toString() {
        String s = name + " + " + damage + " + " + effect;
        return s;
    }
}
