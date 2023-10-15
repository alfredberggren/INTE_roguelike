import java.util.*;

//
/* TODO:
-   Alfred: kommentera skiten så man fattar vad detta är!

Kristian:
-   Bör egentligen inte själva skapandet av NPC/items eller vad som, hanteras av separata klasser? Som själva tar
    hand om randomisering av stats osv, och således bör denna klass döpas om till typ "InteractableDecider" eller ngt?
    På det sättet kan man i konstruktorn av denna klass skicka in objekt av sådana "interactablegeneratorer" som t.ex.
    alla ska ärva från interface "InteractableGenerator" eller nåt. Finns säkert ett pattern för detta. Har kikat på
    Decorator, som inte är direkt det, men skulle kunna vara ett alternativ. Factory? Nej, egentligen kanske mer Builder?!
    Om man ska efterlikna det patternet, borde denna klass heta "Director".

    Dessutom skulle detta eventuellt möjliggöra för "friare"/enklare implementation av klassbiblioteket. Det ger oss
    också möjlighet till att "mocka" dessa generatorer for now, och skapar dessutom mycket mindre hårdkod.

    Detta skulle så klart innebära en del omkodning, men...

    Det jag menar att i konstruktorn till den här klassen skickar man in olika maps med "InteractableGenerator"-objekt
    som nycklar, och Integers som probabilites. Parametrarna kan vara:
    HashMap <InteractableGenerator, Integer> PositiveItemProbabilities,
    PositiveNPCProbabilites
    NegativeItemProbabilities
    NegativeNPCProbabilites

-   Sen får vi också tänka på hur vi kan få mindre "hårdkodning" gällande difficulty:n....

-   Känner också att all randomisering borde, till så hög grad som möjligt (förutom gaussian madness kanske)
    baseras på tydliga procent (skala 1-100). Detta för att hålla det så konsekvent, och således lättförståeligt,
    som möjligt.

-   Jag förstår inte heller determineProbabilites() fungerar. Eller, timmen är så sen så jag inte har modet till att
    ta mig an det.
 */
public class InteractableBuilder {
    private static final String[] WEAPON_ARMOR_MATS = {
            "Steel",
            "Iron",
            "Stone",
            "Wood"
    };
    // Base probabilities
    private List<HashMap<?, Integer>> probabilityMaps;
    private HashMap<String, Integer> matProbabilities;
    private HashMap<String, Integer> posNpcProbabilities;
    private HashMap<String, Integer> posFoodProbabilities;
    private HashMap<Equipment.Effect, Integer> posPotionProbabilities;
    private HashMap<String, Integer> negativeNpcProbabilities;
    Random r = new Random();
    private int diffScale;

    public InteractableBuilder(int diffScale) {
        this.diffScale = diffScale;
        setBaseProbabilities();
        determineProbabilities();
    }


    /**
     * Sets hardcoded base probabilities (ATM). The probabilities work in such a way, that when a random number between
     * 1-100 is generated, the number is compared to the key's value. If the number is less than than the value, that
     * key is chosen. Therefore, the higher the number, the higher the chance of that "thing" generating.
     */
    private void setBaseProbabilities() {
        probabilityMaps = new ArrayList<>();
        matProbabilities = new LinkedHashMap<>(){{
            put("Steel", 10);
            put("Iron", 25);
            put("Stone", 55);
            put("Wood", 100);
        }};
        probabilityMaps.add(matProbabilities);
        posNpcProbabilities = new LinkedHashMap<>(){{
            put("Wizard", 10);
            put("Merchant", 25);
            put("Traveller", 100);
        }};
        probabilityMaps.add(posNpcProbabilities);
        posFoodProbabilities = new LinkedHashMap<>(){{
            put("Steak", 10);
            put("Porkchop", 25);
            put("Soup", 40);
            put("Mushroom", 60);
            put("Bread", 100);
        }};
        probabilityMaps.add(posFoodProbabilities);
        posPotionProbabilities = new LinkedHashMap<>(){{
            put(Equipment.Effect.HEALTH, 25);
            put(Equipment.Effect.SPEED, 50);
            put(Equipment.Effect.DAMAGE, 75);
            put(Equipment.Effect.ARMOR, 100);
        }};
        probabilityMaps.add(posPotionProbabilities);
        negativeNpcProbabilities = new LinkedHashMap<>(){{
            put("Wolf", 30);
            put("Troll", 50);
            put("Giant", 75);
            put("Ghost", 90);
            put("Dragon", 100);
        }};
        probabilityMaps.add(negativeNpcProbabilities);
    }

