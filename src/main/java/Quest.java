 //inte klar än
public class Quest {

    private QuestType type;
    private String name;
    private String questDescription;
    private int rewardXP;
    private boolean isCompleted;


    public Quest(String name, QuestType type, String questDescription, int rewardXP) {
        this.name = name;
        this.questDescription = questDescription;
        this.rewardXP = rewardXP;
        this.type = type;
        this.isCompleted=false;
    }

    public String getName() {
        return name;
    }

    public String getQuestDescription() {
        return questDescription;
    }


    public int getRewardXP() {
        return rewardXP;
    }

    public QuestType  getQuestType(){
        return type;
    }

    public boolean completeQuest() {
        return this.isCompleted = true;
    }

     public boolean isCompleted() {
         return isCompleted;
     }

     public String toString(){
        return "Name: " + this.name + "\nType: " + this.getQuestType() + "\nDescription: " + this.questDescription
                + "\nReward XP: " + this.rewardXP;
    }


    
}
