import java.util.HashMap;

public class MapController {
    private HashMap<Position, Room> gameMap;

    public MapController() {
        gameMap = new HashMap<>();
    }

    public void add(Position p, Room r){
        gameMap.put(p, r);
    }
}
