package src.main.java;

public class Entity {

    private String name;
    private int health;
    private int speed;
    //private Position position;

    public Entity(String name, int health, int speed){
        this.name = name;
        this. health = health;
        this.speed = speed;
    }


    public int getHealth() {
        return health;
    }
}
