import org.junit.jupiter.api.*;

import java.util.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;

import static org.junit.jupiter.api.Assertions.*;

public class MapControllerTest {
    
    private MapController map;
    private Position centerPos, northPos, eastPos, southPos, westPos;
    private Room centerRoom, northRoom, eastRoom, southRoom, westRoom;
    private Character character;

    // För alfreds testfall
    private static final Room TEST_ROOM = new Room(new Position(15, 15), new InteractableInventory());
    private static final Position TEST_POSITION = new Position(15, 15);
    private static final Position NON_EXISTENT_TEST_POSITION = new Position(-100, 100);
    private static final Set<Position> TEST_POSITIONS = new HashSet<>() {{
        add(new Position(0, 0));
        add(new Position(1, 0));
        add(new Position(2, 0));
        add(new Position(1, 1));
    }};


    @BeforeEach
    public void init(){
        map = new MapController();

        centerPos = new Position(0, 0);
        northPos = new Position(0, 1);
        eastPos = new Position(1, 0);
        southPos = new Position(0, -1);
        westPos = new Position(-1, 0);

        centerRoom = new Room(centerPos);
        northRoom = new Room(northPos);
        eastRoom = new Room(eastPos);
        southRoom = new Room(southPos);
        westRoom = new Room(westPos);

        map.add(centerPos, centerRoom);
        map.add(northPos, northRoom);
        map.add(eastPos, eastRoom);
        map.add(southPos, southRoom);
        map.add(westPos, westRoom);

        character = new Character("null", 1, 1, 0, centerPos, new TextIO());
        centerRoom.getInteractables().add(character);

        for (Position p : TEST_POSITIONS) {
            map.add(p, new Room(p, new InteractableInventory()));
        }
    }


    @Test
    public void whenCardinalDirectionIsNorth_thenCharacterIsMovedNorth(){
        map.moveCharacter(CardinalDirection.NORTH, character);
        assertEquals(northPos, character.getPosition());
    }

    @Test
    public void whenCardinalDirectionIsEast_thenCharacterIsMovedEast(){
        map.moveCharacter(CardinalDirection.EAST, character);
        assertEquals(eastPos, character.getPosition());
    }

    @Test
    public void whenCardinalDirectionIsSouth_thenCharacterIsMovedSouth(){
        map.moveCharacter(CardinalDirection.SOUTH, character);
        assertEquals(southPos, character.getPosition());
    }

    @Test
    public void whenCardinalDirectionIsWest_thenCharacterIsMovedWest(){
        map.moveCharacter(CardinalDirection.WEST, character);
        assertEquals(westPos, character.getPosition());
    }

    @Test
    public void whenCharacterIsMoved_thenCharacterIsInNewRoom(){
        map.moveCharacter(CardinalDirection.NORTH, character);
        assertTrue(northRoom.getInteractables().contains(character));
    }

    @Test
    public void whenCharacterIsMoved_thenCharacterIsNotInFormerRoom(){
        map.moveCharacter(CardinalDirection.NORTH, character);
        assertFalse(centerRoom.getInteractables().contains(character));
    }

    @Test
    @DisplayName("Testar att metoden add lägger till ett rum i gameMap på rätt position")
    public void test_afterAddingRoomsToGameMap_gameMapHasRoom() {
        map.add(TEST_POSITION, TEST_ROOM);
        assertEquals(TEST_ROOM, map.getRoom(TEST_POSITION), "MapControllern har inte rummet " + TEST_ROOM + " på korrekt position i kartan.");
    }

    @Test
    @DisplayName("Testar att lägga in ett rum som är null (ska kasta undantag)")
    public void test_whenAddingANullRoom_ExceptionIsThrown() {
        assertThrows(IllegalArgumentException.class, () -> {
            map.add(TEST_POSITION, null);
        }, "Metoden kastade inte IllegalArgumentException för ett rum som är null!");
    }

    @Test
    @DisplayName("Testar att lägga in rum på en position som redan finns (ska kasta undantag)")
    public void test_whenAddingARoomThatAlreadyExists_exceptionIsThrown() {
        assertThrows(IllegalArgumentException.class, () -> {
            map.add(TEST_POSITION, TEST_ROOM);
            map.add(TEST_POSITION, TEST_ROOM);
        }, "Metoden kastade inte IllegalArgumentException för ett rum som redan fanns!");
    }

    @Test
    @DisplayName("Testar att lägga in en position som är null (ska kasta undantag)")
    public void test_whenAddingANullPosition_ExceptionIsThrown() {
        assertThrows(IllegalArgumentException.class, () -> {
            map.add(null, TEST_ROOM);
        }, "Metoden kastade inte IllegalArgumentException för en position som är null!");
    }

    @Test
    @DisplayName("Testar att skicka null till metoden roomExists (ska kasta undantag)")
    public void test_whenSendingANullPositionToRoomExists_ExceptionIsThrown() {
        assertThrows(IllegalArgumentException.class, () -> {
            map.roomExists(null);
        }, "Metoden roomExists kastade inte IllegalArgumentException för ett rum som är null!");
    }

    @Test
    @DisplayName("Testar att skicka null till metoden getAvailableDirections (ska kasta undantag)")
    public void test_whenSendingANullPositionToGetAvailableDirections_ExceptionIsThrown() {
        assertThrows(IllegalArgumentException.class, () -> {
            map.getAvailableDirections(null);
        }, "Metoden getAvailableDirections kastade inte IllegalArgumentException för en position som är null!");
    }

    @Test
    @DisplayName("Testar att skicka in en position som inte finns på kartan till checkAvailableDirections (ska kasta undantag)")
    public void test_whenSendingANonexistentPositionToGetAvailableDirections_ExceptionIsThrown() {
        assertThrows(IllegalArgumentException.class, () -> {
            map.getAvailableDirections(NON_EXISTENT_TEST_POSITION);
        }, "Metoden getAvailableDirections kastade inte IllegalArgumentException för en position som inte finns!");
    }

    @Test
    @DisplayName("Testar metoden getRoom")
    public void test_whenCallingGetRoom_returnsCorrectRoom() {
        map.add(TEST_POSITION, TEST_ROOM);
        assertEquals(TEST_ROOM, map.getRoom(TEST_POSITION));
    }

    @Test
    @DisplayName("Testar att skicka null till metoden containsInteractable (ska kasta undantag)")
    public void test_whenPassingNullToContainsInteractable_ExceptionIsThrown() {
        assertThrows(IllegalArgumentException.class, () -> {
            map.containsInteractable(null);
        }, "Metoden containsInteractable kastar inte undantag när man skickar in null till metoden!");
    }

    @Test
    @DisplayName("Testar getAdjacentRooms med ett värde som är null")
    public void test_whenPassingNullToGetAdjacentRooms_exceptionIsThrown() {
        assertThrows(IllegalArgumentException.class, () -> {
            map.getAdjacentRooms(null);
        }, "Metoden getAdjacentRooms kastar inte undantag när man skickar in null till metoden!");
    }

    @Test
    @DisplayName("Testar TreeMappen gameMap (compareTo i position) att positionsvärden inte skriver över varandra.")
    public void test_afterAddingPositionsToGameMap_gameMapHasAllPositions() {
        TreeMap<Position, Room> gameMap = map.getGameMap();
        assertEquals(TEST_POSITIONS, gameMap.keySet(), "Det verkar som att positionsvärden i gameMap har skrivit över varandra.");
    }
}
