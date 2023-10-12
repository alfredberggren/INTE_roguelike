import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class AbilityTest {

    @Test
    @DisplayName("Returns if it is a Magic Ability")
    public void testOnlyMagicAbility() {
        MagicAbility magicAbility = new MagicAbility("Fireball",25,"Magic");
        assertEquals("Magic", magicAbility.toString());
    }

    @Test
    @DisplayName("Returns if it is a Physical Ability")
    public void testOnlyPhysicalAbility() {
        PhysicalAbility physicalAbility = new PhysicalAbility("Sword",15,"Physical");
        assertEquals("Physical", physicalAbility.toString());
    }

    @Test
    @DisplayName("Returns if it is a Magic Ability and a Physical Ability")
    public void testBothMagicAndPhysicalAbility() {
        MagicAbility magicAbility = new MagicAbility("Fireball", 20,"Magic");
        PhysicalAbility physicalAbility = new PhysicalAbility("Sword", 10, "Physical");
        assertEquals("Magic" + "Physical", magicAbility.toString() + physicalAbility);
    }

    @Test
    @DisplayName("Test calculating damage for Fireball")
    public void testCalculateDamageForFireball() {
        MagicAbility fireBall = new MagicAbility("Fireball",10, "Magic");
        Player player = new Player(100, 10, 50);
        player.setLevel(2);
        player.setExperiencePoint(50);
        int damage = fireBall.calculateDamage(player);
        assertEquals(25, damage);
    }

    @Test
    @DisplayName("Test calculating damage for Sword")
    public void testCalculateDamageForSword() {
        PhysicalAbility sword = new PhysicalAbility("Sword",5, "Physical");
        Player player = new Player(100, 10, 50);
        player.setLevel(2);
        player.setExperiencePoint(50);
        int damage = sword.calculateDamage(player);
        assertEquals(14, damage);
    }

}
