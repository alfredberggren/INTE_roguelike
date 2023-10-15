import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class QuestTest {
    Quest DEFAULT_QUEST = new Quest("Kill Fido", QuestType.KILL, "Test description", 50, 5);

    @Test
    @DisplayName("Test that a quest has correct name")
    public void testGetName() {
        assertEquals("Kill Fido", DEFAULT_QUEST.getName());
    }

    @Test
    @DisplayName("Test that a quest has correct quest type")
    public void testGetQuestType() {
        assertEquals(QuestType.KILL, DEFAULT_QUEST.getQuestType());
    }

    @Test
    @DisplayName("Test that a quest has correct quest description")
    public void testGetQuestDescription() {
        assertEquals("Test description", DEFAULT_QUEST.getQuestDescription());
    }

    @Test
    @DisplayName("Test that a quest has correct reward XP")
    public void testGetRewardXP() {
        assertEquals(50, DEFAULT_QUEST.getRewardXP());
    }

    @Test
    @DisplayName("Test that a quest has correct difficulty")
    public void testGetDifficulty() {
        assertEquals(5, DEFAULT_QUEST.getDifficulty());
    }

    @Test
    @DisplayName("Test to create a quest with negative rewardXP")
    public void testConstructorWithNegativeRewardXP() {
        assertThrows(IllegalArgumentException.class, () -> {
            new Quest("Kill Fido", QuestType.KILL, "Test description", -10, 5);
        });
    }

    @Test
    @DisplayName("Test to create a quest with negative difficulty")
    public void testConstructorWithNegativeDifficulty() {
        assertThrows(IllegalArgumentException.class, () -> {
            new Quest("Kill Fido", QuestType.KILL, "Test description", 10, -1);
        });
    }

    @Test
    @DisplayName("Test to quest type is a valid from the QuestType enumeration")
    public void testQuestTypeValidity() {
        assertTrue(DEFAULT_QUEST.getQuestType() == QuestType.EXPLORE ||
                DEFAULT_QUEST.getQuestType() == QuestType.DELIVER ||
                DEFAULT_QUEST.getQuestType() == QuestType.KILL ||
                DEFAULT_QUEST.getQuestType() == QuestType.PROTECT ||
                DEFAULT_QUEST.getQuestType() == QuestType.RETRIEVE);
    }

    @Test
    @DisplayName("Test that quest is completed")
    public void testCompleteQuest() {
        DEFAULT_QUEST.completeQuest();
        assertTrue(DEFAULT_QUEST.isCompleted());
    }

    @Test
    @DisplayName("Test if difficulty correspond to character's level")
    public void testThatDifficultyCorrespondToLevel() {
        Character character = new Character(100, 10, 100);
        character.setLevel(5);
        assertTrue(DEFAULT_QUEST.correspondToLevel(character));
    }

    @Test
    @DisplayName("Test two unequal quests")
    public void testTwoUnequalQuests() {
        Quest test_quest = new Quest("Explore the Magic Wood", QuestType.EXPLORE, "Test description", 100, 7);
        assertFalse(DEFAULT_QUEST.equals(test_quest));
    }

    @Test
    @DisplayName("Test that two unequal quests have unequal hashCode")
    public void testTwoUnequalQuestsHashCode() {
        Quest test_quest = new Quest("Explore the Magic Wood", QuestType.EXPLORE, "Test description", 100, 7);
        assertNotEquals(DEFAULT_QUEST.hashCode(), test_quest.hashCode());
    }


}

