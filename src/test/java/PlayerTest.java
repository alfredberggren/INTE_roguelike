import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class PlayerTest {

    @Test
    @DisplayName("Test to increase XP")
    public void testToIncreaseXP(){
        Player player1 = new Player("Player1", 100, 30, 0);
        player1.increaseXP(10);
        assertEquals(10, player1.getExperiencePoint());
    }
    @Test
    @DisplayName("Test to decrease XP")
    public void testToDecreaseXP(){
        Player player1 = new Player("Player1", 100, 30, 10);
        player1.decreaseXP(10);
        assertEquals(0, player1.getExperiencePoint());
    }

}
