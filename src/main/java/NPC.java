import java.util.Set;

public class NPC extends Character implements Interactable {
    private Set<Action> possibleActions;

    public NPC(String name, int health, int speed, Set<Action> possibleActions) {
        super(name, health, speed);
        this.possibleActions = possibleActions;
    }

    public Set<Action> getPossibleActions() {
        return possibleActions;
    }
}
