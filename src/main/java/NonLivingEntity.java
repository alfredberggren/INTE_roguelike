import java.util.Set;

public abstract class NonLivingEntity implements Interactable {
    String name;
    Set<InteractableAction> possibleInteractableActions;

    public NonLivingEntity(String name, Set<InteractableAction> possibleInteractableActions) {
        this.name = name;
        this.possibleInteractableActions = possibleInteractableActions;
    }
}
