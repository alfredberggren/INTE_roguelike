import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class EquipmentTest {
    static final Set<Interactable.InteractableAction> DEFAULT_EQUIPMENT_ACTIONS = new HashSet<>(Arrays.asList(Interactable.InteractableAction.LOOT, Interactable.InteractableAction.DROP));

    @Test
    @DisplayName("Testar att en equipment har ett namn")
    public void testEquipmentName() {
        Equipment e = new Equipment("test", DEFAULT_EQUIPMENT_ACTIONS, Equipment.Effect.DAMAGE, 10);
        assertEquals("test", e.getName());
    }

    @Test
    @DisplayName("Testar att en equipment har effekt")
    public void testEquipmentEffect() {
        Equipment e = new Equipment("test", DEFAULT_EQUIPMENT_ACTIONS, Equipment.Effect.DAMAGE, 100);
        assertEquals(Equipment.Effect.DAMAGE, e.getEffect());
    }

    @Test
    @DisplayName("Testar att possibleActions blir korrekt")
    public void testPossibleActions() {
        Equipment e = new Equipment("test", DEFAULT_EQUIPMENT_ACTIONS, Equipment.Effect.HEALTH, 100);
        assertEquals(DEFAULT_EQUIPMENT_ACTIONS, e.getPossibleActions());
    }
}
