import java.util.ArrayList;

public class EquipmentInventory {

    private ArrayList<Equipment> equipments = new ArrayList<>();

    public void add(Equipment e) {
        equipments.add(e);
    }

    public void drop(Equipment e){

        equipments.remove(e);
    }

    public ArrayList<Equipment> getEquipments() {
        return equipments;
    }

    public void removeAllEquipments() {
        equipments.clear();
    }

    public String toString(){
        String s = "";
        for(Equipment e: equipments){
            s += e + "\n";
        }
        return s;
    }
}
