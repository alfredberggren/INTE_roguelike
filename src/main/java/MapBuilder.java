import java.util.HashMap;
import java.util.Random;

public class MapBuilder {
    private static final HashMap<Difficulty, Integer> DIFF_RATIO = new HashMap<>() {{
        put(Difficulty.EASY, 3);
        put(Difficulty.MEDIUM, 5);
        put(Difficulty.HARD, 7);
    }};
    private static final int START_XY = 0;

    // Bestämmer sannolikheten i procent för att ett rum kommer att innehålla något.
    private static final int INTERACTABLE_PERCENTAGE = 10;
    private int difficultyScale;
    private int amountOfRooms;
    private MapController mapController;
    private Player player;
    private Position dynPos;
    private Random r = new Random();
    private InteractableBuilder interactableBuilder;

    public MapBuilder(Difficulty difficulty, int amountOfRooms, Player player, MapController mapController) {
        difficultyScale = DIFF_RATIO.get(difficulty);
        this.amountOfRooms = amountOfRooms;
        this.player = player;
        this.mapController = mapController;
        interactableBuilder = new InteractableBuilder(difficultyScale);
    }

    public void build() {
        dynPos = new Position(START_XY, START_XY);
        HashMap<Interactable, Integer> dynInteractables = new HashMap<>();
        int randomInteractableDeterminator;
        for (int i = 0; i < amountOfRooms; i++) {
            randomInteractableDeterminator = r.nextInt(INTERACTABLE_PERCENTAGE);
            if (randomInteractableDeterminator == 1) {
                dynInteractables = generateInteractables();
            } else {
                dynInteractables.clear();
            }
            mapController.add(dynPos, new Room(dynPos, dynInteractables));
        }
    }

    private HashMap<Interactable, Integer> generateInteractables() {
        HashMap<Interactable, Integer> interactables = new HashMap<>();
        int interactableDeterminator = r.nextInt(10) + 1;
        boolean positiveInteractable = interactableDeterminator > difficultyScale;
        if (positiveInteractable) {
            interactables.put(interactableBuilder.getPositiveInteractable(), 1);
        } else {
            interactables.put(interactableBuilder.getNegativeInteractable(), 1);
        }
    }
}
