public abstract class IO {

    public abstract TurnSystem.TurnCommand requestTurnCommand(Map map, Character character);

    public abstract TurnSystem.TurnCommand requestAnotherTurnCommand(Map map, Character character);

    public abstract CardinalDirection requestMove(Map map, Character character);

    public abstract CardinalDirection requestAnotherMove(Map map, Character character);

    public abstract Interactable.InteractableAction requestAction(Map map, Character character);

    public abstract Interactable.InteractableAction requestAnotherAction(Map map, Character character);
}