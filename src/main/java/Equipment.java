/**The Equipment class represents an item or piece of equipment, that can be used by characters. It extends the NonLivingEntity class*/

import java.util.*;

public class Equipment extends NonLivingEntity{
    private Effect effect;
    private int damage;
    private Ability ability;
    private Position pos;
    private double damageBar;


    /**Constructs Equipment with the specified characteristics*/
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
    /**Retrieves the effect provided by the equipment*/
    public Effect getEffect() {
        return effect;
    }

    public Position getPos() {return pos;}

    public void setPos(Position pos) {this.pos = pos;}

    public int getDamage() {return damage;}

    public Set<InteractableAction> getPossibleActions() {
        return possibleInteractableActions;
    }

    /**Represents the possible effects of the equipment*/
    public enum Effect {
        SPEED, HEALTH, DAMAGE
    }
    /**Represents different types of armor*/
    public enum Armor {
        HELMET, CHEST_ARMOR, LEGGING, BOOTS
    }
    /**Retrieves the type of associated ability*/
    public Ability.AbilityType getAbility(){
         return ability.getTypeOfAbility();
    }
    /**Modifies the damage to the equipment based on a damage bar value. If the damage bar falls to or below zero, the equipment is considered destroyed*/
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
    /**Sets the damage bar value of the equipment*/
    public void setDamageOnEquipment(double damageBar){
        this.damageBar = damageBar;
    }
    /**Retrieves the current damage bar value of the equipment*/
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

    /**Returns a string representation of the equipment, including its name, damage and effect*/
    @Override
    public String toString() {
        String s = name + " +" + damage + "% " + effect;
        return s;
    }
}
