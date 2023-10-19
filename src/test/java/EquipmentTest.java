
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

public class EquipmentTest {
    private static final Set<Interactable.InteractableAction> DEFAULT_EQUIPMENT_ACTIONS = new HashSet<>(Arrays.asList(
            Interactable.InteractableAction.DROP,
            Interactable.InteractableAction.LOOT,
            Interactable.InteractableAction.WEAR,
            Interactable.InteractableAction.UNEQUIP)
    );

    @Test
    @DisplayName("Test that equipment has a name")
    public void testEquipmentName() {
        Equipment e = new Equipment("test", EquipmentSlot.RIGHT_HAND, Equipment.Effect.DAMAGE, 10, new PhysicalAbility("Arrow",4,1));
        assertEquals("test", e.getName());
    }

    @Test
    @DisplayName("Test to create an equipment with valid parameters in constructor")
    public void testConstructorWithValidParametersShouldSucceed() {
        Equipment e = new Equipment("Sword", EquipmentSlot.RIGHT_HAND, Equipment.Effect.DAMAGE, 10, new PhysicalAbility("MegaSword",4,1));
        assertEquals("Sword +10% DAMAGE", e.toString());
    }


    @Test
    @DisplayName("Test that equipment has effect")
    public void testEquipmentEffect() {
        Equipment e = new Equipment("test", EquipmentSlot.RIGHT_HAND, Equipment.Effect.DAMAGE, 100, new PhysicalAbility("MiniSword",2,1));
        assertEquals(Equipment.Effect.DAMAGE, e.getEffect());
    }

    @Test
    @DisplayName("Test that possibleActions is correct")
    public void testPossibleActions() {
        Equipment e = new Equipment("test", EquipmentSlot.RIGHT_HAND, Equipment.Effect.HEALTH, 100, new PhysicalAbility("MegaSword",40,1));
        assertEquals(DEFAULT_EQUIPMENT_ACTIONS, e.getPossibleActions());
    }

    @Test
    @DisplayName("Checking that Equipment have Magic Ability")
    public void testEquipmentHaveMagicAbility() {
        Equipment e = new Equipment("test", EquipmentSlot.RIGHT_HAND, Equipment.Effect.HEALTH, 100, new MagicAbility("IceBall", 5,1,"Shoots ice",1,2,5));
        assertEquals("MAGICAL", e.getAbility());
    }

    @Test
    @DisplayName("Checking that Equipment have Physical Ability")
    public void testEquipmentHavePhysicalAbility() {
        Equipment e = new Equipment("test", EquipmentSlot.RIGHT_HAND, Equipment.Effect.DAMAGE, 100, new PhysicalAbility("Knife",5,1));
        assertEquals("PHYSICAL", e.getAbility());
    }

    @Test
    @DisplayName("Check that damage has been modified")
    public void testDamageModified() {
        Equipment e = new Equipment("test", EquipmentSlot.RIGHT_HAND, Equipment.Effect.DAMAGE, 50, new MagicAbility("Fireball", 30,1,"Shoots fire",2,1,5));
        e.damageModifier(60);
        assertEquals(50, e.getDamageOnEquipment());
    }

    @Test
    @DisplayName("Check if method throws exception when damageBar is 0 and equipment is destroyed")
    public void testEquipmentDisappearWhenDamageBarReachesZero(){
        Equipment e = new Equipment("Sword", EquipmentSlot.RIGHT_HAND, Equipment.Effect.DAMAGE, 50, new PhysicalAbility("Sword",10,1));
        assertThrows(RuntimeException.class, () -> e.damageModifier(0));
    }

    @Test
    @DisplayName("Two unequal equipments have unequal hashCode")
    public void testTwoUnequalEquipmentsHaveUnequalHashCode(){
        assertNotEquals(new Equipment("Dagger", EquipmentSlot.LEFT_HAND, Equipment.Effect.DAMAGE, 100, new PhysicalAbility("Dagger",5,1,5)).hashCode(), new Equipment("Sword", EquipmentSlot.RIGHT_HAND, Equipment.Effect.HEALTH, 50, new MagicAbility("FireBall",10,1,"Shoots fire",2,1,5)).hashCode());
    }

    @Test
    @DisplayName("Two equipments which are unequal")
    public void testTwoUnequalEquipments() {
        assertNotEquals(new Equipment("Dagger", EquipmentSlot.RIGHT_HAND, Equipment.Effect.DAMAGE, 100, new PhysicalAbility("Dagger",5,1)), new Equipment("Sword", EquipmentSlot.RIGHT_HAND, Equipment.Effect.HEALTH, 100, new MagicAbility("IceBall", 5,1,"Shoots ice",2,1,5)));
    }

    @Test
    @DisplayName("Two equipments with same parameters have same hashCode")
    public void testTwoEquipmentsWithSameParametersHaveSameHashCode(){
        assertEquals(new Equipment("Dagger", EquipmentSlot.RIGHT_HAND, Equipment.Effect.DAMAGE, 100, new PhysicalAbility("Dagger",5,1)).hashCode(), new Equipment("Dagger", EquipmentSlot.RIGHT_HAND, Equipment.Effect.DAMAGE, 100, new PhysicalAbility("Dagger",5,1)).hashCode());
    }


}

