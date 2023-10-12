import org.junit.jupiter.api.Test;

import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class RoomTest {
    static final Position DEFAULT_POSITION = new Position(0,0);

    static final Set<Interactable.InteractableAction> DEFAULT_NPC_ACTIONS = new HashSet<>(Arrays.asList(Interactable.InteractableAction.FIGHT, Interactable.InteractableAction.TALK));
    static final Set<Interactable.InteractableAction> DEFAULT_EQUIPMENT_ACTIONS = new HashSet<>(Arrays.asList(Interactable.InteractableAction.LOOT, Interactable.InteractableAction.DROP));
    static final NPC DEFAULT_NPC = new NPC("Harald", 100, 50, DEFAULT_NPC_ACTIONS);

    static final Equipment DEFAULT_EQUIPMENT = new Equipment("Sword", DEFAULT_EQUIPMENT_ACTIONS, Equipment.Effect.DAMAGE, 40, new PhysicalAbility("Slash"));
    static final InteractableInventory DEFAULT_INTERACTABLES = new InteractableInventory();

    static Room DEFAULT_ROOM;

    static final Integer DEFAULT_INTERACTABLE_ADDEND = 1;
    static final Integer DEFAULT_INTERACTABLE_SUBTRAHEND = 1;

    @Test
    public void testConstructorSetsCorrectPosition() {
        setUpDefaultInteractables();
        Room r = new Room(DEFAULT_POSITION, DEFAULT_INTERACTABLES);
        assertEquals(DEFAULT_POSITION, r.getPosition());
    }

    @Test
    public void testConstructorSetsInteractablesCorrectly() {
        setUpDefaultInteractables();
        Room r = new Room(DEFAULT_POSITION, DEFAULT_INTERACTABLES);
        assertEquals(DEFAULT_INTERACTABLES, r.getInteractables());

    }

    @Test
    public void testConstructorWithoutInteractableArgSetsEmptyRoom(){
        Room r = new Room(DEFAULT_POSITION);
        assertEquals(true, r.getInteractables().isEmpty());
    }


    @Test
    public void testConstructorThrowsNullPointerExceptionWhenPositionIsNull() {
        setUpDefaultInteractables();
        assertThrows(NullPointerException.class, () -> {
            new Room(null, DEFAULT_INTERACTABLES);});
    }

    @Test
    public void testConstructorThrowsNullPointerExceptionWhenInteractablesIsNull(){
        assertThrows(NullPointerException.class, () ->
                new Room(DEFAULT_POSITION, null));
    }


    private void setUpDefaultInteractables(){
        DEFAULT_INTERACTABLES.add(DEFAULT_EQUIPMENT, DEFAULT_INTERACTABLE_ADDEND);
        DEFAULT_INTERACTABLES.add(DEFAULT_NPC, DEFAULT_INTERACTABLE_ADDEND);
    }

    private void setUpDefaultRoom() {
        setUpDefaultInteractables();
        DEFAULT_ROOM = new Room(DEFAULT_POSITION, DEFAULT_INTERACTABLES);
    }



}
