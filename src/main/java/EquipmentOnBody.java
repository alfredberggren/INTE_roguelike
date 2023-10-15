import java.util.Collection;
import java.util.Map;

public class EquipmentOnBody {
    private Map<EquipmentSlot, Equipment> equipmentOnBody;

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
        if (slotContainsEquipment(slot)) {
//            Equipment e = equipmentOnBody.get(slot);
//            e.setPos(player.getPos());
            equipmentOnBody.remove(slot);
        } else System.out.println("There is no equipment in this slot");
    }

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
