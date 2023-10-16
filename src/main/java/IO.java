public abstract class IO {

    public abstract TurnSystem.TurnCommand requestTurnCommand(MapController map, Character character);

    public abstract TurnSystem.TurnCommand requestAnotherTurnCommand(MapController map, Character character);

    public abstract CardinalDirection requestMove(MapController map, Character character);

    public abstract CardinalDirection requestAnotherMove(MapController map, Character character);

    public abstract Interactable.InteractableAction requestAction(MapController map, Character character, Interactable interactable);

    public abstract Interactable.InteractableAction requestAnotherAction(MapController map, Character character, Interactable interactable);

    public abstract Interactable requestInteractable(MapController map, Character character);

    public abstract Interactable requestAnotherInteractble(MapController map, Character character);
}