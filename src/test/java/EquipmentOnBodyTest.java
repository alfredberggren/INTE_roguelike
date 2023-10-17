import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

public class EquipmentOnBodyTest {
    static EquipmentOnBody EQUIPMENT_ON_BODY;
    static final EquipmentSlot DEFAULT_EQUIPMENT_SLOT = EquipmentSlot.LEFT_HAND;
    static final Equipment DEFAULT_EQUIPMENT = new Equipment("Sword", EquipmentSlot.LEFT_HAND, Equipment.Effect.DAMAGE, 50, new PhysicalAbility("Sword", 10, 1));
    static final Player DEFAULT_PLAYER = new Player("Name", 100, 10, new Position(0,0), new TextIO());

    @BeforeEach
    void setUp() {
        EQUIPMENT_ON_BODY = new EquipmentOnBody(new HashMap<EquipmentSlot, Equipment>());
    }

    @Test
    @DisplayName("Test to put an equipment in a slot")
    public void testToPutEquipmentInSlot() {
        EQUIPMENT_ON_BODY.putEquipment(DEFAULT_EQUIPMENT_SLOT, DEFAULT_EQUIPMENT);
        assertEquals("LEFT_HAND: Sword +50% DAMAGE", EQUIPMENT_ON_BODY.toString());
    }

    @Test
    @DisplayName("Test to check if slot contains any equipment")
    public void testToCheckIfSlotContainsEquipment() {
        EQUIPMENT_ON_BODY.putEquipment(DEFAULT_EQUIPMENT_SLOT, DEFAULT_EQUIPMENT);
        assertTrue(EQUIPMENT_ON_BODY.slotContainsEquipment(DEFAULT_EQUIPMENT_SLOT));
        assertFalse(EQUIPMENT_ON_BODY.slotContainsEquipment(EquipmentSlot.BELT));
    }

    @Test
    @DisplayName("Test to put an equipment in an occupied slot")
    public void testToPutEquipmentInOccupiedSlot() {
        EQUIPMENT_ON_BODY.putEquipment(DEFAULT_EQUIPMENT_SLOT, DEFAULT_EQUIPMENT);
        EQUIPMENT_ON_BODY.putEquipment(DEFAULT_EQUIPMENT_SLOT, new Equipment("Dagger", EquipmentSlot.LEFT_HAND, Equipment.Effect.DAMAGE, 50, new PhysicalAbility("Dagger", 20, 1)));
        assertEquals("LEFT_HAND: Sword +50% DAMAGE", EQUIPMENT_ON_BODY.toString());
    }

    @Test
    @DisplayName("Test to remove an equipment")
    public void testToRemoveAnEquipment() {
        EQUIPMENT_ON_BODY.putEquipment(DEFAULT_EQUIPMENT_SLOT, DEFAULT_EQUIPMENT);
        EQUIPMENT_ON_BODY.removeEquipment(DEFAULT_EQUIPMENT_SLOT);
        assertEquals("", EQUIPMENT_ON_BODY.toString());
    }


    @Test
    @DisplayName("Test to check where the equipment was placed")
    public void testToCheckWhereEquipmentWasPlaced() {
        EQUIPMENT_ON_BODY.putEquipment(DEFAULT_EQUIPMENT_SLOT, DEFAULT_EQUIPMENT);
        assertEquals(DEFAULT_EQUIPMENT_SLOT, EQUIPMENT_ON_BODY.checkWhereEquipmentWasPlaced(DEFAULT_EQUIPMENT));
    }

    @Test
    @DisplayName("Test to check where the non-existent equipment was placed in the map")
    public void testToCheckWhereNonExistentEquipmentWasPlaced() {
        EQUIPMENT_ON_BODY.putEquipment(DEFAULT_EQUIPMENT_SLOT, DEFAULT_EQUIPMENT);
        assertNull(EQUIPMENT_ON_BODY.checkWhereEquipmentWasPlaced(new Equipment("Dagger", EquipmentSlot.LEFT_HAND, Equipment.Effect.DAMAGE, 60, new PhysicalAbility("Dagger", 20, 1))));
    }

}






