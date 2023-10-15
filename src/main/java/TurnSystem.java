import java.util.function.BooleanSupplier;

public class TurnSystem{
    public enum TurnCommand{
        ACTION, MOVE, END  
    }

    private IO io;

    public TurnSystem(IO io) {
        this.io = io;
    }

    public void move(Character player, Map worldMap) {
    }

    public void action(Player player, Map worldMap) {
    }

    public void startTurn(Map worldMap, Character character, int i) {
        while(true){
            if(io.requestTurnCommand(worldMap, character) == TurnCommand.END) return;
        }
    }

    public boolean isTurnEnded() {
        return true;
    }
    
}