import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/*Ville bara själv testa att mocka något*/
/*Mock objekt efterliknar riktiga objekt, men är anpassade att användas i testningssyfte*/

class MagicAbilityMockTest {

    @Test
    void testCalculateDamageOfMagicalAbilityWithMocking() {
        Character character = Mockito.mock(Character.class);

        //Konfigurera mockade metoder
        when(character.getLevel()).thenReturn(5);

        //Skapa MagicAbility-objekt
        MagicAbility magicAbility = new MagicAbility("Fireball", 10,3,"A fiery projectile",1,2,5);

        //Anropa metoden som ska testas
        int damage = magicAbility.calculateDamageOfMagicalAbility(character);
        assertEquals(35, damage);
    }

    @Test
    void testManaCostForMagicWithSufficientMana() {
        MagicAbility magicAbility = new MagicAbility("Fireball", 10,3,"A fiery projectile",1,2,5);


        //Mock av Character
        Character character = Mockito.mock(Character.class);
        when(character.getMana()).thenReturn(10); //simulera 10 i mana

        assertDoesNotThrow(() -> magicAbility.manaCostForMagic(character));
        verify(character).decreaseMana(5); //kontroll att decreaseMana anropades med rätt värde
    }
}
