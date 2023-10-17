import java.util.Objects;

public class Quest {
    private QuestType questType;
    private String name;
    private String questDescription;
    private int rewardXP;
    private int difficulty;
    private boolean isCompleted;
    //private Character character;

    public Quest(String name, QuestType questType, String questDescription, int rewardXP, int difficulty) {
        this.name = name;
        this.questDescription = questDescription;
        if (rewardXP <= 0)
            throw new IllegalArgumentException("Experience points cannot be zero or be negative!");
        this.rewardXP = rewardXP;
        this.questType = questType;
        if (difficulty <= 0)
            throw new IllegalArgumentException("Difficulty cannot be zero or negative!");
        this.difficulty = difficulty;
        this.isCompleted = false;
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

    public QuestType getQuestType() {
        return questType;
    }

    public int getDifficulty() {
        return difficulty;
    }

    public void completeQuest() {
        this.isCompleted = true;
    }

    public boolean isCompleted() {
        return isCompleted;
    }

    /**
     * difficulty should be equal character's level
     */
    public boolean correspondToLevel(Character c) {
        if(c != null) {
            return this.difficulty == c.getLevel();
        }
            return false;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Quest)) return false;
        Quest quest = (Quest) o;
        return rewardXP == quest.rewardXP &&
                isCompleted == quest.isCompleted &&
                Objects.equals(name, quest.name) &&
                questType == quest.questType &&
                Objects.equals(questDescription, quest.questDescription);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, questType, questDescription, rewardXP, isCompleted);
    }

    public String toString() {
        return "Name: " + this.name + "\nType: " + this.questType + "\nDescription: " + this.questDescription
                + "\nReward XP: " + this.rewardXP + "\nDifficulty: " + this.difficulty;
    }

}
