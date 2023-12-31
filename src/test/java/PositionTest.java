import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class PositionTest {

    static final int DEFAULT_X = 0;
    static final int DEFAULT_Y = 0;

    static final int DEFAULT_NEGATIVE_X = -5;
    static final int DEFAULT_NEGATIVE_Y = -5;

    static final int DEFAULT_POSITIVE_X = 5;
    static final int DEFAULT_POSITIVE_Y = 5;


    //TODO: might need parameterized tests for both X and Y
    @Test
    @DisplayName("assert right X-value when constructing Position")
    public void testConstructorSetsCorrectX(){
        Position pos = new Position(DEFAULT_X, DEFAULT_Y);
        assertEquals(DEFAULT_X, pos.getX());
    }

    @Test
    @DisplayName("assert right Y-value when constructing Position")
    public void testConstructorSetsCorrectY(){
        Position pos = new Position(DEFAULT_X, DEFAULT_Y);
        assertEquals(DEFAULT_Y, pos.getY());
    }

    @Test
    @DisplayName("assert two Positions where p1.x == p2.y and p1.y == p2.x returns different HashCodes")
    public void testDifferentHashCodes() {
        Position pos1 = new Position(DEFAULT_POSITIVE_X, DEFAULT_Y);
        //Intentionally placing Y-value into X and vice versa, for testings' sake
        Position pos2 = new Position(DEFAULT_Y, DEFAULT_POSITIVE_X);
        assertNotEquals(pos1.hashCode(), pos2.hashCode());
    }

    @Test
    @DisplayName("assert two positions with same X&Y equals true")
    public void testEqualsMethodSameValues() {
        Position pos1 = new Position(DEFAULT_NEGATIVE_X, DEFAULT_NEGATIVE_Y);
        Position pos2 = new Position(DEFAULT_NEGATIVE_X, DEFAULT_NEGATIVE_Y);
        assertEquals(true, pos1.equals(pos2));
    }

    @Test
    @DisplayName("assert two positions with different X&Y equals false")
    public void testEqualsMethodDifferentValues() {
        Position pos1 = new Position(DEFAULT_NEGATIVE_X, DEFAULT_NEGATIVE_Y);
        Position pos2 = new Position(DEFAULT_POSITIVE_X, DEFAULT_NEGATIVE_Y);
        assertEquals(false, pos1.equals(pos2));
    }

    @Test
    @DisplayName("assert tanslate changes the position if the values are positive")
    public void testTranslateMethodPositiveValues() {
        Position pos1 = new Position(DEFAULT_X, DEFAULT_Y);
        Position pos2 = new Position(DEFAULT_NEGATIVE_X, DEFAULT_NEGATIVE_Y);
        pos2.translate(DEFAULT_POSITIVE_X, DEFAULT_POSITIVE_Y);
        assertEquals(pos1, pos2);
    }

    @Test
    @DisplayName("assert tanslate changes the position if the values are positive")
    public void testTranslateMethodNegativeValues() {
        Position pos1 = new Position(DEFAULT_X, DEFAULT_Y);
        Position pos2 = new Position(DEFAULT_POSITIVE_X, DEFAULT_POSITIVE_Y);
        pos2.translate(DEFAULT_NEGATIVE_X, DEFAULT_NEGATIVE_Y);
        assertEquals(pos1, pos2);
    }

}
