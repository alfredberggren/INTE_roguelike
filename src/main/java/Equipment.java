package src.main.java;

public class Equipment {

    private String name;
    private int effect;
    private int damage;

    public Equipment(String name, int effect, int damage) {
        this.name = name;
        this.effect = effect;
        this.damage = damage;
    }

    public String getName(){
        return name;
    }

}
