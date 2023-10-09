package src.test.java;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import src.main.java.Entity;
import src.main.java.Position;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class EntityTest {

    @Test
    @DisplayName("Testar att entitetens hälsa blir korrekt")
    public void testEntityHealth(){
      Entity entity1 = new Entity("one", 100, 10);
      assertEquals(100, entity1.getHealth());

    }

    @Test
    @DisplayName("Testar att en entitets position blir korrekt")
    public void testEntityPosition(){
        Entity entity1 = new Entity("test", 0, new Position(1, 2));
        assertEquals(new Position(1, 2), entity1.getPosition());
        assertEquals(1, entity1.getPosition().getX());
        assertEquals(2, entity1.getPosition().getY());
    }


}
