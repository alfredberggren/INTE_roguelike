
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import java.util.HashSet;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class TurnSystemActionTest {

    private MapController worldMapController;
    private Position playerPosition;
    private Room playerRoom;
    private TurnSystem turnSystem;
    private Player player;

    @Mock 
    IO io;

    @BeforeEach
    public void init(){
        worldMapController = new MapController();
        playerPosition = new Position(0, 0);
        playerRoom = new Room(playerPosition, new InteractableInventory());
        worldMapController.add(playerPosition, playerRoom);

        turnSystem = new TurnSystem(io);
        player = new Player("name", 1, 1, playerPosition, io);
    }

    
    /*
     *
     *  Action Tests 
     *
    */


    @Test
    public void whenActionAndNoActionAvailable_thenActionReturnFalse() { // maybe it should be IAE
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

        NPC testNpc = new NPC("npc", 1, 1, playerPosition, io); 
        testNpc.decreaseHealth(1);
        testNpc.getInventory().add(rightEquipment);

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

        NPC testNpc = new NPC("npc", 1, 1, playerPosition, io); 
        testNpc.decreaseHealth(1);
        testNpc.getInventory().add(testEquipment);

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

        NPC testNpc = new NPC("npc", 1, 1, playerPosition, io); 
        testNpc.decreaseHealth(1);
        testNpc.getInventory().add(rightEquipment);

        Mockito.when(io.requestInteractable(playerRoom, player)).thenReturn(testNpc);
        Mockito.when(io.requestAction(testNpc, player)).thenReturn(Interactable.InteractableAction.LOOT);
        Mockito.when(io.requestInteractable(testNpc, player)).thenReturn(wrongEquipment);
        Mockito.when(io.requestAnotherInteractble(testNpc, player)).thenThrow(IllegalArgumentException.class);

        assertFalse(turnSystem.action(player, worldMapController), "action() did not return false when wrong input was given");
        assertTrue(testNpc.getInventory().contains(rightEquipment), "Npc doesn't have the Expected inventory");
    }

    @Test
    public void whenActionLootFromAliveCharacter_thenActionReturnFalse() {   

        NPC testNpc = new NPC("npc", 1, 1, playerPosition, io); 
        testNpc.decreaseHealth(1);

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

        playerRoom.getInteractables().add(testEquipment);

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

        playerRoom.getInteractables().add(testEquipment);

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

        player.getInventory().add(testEquipmentOne);
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
    public void whenActionUnEquip_thenItemIsInInventoryNotEquipped() { 
        PhysicalAbility testAbility = new PhysicalAbility("Test Ability", 1, 1);
        Equipment testEquipment = new Equipment("Test Equipment", EquipmentSlot.LEFT_HAND, Equipment.Effect.HEALTH, 1, testAbility);   

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

        player.getInventory().add(playerEquipment);
        player.equip(playerEquipment);

        Mockito.when(io.requestInteractable(playerRoom, player)).thenReturn(npc);
        Mockito.when(io.requestAction(npc, player)).thenReturn(Interactable.InteractableAction.FIGHT);

        Mockito.when(io.requestAbility(npc, player)).thenReturn(testAbility);
        
        assertTrue(turnSystem.action(player, worldMapController), "action() did not return true");
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

        //need to make requestInteractable() and change requestAction()
        Mockito.when(io.requestInteractable(playerRoom, player)).thenReturn(npc);
        Mockito.when(io.requestAction(npc, player)).thenReturn(Interactable.InteractableAction.TALK);

        turnSystem.action(player, worldMapController);
        
        //I don't know how how talking is suppose to work so i don't know how to see if it happened
    }

    
    /*
     * Request Action testing other paths
    */

    @Test
    public void whenActionAsksForInteractableWrongInput_thenActionReturnFalse() { 
        PhysicalAbility testAbility = new PhysicalAbility("Test Ability", 1, 1);
        Equipment testEquipment = new Equipment("Wrong Equipment", EquipmentSlot.LEFT_HAND, Equipment.Effect.HEALTH, 1, testAbility);     

        playerRoom.getInteractables().add(testEquipment);

        Mockito.when(io.requestInteractable(playerRoom, player)).thenThrow(IllegalArgumentException.class);
        
        assertFalse(turnSystem.action(player, worldMapController), "action() did not return false");
        assertTrue(player.getInventory().isEmpty(), "Player does not have the expected inventory.");
        assertTrue(playerRoom.getInteractables().contains(testEquipment), "Room does not have the expected inventory.");
    }


    @Test
    public void whenActionAsksForInteractableReRequestTwoTimes_thenItemIsInCharacterInventoryNotInRoom() { 
        PhysicalAbility testAbility = new PhysicalAbility("Test Ability", 1, 1);
        Equipment rightEquipment = new Equipment("Right Equipment One", EquipmentSlot.LEFT_HAND, Equipment.Effect.HEALTH, 1, testAbility);
        Equipment wrongEquipment = new Equipment("Wrong Equipment", EquipmentSlot.LEFT_HAND, Equipment.Effect.HEALTH, 1, testAbility);
        
        playerRoom.getInteractables().add(rightEquipment);

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
        Equipment rightEquipment = new Equipment("Right Equipment One", EquipmentSlot.LEFT_HAND, Equipment.Effect.HEALTH, 1, testAbility);
        Equipment wrongEquipment = new Equipment("Wrong Equipment", EquipmentSlot.LEFT_HAND, Equipment.Effect.HEALTH, 1, testAbility);
        
        playerRoom.getInteractables().add(rightEquipment);

        Mockito.when(io.requestInteractable(playerRoom, player)).thenReturn(wrongEquipment);
        Mockito.when(io.requestAnotherInteractble(playerRoom, player)).thenThrow(IllegalArgumentException.class);
        
        assertFalse(turnSystem.action(player, worldMapController), "action() did not return false");
        assertTrue(player.getInventory().isEmpty(), "Player does not have the expected inventory.");
        assertTrue(playerRoom.getInteractables().contains(rightEquipment), "Room does not have the expected inventory.");
    }

    @Test
    public void whenActionAsksForActionWrongInput_thenReturnFalse() { 
        PhysicalAbility testAbility = new PhysicalAbility("Test Ability", 1, 1);
        Equipment testEquipment = new Equipment("Test Equipment One", EquipmentSlot.BELT, Equipment.Effect.HEALTH, 1, testAbility);

        Mockito.when(io.requestInteractable(playerRoom, player)).thenReturn(testEquipment);
        Mockito.when(io.requestAction(testEquipment, player)).thenThrow(IllegalArgumentException.class);

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

        Mockito.when(io.requestInteractable(playerRoom, player)).thenReturn(testEquipment);
        Mockito.when(io.requestAction(testEquipment, player )).thenReturn(Interactable.InteractableAction.FIGHT);
        Mockito.when(io.requestAnotherAction(testEquipment, player )).thenReturn(Interactable.InteractableAction.FIGHT,Interactable.InteractableAction.LOOT);
        
        assertTrue(turnSystem.action(player, worldMapController), "action() did not return true");
        assertTrue(player.getInventory().contains(testEquipment), "Player does not have the expected inventory.");
        assertTrue(playerRoom.getInteractables().isEmpty(), "Room does not have the expected inventory.");
    } 

    @Test
    public void whenActionAsksForActionReRequestWrongInput_thenReturnFalse() { 
        PhysicalAbility testAbility = new PhysicalAbility("Test Ability", 1, 1);
        Equipment testEquipment = new Equipment("Test Equipment One", EquipmentSlot.BELT, Equipment.Effect.HEALTH, 1, testAbility);

        Mockito.when(io.requestInteractable(playerRoom, player)).thenReturn(testEquipment);
        Mockito.when(io.requestAction(testEquipment, player)).thenReturn(Interactable.InteractableAction.FIGHT);
        Mockito.when(io.requestAnotherAction(testEquipment, player)).thenThrow(IllegalArgumentException.class);

        assertFalse(turnSystem.action(player, worldMapController), "action() did not return false");
        assertTrue(player.getInventory().isEmpty(), "Player does not have the expected inventory.");
        assertTrue(playerRoom.getInteractables().contains(testEquipment), "Room does not have the expected inventory.");
    }

}

