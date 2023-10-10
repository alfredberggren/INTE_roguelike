import java.util.Set;

public class Prop extends NonLivingEntity {
    public Prop(String name, Set<Action> possibleActions) {
        super(name, possibleActions);
    }

    public Set<Action> getPossibleActions() {
        return possibleActions;
    }
}
