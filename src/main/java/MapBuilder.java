import java.util.EnumMap;
import java.util.List;
import java.util.Random;

public class MapBuilder {
    private static final EnumMap<Difficulty, Integer> DIFF_RATIO = new EnumMap<>(Difficulty.class) {{
        put(Difficulty.EASY, 3);
        put(Difficulty.MEDIUM, 5);
        put(Difficulty.HARD, 7);
    }};

    private static final int START_XY = 0;

    // Bestämmer sannolikheten i procent för att ett rum kommer att innehålla något.
    private static final int INTERACTABLE_PERCENTAGE = 50;
    private static final int TOTAL_PERCENT = 100;
    private int difficultyScale;
    private int amountOfRooms;
    private MapController mapController;
    private Player player;
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
        Position currentPos = new Position(START_XY, START_XY);
        Position oldPos;
        InteractableInventory dynInteractables;
        int randomInteractableDeterminator;

        //Sets first room at 0,0 with no interactables and no available directions
        Room currentRoom = new Room(currentPos);
        currentRoom.setPossibleRoutes(mapController.getAvailableDirections(currentPos));
        mapController.add(currentPos, currentRoom);


        //Set up rest of rooms
        for (int i = 1; i < amountOfRooms; i++) {
            dynInteractables = new InteractableInventory();

            oldPos = currentPos;
            currentPos = decideNextPosition(currentPos);

            //Kristian: Commented code below was my hard work, based on Alfred's. Came up with different solution, and implemented that instead. Pending Alfred´s approval, please remove.

            //Check where rooms can be created from current room
//            List<CardinalDirection> tempAvailableDirections = mapController.getUnavailableDirections(currentPos);

            //If no directions, backtrack to room where directions can be taken
//            while (tempAvailableDirections.isEmpty()){
//                //Set pos from another random direction in currentRoom
//                currentPos = getNextPosition(currentRoom.getPossibleRoutes().get(r.nextInt(currentRoom.getPossibleRoutes().size())), currentRoom.getPosition());
//                //Set availableDirections from the new pos
//                tempAvailableDirections = mapController.getUnavailableDirections(currentPos);
//                //Set current room as the room at currentPos
//                currentRoom = mapController.getRoom(currentPos);
//            }

            //choose random direction
//            int nextDir = r.nextInt(tempAvailableDirections.size());
//            CardinalDirection direction = tempAvailableDirections.get(nextDir);
//            oldPos = currentPos;
//
//            //Generate new XY-position from direction and current position
//            currentPos = getNextPosition(direction, currentPos);

            //Check if room exists at new pos
//            while (mapController.roomExists(currentPos)){
//                //Add possible route to current room
//                currentRoom.addPossibleRoute(direction);
//
//                //get new direction from oldPos
//                tempAvailableDirections = mapController.getUnavailableDirections(oldPos);
//                nextDir = r.nextInt(tempAvailableDirections.size());
//                direction = tempAvailableDirections.get(nextDir);
//
//                //Set new pos
//                currentPos = getNextPosition(direction, oldPos);
//            }



            //Check if interactables should generate in new room
            randomInteractableDeterminator = r.nextInt(TOTAL_PERCENT) + 1;
            if (randomInteractableDeterminator <= INTERACTABLE_PERCENTAGE) {
                dynInteractables = generateInteractables();
            }

            Room newRoom = new Room(currentPos, dynInteractables);
            mapController.add(currentPos, newRoom);
            newRoom.setPossibleRoutes(mapController.getAvailableDirections(currentPos));

            //Sets routes on current/old room even if new room is not directly connected. Should not matter, but could mean extra computing.
            currentRoom.setPossibleRoutes(mapController.getAvailableDirections(oldPos));
            currentRoom = newRoom;

        }
        //Set all rooms in maps available directions, should not be needed now
        //mapController.setAvailableDirectionsInRooms();


    }


    private Position decideNextPosition(Position pos){
        int rndDirectionIndex;
        //Get dirs where route can be created
        List<CardinalDirection> possibleNewDirs = mapController.getUnavailableDirections(pos);

        //If pos's directions are already filled
        while (possibleNewDirs.isEmpty()){
            //choose a random existing direction from pos
            List<CardinalDirection> existingDirs = mapController.getAvailableDirections(pos);
            rndDirectionIndex = r.nextInt(existingDirs.size());

            //and set pos from those, try to get newDirs from there
            pos = getNextPosition(existingDirs.get(rndDirectionIndex), pos);
            possibleNewDirs = mapController.getUnavailableDirections(pos);
            //if directions still filled, repeat
        }

        rndDirectionIndex = r.nextInt(possibleNewDirs.size());
        return getNextPosition(possibleNewDirs.get(rndDirectionIndex), pos);
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
                //TODO: this causes my SonarLint to react, as the method could return Null. However, i think i've implemented in a way to avoid that.
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
