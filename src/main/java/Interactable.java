import java.util.Set;

public interface Interactable {
    enum InteractableAction {
        LOOT, DROP, FIGHT, WEAR, TALK, USE;
    }

    Set<InteractableAction> getPossibleActions();
}
