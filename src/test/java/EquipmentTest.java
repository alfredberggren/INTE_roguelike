import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class EquipmentTest {

    @Test
    @DisplayName("Testar att en equipment har ett namn")
    public void testEquipmentName() {
        Equipment e = new Equipment("test", Equipment.Effect.DAMAGE, 10);
        assertEquals("test", e.getName());
    }

    @Test
    @DisplayName("Testar att en equipment har effekt")
    public void testEquipmentEffect() {
        Equipment e = new Equipment("test", Equipment.Effect.DAMAGE, 100);
        assertEquals(Equipment.Effect.DAMAGE, e.getEffect());
    }

    @Disabled //inte klar
    @DisplayName("Testar att lägga till equipment")
    public void testAddEquipment() {
        Equipment e1 = new Equipment("test1", Equipment.Effect.SPEED, 15);
        Equipment e2 = new Equipment("test2", Equipment.Effect.HEALTH, 60);
        Equipment e3 = new Equipment("test3", Equipment.Effect.DAMAGE, 100);
        ArrayList<Equipment> equipments = new ArrayList<>();
        equipments.add(e1);
        equipments.add(e2);
        equipments.add(e3);
        System.err.print(equipments);
    }

    @Test
    @DisplayName("Testar att ta bort equipment")
    public void testDropEquipment(){
        Equipment e1 = new Equipment("test1", Equipment.Effect.SPEED, 15);
        Equipment e2 = new Equipment("test2", Equipment.Effect.HEALTH, 60);
        Equipment e3 = new Equipment("test3", Equipment.Effect.DAMAGE, 100);
        ArrayList<Equipment> equipments = new ArrayList<>();
        equipments.add(e1);
        equipments.add(e2);
        equipments.add(e3);
        equipments.remove(e1);
        equipments.remove(e2);
        assertEquals("test3", equipments);

    }
}
