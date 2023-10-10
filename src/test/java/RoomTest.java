import org.junit.jupiter.api.Test;

import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class RoomTest {
    static final Position DEFAULT_POSITION = new Position(0,0);

    static final Set<Interactable.InteractableAction> DEFAULT_NPC_ACTIONS = new HashSet<>(Arrays.asList(Interactable.InteractableAction.FIGHT, Interactable.InteractableAction.TALK));
    static final Set<Interactable.InteractableAction> DEFAULT_EQUIPMENT_ACTIONS = new HashSet<>(Arrays.asList(Interactable.InteractableAction.LOOT, Interactable.InteractableAction.DROP));
    static final NPC DEFAULT_NPC = new NPC("Harald", 100, 50, DEFAULT_NPC_ACTIONS);

    static final Equipment DEFAULT_EQUIPMENT = new Equipment("Sword", DEFAULT_EQUIPMENT_ACTIONS, Equipment.Effect.DAMAGE, 40);
    static final HashMap<Interactable, Integer> DEFAULT_INTERACTABLES = new HashMap<>();

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
        setUpDefaultInteractables();
        Equipment e = new Equipment("Axe", DEFAULT_EQUIPMENT_ACTIONS, Equipment.Effect.DAMAGE, 20);
        Room r = new Room(DEFAULT_POSITION, DEFAULT_INTERACTABLES);
        r.addInteractable(e, 1);
        assertEquals(true, r.getInteractables().containsKey(e));
    }

    @Test
    public void testRemoveMethodRemovesOnlyOneInteractable(){

    }

    private void setUpDefaultInteractables(){
        DEFAULT_INTERACTABLES.put(DEFAULT_EQUIPMENT, 4);
        DEFAULT_INTERACTABLES.put(DEFAULT_NPC, 1);
    }



}
