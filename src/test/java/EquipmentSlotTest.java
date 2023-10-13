import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class EquipmentSlotTest {

    @Test
    @DisplayName("Test to create equipment slot")
    public void testConstructorThrowsNullPointerExceptionWhenNameIsNull(){
        assertThrows(NullPointerException.class, () -> new EquipmentSlot(null));
    }
    @Test
    @DisplayName("Test to create equipment slot with empty name")
    public void testConstructorThrowsNullPointerExceptionWhenNameIsEmpty(){
        assertThrows(IllegalArgumentException.class, () -> new EquipmentSlot(""));
    }
    @Test
    @DisplayName("Test to create equipment slot with correct name")
    public void testConstructorWithCorrectName(){
        EquipmentSlot slot = new EquipmentSlot("leftHand");
        assertEquals("leftHand", slot.getName());
    }
}
