import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

public class EquipmentOnBodyTest {
    static EquipmentOnBody equipmentOnBody;
    static final EquipmentSlot equipmentSlot = EquipmentSlot.LEFT_HAND;
    static final Equipment equipment = new Equipment("Sword", EquipmentSlot.LEFT_HAND, Equipment.Effect.DAMAGE, 50, new PhysicalAbility("Slash", 10, 1,"Physical Attack"));

    @BeforeEach
    void setUp() {
        equipmentOnBody = new EquipmentOnBody();
    }

    @Test
    @DisplayName("Test to get an equipment")
    public void testToGetEquipment() {
        equipmentOnBody.putEquipment(equipmentSlot, equipment);
        assertEquals("LEFT_HAND: Sword +50% DAMAGE", equipmentOnBody.toString());
    }

    @Test
    @DisplayName("Test to put an equipment in a slot")
    public void testToPutEquipmentInSlot() {
        equipmentOnBody.putEquipment(equipmentSlot, equipment);
        assertEquals("LEFT_HAND: Sword +50% DAMAGE", equipmentOnBody.toString());
    }

    @Test
    @DisplayName("Test to check if slot contains any equipment")
    public void testToCheckIfSlotContainsEquipment() {
        equipmentOnBody.putEquipment(equipmentSlot, equipment);
        assertTrue(equipmentOnBody.slotContainsEquipment(equipmentSlot));
        assertFalse(equipmentOnBody.slotContainsEquipment(EquipmentSlot.BELT));
    }

    @Test
    @DisplayName("Test to put an equipment in an occupied slot")
    public void testToPutEquipmentInOccupiedSlot() {
        equipmentOnBody.putEquipment(equipmentSlot, equipment);
        equipmentOnBody.putEquipment(equipmentSlot, new Equipment("Dagger", EquipmentSlot.LEFT_HAND, Equipment.Effect.DAMAGE, 50, new PhysicalAbility("Slash", 20, 1, "Physical Attack")));
        assertEquals("LEFT_HAND: Sword +50% DAMAGE", equipmentOnBody.toString());
    }

    @Test
    @DisplayName("Test to remove an equipment")
    public void testToRemoveAnEquipment() {
        equipmentOnBody.putEquipment(equipmentSlot, equipment);
        equipmentOnBody.removeEquipment(equipmentSlot);
        assertEquals("", equipmentOnBody.toString());
    }

    @Test
    @DisplayName("Test to check where the equipment was placed")
    public void testToCheckWhereEquipmentWasPlaced() {
        equipmentOnBody.putEquipment(equipmentSlot, equipment);
        assertEquals(equipmentSlot, equipmentOnBody.checkWhereEquipmentWasPlaced(equipment));
    }

    @Test
    @DisplayName("Test to check where the non-existent equipment was placed in the map")
    public void testToCheckWhereNonExistentEquipmentWasPlaced() {
        equipmentOnBody.putEquipment(equipmentSlot, equipment);
        assertNull(equipmentOnBody.checkWhereEquipmentWasPlaced(new Equipment("Dagger", EquipmentSlot.LEFT_HAND, Equipment.Effect.DAMAGE, 60, new PhysicalAbility("Slash", 20, 1,"Physical Attack"))));
    }


}






