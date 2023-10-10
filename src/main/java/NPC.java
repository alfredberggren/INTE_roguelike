import java.util.Set;

public class NPC extends Character implements Interactable {
    private Set<InteractableAction> possibleInteractableActions;

    public NPC(String name, int health, int speed, Set<InteractableAction> possibleInteractableActions) {
        super(name, health, speed);
        this.possibleInteractableActions = possibleInteractableActions;
    }

    public Set<InteractableAction> getPossibleActions() {
        return possibleInteractableActions;
    }
}
