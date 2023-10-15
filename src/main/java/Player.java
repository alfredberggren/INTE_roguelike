import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Player extends Character{

    private String name;
    private int experiencePoint;
    private static final String NAME_PATTERN = "^[A-Za-z]\\w{1,11}$";
    public Player(int health, int speed, Position pos) {
        super(health, speed, pos);
        experiencePoint=0;
    }

    public String getName(){return name;}

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
    public void setName(String name) {
        // user input
        //the username consists of 2 to 10 characters. If less - invalid username
        //the username can only contain alphanumeric characters and underscores(_)
        //uppercase, lowercase and digits (0-9)
        //the first character must be an alphabetic character
        if (name == null){
            throw new NullPointerException("Error: name can't be null");
        }
        if (name.isEmpty()){
            System.out.println("Error: please, choose a name");
        }
        if(!matchesPattern(NAME_PATTERN, name)){
            System.out.println("Error: this name is not correct");
        }
        else
            this.name = name;
    }

    boolean matchesPattern(String pattern, String name){
        Pattern p = Pattern.compile(pattern);
        Matcher m = p.matcher(name);
        return m.find();
    }

}
