/**
 * The Equipment class represents an item or piece of equipment, that can be used by characters. It extends the NonLivingEntity class
 */

import java.util.*;

public class Equipment extends NonLivingEntity {

    public enum Effect {
        SPEED, HEALTH, DAMAGE, NONE
    }

    private static final Set<InteractableAction> STANDARD_INTERACTABLE_ACTIONS = new HashSet<>(Arrays.asList(
            InteractableAction.LOOT,
            InteractableAction.DROP,
            InteractableAction.WEAR,
            InteractableAction.UNEQUIP)
    );

    private Effect effect;
    private int durability;
    private Ability ability;

    //Why would equipment need this?
    private EquipmentSlot equipmentSlot;


    /**Constructs Equipment with the specified characteristics*/
    public Equipment(String name, EquipmentSlot equipmentSlot, Effect effect, int durability, Ability ability) {
        super(name, STANDARD_INTERACTABLE_ACTIONS);
        this.effect = effect;
        this.durability = durability;
        this.ability = ability;
        this.equipmentSlot = equipmentSlot;
    }

    public Equipment(String name, EquipmentSlot equipmentSlot, Set<Interactable.InteractableAction> possibleActions, Effect effect, int durability, Ability ability) {
        super(name, possibleActions);
        this.effect = effect;
        this.durability = durability;
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

    public EquipmentSlot getEquipmentSlot() {
        return equipmentSlot;
    }

    public int getDurability() {
        return durability;
    }

    public Set<InteractableAction> getPossibleActions() {
        return possibleInteractableActions;
    }

    /**Retrieves the type of associated ability*/
    public Ability getAbility() {
        return ability;
    }

    /**Modifies the damage to the equipment based on a damage bar value. If the damage bar falls to or below zero, the equipment is considered destroyed*/
    public void decreaseDurability(int decreaseBy) {
        if (!isBroken()) {
            durability -= decreaseBy;
            if (durability <= 0) {
                setDurabilityOnEquipment(0);
            }
        }
    }

    private boolean isBroken() {
        return durability == 0;
    }

    /**Sets the damage bar value of the equipment*/
    private void setDurabilityOnEquipment(int durability) {
        this.durability = durability;
    }

    /**Retrieves the current damage bar value of the equipment*/
    public int getDamageOnEquipment() {
        return durability;
    }

    /**Retrieves the effect provided by the equipment*/
    public Effect getEffect() {
        return effect;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Equipment equipment = (Equipment) o;
        return name.equals(equipment.getName()) && durability == equipment.durability && effect == equipment.effect && Objects.equals(ability, equipment.ability);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, effect, durability, ability);
    }

    /**Returns a string representation of the equipment, including its name, damage and effect*/
    @Override
    public String toString() {
        String s = name + " +" + durability + "% " + effect;
        return s;
    }
}
