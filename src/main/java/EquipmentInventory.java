import java.util.ArrayList;

public class EquipmentInventory {

    private ArrayList<Equipment> equipments = new ArrayList<>();

    public void add(Equipment e) {
        equipments.add(e);
    }

    /*public void discard(Equipment e){
        equipments.remove(e);
        Position current = e.getPos();   // ?? Hämta spelarens position och sätta den till equipment
                                         //om utrustning slängs ska den hamna på kartan
        e.setPos(current);
    }*/

    public ArrayList<Equipment> getEquipments() {
        return equipments;
    }

    public Equipment getEquipment(int index){
        return equipments.get(index);
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
