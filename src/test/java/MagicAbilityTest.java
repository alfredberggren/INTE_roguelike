import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class MagicAbilityTest {

    private Character character;
    private Player player;
    private MagicAbility magicAbility;

    @BeforeEach
    void setUp() {
        character = new Character("Rudolf", 10, 100, 1, new Position(1,1), new TextIO());
        player = new Player("Ragnar",100,10, 1, new Position(1,1), new TextIO());
        magicAbility = new MagicAbility("Fireball",10,1,"A fiery projectile",1,2,5);

    }

    @Test
    @DisplayName("Returns if a Magic Ability exist")
    public void whenMagicAbilityExist_thenReturnTheType() {
        assertEquals("MAGICAL", magicAbility.toString());
    }

    @Test
    @DisplayName("Test calculating damage for Fireball")
    public void whenCharacterUseSpell_thenCalculateTheDamage() {
        character.setLevel(2);
        if(character instanceof Player player) {
            player.increaseXP(50);
        }
        int damage = magicAbility.calculateDamageOfMagicalAbility(character);
        assertEquals(25, damage);
    }

    @Test
    @DisplayName("Test for calculating damage for Magic Ability when character has low level")
    public void whenCharacterHasLowLevel_thenCalculateTheDamageTheAbilityDo() {
        character.setLevel(1);
        if (character instanceof Player player) {
            player.increaseXP(0);
        }
        int damage = magicAbility.calculateDamageOfMagicalAbility(character);
        assertEquals(15, damage);
    }

    @Test
    @DisplayName("Test for calculating damage for Magic Ability when character has high level")
    public void whenCharacterHasHighLevel_thenCalculateTheDamageTheAbilityDo() {
        character.setLevel(10);
        if (character instanceof Player player) {
            player.increaseXP(100);
        }
        int damage = magicAbility.calculateDamageOfMagicalAbility(character);
        assertEquals(70, damage);
    }

    @Test
    @DisplayName("Test that a character is able to learn Magic")
    public void whenCharacterIsAboveRequiredLevelToUseMagic_thenCharacterCanLearnMagicAbility() {
        character.setLevel(10);
        assertTrue(magicAbility.isLearnable(character));
    }

    @Test
    @DisplayName("Test that a character is not able to learn Magic")
    public void whenCharacterIsBelowRequiredLevelToUseMagic_thenCharacterCannotLearnMagicAbility() {
        character.setLevel(0);
        assertFalse(magicAbility.isLearnable(character));
    }

    @Test
    @DisplayName("Test getting the name of a magic ability")
    public void whenGetMagicAbilityName_thenCheckIfItEquals() {
        assertEquals("Fireball", magicAbility.getName());
    }

    @Test
    @DisplayName("Test getting the casting time of a spell")
    public void whenSetCastingTimeOfSpell_thenCheckIfItEqualsGetCastingTime() {
        magicAbility.setRequiredTimeToCast(3);
        assertEquals(3, magicAbility.getRequiredTimeToCast());
    }

    @Test
    @DisplayName("Test getting the cool-down time of a spell")
    public void whenSetCoolDownTime_thenCheckIfItEqualsGetCoolDownTime() {
        magicAbility.setCoolDownTime(8);
        assertEquals(8, magicAbility.getCoolDownTime());
    }

    @Test
    @DisplayName("Test setting and getting the casting time of a spell")
    public void whenSetAndGetOfTimeToCast_thenCheckIfItReturnCorrect() {
        magicAbility.setRequiredTimeToCast(4);
        assertEquals(4, magicAbility.getRequiredTimeToCast());
        magicAbility.setRequiredTimeToCast(3);
        assertEquals(3, magicAbility.getRequiredTimeToCast());
    }

    @Test
    @DisplayName("Test setting and getting the cool-down time of a spell")
    public void whenSetAndGetOfCoolDownTime_thenCheckIfItReturnCorrect() {
        magicAbility.setCoolDownTime(10);
        assertEquals(10, magicAbility.getCoolDownTime());
        magicAbility.setCoolDownTime(5);
        assertEquals(5, magicAbility.getCoolDownTime());
    }

    @Test
    @DisplayName("Required time to cast a magic spell cannot be negative")
    public void whenTimeToCastIsNegative_thenThrowIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class, () -> {
            magicAbility.setRequiredTimeToCast(-1);
        });
    }

    @Test
    @DisplayName("Test cool-down time cannot be negative")
    public void whenCoolDownTimeIsNegative_thenThrowIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class, () -> {
            magicAbility.setCoolDownTime(-1);
        });
    }


    //ska implementera bättre equals + hashTester
    @Test
    @DisplayName("Test equals method for Ability")
    public void testAbilityEquals() {
        Ability abilityOne = new MagicAbility("Magical Spell",30,2, "Magic",2,3,5);
        Ability abilityTwo = new MagicAbility("Magical Spell",30,2, "Magic",2,3,5);
        Ability abilityThree = new MagicAbility("Magical Spell",30,2, "Magic",2,3,5);
        assertEquals(abilityOne, abilityOne);
        assertEquals(abilityOne, abilityTwo);
        assertNotSame(abilityOne, abilityThree);

    }

    @Test
    @DisplayName("Test hashCode method for Ability")
    public void testAbilityHashCode() {
        Ability abilityOne = new MagicAbility("Magical Spell",30,2, "Magic",2,3,5);
        Ability abilityTwo = new MagicAbility("Magical Spell",30,2, "Magic",2,3,5);
        Ability abilityThree = new MagicAbility("Magical Spell",30,2, "Magic",2,3,5);
        assertEquals(abilityOne.hashCode(), abilityTwo.hashCode());
        assertNotSame(abilityOne.hashCode(), abilityThree.hashCode());
    }
}
