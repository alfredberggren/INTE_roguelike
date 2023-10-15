import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.spy;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.concurrent.TimeUnit;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.parallel.ResourceLock;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class TurnSystemTest {

    @Mock 
    IO io;

    /*
     *
     * Move Tests
     *
    */

    @Test
    public void whenMoveAndNoMoveAvailable_thenISEThrown() { // maybe it should be IAE
        Map worldMap = new Map();
        Position playerPosition = new Position(0, 0);
        Room playerRoom = new Room(playerPosition);
        playerRoom.setPossibleRoutes(new ArrayList<CardinalDirection>());
        worldMap.addRoom(playerPosition, playerRoom);

        TurnSystem turnSystem = new TurnSystem(io);
        Player player = new Player(1, 1, playerPosition);

        assertThrows(IllegalStateException.class, () -> {
            turnSystem.move(player, worldMap);
        }, "When there is no option for movement then There wasn't an exception Thrown");
    }

    @Test
    public void whenMove_thenCharacterHasMoved(){
        TurnSystem turnSystem = new TurnSystem(io);
        Map worldMap = new Map();
        Position originalPosition = new Position(0, 0);
        Room originalRoom = new Room(originalPosition);
        Position otherPosition = new Position(0, 1);
        Room otherRoom = new Room(originalPosition);

        ArrayList<CardinalDirection> routes = new ArrayList<>();
        routes.add(CardinalDirection.NORTH);
        originalRoom.setPossibleRoutes(routes);

        worldMap.addRoom(originalPosition, originalRoom);
        worldMap.addRoom(otherPosition, otherRoom);
        Player player = new Player(1, 1, originalPosition);
                
        Mockito.when(io.requestMove(worldMap, player)).thenReturn(CardinalDirection.NORTH);
        turnSystem.move(player, worldMap);
        assertEquals(otherPosition, player.getPosition(), "Player did not change position");
    }

    @Test
    public void whenMoveReRequestedTwoTimes_thenCharacterHasMoved(){
        TurnSystem turnSystem = new TurnSystem(io);
        Map worldMap = new Map();
        Position originalPosition = new Position(0, 0);
        Room originalRoom = new Room(originalPosition);
        Position otherPosition = new Position(0, 1);
        Room otherRoom = new Room(originalPosition);

        ArrayList<CardinalDirection> routes = new ArrayList<>();
        routes.add(CardinalDirection.NORTH);
        originalRoom.setPossibleRoutes(routes);

        worldMap.addRoom(originalPosition, originalRoom);
        worldMap.addRoom(otherPosition, otherRoom);
        Player player = new Player(1, 1, originalPosition);
                
        Mockito.when(io.requestMove(worldMap, player)).thenReturn(CardinalDirection.SOUTH);
        Mockito.when(io.requestAnotherMove(worldMap, player)).thenReturn(CardinalDirection.EAST, CardinalDirection.NORTH);
        turnSystem.move(player, worldMap);
        assertEquals(otherPosition, player.getPosition(), "Player did not change position");
    }

    @Test
    public void whenMoveReRequested_thenIAEThrown(){
        TurnSystem turnSystem = new TurnSystem(io);
        Map worldMap = new Map();
        Position originalPosition = new Position(0, 0);
        Room originalRoom = new Room(originalPosition);
        Position otherPosition = new Position(0, 1);
        Room otherRoom = new Room(originalPosition);

        ArrayList<CardinalDirection> routes = new ArrayList<>();
        routes.add(CardinalDirection.NORTH);
        originalRoom.setPossibleRoutes(routes);

        worldMap.addRoom(originalPosition, originalRoom);
        worldMap.addRoom(otherPosition, otherRoom);
        Player player = new Player(1, 1, originalPosition);
                
        Mockito.when(io.requestMove(worldMap, player)).thenReturn(CardinalDirection.SOUTH);
        Mockito.when(io.requestAnotherMove(worldMap, player)).thenThrow(IllegalArgumentException.class);
        assertThrows(IllegalStateException.class, () -> {
            turnSystem.move(player, worldMap);
        }, "Wrong input doesn't Throw Illegal Argument Exception");
    }

    /*
     *
     *  Action Tests 
     *
    */

    @Test
    public void whenActionAndNoActionAvailable_thenISEThrown() { // maybe it should be IAE
        Map worldMap = new Map();
        Position playerPosition = new Position(0, 0);
        Room playerRoom = new Room(playerPosition, new InteractableInventory());
        worldMap.addRoom(playerPosition, playerRoom);

        TurnSystem turnSystem = new TurnSystem(io);
        Player player = new Player(1, 1, playerPosition);

        assertThrows(IllegalStateException.class, () -> {
            turnSystem.action(player, worldMap);
        }, "When there is no option Interactables there wasn't an exception Thrown");
    }

    /*
     * Different Actions
    */

    @Test
    public void whenActionLoot_thenItemIsInCharacterInventoryNotRoom() { 
        //prob gonna be removed after reformat of equipment
        HashSet<Interactable.InteractableAction> actionSet = new HashSet<>();
        actionSet.add(Interactable.InteractableAction.LOOT);

        PhysicalAbility testAbility = new PhysicalAbility("Test Ability", 1, 1);
        Equipment testEquipment = new Equipment("Test Equipment", actionSet, Equipment.Effect.HEALTH, 1, testAbility);   

        Map worldMap = new Map();
        Position playerPosition = new Position(0, 0);
        Room playerRoom = new Room(playerPosition, new InteractableInventory(testEquipment));
        worldMap.addRoom(playerPosition, playerRoom);

        TurnSystem turnSystem = new TurnSystem(io);
        Player player = new Player(1, 1, playerPosition);


        //need to make requestInteractable() and change requestAction()
        Mockito.when(io.requestInteractable(worldMap, player)).thenReturn(testEquipment);
        Mockito.when(io.requestAction(worldMap, player, testEquipment)).thenReturn(Interactable.InteractableAction.LOOT);

        turnSystem.action(player, worldMap);
        
        //need to wait for getInventory()
        assertEquals(player.getInventory().contains(testEquipment), "Player does not have the expected inventory.");
        assertEquals(playerRoom.getInteractables().isEmpty(), "Room does not have the expected inventory.");
    }

    @Test
    public void whenActionDrop_thenItemIsInRoomInventoryNotCharacter() { 
        //prob gonna be removed after reformat of equipment
        HashSet<Interactable.InteractableAction> actionSet = new HashSet<>();
        actionSet.add(Interactable.InteractableAction.DROP);

        PhysicalAbility testAbility = new PhysicalAbility("Test Ability", 1, 1);
        Equipment testEquipment = new Equipment("Test Equipment", actionSet, Equipment.Effect.HEALTH, 1, testAbility);   

        Map worldMap = new Map();
        Position playerPosition = new Position(0, 0);
        Room playerRoom = new Room(playerPosition, new InteractableInventory());
        worldMap.addRoom(playerPosition, playerRoom);

        TurnSystem turnSystem = new TurnSystem(io);
        Player player = new Player(1, 1, playerPosition);

        //don't know if there gonna be method in player for this
        player.getInventory().add(testEquipment);

        //this is gonna be fixed soon
        //need to make requestInteractable() and change requestAction()!! 
        Mockito.when(io.requestInteractable(worldMap, player)).thenReturn(testEquipment);
        Mockito.when(io.requestAction(worldMap, player, testEquipment)).thenReturn(Interactable.InteractableAction.DROP);

        turnSystem.action(player, worldMap);
        
        //need to wait for getInventory()
        assertTrue(playerRoom.getInteractables().contains(testEquipment), "Room does not have the expected inventory.");
        assertTrue(player.getInventory().isEmpty(), "Player does not have the expected inventory.");
    }

    @Test
    public void whenActionWear_thenItemIsInEquippedNotInRoom() { 
        //prob gonna be removed after reformat of equipment
        HashSet<Interactable.InteractableAction> actionSet = new HashSet<>();
        actionSet.add(Interactable.InteractableAction.WEAR);

        PhysicalAbility testAbility = new PhysicalAbility("Test Ability", 1, 1);
        //prob needs update when there is added what equipment slot it takes (EquipmentSlot.HEAD) 
        Equipment testEquipment = new Equipment("Test Equipment", actionSet, Equipment.Effect.HEALTH, 1, testAbility);   

        Map worldMap = new Map();
        Position playerPosition = new Position(0, 0);
        Room playerRoom = new Room(playerPosition, new InteractableInventory(testEquipment));
        worldMap.addRoom(playerPosition, playerRoom);

        TurnSystem turnSystem = new TurnSystem(io);
        Player player = new Player(1, 1, playerPosition);

        //this is gonna be fixed soon
        //need to make requestInteractable() and change requestAction()!! 
        Mockito.when(io.requestInteractable(worldMap, player)).thenReturn(testEquipment);
        Mockito.when(io.requestAction(worldMap, player, testEquipment)).thenReturn(Interactable.InteractableAction.WEAR);

        turnSystem.action(player, worldMap);
        
        //need to wait for getEquipmentOnBody()
        assertEquals(player.getEquipmentOnBody().getValue(EquipmentSlot.HEAD), "Character didn't equip the equipment.");
        assertTrue(playerRoom.getInteractables().isEmpty(), "Room does not have the expected inventory.");
    }

    @Test
    public void whenActionUse_thenItemIsNotInRoomAndEffectHasApplied() { 
        PotionItem testPotion = new PotionItem("Test Potion", Equipment.Effect.HEALTH, 1);   

        Map worldMap = new Map();
        Position playerPosition = new Position(0, 0);
        Room playerRoom = new Room(playerPosition, new InteractableInventory(testPotion));
        worldMap.addRoom(playerPosition, playerRoom);

        TurnSystem turnSystem = new TurnSystem(io);
        Player player = new Player(1, 1, playerPosition);

        //need to make requestInteractable() and change requestAction()
        Mockito.when(io.requestInteractable(worldMap, player)).thenReturn(testPotion);
        Mockito.when(io.requestAction(worldMap, player, testPotion)).thenReturn(Interactable.InteractableAction.LOOT);

        turnSystem.action(player, worldMap);
        
        //I don't know how Effect work or how the potion is gonna work so this is how the assert looks for now
        assertEquals(Equipment.Effect.HEALTH, player.getEffect(), "Player does not have the expected Effect.");
        assertTrue(playerRoom.getInteractables().isEmpty(), "Room does not have the expected inventory.");
    }

    @Test
    public void whenActionFight_thenNpcIsDeadPlayerAlive() { 
        HashSet<Interactable.InteractableAction> actionSet = new HashSet<>();
        actionSet.add(Interactable.InteractableAction.FIGHT);
        //Charater needs IO in constructor
        NPC npc = new NPC("testNPC", 1, 1, 1, actionSet);

        Map worldMap = new Map();
        Position playerPosition = new Position(0, 0);
        Room playerRoom = new Room(playerPosition, new InteractableInventory(npc));
        worldMap.addRoom(playerPosition, playerRoom);

        TurnSystem playerTurnSystem = new TurnSystem(io);
        Player player = new Player(100, 100, playerPosition);

        //need to make requestInteractable() and change requestAction()
        Mockito.when(io.requestInteractable(worldMap, player)).thenReturn(npc);
        Mockito.when(io.requestAction(worldMap, player, npc)).thenReturn(Interactable.InteractableAction.FIGHT);

        playerTurnSystem.action(player, worldMap);
        
        //I don't know how Effect work or how the potion is gonna work so this is how the assert looks for now
        assertFalse(player.isDead(), "Player is dead");
        assertTrue(npc.isDead(), "Npc isn't dead");
    }

    /*
     * Needs to know how we know if a Character has talked with another character
     * maybe each character has a method called dialog() and it returns a string
    */
    @Test
    public void whenActionTalk_then() { 
        HashSet<Interactable.InteractableAction> actionSet = new HashSet<>();
        actionSet.add(Interactable.InteractableAction.TALK);
        //Charater needs IO in constructor
        NPC npc = new NPC("testNPC", 1, 1, 1, actionSet);

        Map worldMap = new Map();
        Position playerPosition = new Position(0, 0);
        Room playerRoom = new Room(playerPosition, new InteractableInventory(npc));
        worldMap.addRoom(playerPosition, playerRoom);

        TurnSystem playerTurnSystem = new TurnSystem(io);
        Player player = new Player(100, 100, playerPosition);

        //need to make requestInteractable() and change requestAction()
        Mockito.when(io.requestInteractable(worldMap, player)).thenReturn(npc);
        Mockito.when(io.requestAction(worldMap, player, npc)).thenReturn(Interactable.InteractableAction.TALK);

        playerTurnSystem.action(player, worldMap);
        
        //I don't know how how talking is suppose to work so i don't know how to see if it happened
    }

    /*
     * Request another action tests
    */

    @Test
    public void whenActionAsksForInteractableReRequestTwoTimes_thenItemIsInCharacterInventoryNotInRoom() { 
        //prob gonna be removed after reformat of equipment
        HashSet<Interactable.InteractableAction> actionSet = new HashSet<>();
        actionSet.add(Interactable.InteractableAction.LOOT);

        PhysicalAbility testAbility = new PhysicalAbility("Test Ability", 1, 1);
        Equipment testEquipmentOne = new Equipment("Test Equipment One", actionSet, Equipment.Effect.HEALTH, 1, testAbility);
        Equipment testEquipmentTwo = new Equipment("Test Equipment Two", actionSet, Equipment.Effect.HEALTH, 1, testAbility);  
        Equipment testEquipmentThree = new Equipment("Test Equipment Three", actionSet, Equipment.Effect.HEALTH, 1, testAbility);     

        Map worldMap = new Map();
        Position playerPosition = new Position(0, 0);
        Room playerRoom = new Room(playerPosition, new InteractableInventory(testEquipmentThree));
        worldMap.addRoom(playerPosition, playerRoom);

        TurnSystem turnSystem = new TurnSystem(io);
        Player player = new Player(1, 1, playerPosition);


        //need to make requestInteractable() and change requestAction()
        Mockito.when(io.requestInteractable(worldMap, player)).thenReturn(testEquipmentOne);
        Mockito.when(io.requestAnotherInteractable(worldMap, player)).thenReturn(testEquipmentTwo, testEquipmentThree);
        Mockito.when(io.requestAction(worldMap, player, testEquipmentThree)).thenReturn(Interactable.InteractableAction.LOOT);

        turnSystem.action(player, worldMap);
        
        //need to wait for getInventory()
        assertTrue(player.getInventory().contains(testEquipmentThree), "Player does not have the expected inventory.");
        assertTrue(playerRoom.getInteractables().isEmpty(), "Room does not have the expected inventory.");
    } 
    
    @Test
    public void whenActionAsksForInteractableReRequest_thenIAEThrown() { 
        //prob gonna be removed after reformat of equipment
        HashSet<Interactable.InteractableAction> actionSet = new HashSet<>();
        actionSet.add(Interactable.InteractableAction.LOOT);

        PhysicalAbility testAbility = new PhysicalAbility("Test Ability", 1, 1);
        Equipment testEquipmentOne = new Equipment("Test Equipment One", actionSet, Equipment.Effect.HEALTH, 1, testAbility);
        Equipment testEquipmentTwo = new Equipment("Test Equipment Two", actionSet, Equipment.Effect.HEALTH, 1, testAbility);     

        Map worldMap = new Map();
        Position playerPosition = new Position(0, 0);
        Room playerRoom = new Room(playerPosition, new InteractableInventory(testEquipmentOne));
        worldMap.addRoom(playerPosition, playerRoom);

        TurnSystem turnSystem = new TurnSystem(io);
        Player player = new Player(1, 1, playerPosition);


        //need to make requestInteractable() and change requestAction()
        Mockito.when(io.requestInteractable(worldMap, player)).thenReturn(testEquipmentTwo);
        Mockito.when(io.requestAnotherInteractable(worldMap, player)).thenThrow(IllegalArgumentException.class);
        
        assertThrows(IllegalStateException.class, () -> {
            turnSystem.action(player, worldMap);
        }, "Wrong input doesn't Throw Illegal Argument Exception");
    }

    @Test
    public void whenActionAsksForActionReRequestTwoTimes_thenItemIsInCharacterInventoryNotInRoom() { 
        //prob gonna be removed after reformat of equipment
        HashSet<Interactable.InteractableAction> actionSet = new HashSet<>();
        actionSet.add(Interactable.InteractableAction.LOOT);

        PhysicalAbility testAbility = new PhysicalAbility("Test Ability", 1, 1);
        Equipment testEquipment = new Equipment("Test Equipment One", actionSet, Equipment.Effect.HEALTH, 1, testAbility);
  

        Map worldMap = new Map();
        Position playerPosition = new Position(0, 0);
        Room playerRoom = new Room(playerPosition, new InteractableInventory(testEquipment));
        worldMap.addRoom(playerPosition, playerRoom);

        TurnSystem turnSystem = new TurnSystem(io);
        Player player = new Player(1, 1, playerPosition);


        //need to make requestInteractable() and change requestAction()
        Mockito.when(io.requestInteractable(worldMap, player)).thenReturn(testEquipment);
        Mockito.when(io.requestAction(worldMap, player, testEquipment)).thenReturn(Interactable.InteractableAction.FIGHT);
        Mockito.when(io.requestAnotherAction(worldMap, player, testEquipment)).thenReturn(Interactable.InteractableAction.USE,Interactable.InteractableAction.LOOT);

        turnSystem.action(player, worldMap);
        
        //need to wait for getInventory()
        assertTrue(player.getInventory().contains(testEquipment), "Player does not have the expected inventory.");
        assertTrue(playerRoom.getInteractables().isEmpty(), "Room does not have the expected inventory.");
    } 

    @Test
    public void whenActionAsksForActionReRequest_thenIAEThrown() { 
        //prob gonna be removed after reformat of equipment
        HashSet<Interactable.InteractableAction> actionSet = new HashSet<>();
        actionSet.add(Interactable.InteractableAction.LOOT);

        PhysicalAbility testAbility = new PhysicalAbility("Test Ability", 1, 1);
        Equipment testEquipment = new Equipment("Test Equipment One", actionSet, Equipment.Effect.HEALTH, 1, testAbility);


        Map worldMap = new Map();
        Position playerPosition = new Position(0, 0);
        Room playerRoom = new Room(playerPosition, new InteractableInventory(testEquipment));
        worldMap.addRoom(playerPosition, playerRoom);

        TurnSystem turnSystem = new TurnSystem(io);
        Player player = new Player(1, 1, playerPosition);


        //need to make requestInteractable() and change requestAction()
        Mockito.when(io.requestInteractable(worldMap, player)).thenReturn(testEquipment);
        Mockito.when(io.requestAction(worldMap, player, testEquipment)).thenReturn(Interactable.InteractableAction.FIGHT);
        Mockito.when(io.requestAnotherAction(worldMap, player, testEquipment)).thenThrow(IllegalArgumentException.class);

        assertThrows(IllegalStateException.class, () -> {
            turnSystem.action(player, worldMap);
        }, "Wrong input doesn't Throw Illegal Argument Exception");
    }

    /*
     * 
     * Start Turn tests
     * 
    */

    @Test
    @Timeout(value = 15, unit = TimeUnit.SECONDS, threadMode = Timeout.ThreadMode.SEPARATE_THREAD)
    public void whenCommandEndTurnIsCalled_thenTurnEndedIsTrue() throws InterruptedException {
        Thread.sleep(10_000);

        Map worldMap = new Map();
        Position characterPosition = new Position(0, 0);
        Character character = new Character(1, 1, characterPosition);

        Mockito.when(io.requestTurnCommand(worldMap, character)).thenReturn(TurnSystem.TurnCommand.END);
        
        TurnSystem turnSystem = new TurnSystem(io);
        turnSystem.startTurn(worldMap, character, character.getSpeed());
        assertTrue(turnSystem.isTurnEnded(), "Turn hasn't ended");
    }

    @Test
    @Timeout(value = 15, unit = TimeUnit.SECONDS, threadMode = Timeout.ThreadMode.SEPARATE_THREAD)
    public void whenCommandMoveIsCalled_thenCommandIsReRequested() throws InterruptedException {
        Thread.sleep(5_000);

        Map worldMap = new Map();
        Position characterPosition = new Position(0, 0);
        //when character has 0 speed it can do neither action or movement
        Character character = new Character(1, 0, characterPosition);

        Mockito.when(io.requestTurnCommand(worldMap, character)).thenReturn(TurnSystem.TurnCommand.MOVE);
        Mockito.when(io.requestAnotherTurnCommand(worldMap, character)).thenReturn(TurnSystem.TurnCommand.END);
        
        TurnSystem turnSystem = new TurnSystem(io);
        turnSystem.startTurn(worldMap, character, character.getSpeed());
        assertTrue(turnSystem.isTurnEnded(), "Turn hasn't ended");
    }

    @Test
    @Timeout(value = 15, unit = TimeUnit.SECONDS, threadMode = Timeout.ThreadMode.SEPARATE_THREAD)
    public void whenCommandActionIsCalled_thenCommandIsReRequested() throws InterruptedException {
        Thread.sleep(5_000);

        Map worldMap = new Map();
        Position characterPosition = new Position(0, 0);
        //when character has 0 speed it can do neither action or movement
        Character character = new Character(1, 0, characterPosition);

        Mockito.when(io.requestTurnCommand(worldMap, character)).thenReturn(TurnSystem.TurnCommand.ACTION);
        Mockito.when(io.requestAnotherTurnCommand(worldMap, character)).thenReturn(TurnSystem.TurnCommand.END);
        
        TurnSystem turnSystem = new TurnSystem(io);
        turnSystem.startTurn(worldMap, character, character.getSpeed());
        assertTrue(turnSystem.isTurnEnded(), "Turn hasn't ended");
    }

    @Test
    @Timeout(value = 15, unit = TimeUnit.SECONDS, threadMode = Timeout.ThreadMode.SEPARATE_THREAD)
    public void whenWrongInput_thenCommandIsReRequested() throws InterruptedException {
        Thread.sleep(5_000);

        Map worldMap = new Map();
        Position characterPosition = new Position(0, 0);
        Character character = new Character(1, 1, characterPosition);

        Mockito.when(io.requestTurnCommand(worldMap, character)).thenThrow(IllegalArgumentException.class);
        Mockito.when(io.requestAnotherTurnCommand(worldMap, character)).thenReturn(TurnSystem.TurnCommand.END);
        
        TurnSystem turnSystem = new TurnSystem(io);
        turnSystem.startTurn(worldMap, character, character.getSpeed());
        assertTrue(turnSystem.isTurnEnded(), "Turn hasn't ended");
    }

    /*
     * Start turn move tests
    */

    @Test
    @Timeout(value = 15, unit = TimeUnit.SECONDS, threadMode = Timeout.ThreadMode.SEPARATE_THREAD)
    public void whenCommandMoveAndCharacterMoved_thenMovesHasDecreased() throws InterruptedException {
        Thread.sleep(10_000);

        Map worldMap = new Map();
        Position characterPosition = new Position(0, 0);
        //when character has 0 speed it can do neither action or movement
        Character character = new Character(1, 1, characterPosition);

        Mockito.when(io.requestTurnCommand(worldMap, character)).thenReturn(TurnSystem.TurnCommand.MOVE);
        Mockito.when(io.requestAnotherTurnCommand(worldMap, character)).thenReturn(TurnSystem.TurnCommand.END);
        
        TurnSystem turnSystem = new TurnSystem(io);
        TurnSystem spyTurnSystem = spy(turnSystem);

        

        turnSystem.startTurn(worldMap, character, character.getSpeed());
        assertTrue(turnSystem.isTurnEnded(), "Turn hasn't ended");
        
    }

}