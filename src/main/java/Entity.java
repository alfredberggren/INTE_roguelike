package src.main.java;

public class Entity {

    private String name;
    private int health;
    private int speed;

    public Entity(String name, int health, int speed){
        this.name = name;
        this. health = health;
        this.speed = speed;
    }


    public String getName(){
        return name;
    }
    public int getHealth() {
        return health;
    }

    public int getSpeed() {
        return speed;
    }
}
