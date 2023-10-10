import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class PlayerTest {
    static final Player DEFAULT_PLAYER = new Player("Rudolf", 80, 20, 10);

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

}
