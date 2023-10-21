import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

public class InteractableTest {
    static final Position DEFAULT_POSITION = new Position(0,0);
    static final Set<Interactable.InteractableAction> DEFAULT_INTERACTABLE_ACTIONS = new HashSet<>(Arrays.asList(
            Interactable.InteractableAction.LOOT,
            Interactable.InteractableAction.DROP,
            Interactable.InteractableAction.USE)
    );

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
        Interactable i1 = new NPC("Fido", 10, 100,1,DEFAULT_POSITION, new TextIO());
        assertEquals(possibleInteractableActions, i1.getPossibleActions());
    }

    @Test
    @DisplayName("Testar Equipments actions")
    public void testEquipmentHasCorrectActions() {
        Set<Interactable.InteractableAction> possibleInteractableActions = new HashSet<>(Arrays.asList(Interactable.InteractableAction.LOOT, Interactable.InteractableAction.DROP, Interactable.InteractableAction.WEAR));
        Interactable i1 = new Equipment("Steel Sword", EquipmentSlot.RIGHT_HAND, possibleInteractableActions, Equipment.Effect.HEALTH, 0, new PhysicalAbility("SuperArrow",15,1));
        assertEquals(possibleInteractableActions, i1.getPossibleActions());
    }

    @Test
    @DisplayName("Testar att en interactables inte har actions som vi inte gav den")
    public void testInteractableDoesNotHaveUnassignedActions() {
        Set<Interactable.InteractableAction> possibleInteractableActions = new HashSet<>(Arrays.asList(Interactable.InteractableAction.LOOT, Interactable.InteractableAction.WEAR));
        Interactable i1 = new Prop("Stone", possibleInteractableActions);
        assertFalse(i1.getPossibleActions().contains(Interactable.InteractableAction.FIGHT));
    }

    @Test
    @DisplayName("Interactables som har en nullad lista av möjliga handlingar ska kasta undantag")
    public void testNullPossibleActionsThrowsException() {
        assertThrows(NullPointerException.class, () -> {
            Interactable i1 = new Prop("test", null);
        });
    }

    @Test
    @DisplayName("Två props med samma namn ska vara lika")
    public void testTwoPropsWithSameNameAreEqual() {
        assertEquals(new Prop("test123", DEFAULT_INTERACTABLE_ACTIONS), new Prop("test123", DEFAULT_INTERACTABLE_ACTIONS));
    }

    @Test
    @DisplayName("Två props med samma namn ska ha samma hashCode")
    public void testTwoPropsWithSameNameShouldHaveSameHashCode() {
        assertEquals(new Prop("test123", DEFAULT_INTERACTABLE_ACTIONS).hashCode(), new Prop("test123", DEFAULT_INTERACTABLE_ACTIONS).hashCode());
    }

    @Test
    @DisplayName("Två props som är olika")
    public void testTwoUnequalProps() {
        assertNotEquals(new Prop("test1234", DEFAULT_INTERACTABLE_ACTIONS), new Prop("test123", DEFAULT_INTERACTABLE_ACTIONS));
    }

    @Test
    @DisplayName("Två olika props har olika hashCode")
    public void testTwoUnequalPropsShouldHaveUnequalHashCodes() {
        assertNotEquals(new Prop("test1234", DEFAULT_INTERACTABLE_ACTIONS).hashCode(), new Prop("test123", DEFAULT_INTERACTABLE_ACTIONS).hashCode());
    }

    @Test
    @DisplayName("Två NPC:s med samma namn ska vara lika")
    public void testTwoNPCsWithSameNameAreEqual() {
        assertEquals(new NPC("test123", 10, 10,1, DEFAULT_POSITION, new TextIO()), new NPC("test123", 13, 23,1, DEFAULT_POSITION, new TextIO()));
    }

    @Test
    @DisplayName("Två NPC:s med samma namn ska ha samma hashCode")
    public void testTwoNPCsWithSameNameShouldHaveSameHashCode() {
        assertEquals(new NPC("test123", 10, 10,1, DEFAULT_POSITION, new TextIO()).hashCode(), new NPC("test123", 13, 23,1,DEFAULT_POSITION, new TextIO()).hashCode());
    }

    @Test
    @DisplayName("Två NPCs som är olika")
    public void testTwoUnequalNPCs() {
        assertNotEquals(new NPC("test1234", 10, 10,1, DEFAULT_POSITION, new TextIO()), new NPC("test123", 13, 23,1, new Position(3,6), new TextIO()));
    }

    @Test
    @DisplayName("Två olika NPCs har olika hashCode")
    public void testTwoUnequalNPCsShouldHaveUnequalHashCodes() {
        assertNotEquals(new NPC("test1234", 10, 10,1, DEFAULT_POSITION, new TextIO()).hashCode(), new NPC("test123", 13, 23, 1,DEFAULT_POSITION, new TextIO()).hashCode());
    }
}
