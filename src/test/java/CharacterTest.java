
import org.junit.jupiter.api.*;

import java.util.Arrays;
import java.util.HashSet;

import static org.junit.jupiter.api.Assertions.*;

public class CharacterTest {
    static Character DEFAULT_CHARACTER;
    static Character DEFAULT_CHARACTER_WITH_POS = new Character("Rudolf", 100, 1, new Position(1, 2), new TextIO());

    @BeforeEach
    void setUp() {
        DEFAULT_CHARACTER = new Character("Ragnar", 80, 20, new TextIO());
    }

    //name tests
    @Test
    public void testCorrectName() {
        DEFAULT_CHARACTER.setName("Rudolf");
        assertEquals("Rudolf", DEFAULT_CHARACTER.getName());
    }

    @Test
    public void testNameInputIsNull() {
        assertThrows(NullPointerException.class, () -> {
            DEFAULT_CHARACTER.setName(null);
        });
    }

    @Test
    public void testNameInputIsEmpty() {
        assertThrows(IllegalArgumentException.class, () -> {
            DEFAULT_CHARACTER.setName("");
        });
    }

    @Test
    public void testTooShortName() {
        assertThrows(IllegalArgumentException.class, () -> {
            DEFAULT_CHARACTER.setName("A");
        });
    }

    @Test
    public void testNameBeginsWithDigit() {
        assertThrows(IllegalArgumentException.class, () -> {
            DEFAULT_CHARACTER.setName("1Ab");
        });
    }

    @Test
    public void testTooLongName() {
        assertThrows(IllegalArgumentException.class, () -> {
            DEFAULT_CHARACTER.setName("tooLongNameToBeAccepted");
        });
    }

    @Test
    public void testNameContainsNotOnlyAlphanumericCharactersAndUnderscores() {
        assertThrows(IllegalArgumentException.class, () -> {
            DEFAULT_CHARACTER.setName("Aabba&&");
        });
    }
//name tests


    @Test
    @DisplayName("Test character's health")
    public void testCharactersHealth() {
        assertEquals(80, DEFAULT_CHARACTER.getHealth());
    }

    @Test
    @DisplayName("Test character's position")
    public void testCharactersPosition() {
        assertEquals(new Position(1, 2), DEFAULT_CHARACTER_WITH_POS.getPosition());
        assertEquals(1, DEFAULT_CHARACTER_WITH_POS.getPosition().getX());
        assertEquals(2, DEFAULT_CHARACTER_WITH_POS.getPosition().getY());
    }

    @Test
    @DisplayName("Test to get character's speed")
    public void testCharactersSpeed() {
        assertEquals(20, DEFAULT_CHARACTER.getSpeed());
    }

