import java.util.Scanner;

public class TextIO extends IO{

    @Override
    public TurnSystem.TurnCommand requestTurnCommand(MapController map, Character character){
        String input = getUserInput();
        switch(input){
            case "action": return TurnSystem.TurnCommand.ACTION;
            case "move": return TurnSystem.TurnCommand.MOVE;
            case "end turn": return TurnSystem.TurnCommand.END;
            default: throw new IllegalArgumentException(input + " is not a valid command.");
        }
    }

    @Override
    public TurnSystem.TurnCommand requestAnotherTurnCommand(MapController map, Character character){
        System.out.println("Command is not allowed");
        return requestTurnCommand(map, character);
    }

    @Override
    public CardinalDirection requestMove(MapController map, Character character){
        String input = getUserInput();
        switch(input){
            case "north": return CardinalDirection.NORTH;
            case "east": return CardinalDirection.EAST;
            case "south": return CardinalDirection.SOUTH;
            case "west": return CardinalDirection.WEST;
            default: throw new IllegalArgumentException(input + " is not a valid move.");
        }
    }

    @Override
    public CardinalDirection requestAnotherMove(MapController map, Character character){
        System.out.println("Move is not allowed");
        return requestMove(map, character);
    }

    @Override
    public Interactable.InteractableAction requestAction(MapController map, Character character, Interactable interactable){
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

    @Override
    public Interactable.InteractableAction requestAnotherAction(MapController map, Character character, Interactable interactable){
        System.out.println("Action is not allowed");
        return requestAction(map, character, interactable);
    }


    private String getUserInput(){
        Scanner scanner = new Scanner(System.in);
        String input = scanner.nextLine();
        scanner.close();
        return input;
    }

    @Override
    public Interactable requestInteractable(MapController map, Character character) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'requestInteractable'");
    }

    @Override
    public Interactable requestAnotherInteractble(MapController map, Character character) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'requestAnotherInteractble'");
    }
}