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
        Set<Interactable.InteractableAction> possibleActions = new HashSet<>(Arrays.asList(Interactable.InteractableAction.FIGHT, Interactable.InteractableAction.TALK));
        Interactable i1 = new Prop("Tree", possibleActions);
        assertEquals(possibleActions, i1.getPossibleActions());
    }

    @Test
    @DisplayName("Testar en NPC-karaktärs actions")
    public void testNPCHasCorrectActions() {
        Set<Interactable.InteractableAction> possibleInteractableActions = new HashSet<>(Arrays.asList(Interactable.InteractableAction.FIGHT, Interactable.InteractableAction.TALK));
        Interactable i1 = new NPC("Fido", 10, 100, possibleInteractableActions);
        assertEquals(possibleInteractableActions, i1.getPossibleActions());
    }

    @Test
    @DisplayName("Testar Equipments actions")
    public void testEquipmentHasCorrectActions() {
        Set<Interactable.InteractableAction> possibleInteractableActions = new HashSet<>(Arrays.asList(Interactable.InteractableAction.LOOT, Interactable.InteractableAction.DROP, Interactable.InteractableAction.WEAR));
        Interactable i1 = new Equipment("Steel Sword", possibleInteractableActions, Equipment.Effect.HEALTH, 0, new PhysicalAbility("Physical"));
        assertEquals(possibleInteractableActions, i1.getPossibleActions());
    }

    @Test
    @DisplayName("Testar att en interactables inte har actions som vi inte gav den")
    public void interactableDoesNotHaveUnassignedActions() {
        Set<Interactable.InteractableAction> possibleInteractableActions = new HashSet<>(Arrays.asList(Interactable.InteractableAction.LOOT, Interactable.InteractableAction.WEAR));
        Interactable i1 = new Prop("Stone", possibleInteractableActions);
        assertFalse(i1.getPossibleActions().contains(Interactable.InteractableAction.FIGHT));
    }
}
