
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class InteractableInventoryTest {
    static final Set<Interactable.InteractableAction> DEFAULT_NPC_ACTIONS = new HashSet<>(Arrays.asList(Interactable.InteractableAction.FIGHT, Interactable.InteractableAction.TALK));
    static final Set<Interactable.InteractableAction> DEFAULT_EQUIPMENT_ACTIONS = new HashSet<>(Arrays.asList(Interactable.InteractableAction.LOOT, Interactable.InteractableAction.DROP));

    static final NPC DEFAULT_NPC = new NPC("Harald", 100, 50, 10, DEFAULT_NPC_ACTIONS);

    static final Equipment DEFAULT_EQUIPMENT = new Equipment("Sword", DEFAULT_EQUIPMENT_ACTIONS, Equipment.Effect.DAMAGE, 40, new PhysicalAbility("Slash",10,"Physical"));

    InteractableInventory inventory;

    @Test
    public void testAddMethodAddsInteractable(){
        inventory = new InteractableInventory();
        Equipment e = new Equipment("Axe", DEFAULT_EQUIPMENT_ACTIONS, Equipment.Effect.DAMAGE, 20, new PhysicalAbility("Slash",10,"Physical"));
        inventory.add(e);
        assertEquals(true, inventory.contains(e));
    }

    @Test
    public void testAddMethodIncreasesValueOfAlreadyExistingInteractable() {
        setUpDefaultInventory();
        inventory.add(DEFAULT_EQUIPMENT);
        assertEquals(2, inventory.getAmountOf(DEFAULT_EQUIPMENT));
    }

    @Test
    public void testAddMethodThrowsWhenInteractableArgIsNull(){
        setUpDefaultInventory();
        assertThrows(NullPointerException.class, () -> inventory.add(null));
    }

    @Test
    public void testAddMethodOnlyAcceptsPositiveIntegers() {
        setUpDefaultInventory();
        assertThrows(IllegalArgumentException.class, () -> inventory.add(DEFAULT_EQUIPMENT, -1));
    }

    @Test
    public void testRemoveAllMethodRemovesAllCopiesOfInteractable(){
        setUpDefaultInventory();
        inventory.add(DEFAULT_EQUIPMENT);
        inventory.removeAll(DEFAULT_EQUIPMENT);
        assertEquals(0, inventory.getAmountOf(DEFAULT_EQUIPMENT));
    }

    @Test
    public void testRemoveMethodRemovesOnlyOne(){
        setUpDefaultInventory();
        inventory.add(DEFAULT_EQUIPMENT);
        inventory.remove(DEFAULT_EQUIPMENT);
        assertEquals(1, inventory.getAmountOf(DEFAULT_EQUIPMENT));
    }

    @Test
    public void testRemoveMethodWithAmountOverTotalRemovesAll(){
        setUpDefaultInventory();
        inventory.add(DEFAULT_EQUIPMENT);
        inventory.remove(DEFAULT_EQUIPMENT, 4);
        assertEquals(0, inventory.getAmountOf(DEFAULT_EQUIPMENT));
    }

    @Test
    public void testTransferMethodTransfersInteractableWhenInInventory() {
        setUpDefaultInventory();
        InteractableInventory inventory2 = new InteractableInventory();
        inventory.transferInteractableTo(DEFAULT_EQUIPMENT, inventory2);
        assertEquals(true, inventory2.contains(DEFAULT_EQUIPMENT));
    }

    @Test
    public void testTransferMethodRemovesInteractableFromInventoryWhenInInventory(){
        setUpDefaultInventory();
        InteractableInventory inventory2 = new InteractableInventory();
        inventory.transferInteractableTo(DEFAULT_EQUIPMENT, inventory2);
        assertEquals(false, inventory.contains(DEFAULT_EQUIPMENT));
    }

    @Test
    public void testTransferMethodDoesNotTransferWhenInteractableNotInInventory(){
        setUpDefaultInventory();
        InteractableInventory inventory2 = new InteractableInventory();
        Equipment e = new Equipment("Potion", DEFAULT_EQUIPMENT_ACTIONS, Equipment.Effect.HEALTH, 0, new MagicAbility("Heal",10,"Magic"));
        inventory.transferInteractableTo(e, inventory2);
        assertEquals(false, inventory2.contains(DEFAULT_EQUIPMENT));
    }


    private void setUpDefaultInventory(){
        inventory = new InteractableInventory();
        inventory.add(DEFAULT_EQUIPMENT);
        inventory.add(DEFAULT_NPC);
    }
}

