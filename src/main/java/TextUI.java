import java.util.Scanner;

public class TextUI extends IO{

    public TurnCommand requestTurnCommand(Room room, Character character){
        String input = getUserInput();
        switch(input){
            case "action": return TurnCommand.ACTION;
            case "move": return TurnCommand.MOVE;
            case "end turn": return TurnCommand.END;
            default: throw new IllegalArgumentException(input + " is not a valid command.");
        }
    }

    public IO.TurnCommand requestAnotherTurnCommand(Room room, Character character){
        System.out.println("Command is not allowed");
        return requestTurnCommand(room, character);
    }


    public CardinalDirection requestMove(Room room, Character character){
        String input = getUserInput();
        switch(input){
            case "north": return CardinalDirection.NORTH;
            case "east": return CardinalDirection.EAST;
            case "south": return CardinalDirection.SOUTH;
            case "west": return CardinalDirection.WEST;
            default: throw new IllegalArgumentException(input + " is not a valid move.");
        }
    }

    public CardinalDirection requestAnotherMove(Room room, Character character){
        System.out.println("Move is not allowed");
        return requestMove(room, character);
    }

    public Interactable.InteractableAction requestAction(Room room, Character character){
        String input = getUserInput();
        switch(input){
            case "loot": return Interactable.InteractableAction.LOOT;
            case "drop": return Interactable.InteractableAction.DROP;
            case "fight": return Interactable.InteractableAction.FIGHT;
            case "wear": return Interactable.InteractableAction.WEAR;
            case "talk": return Interactable.InteractableAction.TALK;
            case "use": return Interactable.InteractableAction.USE;
            default: throw new IllegalArgumentException(input + " is not a valid action.");
        }
    }

    public Interactable.InteractableAction requestAnotherAction(Room room, Character character){
        System.out.println("Action is not allowed");
        return requestAction(room, character);
    }

    private String getUserInput(){
        Scanner scanner = new Scanner(System.in);
        String input = scanner.nextLine();
        scanner.close();
        return input;
    }
}