import java.util.*;

public class MapController {
    private TreeMap<Position, Room> gameMap;
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

    public String toLog() {
        StringBuilder sb = new StringBuilder();
        for (Map.Entry<Position, Room> me : gameMap.entrySet()) {
            sb.append(me.getKey().getX()).append(";").append(me.getKey().getY()).append(";");
            sb.append(me.getValue().toLog());
            sb.append("\n");
        }
        return sb.toString();
    }

    public List<Room> getAdjacentRooms(Room r) {
        List<CardinalDirection> dirs = r.getPossibleRoutes();
        List<Room> adjacentRooms = new ArrayList<>();
        for (CardinalDirection c : dirs) {
            if (c == CardinalDirection.NORTH)
                adjacentRooms.add(gameMap.get(new Position(r.getPosition().getX(), r.getPosition().getY() + 1)));
            if (c == CardinalDirection.SOUTH)
                adjacentRooms.add(gameMap.get(new Position(r.getPosition().getX(), r.getPosition().getY() - 1)));
            if (c == CardinalDirection.WEST)
                adjacentRooms.add(gameMap.get(new Position(r.getPosition().getX() - 1, r.getPosition().getY())));
            if (c == CardinalDirection.EAST)
                adjacentRooms.add(gameMap.get(new Position(r.getPosition().getX() + 1, r.getPosition().getY())));
        }
        return adjacentRooms;
    }
}
