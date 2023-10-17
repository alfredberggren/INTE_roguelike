import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class EquipmentOnBody {
    private Map<EquipmentSlot, Equipment> equipmentOnBody = new HashMap<>();

    public EquipmentOnBody() {
    }

    public EquipmentOnBody(Map<EquipmentSlot, Equipment> equipmentOnBody) {
        this.equipmentOnBody = equipmentOnBody;
    }

    public Equipment getEquipment(EquipmentSlot slot) {
        return equipmentOnBody.get(slot);
    }

    public void removeEquipment(EquipmentSlot slot) {
        equipmentOnBody.remove(slot);
    }

    public void putEquipment(EquipmentSlot slot, Equipment equipment) {
        if (!slotContainsEquipment(slot))
            equipmentOnBody.put(slot, equipment);
    }

    public boolean slotContainsEquipment(EquipmentSlot slot) {
        return equipmentOnBody.containsKey(slot);
    }

    /**
     * find the key(int the Map) - equipment's slot for a value (equipment)
     */
    public EquipmentSlot checkWhereEquipmentWasPlaced(Equipment e) {
        Collection<EquipmentSlot> equipmentSlots = equipmentOnBody.keySet();
        for (EquipmentSlot key : equipmentSlots) {
            Equipment equipment = equipmentOnBody.get(key);
            if (key != null) {
                if (e.equals(equipment)) {
                    return key;
                }
            }
        }
        return null;
    }

    public String toString() {
        for (Map.Entry<EquipmentSlot, Equipment> pair : equipmentOnBody.entrySet()) {
            return pair.getKey() + ": " + pair.getValue();
        }
        return "";
    }
}
