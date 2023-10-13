public class EquipmentSlot {
    private String name;

    public EquipmentSlot(String name){
        if (name == null) {
            throw new NullPointerException("Name cannot be null!");
        }
        if (name.isEmpty()) {
            throw new IllegalArgumentException("Name cannot be empty!");
        }
        this.name = name;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return name;
    }
}
