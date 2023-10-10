import java.util.Set;
import java.util.ArrayList;
import java.util.Objects;

public class Equipment extends NonLivingEntity{
    private Effect effect;
    private int damage;
    private Ability ability;
    private double damageBar;

    public Equipment(String name, Set<InteractableAction> possibleActions, Effect effect, int damage, Ability ability) {
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

    public Set<InteractableAction> getPossibleActions() {
        return possibleInteractableActions;
    }

    public enum Effect {
        SPEED, HEALTH, DAMAGE
    }

    public String getAbility(){
         return ability.getTypeOfAbility();
    }

    public double damageModifier(double damageBar) {
        double decreaseBy = 10;
        if(10 <= damageBar || damageBar <= 100) {
            setDamageOnEquipment(damageBar - decreaseBy);
            return damageBar;
        }
        setDamageOnEquipment(damageBar);
        return damageBar;
    }

    public void setDamageOnEquipment(double damageBar){
        this.damageBar = damageBar;
    }

    public double getDamageOnEquipment(){
        return damageBar;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Equipment equipment = (Equipment) o;
        return name.equals(equipment.getName()) && damage == equipment.damage && effect == equipment.effect && Objects.equals(ability, equipment.ability);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, effect, damage, ability);
    }

    @Override
    public String toString() {
        String s = name + " +" + damage + "% " + effect;
        return s;
    }
}
