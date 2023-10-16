import java.util.*;
import java.util.Map;

/* TODO:

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
public class InteractableDirector {
    Random r = new Random();
    private int diffScale;

    Map<HashMap<InteractableGenerator, Integer>, Integer> generatorProbabilityMaps;

    public InteractableDirector(int diffScale, Map.Entry<HashMap<InteractableGenerator, Integer>, Integer>... generatorProbabilities) {
        this.diffScale = diffScale;
        this.generatorProbabilityMaps = new HashMap<>();
        addProbabilityMaps(generatorProbabilities);
        convertPercentages(generatorProbabilityMaps);
        for (Map<InteractableGenerator, Integer> hm : generatorProbabilityMaps.keySet()) {
            convertPercentages(hm);
        }
        determineProbabilities();
    }

    private void addProbabilityMaps(Map.Entry<HashMap<InteractableGenerator, Integer>, Integer>[] generatorProbabilities) {
        for (Map.Entry<HashMap<InteractableGenerator, Integer>, Integer> e : generatorProbabilities) {
            generatorProbabilityMaps.put(e.getKey(), e.getValue());
        }
    }

    /**
     * Decides which type of, and makes, a positive Interactable
     * @return
     * 90% chance of returning a positive consumable, 10% chance of returning a positive NPC.
     */
    public Interactable getInteractable() {
        int mapDeterminator = r.nextInt(100) + 1;
        for (Map.Entry<HashMap<InteractableGenerator, Integer>, Integer> me : generatorProbabilityMaps.entrySet()) {
            if (mapDeterminator < me.getValue()) {
                return getInteractableFromProbabilityMap(me.getKey());
            }
        }
        return null;
    }

    private Interactable getInteractableFromProbabilityMap(HashMap<InteractableGenerator, Integer> probMap) {
        int interactableGeneratorDeterminator = r.nextInt(100) + 1;
        for (Map.Entry<InteractableGenerator, Integer> e : probMap.entrySet()) {
            if (interactableGeneratorDeterminator < e.getValue()) {
                return e.getKey().generateInteractable();
            }
        }
        return null;
    }

//    /**
//     *
//     * @return
//     * 50/50 chance to decide either positive food item, or positive potion item
//     */
//    private Interactable getPositiveConsumable() {
//        boolean food = r.nextBoolean();
//        if (food) {
//            return getPositiveFoodItem();
//        } else {
//            return getPositivePotionItem();
//        }
//    }
//
//    /**
//     * Randomizes a number between 1 - 100, to then check the posPotionProbabilities. If random number is lower than
//     * the probability, that Potion is created (with HARDCODED turnlimit).
//     * @return
//     * A "positive" PotionItem. Null if the number wasn't lower than any probability.
//     */
//    private Interactable getPositivePotionItem() {
//        int determinator = r.nextInt(100) + 1;
//        for (Equipment.Effect e : posPotionProbabilities.keySet()) {
//            if (determinator < posPotionProbabilities.get(e)) {
//                return new PotionItem(e.name(), e, 5);
//            }
//        }
//        return null;
//    }
//
//    /**
//     * Randomizes a number between 1 - 100, to then check the Food Probabilities. If random number is lower than
//     * the probability, that food is created (with HARDCODED healvalue).
//     * @return
//     * A "positive" Food item. Null if the number wasn't lower than any probability.
//     */
//    private Interactable getPositiveFoodItem() {
//        int determinator = r.nextInt(100) + 1;
//        for (String s : posFoodProbabilities.keySet()) {
//            if (determinator < posFoodProbabilities.get(s)) {
//                return new FoodItem(s, 10);
//            }
//        }
//        return null;
//    }
//
//    /**
//     * Randomizes a number between 1 - 100, to then check the Positive NPC probabilites. If random number is lower than
//     * the probability, that npc is created (with HARDCODED health and speed values).
//     * @return
//     * A positive NPC.
//     * Null if the number wasn't lower than any probability.
//     */
//    private Interactable getPositiveNPC() {
//        int determinator = r.nextInt(100) + 1;
//        for (String s : posNpcProbabilities.keySet()) {
//            if (determinator < posNpcProbabilities.get(s)) {
//                return new NPC(s, 100, 10);
//            }
//        }
//        return null;
//    }
//
//    /**
//     * Randomizes a number between 1 - 100, to then check the negative Interactable probabilites. If random number is lower than
//     * the probability, that interactable is created (ATM hardcoded to negative NPC's, with hardcoded health and speed).
//     * @return
//     * A negative Interactable (NPC).
//     * Null if the number wasn't lower than any probability.
//     */
//    public Interactable getNegativeInteractable() {
//        int determinator = r.nextInt(100) + 1;
//        for (String s : negativeNpcProbabilities.keySet()) {
//            if (determinator < negativeNpcProbabilities.get(s)) {
//                return new NPC(s, 100, 10);
//            }
//        }
//        return null;
//    }


    private void determineProbabilities() {
        for (HashMap<?, Integer> m : generatorProbabilityMaps.keySet()) {
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

    /**
     * Updates percentages to adhere to how our implementation of random works.
     * @param mapToGenerateFrom
     * @return
     * New map with updated Integer-values
     * @param <T>
     * @throws IllegalStateException
     * If values amount to more than 100%
     *
     */
    private static <T> Map<T, Integer> convertPercentages(Map<T, Integer> mapToGenerateFrom){
        int check = 0;
        Map<T, Integer> newMap = new HashMap<>();
        
        for (Map.Entry<T, Integer> e : mapToGenerateFrom.entrySet()) {
            check = check + e.getValue();
            if (check > 100) {
                throw new IllegalStateException("Percentages must amount to exactly 100");
            }

            newMap.put(e.getKey(), check);

        }
        return newMap;
    }
}