    @Test
    @DisplayName("Test throws exception if health<0")
    public void testCharacterWithNegativeHealth() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            new Character("Ragnar", -100, 0, new TextIO());
        });
    }

    @Test
    @DisplayName("Test throws exception if speed<0")
    public void testCharacterWithNegativeSpeed() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            new Character("Ragnar", 100, 0, new TextIO());
        });
    }

    @Test
    @DisplayName("Test to decrease health to 0")
    public void testToDecreaseHealthToZero() {
        DEFAULT_CHARACTER.decreaseHealth(80);
        assertEquals(0, DEFAULT_CHARACTER.getHealth());
        assertTrue(DEFAULT_CHARACTER.isDead());
    }

    @Test
    @DisplayName("Test to decrease health to negative value")
    public void testToDecreaseHealthToNegativeValue() {
        DEFAULT_CHARACTER.decreaseHealth(100);
        assertEquals(0, DEFAULT_CHARACTER.getHealth());
        assertTrue(DEFAULT_CHARACTER.isDead());
    }

    @Test
    @DisplayName("Test to increase health")
    public void testToIncreaseHealth() {
        DEFAULT_CHARACTER.increaseHealth(20);
        assertEquals(100, DEFAULT_CHARACTER.getHealth());
    }

    @Test
    @DisplayName("Test to increase mana and ability to use magic if mana>0")
    public void testToIncreaseMana() {
        DEFAULT_CHARACTER.increaseMana(20);
        assertEquals(120, DEFAULT_CHARACTER.getMana());
        assertTrue(DEFAULT_CHARACTER.canUseMagic());
    }

    @Test
    @DisplayName("Test to decrease mana and ability to use magic if mana=0")
    public void testToDecreaseMana() {
        DEFAULT_CHARACTER.decreaseMana(100);
        assertEquals(0, DEFAULT_CHARACTER.getMana());
        assertFalse(DEFAULT_CHARACTER.canUseMagic());
    }


    @Test
    @DisplayName("Test that character get Magic Ability")
    public void testCharacterMagicAbility() {
        Character c = new Character("Rudolf", 10, 10, new Position(0, 0), new TextIO());
        MagicAbility fireMagic = new MagicAbility("Fireball", 20, 1, "Shoots fire", 2, 1);
        c.addAbility(fireMagic);
        //assertEquals("Wizard", c.getName());
        assertEquals("Fireball", fireMagic.name);
    }

    @Test
    @DisplayName("Test setting and getting Magic Ability")
    public void testCharacterSetAndGetMagicAbility() {
        MagicAbility fireMagic = new MagicAbility("Fireball", 20, 1, "Shoots fire", 2, 1);
        DEFAULT_CHARACTER.addAbility(fireMagic);
        assertEquals(fireMagic, DEFAULT_CHARACTER.getAbilities());
    }

    @Test
    @DisplayName("Test adding and forgetting spells")
    public void testCharacterSpellHandling() {
        MagicAbility fireSpell = new MagicAbility("Fire", 10, 1, "Shoots fire", 1, 2);
        MagicAbility iceSpell = new MagicAbility("Ice", 12, 1, "Shoots ice", 1, 3);
        DEFAULT_CHARACTER.addAbility(fireSpell);
        assertTrue(DEFAULT_CHARACTER.getAbilities().contains(fireSpell));
        DEFAULT_CHARACTER.forgetAbility(fireSpell);
        assertFalse(DEFAULT_CHARACTER.getAbilities().contains(fireSpell));
        assertDoesNotThrow(() -> DEFAULT_CHARACTER.forgetAbility(iceSpell));
    }

    @Test
    @DisplayName("Test set-method at health=0")
    public void testIfCharacterIsDeadWhenHealthIsZero() {
        DEFAULT_CHARACTER.setHealth(0);
        assertTrue(DEFAULT_CHARACTER.isDead());
    }

    @Test
    @DisplayName("Test throws Exception at negative health")
    public void testThrowsExceptionWithNegativeHealth() {
        assertThrows(IllegalArgumentException.class, () -> {
            DEFAULT_CHARACTER.setHealth(-10);
        });
    }

    @Test
    @DisplayName("Test to equip a character")
    public void testToEquipCharacter(){
        DEFAULT_CHARACTER.equip(new Equipment("Sword", EquipmentSlot.LEFT_HAND, Equipment.Effect.DAMAGE, 50, new PhysicalAbility("Sword", 10, 1)));
        assertEquals("LEFT_HAND: Sword", DEFAULT_CHARACTER.getEquipmentOnBody());
    }
    // public void testToEquipCharacter() {
    //     Equipment equipment = new Equipment("Sword", new HashSet<>(Arrays.asList(Interactable.InteractableAction.LOOT, Interactable.InteractableAction.DROP)), Equipment.Effect.DAMAGE, 50, new PhysicalAbility("Sword", 10, 1));
    //     DEFAULT_CHARACTER.equip(EquipmentSlot.LEFT_HAND, equipment);
    //     assertEquals("LEFT_HAND: Sword", DEFAULT_CHARACTER.getEquipmentOnBody());
    // }

    @Test //testa!
    @DisplayName("Test to equip a character when Equipment is null")
    public void testToEquipCharacterWhenEquipmentIsNull() {
        DEFAULT_CHARACTER.equip(null);
        assertEquals("", DEFAULT_CHARACTER.getEquipmentOnBody());
    }

    @Test
    @DisplayName("Test to unequip a character")
    public void testToUnEquipCharacter(){
        var testEquipment = new Equipment("Sword", EquipmentSlot.LEFT_HAND, Equipment.Effect.DAMAGE, 50, new PhysicalAbility("Sword", 10, 1));
        DEFAULT_CHARACTER.equip(testEquipment);
        DEFAULT_CHARACTER.unEquip(testEquipment);
        assertEquals("", DEFAULT_CHARACTER.getEquipmentOnBody());
    }

    // public void testToUnEquipCharacter() {
    //     Equipment equipment = new Equipment("Sword", new HashSet<>(Arrays.asList(Interactable.InteractableAction.LOOT, Interactable.InteractableAction.DROP)), Equipment.Effect.DAMAGE, 50, new PhysicalAbility("Sword", 10, 1));
    //     DEFAULT_CHARACTER.equip(EquipmentSlot.LEFT_HAND, equipment);
    //     DEFAULT_CHARACTER.unEquip(EquipmentSlot.LEFT_HAND);
    //     assertEquals("", DEFAULT_CHARACTER.getEquipmentOnBody());
    // }

    @Test //testa
    @DisplayName("Test to unequip a character when Equipment is null")
    public void testToUnEquipCharacterWhenEquipmentIsNull() {
        Equipment equipment = new Equipment("Sword", EquipmentSlot.LEFT_HAND, new HashSet<>(Arrays.asList(Interactable.InteractableAction.LOOT, Interactable.InteractableAction.DROP)), Equipment.Effect.DAMAGE, 50, new PhysicalAbility("Sword", 10, 1));
        DEFAULT_CHARACTER.equip(equipment);
        DEFAULT_CHARACTER.unEquip(null);
        assertEquals("LEFT_HAND: Sword", DEFAULT_CHARACTER.getEquipmentOnBody());
    }


    @Test
    @DisplayName("Test that equipment which was Uneqiped was added to Inventory")
    public void testThatDiscardedEquipmentWasAddedToInventory() {
        Equipment equipment = new Equipment("Sword", EquipmentSlot.LEFT_HAND, new HashSet<>(Arrays.asList(Interactable.InteractableAction.LOOT, Interactable.InteractableAction.UNEQUIP)), Equipment.Effect.DAMAGE, 50, new PhysicalAbility("Sword", 10, 1));
        DEFAULT_CHARACTER.equip(equipment);
        DEFAULT_CHARACTER.unEquip(equipment);
        assertTrue(DEFAULT_CHARACTER.getInventory().contains(equipment));
    }

    @Test
    @DisplayName("Test that equipment which was selected to equip was removed from Inventory")
    public void testThatChosenEquipmentWasRemovedFromInventory() {
        Equipment equipment = new Equipment("Sword", EquipmentSlot.LEFT_HAND, new HashSet<>(Arrays.asList(Interactable.InteractableAction.LOOT, Interactable.InteractableAction.DROP)), Equipment.Effect.DAMAGE, 50, new PhysicalAbility("Sword", 10, 1));
        DEFAULT_CHARACTER.equip(equipment);
        assertFalse(DEFAULT_CHARACTER.getInventory().contains(equipment));
    }

}
