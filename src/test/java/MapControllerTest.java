import org.junit.jupiter.api.*;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;

import static org.junit.jupiter.api.Assertions.*;

public class MapControllerTest {
    
    private MapController map;
    private Position centerPos, northPos, eastPos, southPos, westPos;
    private Room centerRoom, northRoom, eastRoom, southRoom, westRoom;
    private Character character;


    @BeforeEach
    public void init(){
        map = new MapController();

        centerPos = new Position(0, 0);
        northPos = new Position(0, 1);
        eastPos = new Position(1, 0);
        southPos = new Position(0, -1);
        westPos = new Position(-1, 0);

        centerRoom = new Room(centerPos);
        northRoom = new Room(northPos);
        eastRoom = new Room(eastPos);
        southRoom = new Room(southPos);
        westRoom = new Room(westPos);

        map.add(centerPos, centerRoom);
        map.add(northPos, northRoom);
        map.add(eastPos, eastRoom);
        map.add(southPos, southRoom);
        map.add(westPos, westRoom);

        character = new Character("null", 1, 1, 0, centerPos, new TextIO());
        centerRoom.getInteractables().add(character);
    }


    @Test
    public void whenCardinalDirectionIsNorth_thenCharacterIsMovedNorth(){
        map.moveCharacter(CardinalDirection.NORTH, character);
        assertEquals(northPos, character.getPosition());
    }

    @Test
    public void whenCardinalDirectionIsEast_thenCharacterIsMovedEast(){
        map.moveCharacter(CardinalDirection.EAST, character);
        assertEquals(eastPos, character.getPosition());
    }

    @Test
    public void whenCardinalDirectionIsSouth_thenCharacterIsMovedSouth(){
        map.moveCharacter(CardinalDirection.SOUTH, character);
        assertEquals(southPos, character.getPosition());
    }

    @Test
    public void whenCardinalDirectionIsWest_thenCharacterIsMovedWest(){
        map.moveCharacter(CardinalDirection.WEST, character);
        assertEquals(westPos, character.getPosition());
    }

    @Test
    public void whenCharacterIsMoved_thenCharacterIsInNewRoom(){
        map.moveCharacter(CardinalDirection.NORTH, character);
        assertTrue(northRoom.getInteractables().contains(character));
    }

    @Test
    public void whenCharacterIsMoved_thenCharacterIsNotInFormerRoom(){
        map.moveCharacter(CardinalDirection.NORTH, character);
        assertFalse(centerRoom.getInteractables().contains(character));
    }
}
