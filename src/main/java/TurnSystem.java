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
        int amountOfMoves = speed;
        int amountOfActions = speed;
        if (character.isDead() || doneTurn){
            throw new IllegalStateException("A characters turn should not be run if they are dead or has already done their turn");
        }
        
        //character.update() ~~~ or something alike that checks if there are updates like damaged equipment, effects that are over etc.

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
    
    public boolean action(MapController worldMap, Character character) {
        InteractableInventory ri = worldMap.getRoom(character.getPosition()).getInteractables();
        InteractableInventory ci = character.getInventory();
        ArrayList<Interactable> el = new ArrayList<>();
        for (EquipmentSlot s : EquipmentSlot.values()){
            el.add(character.getEquipmentOnBody().getEquipment(s)); // adds null atm
        }
        if (ri.size() <= 1 && ci.size() == 0 && el.size() == 0){
            return false;
        }
        try {
            Interactable requestedInteractable = io.requestInteractable(worldMap.getRoom(character.getPosition()), character);
            while(!ri.contains(requestedInteractable) && !ci.contains(requestedInteractable) && !el.contains(requestedInteractable)){
                requestedInteractable = io.requestAnotherInteractble(worldMap.getRoom(character.getPosition()), character);
            }
            Interactable.InteractableAction requestedAction = io.requestAction(requestedInteractable, character);
            while(!requestedInteractable.getPossibleActions().contains(requestedAction)){
                requestedAction = io.requestAnotherAction(requestedInteractable, character);
            }
            return performAction(worldMap, character, requestedInteractable, requestedAction); 
        } catch (Exception e) {
            return false;
        }

    }

    private boolean performAction(MapController map, Character character, Interactable requestedInteractable, Interactable.InteractableAction requestedAction) throws NullPointerException {
        switch(requestedAction){
            case LOOT -> {return loot(map, requestedInteractable, character);}
            case DROP -> {return drop(map, requestedInteractable, character);}
            case FIGHT -> {return fight(map, requestedInteractable, character);}
            case WEAR -> {return wear(map, requestedInteractable, character);}
            case TALK -> {return talk(map, requestedInteractable, character);}
            case USE -> {return use(map, requestedInteractable, character);}
            case UNEQUIP -> {return unequip(map, requestedInteractable, character);}
            default -> {return false;}
        }
    }

    private boolean loot(MapController map, Interactable requestedInteractable, Character character) {
        InteractableInventory roomInv = map.getRoom(character.getPosition()).getInteractables();
        InteractableInventory charInv = character.getInventory();
        if(!roomInv.contains(requestedInteractable) || requestedInteractable.equals(character))return false;
        if(!(requestedInteractable instanceof Character)){
            roomInv.remove(requestedInteractable);
            charInv.add(requestedInteractable);
            return true;
        }
        if(!((Character)requestedInteractable).isDead()) return false;
        InteractableInventory inInv = ((Character)requestedInteractable).getInventory();
        if(inInv.isEmpty()){
            roomInv.remove(requestedInteractable);
            charInv.add(requestedInteractable);
            return true;
        }
        try {
            Interactable ri = io.requestInteractable(((Character)requestedInteractable), character);
            while(!inInv.contains(ri)){
                ri = io.requestAnotherInteractble(((Character)requestedInteractable), character);
            }
            inInv.remove(ri);
            charInv.add(ri);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
    
    private boolean drop(MapController map, Interactable requestedInteractable, Character character) {
        return false;
    }

    private boolean fight(MapController map, Interactable requestedInteractable, Character character) {
        return false;
    }
    
    private boolean wear(MapController map, Interactable requestedInteractable, Character character) {
        return false;
    }

    private boolean talk(MapController map, Interactable requestedInteractable, Character character) {
        return false;
    }

    private boolean use(MapController map, Interactable requestedInteractable, Character character) {
        return false;
    }
    
    private boolean unequip(MapController map, Interactable requestedInteractable, Character character) {
        return false;
    }
    
    public boolean move(MapController worldMap, Character character) {
        List<CardinalDirection> directions = worldMap.getAvailableDirections(character.getPosition());
        if(directions.isEmpty()){
            return false;
        }
        try {
            CardinalDirection requestedDirection = io.requestMove(worldMap, character);
            while(!directions.contains(requestedDirection)){
                requestedDirection = io.requestAnotherMove(worldMap, character);
            }
            worldMap.moveCharacter(requestedDirection, character);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public boolean hasDoneTurn() {
        return doneTurn;
    }

    // I had to look up how to do this:
    // https://stackoverflow.com/questions/61848418/how-to-change-an-instance-variable-of-all-instances-of-a-class-at-once-java
    public static void resetTurns() {
        synchronized(TurnSystem.all){
            TurnSystem.all.removeIf(ref -> ref.get() == null);
            TurnSystem.all.stream().map(WeakReference::get).forEach(TurnSystem::resetTurn);
        }
    }
    
    private void resetTurn(){
        doneTurn = false;
    }
}