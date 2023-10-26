
import org.junit.jupiter.api.BeforeEach;
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

    private Equipment equipment;

    @BeforeEach
    void setUp() {
        equipment = new Equipment("Sword", EquipmentSlot.RIGHT_HAND, Equipment.Effect.DAMAGE, 10, new PhysicalAbility("Slash", 10, 1, "Physical Attack"));
    }

    @Test
    @DisplayName("Test that equipment has a name")
    public void testEquipmentName() {
        assertEquals("Sword", equipment.getName());
    }

    @Test
    @DisplayName("Test to create an equipment with valid parameters in constructor")
    public void testConstructorWithValidParametersShouldSucceed() {
        assertEquals("Sword +10% DAMAGE", equipment.toString());
    }

    @Test
    @DisplayName("Test to create an equipment with effect equal to null")
    public void testConstructorWithEffectEqualToNull_ThrowsException() {
        assertThrows(IllegalArgumentException.class, () -> {
            new Equipment("Dagger", EquipmentSlot.LEFT_HAND, null, 1, new PhysicalAbility("Test ability", 10, 1, "test"));
        });
    }

    @Test
    @DisplayName("Test to create an equipment with ability equal to null")
    public void testConstructorWithAbilityEqualToNull_ThrowsException() {
        assertThrows(IllegalArgumentException.class, () -> {
            new Equipment("Dagger", EquipmentSlot.LEFT_HAND, Equipment.Effect.DAMAGE, 1, null);
        });
    }

    @Test
    @DisplayName("Test to create an equipment with slot equal to null")
    public void testConstructorWithSlotEqualToNull_ThrowsException() {
        assertThrows(IllegalArgumentException.class, () -> {
            new Equipment("Dagger", null, Equipment.Effect.DAMAGE, 1, new PhysicalAbility("Test ability", 10, 1, "test"));
        });
    }

    @Test
    @DisplayName("Test that equipment has effect")
    public void testEquipmentEffect() {
        assertEquals(Equipment.Effect.DAMAGE, equipment.getEffect());
    }

    @Test
    @DisplayName("Test that possibleActions is correct")
    public void testPossibleActions() {
        assertEquals(DEFAULT_EQUIPMENT_ACTIONS, equipment.getPossibleActions());
    }

    @Test
    @DisplayName("Checking that Equipment have Magic Ability")
    public void testEquipmentHaveMagicAbility() {
        Equipment e = new Equipment("Test", EquipmentSlot.LEFT_HAND, Equipment.Effect.DAMAGE, 10, new MagicAbility("Spell", 10, 1, "Spell", 0, 0, 5));
        assertEquals("MAGICAL", e.getAbility().toString());
    }

    @Test
    @DisplayName("Checking that Equipment have Physical Ability")
    public void testEquipmentHavePhysicalAbility() {
        assertEquals("PHYSICAL", equipment.getAbility().toString());
    }

    @Test
    @DisplayName("Check that durability is correct")
    public void testDurabilityOnEquipment() {
        equipment.getDurabilityOnEquipment();
        assertEquals(10, equipment.getDurabilityOnEquipment());
    }

    @Test
    @DisplayName("Check if method throws exception when durability is 0 and equipment is destroyed")
    public void testEquipmentIsDestroyedWhenDurabilityReachesZero() {
        equipment.decreaseDurability(10);
        assertTrue(equipment.isBroken());
    }

    @Test
    @DisplayName("Check if durability decreases correctly")
    public void testDecreaseDurability() {
        equipment.decreaseDurability(5);
        assertEquals(5, equipment.getDurabilityOnEquipment());
    }

    @Test
    @DisplayName("Check that Equipment is not destroyed if durability is not zero")
    public void testEquipmentNotDestroyedWhenDurabilityIsAboveZero() {
        equipment.decreaseDurability(5);
        assertFalse(equipment.isBroken());
    }

    @Test
    @DisplayName("Test to set negative durability on equipment")
    public void testSetNegativeDurability_ThrowsException() {
        assertThrows(IllegalArgumentException.class, () -> {
            equipment.setDurabilityOnEquipment(-10);
        });
    }

    @Test
    @DisplayName("Two unequal equipments have unequal hashCode")
    public void testTwoUnequalEquipmentsHaveUnequalHashCode() {
        assertNotEquals(new Equipment("Dagger", EquipmentSlot.LEFT_HAND, Equipment.Effect.DAMAGE, 100, new PhysicalAbility("Slash", 5, 1, "Physical Attack")).hashCode(), new Equipment("Sword", EquipmentSlot.RIGHT_HAND, Equipment.Effect.HEALTH, 50, new MagicAbility("FireBall", 10, 1, "Shoots fire", 2, 1, 5)).hashCode());
    }

    @Test
    @DisplayName("Two equipments which are unequal")
    public void testTwoUnequalEquipments() {
        assertNotEquals(new Equipment("Dagger", EquipmentSlot.RIGHT_HAND, Equipment.Effect.DAMAGE, 100, new PhysicalAbility("Slash", 5, 1, "Physical Attack")), new Equipment("Slash", EquipmentSlot.RIGHT_HAND, Equipment.Effect.HEALTH, 100, new MagicAbility("IceBall", 5, 1, "Shoots ice", 2, 1, 5)));
    }

    @Test
    @DisplayName("Two equipments with same parameters have same hashCode")
    public void testTwoEquipmentsWithSameParametersHaveSameHashCode() {
        assertEquals(new Equipment("Slash", EquipmentSlot.RIGHT_HAND, Equipment.Effect.DAMAGE, 100, new PhysicalAbility("Slash", 5, 1, "Physical Attack")).hashCode(), new Equipment("Slash", EquipmentSlot.RIGHT_HAND, Equipment.Effect.DAMAGE, 100, new PhysicalAbility("Slash", 5, 1, "Physical Attack")).hashCode());
    }


}

