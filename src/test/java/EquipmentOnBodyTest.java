import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.*;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class EquipmentOnBodyTest {
    static EquipmentOnBody EQUIPMENT_ON_BODY;
    static EquipmentSlot DEFAULT_EQUIPMENT_SLOT = new EquipmentSlot("leftHand");
    static final Set<Interactable.InteractableAction> DEFAULT_EQUIPMENT_ACTIONS = new HashSet<>(Arrays.asList(Interactable.InteractableAction.LOOT, Interactable.InteractableAction.DROP));
    static Equipment DEFAULT_EQUIPMENT = new Equipment("Sword", DEFAULT_EQUIPMENT_ACTIONS, Equipment.Effect.DAMAGE, 50, new PhysicalAbility("Sword", 10, "Physical"));

    @BeforeEach
    void setUp() {
        EQUIPMENT_ON_BODY = new EquipmentOnBody(new HashMap<EquipmentSlot, Equipment>());
    }

    @Test
    @DisplayName("Test to put an equipment in a slot")
    public void testToPutEquipmentInSlot() {
        EQUIPMENT_ON_BODY.putEquipment(DEFAULT_EQUIPMENT_SLOT, DEFAULT_EQUIPMENT);
        assertEquals("leftHand: Sword +50% DAMAGE", EQUIPMENT_ON_BODY.toString());
    }

    @Test
    @DisplayName("Test to put an equipment in an occupied slot")
    public void testToPutEquipmentInOccupiedSlot() {
        EQUIPMENT_ON_BODY.putEquipment(DEFAULT_EQUIPMENT_SLOT, DEFAULT_EQUIPMENT);
        EQUIPMENT_ON_BODY.putEquipment(DEFAULT_EQUIPMENT_SLOT, new Equipment("Dagger", DEFAULT_EQUIPMENT_ACTIONS, Equipment.Effect.DAMAGE, 50, new PhysicalAbility("Dagger", 20, "Physical")));
        assertEquals("leftHand: Sword +50% DAMAGE", EQUIPMENT_ON_BODY.toString());
    }

    @Test
    @DisplayName("Test to drop an equipment")
    public void testToDropAnEquipment() {
        EQUIPMENT_ON_BODY.putEquipment(DEFAULT_EQUIPMENT_SLOT, DEFAULT_EQUIPMENT);
        EQUIPMENT_ON_BODY.dropEquipment(DEFAULT_EQUIPMENT_SLOT);
        assertEquals("", EQUIPMENT_ON_BODY.toString());
    }


}





