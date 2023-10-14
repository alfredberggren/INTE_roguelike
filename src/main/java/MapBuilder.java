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
    private Position currentPos;
    private Random r = new Random();
    private InteractableBuilder interactableBuilder;

    public MapBuilder(Difficulty difficulty, int amountOfRooms, Player player, MapController mapController) {
        difficultyScale = DIFF_RATIO.get(difficulty);
        this.amountOfRooms = amountOfRooms;
        this.player = player;
        this.mapController = mapController;
        interactableBuilder = new InteractableBuilder(difficultyScale);
    }

    //TODO: implementera något som gör att rummen inte går in i varandra! Eller vad som händer om det inte finns några directions att gå längre (direction = null)
    public void build() {
        //Set up first room
        currentPos = new Position(START_XY, START_XY);
        InteractableInventory dynInteractables;
        int randomInteractableDeterminator;

        //Sets first room at 0,0 with no interactables and no available directions
        Room currentRoom = new Room(currentPos);
        currentRoom.setPossibleRoutes(mapController.getAvailableDirections(currentPos));
        mapController.add(currentPos, currentRoom);
        Position oldPos = currentPos;

        //Set up rest of rooms
        for (int i = 1; i < amountOfRooms; i++) {
            dynInteractables = new InteractableInventory();

            //Check where rooms can be created from current room
            List<CardinalDirection> tempAvailableDirections = mapController.getUnavailableDirections(currentPos);

            //If no directions, backtrack to room where directions can be taken
            while (tempAvailableDirections.isEmpty()){
                currentPos = getNextPosition(currentRoom.getPossibleRoutes().get(r.nextInt(currentRoom.getPossibleRoutes().size())), currentRoom.getPosition());
                tempAvailableDirections = mapController.getUnavailableDirections(currentPos);
                currentRoom = mapController.getRoom(currentPos);
            }

            //choose random direction
            int nextDir = r.nextInt(tempAvailableDirections.size());
            CardinalDirection direction = tempAvailableDirections.get(nextDir);
            oldPos = currentPos;

            //Generate new XY-position from direction and current position
            currentPos = getNextPosition(direction, currentPos);

            //Check if room exists at new pos
            while (mapController.roomExists(currentPos)){
                currentRoom.addPossibleRoute(direction);
                tempAvailableDirections = mapController.getUnavailableDirections(oldPos);
                nextDir = r.nextInt(tempAvailableDirections.size());
                direction = tempAvailableDirections.get(nextDir);
                currentPos = getNextPosition(direction, oldPos);
            }



            //Check if interactables should generate in new room
            randomInteractableDeterminator = r.nextInt(INTERACTABLE_PERCENTAGE) + 1;
            if (randomInteractableDeterminator == 1) {
                dynInteractables = generateInteractables();
            }

            Room newRoom = new Room(currentPos, dynInteractables);
            mapController.add(currentPos, newRoom);
            newRoom.setPossibleRoutes(mapController.getAvailableDirections(currentPos));
            currentRoom = newRoom;
        }
        //Set all rooms in maps available directions
        mapController.setAvailableDirectionsInRooms();


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
