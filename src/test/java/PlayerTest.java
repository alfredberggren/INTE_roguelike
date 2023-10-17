import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class PlayerTest {
    static Player DEFAULT_PLAYER;

    @BeforeEach
    void setUp() {
        DEFAULT_PLAYER = new Player("Rudolf",80, 20, new Position(0, 0), new TextIO());
    }

    @Test
    @DisplayName("Test that new player has XP=0")
    public void testNewCharactersXP() {
        assertEquals(0, DEFAULT_PLAYER.getExperiencePoint());
    }

    @Test
    @DisplayName("Test to set correct experience points")
    public void testSetCorrectXP() {
        DEFAULT_PLAYER.increaseXP(100);
        assertEquals(100, DEFAULT_PLAYER.getExperiencePoint());
    }

    @Test
    @DisplayName("Test to decrease XP to negative value")
    public void testDecreaseXPToNegativeValue() {
       DEFAULT_PLAYER.decreaseXP(10);
       assertEquals(0, DEFAULT_PLAYER.getExperiencePoint());
    }

    @Test
    @DisplayName("Test to set negative experience point value")
    public void testSetNegativeXP() {
        assertThrows(IllegalArgumentException.class, () -> {
            DEFAULT_PLAYER.setHealth(-10);
        });
    }



    @Test
    @DisplayName("Test that player gets a reward after winning")
    public void testToGetRewardsAfterWinning() {
        Quest DEFAULT_QUEST = new Quest("Kill Fido", QuestType.KILL, "Test description", 50, 5);
        DEFAULT_QUEST.completeQuest();
        DEFAULT_PLAYER.getRewardsAfterWinning(DEFAULT_QUEST);
        assertEquals(50, DEFAULT_PLAYER.getExperiencePoint());
    }

    @Test
    @DisplayName("Test that player does not get a reward if the quest is not completed")
    public void testDoNotGetRewardsIfQuestNotCompleted() {
        Quest DEFAULT_QUEST = new Quest("Kill Fido", QuestType.KILL, "Test description", 50, 5);
        DEFAULT_PLAYER.getRewardsAfterWinning(DEFAULT_QUEST);
        assertEquals(0, DEFAULT_PLAYER.getExperiencePoint());
    }

    @Test
    @DisplayName("Test to increase XP")
    public void testToIncreaseXP() {
        DEFAULT_PLAYER.increaseXP(10);
        DEFAULT_PLAYER.increaseXP(10);
        assertEquals(20, DEFAULT_PLAYER.getExperiencePoint());
    }

    @Test
    @DisplayName("Test to decrease XP")
    public void testToDecreaseXP() {
        DEFAULT_PLAYER.decreaseXP(10);
        assertEquals(0, DEFAULT_PLAYER.getExperiencePoint());
    }

    @Test
    @DisplayName("Test that character level up")
    public void testCharacterLevelUpWhenXPReaches100() {
        DEFAULT_PLAYER.setLevel(0);
        DEFAULT_PLAYER.increaseXP(100);
        DEFAULT_PLAYER.levelUp();
        assertEquals(1, DEFAULT_PLAYER.getLevel());
        assertEquals(0, DEFAULT_PLAYER.getExperiencePoint());
        DEFAULT_PLAYER.increaseXP(100);
        DEFAULT_PLAYER.levelUp();
        assertEquals(2, DEFAULT_PLAYER.getLevel());
        assertEquals(0, DEFAULT_PLAYER.getExperiencePoint());
    }

}
