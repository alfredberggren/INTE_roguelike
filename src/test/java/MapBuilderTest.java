//import org.junit.Test;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.junit.runner.RunWith;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.Mockito;
//import org.mockito.MockitoAnnotations;
//import org.mockito.junit.MockitoJUnitRunner;
//import org.mockito.junit.jupiter.MockitoExtension;
//
//import java.util.HashMap;
//import java.util.Map;
//
//import static org.junit.jupiter.api.Assertions.*;
//
//@ExtendWith(MockitoExtension.class)
//@RunWith(MockitoJUnitRunner.class)
//public class MapBuilderTest {
//    class MockedInteractableGenerator implements InteractableGenerator {
//        @Override
//        public Interactable generateInteractable() {
//            return null;
//        }
//
//        @Override
//        public boolean equals(Object obj) {
//            return false;
//        }
//    }
//
//    @Mock
//    MockedInteractableGenerator mockedIG1 = new MockedInteractableGenerator();
//    @Mock
//    IO io;
//
//    private static final NPC TEST_NPC = new NPC("Test Testsson", 100, 10, new Position(0,0), io);
//    private static final Position DEFAULT_VALID_PLAYER_POSITION = new Position(0, 0);
//    private static final Player DEFAULT_PLAYER = new Player(100, 10, DEFAULT_VALID_PLAYER_POSITION);
//    private final HashMap<InteractableGenerator, Integer> NEGATIVE_INTERACTABLE_PROBABILITY_MAP = new HashMap<>() {{
//        put(mockedIG1, 10);
//        put(mockedIG1, 40);
//        put(mockedIG1, 10);
//        put(mockedIG1, 20);
//        put(mockedIG1, 20);
//    }};
//    private final HashMap<InteractableGenerator, Integer> POSITIVE_INTERACTABLE_PROBABILITY_MAP = new HashMap<>() {{
//        put(mockedIG1, 25);
//        put(mockedIG1, 50);
//        put(mockedIG1, 5);
//        put(mockedIG1, 5);
//        put(mockedIG1, 10);
//        put(mockedIG1, 5);
//    }};
//
//    private MapController mapController;
//    private MapBuilder mapBuilder;
//
//    @BeforeEach
//    public void setup() {
//        MockitoAnnotations.initMocks(this);
//    }
//
//    @Test
//    public void test_afterBuildingMap_GeneratesNPCs() {
////        mapController = new MapController();
////        Map<HashMap<InteractableGenerator, Integer>, Integer> testProbabilityMap = new HashMap<>();
////        testProbabilityMap.put(POSITIVE_INTERACTABLE_PROBABILITY_MAP, 90);
////        testProbabilityMap.put(NEGATIVE_INTERACTABLE_PROBABILITY_MAP, 10);
////        InteractableDirector interactableDirector = new InteractableDirector(testProbabilityMap);
////
////
////        mapBuilder = new MapBuilder(Difficulty.MEDIUM, 1000, DEFAULT_PLAYER, mapController, interactableDirector);
//
//        Mockito.when(mockedIG1.generateInteractable()).thenReturn(TEST_NPC);
//        assertEquals(TEST_NPC, mockedIG1.generateInteractable());
//        //mapBuilder.build();
//        //assertTrue(mapController.containsInteractable(TEST_NPC));
//    }
//}
