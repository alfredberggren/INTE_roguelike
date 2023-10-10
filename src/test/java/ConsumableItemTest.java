import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

//Ska testa olika typer av consumables (potions, mat etc.)
public class ConsumableItemTest {
    private static final Set<Interactable.InteractableAction> DEFAULT_CONSUMABLE_INTERACTABLE_ACTIONS = new HashSet<>(Arrays.asList(
            Interactable.InteractableAction.USE,
            Interactable.InteractableAction.DROP,
            Interactable.InteractableAction.LOOT)
    );

    @Test
    @DisplayName("Testar att ett Food har korrekt healing-värde")
    public void testFoodHasCorrectHealValueTest() {
        int expectedHealValue = 5;
        FoodItem f1 = new FoodItem("Bread", expectedHealValue);
        assertEquals(expectedHealValue, f1.getHealValue());
    }

    @Test
    @DisplayName("Testar att en Potion har korrekt effekt och tidsgräns i antal drag")
    public void testPotionHasCorrectEffectAndTurnDuration() {
        int expectedTurnLimit = 10;
        Equipment.Effect expectedEffect = Equipment.Effect.SPEED;
        PotionItem p1 = new PotionItem("SpeedPotion", expectedEffect, expectedTurnLimit);
        assertEquals(expectedEffect, p1.getEffect());
        assertEquals(expectedTurnLimit, p1.getTurnLimit());
    }

    @Test
    @DisplayName("Ett namn som är null ska hanteras av FoodItem")
    public void testCtrNullNameShouldThrowException() {
        assertThrows(NullPointerException.class, () -> {
            ConsumableItem c1 = new FoodItem(null, 10);
        });
    }

    @Test
    @DisplayName("Ett tomt namn ska hanteras")
    public void testEmptyNameShouldThrowException() {
        assertThrows(IllegalArgumentException.class, () -> {
            ConsumableItem c1 = new PotionItem("", Equipment.Effect.DAMAGE, 100);
        });
    }

    @Test
    @DisplayName("Ett healvalue som är mindre än 0 ska hanteras")
    public void testNullHealValueShouldThrowException() {
        assertThrows(IllegalArgumentException.class, () -> {
            ConsumableItem c1 = new FoodItem("Test", -1);
        });
    }

    @Test
    @DisplayName("En effekt som är null ska kasta undantag")
    public void testNullEffectShouldThrowException() {
        assertThrows(NullPointerException.class, () -> {
            ConsumableItem c1 = new PotionItem("Test", null, 19);
        });
    }

    @Test
    @DisplayName("En turnlimit som är mindre än eller lika med 0 ska kasta undantag")
    public void testZeroOrLessTurnLimitShouldThrowException() {
        assertThrows(IllegalArgumentException.class, () -> {
            ConsumableItem c1 = new PotionItem("Test", Equipment.Effect.DAMAGE, -10);
        } );
    }
}
