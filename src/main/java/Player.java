public class Player extends Character{

    private static final int AMOUNT_OF_XP_TO_LEVEL_UP = 1000;
    private static final int MAX_LEVEL = 10;
    private int amountOfExperience;


    public Player(String name, int health, int speed, int level, Position pos, IO io) {
        super(name, health, speed, level, pos, io);
        amountOfExperience = 0;
        this.setLevel(level);
    }

    public int getAmountOfExperience() {
        return amountOfExperience;
    }

    protected void setAmountOfExperience(int amountOfExperience) {
        if(amountOfExperience < 0)
            throw new IllegalArgumentException("Experience points cannot be negative!");
        this.amountOfExperience = amountOfExperience;
    }

    /**Increases the character's experience points*/
    public void increaseXP(int add){
        if (add < 0) {
            throw new IllegalArgumentException("XP increase value cannot be negative");
        }
        int result = amountOfExperience + add;
        if (result < 0){
            result = AMOUNT_OF_XP_TO_LEVEL_UP;
        }
        setAmountOfExperience(result);
        levelUpOnTurn();
    }

    /**Decreases the character's experience points*/
    public void decreaseXP(int decrease){
        int result = amountOfExperience - decrease;
        if(result < 0){
            setAmountOfExperience(0);
        }else {
            setAmountOfExperience(result);
        }
    }

    private void increaseLevel() {
        if(getLevel() < MAX_LEVEL) {
            setLevel(getLevel() + 1);
        }
    }

    /**Checks if the character has gained enough experience points to level up. If the character's experience points are greater than or equal to the experience required for the next level and the character's level is less than 10, the character levels up*/
    public void levelUpOnTurn() {
        while(getAmountOfExperience() >= AMOUNT_OF_XP_TO_LEVEL_UP) {
            increaseLevel();
            setAmountOfExperience(getAmountOfExperience() - AMOUNT_OF_XP_TO_LEVEL_UP);
        }
        if (getAmountOfExperience() == 0 && getLevel() > 1) {
            setAmountOfExperience(0);
        }
    }

    public void getXPAfterWinning(Quest quest){
        if(quest.isCompleted()){
            setAmountOfExperience(this.amountOfExperience + quest.getRewardXP());
        }
    }

}
