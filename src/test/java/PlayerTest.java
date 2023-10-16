import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class PlayerTest {
    static Player DEFAULT_PLAYER;

    @BeforeEach
    void setUp() {
        DEFAULT_PLAYER = new Player(80, 20, new Position(0, 0));
    }


    //name tests
    @Test
    public void testCorrectName() {
        DEFAULT_PLAYER.setName("Rudolf");
        assertEquals("Rudolf", DEFAULT_PLAYER.getName());
    }

    @Test
    public void testNameInputIsNull() {
        assertThrows(NullPointerException.class, () -> {
            DEFAULT_PLAYER.setName(null);
        });
    }

    @Test
    public void testNameInputIsEmpty() {
        assertThrows(IllegalArgumentException.class, () ->{
            DEFAULT_PLAYER.setName("");
        });
    }

    @Test
    public void testTooShortName() {
        assertThrows(IllegalArgumentException.class, () ->{
            DEFAULT_PLAYER.setName("A");
        });
    }

    @Test
    public void testNameBeginsWithDigit() {
        assertThrows(IllegalArgumentException.class, () ->{
            DEFAULT_PLAYER.setName("1Ab");
        });
    }

    @Test
    public void testTooLongName() {
        assertThrows(IllegalArgumentException.class, () ->{
            DEFAULT_PLAYER.setName("tooLongNameToBeAccepted");
        });
    }

    @Test
    public void testNameContainsNotOnlyAlphanumericCharactersAndUnderscores() {
        assertThrows(IllegalArgumentException.class, () ->{
            DEFAULT_PLAYER.setName("Aabba&&");
        });
    }
//name tests

    @Test
    @DisplayName("Test that new player has XP=0")
    public void testNewCharactersXP() {
        assertEquals(0, DEFAULT_PLAYER.getExperiencePoint());
    }

    @Test
    @DisplayName("Test to set correct experience points")
    public void testSetCorrectXP() {
        DEFAULT_PLAYER.setExperiencePoint(100);
        assertEquals(100, DEFAULT_PLAYER.getExperiencePoint());
    }

    @Test
    @DisplayName("Test throws exception to set negative experience points")
    public void testSetNegativeXP() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            DEFAULT_PLAYER.setExperiencePoint(-10);
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


}
