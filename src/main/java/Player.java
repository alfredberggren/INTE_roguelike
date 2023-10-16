public class Player extends Character{

    
    private int experiencePoint;


    public Player(String name, int health, int speed, Position pos, IO io) {
        super(name, health, speed, pos, new TextUI());
        experiencePoint=0;
    }

    public int getExperiencePoint() {
        return experiencePoint;
    }

    private void setExperiencePoint(int experiencePoint) {
        if(experiencePoint<0)
            throw new IllegalArgumentException("Experience points cannot be negative!");
        this.experiencePoint = experiencePoint;
    }

    /**Increases the character's experience points*/
    public void increaseXP(int add){
        int result = experiencePoint + add;
        setExperiencePoint(result);
    }

    /**Decreases the character's experience points*/
    public void decreaseXP(int decrease){
        int result = experiencePoint - decrease;
        if(result<0){
            setExperiencePoint(0);
        }else {
            setExperiencePoint(result);
        }
    }

    /**Checks if the character has gained enough experience points to level up. If the character's experience points are greater than or equal to the experience required for the next level and the character's level is less than 10, the character levels up*/
    public void levelUp() {
        int experiencePerLevelUp = 100;
        while(experiencePoint >= experiencePerLevelUp && level < 10) {
            experiencePoint -= experiencePerLevelUp;
            level++;
        }
    }

    public void getRewardsAfterWinning(Quest quest){
        if(quest.isCompleted()){
            setExperiencePoint(experiencePoint += quest.getRewardXP());
        }
    }

}
