import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Player extends Character{
    private static final STANDARD_PLAYER_NAME = "Player";

    private int experiencePoint;
    private static final String NAME_PATTERN = "^[A-Za-z]\\w{1,11}$";
    public Player(int health, int speed, Position pos) {
        super(STANDARD_PLAYER_NAME,health, speed, pos);
        experiencePoint=0;
    }

    @Override
    public int getExperiencePoint() {
        return experiencePoint;
    }



    @Override
    public void setExperiencePoint(int experiencePoint) {
        if(experiencePoint<0)
            throw new IllegalArgumentException("Experience points cannot be negative!");
        this.experiencePoint = experiencePoint;
    }
    public void getRewardsAfterWinning(Quest quest){
        if(quest.isCompleted()){
            setExperiencePoint(experiencePoint += quest.getRewardXP());
        }
    }

}
