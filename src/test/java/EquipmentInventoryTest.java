import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class EquipmentInventoryTest {
    @Test
    @DisplayName("Testar att ta bort all equipment")
    public void testRemoveAllEquipment() {
        Equipment e1 = new Equipment("test1", Equipment.Effect.SPEED, 15);
        Equipment e2 = new Equipment("test2", Equipment.Effect.HEALTH, 60);
        Equipment e3 = new Equipment("test3", Equipment.Effect.DAMAGE, 100);
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
        Equipment e1 = new Equipment("test1", Equipment.Effect.SPEED, 15);
        Equipment e2 = new Equipment("test2", Equipment.Effect.HEALTH, 60);
        Equipment e3 = new Equipment("test3", Equipment.Effect.DAMAGE, 100);
        EquipmentInventory inventory= new EquipmentInventory();
        inventory.add(e1);
        inventory.add(e2);
        inventory.add(e3);
        assertEquals("test +15% Speed\ntest2 +60% Health\ntest3 +100% Damage\n", inventory.toString());
    }

    @Test
    @DisplayName("Testar att ta bort equipment")
    public void testDropEquipment(){
        Equipment e1 = new Equipment("test1", Equipment.Effect.SPEED, 15);
        Equipment e2 = new Equipment("test2", Equipment.Effect.HEALTH, 60);
        Equipment e3 = new Equipment("test3", Equipment.Effect.DAMAGE, 100);
        EquipmentInventory inventory= new EquipmentInventory();
        inventory.add(e1);
        inventory.add(e2);
        inventory.add(e3);
        inventory.drop(e1);
        inventory.drop(e2);
        assertEquals("test3 +100% Damage\n", inventory.toString());

    }

    @Test
    @DisplayName ("Testar att hämta equipment")
    public void testGetEquipment(){
        Equipment e = new Equipment("test", Equipment.Effect.HEALTH, 60);
        EquipmentInventory inventory = new EquipmentInventory();
        inventory.add(e);
        Equipment equipment = inventory.getEquipment(0);
        assertEquals("test + 60% Health\n", equipment.toString());

    }
}
