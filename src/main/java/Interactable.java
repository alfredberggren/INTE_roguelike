import java.util.Set;

public interface Interactable {
    enum Action {
        LOOT, DROP, FIGHT, MOVE, WEAR;
    }

    Set<Action> getPossibleActions();
}
