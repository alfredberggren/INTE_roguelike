import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Player extends Character{

    private MagicAbility magicAbility;
    private String name;
    private Position pos;
    private static final String NAME_PATTERN = "^[A-Za-z]\\w{1,11}$";
    public Player(int health, int speed, Position pos, int experiencePoint) {
        super(health, speed, pos);
    }

    public String getName(){return name;}
    public Position getPos() {return pos;}

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
