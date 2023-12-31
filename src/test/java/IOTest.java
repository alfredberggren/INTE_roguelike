

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

    IO tempTextIO;
    Character tempCharacter;

    @BeforeEach
    public void setDefaultInputStream() {
        defaultInputStream = System.in;
        defaultOutputStream = new PrintStream(System.out);

        tempTextIO = new TextIO();
        tempCharacter = new Character("name", 1, 1, 0, new Position(0, 0), tempTextIO);
    }
  
    @AfterEach
    public void resetInputStream() {
        System.setIn(defaultInputStream);
        System.setOut(defaultOutputStream);
    }

    @Test
    public void testRequestTurnCommand() {
        String inputString = "action";
        InputStream tempInputStream = new ByteArrayInputStream(inputString.getBytes());
        System.setIn(tempInputStream);

        MapController tempMapController = new MapController();

        assertEquals(TurnSystem.TurnCommand.ACTION, tempTextIO.requestTurnCommand(tempMapController, tempCharacter, tempCharacter.getSpeed(), tempCharacter.getSpeed()),
               "Could not request Turn Command");
    }

    @Test
    public void testRequestTurnCommandsAllTurnCommands() {
        MapController tempMapController = new MapController();

        String[] availableCommands = {"end turn", "action", "move"};
        int correctCommand = 0;
        for (int i = 0; i < TurnSystem.TurnCommand.values().length; i++){
            InputStream tempInputStream = new ByteArrayInputStream(availableCommands[i].getBytes());
            System.setIn(tempInputStream);

            if(TurnSystem.TurnCommand.values()[i].equals(tempTextIO.requestTurnCommand(tempMapController, tempCharacter, tempCharacter.getSpeed(), tempCharacter.getSpeed()))){
                correctCommand++;
            }
        }
        assertEquals(TurnSystem.TurnCommand.values().length, correctCommand);
    }

    @Test
    public void testRequestedTurnCommandWrongInput() {
        String inputString = "not a command";
        InputStream tempInputStream = new ByteArrayInputStream(inputString.getBytes());
        System.setIn(tempInputStream);

        MapController tempMapController = new MapController();  

        assertThrows(IllegalArgumentException.class, () ->{
            tempTextIO.requestTurnCommand(tempMapController, tempCharacter, tempCharacter.getSpeed(), tempCharacter.getSpeed());
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

        MapController tempMapController = new MapController();  

        //Checks the method outputs the correct string
        assertEquals(TurnSystem.TurnCommand.ACTION, tempTextIO.requestAnotherTurnCommand(tempMapController, tempCharacter, tempCharacter.getSpeed(), tempCharacter.getSpeed()));
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

        MapController tempMapController = new MapController();

        assertEquals(CardinalDirection.NORTH, tempTextIO.requestMove(tempMapController, tempCharacter),
               "Could not request move");
    }

    @Test
    public void testRequestMoveInAllDirections() {
        MapController tempMapController = new MapController();

        String[] movableDirections = {"north", "east", "south", "west"};
        int correctDirections = 0;
        for (int i = 0; i < CardinalDirection.values().length; i++){
            InputStream tempInputStream = new ByteArrayInputStream(movableDirections[i].getBytes());
            System.setIn(tempInputStream);

            if(CardinalDirection.values()[i].equals(tempTextIO.requestMove(tempMapController, tempCharacter))){
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

        MapController tempMapController = new MapController();

        assertThrows(IllegalArgumentException.class, () ->{
            tempTextIO.requestMove(tempMapController, tempCharacter);
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

        MapController tempMapController = new MapController();
        
        //Checks the method outputs the correct string
        assertEquals(CardinalDirection.NORTH, tempTextIO.requestAnotherMove(tempMapController, tempCharacter));
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

        FoodItem tempInteractableItem = new FoodItem("name", 1);

        assertEquals(Interactable.InteractableAction.LOOT, tempTextIO.requestAction(tempInteractableItem, tempCharacter),
               "Could not request move");
    }

    @Test
    public void testRequestActionAllActions() {
        FoodItem tempInteractableItem = new FoodItem("name", 1);

        String[] availableActions = {"loot", "drop", "fight", "wear", "talk", "use", "unequip"};
        int correctDirections = 0;
        for (int i = 0; i < Interactable.InteractableAction.values().length; i++){
            InputStream tempInputStream = new ByteArrayInputStream(availableActions[i].getBytes());
            System.setIn(tempInputStream);

            if(Interactable.InteractableAction.values()[i].equals(tempTextIO.requestAction(tempInteractableItem, tempCharacter))){
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

        FoodItem tempInteractableItem = new FoodItem("name", 1);

        assertThrows(IllegalArgumentException.class, () ->{
            tempTextIO.requestAction(tempInteractableItem, tempCharacter);
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

        FoodItem tempInteractableItem = new FoodItem("name", 1);
        assertEquals(Interactable.InteractableAction.LOOT, tempTextIO.requestAnotherAction(tempInteractableItem, tempCharacter));
        
        //Checks the method outputs the correct string
        InputStream outputStreamRead = new ByteArrayInputStream(outputStream.toByteArray());
        Scanner scanner = new Scanner(outputStreamRead);
        String scannedLine = scanner.nextLine();
        scanner.close();
        assertEquals(failedMoveString, scannedLine);
    }
}
