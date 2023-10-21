import java.lang.ref.ReferenceQueue;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class TurnSystem{
    public enum TurnCommand{
        END, ACTION, MOVE  
    }

    private static final ReferenceQueue<TurnSystem> reference = new ReferenceQueue<>();
    private static final List<WeakReference<TurnSystem>> all = Collections.synchronizedList(new ArrayList<>());

    private IO io;
    private volatile boolean doneTurn = false;

    public TurnSystem(IO io) {
        this.io = io;
        TurnSystem.all.add(new WeakReference<>(this, reference));
    }


    public void startTurn(MapController worldMap, Character character, int speed) {
        if (character.isDead() || doneTurn){
            throw new IllegalStateException("A characters turn should not be run if they are dead or has already done their turn");
        }
        
        //character.update() ~~~ or something alike that checks if there are updates like damaged equipment, removes effects etc.

        int amountOfMoves = speed;
        int amountOfActions = speed;

        // try{
            TurnCommand command = io.requestTurnCommand(worldMap, character, amountOfActions, amountOfMoves);
            while(!character.isDead()){
                switch(command){
                case END : 
                    doneTurn = true;
                    return;
                case ACTION:
                    if(amountOfActions <= 0){
                        command = io.requestAnotherTurnCommand(worldMap, character, amountOfActions, amountOfMoves);
                        continue;
                    }
                    if(action(worldMap, character)){
                        amountOfActions--;
                        break;
                    }else{
                        break;
                    }
                case MOVE:
                    if(amountOfMoves <= 0){
                        command = io.requestAnotherTurnCommand(worldMap, character, amountOfActions, amountOfMoves);
                        continue;
                    }
                    if(move(worldMap, character)){
                        amountOfMoves--;
                        break;
                    }
                    break;
                default :
                }
                command = io.requestTurnCommand(worldMap, character, amountOfActions, amountOfMoves);
            }
        // } catch(Exception e) {
            // doneTurn = true;
        // }
    }

    TurnCommand r(MapController m, Character c, int move, int action){
        return io.requestTurnCommand(m, c, move, action);
    }
    
    public boolean action(MapController worldMap, Character character) {
        return false;
    }

    public boolean move(MapController worldMap, Character character) {
        return false;
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