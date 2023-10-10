import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class EquipmentInventoryTest {
    static final Set<Interactable.InteractableAction> DEFAULT_EQUIPMENT_ACTIONS = new HashSet<>(Arrays.asList(Interactable.InteractableAction.LOOT, Interactable.InteractableAction.DROP));

    @Test
    @DisplayName("Testar att ta bort all equipment")
    public void testRemoveAllEquipment() {
        Equipment e1 = new Equipment("test1", DEFAULT_EQUIPMENT_ACTIONS, Equipment.Effect.SPEED, 15, new MagicAbility("Magic"));
        Equipment e2 = new Equipment("test2", DEFAULT_EQUIPMENT_ACTIONS, Equipment.Effect.HEALTH, 60, new MagicAbility("Magic"));
        Equipment e3 = new Equipment("test3", DEFAULT_EQUIPMENT_ACTIONS, Equipment.Effect.DAMAGE, 100, new MagicAbility("Magic"));
        EquipmentInventory inventory= new EquipmentInventory();
        inventory.add(e1);
        inventory.add(e2);
        inventory.add(e3);
        inventory.removeAllEquipments();
        assertEquals("", inventory.toString());
    }

    @Test
    @DisplayName("Testar att lägga till equipment")
    public void testAddEquipment() {
        Equipment e1 = new Equipment("test1", DEFAULT_EQUIPMENT_ACTIONS, Equipment.Effect.SPEED, 15, new MagicAbility("Magic"));
        Equipment e2 = new Equipment("test2", DEFAULT_EQUIPMENT_ACTIONS, Equipment.Effect.HEALTH, 60, new MagicAbility("Magic"));
        Equipment e3 = new Equipment("test3", DEFAULT_EQUIPMENT_ACTIONS, Equipment.Effect.DAMAGE, 100, new MagicAbility("Magic"));
        EquipmentInventory inventory= new EquipmentInventory();
        inventory.add(e1);
        inventory.add(e2);
        inventory.add(e3);
        assertEquals("test1 +15% SPEED\ntest2 +60% HEALTH\ntest3 +100% DAMAGE\n", inventory.toString());
    }

    @Disabled
    @DisplayName("Test to discard equipment")
    public void testToDiscardEquipment(){
        Equipment e1 = new Equipment("test1", DEFAULT_EQUIPMENT_ACTIONS, Equipment.Effect.SPEED, 15, new MagicAbility("Magic"));
        Equipment e2 = new Equipment("test2", DEFAULT_EQUIPMENT_ACTIONS, Equipment.Effect.HEALTH, 60, new MagicAbility("Magic"));
        Equipment e3 = new Equipment("test3", DEFAULT_EQUIPMENT_ACTIONS, Equipment.Effect.DAMAGE, 100, new MagicAbility("Magic"));
        EquipmentInventory inventory= new EquipmentInventory();
        inventory.add(e1);
        inventory.add(e2);
        inventory.add(e3);
        //inventory.discard(e1);
        //inventory.discard(e2);
        assertEquals("test3 +100% Damage\n", inventory.toString());

    }

    @Test
    @DisplayName ("Test to get equipment")
    public void testGetEquipment(){
        Equipment e = new Equipment("test", DEFAULT_EQUIPMENT_ACTIONS, Equipment.Effect.HEALTH, 60, new MagicAbility("Magic"));
        EquipmentInventory inventory = new EquipmentInventory();
        inventory.add(e);
        Equipment equipment = inventory.getEquipment(0);
        assertEquals("test + 60% Health\n", equipment.toString());

    }
}
