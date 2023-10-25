import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;

import java.util.*;
import java.util.stream.Stream;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class MapControllerTest {
    private MapController mapController;
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
    public void setup() {
        mapController = new MapController();
        for (Position p : TEST_POSITIONS) {
            mapController.add(p, new Room(p, new InteractableInventory()));
        }
    }

    @Test
    @DisplayName("Testar att metoden add lägger till ett rum i gameMap på rätt position")
    public void test_afterAddingRoomsToGameMap_gameMapHasRoom() {
        mapController.add(TEST_POSITION, TEST_ROOM);
        assertEquals(TEST_ROOM, mapController.getRoom(TEST_POSITION), "MapControllern har inte rummet " + TEST_ROOM + " på korrekt position i kartan.");
    }

    @Test
    @DisplayName("Testar att lägga in ett rum som är null (ska kasta undantag)")
    public void test_whenAddingANullRoom_ExceptionIsThrown() {
        assertThrows(IllegalArgumentException.class, () -> {
            mapController.add(TEST_POSITION, null);
        }, "Metoden kastade inte IllegalArgumentException för ett rum som är null!");
    }

    @Test
    @DisplayName("Testar att lägga in rum på en position som redan finns (ska kasta undantag)")
    public void test_whenAddingARoomThatAlreadyExists_exceptionIsThrown() {
        assertThrows(IllegalArgumentException.class, () -> {
            mapController.add(TEST_POSITION, TEST_ROOM);
            mapController.add(TEST_POSITION, TEST_ROOM);
        }, "Metoden kastade inte IllegalArgumentException för ett rum som redan fanns!");
    }

    @Test
    @DisplayName("Testar att lägga in en position som är null (ska kasta undantag)")
    public void test_whenAddingANullPosition_ExceptionIsThrown() {
        assertThrows(IllegalArgumentException.class, () -> {
            mapController.add(null, TEST_ROOM);
        }, "Metoden kastade inte IllegalArgumentException för en position som är null!");
    }

    @Test
    @DisplayName("Testar att skicka null till metoden roomExists (ska kasta undantag)")
    public void test_whenSendingANullPositionToRoomExists_ExceptionIsThrown() {
        assertThrows(IllegalArgumentException.class, () -> {
            mapController.roomExists(null);
        }, "Metoden roomExists kastade inte IllegalArgumentException för ett rum som är null!");
    }

    @Test
    @DisplayName("Testar att skicka null till metoden getAvailableDirections (ska kasta undantag)")
    public void test_whenSendingANullPositionToGetAvailableDirections_ExceptionIsThrown() {
        assertThrows(IllegalArgumentException.class, () -> {
            mapController.getAvailableDirections(null);
        }, "Metoden getAvailableDirections kastade inte IllegalArgumentException för en position som är null!");
    }

    @Test
    @DisplayName("Testar att skicka in en position som inte finns på kartan till checkAvailableDirections (ska kasta undantag)")
    public void test_whenSendingANonexistentPositionToGetAvailableDirections_ExceptionIsThrown() {
        assertThrows(IllegalArgumentException.class, () -> {
            mapController.getAvailableDirections(NON_EXISTENT_TEST_POSITION);
        }, "Metoden getAvailableDirections kastade inte IllegalArgumentException för en position som inte finns!");
    }

    @Test
    @DisplayName("Testar metoden getRoom")
    public void test_whenCallingGetRoom_returnsCorrectRoom() {
        mapController.add(TEST_POSITION, TEST_ROOM);
        assertEquals(TEST_ROOM, mapController.getRoom(TEST_POSITION));
    }

    @Test
    @DisplayName("Testar att skicka null till metoden containsInteractable (ska kasta undantag)")
    public void test_whenPassingNullToContainsInteractable_ExceptionIsThrown() {
        assertThrows(IllegalArgumentException.class, () -> {
            mapController.containsInteractable(null);
        }, "Metoden containsInteractable kastar inte undantag när man skickar in null till metoden!");
    }

    @Test
    @DisplayName("Testar getAdjacentRooms med ett värde som är null")
    public void test_whenPassingNullToGetAdjacentRooms_exceptionIsThrown() {
        assertThrows(IllegalArgumentException.class, () -> {
            mapController.getAdjacentRooms(null);
        }, "Metoden getAdjacentRooms kastar inte undantag när man skickar in null till metoden!");
    }

    @Test
    @DisplayName("Testar TreeMappen gameMap (compareTo i position) att positionsvärden inte skriver över varandra.")
    public void test_afterAddingPositionsToGameMap_gameMapHasAllPositions() {
        TreeMap<Position, Room> gameMap = mapController.getGameMap();
        assertEquals(TEST_POSITIONS, gameMap.keySet(), "Det verkar som att positionsvärden i gameMap har skrivit över varandra.");
    }
}
