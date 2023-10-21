
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.when;

import java.util.concurrent.TimeUnit;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class TurnSystemTest {

    MapController worldMapController;
    Position characterPosition;
    Room characterRoom;

    TurnSystem turnSystem;
    Character character;

    @Mock 
    IO io;

    @BeforeEach
    public void init(){
        worldMapController = new MapController();
        characterPosition = new Position(0, 0);
        characterRoom = new Room(characterPosition);
        worldMapController.add(characterPosition, characterRoom);

        turnSystem = new TurnSystem(io);
        character = new Character("name", 1, 1, characterPosition, io);

    }

    
    /*
     *
     * Move Tests
     * 
    */


    @Test
    public void whenMoveAndNoMoveAvailable_thenReturnFalse() {
        assertFalse(turnSystem.move(character, worldMapController), "move() did not return false when no move was available");
    }

    @Test
    public void whenMove_thenCharacterHasMoved(){
        Position otherPosition = new Position(0, 1);
        Room otherRoom = new Room(otherPosition);
        worldMapController.add(otherPosition, otherRoom);
        
        when(io.requestMove(worldMapController, character)).thenReturn(CardinalDirection.NORTH);

        assertTrue(turnSystem.move(character, worldMapController));
        assertEquals(otherPosition, character.getPosition(), "Player did not change position");
    }

    @Test
    public void whenMoveReRequestedTwoTimes_thenCharacterHasMoved(){
        Position otherPosition = new Position(0, 1);
        Room otherRoom = new Room(otherPosition);
        worldMapController.add(otherPosition, otherRoom);
                
        when(io.requestMove(worldMapController, character)).thenReturn(CardinalDirection.SOUTH);
        when(io.requestAnotherMove(worldMapController, character)).thenReturn(CardinalDirection.EAST, CardinalDirection.NORTH);

        assertTrue(turnSystem.move(character, worldMapController));
        assertEquals(otherPosition, character.getPosition(), "Player did not change position");
    }

    @Test
    public void whenMoveHasWrongInput_thenReturnFalse(){
        Position otherPosition = new Position(0, 1);
        Room otherRoom = new Room(otherPosition);
        worldMapController.add(otherPosition, otherRoom);
                
        when(io.requestMove(worldMapController, character)).thenThrow(IllegalArgumentException.class);

        assertFalse(turnSystem.move(character, worldMapController), "move() did not return false when wrong input was given");
    }

    @Test
    public void whenMoveHasWrongInputInReRequest_thenReturnFalse(){
        Position otherPosition = new Position(0, 1);
        Room otherRoom = new Room(otherPosition);
        worldMapController.add(otherPosition, otherRoom);
          
        when(io.requestMove(worldMapController, character)).thenReturn(CardinalDirection.SOUTH);
        when(io.requestAnotherMove(worldMapController, character)).thenThrow(IllegalArgumentException.class);

        assertFalse(turnSystem.move(character, worldMapController), "move() did not return false when wrong input was given");
    }

    
    /*
     * 
     * Start Turn tests
     * 
     * Command test:
    */


    @Test
    @Timeout(value = 15, unit = TimeUnit.SECONDS)
    public void whenCommandEndTurnIsCalled_thenTurnEndedIsTrue() throws InterruptedException {
        Thread.sleep(1_000);

        when(io.requestTurnCommand(worldMapController, character, character.getSpeed(), character.getSpeed())).thenReturn(TurnSystem.TurnCommand.END);
        
        turnSystem.startTurn(worldMapController, character, character.getSpeed());
        assertTrue(turnSystem.hasDoneTurn(), "Turn hasn't ended");
    }

    @Test
    @Timeout(value = 15, unit = TimeUnit.SECONDS, threadMode = Timeout.ThreadMode.SEPARATE_THREAD)
    public void whenCommandMoveIsCalledWhenOutOfMoves_thenCommandIsReRequested() throws InterruptedException {
        Thread.sleep(1_000);

        //when character has 0 speed it can do neither action or movement
        character = new Character("name", 1, 0, characterPosition, io);

        when(io.requestTurnCommand(worldMapController, character, character.getSpeed(), character.getSpeed())).thenReturn(TurnSystem.TurnCommand.MOVE);
        when(io.requestAnotherTurnCommand(worldMapController, character, character.getSpeed(), character.getSpeed())).thenReturn(TurnSystem.TurnCommand.END);
        
        turnSystem.startTurn(worldMapController, character, character.getSpeed());
        assertTrue(turnSystem.hasDoneTurn(), "Turn hasn't ended");
    }

    @Test
    @Timeout(value = 15, unit = TimeUnit.SECONDS)
    public void whenCommandActionIsCalledWhenOutOfActions_thenCommandIsReRequested() throws InterruptedException {
        Thread.sleep(5_000);

        //when character has 0 speed it can do neither action or movement
        character = new Character("name", 1, 0, characterPosition, io);

        when(io.requestTurnCommand(worldMapController, character, character.getSpeed(), character.getSpeed())).thenReturn(TurnSystem.TurnCommand.ACTION);
        when(io.requestAnotherTurnCommand(worldMapController, character, character.getSpeed(), character.getSpeed())).thenReturn(TurnSystem.TurnCommand.END);

        turnSystem.startTurn(worldMapController, character, character.getSpeed());
        assertTrue(turnSystem.hasDoneTurn(), "Turn hasn't ended");
    }

    @Test
    @Timeout(value = 15, unit = TimeUnit.SECONDS)
    public void whenWrongInput_thenCommandIsReRequested() throws InterruptedException {
        Thread.sleep(5_000);

        character = new Character("name", 1, 1, characterPosition, io);

        when(io.requestTurnCommand(worldMapController, character, character.getSpeed(), character.getSpeed())).thenThrow(IllegalArgumentException.class);
        when(io.requestAnotherTurnCommand(worldMapController, character, character.getSpeed(), character.getSpeed())).thenReturn(TurnSystem.TurnCommand.END);
        
        turnSystem.startTurn(worldMapController, character, character.getSpeed());
        assertTrue(turnSystem.hasDoneTurn(), "Turn hasn't ended");
    }

    
    /*
     * Start turn successful action/move
    */

    @Test
    @Timeout(value = 15, unit = TimeUnit.SECONDS)
    public void whenCommandMoveAndCharacterMoved_thenMovesHasDecreased() throws InterruptedException {
        Thread.sleep(10_000);

        when(io.requestTurnCommand(worldMapController, character, character.getSpeed(), character.getSpeed())).thenReturn(TurnSystem.TurnCommand.MOVE);
        when(io.requestAnotherTurnCommand(worldMapController, character, character.getSpeed(), character.getSpeed())).thenReturn(TurnSystem.TurnCommand.END);

        TurnSystem spyTurnSystem = spy(turnSystem);
        when(spyTurnSystem.move(character, worldMapController)).thenReturn(true);

        spyTurnSystem.startTurn(worldMapController, character, character.getSpeed());
        assertTrue(spyTurnSystem.hasDoneTurn(), "Turn hasn't ended");    
    }

    @Test
    @Timeout(value = 15, unit = TimeUnit.SECONDS)
    public void whenCommandActionAndCharacterDoesAction_thenActionsHasDecreased() throws InterruptedException {
        Thread.sleep(10_000);

        when(io.requestTurnCommand(worldMapController, character, character.getSpeed(), character.getSpeed())).thenReturn(TurnSystem.TurnCommand.ACTION);
        when(io.requestAnotherTurnCommand(worldMapController, character, character.getSpeed(), character.getSpeed())).thenReturn(TurnSystem.TurnCommand.END);

        TurnSystem spyTurnSystem = spy(turnSystem);
        when(spyTurnSystem.action(character, worldMapController)).thenReturn(true);

        spyTurnSystem.startTurn(worldMapController, character, character.getSpeed());
        assertTrue(spyTurnSystem.hasDoneTurn(), "Turn hasn't ended");    
    }

    /*
     * Start turn doneTurn/dead
    */

    @Test
    @Timeout(value = 15, unit = TimeUnit.SECONDS)
    public void whenStartTurnAndHasDoneTurn_thenISEThrown() throws InterruptedException {
        Thread.sleep(10_000);

        when(io.requestTurnCommand(worldMapController, character, character.getSpeed(), character.getSpeed())).thenReturn(TurnSystem.TurnCommand.END);
        turnSystem.startTurn(worldMapController, character, character.getSpeed());

        assertThrows(IllegalStateException.class, () -> {
            turnSystem.startTurn(worldMapController, character, character.getSpeed());
        });
    }

    @Test
    @Timeout(value = 15, unit = TimeUnit.SECONDS)
    public void whenStartTurnAndCharacterIdDead_thenISEThrown() throws InterruptedException {
        Thread.sleep(10_000);
        
        character.decreaseHealth(character.getHealth());

        assertThrows(IllegalStateException.class, () -> {
            turnSystem.startTurn(worldMapController, character, character.getSpeed());
        });    
    }


    /*
     * resetTurns test
    */

    @Test
    public void whenResetTurn_thenAllTurnsDoneTurnSetToFalse() throws InterruptedException {        
        Character otherCharacter = new Character("other Character", 1, 1, characterPosition, io);

        when(io.requestTurnCommand(worldMapController, character, character.getSpeed(), character.getSpeed())).thenReturn(TurnSystem.TurnCommand.END);

        character.getTurnSystem().startTurn(worldMapController, character, character.getSpeed());
        otherCharacter.getTurnSystem().startTurn(worldMapController, character, otherCharacter.getSpeed());
    
        TurnSystem.resetTurns();
        assertFalse(character.getTurnSystem().hasDoneTurn());
        assertFalse(otherCharacter.getTurnSystem().hasDoneTurn());
    }
}
