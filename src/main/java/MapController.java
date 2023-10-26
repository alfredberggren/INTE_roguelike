import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * TODO: Singleton?
 * TODO: Mindre hårdkodade grunkor
 * TODO: Items som genereras på mappen ska skickas in som en map med interactables som har en key för sannolikhetsvärdet.
 * TODO: De interactables som väljs ut ska baseras på gausskurvan.
 * TODO: Skulle också kunna implementera en "Density" i MapBuilder, d.v.s. bestämma om kartan ska vara långa korridorer eller kompakta rum, svårt och kanske onödigt
 */

public class MapController {
    private HashMap<Position, Room> gameMap;

    public MapController() {
        gameMap = new HashMap<>();
    }

    public void add(Position p, Room r){
        gameMap.put(p, r);
    }

    public boolean roomExists(Position position) {
        return gameMap.containsKey(position);
    }

    public List<CardinalDirection> getAvailableDirections(Position position) {
        List<CardinalDirection> directions = new ArrayList<>();
        if (roomExists(new Position(position.getX() + 1, position.getY()))) {
            directions.add(CardinalDirection.EAST);
        }
        if (roomExists(new Position(position.getX(), position.getY() + 1))) {
            directions.add(CardinalDirection.NORTH);
        }
        if (roomExists(new Position(position.getX() - 1, position.getY()))) {
            directions.add(CardinalDirection.WEST);
        }
        if (roomExists(new Position(position.getX(), position.getY() - 1))) {
            directions.add(CardinalDirection.SOUTH);
        }
        return directions;
    }

    public List<CardinalDirection> getUnavailableDirections(Position position) {
        List<CardinalDirection> allDirections = new ArrayList<>(){{
            add(CardinalDirection.SOUTH);
            add(CardinalDirection.NORTH);
            add(CardinalDirection.EAST);
            add(CardinalDirection.WEST);
        }};
        List<CardinalDirection> directions = getAvailableDirections(position);
        allDirections.removeAll(directions);
        return allDirections;
    }

    public void setAvailableDirectionsInRooms(){
        for (Map.Entry<Position, Room> e: gameMap.entrySet()){
            e.getValue().setPossibleRoutes(getAvailableDirections(e.getKey()));
        }
    }

    public Room getRoom(Position pos){
        return gameMap.get(pos);
    }

    public boolean containsInteractable(Interactable interactable) {
        for (Room r : gameMap.values()) {
            if (r.getInteractables().contains(interactable)) {
                return true;
            }
        }
        return false;
    }

    //TODO: Slight chance of this method causing trouble. If any other object shares the same position-object, their position will be changed as well.
    public void moveCharacter(CardinalDirection direction, Character character) {
        switch(direction){
            case NORTH -> {
                gameMap.get(character.getPosition()).getInteractables().remove(character);
                character.getPosition().translate(0, 1);
                gameMap.get(character.getPosition()).getInteractables().add(character);
            }
            case EAST -> {
                gameMap.get(character.getPosition()).getInteractables().remove(character);
                character.getPosition().translate(1, 0);
                gameMap.get(character.getPosition()).getInteractables().add(character);
            }
            case SOUTH -> {
                gameMap.get(character.getPosition()).getInteractables().remove(character);
                character.getPosition().translate(0, -1);
                gameMap.get(character.getPosition()).getInteractables().add(character);
            }
            case WEST -> {
                gameMap.get(character.getPosition()).getInteractables().remove(character);
                character.getPosition().translate(-1, 0);
                gameMap.get(character.getPosition()).getInteractables().add(character);
            }
        }
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Map: \n");
        int counter = 1;
        for (Position p : gameMap.keySet()) {
            sb.append(counter);
            sb.append("- \t - ").append(gameMap.get(p).toString()).append(" }\n");
            counter++;
        }
        return sb.toString();
    }
}
