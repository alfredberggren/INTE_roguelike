import java.util.Set;

public class Prop extends NonLivingEntity {
    public Prop(String name, Set<InteractableAction> possibleInteractableActions) {
        super(name, possibleInteractableActions);
    }

    public Set<InteractableAction> getPossibleActions() {
        return possibleInteractableActions;
    }
}
