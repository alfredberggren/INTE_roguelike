import java.util.ArrayList;

public class InteractableInventory {
    private ArrayList<Interactable> inventory;

    public InteractableInventory(){
        inventory = new ArrayList<>();
    }

    public void add(Interactable i) {
        checkNull(i);
        inventory.add(i);
    }

    public void add(Interactable i, int amount){
        checkNull(i);
        checkAmount(amount);
        for (int j = 1; j <= amount; j++){
            inventory.add(i);
        }
    }

    public void removeAll(Interactable i){
        checkNull(i);
        ArrayList<Interactable> temp = new ArrayList<>();
        temp.add(i);
        inventory.removeAll(temp);
    }

    public void remove(Interactable i){
        checkNull(i);
        inventory.remove(i);
    }

    public void remove (Interactable i, int amount){
        checkNull(i);
        checkAmount(amount);

        if (amount > getAmountOf(i))
            amount = getAmountOf(i);

        for (int j = 1; j <= amount; j++){
            inventory.remove(i);
        }
    }

    public void transferInteractableTo(Interactable i, InteractableInventory inv){
        if (contains(i)) {
            inv.add(i);
            remove(i);
        }
    }

    public int getAmountOf(Interactable i) {
        checkNull(i);
        int amount = 0;

        for (Interactable j: inventory) {
            if (i.equals(j)){
                amount++;
            }
        }
        return amount;
    }

    public boolean contains(Interactable i){
        return inventory.contains(i);
    }

    public boolean isEmpty() {
        return inventory.isEmpty();
    }


    private void checkNull(Interactable i){
        if (i == null){
            throw new NullPointerException("Interactable can not be null!");
        }
    }

    private void checkAmount(int amount){
        if (amount < 1){
            throw new IllegalArgumentException("Amount must be 1 or larger!");
        }
    }





}
