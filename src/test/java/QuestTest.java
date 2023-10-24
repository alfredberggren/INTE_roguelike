import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class QuestTest {
    Quest quest = new Quest("Kill Fido", QuestType.KILL, "Test description", 50, 5);

    @Test
    @DisplayName("Test that a quest has correct name")
    public void testGetName() {
        assertEquals("Kill Fido", quest.getName());
    }

    @Test
    @DisplayName("Test that a quest has correct quest type")
    public void testGetQuestType() {
        assertEquals(QuestType.KILL, quest.getQuestType());
    }

    @Test
    @DisplayName("Test that a quest has correct quest description")
    public void testGetQuestDescription() {
        assertEquals("Test description", quest.getQuestDescription());
    }

    @Test
    @DisplayName("Test that a quest has correct reward XP")
    public void testGetRewardXP() {
        assertEquals(50, quest.getRewardXP());
    }

    @Test
    @DisplayName("Test that a quest has correct difficulty")
    public void testGetDifficulty() {
        assertEquals(5, quest.getDifficulty());
    }

    @Test
    @DisplayName("Test that quest is not completed in constructor")
    public void testThatQuestIsNotCompletedInConstructor() {
        assertFalse(quest.isCompleted());
    }

    @Test
    @DisplayName("Test to create a quest with negative rewardXP")
    public void testConstructorWithNegativeRewardXP_shouldThrowException() {
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
        assertTrue(quest.getQuestType() == QuestType.EXPLORE ||
                quest.getQuestType() == QuestType.DELIVER ||
                quest.getQuestType() == QuestType.KILL ||
                quest.getQuestType() == QuestType.PROTECT ||
                quest.getQuestType() == QuestType.RETRIEVE);
    }

    @Test
    @DisplayName("Test that quest is completed")
    public void testCompleteQuest() {
        quest.completeQuest();
        assertTrue(quest.isCompleted());
    }

    @Test
    @DisplayName("Test if difficulty correspond to character's level")
    public void testThatDifficultyCorrespondToLevel() {
        Character character = new Character("Rudolf", 10, 10,1, new TextIO());
        character.setLevel(5);
        assertTrue(quest.correspondToLevel(character));
    }

    @Test
    @DisplayName("Test to check if difficulty correspond character's level when Character is null")
    public void testThatDifficultyCorrespondToLevelWhenCharacterIsNull() {
        assertFalse(quest.correspondToLevel(null));
    }

    @Test
    @DisplayName("Test two unequal quests")
    public void testTwoUnequalQuests() {
        Quest test_quest = new Quest("Explore the Magic Wood", QuestType.EXPLORE, "Test description", 100, 7);
        assertFalse(quest.equals(test_quest));
    }

    @Test
    @DisplayName("Test two equal quests")
    public void testTwoEqualTests() {
        assertTrue(quest.equals(quest));
    }

    @Test
    @DisplayName("Test equals() when parameter i not instance of Quest class")
    public void testEqualityQuestWithNotQuest() {
        assertFalse(quest.equals(new Equipment("Sword", EquipmentSlot.LEFT_HAND, Equipment.Effect.DAMAGE, 50, new PhysicalAbility("Slash", 10, 1,"Physical Attack"))));
    }

    @Test
    @DisplayName("Test that two unequal quests have unequal hashCode")
    public void testTwoUnequalQuestsHashCode() {
        Quest test_quest = new Quest("Explore the Magic Wood", QuestType.EXPLORE, "Test description", 100, 7);
        assertNotEquals(quest.hashCode(), test_quest.hashCode());
    }

}

