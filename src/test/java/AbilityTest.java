import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class AbilityTest {

    @Test
    @DisplayName("Returns if it is a Magic Ability")
    public void testOnlyMagicAbility() {
        MagicAbility magicAbility = new MagicAbility("Magic");
        assertEquals("Magic", magicAbility.toString());
    }

    @Test
    @DisplayName("Returns if it is a Physical Ability")
    public void testOnlyPhysicalAbility() {
        PhysicalAbility physicalAbility = new PhysicalAbility("Physical");
        assertEquals("Physical", physicalAbility.toString());
    }

    @Test
    @DisplayName("Returns if it is a Magic Ability and a Physical Ability")
    public void testBothMagicAndPhysicalAbility() {
        MagicAbility magicAbility = new MagicAbility("Magic");
        PhysicalAbility physicalAbility = new PhysicalAbility("Physical");
        assertEquals("Magic" + "Physical", magicAbility.toString() + physicalAbility.toString());
    }

}
