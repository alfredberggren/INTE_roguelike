import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

//TODO: Testa att alla rum som har rum brevid sig har riktningar dit och rummet intill har riktning tillbaks till rummet man kom ifrån

@ExtendWith(MockitoExtension.class)
public class MapBuilderTest {

    @Mock
    InteractableGenerator mockedIG1;
    @Mock
    InteractableGenerator mockedIG2;
    @Mock
    InteractableGenerator mockedIG3;
    @Mock
    InteractableGenerator mockedIG4;
    @Mock
    InteractableGenerator mockedIG5;

    @Mock
    static IO io;

    private static final Set<Interactable.InteractableAction> DEFAULT_INTERACTABLE_ACTIONS = new HashSet<>(Arrays.asList(
            Interactable.InteractableAction.LOOT,
            Interactable.InteractableAction.DROP,
            Interactable.InteractableAction.USE)
    );

    private static final Room TEST_ROOM = new Room(new Position(0, 0), new InteractableInventory());

    private static final NPC TEST_NPC = new NPC("Test Testsson", 100, 10, new Position(0, 0), io);
    private static final FoodItem TEST_FOOD_ITEM = new FoodItem("Bread", 10);
    private static final PotionItem TEST_POTION_ITEM = new PotionItem("HealthPotion", Equipment.Effect.HEALTH, 5);
    private static final Prop TEST_PROP = new Prop("Key", DEFAULT_INTERACTABLE_ACTIONS);
    private static final Equipment TEST_EQUIPMENT = new Equipment("Stone Sword", DEFAULT_INTERACTABLE_ACTIONS, Equipment.Effect.DAMAGE, 10, new PhysicalAbility("Swipe", 10, 5));

    private static final Position DEFAULT_VALID_PLAYER_POSITION = new Position(0, 0);
    private static final Player DEFAULT_PLAYER = new Player("Spelarsson", 100, 10, DEFAULT_VALID_PLAYER_POSITION, io);

    private HashMap<InteractableGenerator, Integer> positiveInteractableProbabilityMap;

    private InteractableDirector interactableDirector;

    private MapController mapController;
    private MapBuilder mapBuilder;

    private Map<HashMap<InteractableGenerator, Integer>, Integer> getTestProbabilityMap() {
        // Innehåller InteractableGenerators som genererar Interactables när kartan byggs, och Integers som är ett värde i procent för sannolikheten att den korrsponderande generatorn används.
        Map<HashMap<InteractableGenerator, Integer>, Integer> testProbabilityMap = new HashMap<>();
        positiveInteractableProbabilityMap = new HashMap<>() {{
            put(mockedIG1, 10);
            put(mockedIG2, 40);
            put(mockedIG3, 20);
            put(mockedIG4, 5);
            put(mockedIG5, 25);
        }};
        testProbabilityMap.put(positiveInteractableProbabilityMap, 100);
        return testProbabilityMap;
    }

    @BeforeEach
    public void setup() {
        mapController = new MapController();
        Mockito.when(mockedIG1.generateInteractable()).thenReturn(TEST_NPC);
        Mockito.when(mockedIG2.generateInteractable()).thenReturn(TEST_FOOD_ITEM);
        Mockito.when(mockedIG3.generateInteractable()).thenReturn(TEST_POTION_ITEM);
        Mockito.when(mockedIG4.generateInteractable()).thenReturn(TEST_PROP);
        Mockito.when(mockedIG5.generateInteractable()).thenReturn(TEST_EQUIPMENT);

        Map<HashMap<InteractableGenerator, Integer>, Integer> testProbabilityMap = getTestProbabilityMap();
        interactableDirector = new InteractableDirector(testProbabilityMap);

        mapBuilder = new MapBuilder(Difficulty.MEDIUM, 1000, DEFAULT_PLAYER, mapController, interactableDirector);
        mapBuilder.build();
    }

    @AfterEach
    public void reset() {
        mapController = null;
        mapBuilder = null;
    }

    @Test
    @DisplayName("Testar om kartan har NPC-karaktärer.")
    public void test_afterBuildingMap_generatesNPCs() {
        assertEquals(true, mapController.containsInteractable(TEST_NPC));
    }

    @Test
    @DisplayName("Testar om antalet interactables av varje typ stämmer överrens med procentsatserna i positiveInteractableProbabilityMap")
    public void test_afterBuildingMap_generatesInteractableAmountsInAccordanceWithProbabilityMap() {
        TreeMap<Position, Room> gameMap = mapController.getGameMap();
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
                case "class NPC" -> npc++;
                case "class FoodItem" -> food++;
                case "class PotionItem" -> potion++;
                case "class Equipment" -> equip++;
                case "class Prop" -> prop++;
                default -> {
                }
            }
        }

        double total = allInteractables.size();

        assertTrue(((npc / total) * 100) > positiveInteractableProbabilityMap.get(mockedIG1) - 2
                        && ((npc / total) * 100) < positiveInteractableProbabilityMap.get(mockedIG1) + 2,
                "Antalet NPC borde ligga nära den procentsats som finns i sannolikhetsmappen.");
        assertTrue(((food / total) * 100) > positiveInteractableProbabilityMap.get(mockedIG2) - 2
                        && ((food / total) * 100) < positiveInteractableProbabilityMap.get(mockedIG2) + 2,
                "Antalet Food borde ligga nära den procentsats som finns i sannolikhetsmappen.");
        assertTrue(((potion / total) * 100) > positiveInteractableProbabilityMap.get(mockedIG3) - 2
                        && ((potion / total) * 100) < positiveInteractableProbabilityMap.get(mockedIG3) + 2,
                "Antalet Potion borde ligga nära den procentsats som finns i sannolikhetsmappen.");
        assertTrue(((prop / total) * 100) > positiveInteractableProbabilityMap.get(mockedIG4) - 2
                        && ((prop / total) * 100) < positiveInteractableProbabilityMap.get(mockedIG4) + 2,
                "Antalet Prop borde ligga nära den procentsats som finns i sannolikhetsmappen.");
        assertTrue(((equip / total) * 100) > positiveInteractableProbabilityMap.get(mockedIG5) - 2
                        && ((equip / total) * 100) < positiveInteractableProbabilityMap.get(mockedIG5) + 2,
                "Antalet Equipment borde ligga nära den procentsats som finns i sannolikhetsmappen.");
    }
