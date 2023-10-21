import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public abstract class IO {

    /*
     * TODO: description
     * 
    */

    public abstract TurnSystem.TurnCommand requestTurnCommand(MapController map, Character character, int amountOfActions, int amountOfMoves);

    public abstract TurnSystem.TurnCommand requestAnotherTurnCommand(MapController map, Character character, int amountOfActions, int amountOfMoves);

    public abstract CardinalDirection requestMove(MapController map, Character character);

    public abstract CardinalDirection requestAnotherMove(MapController map, Character character);

    public abstract Interactable.InteractableAction requestAction(Interactable interactable, Character character);

    public abstract Interactable.InteractableAction requestAnotherAction(Interactable interactable, Character character);

    //TODO: tests
    public abstract Interactable requestInteractable(Room room, Character character);

    public abstract Interactable requestAnotherInteractble(Room room, Character character);

    public abstract Interactable requestInteractable(Character from, Character to);

    public abstract Interactable requestAnotherInteractble(Character from, Character to);

    public abstract Ability requestAbility(Character from, Character at);

    public abstract Ability requestAnotherAbility(Character from, Character at);

    public abstract void retrieveDialogString(String string);
}