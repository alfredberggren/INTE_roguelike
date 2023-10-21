import java.util.Scanner;

public class TextIO extends IO{

    @Override
    public TurnSystem.TurnCommand requestTurnCommand(MapController map, Character character, int amountOfActions, int amountOfMoves){
        String input = getUserInput();
        switch(input){
            case "action": return TurnSystem.TurnCommand.ACTION;
            case "move": return TurnSystem.TurnCommand.MOVE;
            case "end turn": return TurnSystem.TurnCommand.END;
            default: throw new IllegalArgumentException(input + " is not a valid command.");
        }
    }

    @Override
    public TurnSystem.TurnCommand requestAnotherTurnCommand(MapController map, Character character, int amountOfActions, int amountOfMoves){
        System.out.println("Command is not allowed");
        return requestTurnCommand(map, character, amountOfActions, amountOfMoves);
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
    public Interactable.InteractableAction requestAction(Interactable interactable, Character character){
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
    public Interactable.InteractableAction requestAnotherAction(Interactable interactable, Character character) {
        return null;
    }

    /*@Override
    public Interactable.InteractableAction requestAnotherAction(Interactable interactable, Character character){
        System.out.println("Action is not allowed");
        return requestAction(map, character, interactable);
    }*/


    private String getUserInput(){
        Scanner scanner = new Scanner(System.in);
        String input = scanner.nextLine();
        scanner.close();
        return input;
    }

    @Override
    public Interactable requestInteractable(Room room, Character character) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'requestInteractable'");
    }

    @Override
    public Interactable requestAnotherInteractble(Room room, Character character) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'requestAnotherInteractble'");
    }

    @Override
    public Interactable requestInteractable(Character from, Character to) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'requestInteractable'");
    }

    @Override
    public Interactable requestAnotherInteractble(Character from, Character to) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'requestAnotherInteractble'");
    }

    @Override
    public Ability requestAbility(Character from, Character at) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'requestAbility'");
    }

    @Override
    public Ability requestAnotherAbility(Character from, Character at) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'requestAnotherAbility'");
    }

    @Override
    public void retrieveDialogString(String string) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'retrieveDialogString'");
    }
}
