
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import java.util.HashSet;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class EquipmentTest {

    @Test
    @DisplayName("Testar att en equipment har ett namn")
    public void testEquipmentName() {
        Equipment e = new Equipment("test", null, Equipment.Effect.DAMAGE, 10);
        assertEquals("test", e.getName());
    }

    @Test
    @DisplayName("Testar att en equipment har effekt")
    public void testEquipmentEffect() {
        Equipment e = new Equipment("test", null, Equipment.Effect.DAMAGE, 100);
        assertEquals(Equipment.Effect.DAMAGE, e.getEffect());
    }

    @Test
    @DisplayName("Testar att possibleActions blir korrekt")
    public void testPossibleActions() {
        HashSet<Interactable.InteractableAction> possibleActions = new HashSet<>();
        possibleActions.add(Interactable.InteractableAction.LOOT);
        possibleActions.add(Interactable.InteractableAction.DROP);
        Equipment e = new Equipment("test", possibleActions, Equipment.Effect.HEALTH, 100);
        assertEquals(possibleActions, e.getPossibleActions());
    }
}
