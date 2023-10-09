import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class EquipmentTest {

    @Test
    @DisplayName("Testar att en equipment har ett namn")
    public void testEquipmentName() {
        Equipment e = new Equipment("test", Equipment.Effect.DAMAGE, 10);
        assertEquals("test", e.getName());
    }
    @Test
    @DisplayName("Testar att equipment har en effekt")
    public void testEquipmentEffect() {
        Equipment e = new Equipment("test", Equipment.Effect.DAMAGE, 100);
        assertEquals(Equipment.Effect.DAMAGE, e.getEffect());
    }

}
