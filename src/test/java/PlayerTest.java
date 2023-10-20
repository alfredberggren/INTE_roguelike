import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class PlayerTest {
    static Player player;

    @BeforeEach
    void setUp() {
        player = new Player("Rudolf", 80, 20,1, new Position(0, 0), new TextIO());
    }

    @Test
    @DisplayName("Test to get player's amount of experience")
    public void testToGetAmountOfExperience() {
        assertEquals(0, player.getAmountOfExperience());
    }

    @Test
    @DisplayName("Test to set correct amount of experience points")
    public void testSetCorrectXP() {
        player.setAmountOfExperience(100);
        assertEquals(100, player.getAmountOfExperience());
    }

    @Test
    @DisplayName("Test to set negative amount of experience point value")
    public void whenSetNegativeXP_ThrowsException() {
        assertThrows(IllegalArgumentException.class, () -> {
            player.setHealth(-10);
        });
    }


    ///
    @Test
    @DisplayName("Test to increase amount of experience points with correct value")
    public void testToIncreaseXP() {
        player.increaseXP(10);
        player.increaseXP(10);
        assertEquals(20, player.getAmountOfExperience());
    }

    @Test
    @DisplayName("Test to decrease XP to negative value")
    public void testDecreaseXPToNegativeValue() {
        player.decreaseXP(10);
        assertEquals(0, player.getAmountOfExperience());
    }



    @Test
    @DisplayName("Test that player gets a reward after winning")
    public void testToGetRewardsAfterWinning() {
        Quest DEFAULT_QUEST = new Quest("Kill Fido", QuestType.KILL, "Test description", 50, 5);
        DEFAULT_QUEST.completeQuest();
        player.getRewardsAfterWinning(DEFAULT_QUEST);
        assertEquals(50, player.getAmountOfExperience());
    }

    @Test
    @DisplayName("Test that player does not get a reward if the quest is not completed")
    public void testDoNotGetRewardsIfQuestNotCompleted() {
        Quest DEFAULT_QUEST = new Quest("Kill Fido", QuestType.KILL, "Test description", 50, 5);
        player.getRewardsAfterWinning(DEFAULT_QUEST);
        assertEquals(0, player.getAmountOfExperience());
    }



    @Test
    @DisplayName("Test to decrease XP")
    public void testToDecreaseXP() {
        player.decreaseXP(10);
        assertEquals(0, player.getAmountOfExperience());
    }

    @Test
    @DisplayName("Test that character level up")
    public void testCharacterLevelUpWhenXPReaches100() {
        player.setLevel(0);
        player.increaseXP(100);
        player.levelUpOnTurn();
        assertEquals(1, player.getLevel());
        assertEquals(0, player.getAmountOfExperience());
        player.increaseXP(100);
        player.levelUpOnTurn();
        assertEquals(2, player.getLevel());
        assertEquals(0, player.getAmountOfExperience());
    }

    @Test
    @DisplayName("Test that player cannot be in a level higher than 10")
    public void testPlayerLevelDoNotExceedAcceptedLevel() {
        player.setLevel(11);
        assertEquals(10, player.getLevel());
    }

}
