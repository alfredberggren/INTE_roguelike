import java.util.Set;

public abstract class NonLivingEntity implements Interactable {
    protected String name;
    protected Set<InteractableAction> possibleInteractableActions;

    public NonLivingEntity(String name, Set<InteractableAction> possibleInteractableActions) {
        if (name == null) {
            throw new NullPointerException("Name cannot be null!");
        }
        if (name.isEmpty()) {
            throw new IllegalArgumentException("Name cannot be empty!");
        }
        if (possibleInteractableActions == null) {
            throw new NullPointerException("possibleInteractions cannot be null");
        }
        this.name = name;
        this.possibleInteractableActions = possibleInteractableActions;
    }

    public String getName() {
        return name;
    }

    @Override
    public abstract boolean equals(Object other);

    @Override
    public abstract int hashCode();
}
