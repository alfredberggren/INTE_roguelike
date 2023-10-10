import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class AbilityTest {

    @Test
    @DisplayName("Returnerar om det är en Magic Ability")
    public void testOnlyMagicAbility() {
        MagicAbility magicAbility = new MagicAbility("Magic");
        assertEquals("Magic", magicAbility.typeOfAbility());
    }

    @Test
    @DisplayName("Returnerar om det är en Physical Ability")
    public void testOnlyPhysicalAbility() {
        PhysicalAbility physicalAbility = new PhysicalAbility("Physical");
        assertEquals("Physical", physicalAbility.typeOfAbility());
    }

    @Test
    @DisplayName("Returnerar om det är en Magic Ability och en Physical Ability")
    public void testBothMagicAndPhysicalAbility() {
        MagicAbility magicAbility = new MagicAbility("Magic");
        PhysicalAbility physicalAbility = new PhysicalAbility("Physical");
        assertEquals("Magic" + "Physical", magicAbility.typeOfAbility() + physicalAbility.typeOfAbility());
    }
}
