

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;

import java.io.PrintStream;
import java.util.Scanner;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class IOTest {

    private InputStream defaultInputStream;
    private PrintStream defaultOutputStream;

    @BeforeEach
    public void setDefaultInputStream() {
        defaultInputStream = System.in;
        defaultOutputStream = new PrintStream(System.out);
    }
  
    @AfterEach
    public void resetInputStream() {
        System.setIn(defaultInputStream);
        System.setOut(defaultOutputStream);
    }

    @Test
    public void testRequestTurnCommand() {
        String inputString = "move";
        InputStream tempInputStream = new ByteArrayInputStream(inputString.getBytes());
        System.setIn(tempInputStream);

        IO tempTextUI = new TextUI();

        // Needs updating when Room constructor is updated
        Room tempRoom = new Room(new Position(0, 0));

        // Needs updating when Character constructor is updated
        Character tempCharacter = new Character(0, 0, new Position(0, 0));

        assertEquals(IO.TurnCommand.ACTION, tempTextUI.requestTurnCommand(tempRoom, tempCharacter),
               "Could not request Turn Command");
    }

    @Test
    public void testRequestTurnCommandsAllTurnCommands() {
        IO tempTextUI = new TextUI();
        // Needs updating when Room constructor is updated
        Room tempRoom = new Room(new Position(0, 0));
        // Needs updating when Character constructor is updated
        Character tempCharacter = new Character(0, 0, new Position(0, 0));

        String[] availableCommands = {"action", "move", "end turn"};
        int correctCommand = 0;
        for (int i = 0; i < IO.TurnCommand.values().length; i++){
            InputStream tempInputStream = new ByteArrayInputStream(availableCommands[i].getBytes());
            System.setIn(tempInputStream);

            if(IO.TurnCommand.values()[i].equals(tempTextUI.requestTurnCommand(tempRoom, tempCharacter))){
                correctCommand++;
            }
        }
        assertEquals(IO.TurnCommand.values().length, correctCommand);
    }

    @Test
    public void testRequestedTurnCommandWrongInput() {
        String inputString = "not a command";
        InputStream tempInputStream = new ByteArrayInputStream(inputString.getBytes());
        System.setIn(tempInputStream);

        IO tempTextUI = new TextUI();
        // Needs updating when Room constructor is updated
        Room tempRoom = new Room(new Position(0, 0));
        // Needs updating when Character constructor is updated
        Character tempCharacter = new Character(0, 0, new Position(0, 0));

        assertThrows(IllegalArgumentException.class, () ->{
            tempTextUI.requestMove(tempRoom, tempCharacter);
        });
    }

    @Test
    public void testRequestTurnCommandIsNotAllowed() {
        String inputString = "action";
        String failedMoveString = "Command is not allowed";
        InputStream tempInputStream = new ByteArrayInputStream(inputString.getBytes());
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        PrintStream printStream = new PrintStream(outputStream);
        System.setIn(tempInputStream);
        System.setOut(printStream);

        TextUI tempTextUI = new TextUI();
        // Needs updating when Room constructor is updated
        Room tempRoom = new Room(new Position(0, 0));
        // Needs updating when Character constructor is updated
        Character tempCharacter = new Character(0, 0, new Position(0, 0));
        assertEquals(IO.TurnCommand.ACTION, tempTextUI.requestAnotherTurnCommand(tempRoom, tempCharacter));
        InputStream outputStreamRead = new ByteArrayInputStream(outputStream.toByteArray());

        Scanner scanner = new Scanner(outputStreamRead);
        String scannedLine = scanner.nextLine();
        scanner.close();
        assertEquals(failedMoveString, scannedLine);
    }

    @Test
    public void testRequestMove() {
        String inputString = "north";
        InputStream tempInputStream = new ByteArrayInputStream(inputString.getBytes());
        System.setIn(tempInputStream);

        IO tempTextUI = new TextUI();

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
        for (int i = 0; i < CardinalDirection.values().length; i++){
            InputStream tempInputStream = new ByteArrayInputStream(movableDirections[i].getBytes());
            System.setIn(tempInputStream);

            if(CardinalDirection.values()[i].equals(tempTextUI.requestMove(tempRoom, tempCharacter))){
                correctDirections++;
            }
        }
        assertEquals(CardinalDirection.values().length, correctDirections);
    }

    @Test
    public void testRequestedMoveWrongInput() {
        String inputString = "not a direction";
        InputStream tempInputStream = new ByteArrayInputStream(inputString.getBytes());
        System.setIn(tempInputStream);

        IO tempTextUI = new TextUI();
        // Needs updating when Room constructor is updated
        Room tempRoom = new Room(new Position(0, 0));
        // Needs updating when Character constructor is updated
        Character tempCharacter = new Character(0, 0, new Position(0, 0));

        assertThrows(IllegalArgumentException.class, () ->{
            tempTextUI.requestMove(tempRoom, tempCharacter);
        });
    }

    @Test
    public void testRequestAnotherMoveAfterFail() {
        String inputString = "north";
        String failedMoveString = "Move is not allowed";
        InputStream tempInputStream = new ByteArrayInputStream(inputString.getBytes());
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        PrintStream printStream = new PrintStream(outputStream);
        System.setIn(tempInputStream);
        System.setOut(printStream);

        IO tempTextUI = new TextUI();
        // Needs updating when Room constructor is updated
        Room tempRoom = new Room(new Position(0, 0));
        // Needs updating when Character constructor is updated
        Character tempCharacter = new Character(0, 0, new Position(0, 0));
        assertEquals(CardinalDirection.NORTH, tempTextUI.requestAnotherMove(tempRoom, tempCharacter));
        InputStream outputStreamRead = new ByteArrayInputStream(outputStream.toByteArray());

        Scanner scanner = new Scanner(outputStreamRead);
        String scannedLine = scanner.nextLine();
        scanner.close();
        assertEquals(failedMoveString, scannedLine);
    }

    @Test
    public void testRequestAction() {
        String inputString = "loot";
        InputStream tempInputStream = new ByteArrayInputStream(inputString.getBytes());
        System.setIn(tempInputStream);

        IO tempTextUI = new TextUI();

        // Needs updating when Room constructor is updated
        Room tempRoom = new Room(new Position(0, 0));

        // Needs updating when Character constructor is updated
        Character tempCharacter = new Character(0, 0, new Position(0, 0));

        assertEquals(Interactable.InteractableAction.LOOT, tempTextUI.requestAction(tempRoom, tempCharacter),
               "Could not request move");
    }

    @Test
    public void testRequestActionAllActions() {
        IO tempTextUI = new TextUI();

        // Needs updating when Room constructor is updated
        Room tempRoom = new Room(new Position(0, 0));

        // Needs updating when Character constructor is updated
        Character tempCharacter = new Character(0, 0, new Position(0, 0));

        String[] movableDirections = {"loot", "drop", "fight", "wear", "talk", "use"};
        int correctDirections = 0;
        for (int i = 0; i < Interactable.InteractableAction.values().length; i++){
            InputStream tempInputStream = new ByteArrayInputStream(movableDirections[i].getBytes());
            System.setIn(tempInputStream);

            if(Interactable.InteractableAction.values()[i].equals(tempTextUI.requestAction(tempRoom, tempCharacter))){
                correctDirections++;
            }
        }
        assertEquals(Interactable.InteractableAction.values().length, correctDirections);
    }

    @Test
    public void testRequestActionWrongInput() {
        String inputString = "not a action";
        InputStream tempInputStream = new ByteArrayInputStream(inputString.getBytes());
        System.setIn(tempInputStream);

        IO tempTextUI = new TextUI();
        // Needs updating when Room constructor is updated
        Room tempRoom = new Room(new Position(0, 0));
        // Needs updating when Character constructor is updated
        Character tempCharacter = new Character(0, 0, new Position(0, 0));

        assertThrows(IllegalArgumentException.class, () ->{
            tempTextUI.requestAction(tempRoom, tempCharacter);
        });
    }

    @Test
    public void testRequestAnotherActionAfterFail() {
        String inputString = "loot";
        String failedMoveString = "Action is not allowed";
        InputStream tempInputStream = new ByteArrayInputStream(inputString.getBytes());
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        PrintStream printStream = new PrintStream(outputStream);
        System.setIn(tempInputStream);
        System.setOut(printStream);

        IO tempTextUI = new TextUI();
        // Needs updating when Room constructor is updated
        Room tempRoom = new Room(new Position(0, 0));
        // Needs updating when Character constructor is updated
        Character tempCharacter = new Character(0, 0, new Position(0, 0));
        assertEquals(Interactable.InteractableAction.LOOT, tempTextUI.requestAnotherAction(tempRoom, tempCharacter));
        
        //Checks the method outputs the correct string
        InputStream outputStreamRead = new ByteArrayInputStream(outputStream.toByteArray());
        Scanner scanner = new Scanner(outputStreamRead);
        String scannedLine = scanner.nextLine();
        scanner.close();
        assertEquals(failedMoveString, scannedLine);
    }
}