    /**
     * Decides which type of, and makes, a positive Interactable
     * @return
     * 90% chance of returning a positive consumable, 10% chance of returning a positive NPC.
     */
    public Interactable getPositiveInteractable() {
        if (r.nextInt(10) == 0) {
            return getPositiveNPC();
        } else {
            return getPositiveConsumable();
        }
    }


    /**
     *
     * @return
     * 50/50 chance to decide either positive food item, or positive potion item
     */
    private Interactable getPositiveConsumable() {
        boolean food = r.nextBoolean();
        if (food) {
            return getPositiveFoodItem();
        } else {
            return getPositivePotionItem();
        }
    }

    /**
     * Randomizes a number between 1 - 100, to then check the posPotionProbabilities. If random number is lower than
     * the probability, that Potion is created (with HARDCODED turnlimit).
     * @return
     * A "positive" PotionItem. Null if the number wasn't lower than any probability.
     */
    private Interactable getPositivePotionItem() {
        int determinator = r.nextInt(100) + 1;
        for (Equipment.Effect e : posPotionProbabilities.keySet()) {
            if (determinator < posPotionProbabilities.get(e)) {
                return new PotionItem(e.name(), e, 5);
            }
        }
        return null;
    }

    /**
     * Randomizes a number between 1 - 100, to then check the Food Probabilities. If random number is lower than
     * the probability, that food is created (with HARDCODED healvalue).
     * @return
     * A "positive" Food item. Null if the number wasn't lower than any probability.
     */
    private Interactable getPositiveFoodItem() {
        int determinator = r.nextInt(100) + 1;
        for (String s : posFoodProbabilities.keySet()) {
            if (determinator < posFoodProbabilities.get(s)) {
                return new FoodItem(s, 10);
            }
        }
        return null;
    }

    /**
     * Randomizes a number between 1 - 100, to then check the Positive NPC probabilites. If random number is lower than
     * the probability, that npc is created (with HARDCODED health and speed values).
     * @return
     * A positive NPC.
     * Null if the number wasn't lower than any probability.
     */
    private Interactable getPositiveNPC() {
        int determinator = r.nextInt(100) + 1;
        for (String s : posNpcProbabilities.keySet()) {
            if (determinator < posNpcProbabilities.get(s)) {
                return new NPC(s, 100, 10);
            }
        }
        return null;
    }

    /**
     * Randomizes a number between 1 - 100, to then check the negative Interactable probabilites. If random number is lower than
     * the probability, that interactable is created (ATM hardcoded to negative NPC's, with hardcoded health and speed).
     * @return
     * A negative Interactable (NPC).
     * Null if the number wasn't lower than any probability.
     */
    public Interactable getNegativeInteractable() {
        int determinator = r.nextInt(100) + 1;
        for (String s : negativeNpcProbabilities.keySet()) {
            if (determinator < negativeNpcProbabilities.get(s)) {
                return new NPC(s, 100, 10);
            }
        }
        return null;
    }


    private void determineProbabilities() {
        for (HashMap<?, Integer> m : probabilityMaps) {
            int tempDiffScale = diffScale;
            int changeFactor = tempDiffScale * 2 / m.keySet().size();
            putChangeFactor(m, changeFactor, tempDiffScale);
        }
    }

    private static <T> void putChangeFactor(HashMap<T, Integer> m, int changeFactor, int tempDiffScale) {
        for (T t : m.keySet()) {
            m.put(t, m.get(t) - tempDiffScale);
            tempDiffScale = tempDiffScale - changeFactor;
        }
    }

}