// TODO: should be in a different class
//    @Test
//    @DisplayName("Testar TreeMappen gameMap (compareTo i position)")
//    public void test_whenBuildingMap_gameMapHasAllPositions() {
//        mapController.add(new Position(0, 0), TEST_ROOM);
//        mapController.add(new Position(1, 0), TEST_ROOM);
//        mapController.add(new Position(2, 0), TEST_ROOM);
//        mapController.add(new Position(1, 1), TEST_ROOM);
//        TreeMap<Position, Room> gameMap = mapController.getGameMap();
//        Set<Position> expectedPositions = new HashSet<>();
//        expectedPositions.add(new Position(0, 0));
//        expectedPositions.add(new Position(1, 0));
//        expectedPositions.add(new Position(2, 0));
//        expectedPositions.add(new Position(1, 1));
//        assertEquals(expectedPositions, gameMap.keySet());
//    }

    @Test
    @DisplayName("Testar så att alla rum på kartan kan besökas av spelaren")
    public void test_afterBuildingMap_allRoomsCanBeVisited() {
        List<Room> visited = new ArrayList<>();
        Position start = new Position(0, 0);
        visited.add(mapController.getRoom(start));

        for (int i = 0; i < visited.size(); i++) {
            List<Room> adjacentRooms = mapController.getAdjacentRooms(visited.get(i));
            for (Room r : adjacentRooms) {
                if (!visited.contains(r)) {
                    visited.add(r);
                }
            }
        }

        assertEquals(mapController.getGameMap().values().size(), visited.size(), "Alla rum ska kunna besökas! Det verkar som att antalet rum man kan besöka inte stämmer överrens med det totala antalet rum.");
    }

    @Test
    @DisplayName("Testar så att rummens möjliga riktningar blir korrekta efter byggning")
    public void test_whenBuildingMap_allRoomsHaveCorrectPossibleRoutes() {

    }

    @Test
    @DisplayName("Testar så att rummens riktning går åt båda hållen, alltså att om ett rum har en riktning till ett annat rum, så har de andra rummet en riktning tillbaka till det som det kom ifrån.")
    public void test_afterBuildingMap_directionsWorkInBothWays() {
        for (Room r : mapController.getGameMap().values()) {
            List<Room> adjacentRooms = mapController.getAdjacentRooms(r);
            for (Room r2 : adjacentRooms) {
                assertTrue(mapController.getAdjacentRooms(r2).contains(r));
            }
        }
    }

    @Test
    @DisplayName("Medan man bygger, är alla rummens riktningar korrekta?")
    public void test_whileBuildingMap_allRoomsHaveCorrectPossibleRoutes() {
        mapBuilder.setLogMap(true);
        mapBuilder.build();
        mapBuilder.setLogMap(false);
        Map<Position, Room> testMap = parseLogMap("logs/MapBuilderLog.log");
        for (Room r : testMap.values()) {
            List<Room> adjacentRooms = mapController.getAdjacentRooms(r);
            for (Room r2 : adjacentRooms) {
                assertTrue(mapController.getAdjacentRooms(r2).contains(r));
            }
        }
    }

    private Map<Position, Room> parseLogMap(String s) {
        Map<Position, Room> testMap = new HashMap<>();
        try {
            File mapFile = new File(s);
            Scanner reader = new Scanner(mapFile);
            while (reader.hasNextLine()) {
                String data = reader.nextLine();
                String[] parts = data.split(";");
                if (parts[0].isEmpty()) {
                    continue;
                }
                int x = Integer.parseInt(parts[0]);
                int y = Integer.parseInt(parts[1]);
                Position p = new Position(x, y);
                Room r = new Room(p);
                int amtOfDirs = Integer.parseInt(parts[2]);
                List<CardinalDirection> testDirs = new ArrayList<>();
                for (int i = 0; i < amtOfDirs; i++) {
                    r.addPossibleRoute(parseDir(parts[i + 3]));
                }
                testMap.put(p, r);
            }
            reader.close();
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
        return testMap;
    }

    private CardinalDirection parseDir(String part) {
        switch (part) {
            case "EAST" -> {
                return CardinalDirection.EAST;
            }
            case "SOUTH" -> {
                return CardinalDirection.SOUTH;
            }
            case "WEST" -> {
                return CardinalDirection.WEST;
            }
            case "NORTH" -> {
                return CardinalDirection.NORTH;
            }
        }
        return null;
    }
}
