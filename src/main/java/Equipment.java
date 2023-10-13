import java.util.*;

public class Equipment extends NonLivingEntity{
    private Effect effect;
    private int damage;
    private Ability ability;
    private Position pos;
    private double damageBar;
    private static final Set<InteractableAction> STANDARD_INTERACTABLE_ACTIONS = new HashSet<>(Arrays.asList(
            InteractableAction.DROP,
            InteractableAction.LOOT,
            InteractableAction.USE)
    );

    public Equipment(String name, Set<InteractableAction> STANDARD_INTERACTABLE_ACTIONS, Effect effect, int damage, Ability ability) {
        super(name, STANDARD_INTERACTABLE_ACTIONS);
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

    public Position getPos() {return pos;}

    public void setPos(Position pos) {this.pos = pos;}

    public int getDamage() {return damage;}

    public Set<InteractableAction> getPossibleActions() {
        return possibleInteractableActions;
    }

    public enum Effect {
        SPEED, HEALTH, DAMAGE
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
