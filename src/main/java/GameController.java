public class GameController {
    private MapController mapController;
    private Difficulty difficulty;
    private int amountOfRooms;
    private Player player;
    private MapBuilder mapBuilder;
    public GameController() {
        //difficulty = IO.promptDifficulty();
        //amountOfRooms = IO.promptAmountOfRooms();
        //character = IO.promptCreatePlayer();
        mapController = new MapController();
        mapBuilder = new MapBuilder(difficulty, amountOfRooms, player, mapController);
    }
}
