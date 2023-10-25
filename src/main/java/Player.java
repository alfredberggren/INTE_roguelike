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

    /**This method checks that amountOfExperience is not negative and if so then throw IllegalArgumentException*/
    public void setAmountOfExperience(int amountOfExperience) {
        if(amountOfExperience < 0)
            throw new IllegalArgumentException("Experience points cannot be negative!");
        this.amountOfExperience = amountOfExperience;
    }

    /**Increases the character's experience points and checks that add is not negative.
     * The method also calls on levelUpOnTurn() to level the Player up when experience points is enough*/
    public void increaseXP(int add){
        if (add < 0) {
            throw new IllegalArgumentException("XP increase value cannot be negative");
        }
        int result = amountOfExperience + add;
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

    /**Increases the level if the Player is below the max level*/
    private void increaseLevel() {
        if(getLevel() < MAX_LEVEL) {
            setLevel(getLevel() + 1);
        }
    }

    /**Checks if the Player has gained enough experience points to level up.
     * If the Player levels up, then the experience points should be 0 again*/
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
