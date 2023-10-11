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
    static final HashMap<Interactable, Integer> DEFAULT_INTERACTABLES = new HashMap<>();

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

    @Test
    public void testAddMethodAddsInteractableToRoom(){
        setUpDefaultRoom();
        Equipment e = new Equipment("Axe", DEFAULT_EQUIPMENT_ACTIONS, Equipment.Effect.DAMAGE, 20, new PhysicalAbility("Slash"));
        DEFAULT_ROOM.addInteractable(e, DEFAULT_INTERACTABLE_ADDEND);
        assertEquals(true, DEFAULT_ROOM.getInteractables().containsKey(e));
    }

    @Test
    public void testAddMethodIncreasesValueOfAlreadyExistingInteractable() {
        setUpDefaultRoom();
        DEFAULT_ROOM.addInteractable(DEFAULT_EQUIPMENT, DEFAULT_INTERACTABLE_ADDEND);
        assertEquals(2, DEFAULT_ROOM.getInteractables().get(DEFAULT_EQUIPMENT));
    }

    @Test
    public void testAddMethodThrowsWhenInteractableArgIsNull(){
        setUpDefaultRoom();
        assertThrows(NullPointerException.class, () -> DEFAULT_ROOM.addInteractable(null));
    }

    @Test
    public void testAddMethodThrowsWhenIntegerArgIsNull(){
        setUpDefaultRoom();
        assertThrows(NullPointerException.class, () -> DEFAULT_ROOM.addInteractable(DEFAULT_EQUIPMENT, null));
    }

    @Test
    public void testAddMethodOnlyAcceptsPositiveIntegers() {
        setUpDefaultRoom();
        assertThrows(IllegalArgumentException.class, () -> DEFAULT_ROOM.addInteractable(DEFAULT_EQUIPMENT, -1));
    }

    @Test
    public void testRemoveMethodRemovesAllCopiesOfInteractable(){
        setUpDefaultRoom();
        DEFAULT_ROOM.addInteractable(DEFAULT_EQUIPMENT,  DEFAULT_INTERACTABLE_ADDEND);
        DEFAULT_ROOM.removeInteractable(DEFAULT_EQUIPMENT);
        assertEquals(false, DEFAULT_ROOM.getInteractables().containsKey(DEFAULT_EQUIPMENT));
    }

    @Test
    public void testRemoveMethodWithAmountRemovesCorrectAmount(){
        setUpDefaultRoom();
        DEFAULT_ROOM .removeInteractable(DEFAULT_EQUIPMENT, DEFAULT_INTERACTABLE_SUBTRAHEND);
        assertEquals(0, DEFAULT_ROOM.getInteractables().get(DEFAULT_EQUIPMENT));
    }

    private void setUpDefaultInteractables(){
        DEFAULT_INTERACTABLES.put(DEFAULT_EQUIPMENT, DEFAULT_INTERACTABLE_ADDEND);
        DEFAULT_INTERACTABLES.put(DEFAULT_NPC, DEFAULT_INTERACTABLE_ADDEND);
    }

    private void setUpDefaultRoom() {
        setUpDefaultInteractables();
        DEFAULT_ROOM = new Room(DEFAULT_POSITION, DEFAULT_INTERACTABLES);
    }



}
