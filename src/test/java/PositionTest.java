import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

public class PositionTest {

    static final int DEFAULT_X = 0;
    static final int DEFAULT_Y = 0;

    static final int DEFAULT_NEGATIVE_X = -5;
    static final int DEFAULT_NEGATIVE_Y = -5;

    static final int DEFAULT_POSITIVE_X = 5;
    static final int DEFAULT_POSITIVE_Y = 5;

    @Test
    @DisplayName("assert right X-value when constructing Position")
    void assertConstructorSetsCorrectX(){
        Position pos = new Position(DEFAULT_X, DEFAULT_Y);
        assertEquals(DEFAULT_X, pos.getX());
    }

    @Test
    @DisplayName("assert right Y-value when constructing Position")
    void assertConstructorSetsCorrectY(){
        Position pos = new Position(DEFAULT_X, DEFAULT_Y);
        assertEquals(DEFAULT_Y, pos.getY());
    }

    @Test
    @DisplayName("assert two Positions where p1.x == p2.y and p1.y == p2.x returns different HashCodes")
    void assertDifferentHashCodes() {
        Position pos1 = new Position(DEFAULT_POSITIVE_X, DEFAULT_Y);
        Position pos2 = new Position(DEFAULT_Y, DEFAULT_POSITIVE_X);
        assertNotEquals(pos1.hashCode(), pos2.hashCode());
    }


}
