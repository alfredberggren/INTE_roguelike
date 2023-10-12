import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Player extends Character{

    private int experiencePoint;
    private int level;
    private String name;
    private static final String NAME_PATTERN = "^[A-Za-z]\\w{1,11}$";
    public Player(int health, int speed, int experiencePoint) {
        super(health, speed);

        this.experiencePoint = experiencePoint;
    }

//    public void setDefaultValues(){  // ?defaultPosition
//
//    }

    public String getName(){return name;}

    public void setName(String name) {
        // user input
        //the user name consists of 2 to 10 characters. If less - invalid username
        //the usrname can only contain alphanumeric characters and underscores(_)
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
