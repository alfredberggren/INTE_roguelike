 //inte klar än
public class Quest {
    private QuestType type;
    private String name;
    private String questDescription;
    private int rewardXP;
    private Equipment rewardEquipment;
    private boolean questFailed;


    public Quest(String name, QuestType type, String questDescription, int rewardXP, Equipment rewardEquipment) {
        this.name = name;
        this.questDescription = questDescription;
        this.rewardXP = rewardXP;
        this.rewardEquipment = rewardEquipment;
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public String getQuestDescription() {
        return questDescription;
    }

    public Equipment getRewardEquipment() {
        return rewardEquipment;
    }

    public int getRewardXP() {
        return rewardXP;
    }

    //skriva klart
    public QuestType  getQuestType(){
        QuestType type = null;
        return type;
    }

    public boolean isQuestFailed() {
        return questFailed;
    }
    public String toString(){
        return "Name: " + this.name + "\nType: " + this.getQuestType() + "\nDescription: " + this.questDescription
                + "\nReward XP" + this.rewardXP + "\nReward equipment " + this.rewardEquipment;
    }


    
}
