import java.util.Set;

public class FoodItem extends ConsumableItem {
    private int healValue;

    public FoodItem(String name, int healValue) {
        super(name);
        this.healValue = healValue;
    }

    public Set<InteractableAction> getPossibleActions(){
        return possibleInteractableActions;
    }

    public int getHealValue() {
        return healValue;
    }
}
