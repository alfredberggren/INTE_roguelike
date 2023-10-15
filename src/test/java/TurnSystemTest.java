import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.ArrayList;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class TurnSystemTest {

    @Mock 
    IO io;

    /*
     * Move Tests
    */

    @Test
    public void whenMoveCharacterAndNoMoveAvailable_thenISEThrown() {
        Map worldMap = new Map();
        Position playerPosition = new Position(0, 0);
        Room playerRoom = new Room(playerPosition);
        playerRoom.setPossibleRoutes(new ArrayList<CardinalDirection>());
        worldMap.addRoom(playerPosition, playerRoom);

        IO io = new TextUI();
        TurnSystem turnSystem = new TurnSystem(io);
        Player player = new Player(1, 1, playerPosition);

        assertThrows(IllegalStateException.class, () -> {
            turnSystem.move(player, worldMap);
        });
    }

    @Test
    public void whenMoveCharacter_thenCharacterHasMoved(){
        TurnSystem turnSystem = new TurnSystem(io);
        Map worldMap = new Map();
        Position originalPosition = new Position(0, 0);
        Room originalRoom = new Room(originalPosition);
        Position otherPosition = new Position(0, 1);
        Room otherRoom = new Room(originalPosition);

        ArrayList<CardinalDirection> routes = new ArrayList<>();
        routes.add(CardinalDirection.NORTH);
        originalRoom.setPossibleRoutes(routes);

        worldMap.addRoom(originalPosition, originalRoom);
        worldMap.addRoom(otherPosition, otherRoom);
        Player player = new Player(1, 1, originalPosition);
                
        Mockito.when(io.requestMove(worldMap, player)).thenReturn(CardinalDirection.NORTH);
        turnSystem.move(player, worldMap);
        assertEquals(otherPosition, player.getPosition(), "Player did not change position");
    }

    @Test
    public void whenMoveCharacterReRequestedTwoTimes_thenCharacterHasMoved(){
        TurnSystem turnSystem = new TurnSystem(io);
        Map worldMap = new Map();
        Position originalPosition = new Position(0, 0);
        Room originalRoom = new Room(originalPosition);
        Position otherPosition = new Position(0, 1);
        Room otherRoom = new Room(originalPosition);

        ArrayList<CardinalDirection> routes = new ArrayList<>();
        routes.add(CardinalDirection.NORTH);
        originalRoom.setPossibleRoutes(routes);

        worldMap.addRoom(originalPosition, originalRoom);
        worldMap.addRoom(otherPosition, otherRoom);
        Player player = new Player(1, 1, originalPosition);
                
        Mockito.when(io.requestMove(worldMap, player)).thenReturn(CardinalDirection.SOUTH);
        Mockito.when(io.requestAnotherMove(worldMap, player)).thenReturn(CardinalDirection.EAST, CardinalDirection.NORTH);
        turnSystem.move(player, worldMap);
        assertEquals(otherPosition, player.getPosition(), "Player did not change position");
    }

    @Test
    public void whenMoveCharacterReRequested_thenIAEThrown(){
        TurnSystem turnSystem = new TurnSystem(io);
        Map worldMap = new Map();
        Position originalPosition = new Position(0, 0);
        Room originalRoom = new Room(originalPosition);
        Position otherPosition = new Position(0, 1);
        Room otherRoom = new Room(originalPosition);

        ArrayList<CardinalDirection> routes = new ArrayList<>();
        routes.add(CardinalDirection.NORTH);
        originalRoom.setPossibleRoutes(routes);

        worldMap.addRoom(originalPosition, originalRoom);
        worldMap.addRoom(otherPosition, otherRoom);
        Player player = new Player(1, 1, originalPosition);
                
        Mockito.when(io.requestMove(worldMap, player)).thenReturn(CardinalDirection.SOUTH);
        Mockito.when(io.requestAnotherMove(worldMap, player)).thenThrow(IllegalArgumentException.class);
        assertThrows(IllegalStateException.class, () -> {
            turnSystem.move(player, worldMap);
        }, "Wrong input doesn't Throw Illegal Argument Exception");
    }

}