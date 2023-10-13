public abstract class IO {
    public enum TurnCommand{
        ACTION, MOVE, END  
    }

    public abstract TurnCommand requestTurnCommand(Room room, Character character);

    public abstract TurnCommand requestAnotherTurnCommand(Room room, Character character);

    public abstract CardinalDirection requestMove(Room room, Character character);

    public abstract CardinalDirection requestAnotherMove(Room room, Character character);

    public abstract Interactable.InteractableAction requestAction(Room room, Character character);

    public abstract Interactable.InteractableAction requestAnotherAction(Room room, Character character);
}