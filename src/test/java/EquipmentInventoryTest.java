import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class EquipmentInventoryTest {

    @Test
    @DisplayName("Testar att lägga till equipment")
    public void testAddEquipment() {
        Equipment e1 = new Equipment("test1", Equipment.Effect.SPEED, 15);
        Equipment e2 = new Equipment("test2", Equipment.Effect.HEALTH, 60);
        Equipment e3 = new Equipment("test3", Equipment.Effect.DAMAGE, 100);
        EquipmentInventory ei= new EquipmentInventory();
        ei.add(e1);
        ei.add(e2);
        ei.add(e3);
        assertEquals("test +15% Speed\ntest2 +60% Health\ntest3 +100% Damage\n", ei.toString());
    }

    @Test
    @DisplayName("Testar att ta bort equipment")
    public void testDropEquipment(){
        Equipment e1 = new Equipment("test1", Equipment.Effect.SPEED, 15);
        Equipment e2 = new Equipment("test2", Equipment.Effect.HEALTH, 60);
        Equipment e3 = new Equipment("test3", Equipment.Effect.DAMAGE, 100);
        EquipmentInventory ei= new EquipmentInventory();
        ei.add(e1);
        ei.add(e2);
        ei.add(e3);
        ei.drop(e1);
        ei.drop(e2);
        assertEquals("test3 +100% Damage\n", ei.toString());

    }
}
