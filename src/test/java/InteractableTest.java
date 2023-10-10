import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class InteractableTest {
    @Test
    @DisplayName("Testar att skapa en icke-levande entitet")
    public void testInteractables() {
        Set<Interactable.Action> possibleActions = new HashSet<>();
        possibleActions.add(Interactable.Action.FIGHT);
        possibleActions.add(Interactable.Action.TALK);
        Interactable i1 = new Prop("Tree", possibleActions);
        assertEquals(possibleActions, i1.getPossibleActions());
    }

    @Test
    @DisplayName("Testar en NPC-karaktärs interactables")
    public void testCreateNPC() {
        Set<Interactable.Action> possibleActions = new HashSet<>(Arrays.asList(Interactable.Action.FIGHT, Interactable.Action.TALK));
        NPC n1 = new NPC("Fido", 10, 100, possibleActions);
        assertEquals(possibleActions, n1.getPossibleActions());
    }
}
