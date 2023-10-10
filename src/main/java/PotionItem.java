import java.util.Set;

public class PotionItem extends ConsumableItem {
    private int turnLimit;
    private Equipment.Effect effect;

    public PotionItem(String name, Equipment.Effect effect, int turnLimit) {
        super(name);
        if (effect == null) {
            throw new NullPointerException("Effect cannot be null!");
        }
        if (turnLimit <= 0) {
            throw new IllegalArgumentException("Turn limit cannot be <=0!");
        }
        this.effect = effect;
        this.turnLimit = turnLimit;
    }

    public Set<InteractableAction> getPossibleActions(){
        return possibleInteractableActions;
    }

    public int getTurnLimit() {
        return turnLimit;
    }

    public Equipment.Effect getEffect() {
        return effect;
    }
}