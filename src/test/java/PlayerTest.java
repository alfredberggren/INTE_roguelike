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
        DEFAULT_PLAYER = new Player( 80, 20, 10);
    }

    public class NameTest {
        @Test
        void nameInputIsNull() {
            assertThrows(NullPointerException.class, () -> {
                DEFAULT_PLAYER.setName(null);
            });
        }
        @Test
        void nameInputIsEmpty() {
            DEFAULT_PLAYER.setName("");
            assertEquals("error", DEFAULT_PLAYER.getName());
        }
        @Test
        void tooShortName() {
            DEFAULT_PLAYER.setName("A");
            assertEquals("error", DEFAULT_PLAYER.getName());
        }


    }

    @Test
    @DisplayName("Test to increase XP")
    public void testToIncreaseXP(){
        DEFAULT_PLAYER.increaseXP(10);
        assertEquals(20, DEFAULT_PLAYER.getExperiencePoint());
    }
    @Test
    @DisplayName("Test to decrease XP")
    public void testToDecreaseXP(){
        DEFAULT_PLAYER.decreaseXP(10);
        assertEquals(10, DEFAULT_PLAYER.getExperiencePoint());
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
