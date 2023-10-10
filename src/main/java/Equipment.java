import java.util.ArrayList;
import java.util.Objects;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Equipment equipment = (Equipment) o;
        return name.equals(equipment.getName()) && damage == equipment.damage && effect == equipment.effect && Objects.equals(ability, equipment.ability) && Objects.equals(pos, equipment.pos);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, effect, damage, ability, pos);
    }

    @Override
    public String toString() {
        String s = name + " + " + damage + " + " + effect;
        return s;
    }
}
