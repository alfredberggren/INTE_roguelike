/**
 * The Equipment class represents an item or piece of equipment, that can be used by characters. It extends the NonLivingEntity class
 */

import java.util.*;

public class Equipment extends NonLivingEntity {
    private static final Set<InteractableAction> STANDARD_INTERACTABLE_ACTIONS = new HashSet<>(Arrays.asList(
            InteractableAction.LOOT,
            InteractableAction.DROP,
            InteractableAction.WEAR,
            InteractableAction.UNEQUIP)
    );

    private Effect effect; //gör till egen klass?
    private int damage;
    private Ability ability;

    //Why would equipment need this?
    private EquipmentSlot equipmentSlot;
    private double damageBar;


    /**Constructs Equipment with the specified characteristics*/
    public Equipment(String name, EquipmentSlot equipmentSlot, Effect effect, int damage, Ability ability) {
        super(name, STANDARD_INTERACTABLE_ACTIONS);
        this.effect = effect;
        this.damage = damage;
        this.ability = ability;
        this.equipmentSlot = equipmentSlot;
    }

    public Equipment(String name, EquipmentSlot equipmentSlot, Set<Interactable.InteractableAction> possibleActions, Effect effect, int damage, Ability ability) {
        super(name, possibleActions);
        this.effect = effect;
        this.damage = damage;
        this.ability = ability;
        this.equipmentSlot = equipmentSlot;
    }

    /**
     * Constructor with fewer parameters, should be used for equipment that has "passive" uses, i.e. non-violent/non-magical.
     * Sets effect to none, damage to 0, and ability to null.
     * @param name
     * The name of the Equipment
     * @param possibleActions
     * The actions that can be done with Equipment
     */
    public Equipment(String name, Set<InteractableAction> possibleActions) {
        this(name, null, Effect.NONE, 0, null);
    }

    public String getName(){
        return name;
    }

    /**Retrieves the effect provided by the equipment*/
    public Effect getEffect() {
        return effect;
    }

    public EquipmentSlot getEquipmentSlot() {
        return equipmentSlot;
    }

    public int getDamage() {
        return damage;
    }

    public Set<InteractableAction> getPossibleActions() {
        return possibleInteractableActions;
    }

    /**Represents the possible effects of the equipment*/
    public enum Effect {
        SPEED, HEALTH, DAMAGE, ARMOR, NONE
    }

    /**Represents different types of armor*/
    public enum Armor {
        HELMET, CHEST_ARMOR, LEGGING, BOOTS
    }

    /**Retrieves the type of associated ability*/
    public Ability getAbility() {
        return ability;
    }

    /**Modifies the damage to the equipment based on a damage bar value. If the damage bar falls to or below zero, the equipment is considered destroyed*/
    public void damageModifier(double damageBar) {
        double decreaseBy = 10;
        if (damageBar >= 10 && damageBar <= 100) {
            damageBar -= decreaseBy;
            if (damageBar <= 0) {
                setDamageOnEquipment(0);
            }
        }
        setDamageOnEquipment(damageBar);
        //Simon:
        //should this be a runtime exception? and how is this exception handled
        //it could instead be handled in turnSystem
        //where if any of the equipment on the char has reached 0 att the start of the turn
        //it is removed from character
        if (damageBar == 0) {
            throw new RuntimeException("Equipment has been destroyed!");
        }
    }

    /**Sets the damage bar value of the equipment*/
    public void setDamageOnEquipment(double damageBar) {
        this.damageBar = damageBar;
    }

    /**Retrieves the current damage bar value of the equipment*/
    public double getDamageOnEquipment() {
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
