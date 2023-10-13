import java.util.Set;
import java.util.ArrayList;
import java.util.Objects;

public class Equipment extends NonLivingEntity{
    private Effect effect;
    private int damage;
    private Ability ability;
    private Position pos;
    private double damageBar;

    public Equipment(String name, Set<InteractableAction> possibleActions, Effect effect, int damage, Ability ability) {
        super(name, possibleActions);
        this.effect = effect;
        this.damage = damage;
        this.ability = ability;
        this.pos = new Position(0,0);
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
        SPEED, HEALTH, DAMAGE, ARMOR
    }

    public enum Armor {
        HELMET, CHEST_ARMOR, LEGGING, BOOTS
    }

    public String getAbility(){
         return ability.getTypeOfAbility();
    }

    public void damageModifier(double damageBar) {
        double decreaseBy = 10;
        if(damageBar >= 10 && damageBar <= 100) {
            damageBar -= decreaseBy;
            if(damageBar <= 0) {
                setDamageOnEquipment(0);
            }
        }
        setDamageOnEquipment(damageBar);
        if(damageBar == 0) {
            throw new RuntimeException("Equipment has been destroyed!");
        }
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
