import java.lang.ref.ReferenceQueue;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class TurnSystem{
    public enum TurnCommand{
        ACTION, MOVE, END  
    }

    private static final ReferenceQueue<TurnSystem> reference = new ReferenceQueue<>();
    private static final List<WeakReference<TurnSystem>> all = Collections.synchronizedList(new ArrayList<>());

    private IO io;
    private volatile boolean doneTurn;

    public TurnSystem(IO io) {
        this.io = io;
        TurnSystem.all.add(new WeakReference<>(this, reference));
    }

    public boolean move(Character character, MapController worldMap) {
        return false;
    }

    public boolean action(Character character, MapController worldMap) {
        return false;
    }

    public void startTurn(MapController worldMap, Character character, int speed) {
        while(true){
            if(io.requestTurnCommand(worldMap, character, speed, speed) == TurnCommand.END){
                doneTurn = true;
                return;
            }
        }
    }

    public boolean hasDoneTurn() {
        return doneTurn;
    }
    
    public void startCombatTurn(){
        
    }

    private void resetTurn(){
        doneTurn = false;
    }

    // I had to look up how to do this:
    // https://stackoverflow.com/questions/61848418/how-to-change-an-instance-variable-of-all-instances-of-a-class-at-once-java
    public static void resetTurns() {
        synchronized(TurnSystem.all){
            TurnSystem.all.removeIf(ref -> ref.get() == null);
            TurnSystem.all.stream().map(WeakReference::get).forEach(TurnSystem::resetTurn);
        }
    }
}