import java.util.Objects;
import java.util.Set;

public class FoodItem extends ConsumableItem {
    private int healValue;

    public FoodItem(String name, int healValue) {
        super(name);
        if (healValue <= 0) {
            throw new IllegalArgumentException();
        }
        this.healValue = healValue;
    }

    public Set<InteractableAction> getPossibleActions(){
        return possibleInteractableActions;
    }

    public int getHealValue() {
        return healValue;
    }

    @Override
    public boolean equals(Object other) {
        if (other instanceof FoodItem f) {
            return f.getHealValue() == healValue && f.getName().equals(name);
        }
        return false;
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, healValue);
    }
}
