import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
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
