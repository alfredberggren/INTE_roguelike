import java.util.Set;

public interface Interactable {
    enum Action {
        LOOT, DROP, FIGHT, WEAR, TALK, USE;
    }

    Set<Action> getPossibleActions();
}
