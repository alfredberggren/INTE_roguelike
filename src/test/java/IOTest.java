import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class IOTest {

    private InputStream defaultInputStream;

    @BeforeAll
    public void setDefaultInputStream() {
        defaultInputStream = System.in;
    }

    @AfterAll
    public void resetInputStream() {
        System.setIn(defaultInputStream);
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
    public void testRequestMoveAllDirections() {
        TextUI tempTextUI = new TextUI();

        // Needs updating when Room constructor is updated
        Room tempRoom = new Room(new Position(0, 0));

        // Needs updating when Character constructor is updated
        Character tempCharacter = new Character(0, 0, new Position(0, 0));

        String[] movableDirections = {"north", "east", "south", "west"};
        for (int i = 0; i > 4; i++){
            InputStream tempInputStream = new ByteArrayInputStream(movableDirections[i].getBytes());
            System.setIn(tempInputStream);

            CardinalDirection direction = CardinalDirection.values()[i];
            assertEquals(direction, tempTextUI.requestMove(tempRoom, tempCharacter),
                    "When trying to perform requestMove() in all directions it could not request the direction "
                    + direction);
        }
        
        

    }

    @Test
    public void testRequestedMoveIsNotAllowed() {

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