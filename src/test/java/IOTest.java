import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
// import java.util.ArrayList;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class IOTest {

    private InputStream defaultInputStream;

    @BeforeEach
    public void setDefaultInputStream() {
        defaultInputStream = System.in;
    }
  
    @AfterEach
    public void resetInputStream() {
        System.setIn(defaultInputStream);
    }

    @Test
    public void testRequestTurnCommand() {

    }

    @Test
    public void testRequestTurnCommandsAllTurnCommands() {

    }

    @Test
    public void testRequestTurnCommandIsNotAllowed() {

    }

    @Test
    public void testRequestMove() {
        String inputString = "north";
        InputStream tempInputStream = new ByteArrayInputStream(inputString.getBytes());
        System.setIn(tempInputStream);

        TextUI tempTextUI = new TextUI();

        // Needs updating when Room constructor is updated
        Room tempRoom = new Room(new Position(0, 0));

        // Needs updating when Character constructor is updated
        Character tempCharacter = new Character(0, 0, new Position(0, 0));

        // requestMove(Room room, Character character) has it parameters so it knows
        // where from and who preforms the move.
        assertEquals(CardinalDirection.NORTH, tempTextUI.requestMove(tempRoom, tempCharacter),
               "Could not request move");
    }

    @Test
    public void testRequestMoveInAllDirections() {
        TextUI tempTextUI = new TextUI();

        // Needs updating when Room constructor is updated
        Room tempRoom = new Room(new Position(0, 0));

        // Needs updating when Character constructor is updated
        Character tempCharacter = new Character(0, 0, new Position(0, 0));

        String[] movableDirections = {"north", "east", "south", "west"};
        int correctDirections = 0;
        for (int i = 0; i < 4; i++){
            InputStream tempInputStream = new ByteArrayInputStream(movableDirections[i].getBytes());
            System.setIn(tempInputStream);

            if(CardinalDirection.values()[i].equals(tempTextUI.requestMove(tempRoom, tempCharacter))){
                correctDirections++;
            }
        }
        assertEquals(4, correctDirections);
    }

    @Test
    public void testRequestedMoveIsNotAllowed() {
        String inputString = "not a direction";
        InputStream tempInputStream = new ByteArrayInputStream(inputString.getBytes());
        System.setIn(tempInputStream);

        TextUI tempTextUI = new TextUI();
        // Needs updating when Room constructor is updated
        Room tempRoom = new Room(new Position(0, 0));
        // Needs updating when Character constructor is updated
        Character tempCharacter = new Character(0, 0, new Position(0, 0));

        CardinalDirection requestedDirection = tempTextUI.requestMove(tempRoom, tempCharacter);
        
        //Asks for a second input
        String inputStringTwo = "north";
        var tempInputStreamTwo = new ByteArrayInputStream(inputStringTwo.getBytes());
        System.setIn(tempInputStreamTwo);
        
        assertEquals(CardinalDirection.NORTH, requestedDirection, "");

    }

    private void assertEqual() {
    }

    @Test
    public void testRequestAction() {

    }

    @Test
    public void testRequestActionAllActions() {

    }

    @Test
    public void testRequestActionIsNotAllowed() {

    }
}