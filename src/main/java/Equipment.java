public class Equipment {

    private String name;
    private int effect;
    private int damage;

    Equipment(String name, int effect, int damage) {
        this.name = name;
        this.effect = effect;
        this.damage = damage;
    }

    public String getName(){
        return name;
    }

}
