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
    private EquipmentSlot canBePlacedInSlot;


    /**Constructs Equipment with the specified characteristics*/
    public Equipment(String name, EquipmentSlot canBePlacedInSlot, Effect effect, int durability, Ability ability) {
        super(name, STANDARD_INTERACTABLE_ACTIONS);
        if(effect == null){
            throw new IllegalArgumentException("Effect can not be null");
        }
        this.effect = effect;
        setDurabilityOnEquipment(durability);
        if(ability == null){
            throw new IllegalArgumentException("Ability can not be null");
        }
        this.ability = ability;
        if(canBePlacedInSlot == null){
            throw new IllegalArgumentException("Slot can not be null");
        }
        this.canBePlacedInSlot = canBePlacedInSlot;
    }

    public Equipment(String name, EquipmentSlot canBePlacedInSlot, Set<Interactable.InteractableAction> possibleActions, Effect effect, int durability, Ability ability) {
        super(name, possibleActions);
        if(effect == null){
            throw new IllegalArgumentException("Effect can not be null");
        }
        this.effect = effect;
        setDurabilityOnEquipment(durability);
        if(ability == null){
            throw new IllegalArgumentException("Ability can not be null");
        }
        this.ability = ability;
        if(canBePlacedInSlot == null){
            throw new IllegalArgumentException("Slot can not be null");
        }
        this.canBePlacedInSlot = canBePlacedInSlot;
    }


    public EquipmentSlot getCanBePlacedInSlot() {
        return canBePlacedInSlot;
    }

    public Set<InteractableAction> getPossibleActions() {
        return possibleInteractableActions;
    }

    /**Retrieves the type of associated ability*/
    public Ability getAbility() {
        return ability;
    }

    /**Modifies the damage to the equipment based on a durability value. If the durability falls to or below zero, the equipment is considered destroyed*/
    public void decreaseDurability(int decreaseBy) {
        if (!isBroken()) {
            durability -= decreaseBy;
            if (durability <= 0) {
                setDurabilityOnEquipment(0);
            }
        }
    }

    public boolean isBroken() {
        return durability == 0;
    }

    /**Sets the damage bar value of the equipment*/
    private void setDurabilityOnEquipment(int durability) {
        if(durability < 0) {
            throw new IllegalArgumentException("Durability on Equipment cannot be less than zero");
        }
        this.durability = durability;
    }

    /**Retrieves the current damage bar value of the equipment*/
    public int getDurabilityOnEquipment() {
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
        return name + " +" + durability + "% " + effect;
    }
}
