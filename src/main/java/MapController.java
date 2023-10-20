import java.util.*;

/**
 * TODO: Mindre hårdkodade grunkor
 * TODO: Items som genereras på mappen ska skickas in som en map med interactables som har en key för sannolikhetsvärdet.
 * TODO: De interactables som väljs ut ska baseras på gausskurvan.
 * TODO: Skulle också kunna implementera en "Density" i MapBuilder, d.v.s. bestämma om kartan ska vara långa korridorer eller kompakta rum, svårt och kanske onödigt
 */

public class MapController {
    private TreeMap<Position, Room> gameMap;
    //private TreeSet<Position> allPositions;
    private Position positionMarker;

    public MapController() {
        gameMap = new TreeMap<>();
        positionMarker = new Position(0, 0);
    }

    public void add(Position p, Room r) {
        gameMap.put(p, r);
    }

    public boolean roomExists(Position position) {
        return gameMap.containsKey(position);
    }

    public List<CardinalDirection> getAvailableDirections(Position position) {
        List<CardinalDirection> directions = new ArrayList<>();
        positionMarker.setX(position.getX() + 1);
        positionMarker.setY(position.getY());
        if (roomExists(positionMarker)) {
            directions.add(CardinalDirection.EAST);
        }
        positionMarker.setX(position.getX());
        positionMarker.setY(position.getY() + 1);
        if (roomExists(positionMarker)) {
            directions.add(CardinalDirection.NORTH);
        }
        positionMarker.setX(position.getX() - 1);
        positionMarker.setY(position.getY());
        if (roomExists(positionMarker)) {
            directions.add(CardinalDirection.WEST);
        }
        positionMarker.setX(position.getX());
        positionMarker.setY(position.getY() - 1);
        if (roomExists(positionMarker)) {
            directions.add(CardinalDirection.SOUTH);
        }
        return directions;
    }

    public TreeMap<Position, Room> getGameMap() {
        return gameMap;
    }

    public List<CardinalDirection> getUnavailableDirections(Position position) {
        List<CardinalDirection> allDirections = new ArrayList<>() {{
            add(CardinalDirection.SOUTH);
            add(CardinalDirection.NORTH);
            add(CardinalDirection.EAST);
            add(CardinalDirection.WEST);
        }};
        List<CardinalDirection> directions = getAvailableDirections(position);
        allDirections.removeAll(directions);
        return allDirections;
    }

    public void setAvailableDirectionsInRooms() {
        for (Map.Entry<Position, Room> e : gameMap.entrySet()) {
            e.getValue().setPossibleRoutes(getAvailableDirections(e.getKey()));
        }
    }

    public Room getRoom(Position pos) {
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

    public Collection<Room> getAdjacentRooms(Room r) {
        List<CardinalDirection> dirs = r.getPossibleRoutes();
        List<Room> adjacentRooms = new ArrayList<>();
        for (CardinalDirection c : dirs) {
            if (c == CardinalDirection.NORTH) {
                adjacentRooms.add(getRoom(new Position(r.getPosition().getX(), r.getPosition().getY() + 1)));
            } if (c == CardinalDirection.SOUTH) {
                adjacentRooms.add(getRoom(new Position(r.getPosition().getX(), r.getPosition().getY() - 1)));
            } if (c == CardinalDirection.WEST) {
                adjacentRooms.add(getRoom(new Position(r.getPosition().getX() - 1, r.getPosition().getY())));
            } if (c == CardinalDirection.EAST) {
                adjacentRooms.add(getRoom(new Position(r.getPosition().getX() + 1, r.getPosition().getY())));
            }
        }
        return adjacentRooms;
    }
}
