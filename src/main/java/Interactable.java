import java.util.Set;

public interface Interactable {
    enum Action {
        LOOT, DROP, FIGHT, WEAR, TALK;
    }

    Set<Action> getPossibleActions();
}
