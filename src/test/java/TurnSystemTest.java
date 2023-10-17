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
        MapController worldMapController = new MapController();
        Position playerPosition = new Position(0, 0);
        Room playerRoom = new Room(playerPosition);
        worldMapController.add(playerPosition, playerRoom);

        Player player = new Player("name", 1, 1, playerPosition, io);

        assertThrows(IllegalStateException.class, () -> {
            player.getTurnSystem().move(player, worldMapController);
        }, "When there is no option for movement then There wasn't an exception Thrown");
    }

    @Test
    public void whenMove_thenCharacterHasMoved(){
        MapController worldMapController = new MapController();

        Position originalPosition = new Position(0, 0);
        Room originalRoom = new Room(originalPosition);
        worldMapController.add(originalPosition, originalRoom);

        Position otherPosition = new Position(0, 1);
        Room otherRoom = new Room(otherPosition);
        worldMapController.add(otherPosition, otherRoom);

        Player player = new Player("name", 1, 1, originalPosition, io);
        
        Mockito.when(io.requestMove(worldMapController, player)).thenReturn(CardinalDirection.NORTH);

        player.getTurnSystem().move(player, worldMapController);
        assertEquals(otherPosition, player.getPosition(), "Player did not change position");
    }

    @Test
    public void whenMoveReRequestedTwoTimes_thenCharacterHasMoved(){
        MapController worldMapController = new MapController();

        Position originalPosition = new Position(0, 0);
        Room originalRoom = new Room(originalPosition);
        worldMapController.add(originalPosition, originalRoom);

        Position otherPosition = new Position(0, 1);
        Room otherRoom = new Room(otherPosition);
        worldMapController.add(otherPosition, otherRoom);

        Player player = new Player("name", 1, 1, originalPosition, io);
                
        Mockito.when(io.requestMove(worldMapController, player)).thenReturn(CardinalDirection.SOUTH);
        Mockito.when(io.requestAnotherMove(worldMapController, player)).thenReturn(CardinalDirection.EAST, CardinalDirection.NORTH);

        player.getTurnSystem().move(player, worldMapController);
        assertEquals(otherPosition, player.getPosition(), "Player did not change position");
    }

    @Test
    public void whenMoveReRequested_thenIAEThrown(){
        MapController worldMapController = new MapController();

        Position originalPosition = new Position(0, 0);
        Room originalRoom = new Room(originalPosition);
        worldMapController.add(originalPosition, originalRoom);

        Position otherPosition = new Position(0, 1);
        Room otherRoom = new Room(otherPosition);
        worldMapController.add(otherPosition, otherRoom);

        Player player = new Player("name", 1, 1, originalPosition, io);
                
        Mockito.when(io.requestMove(worldMapController, player)).thenReturn(CardinalDirection.SOUTH);
        Mockito.when(io.requestAnotherMove(worldMapController, player)).thenThrow(IllegalArgumentException.class);

        assertThrows(IllegalStateException.class, () -> {
            player.getTurnSystem().move(player, worldMapController);
        }, "Wrong input doesn't Throw Illegal Argument Exception");
    }

    /*
     *
     *  Action Tests 
     *
    */

    @Test
    public void whenActionAndNoActionAvailable_thenISEThrown() { // maybe it should be IAE
        MapController worldMapController = new MapController();
        Position playerPosition = new Position(0, 0);
        Room playerRoom = new Room(playerPosition, new InteractableInventory());
        worldMapController.add(playerPosition, playerRoom);

        Player player = new Player("name", 1, 1, playerPosition, io);

        assertThrows(IllegalStateException.class, () -> {
            player.getTurnSystem().action(player, worldMapController);
        }, "When there is no option Interactables there wasn't an exception Thrown");
    }

    /*
     * Different Actions
     * 
     * loot:
    */

    @Test
    public void whenActionLoot_thenItemIsInCharacterInventoryNotRoom() { 
        //prob gonna be removed after reformat of equipment
        HashSet<Interactable.InteractableAction> actionSet = new HashSet<>();
        actionSet.add(Interactable.InteractableAction.LOOT);

        PhysicalAbility testAbility = new PhysicalAbility("Test Ability", 1, 1);
        Equipment testEquipment = new Equipment("Test Equipment", EquipmentSlot.LEFT_HAND, actionSet, Equipment.Effect.HEALTH, 1, testAbility);   

        MapController worldMapController = new MapController();
        Position playerPosition = new Position(0, 0);
        Room playerRoom = new Room(playerPosition, new InteractableInventory(testEquipment));
        worldMapController.add(playerPosition, playerRoom);

        TurnSystem turnSystem = new TurnSystem(io);
        Player player = new Player("name", 1, 1, playerPosition, io);


        //need to make requestInteractable() and change requestAction()
        Mockito.when(io.requestInteractable(worldMapController, player)).thenReturn(testEquipment);
        Mockito.when(io.requestAction(worldMapController, player, testEquipment)).thenReturn(Interactable.InteractableAction.LOOT);

        player.getTurnSystem().action(player, worldMapController);
        
        //need to wait for getInventory()
        assertEquals(player.getInventory().contains(testEquipment), "Player does not have the expected inventory.");
        assertEquals(playerRoom.getInteractables().isEmpty(), "Room does not have the expected inventory.");
    }

    /*
     * Drop:
    */

    @Test
    public void whenActionDrop_thenItemIsInRoomInventoryNotCharacter() { 
        //prob gonna be removed after reformat of equipment
        HashSet<Interactable.InteractableAction> actionSet = new HashSet<>();
        actionSet.add(Interactable.InteractableAction.DROP);

        PhysicalAbility testAbility = new PhysicalAbility("Test Ability", 1, 1);
        Equipment testEquipment = new Equipment("Test Equipment", EquipmentSlot.LEFT_HAND, actionSet, Equipment.Effect.HEALTH, 1, testAbility);   

        MapController worldMapController = new MapController();
        Position playerPosition = new Position(0, 0);
        Room playerRoom = new Room(playerPosition, new InteractableInventory());
        worldMapController.add(playerPosition, playerRoom);

        TurnSystem turnSystem = new TurnSystem(io);
        Player player = new Player("name", 1, 1, playerPosition, io);

        //don't know if there gonna be method in player for this
        player.getInventory().add(testEquipment);

        //this is gonna be fixed soon
        //need to make requestInteractable() and change requestAction()!! 
        Mockito.when(io.requestInteractable(worldMapController, player)).thenReturn(testEquipment);
        Mockito.when(io.requestAction(worldMapController, player, testEquipment)).thenReturn(Interactable.InteractableAction.DROP);

        player.getTurnSystem().action(player, worldMapController);
        
        //need to wait for getInventory()
        assertTrue(playerRoom.getInteractables().contains(testEquipment), "Room does not have the expected inventory.");
        assertTrue(player.getInventory().isEmpty(), "Player does not have the expected inventory.");
    }

    /*
     * Wear:
    */

    @Test
    public void whenActionWear_thenItemIsInEquippedNotInRoom() { 
        //prob gonna be removed after reformat of equipment
        HashSet<Interactable.InteractableAction> actionSet = new HashSet<>();
        actionSet.add(Interactable.InteractableAction.WEAR);

        PhysicalAbility testAbility = new PhysicalAbility("Test Ability", 1, 1);
        //prob needs update when there is added what equipment slot it takes (EquipmentSlot.HEAD) 
        Equipment testEquipment = new Equipment("Test Equipment", EquipmentSlot.LEFT_HAND, actionSet, Equipment.Effect.HEALTH, 1, testAbility);   

        MapController worldMapController = new MapController();
        Position playerPosition = new Position(0, 0);
        Room playerRoom = new Room(playerPosition, new InteractableInventory(testEquipment));
        worldMapController.add(playerPosition, playerRoom);

        TurnSystem turnSystem = new TurnSystem(io);
        Player player = new Player("name", 1, 1, playerPosition, io);

        //this is gonna be fixed soon
        //need to make requestInteractable() and change requestAction()!! 
        Mockito.when(io.requestInteractable(worldMapController, player)).thenReturn(testEquipment);
        Mockito.when(io.requestAction(worldMapController, player, testEquipment)).thenReturn(Interactable.InteractableAction.WEAR);

        player.getTurnSystem().action(player, worldMapController);
        player.unEquip(testEquipment);
        
        //need to wait for getEquipmentOnBody()
        assertEquals(testEquipment, player.getEquipmentOnBody().getEquipment(EquipmentSlot.LEFT_HAND), "Character didn't equip the equipment.");
        assertTrue(playerRoom.getInteractables().isEmpty(), "Room does not have the expected inventory.");
    }

    /*
     * Use:
    */

    @Test
    public void whenActionUse_thenItemIsNotInRoomAndEffectHasApplied() { 
        PotionItem testPotion = new PotionItem("Test Potion", Equipment.Effect.HEALTH, 1);   

        MapController worldMapController = new MapController();
        Position playerPosition = new Position(0, 0);
        Room playerRoom = new Room(playerPosition, new InteractableInventory(testPotion));
        worldMapController.add(playerPosition, playerRoom);

        TurnSystem turnSystem = new TurnSystem(io);
        Player player = new Player("name", 1, 1, playerPosition, io);

        //need to make requestInteractable() and change requestAction()
        Mockito.when(io.requestInteractable(worldMapController, player)).thenReturn(testPotion);
        Mockito.when(io.requestAction(worldMapController, player, testPotion)).thenReturn(Interactable.InteractableAction.LOOT);

        player.getTurnSystem().action(player, worldMapController);
        
        //I don't know how Effect work or how the potion is gonna work so this is how the assert looks for now
        assertEquals(Equipment.Effect.HEALTH, player.getEffect(), "Player does not have the expected Effect.");
        assertTrue(playerRoom.getInteractables().isEmpty(), "Room does not have the expected inventory.");
    }

    /*
     * Use:
    */

    @Test
    public void whenActionFight_thenNpcIsDeadPlayerAlive() { 
        HashSet<Interactable.InteractableAction> actionSet = new HashSet<>();
        actionSet.add(Interactable.InteractableAction.FIGHT);
        //Charater needs IO in constructor
        NPC npc = new NPC("testNPC", 1, 1, new Position(0, 0), new NPCAI());

        MapController worldMapController = new MapController();
        Position playerPosition = new Position(0, 0);
        Room playerRoom = new Room(playerPosition, new InteractableInventory(npc));
        worldMapController.add(playerPosition, playerRoom);

        TurnSystem playerTurnSystem = new TurnSystem(io);
        Player player = new Player("name", 100, 100, playerPosition, io);

        //need to make requestInteractable() and change requestAction()
        Mockito.when(io.requestInteractable(worldMapController, player)).thenReturn(npc);
        Mockito.when(io.requestAction(worldMapController, player, npc)).thenReturn(Interactable.InteractableAction.FIGHT);

        playerTurnSystem.action(player, worldMapController);
        
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
        NPC npc = new NPC("testNPC", 1, 1, new Position(0, 0), new NPCAI());

        MapController worldMapController = new MapController();
        Position playerPosition = new Position(0, 0);
        Room playerRoom = new Room(playerPosition, new InteractableInventory(npc));
        worldMapController.add(playerPosition, playerRoom);

        TurnSystem playerTurnSystem = new TurnSystem(io);
        Player player = new Player("name", 100, 100, playerPosition, io);

        //need to make requestInteractable() and change requestAction()
        Mockito.when(io.requestInteractable(worldMapController, player)).thenReturn(npc);
        Mockito.when(io.requestAction(worldMapController, player, npc)).thenReturn(Interactable.InteractableAction.TALK);

        playerTurnSystem.action(player, worldMapController);
        
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
        Equipment testEquipmentOne = new Equipment("Test Equipment One", EquipmentSlot.LEFT_HAND, actionSet, Equipment.Effect.HEALTH, 1, testAbility);
        Equipment testEquipmentTwo = new Equipment("Test Equipment Two", EquipmentSlot.LEFT_HAND, actionSet, Equipment.Effect.HEALTH, 1, testAbility);  
        Equipment testEquipmentThree = new Equipment("Test Equipment Three", EquipmentSlot.LEFT_HAND, actionSet, Equipment.Effect.HEALTH, 1, testAbility);     

        MapController worldMapController = new MapController();
        Position playerPosition = new Position(0, 0);
        Room playerRoom = new Room(playerPosition, new InteractableInventory(testEquipmentThree));
        worldMapController.add(playerPosition, playerRoom);

        TurnSystem turnSystem = new TurnSystem(io);
        Player player = new Player("name", 100, 100, playerPosition, io);


        //need to make requestInteractable() and change requestAction()
        Mockito.when(io.requestInteractable(worldMapController, player)).thenReturn(testEquipmentOne);
        Mockito.when(io.requestAnotherInteractble(worldMapController, player)).thenReturn(testEquipmentTwo, testEquipmentThree);
        Mockito.when(io.requestAction(worldMapController, player, testEquipmentThree)).thenReturn(Interactable.InteractableAction.LOOT);

        player.getTurnSystem().action(player, worldMapController);
        
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
        Equipment testEquipmentOne = new Equipment("Test Equipment One", EquipmentSlot.LEFT_HAND, actionSet, Equipment.Effect.HEALTH, 1, testAbility);
        Equipment testEquipmentTwo = new Equipment("Test Equipment Two", EquipmentSlot.LEFT_HAND, actionSet, Equipment.Effect.HEALTH, 1, testAbility);     

        MapController worldMapController = new MapController();
        Position playerPosition = new Position(0, 0);
        Room playerRoom = new Room(playerPosition, new InteractableInventory(testEquipmentOne));
        worldMapController.add(playerPosition, playerRoom);

        TurnSystem turnSystem = new TurnSystem(io);
        Player player = new Player("name", 100, 100, playerPosition, io);


        //need to make requestInteractable() and change requestAction()
        Mockito.when(io.requestInteractable(worldMapController, player)).thenReturn(testEquipmentTwo);
        Mockito.when(io.requestAnotherInteractble(worldMapController, player)).thenThrow(IllegalArgumentException.class);
        
        assertThrows(IllegalStateException.class, () -> {
            player.getTurnSystem().action(player, worldMapController);
        }, "Wrong input doesn't Throw Illegal Argument Exception");
    }

    @Test
    public void whenActionAsksForActionReRequestTwoTimes_thenItemIsInCharacterInventoryNotInRoom() { 
        //prob gonna be removed after reformat of equipment
        HashSet<Interactable.InteractableAction> actionSet = new HashSet<>();
        actionSet.add(Interactable.InteractableAction.LOOT);

        PhysicalAbility testAbility = new PhysicalAbility("Test Ability", 1, 1);
        Equipment testEquipment = new Equipment("Test Equipment One", EquipmentSlot.LEFT_HAND, actionSet, Equipment.Effect.HEALTH, 1, testAbility);
  

        MapController worldMapController = new MapController();
        Position playerPosition = new Position(0, 0);
        Room playerRoom = new Room(playerPosition, new InteractableInventory(testEquipment));
        worldMapController.add(playerPosition, playerRoom);

        TurnSystem turnSystem = new TurnSystem(io);
        Player player = new Player("name", 100, 100, playerPosition, io);


        //need to make requestInteractable() and change requestAction()
        Mockito.when(io.requestInteractable(worldMapController, player)).thenReturn(testEquipment);
        Mockito.when(io.requestAction(worldMapController, player, testEquipment)).thenReturn(Interactable.InteractableAction.FIGHT);
        Mockito.when(io.requestAnotherAction(worldMapController, player, testEquipment)).thenReturn(Interactable.InteractableAction.USE,Interactable.InteractableAction.LOOT);

        player.getTurnSystem().action(player, worldMapController);
        
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
        Equipment testEquipment = new Equipment("Test Equipment One", EquipmentSlot.BELT, Equipment.Effect.HEALTH, 1, testAbility);


        MapController worldMapController = new MapController();
        Position playerPosition = new Position(0, 0);
        Room playerRoom = new Room(playerPosition, new InteractableInventory(testEquipment));
        worldMapController.add(playerPosition, playerRoom);

        TurnSystem turnSystem = new TurnSystem(io);
        Player player = new Player("name", 1, 1, playerPosition, io);


        //need to make requestInteractable() and change requestAction()
        Mockito.when(io.requestInteractable(worldMapController, player)).thenReturn(testEquipment);
        Mockito.when(io.requestAction(worldMapController, player, testEquipment)).thenReturn(Interactable.InteractableAction.FIGHT);
        Mockito.when(io.requestAnotherAction(worldMapController, player, testEquipment)).thenThrow(IllegalArgumentException.class);

        assertThrows(IllegalStateException.class, () -> {
            player.getTurnSystem().action(player, worldMapController);
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

        MapController worldMapController = new MapController();
        Position characterPosition = new Position(0, 0);
        Character character = new Character("name", 1, 1, characterPosition, io);

        Mockito.when(io.requestTurnCommand(worldMapController, character)).thenReturn(TurnSystem.TurnCommand.END);
        
        TurnSystem turnSystem = new TurnSystem(io);
        turnSystem.startTurn(worldMapController, character, character.getSpeed());
        assertTrue(turnSystem.isTurnEnded(), "Turn hasn't ended");
    }

    @Test
    @Timeout(value = 15, unit = TimeUnit.SECONDS, threadMode = Timeout.ThreadMode.SEPARATE_THREAD)
    public void whenCommandMoveIsCalled_thenCommandIsReRequested() throws InterruptedException {
        Thread.sleep(5_000);

        MapController worldMapController = new MapController();
        Position characterPosition = new Position(0, 0);
        //when character has 0 speed it can do neither action or movement
        Character character = new Character("name", 1, 0, characterPosition, io);

        Mockito.when(io.requestTurnCommand(worldMapController, character)).thenReturn(TurnSystem.TurnCommand.MOVE);
        Mockito.when(io.requestAnotherTurnCommand(worldMapController, character)).thenReturn(TurnSystem.TurnCommand.END);
        
        TurnSystem turnSystem = new TurnSystem(io);
        turnSystem.startTurn(worldMapController, character, character.getSpeed());
        assertTrue(turnSystem.isTurnEnded(), "Turn hasn't ended");
    }

    @Test
    @Timeout(value = 15, unit = TimeUnit.SECONDS, threadMode = Timeout.ThreadMode.SEPARATE_THREAD)
    public void whenCommandActionIsCalled_thenActionIsPerformed() throws InterruptedException {
        Thread.sleep(5_000);

        MapController worldMapController = new MapController();
        Position characterPosition = new Position(0, 0);
        //when character has 0 speed it can do neither action or movement
        Character character = new Character("name", 1, 0, characterPosition, io);

        Mockito.when(io.requestTurnCommand(worldMapController, character)).thenReturn(TurnSystem.TurnCommand.ACTION);
        Mockito.when(io.requestAnotherTurnCommand(worldMapController, character)).thenReturn(TurnSystem.TurnCommand.END);
        
        TurnSystem turnSystem = new TurnSystem(io);
        turnSystem.startTurn(worldMapController, character, character.getSpeed());
        assertTrue(turnSystem.isTurnEnded(), "Turn hasn't ended");
    }

    @Test
    @Timeout(value = 15, unit = TimeUnit.SECONDS, threadMode = Timeout.ThreadMode.SEPARATE_THREAD)
    public void whenWrongInput_thenCommandIsReRequested() throws InterruptedException {
        Thread.sleep(5_000);

        MapController worldMapController = new MapController();
        Position characterPosition = new Position(0, 0);
        Character character = new Character("name", 1, 1, characterPosition, io);

        Mockito.when(io.requestTurnCommand(worldMapController, character)).thenThrow(IllegalArgumentException.class);
        Mockito.when(io.requestAnotherTurnCommand(worldMapController, character)).thenReturn(TurnSystem.TurnCommand.END);
        
        TurnSystem turnSystem = new TurnSystem(io);
        turnSystem.startTurn(worldMapController, character, character.getSpeed());
        assertTrue(turnSystem.isTurnEnded(), "Turn hasn't ended");
    }

    /*
     * Start turn move tests
    */

    @Test
    @Timeout(value = 15, unit = TimeUnit.SECONDS, threadMode = Timeout.ThreadMode.SEPARATE_THREAD)
    public void whenCommandMoveAndCharacterMoved_thenMovesHasDecreased() throws InterruptedException {
        Thread.sleep(10_000);

        MapController worldMapController = new MapController();
        Position characterPosition = new Position(0, 0);
        Character character = new Character("name", 1, 1, characterPosition, io);

        Mockito.when(io.requestTurnCommand(worldMapController, character)).thenReturn(TurnSystem.TurnCommand.MOVE);
        Mockito.when(io.requestAnotherTurnCommand(worldMapController, character)).thenReturn(TurnSystem.TurnCommand.END);
        
        TurnSystem turnSystem = new TurnSystem(io);
        TurnSystem spyTurnSystem = spy(turnSystem);

        

        turnSystem.startTurn(worldMapController, character, character.getSpeed());
        assertTrue(turnSystem.isTurnEnded(), "Turn hasn't ended");    
    }

}