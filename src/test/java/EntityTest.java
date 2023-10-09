package src.test.java;

import org.junit.jupiter.api.Test;

public class EntityTest {

    @Test
    public void testEntityHealth(){
      Entity entity1 = new Entity(one, 100);
      assertEquals(100, entity.getHealth());
      blabla
    }

    @Test
    public void testEntityPosition(){
        Entity entity1 = new Entity(0, 0, 0, new Position(1, 2));
        assertEquals(new Position(1, 2), entity1.getPosition());
        assertEquals(1, entity1.getPosition().getX());
        assertEquals(2, entity1.getPosition().getY());
    }


}
