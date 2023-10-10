import java.util.Objects;
import java.util.Set;

public class Prop extends NonLivingEntity {
    public Prop(String name, Set<InteractableAction> possibleInteractableActions) {
        super(name, possibleInteractableActions);
    }

    public Set<InteractableAction> getPossibleActions() {
        return possibleInteractableActions;
    }

    @Override
    public boolean equals(Object other) {
        if (other instanceof Prop prop) {
            return prop.getName().equals(name);
        }
        return false;
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }
}
