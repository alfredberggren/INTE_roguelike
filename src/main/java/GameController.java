public class GameController {
//    private MapController mapController;
//    private Difficulty difficulty;
//    private int amountOfRooms;
//    private Player player;
//    private MapBuilder mapBuilder;
    public GameController() {
        //difficulty = IO.promptDifficulty();
        //amountOfRooms = IO.promptAmountOfRooms();
        //character = IO.promptCreatePlayer();
        //mapController = new MapController();
        //mapBuilder = new MapBuilder(difficulty, amountOfRooms, player, mapController);
    }

    public static void main(String[] args) {
        MapController mapController = new MapController();
        MapBuilder mapBuilder = new MapBuilder(Difficulty.HARD, 10000, new Player(100, 10, 10), mapController);
        mapBuilder.build();
        System.out.println(mapController);
    }
}
