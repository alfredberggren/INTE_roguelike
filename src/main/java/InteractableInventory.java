import java.util.ArrayList;

public class InteractableInventory {
    private ArrayList<Interactable> inventory;

    public InteractableInventory(){
        inventory = new ArrayList<>();
    }

    public InteractableInventory(Interactable... interactables){
        this();
        add(interactables);
    }

    public void add(Interactable... interactables) {
        for (Interactable i: interactables) {
            checkNull(i);
            inventory.add(i);
        }

    }


    public void remove(Interactable... interactables){
        for (Interactable i: interactables){
            checkNull(i);
            inventory.remove(i);
        }
    }
    public void removeAll(){
        inventory.clear();
    }

    public void transfer(Interactable i, InteractableInventory inv){
        if (contains(i)) {
            inv.add(i);
            remove(i);
        }
    }

    public int getAmountOfInteractables() {
        return inventory.size();
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

    @Override
    public String toString(){
        return inventory.toString();
    }
}
