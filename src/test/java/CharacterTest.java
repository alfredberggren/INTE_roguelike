
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CharacterTest {

    @Test
    @DisplayName("Testar att entitetens hälsa blir korrekt")
    public void testEntityHealth(){
      Character character1 = new Character("one", 100, 10);
      assertEquals(100, character1.getHealth());

    }

    @Test
    @DisplayName("Testar att en entitets position blir korrekt")
    public void testEntityPosition(){
        Character character1 = new Character("test", 0, 9, new Position(1, 2));
        assertEquals(new Position(1, 2), character1.getPosition());
        assertEquals(1, character1.getPosition().getX());
        assertEquals(2, character1.getPosition().getY());
    }


}
