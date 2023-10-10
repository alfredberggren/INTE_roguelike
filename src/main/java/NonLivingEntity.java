import java.util.Set;

public abstract class NonLivingEntity implements Interactable {
    private String name;
    Set<Action> possibleActions;

    public NonLivingEntity(String name, Set<Action> possibleActions) {
        this.name = name;
        this.possibleActions = possibleActions;
    }
}
