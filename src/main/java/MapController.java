import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

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

    public List<CardinalDirection> getAvailableDirections(Position p) {
        List<CardinalDirection> directions = new ArrayList<>();
        if (roomExists(new Position(p.getX() + 1, p.getY()))) {
            directions.add(CardinalDirection.EAST);
        }
        if (roomExists(new Position(p.getX(), p.getY() + 1))) {
            directions.add(CardinalDirection.NORTH);
        }
        if (roomExists(new Position(p.getX() - 1, p.getY()))) {
            directions.add(CardinalDirection.WEST);
        }
        if (roomExists(new Position(p.getX(), p.getY() - 1))) {
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
}
