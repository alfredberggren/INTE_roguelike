import org.junit.After;
import org.junit.Before;
import org.junit.jupiter.api.DisplayName;
import org.junit.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

/*
 * TODO: Skriv ett test som kollar om sannolikhetsvärdena i probabilityMap stämmer överrens med hur många av varje Interactable som faktiskt genereras.
 */

@ExtendWith(MockitoExtension.class)
@RunWith(MockitoJUnitRunner.class)
public class MapBuilderTest {

    @Mock InteractableGenerator mockedIG1;
    @Mock InteractableGenerator mockedIG2;
    @Mock InteractableGenerator mockedIG3;
    @Mock InteractableGenerator mockedIG4;
    @Mock InteractableGenerator mockedIG5;

    @Mock static IO io;

    private static final Set<Interactable.InteractableAction> DEFAULT_INTERACTABLE_ACTIONS = new HashSet<>(Arrays.asList(
            Interactable.InteractableAction.LOOT,
            Interactable.InteractableAction.DROP,
            Interactable.InteractableAction.USE)
    );

    private static final NPC TEST_NPC = new NPC("Test Testsson", 100, 10, new Position(0, 0), io);
    private static final FoodItem TEST_FOOD_ITEM = new FoodItem("Bread", 10);
    private static final PotionItem TEST_POTION_ITEM = new PotionItem("HealthPotion", Equipment.Effect.HEALTH, 5);
    private static final Prop TEST_PROP = new Prop("Key", DEFAULT_INTERACTABLE_ACTIONS);
    private static final Equipment TEST_EQUIPMENT = new Equipment("Stone Sword", DEFAULT_INTERACTABLE_ACTIONS, Equipment.Effect.DAMAGE, 10, new PhysicalAbility("Swipe", 10, 5));

    private static final Position DEFAULT_VALID_PLAYER_POSITION = new Position(0, 0);
    private static final Player DEFAULT_PLAYER = new Player("Spelarsson", 100, 10, DEFAULT_VALID_PLAYER_POSITION, io);

    private InteractableDirector interactableDirector;

    private MapController mapController;
    private MapBuilder mapBuilder;

    private Map<HashMap<InteractableGenerator, Integer>, Integer> getTestProbabilityMap() {
        // Innehåller InteractableGenerators som genererar Interactables när kartan byggs, och Integers som är ett värde i procent för sannolikheten att den korrsponderande generatorn används.
        HashMap<InteractableGenerator, Integer> positiveInteractableProbabilityMap = new HashMap<>() {{
            put(mockedIG1, 10);
            put(mockedIG2, 40);
            put(mockedIG3, 20);
            put(mockedIG4, 5);
            put(mockedIG5, 25);
        }};
        Map<HashMap<InteractableGenerator, Integer>, Integer> testProbabilityMap = new HashMap<>();
        testProbabilityMap.put(positiveInteractableProbabilityMap, 100);
        return testProbabilityMap;
    }

    @Before
    public void setup() {
        mapController = new MapController();
        Mockito.when(mockedIG1.generateInteractable()).thenReturn(TEST_NPC);
        Mockito.when(mockedIG2.generateInteractable()).thenReturn(TEST_FOOD_ITEM);
        Mockito.when(mockedIG3.generateInteractable()).thenReturn(TEST_POTION_ITEM);
        Mockito.when(mockedIG4.generateInteractable()).thenReturn(TEST_PROP);
        Mockito.when(mockedIG5.generateInteractable()).thenReturn(TEST_EQUIPMENT);

        Map<HashMap<InteractableGenerator, Integer>, Integer> testProbabilityMap = getTestProbabilityMap();
        interactableDirector = new InteractableDirector(testProbabilityMap);

        mapBuilder = new MapBuilder(Difficulty.MEDIUM, 4_000_000, DEFAULT_PLAYER, mapController, interactableDirector);
    }

    @After
    public void reset() {
        mapController = null;
        mapBuilder = null;
    }

    @Test
    @DisplayName("Testar om kartan har NPC-karaktärer.")
    public void test_buildingMap_generatesNPCs() {
        mapBuilder.build();
        assertEquals(true, mapController.containsInteractable(TEST_NPC));
    }

    @Test
    @DisplayName("Testar om antalet interactables av varje typ stämmer överrens med procentsatserna i positiveInteractableProbabilityMap")
    public void test_buildingMap_generatesInteractableAmountsInAccordanceWithProbabilityMap() {
        mapBuilder.build();
        HashMap<Position, Room> gameMap = mapController.getGameMap();
        ArrayList<Interactable> allInteractables = new ArrayList<>();
        for (Room r : gameMap.values()) {
            allInteractables.addAll(r.getInteractables().getAll());
        }

        double npc = 0;
        double food = 0;
        double potion = 0;
        double equip = 0;
        double prop = 0;

        for (Interactable i : allInteractables) {
            switch (i.getClass().toString()) {
                case "class NPC": npc++; break;
                case "class FoodItem": food++; break;
                case "class PotionItem": potion++; break;
                case "class Equipment": equip++; break;
                case "class Prop": prop++; break;
                default: break;
            }
        }

        double total = allInteractables.size();

        System.out.println("NPC: " + (npc / total) * 100 + " %\nFoodItem: " + (food / total) * 100 + " %\nPotionItem: " + (potion / total) * 100 +  " %\nProp: " + (prop / total) * 100 + " %\nEquipment: " + (equip / total) * 100 + " %");
    }

    @Test
    @DisplayName("Test som ska användas med profiler")
    public void test_Profiler(){
        mapBuilder.build();
    }
}
