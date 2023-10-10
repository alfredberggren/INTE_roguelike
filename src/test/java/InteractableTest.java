import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

public class InteractableTest {
    @Test
    @DisplayName("Testar att skapa en icke-levande entitet och kollar om den har korrekt actions")
    public void testNLEHasCorrectActions() {
        Set<Interactable.Action> possibleActions = new HashSet<>(Arrays.asList(Interactable.Action.FIGHT, Interactable.Action.TALK));
        Interactable i1 = new Prop("Tree", possibleActions);
        assertEquals(possibleActions, i1.getPossibleActions());
    }

    @Test
    @DisplayName("Testar en NPC-karaktärs actions")
    public void testNPCHasCorrectActions() {
        Set<Interactable.Action> possibleActions = new HashSet<>(Arrays.asList(Interactable.Action.FIGHT, Interactable.Action.TALK));
        Interactable i1 = new NPC("Fido", 10, 100, possibleActions);
        assertEquals(possibleActions, i1.getPossibleActions());
    }

    @Test
    @DisplayName("Testar Equipments actions")
    public void testEquipmentHasCorrectActions() {
        Set<Interactable.Action> possibleActions = new HashSet<>(Arrays.asList(Interactable.Action.LOOT, Interactable.Action.DROP, Interactable.Action.WEAR));
        Interactable i1 = new Equipment("Steel Sword", possibleActions, Equipment.Effect.HEALTH, 0, new MagicAbility("Magic"));
        assertEquals(possibleActions, i1.getPossibleActions());
    }

    @Test
    @DisplayName("Testar att en interactables inte har actions som vi inte gav den")
    public void interactableDoesNotHaveUnassignedActions() {
        Set<Interactable.Action> possibleActions = new HashSet<>(Arrays.asList(Interactable.Action.LOOT, Interactable.Action.WEAR));
        Interactable i1 = new Prop("Stone", possibleActions);
        assertFalse(i1.getPossibleActions().contains(Interactable.Action.FIGHT));
    }
}
