package src.test.java;

import org.junit.jupiter.api.Test;
import src.main.java.Entity;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class EntityTest {

    @Test
    public void testEntityHealth(){
      Entity entity1 = new Entity("one", 100);
      assertEquals(100, entity1.getHealth());

    }


}
