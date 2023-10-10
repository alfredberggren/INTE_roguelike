import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class RoomTest {
static final Position DEFAULT_POSITION = new Position(0,0);

static final Set<Interactable.Action> DEFAULT_NPC_ACTIONS = new HashSet<>(Arrays.asList(Interactable.Action.FIGHT, Interactable.Action.TALK));
static final Set<Interactable.Action> DEFAULT_EQUIPMENT_ACTIONS = new HashSet<>(Arrays.asList(Interactable.Action.LOOT, Interactable.Action.DROP))
static final NPC DEFAULT_NPC = new NPC("Harald", 100, 50, DEFAULT_NPC_ACTIONS);

static final Equipment DEFAULT_EQUIPMENT = new Equipment("Bag", DEFAULT_EQUIPMENT_ACTIONS, 10, 20);
static final ArrayList<Interactable> DEFAULT_INTERACTABLES = new ArrayList<>(Arrays.asList(DEFAULT_NPC, DEFAULT_EQUIPMENT));




}
