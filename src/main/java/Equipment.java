import java.util.Set;

public class Equipment extends NonLivingEntity{
    private Effect effect;
    private int damage;

    private Ability ability;

    public Equipment(String name, Set<Action> possibleActions, Effect effect, int damage, Ability ability) {
        super(name, possibleActions);
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

    public Set<Action> getPossibleActions() {
        return possibleActions;
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
