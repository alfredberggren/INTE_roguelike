import java.util.HashMap;
import java.util.Map;

public class EquipmentOnBody {
    private Map<EquipmentSlot, Equipment> equipmentOnBody = new HashMap();

    public EquipmentOnBody(Map<EquipmentSlot, Equipment> equipmentOnBody) {
        this.equipmentOnBody = equipmentOnBody;
    }

    public void putEquipment(EquipmentSlot slot, Equipment equipment) {
        if (!slotContainsEquipment(slot))
            equipmentOnBody.put(slot, equipment);
        else
            System.out.println("This slot already contains an equipment!");

    }

    public boolean slotContainsEquipment(EquipmentSlot slot) {
        return equipmentOnBody.containsKey(slot);
    }

    public void dropEquipment(EquipmentSlot slot) {
        if(slotContainsEquipment(slot))
            equipmentOnBody.remove(slot);
        else System.out.println("There is no equipment in this slot");
    }

    public String toString() {
        for (Map.Entry<EquipmentSlot, Equipment> pair : equipmentOnBody.entrySet()) {
            return pair.getKey() + ": " + pair.getValue();
        }
        return "";
    }
}
