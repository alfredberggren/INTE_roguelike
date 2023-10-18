import org.junit.Before;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
@RunWith(MockitoJUnitRunner.class)
public class MapBuilderTest {
    class MockedGenerator implements InteractableGenerator {
        public Interactable generateInteractable() {
            return TEST_NPC;
        }
    }
    MockedGenerator mockedG;

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
    private static final Equipment TEST_EQUIPMENT = new Equipment("Stone Sword", EquipmentSlot.RIGHT_HAND, Equipment.Effect.DAMAGE, 10, new PhysicalAbility("Swipe", 10, 5));

    private static final Position DEFAULT_VALID_PLAYER_POSITION = new Position(0, 0);
    private static final Player DEFAULT_PLAYER = new Player("Spelarsson", 100, 10, DEFAULT_VALID_PLAYER_POSITION, io);

    private InteractableDirector interactableDirector;

    private MapController mapController;
    private MapBuilder mapBuilder;

    private Map<HashMap<InteractableGenerator, Integer>, Integer> getTestProbabilityMap() {
        HashMap<InteractableGenerator, Integer> positiveInteractableProbabilityMap = new HashMap<>() {{
            put(mockedG, 100);
//            put(mockedIG2, 10);
//            put(mockedIG3, 20);
//            put(mockedIG4, 5);
//            put(mockedIG5, 25);
        }};
        Map<HashMap<InteractableGenerator, Integer>, Integer> testProbabilityMap = new HashMap<>();
        testProbabilityMap.put(positiveInteractableProbabilityMap, 100);
        return testProbabilityMap;
    }

    @Before
    public void setup() {
        mapController = new MapController();
//        Mockito.when(mockedIG1.generateInteractable()).thenReturn(TEST_NPC);
//        Mockito.when(mockedIG2.generateInteractable()).thenReturn(TEST_FOOD_ITEM);
//        Mockito.when(mockedIG3.generateInteractable()).thenReturn(TEST_POTION_ITEM);
//        Mockito.when(mockedIG4.generateInteractable()).thenReturn(TEST_PROP);
//        Mockito.when(mockedIG5.generateInteractable()).thenReturn(TEST_EQUIPMENT);

        Map<HashMap<InteractableGenerator, Integer>, Integer> testProbabilityMap = getTestProbabilityMap();
        interactableDirector = new InteractableDirector(testProbabilityMap);

        mapBuilder = new MapBuilder(Difficulty.MEDIUM, 3000, DEFAULT_PLAYER, mapController, interactableDirector);
    }

    @Test
    @DisplayName("Testar om kartan har NPC-karaktärer.")
    public void test_buildingMap_generatesNPCs() {
        mapBuilder.build();
        assertEquals(true, mapController.containsInteractable(TEST_NPC));
    }

//    @Test
//    @DisplayName("Testar att om kartan har olika interactables")
//    public void test_buildingMap_generatesDifferentInteractables() {
//
//    }
}
