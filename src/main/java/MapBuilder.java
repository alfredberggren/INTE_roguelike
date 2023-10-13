import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

public class MapBuilder {
    private static final HashMap<Difficulty, Integer> DIFF_RATIO = new HashMap<>() {{
        put(Difficulty.EASY, 3);
        put(Difficulty.MEDIUM, 5);
        put(Difficulty.HARD, 7);
    }};
    private static final int START_XY = 0;

    // Bestämmer sannolikheten i procent för att ett rum kommer att innehålla något.
    private static final int INTERACTABLE_PERCENTAGE = 3;
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

    //TODO: implementera något som gör att rummen inte går in i varandra!
    public void build() {
        dynPos = new Position(START_XY, START_XY);
        InteractableInventory dynInteractables;
        int randomInteractableDeterminator;
        mapController.add(dynPos, new Room(dynPos));

        for (int i = 0; i < amountOfRooms; i++) {
            dynInteractables = new InteractableInventory();
            List<CardinalDirection> tempAvailableDirections = mapController.getUnavailableDirections(dynPos);
            int nextDir = r.nextInt(tempAvailableDirections.size());
            CardinalDirection c = tempAvailableDirections.get(nextDir);
            dynPos = getNextPosition(c, dynPos);

            randomInteractableDeterminator = r.nextInt(INTERACTABLE_PERCENTAGE) + 1;
            if (randomInteractableDeterminator == 1) {
                dynInteractables = generateInteractables();
            }
            mapController.add(dynPos, new Room(dynPos, dynInteractables));
        }


    }

    private Position getNextPosition(CardinalDirection cardinalDirection, Position currentPos) {
        switch (cardinalDirection) {
            case EAST -> {
                return new Position(currentPos.getX() + 1, currentPos.getY());
            }
            case WEST -> {
                return new Position(currentPos.getX() - 1, currentPos.getY());
            }
            case NORTH -> {
                return new Position(currentPos.getX(), currentPos.getY() + 1);
            }
            case SOUTH -> {
                return new Position(currentPos.getX(), currentPos.getY() - 1);
            }
            default -> {
                return null;
            }
        }
    }

    private InteractableInventory generateInteractables() {
        InteractableInventory interactables = new InteractableInventory();
        int interactableDeterminator;
        boolean positiveInteractable;
        int amountOfInteractables = generateAmountOfInteractables();

        for (int i = 0; i < amountOfInteractables; i++){
            interactableDeterminator = r.nextInt(10) + 1;
            positiveInteractable= interactableDeterminator > difficultyScale;

            if (positiveInteractable) {
                interactables.add(interactableBuilder.getPositiveInteractable());
            } else {
                interactables.add(interactableBuilder.getNegativeInteractable());
            }
        }
        return interactables;
    }

    //Gaussian madness 9000
    private int generateAmountOfInteractables(){
        return Math.abs((int)r.nextGaussian(0.0, 1.2)) + 1;
    }
}
