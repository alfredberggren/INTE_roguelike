public class Player extends Entity{

    private int experiencePoint;
    public Player(String name, int health, int speed, int experiencePoint) {
        super(name, health, speed);
        this.experiencePoint = experiencePoint;
    }

//    public void setDefaultValues(){  // ?defaultPosition
//
//    }

}
