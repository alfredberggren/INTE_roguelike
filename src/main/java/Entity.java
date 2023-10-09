public class Entity {

    private String name;
    private int health;
    private int speed;
    private Position pos;

    public Entity(String name, int health, int speed){
        this.name = name;
        this.health = health;
        this.speed = speed;
        this.pos = new Position(0, 0);
    }

    public Entity(String name, int health, int speed, Position pos){
        this.name = name;
        this. health = health;
        this.speed = speed;
        this.pos = pos;
    }


    public String getName(){
        return name;
    }

    public Position getPosition() {
        return pos;
    }
    public int getHealth() {
        return health;
    }

    public int getSpeed() {
        return speed;
    }
}
