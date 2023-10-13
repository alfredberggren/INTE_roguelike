
import org.junit.jupiter.api.Test;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

public class RoomTest {
    static final Position DEFAULT_POSITION = new Position(0,0);

    static final Set<Interactable.InteractableAction> DEFAULT_NPC_ACTIONS = new HashSet<>(Arrays.asList(Interactable.InteractableAction.FIGHT, Interactable.InteractableAction.TALK));
    static final Set<Interactable.InteractableAction> DEFAULT_EQUIPMENT_ACTIONS = new HashSet<>(Arrays.asList(Interactable.InteractableAction.LOOT, Interactable.InteractableAction.DROP));
    static final NPC DEFAULT_NPC = new NPC("Harald", 100, 50, DEFAULT_NPC_ACTIONS);

    static final Equipment DEFAULT_EQUIPMENT = new Equipment("Sword", DEFAULT_EQUIPMENT_ACTIONS, Equipment.Effect.DAMAGE, 40, new PhysicalAbility("Slash", 10, "Ability"));
    static final InteractableInventory DEFAULT_INTERACTABLES = new InteractableInventory();

    static final ArrayList<CardinalDirection> DEFAULT_DIRECTIONS = new ArrayList<>(Arrays.asList(CardinalDirection.NORTH, CardinalDirection.SOUTH));

    static Room DEFAULT_ROOM;


    //TODO: might need more rigourous testing

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
    public void testSetDirectionsSetsDirectionsCorrectly(){
        setUpDefaultRoom();
        DEFAULT_ROOM.setPossibleRoutes(DEFAULT_DIRECTIONS);
        for (int i = 0; i < DEFAULT_DIRECTIONS.size(); i++){
            assertEquals(DEFAULT_DIRECTIONS.get(i), DEFAULT_ROOM.getPossibleRoutes().get(i));
        }
    }

    @Test
    public void testAddDirectionSetsDirectionCorrectly(){
        setUpDefaultRoom();
        DEFAULT_ROOM.addPossibleRoute(CardinalDirection.NORTH);
        assertEquals(CardinalDirection.NORTH, DEFAULT_ROOM.getPossibleRoutes().get(0));
    }


    private void setUpDefaultInteractables(){
        DEFAULT_INTERACTABLES.add(DEFAULT_EQUIPMENT, DEFAULT_NPC);
    }

    private void setUpDefaultRoom() {
        setUpDefaultInteractables();
        DEFAULT_ROOM = new Room(DEFAULT_POSITION, DEFAULT_INTERACTABLES);
    }



}

