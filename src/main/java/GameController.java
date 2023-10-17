public class GameController {
    public GameController() {
    }

    public static void main(String[] args) {
        MapController mapController = new MapController();
        Position p = new Position(0,0);
        Room r = new Room(p, new InteractableInventory(new NPC("Ragnar", 10, 100, new Position(0,0), new TextIO())));

        //Problem when adding room before map built, building overwrites manually inserted rooms. Gives idea to implement "Set up first room".
        //mapController.add(p, r);
        //MapBuilder mapBuilder = new MapBuilder(Difficulty.HARD, 10, new Player(100, 10, p, ), mapController);


       // mapBuilder.build();

        //Problem when adding room after map has been built. Does not set directions in room.
        //mapController.add(p, r);

        //System.out.println(mapController);
    }
}
