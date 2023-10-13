import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class AbilityTest {

    static Character DEFAULT_CHARACTER;
    static Player DEFAULT_PLAYER;

    @BeforeEach
    void setUp() {
        DEFAULT_PLAYER = new Player(100, 10, new Position(0,0),100);
        DEFAULT_CHARACTER = new Character(100,10);
    }
    @Test
    @DisplayName("Returns if a Magic Ability exist")
    public void testOnlyMagicAbility() {
        MagicAbility magicAbility = new MagicAbility("Fireball",25,"Magic");
        assertEquals("Magic", magicAbility.toString());
    }

    @Test
    @DisplayName("Returns if a Physical Ability exist")
    public void testOnlyPhysicalAbility() {
        PhysicalAbility physicalAbility = new PhysicalAbility("Sword",15,"Physical");
        assertEquals("Physical", physicalAbility.toString());
    }

    @Test
    @DisplayName("Returns if both Magic Ability and Physical Ability exist")
    public void testBothMagicAndPhysicalAbility() {
        MagicAbility magicAbility = new MagicAbility("Fireball", 20,"Magic");
        PhysicalAbility physicalAbility = new PhysicalAbility("Sword", 10, "Physical");
        assertEquals("Magic" + "Physical", magicAbility.toString() + physicalAbility);
    }

    @Test
    @DisplayName("Test calculating damage for Fireball")
    public void testCalculateDamageForFireball() {
        MagicAbility fireBall = new MagicAbility("Fireball",10, "Magic");
        DEFAULT_PLAYER.setLevel(2);
        DEFAULT_PLAYER.setExperiencePoint(50);
        int damage = fireBall.calculateDamage(DEFAULT_PLAYER);
        assertEquals(25, damage);
    }

    @Test
    @DisplayName("Test calculating damage for Sword")
    public void testCalculateDamageForSword() {
        PhysicalAbility sword = new PhysicalAbility("Sword",5, "Physical");
        DEFAULT_PLAYER.setLevel(2);
        DEFAULT_PLAYER.setExperiencePoint(50);
        int damage = sword.calculateDamage(DEFAULT_PLAYER);
        assertEquals(14, damage);
    }

    @Test
    @DisplayName("Testing that magic ability is affected")
    public void testMagicAbilityAffected() {
        Spell fireSpell = new Spell("Fire");
        DEFAULT_CHARACTER.addSpell(fireSpell);
        DEFAULT_PLAYER.setExperiencePoint(100);
        MagicAbility ability = new MagicAbility(fireSpell.getName(), 10,"Magic");
        DEFAULT_PLAYER.decreaseXP(20);
        DEFAULT_CHARACTER.forgetSpell(fireSpell);
        String affectedDamage = ability.calculateAffect();
        assertEquals("You have forgotten a spell due to losing experience points", affectedDamage);
    }

    @Test
    @DisplayName("Testing that magic ability is not affected")
    public void testMagicAbilityNotAffected() {
        Spell iceSpell = new Spell("Ice");
        DEFAULT_CHARACTER.addSpell(iceSpell);
        MagicAbility ability = new MagicAbility(iceSpell.getName(), 10,"Magic");
        String affectedDamage = ability.calculateAffect();
        assertEquals("You have not forgotten any spells", affectedDamage);
    }

    @Test
    @DisplayName("Test for calculating damage for Magic Ability when player has low level")
    public void testMagicCalculateDamageForLowLevelPlayer() {
        MagicAbility fireBall = new MagicAbility("Fireball",10,"Magic");
        DEFAULT_PLAYER.setLevel(1);
        DEFAULT_PLAYER.setExperiencePoint(0);
        int damage = fireBall.calculateDamage(DEFAULT_PLAYER);
        assertEquals(15, damage);
    }

    @Test
    @DisplayName("Test for calculating damage for Magic Ability when player has high level")
    public void testMagicCalculateDamageForHighLevelPlayer() {
        MagicAbility fireBall = new MagicAbility("Fireball",10,"Magic");
        DEFAULT_PLAYER.setLevel(10);
        DEFAULT_PLAYER.setExperiencePoint(100);
        int damage = fireBall.calculateDamage(DEFAULT_PLAYER);
        assertEquals(70, damage);
    }

    @Test
    @DisplayName("Test for calculating damage for Physical Ability when player has low level")
    public void testPhysicalCalculateDamageForLowLevelPlayer() {
        PhysicalAbility sword = new PhysicalAbility("Sword", 5, "Physical");
        DEFAULT_PLAYER.setLevel(1);
        DEFAULT_PLAYER.setExperiencePoint(0);
        int damage = sword.calculateDamage(DEFAULT_PLAYER);
        assertEquals(7, damage);
    }

    @Test
    @DisplayName("Test for calculating damage for Physical Ability when player has high level")
    public void testPhysicalCalculateDamageForHighLevelPlayer() {
        PhysicalAbility sword = new PhysicalAbility("Sword", 5, "Physical");
        DEFAULT_PLAYER.setLevel(10);
        DEFAULT_PLAYER.setExperiencePoint(100);
        int damage = sword.calculateDamage(DEFAULT_PLAYER);
        assertEquals(35, damage);
    }

}
