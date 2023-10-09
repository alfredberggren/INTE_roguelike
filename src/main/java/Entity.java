package src.main.java;
import src.main.java.Position;

public class Entity {

    private String name;
    private int health;
    private Position position;

    public Entity(String name, int health, Position pos){
        this.name = name;
        this. health = health;
        position = pos;
    }


    public int getHealth() {
        return health;
    }

    public Position getPosition() {
        return position;
    }
}
