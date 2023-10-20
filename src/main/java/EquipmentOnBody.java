
import java.util.HashMap;
import java.util.Map;

public class EquipmentOnBody {
    private Map<EquipmentSlot, Equipment> equipmentOnBody;

    public EquipmentOnBody() {
        equipmentOnBody  = new HashMap<>();
    }

    public Equipment getEquipment(EquipmentSlot slot) {
        if(slot!=null) {
            return equipmentOnBody.get(slot);
        }
        return null;
    }

    public void removeEquipment(EquipmentSlot slot) {
        if(slot!=null) {
            equipmentOnBody.remove(slot);
        }
    }

    public void putEquipment(EquipmentSlot slot, Equipment equipment) {
        if(slot!=null && equipment!=null) {
            if (!slotContainsEquipment(slot))
                equipmentOnBody.put(slot, equipment);
        }
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
        StringBuilder sb = new StringBuilder();
        for (Map.Entry<EquipmentSlot, Equipment> pair : equipmentOnBody.entrySet()) {
            sb.append(pair.getKey().toString()).append(": ").append(pair.getValue());
        }
        return sb.toString();
    }
}