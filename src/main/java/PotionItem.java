import java.util.Objects;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PotionItem that = (PotionItem) o;
        return name.equals(that.getName()) && turnLimit == that.turnLimit && effect == that.effect;
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, turnLimit, effect);
    }
}