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
        Position p = new Position(0,0);
        Room r = new Room(p, new InteractableInventory(new NPC("Ragnar", 10, 100)));

        //Problem when adding room before map built, building overwrites manually inserted rooms. Gives idea to implement "Set up first room".
        mapController.add(p, r);
        MapBuilder mapBuilder = new MapBuilder(Difficulty.HARD, 10, new Player(100, 10, 10), mapController);


        mapBuilder.build();

        //Problem when adding room after map has been built. Does not set directions in room.
        mapController.add(p, r);

        System.out.println(mapController);
    }
}
