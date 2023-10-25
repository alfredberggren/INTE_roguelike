import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.HashSet;
import java.util.Set;
import java.util.TreeMap;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class MapControllerTest {
    private MapController mapController;
    private static final Room TEST_ROOM = new Room(new Position(0, 0), new InteractableInventory());

    @BeforeEach
    public void setup() {
        mapController = new MapController();
    }

    @Test
    @DisplayName("Testar TreeMappen gameMap (compareTo i position) att positionsvärden inte skriver över varandra.")
    public void test_afterAddingPositionsToGameMap_gameMapHasAllPositions() {
        mapController.add(new Position(0, 0), TEST_ROOM);
        mapController.add(new Position(1, 0), TEST_ROOM);
        mapController.add(new Position(2, 0), TEST_ROOM);
        mapController.add(new Position(1, 1), TEST_ROOM);
        TreeMap<Position, Room> gameMap = mapController.getGameMap();
        Set<Position> expectedPositions = new HashSet<>();
        expectedPositions.add(new Position(0, 0));
        expectedPositions.add(new Position(1, 0));
        expectedPositions.add(new Position(2, 0));
        expectedPositions.add(new Position(1, 1));
        assertEquals(expectedPositions, gameMap.keySet(), "Det verkar som att positionsvärden i gameMap har skrivit över varandra.");
    }
}
