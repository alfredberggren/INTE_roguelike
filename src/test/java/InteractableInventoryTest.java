
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class InteractableInventoryTest {

    static final Position DEFAULT_POSITION = new Position(0,0);
    static final Set<Interactable.InteractableAction> DEFAULT_NPC_ACTIONS = new HashSet<>(Arrays.asList(Interactable.InteractableAction.FIGHT, Interactable.InteractableAction.TALK));
    static final Set<Interactable.InteractableAction> DEFAULT_EQUIPMENT_ACTIONS = new HashSet<>(Arrays.asList(Interactable.InteractableAction.LOOT, Interactable.InteractableAction.DROP));

    static final NPC DEFAULT_NPC = new NPC("Harald", 100, 50, 1, DEFAULT_POSITION, new TextIO());

    static final Equipment DEFAULT_EQUIPMENT = new Equipment("Sword", EquipmentSlot.RIGHT_HAND, Equipment.Effect.DAMAGE, 40, new PhysicalAbility("Slash",10,1, "Watch your foes get struck to the ground with this mighty slash"));

    InteractableInventory inventory;

    //TODO: might need more rigourous testing
    @Test
    public void testAddMethodAddsInteractable(){
        inventory = new InteractableInventory();
        //Equipment e = new Equipment("Axe", EquipmentSlot.RIGHT_HAND, Equipment.Effect.DAMAGE, 20, new PhysicalAbility("Slash",10,1));
        inventory.add(DEFAULT_EQUIPMENT);
        assertEquals(true, inventory.contains(DEFAULT_EQUIPMENT));
    }

    @Test
    public void testAddMethodThrowsWhenInteractableArgIsNull(){
        setUpDefaultInventory();
        assertThrows(NullPointerException.class, () -> inventory.add(null));
    }

    @Test
    public void testTransferMethodTransfersInteractableWhenInInventory() {
        setUpDefaultInventory();
        InteractableInventory inventory2 = new InteractableInventory();
        inventory.transfer(DEFAULT_EQUIPMENT, inventory2);
        assertEquals(true, inventory2.contains(DEFAULT_EQUIPMENT));
    }

    @Test
    public void testTransferMethodRemovesInteractableFromInventoryWhenInInventory(){
        setUpDefaultInventory();
        InteractableInventory inventory2 = new InteractableInventory();
        inventory.transfer(DEFAULT_EQUIPMENT, inventory2);
        assertEquals(false, inventory.contains(DEFAULT_EQUIPMENT));
    }

    @Test
    public void testTransferMethodDoesNotTransferWhenInteractableNotInInventory(){
        setUpDefaultInventory();
        InteractableInventory inventory2 = new InteractableInventory();
        Equipment e = new Equipment("Potion", EquipmentSlot.BELT, Equipment.Effect.HEALTH, 0, new MagicAbility("Heal",10,1,"Healing",1,1, 1));
        inventory.transfer(e, inventory2);
        assertEquals(false, inventory2.contains(DEFAULT_EQUIPMENT));
    }

    //before each
    private void setUpDefaultInventory(){
        inventory = new InteractableInventory();
        inventory.add(DEFAULT_EQUIPMENT, DEFAULT_NPC);
    }
}

