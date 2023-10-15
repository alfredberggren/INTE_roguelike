import java.util.Scanner;

public class TextUI extends IO{

    public TurnCommand requestTurnCommand(Map map, Character character){
        String input = getUserInput();
        switch(input){
            case "action": return TurnCommand.ACTION;
            case "move": return TurnCommand.MOVE;
            case "end turn": return TurnCommand.END;
            default: throw new IllegalArgumentException(input + " is not a valid command.");
        }
    }

    public IO.TurnCommand requestAnotherTurnCommand(Map map, Character character){
        System.out.println("Command is not allowed");
        return requestTurnCommand(map, character);
    }


    public CardinalDirection requestMove(Map map, Character character){
        String input = getUserInput();
        switch(input){
            case "north": return CardinalDirection.NORTH;
            case "east": return CardinalDirection.EAST;
            case "south": return CardinalDirection.SOUTH;
            case "west": return CardinalDirection.WEST;
            default: throw new IllegalArgumentException(input + " is not a valid move.");
        }
    }

    public CardinalDirection requestAnotherMove(Map map, Character character){
        System.out.println("Move is not allowed");
        return requestMove(map, character);
    }

    public Interactable.InteractableAction requestAction(Map map, Character character){
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

    public Interactable.InteractableAction requestAnotherAction(Map map, Character character){
        System.out.println("Action is not allowed");
        return requestAction(map, character);
    }

    private String getUserInput(){
        Scanner scanner = new Scanner(System.in);
        String input = scanner.nextLine();
        scanner.close();
        return input;
    }
}