import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class RoomTest {
static final Position DEFAULT_POSITION = new Position(0,0);

static final Set<Interactable.InteractableAction> DEFAULT_NPC_ACTIONS = new HashSet<>(Arrays.asList(Interactable.InteractableAction.FIGHT, Interactable.InteractableAction.TALK));
static final Set<Interactable.InteractableAction> DEFAULT_EQUIPMENT_ACTIONS = new HashSet<>(Arrays.asList(Interactable.InteractableAction.LOOT, Interactable.InteractableAction.DROP));
static final NPC DEFAULT_NPC = new NPC("Harald", 100, 50, DEFAULT_NPC_ACTIONS);

static final Equipment DEFAULT_EQUIPMENT = new Equipment("Sword", DEFAULT_EQUIPMENT_ACTIONS, Equipment.Effect.DAMAGE, 20);
static final ArrayList<Interactable> DEFAULT_INTERACTABLES = new ArrayList<>(Arrays.asList(DEFAULT_NPC, DEFAULT_EQUIPMENT));

@Test
void assertConstructorSetsCorrectPosition() {
    Room r = new Room(DEFAULT_POSITION);
    assertEquals(DEFAULT_POSITION, r.getPosition());
}


}
