import java.util.Set;

public abstract class NonLivingEntity implements Interactable {
    String name;
    Set<InteractableAction> possibleInteractableActions;

    public NonLivingEntity(String name, Set<InteractableAction> possibleInteractableActions) {
        if (name == null) {
            throw new NullPointerException("Name cannot be null!");
        }
        this.name = name;
        this.possibleInteractableActions = possibleInteractableActions;
    }
}
