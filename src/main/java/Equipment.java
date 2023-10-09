import java.util.ArrayList;

public class Equipment {

    private String name;
    private Effect effect;
    private int damage;
    private ArrayList<Equipment> equipments = new ArrayList<>();


    public Equipment(String name, Effect effect, int damage) {
        this.name = name;
        this.effect = effect;
        this.damage = damage;
    }

    public String getName(){
        return name;
    }

    public Effect getEffect() {
        return effect;
    }

    public enum Effect {
        SPEED, HEALTH, DAMAGE
    }

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
