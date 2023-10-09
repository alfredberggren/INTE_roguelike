package src.test.java;

import org.junit.jupiter.api.Test;

public class EntityTest {

    @Test
    public void testEntityHealth(){
      Entity entity1 = new Entity(one, 100);
      assertEquals(100, entity.getHealth());
    }


}
