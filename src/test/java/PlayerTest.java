import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class PlayerTest {
    static Player DEFAULT_PLAYER;

    @BeforeEach
    void setUp() {
        DEFAULT_PLAYER = new Player(80, 20, 10);
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
        DEFAULT_PLAYER.setName("");
        assertEquals(null, DEFAULT_PLAYER.getName());
    }

    @Test
    public void testTooShortName() {
        DEFAULT_PLAYER.setName("A");
        assertEquals(null, DEFAULT_PLAYER.getName());
    }

    @Test
    public void testNameBeginsWithDigit() {
        DEFAULT_PLAYER.setName("1Ab");
        assertEquals(null, DEFAULT_PLAYER.getName());
    }

    @Test
    public void testTooLongName() {
        DEFAULT_PLAYER.setName("tooLongNameToBeAccepted");
        assertEquals(null, DEFAULT_PLAYER.getName());
    }

    @Test
    public void testNameContainsNotOnlyAlphanumericCharactersAndUnderscores() {
        DEFAULT_PLAYER.setName("Aabba&&");
        assertEquals(null, DEFAULT_PLAYER.getName());
    }
//name tests


    @Test
    @DisplayName("Test to increase XP")
    public void testToIncreaseXP() {
        DEFAULT_PLAYER.increaseXP(10);
        assertEquals(20, DEFAULT_PLAYER.getExperiencePoint());
    }

    @Test
    @DisplayName("Test to decrease XP")
    public void testToDecreaseXP() {
        DEFAULT_PLAYER.decreaseXP(10);
        assertEquals(0, DEFAULT_PLAYER.getExperiencePoint());
    }

    @Disabled //ens nödvändig?
    @DisplayName("Test that character level up")
    public void testCharacterLevelUpWhenExperienceReaches100() {
        assertEquals(1, DEFAULT_PLAYER.getLevel());
        assertEquals(0, DEFAULT_PLAYER.getExperiencePoint());
        DEFAULT_PLAYER.increaseXP(100);
        assertEquals(2, DEFAULT_PLAYER.getLevel());
        assertEquals(0, DEFAULT_PLAYER.getExperiencePoint());
    }

}
