
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.mockito.Mockito.spy;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.concurrent.TimeUnit;

import org.junit.Assert;
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
    public void whenMoveAndNoMoveAvailable_thenReturnFalse() {
        MapController worldMapController = new MapController();
        Position playerPosition = new Position(0, 0);
        Room playerRoom = new Room(playerPosition);
        worldMapController.add(playerPosition, playerRoom);

        TurnSystem turnSystem = new TurnSystem(io);
        Player player = new Player("name", 1, 1, playerPosition, io);

        assertFalse(turnSystem.move(player, worldMapController), "move() did not return false when no move was available");
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

        TurnSystem turnSystem = new TurnSystem(io);
        Player player = new Player("name", 1, 1, originalPosition, io);
        
        Mockito.when(io.requestMove(worldMapController, player)).thenReturn(CardinalDirection.NORTH);

        assertTrue(turnSystem.move(player, worldMapController));
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

        TurnSystem turnSystem = new TurnSystem(io);
        Player player = new Player("name", 1, 1, originalPosition, io);
                
        Mockito.when(io.requestMove(worldMapController, player)).thenReturn(CardinalDirection.SOUTH);
        Mockito.when(io.requestAnotherMove(worldMapController, player)).thenReturn(CardinalDirection.EAST, CardinalDirection.NORTH);

        assertTrue(turnSystem.move(player, worldMapController));
        assertEquals(otherPosition, player.getPosition(), "Player did not change position");
    }

    @Test
    public void whenMoveHasWrongInput_thenReturnFalse(){
        MapController worldMapController = new MapController();

        Position originalPosition = new Position(0, 0);
        Room originalRoom = new Room(originalPosition);
        worldMapController.add(originalPosition, originalRoom);

        Position otherPosition = new Position(0, 1);
        Room otherRoom = new Room(otherPosition);
        worldMapController.add(otherPosition, otherRoom);

        TurnSystem turnSystem = new TurnSystem(io);
        Player player = new Player("name", 1, 1, originalPosition, io);
                
        Mockito.when(io.requestMove(worldMapController, player)).thenThrow(IllegalArgumentException.class);

        assertFalse(turnSystem.move(player, worldMapController), "move() did not return false when wrong input was given");
    }

    @Test
    public void whenMoveHasWrongInputInReRequest_thenReturnFalse(){
        MapController worldMapController = new MapController();

        Position originalPosition = new Position(0, 0);
        Room originalRoom = new Room(originalPosition);
        worldMapController.add(originalPosition, originalRoom);

        Position otherPosition = new Position(0, 1);
        Room otherRoom = new Room(otherPosition);
        worldMapController.add(otherPosition, otherRoom);

        TurnSystem turnSystem = new TurnSystem(io);
        Player player = new Player("name", 1, 1, originalPosition, io);
                
        Mockito.when(io.requestMove(worldMapController, player)).thenReturn(CardinalDirection.SOUTH);
        Mockito.when(io.requestAnotherMove(worldMapController, player)).thenThrow(IllegalArgumentException.class);

        assertFalse(turnSystem.move(player, worldMapController), "move() did not return false when wrong input was given");
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

        TurnSystem turnSystem = new TurnSystem(io);
        Player player = new Player("name", 1, 1, playerPosition, io);

        assertFalse(turnSystem.action(player, worldMapController), "when no action was available action() did not return false");
    }

    
    /*
     * Different Actions
     * 
     * loot:
    */


    @Test
    public void whenActionLootFromRoom_thenItemIsInCharacterInventoryNotRoom() { 
        PhysicalAbility testAbility = new PhysicalAbility("Test Ability", 1, 1);
        Equipment testEquipment = new Equipment("Test Equipment", EquipmentSlot.LEFT_HAND, Equipment.Effect.HEALTH, 1, testAbility);   

        MapController worldMapController = new MapController();
        Position playerPosition = new Position(0, 0);
        Room playerRoom = new Room(playerPosition, new InteractableInventory(testEquipment));
        worldMapController.add(playerPosition, playerRoom);

        TurnSystem turnSystem = new TurnSystem(io);
        Player player = new Player("name", 1, 1, playerPosition, io);

        Mockito.when(io.requestInteractable(playerRoom, player)).thenReturn(testEquipment);
        Mockito.when(io.requestAction(testEquipment, player)).thenReturn(Interactable.InteractableAction.LOOT);

        assertTrue(turnSystem.action(player, worldMapController), "action() did not return true");
        assertTrue(player.getInventory().contains(testEquipment), "Player does not have the expected inventory.");
        assertTrue(playerRoom.getInteractables().isEmpty(), "Room does not have the expected inventory.");
    }

    @Test
    public void whenActionLootDeadCharacter_thenItemIsInCharacterInventoryNotRoom() { 
        Position playerPosition = new Position(0, 0);

        NPC lootNpc = new NPC("npc", 1, 1, playerPosition, io); 
        lootNpc.decreaseHealth(1);

        MapController worldMapController = new MapController();
        Room playerRoom = new Room(playerPosition, new InteractableInventory(lootNpc));
        worldMapController.add(playerPosition, playerRoom);

        TurnSystem turnSystem = new TurnSystem(io);
        Player player = new Player("name", 1, 1, playerPosition, io);


        Mockito.when(io.requestInteractable(playerRoom, player)).thenReturn(lootNpc);
        Mockito.when(io.requestAction(lootNpc, player)).thenReturn(Interactable.InteractableAction.LOOT);

        assertTrue(turnSystem.action(player, worldMapController), "action() did not return true");
        assertTrue(player.getInventory().contains(lootNpc), "Player does not have the expected inventory.");
        assertTrue(playerRoom.getInteractables().isEmpty(), "Room does not have the expected inventory.");
    }

    @Test
    public void whenActionLootFromDeadCharacter_thenItemIsInPlayerInventoryNotOnNpc() { 
        PhysicalAbility testAbility = new PhysicalAbility("Test Ability", 1, 1);
        Equipment testEquipment = new Equipment("Test Equipment", EquipmentSlot.LEFT_HAND, Equipment.Effect.HEALTH, 1, testAbility);   

        Position playerPosition = new Position(0, 0);

        NPC testNpc = new NPC("npc", 1, 1, playerPosition, io); 
        testNpc.decreaseHealth(1);
        testNpc.getInventory().add(testEquipment);

        MapController worldMapController = new MapController();
        Room playerRoom = new Room(playerPosition, new InteractableInventory(testNpc));
        worldMapController.add(playerPosition, playerRoom);

        TurnSystem turnSystem = new TurnSystem(io);
        Player player = new Player("name", 1, 1, playerPosition, io);

        Mockito.when(io.requestInteractable(playerRoom, player)).thenReturn(testNpc);
        Mockito.when(io.requestAction(testNpc, player)).thenReturn(Interactable.InteractableAction.LOOT);
        Mockito.when(io.requestInteractable(testNpc, player)).thenReturn(testEquipment);

        assertTrue(turnSystem.action(player, worldMapController), "action() did not return true");
        assertTrue(player.getInventory().contains(testNpc), "Player does not have the expected inventory.");
        assertTrue(testNpc.getInventory().isEmpty(), "TestNpc does not have the expected inventory.");
    }

    @Test
    public void whenActionLootFromDeadCharacterReRequestTwoTimes_thenItemIsInPlayerInventoryNotOnNpc() { 
        PhysicalAbility testAbility = new PhysicalAbility("Test Ability", 1, 1);
        Equipment rightEquipment = new Equipment("Test Equipment", EquipmentSlot.LEFT_HAND, Equipment.Effect.HEALTH, 1, testAbility);
        Equipment wrongEquipment = new Equipment("Wrong Equipment", EquipmentSlot.LEFT_HAND, Equipment.Effect.HEALTH, 1, testAbility);      

        Position playerPosition = new Position(0, 0);

        NPC testNpc = new NPC("npc", 1, 1, playerPosition, io); 
        testNpc.decreaseHealth(1);
        testNpc.getInventory().add(rightEquipment);

        MapController worldMapController = new MapController();
        Room playerRoom = new Room(playerPosition, new InteractableInventory(testNpc));
        worldMapController.add(playerPosition, playerRoom);

        TurnSystem turnSystem = new TurnSystem(io);
        Player player = new Player("name", 1, 1, playerPosition, io);

        Mockito.when(io.requestInteractable(playerRoom, player)).thenReturn(testNpc);
        Mockito.when(io.requestAction(testNpc, player)).thenReturn(Interactable.InteractableAction.LOOT);
        Mockito.when(io.requestInteractable(testNpc, player)).thenReturn(wrongEquipment);
        Mockito.when(io.requestAnotherInteractble(testNpc, player)).thenReturn(wrongEquipment, rightEquipment);

        assertTrue(turnSystem.action(player, worldMapController), "action() did not return true");
        assertTrue(player.getInventory().contains(testNpc), "Player does not have the expected inventory.");
        assertTrue(testNpc.getInventory().isEmpty(), "TestNpc does not have the expected inventory.");
    }

    @Test
    public void whenActionLootFromDeadCharacterWrongInput_thenActionReturnFalse() { 
        PhysicalAbility testAbility = new PhysicalAbility("Test Ability", 1, 1);
        Equipment testEquipment = new Equipment("Test Equipment", EquipmentSlot.LEFT_HAND, Equipment.Effect.HEALTH, 1, testAbility);     

        Position playerPosition = new Position(0, 0);

        NPC testNpc = new NPC("npc", 1, 1, playerPosition, io); 
        testNpc.decreaseHealth(1);
        testNpc.getInventory().add(testEquipment);

        MapController worldMapController = new MapController();
        Room playerRoom = new Room(playerPosition, new InteractableInventory(testNpc));
        worldMapController.add(playerPosition, playerRoom);

        TurnSystem turnSystem = new TurnSystem(io);
        Player player = new Player("name", 1, 1, playerPosition, io);

        Mockito.when(io.requestInteractable(playerRoom, player)).thenReturn(testNpc);
        Mockito.when(io.requestAction(testNpc, player)).thenReturn(Interactable.InteractableAction.LOOT);
        Mockito.when(io.requestInteractable(testNpc, player)).thenThrow(IllegalArgumentException.class);

        assertFalse(turnSystem.action(player, worldMapController), "action() did not return false when wrong input was given");
        assertTrue(testNpc.getInventory().contains(testEquipment), "Npc doesn't have the Expected inventory");
    }

    @Test
    public void whenActionLootFromDeadCharacterWrongInputInReRequest_thenActionReturnFalse() { 
        PhysicalAbility testAbility = new PhysicalAbility("Test Ability", 1, 1);
        Equipment rightEquipment = new Equipment("Right Equipment", EquipmentSlot.LEFT_HAND, Equipment.Effect.HEALTH, 1, testAbility);     
        Equipment wrongEquipment = new Equipment("Wrong Equipment", EquipmentSlot.LEFT_HAND, Equipment.Effect.HEALTH, 1, testAbility);   

        Position playerPosition = new Position(0, 0);

        NPC testNpc = new NPC("npc", 1, 1, playerPosition, io); 
        testNpc.decreaseHealth(1);
        testNpc.getInventory().add(rightEquipment);

        MapController worldMapController = new MapController();
        Room playerRoom = new Room(playerPosition, new InteractableInventory(testNpc));
        worldMapController.add(playerPosition, playerRoom);

        TurnSystem turnSystem = new TurnSystem(io);
        Player player = new Player("name", 1, 1, playerPosition, io);

        Mockito.when(io.requestInteractable(playerRoom, player)).thenReturn(testNpc);
        Mockito.when(io.requestAction(testNpc, player)).thenReturn(Interactable.InteractableAction.LOOT);
        Mockito.when(io.requestInteractable(testNpc, player)).thenReturn(wrongEquipment);
        Mockito.when(io.requestAnotherInteractble(testNpc, player)).thenThrow(IllegalArgumentException.class);

        assertFalse(turnSystem.action(player, worldMapController), "action() did not return false when wrong input was given");
        assertTrue(testNpc.getInventory().contains(rightEquipment), "Npc doesn't have the Expected inventory");
    }

    @Test
    public void whenActionLootFromAliveCharacter_thenActionReturnFalse() {   
        Position playerPosition = new Position(0, 0);

        NPC testNpc = new NPC("npc", 1, 1, playerPosition, io); 
        testNpc.decreaseHealth(1);

        MapController worldMapController = new MapController();
        Room playerRoom = new Room(playerPosition, new InteractableInventory(testNpc));
        worldMapController.add(playerPosition, playerRoom);

        TurnSystem turnSystem = new TurnSystem(io);
        Player player = new Player("name", 1, 1, playerPosition, io);

        Mockito.when(io.requestInteractable(playerRoom, player)).thenReturn(testNpc);
        Mockito.when(io.requestAction(testNpc, player)).thenReturn(Interactable.InteractableAction.LOOT);

        assertFalse(turnSystem.action(player, worldMapController), "action() did not return false when trying to loot an alive character");
        assertTrue(playerRoom.getInteractables().contains(testNpc), "Room doesn't have the expected inventory");
    }

    /*
     * Drop:
    */

    @Test
    public void whenActionDrop_thenItemIsInRoomInventoryNotCharacter() { 
        PhysicalAbility testAbility = new PhysicalAbility("Test Ability", 1, 1);
        Equipment testEquipment = new Equipment("Test Equipment", EquipmentSlot.LEFT_HAND, Equipment.Effect.HEALTH, 1, testAbility);   

        MapController worldMapController = new MapController();
        Position playerPosition = new Position(0, 0);
        Room playerRoom = new Room(playerPosition, new InteractableInventory());
        worldMapController.add(playerPosition, playerRoom);

        TurnSystem turnSystem = new TurnSystem(io);
        Player player = new Player("name", 1, 1, playerPosition, io);
        player.getInventory().add(testEquipment);


        Mockito.when(io.requestInteractable(playerRoom, player)).thenReturn(testEquipment);
        Mockito.when(io.requestAction(testEquipment, player)).thenReturn(Interactable.InteractableAction.DROP);

        assertTrue(turnSystem.action(player, worldMapController), "action() did not return true");
        assertTrue(playerRoom.getInteractables().contains(testEquipment), "Room does not have the expected inventory.");
        assertTrue(player.getInventory().isEmpty(), "Player does not have the expected inventory.");
    }

    @Test
    public void whenActionDropItemNotInPlayerInv_thenActionReturnFalse() { 
        PhysicalAbility testAbility = new PhysicalAbility("Test Ability", 1, 1);
        Equipment testEquipment = new Equipment("Test Equipment", EquipmentSlot.LEFT_HAND, Equipment.Effect.HEALTH, 1, testAbility);   

        MapController worldMapController = new MapController();
        Position playerPosition = new Position(0, 0);
        Room playerRoom = new Room(playerPosition, new InteractableInventory(testEquipment));
        worldMapController.add(playerPosition, playerRoom);

        TurnSystem turnSystem = new TurnSystem(io);
        Player player = new Player("name", 1, 1, playerPosition, io);
        player.getInventory().add();


        Mockito.when(io.requestInteractable(playerRoom, player)).thenReturn(testEquipment);
        Mockito.when(io.requestAction(testEquipment, player)).thenReturn(Interactable.InteractableAction.DROP);

        assertFalse(turnSystem.action(player, worldMapController), "Action does not return false.");
        assertTrue(playerRoom.getInteractables().contains(testEquipment), "Room does not have the expected inventory.");
    }

    /*
     * Wear:
    */

    @Test
    public void whenActionWear_thenItemIsInEquippedNotInInvenory() { 
        PhysicalAbility testAbility = new PhysicalAbility("Test Ability", 1, 1);
        Equipment testEquipment = new Equipment("Test Equipment", EquipmentSlot.LEFT_HAND, Equipment.Effect.HEALTH, 1, testAbility);   

        MapController worldMapController = new MapController();
        Position playerPosition = new Position(0, 0);
        Room playerRoom = new Room(playerPosition, new InteractableInventory());
        worldMapController.add(playerPosition, playerRoom);

        TurnSystem turnSystem = new TurnSystem(io);
        Player player = new Player("name", 1, 1, playerPosition, io);
        player.getInventory().add(testEquipment);

        Mockito.when(io.requestInteractable(playerRoom, player)).thenReturn(testEquipment);
        Mockito.when(io.requestAction(testEquipment, player)).thenReturn(Interactable.InteractableAction.WEAR);

        assertTrue(turnSystem.action(player, worldMapController), "action() did not return true");
        assertEquals(testEquipment, player.getEquipmentOnBody().getEquipment(EquipmentSlot.LEFT_HAND), "Character didn't equip the equipment.");
    }

    @Test
    public void whenActionWearEquipmentNotInInventory_thenActionReturnFalse() { 
        PhysicalAbility testAbility = new PhysicalAbility("Test Ability", 1, 1);
        Equipment testEquipment = new Equipment("Test Equipment", EquipmentSlot.LEFT_HAND, Equipment.Effect.HEALTH, 1, testAbility);   

        MapController worldMapController = new MapController();
        Position playerPosition = new Position(0, 0);
        Room playerRoom = new Room(playerPosition, new InteractableInventory(testEquipment));
        worldMapController.add(playerPosition, playerRoom);

        TurnSystem turnSystem = new TurnSystem(io);
        Player player = new Player("name", 1, 1, playerPosition, io);

        Mockito.when(io.requestInteractable(playerRoom, player)).thenReturn(testEquipment);
        Mockito.when(io.requestAction(testEquipment, player)).thenReturn(Interactable.InteractableAction.WEAR);

        assertFalse(turnSystem.action(player, worldMapController), "action() did not return true");
        assertTrue(playerRoom.getInteractables().contains(testEquipment), "Room does not have the expected inventory.");
        assertNotEquals(testEquipment, player.getEquipmentOnBody().getEquipment(EquipmentSlot.LEFT_HAND), "PLayer does not have the right item equiped");
    }

    @Test
    public void whenActionWearEquipmentOnBodySlotTaken_thenActionReturnFalse() { 
        PhysicalAbility testAbility = new PhysicalAbility("Test Ability", 1, 1);
        Equipment testEquipmentOne = new Equipment("Test Equipment One", EquipmentSlot.LEFT_HAND, Equipment.Effect.HEALTH, 1, testAbility);   
        Equipment testEquipmentTwo = new Equipment("Test Equipment Two", EquipmentSlot.LEFT_HAND, Equipment.Effect.HEALTH, 1, testAbility);   

        MapController worldMapController = new MapController();
        Position playerPosition = new Position(0, 0);
        Room playerRoom = new Room(playerPosition, new InteractableInventory());
        worldMapController.add(playerPosition, playerRoom);

        TurnSystem turnSystem = new TurnSystem(io);
        Player player = new Player("name", 1, 1, playerPosition, io);
        player.equip(testEquipmentOne);

        Mockito.when(io.requestInteractable(playerRoom, player)).thenReturn(testEquipmentTwo);
        Mockito.when(io.requestAction(testEquipmentTwo, player)).thenReturn(Interactable.InteractableAction.WEAR);

        assertFalse(turnSystem.action(player, worldMapController), "action() did not return true");
        assertTrue(playerRoom.getInteractables().contains(testEquipmentTwo), "Player does not have the expected inventory.");
        assertEquals(testEquipmentOne, player.getEquipmentOnBody().getEquipment(EquipmentSlot.LEFT_HAND), "PLayer does not have the right item equiped");
    }

    /*
     * UnEquip:
    */

    @Test
    public void whenActionUnEquip_thenItemIsInEquippedNotInInvenory() { 
        PhysicalAbility testAbility = new PhysicalAbility("Test Ability", 1, 1);
        Equipment testEquipment = new Equipment("Test Equipment", EquipmentSlot.LEFT_HAND, Equipment.Effect.HEALTH, 1, testAbility);   

        MapController worldMapController = new MapController();
        Position playerPosition = new Position(0, 0);
        Room playerRoom = new Room(playerPosition, new InteractableInventory());
        worldMapController.add(playerPosition, playerRoom);

        TurnSystem turnSystem = new TurnSystem(io);
        Player player = new Player("name", 1, 1, playerPosition, io);
        player.getInventory().add(testEquipment);
        player.equip(testEquipment);

        Mockito.when(io.requestInteractable(playerRoom, player)).thenReturn(testEquipment);
        Mockito.when(io.requestAction(testEquipment, player)).thenReturn(Interactable.InteractableAction.UNEQUIP);

        assertTrue(turnSystem.action(player, worldMapController), "action() did not return true");
        assertEquals(testEquipment, player.getInventory().contains(testEquipment), "Character didn't unequip the equipment.");
    }

    @Test
    public void whenActionUnEquipEquipmentNotOnCharacter_thenActionReturnFalse() { 
        PhysicalAbility testAbility = new PhysicalAbility("Test Ability", 1, 1);
        Equipment testEquipment = new Equipment("Test Equipment", EquipmentSlot.LEFT_HAND, Equipment.Effect.HEALTH, 1, testAbility);   

        MapController worldMapController = new MapController();
        Position playerPosition = new Position(0, 0);
        Room playerRoom = new Room(playerPosition, new InteractableInventory());
        worldMapController.add(playerPosition, playerRoom);

        TurnSystem turnSystem = new TurnSystem(io);
        Player player = new Player("name", 1, 1, playerPosition, io);
        player.getInventory().add(testEquipment);

        Mockito.when(io.requestInteractable(playerRoom, player)).thenReturn(testEquipment);
        Mockito.when(io.requestAction(testEquipment, player)).thenReturn(Interactable.InteractableAction.UNEQUIP);

        assertFalse(turnSystem.action(player, worldMapController), "action() did not return true");
        assertTrue(player.getInventory().contains(testEquipment), "Player does not have the expected inventory.");
    }

    /*
     * Use:
    */

    @Test
    public void whenActionUsePotion_thenItemIsNotInInventoryAndEffectHasApplied() { 
        PotionItem testPotion = new PotionItem("Test Potion", Equipment.Effect.HEALTH, 1);   

        MapController worldMapController = new MapController();
        Position playerPosition = new Position(0, 0);
        Room playerRoom = new Room(playerPosition, new InteractableInventory());
        worldMapController.add(playerPosition, playerRoom);

        TurnSystem turnSystem = new TurnSystem(io);
        Player player = new Player("name", 1, 1, playerPosition, io);
        player.getInventory().add(testPotion);

        Mockito.when(io.requestInteractable(playerRoom, player)).thenReturn(testPotion);
        Mockito.when(io.requestAction(testPotion, player)).thenReturn(Interactable.InteractableAction.USE);
        
        assertTrue(turnSystem.action(player, worldMapController), "action() did not return true");
        assertEquals(Equipment.Effect.HEALTH, player.getEffect(), "Player does not have the expected Effect.");
        assertTrue(player.getInventory().isEmpty(), "Player does not have the expected inventory.");
    }

    @Test
    public void whenActionUseWhenItemNotInInventory_thenActionReturnFalse() { 
        PotionItem testPotion = new PotionItem("Test Potion", Equipment.Effect.HEALTH, 1);   

        MapController worldMapController = new MapController();
        Position playerPosition = new Position(0, 0);
        Room playerRoom = new Room(playerPosition, new InteractableInventory(testPotion));
        worldMapController.add(playerPosition, playerRoom);

        TurnSystem turnSystem = new TurnSystem(io);
        Player player = new Player("name", 1, 1, playerPosition, io);

        Mockito.when(io.requestInteractable(playerRoom, player)).thenReturn(testPotion);
        Mockito.when(io.requestAction(testPotion, player)).thenReturn(Interactable.InteractableAction.USE);
        
        assertFalse(turnSystem.action(player, worldMapController), "action() did not return false");
        //assertEquals(?, player.getEffect(), "Player does not have the expected Health."); This is gonna have to wait until after effect is implemented in character
        assertTrue(playerRoom.getInteractables().contains(testPotion), "Room does not have the expected inventory.");
    }

    //door isn't yet in master branch
    // @Test
    // public void whenActionUseOnLockedDoor_thenDoorIsOpen() { 
    //     MapController worldMapController = new MapController();
    //     Position playerPosition = new Position(0, 0);
    //     Room playerRoom = new Room(playerPosition, new InteractableInventory());
    //     Position otherPosition = new Position(0, 1);
    //     Room otherRoom = new Room(playerPosition, new InteractableInventory());
    //     worldMapController.add(playerPosition, playerRoom);
    //     worldMapController.add(otherPosition, otherRoom);
    //     Door testDoor = new Door(Key.Type.BLUE);
    //     worldMapController.addDoor(playerPosition, CardinalDirection.NORTH, testDoor);

    //     Key testKey = new Key(Key.Type.BLUE);

    //     TurnSystem turnSystem = new TurnSystem(io);
    //     Player player = new Player("name", 1, 1, playerPosition, io);
    //     player.getInventory().add(testKey);

    //     Mockito.when(io.requestInteractable(playerRoom, player)).thenReturn(testDoor);
    //     Mockito.when(io.requestAction(testDoor, player)).thenReturn(Interactable.InteractableAction.USE);
        
    //     assertTrue(turnSystem.action(player, worldMapController), "action() did not return true");
    //     assertEquals(CardinalDirection.NORTH, worldMapController.getAvailableDirections(playerPosition), "Room does not have the expected Routes.");
    // }

    // @Test
    // public void whenActionUseOnOpenDoor_thenDoorIsLocked() { 
    //     MapController worldMapController = new MapController();
    //     Position playerPosition = new Position(0, 0);
    //     Room playerRoom = new Room(playerPosition, new InteractableInventory());
    //     Position otherPosition = new Position(0, 1);
    //     Room otherRoom = new Room(playerPosition, new InteractableInventory());
    //     worldMapController.add(playerPosition, playerRoom);
    //     worldMapController.add(otherPosition, otherRoom);

    //     Door testDoor = new Door(Key.Type.BLUE);
    //     worldMapController.addDoor(playerPosition, CardinalDirection.NORTH, testDoor);

    //     Key testKey = new Key(Key.Type.BLUE);
    //     testDoor.open(testKey);

    //     TurnSystem turnSystem = new TurnSystem(io);
    //     Player player = new Player("name", 1, 1, playerPosition, io);
    //     player.getInventory().add(testKey);

    //     Mockito.when(io.requestInteractable(playerRoom, player)).thenReturn(testDoor);
    //     Mockito.when(io.requestAction(testDoor, player)).thenReturn(Interactable.InteractableAction.USE);
        
    //     assertTrue(turnSystem.action(player, worldMapController), "action() did not return true");
    //     assertTrue(worldMapController.getAvailableDirections(playerPosition).isEmpty(), "Room does not have the expected Routes.");
    // }

    // @Test
    // public void whenActionUseDoorNoKey_thenActionReturnFalse() { 
    //     MapController worldMapController = new MapController();
    //     Position playerPosition = new Position(0, 0);
    //     Room playerRoom = new Room(playerPosition, new InteractableInventory());
    //     Position otherPosition = new Position(0, 1);
    //     Room otherRoom = new Room(playerPosition, new InteractableInventory());
    //     worldMapController.add(playerPosition, playerRoom);
    //     worldMapController.add(otherPosition, otherRoom);
        
    //     Door testDoor = new Door(Key.Type.BLUE);
    //     worldMapController.addDoor(playerPosition, CardinalDirection.NORTH, testDoor);

    //     TurnSystem turnSystem = new TurnSystem(io);
    //     Player player = new Player("name", 1, 1, playerPosition, io);

    //     Mockito.when(io.requestInteractable(playerRoom, player)).thenReturn(testDoor);
    //     Mockito.when(io.requestAction(testDoor, player)).thenReturn(Interactable.InteractableAction.USE);
        
    //     assertFalse(turnSystem.action(player, worldMapController), "action() did not return false");
    //     assertTrue(worldMapController.getAvailableDirections(playerPosition).isEmpty(),"Room does not have the expected Routes.");
    // }

    /*
     * Fight:
    */

    @Test
    public void whenActionFightPlayerWins_thenNpcIsDeadPlayerIsAlive() { 
        PhysicalAbility testAbility = new PhysicalAbility("Test Ability", 1, 0);
        Equipment playerEquipment = new Equipment("Player Equipment", EquipmentSlot.LEFT_HAND, Equipment.Effect.HEALTH, 100, testAbility);
        Equipment npcEquipment = new Equipment("Npc Equipment", EquipmentSlot.LEFT_HAND, Equipment.Effect.HEALTH, 1, testAbility); 

        NPC npc = new NPC("testNPC", 1, 1, new Position(0, 0), io);
        npc.getInventory().add(npcEquipment);
        npc.equip(npcEquipment);

        MapController worldMapController = new MapController();
        Position playerPosition = new Position(0, 0);
        Room playerRoom = new Room(playerPosition, new InteractableInventory(npc));
        worldMapController.add(playerPosition, playerRoom);

        TurnSystem playerTurnSystem = new TurnSystem(io);
        Player player = new Player("name", 100, 100, playerPosition, io);
        player.getInventory().add(playerEquipment);
        player.equip(playerEquipment);

        Mockito.when(io.requestInteractable(playerRoom, player)).thenReturn(npc);
        Mockito.when(io.requestAction(npc, player)).thenReturn(Interactable.InteractableAction.FIGHT);

        Mockito.when(io.requestAbility(npc, player)).thenReturn(testAbility);
        
        assertTrue(playerTurnSystem.action(player, worldMapController), "action() did not return true");
        assertFalse(player.isDead(), "Player is dead");
        assertTrue(npc.isDead(), "Npc isn't dead");
    }

    @Test
    public void whenActionFightNpcWins_thenPlayerIsDeadNpcIsAlive() { 
        PhysicalAbility testAbility = new PhysicalAbility("Test Ability", 1, 0);
        Equipment playerEquipment = new Equipment("Player Equipment", EquipmentSlot.LEFT_HAND, Equipment.Effect.HEALTH, 1, testAbility);
        Equipment npcEquipment = new Equipment("Npc Equipment", EquipmentSlot.LEFT_HAND, Equipment.Effect.HEALTH, 100, testAbility);  

        NPC npc = new NPC("testNPC", 100, 100, new Position(0, 0), io);
        npc.getInventory().add(npcEquipment);
        npc.equip(npcEquipment);

        MapController worldMapController = new MapController();
        Position playerPosition = new Position(0, 0);
        Room playerRoom = new Room(playerPosition, new InteractableInventory(npc));
        worldMapController.add(playerPosition, playerRoom);

        TurnSystem turnSystem = new TurnSystem(io);
        Player player = new Player("name", 1, 1, playerPosition, io);
        player.getInventory().add(playerEquipment);
        player.equip(playerEquipment);

        Mockito.when(io.requestInteractable(playerRoom, player)).thenReturn(npc);
        Mockito.when(io.requestAction(npc, player)).thenReturn(Interactable.InteractableAction.FIGHT);

        Mockito.when(io.requestAbility(npc, player)).thenReturn(testAbility);

        assertTrue(turnSystem.action(player, worldMapController), "action() did not return true");
        assertFalse(npc.isDead(), "Npc is dead");
        assertTrue(player.isDead(), "player isn't dead");
    }

    @Test
    public void whenActionFightPlayerFlee_thenPlayerAndNpcIsAlive() { 
        PhysicalAbility testAbility = new PhysicalAbility("Test Ability", 1, 0);
        Equipment playerEquipment = new Equipment("Player Equipment", EquipmentSlot.LEFT_HAND, Equipment.Effect.HEALTH, 1, testAbility);
        Equipment npcEquipment = new Equipment("Npc Equipment", EquipmentSlot.LEFT_HAND, Equipment.Effect.HEALTH, 1, testAbility);  

        NPC npc = new NPC("testNPC", 100, 1, new Position(0, 0), io);
        npc.getInventory().add(npcEquipment);
        npc.equip(npcEquipment);

        MapController worldMapController = new MapController();
        Position playerPosition = new Position(0, 0);
        Room playerRoom = new Room(playerPosition, new InteractableInventory(npc));
        worldMapController.add(playerPosition, playerRoom);

        TurnSystem turnSystem = new TurnSystem(io);
        Player player = new Player("name", 100, 10000, playerPosition, io);
        player.getInventory().add(playerEquipment);
        player.equip(playerEquipment);

        Mockito.when(io.requestInteractable(playerRoom, player)).thenReturn(npc);
        Mockito.when(io.requestAction(npc, player)).thenReturn(Interactable.InteractableAction.FIGHT);

        Mockito.when(io.requestAbility(npc, player)).thenThrow(IllegalArgumentException.class);

        assertTrue(turnSystem.action(player, worldMapController), "action() did not return true");
        assertFalse(npc.isDead(), "Npc is dead");
        assertFalse(player.isDead(), "player is dead");
    }

    @Test
    public void whenActionFightNpcFlee_thenPlayerAndNpcIsAlive() { 
        PhysicalAbility testAbility = new PhysicalAbility("Test Ability", 1, 0);
        Equipment playerEquipment = new Equipment("Player Equipment", EquipmentSlot.LEFT_HAND, Equipment.Effect.HEALTH, 1, testAbility);
        Equipment npcEquipment = new Equipment("Npc Equipment", EquipmentSlot.LEFT_HAND, Equipment.Effect.HEALTH, 1, testAbility);  

        NPC npc = new NPC("testNPC", 100, 10000, new Position(0, 0), io);
        npc.getInventory().add(npcEquipment);
        npc.equip(npcEquipment);

        MapController worldMapController = new MapController();
        Position playerPosition = new Position(0, 0);
        Room playerRoom = new Room(playerPosition, new InteractableInventory(npc));
        worldMapController.add(playerPosition, playerRoom);

        TurnSystem turnSystem = new TurnSystem(io);
        Player player = new Player("name", 100, 1, playerPosition, io);
        player.getInventory().add(playerEquipment);
        player.equip(playerEquipment);

        Mockito.when(io.requestInteractable(playerRoom, player)).thenReturn(npc);
        Mockito.when(io.requestAction(npc, player)).thenReturn(Interactable.InteractableAction.FIGHT);

        Mockito.when(io.requestAbility(npc, player)).thenThrow(IllegalArgumentException.class);

        assertTrue(turnSystem.action(player, worldMapController), "action() did not return true");
        assertFalse(npc.isDead(), "Npc is dead");
        assertFalse(player.isDead(), "player is dead");
    }




    /*
     * Needs to know how we know if a Character has talked with another character
     * maybe each character has a method called dialog() and it returns a string
     * 
     * Something with quests?
    */

    @Test
    public void whenActionTalkWithNpc_then() { 
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
        Mockito.when(io.requestInteractable(playerRoom, player)).thenReturn(npc);
        Mockito.when(io.requestAction(npc, player)).thenReturn(Interactable.InteractableAction.TALK);

        playerTurnSystem.action(player, worldMapController);
        
        //I don't know how how talking is suppose to work so i don't know how to see if it happened
    }

    
    /*
     * Request Action testing other paths
    */

    @Test
    public void whenActionAsksForInteractableWrongInput_thenActionReturnFalse() { 
        PhysicalAbility testAbility = new PhysicalAbility("Test Ability", 1, 1);
        Equipment testEquipment = new Equipment("Wrong Equipment", EquipmentSlot.LEFT_HAND, Equipment.Effect.HEALTH, 1, testAbility);     

        MapController worldMapController = new MapController();
        Position playerPosition = new Position(0, 0);
        Room playerRoom = new Room(playerPosition, new InteractableInventory(testEquipment));
        worldMapController.add(playerPosition, playerRoom);

        TurnSystem turnSystem = new TurnSystem(io);
        Player player = new Player("name", 100, 100, playerPosition, io);

        Mockito.when(io.requestInteractable(playerRoom, player)).thenThrow(IllegalArgumentException.class);
        
        assertFalse(turnSystem.action(player, worldMapController), "action() did not return false");
        assertTrue(player.getInventory().isEmpty(), "Player does not have the expected inventory.");
        assertTrue(playerRoom.getInteractables().contains(testEquipment), "Room does not have the expected inventory.");
    }


    @Test
    public void whenActionAsksForInteractableReRequestTwoTimes_thenItemIsInCharacterInventoryNotInRoom() { 
        //prob gonna be removed after reformat of equipment
        HashSet<Interactable.InteractableAction> actionSet = new HashSet<>();
        actionSet.add(Interactable.InteractableAction.LOOT);

        PhysicalAbility testAbility = new PhysicalAbility("Test Ability", 1, 1);
        Equipment rightEquipment = new Equipment("Right Equipment One", EquipmentSlot.LEFT_HAND, actionSet, Equipment.Effect.HEALTH, 1, testAbility);
        Equipment wrongEquipment = new Equipment("Wrong Equipment", EquipmentSlot.LEFT_HAND, actionSet, Equipment.Effect.HEALTH, 1, testAbility);  

        MapController worldMapController = new MapController();
        Position playerPosition = new Position(0, 0);
        Room playerRoom = new Room(playerPosition, new InteractableInventory(rightEquipment));
        worldMapController.add(playerPosition, playerRoom);

        TurnSystem turnSystem = new TurnSystem(io);
        Player player = new Player("name", 100, 100, playerPosition, io);


        //need to make requestInteractable() and change requestAction()
        Mockito.when(io.requestInteractable(playerRoom, player)).thenReturn(wrongEquipment);
        Mockito.when(io.requestAnotherInteractble(playerRoom, player)).thenReturn(wrongEquipment, rightEquipment);
        Mockito.when(io.requestAction(rightEquipment, player)).thenReturn(Interactable.InteractableAction.LOOT);

        assertTrue(turnSystem.action(player, worldMapController), "action() did not return true");
        assertTrue(player.getInventory().contains(rightEquipment), "Player does not have the expected inventory.");
        assertTrue(playerRoom.getInteractables().isEmpty(), "Room does not have the expected inventory.");
    } 
    
    @Test
    public void whenActionAsksForInteractableReRequestWrongInput_thenActionReturnFalse() { 
        PhysicalAbility testAbility = new PhysicalAbility("Test Ability", 1, 1);
        Equipment testEquipment = new Equipment("Wrong Equipment", EquipmentSlot.LEFT_HAND, Equipment.Effect.HEALTH, 1, testAbility);     

        MapController worldMapController = new MapController();
        Position playerPosition = new Position(0, 0);
        Room playerRoom = new Room(playerPosition, new InteractableInventory(testEquipment));
        worldMapController.add(playerPosition, playerRoom);

        TurnSystem turnSystem = new TurnSystem(io);
        Player player = new Player("name", 100, 100, playerPosition, io);


        //need to make requestInteractable() and change requestAction()
        Mockito.when(io.requestInteractable(playerRoom, player)).thenReturn(testEquipment);
        Mockito.when(io.requestAnotherInteractble(playerRoom, player)).thenThrow(IllegalArgumentException.class);
        
        assertFalse(turnSystem.action(player, worldMapController), "action() did not return false");
        assertTrue(player.getInventory().isEmpty(), "Player does not have the expected inventory.");
        assertTrue(playerRoom.getInteractables().contains(testEquipment), "Room does not have the expected inventory.");
    }

    @Test
    public void whenActionAsksForActionWrongInput_thenReturnFalse() { 
        PhysicalAbility testAbility = new PhysicalAbility("Test Ability", 1, 1);
        Equipment testEquipment = new Equipment("Test Equipment One", EquipmentSlot.BELT, Equipment.Effect.HEALTH, 1, testAbility);


        MapController worldMapController = new MapController();
        Position playerPosition = new Position(0, 0);
        Room playerRoom = new Room(playerPosition, new InteractableInventory(testEquipment));
        worldMapController.add(playerPosition, playerRoom);

        TurnSystem turnSystem = new TurnSystem(io);
        Player player = new Player("name", 1, 1, playerPosition, io);


        //need to make requestInteractable() and change requestAction()
        Mockito.when(io.requestInteractable(playerRoom, player)).thenReturn(testEquipment);
        Mockito.when(io.requestAction(testEquipment, player)).thenReturn(Interactable.InteractableAction.FIGHT);
        Mockito.when(io.requestAnotherAction(testEquipment, player)).thenThrow(IllegalArgumentException.class);

        assertFalse(turnSystem.action(player, worldMapController), "action() did not return false");
        assertTrue(player.getInventory().isEmpty(), "Player does not have the expected inventory.");
        assertTrue(playerRoom.getInteractables().contains(testEquipment), "Room does not have the expected inventory.");
    }

    @Test
    public void whenActionAsksForActionReRequestTwoTimes_thenItemIsInCharacterInventoryNotInRoom() { 
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
        Mockito.when(io.requestInteractable(playerRoom, player)).thenReturn(testEquipment);
        Mockito.when(io.requestAction(testEquipment, player )).thenReturn(Interactable.InteractableAction.FIGHT);
        Mockito.when(io.requestAnotherAction(testEquipment, player )).thenReturn(Interactable.InteractableAction.USE,Interactable.InteractableAction.LOOT);

        player.getTurnSystem().action(player, worldMapController);
        
        assertTrue(turnSystem.action(player, worldMapController), "action() did not return true");
        assertTrue(player.getInventory().contains(testEquipment), "Player does not have the expected inventory.");
        assertTrue(playerRoom.getInteractables().isEmpty(), "Room does not have the expected inventory.");
    } 

    @Test
    public void whenActionAsksForActionReRequest_thenReturnFalse() { 
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
        Mockito.when(io.requestInteractable(playerRoom, player)).thenReturn(testEquipment);
        Mockito.when(io.requestAction(testEquipment, player)).thenReturn(Interactable.InteractableAction.FIGHT);
        Mockito.when(io.requestAnotherAction(testEquipment, player)).thenThrow(IllegalArgumentException.class);

        assertFalse(turnSystem.action(player, worldMapController), "action() did not return false");
        assertTrue(player.getInventory().isEmpty(), "Player does not have the expected inventory.");
        assertTrue(playerRoom.getInteractables().contains(testEquipment), "Room does not have the expected inventory.");
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
