
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class EquipmentTest {
    static final Set<Interactable.InteractableAction> DEFAULT_EQUIPMENT_ACTIONS = new HashSet<>(Arrays.asList(Interactable.InteractableAction.LOOT, Interactable.InteractableAction.DROP));

    @Test
    @DisplayName("Testar att en equipment har ett namn")
    public void testEquipmentName() {
        Equipment e = new Equipment("test", DEFAULT_EQUIPMENT_ACTIONS, Equipment.Effect.DAMAGE, 10, new MagicAbility("Physical"));
        assertEquals("test", e.getName());
    }

    @Test
    @DisplayName("Testar att en equipment har effekt")
    public void testEquipmentEffect() {
        Equipment e = new Equipment("test", DEFAULT_EQUIPMENT_ACTIONS, Equipment.Effect.DAMAGE, 100, new MagicAbility("Physical"));
        assertEquals(Equipment.Effect.DAMAGE, e.getEffect());
    }

    @Test
    @DisplayName("Testar att possibleActions blir korrekt")
    public void testPossibleActions() {
        Equipment e = new Equipment("test", DEFAULT_EQUIPMENT_ACTIONS, Equipment.Effect.HEALTH, 100, new MagicAbility("Physical"));
        assertEquals(DEFAULT_EQUIPMENT_ACTIONS, e.getPossibleActions());
    }

    @Test
    @DisplayName("Checking that Equipment have Magic Ability")
    public void testEquipmentHaveMagicAbility() {
        Equipment e = new Equipment("test", DEFAULT_EQUIPMENT_ACTIONS, Equipment.Effect.HEALTH, 100, new MagicAbility("Magic"));
        assertEquals("Magic", e.getAbility());
    }

    @Test
    @DisplayName("Checking that Equipment have Physical Ability")
    public void testEquipmentHavePhysicalAbility() {
        Equipment e = new Equipment("test", DEFAULT_EQUIPMENT_ACTIONS, Equipment.Effect.DAMAGE, 100, new MagicAbility("Physical"));
        assertEquals("Physical", e.getAbility());
    }

    @Test
    @DisplayName("Check that damage has been modified")
    public void testDamageModified() {
        Equipment e = new Equipment("test", DEFAULT_EQUIPMENT_ACTIONS, Equipment.Effect.DAMAGE, 50, new MagicAbility("Magic"));
        e.damageModifier(60);
        assertEquals(50, e.getDamageOnEquipment());
    }

    @Test
    @DisplayName("Check if method throws exception when damageBar is 0 and equipment is destroyed")
    public void testEquipmentDisappearWhenDamageBarReachesZero(){
        Equipment e = new Equipment("Sword", DEFAULT_EQUIPMENT_ACTIONS, Equipment.Effect.DAMAGE, 50, new PhysicalAbility("Physical"));
        assertThrows(RuntimeException.class, () -> e.damageModifier(0));
    }
}
