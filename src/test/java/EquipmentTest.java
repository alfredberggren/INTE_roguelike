
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import java.util.HashSet;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class EquipmentTest {

    @Test
    @DisplayName("Testar att en equipment har ett namn")
    public void testEquipmentName() {
        Equipment e = new Equipment("test", null, Equipment.Effect.DAMAGE, 10, new MagicAbility("Magic"));
        assertEquals("test", e.getName());
    }

    @Test
    @DisplayName("Testar att en equipment har effekt")
    public void testEquipmentEffect() {
        Equipment e = new Equipment("test", null, Equipment.Effect.DAMAGE, 100, new MagicAbility("Magic"));
        assertEquals(Equipment.Effect.DAMAGE, e.getEffect());
    }

    @Test
    @DisplayName("Testar att possibleActions blir korrekt")
    public void testPossibleActions() {
        HashSet<Interactable.InteractableAction> possibleActions = new HashSet<>();
        possibleActions.add(Interactable.InteractableAction.LOOT);
        possibleActions.add(Interactable.InteractableAction.DROP);
        Equipment e = new Equipment("test", possibleActions, Equipment.Effect.HEALTH, 100, new MagicAbility("Magic"));
        assertEquals(possibleActions, e.getPossibleActions());
    }

    @Test
    @DisplayName("Checking that Equipment have Magic Ability")
    public void testEquipmentHaveMagicAbility() {
        Equipment e = new Equipment("test", null, Equipment.Effect.HEALTH, 100, new MagicAbility("Magic"));
        assertEquals("Magic", e.getAbility());
    }

    @Test
    @DisplayName("Checking that Equipment have Physical Ability")
    public void testEquipmentHavePhysicalAbility() {
        Equipment e = new Equipment("test", null, Equipment.Effect.DAMAGE, 100, new MagicAbility("Physical"));
        assertEquals("Physical", e.getAbility());
    }

    @Test
    @DisplayName("Check that damage has been modified")
    public void testDamageModified() {
        Equipment e = new Equipment("test", null, Equipment.Effect.DAMAGE, 50, new MagicAbility("Magic"));
        double newPercentage = e.damageModifier(60);
        assertEquals(50, e.getDamageOnEquipment());
    }
}
