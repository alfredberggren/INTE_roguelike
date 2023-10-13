import java.util.Scanner;

public class TextUI extends IO{

    public TurnCommand requestTurnCommand(Room room, Character character){
        return TurnCommand.ACTION;
    }

    public IO.TurnCommand requestAnotherTurnCommand(Room room, Character character){
        System.out.println("Command is not allowed");
        return TurnCommand.ACTION;
    }


    public CardinalDirection requestMove(Room room, Character character){
        Scanner scanner = new Scanner(System.in);
        String input = scanner.nextLine();
        scanner.close();
        switch(input){
            case "north": return CardinalDirection.NORTH;
            case "east": return CardinalDirection.EAST;
            case "south": return CardinalDirection.SOUTH;
            case "west": return CardinalDirection.WEST;
            default: return null;
        }
    }

    public CardinalDirection requestAnotherMove(Room room, Character character){
        System.out.println("Move is not allowed");
        return CardinalDirection.NORTH;
    }

    public Interactable.InteractableAction requestAction(Room room, Character character){
        System.out.println("Action is not allowed");
        return Interactable.InteractableAction.LOOT;
    }

    public Interactable.InteractableAction requestAnotherAction(Room room, Character character){
        System.out.println("Action is not allowed");
        return Interactable.InteractableAction.LOOT;
    }
}