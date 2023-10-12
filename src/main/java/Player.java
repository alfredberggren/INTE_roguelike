import java.util.Set;

public class Player extends Character implements Interactable{

    private int experiencePoint;
    public Player(String name, int health, int speed, int experiencePoint) {
        super(name, health, speed);
        this.experiencePoint = experiencePoint;
    }

//    public void setDefaultValues(){  // ?defaultPosition
//
//    }


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

    @Override
    public Set<InteractableAction> getPossibleActions() {
        return null;
    }
}
