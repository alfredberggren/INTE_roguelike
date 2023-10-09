import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class EquipmentTest {

    @Test
    public void testEquipmentName() {
        Equipment e = new Equipment("test", 100, 10);
        assertEquals("test", e.getName());
    }
}
