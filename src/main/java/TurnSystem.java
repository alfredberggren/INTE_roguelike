import java.util.function.BooleanSupplier;

public class TurnSystem{
    public enum TurnCommand{
        ACTION, MOVE, END  
    }

    private IO io;

    public TurnSystem(IO io) {
        this.io = io;
    }

    public void move(Character player, MapController worldMap) {
    }

    public void action(Player player, MapController worldMap) {
    }

    public void startTurn(MapController worldMap, Character character, int speed) {
        while(true){
            if(io.requestTurnCommand(worldMap, character) == TurnCommand.END) return;
        }
    }

    public boolean isTurnEnded() {
        return true;
    }
    
    public void startCombatTurn(){
        
    }
}