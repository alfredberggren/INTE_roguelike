import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

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
    @DisplayName("Test that constructor generate new HashMap")
    public void testToGenerateNewHashMapInConstructor(){
        assertNotNull(equipmentOnBody.toString());
    }

    @Test
    @DisplayName("Test to put an equipment in a slot")
    public void testToPutEquipmentInSlot() {
        equipmentOnBody.putEquipment(equipmentSlot, equipment);
        assertEquals("LEFT_HAND: Sword +50% DAMAGE", equipmentOnBody.toString());
    }

    @Test
    @DisplayName("Test to put an equipment equal to null in a slot")
    public void testToPutEquipmentEqualToNullInSlot() {
        equipmentOnBody.putEquipment(equipmentSlot, null);
        assertEquals("", equipmentOnBody.toString());
    }

    @Test
    @DisplayName("Test to put an equipment in a slot equal to null")
    public void testToPutEquipmentInSlotEqualToNull() {
        equipmentOnBody.putEquipment(null, equipment);
        assertEquals("", equipmentOnBody.toString());
    }

    @Test
    @DisplayName("Test to get an equipment")
    public void testToGetEquipment() {
        equipmentOnBody.putEquipment(equipmentSlot, equipment);
        assertEquals("Sword +50% DAMAGE", equipmentOnBody.getEquipment(equipmentSlot).toString());
    }

    @Test
    @DisplayName("Test to get an equipment equal to null")
    public void testToGetEquipmentEqualToNull() {
        equipmentOnBody.putEquipment(equipmentSlot, equipment);
        assertNull(equipmentOnBody.getEquipment(null));
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
    @DisplayName("Test to remove an equipment equal to null")
    public void testToRemoveAnEquipmentEqualToNull() {
        equipmentOnBody.putEquipment(equipmentSlot, equipment);
        equipmentOnBody.removeEquipment(null);
        assertEquals("LEFT_HAND: Sword +50% DAMAGE", equipmentOnBody.toString());
    }

    @Test
    @DisplayName("Test to check where the equipment was placed")
    public void testToCheckWhereEquipmentWasPlaced_ShouldReturnCorrectSlot() {
        equipmentOnBody.putEquipment(equipmentSlot, equipment);
        assertEquals(equipmentSlot, equipmentOnBody.checkWhereEquipmentWasPlaced(equipment));
    }

    @Test
    @DisplayName("Test to check where the non-existent equipment was placed in the map")
    public void testToCheckWhereNonExistentEquipmentWasPlaced_ShouldBeNull() {
        equipmentOnBody.putEquipment(equipmentSlot, equipment);
        assertNull(equipmentOnBody.checkWhereEquipmentWasPlaced(new Equipment("Dagger", EquipmentSlot.LEFT_HAND, Equipment.Effect.DAMAGE, 60, new PhysicalAbility("Slash", 20, 1,"Physical Attack"))));
    }

}






