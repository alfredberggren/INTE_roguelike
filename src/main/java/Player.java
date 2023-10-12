import java.util.Set;

public class Player extends Character{

    private int experiencePoint;
    private int level;
    public Player(int health, int speed, int experiencePoint) {
        super(health, speed);
        this.experiencePoint = experiencePoint;
    }

//    public void setDefaultValues(){  // ?defaultPosition
//
//    }

    public void checkLevelUp() {
        //kolla om spelaren ska gå upp i nivå baserat på erfarenhet
        int experiencePerLevelUp = 100;
        while(experiencePoint >= experiencePerLevelUp && level < 10) {
            experiencePoint -= experiencePerLevelUp;
            level++;
            System.out.println("Congratulations! You've reached level " + level + "!");
        }
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public int getLevel() {
        return level;
    }
    public int getExperiencePoint() {
        return experiencePoint;
    }

    public void setExperiencePoint(int experiencePoint) {
        this.experiencePoint = experiencePoint;
    }

    public void increaseXP(int add){
        int result = experiencePoint + add;
        setExperiencePoint(result);
    }

    public void decreaseXP(int decrease){
        int result = experiencePoint - decrease;
        if(result<0){
            setExperiencePoint(0);
        }else {
            setExperiencePoint(result);
        }
    }

}
