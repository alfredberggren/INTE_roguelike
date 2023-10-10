import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public abstract class ConsumableItem extends NonLivingEntity {
    private static final Set<InteractableAction> STANDARD_CONSUMABLE_INTERACTABLE_ACTIONS = new HashSet<>(Arrays.asList(
            InteractableAction.DROP,
            InteractableAction.LOOT,
            InteractableAction.USE)
    );

    public ConsumableItem(String name) {
        super(name, STANDARD_CONSUMABLE_INTERACTABLE_ACTIONS);
    }
}
