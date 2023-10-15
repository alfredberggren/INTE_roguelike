public abstract class IO {
    public enum TurnCommand{
        ACTION, MOVE, END  
    }

    public abstract TurnCommand requestTurnCommand(Map map, Character character);

    public abstract TurnCommand requestAnotherTurnCommand(Map map, Character character);

    public abstract CardinalDirection requestMove(Map map, Character character);

    public abstract CardinalDirection requestAnotherMove(Map map, Character character);

    public abstract Interactable.InteractableAction requestAction(Map map, Character character);

    public abstract Interactable.InteractableAction requestAnotherAction(Map map, Character character);
}