
import java.util.HashMap;
import java.util.Map;

public class EquipmentOnBody {
    private Map<EquipmentSlot, Equipment> equipmentOnBody;

    public EquipmentOnBody() {
        equipmentOnBody  = new HashMap<>();
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
    public EquipmentSlot checkWhereEquipmentWasPlaced(Equipment equipment) {
        if(equipment!=null) {
            for (Map.Entry<EquipmentSlot, Equipment> e : equipmentOnBody.entrySet()) {
                if (equipment == e.getValue())
                    return e.getKey();
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
