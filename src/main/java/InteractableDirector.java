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
    private int difficultyScale;

    Map<HashMap<InteractableGenerator, Integer>, Integer> generatorProbabilityMaps;

    public InteractableDirector(Map<HashMap<InteractableGenerator, Integer>, Integer> generatorProbabilities) {
        generatorProbabilityMaps = convertPercentages(generatorProbabilities);
        Map<HashMap<InteractableGenerator, Integer>, Integer> temp = new HashMap<>();
        for (Map.Entry<HashMap<InteractableGenerator, Integer>, Integer> me : generatorProbabilityMaps.entrySet()) {
            temp.put((HashMap<InteractableGenerator, Integer>) convertPercentages(me.getKey()), me.getValue());
        }
        generatorProbabilityMaps = temp;
        determineProbabilities();
    }

    /**
     * Decides which probability map to use based on the percentage values in generatorProbabilityMaps.
     * @return
     * Returns an interactable from the randomly selected map.
     */
    public Interactable getInteractable() {
        int mapDeterminator = r.nextInt(100) + 1;
        System.out.println(generatorProbabilityMaps);
        for (Map.Entry<HashMap<InteractableGenerator, Integer>, Integer> me : generatorProbabilityMaps.entrySet()) {
            if (mapDeterminator < me.getValue()) {
                return getInteractableFromProbabilityMap(me.getKey());
            }
        }
        return null;
    }

    /**
     * Makes an interactable from a probability map, based on percentages as values.
     * @return
     * Returns a generated interactable from a randomly selected InteractableGenerator.
     */
    private Interactable getInteractableFromProbabilityMap(HashMap<InteractableGenerator, Integer> probMap) {
        int interactableGeneratorDeterminator = r.nextInt(100) + 1;
        for (Map.Entry<InteractableGenerator, Integer> e : probMap.entrySet()) {
            if (interactableGeneratorDeterminator < e.getValue()) {
                return e.getKey().generateInteractable();
            }
        }
        return null;
    }

    private void determineProbabilities() {
        for (HashMap<?, Integer> m : generatorProbabilityMaps.keySet()) {
            int tempDiffScale = difficultyScale;
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
        System.out.println(mapToGenerateFrom + " " + check);

        Map<T, Integer> newMap = new HashMap<>();
        
        for (Map.Entry<T, Integer> e : mapToGenerateFrom.entrySet()) {
            check = check + e.getValue();
            System.out.println(e.getValue() + " : " + check);

            newMap.put(e.getKey(), check);
        }
        System.out.println("Total check: " +check);

        if (check != 100) {
            throw new IllegalStateException("Percentages must amount to exactly 100!");
        }
        return newMap;
    }

    public void setDifficultyScale(int difficultyScale) {
        this.difficultyScale = difficultyScale;
    }
}
