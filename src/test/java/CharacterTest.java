
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CharacterTest {

    @Test
    @DisplayName("Testar att entitetens hälsa blir korrekt")
    public void testCharacterHealth() {
        Character character1 = new Character("one", 100, 10);
        assertEquals(100, character1.getHealth());

    }

    @Test
    @DisplayName("Testar att en entitets position blir korrekt")
    public void testCharacterPosition() {
        Character character1 = new Character("test", 0, 9, new Position(1, 2));
        assertEquals(new Position(1, 2), character1.getPosition());
        assertEquals(1, character1.getPosition().getX());
        assertEquals(2, character1.getPosition().getY());
    }

    @Test
    @DisplayName("assert throws exception if health<0")
    public void testCharacterWithNegativeHealth() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            new Character("one", -100, 10);
        });
    }

    @Test
    @DisplayName("assert throws exception if speed<0")
    public void testCharacterWithNegativeSpeed() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            new Character("one", 100, -10);
        });
    }


    @Test
    @DisplayName("Test to decrease health")
    public void testToDecreaseHealth() {
        Character character1 = new Character("one", 100, 10);
        int healthAfter = character1.decreaseHealth(20);
        assertEquals(80, character1.getHealth());
    }


}



