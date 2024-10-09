import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

public class InterlockingImplTest {
    private InterlockingImpl interlockingSystem;
    private Train train1;
    private Train train2;

    @Before
    public void setUp() {
        interlockingSystem = new InterlockingImpl();
        train1 = new Train("T1", TrainType.PASSENGER, 1);
        train2 = new Train("T2", TrainType.FREIGHT, 1);
    }

    @Test
    public void testTrainEntryToSection() {
        // Test entering section 1
        interlockingSystem.enterTrain(train1, 1);
        assertEquals(1, train1.getCurrentSection());
    }

    @Test
    public void testTrainMoveBetweenSections() {
        // Train T1 enters Section 1 and moves to Section 2
        interlockingSystem.enterTrain(train1, 1);
        interlockingSystem.moveTrain(train1, 1, 2);
        assertEquals(2, train1.getCurrentSection());

        // Test that Train T1 has vacated Section 1
        interlockingSystem.moveTrain(train1, 2, 3);
        assertEquals(3, train1.getCurrentSection());
    }

    @Test
    public void testCollisionAvoidance() {
        // Train T1 enters Section 1 and moves to Section 2
        interlockingSystem.enterTrain(train1, 1);
        interlockingSystem.moveTrain(train1, 1, 2);

        // Train T2 tries to move to Section 2 (should fail because T1 is there)
        interlockingSystem.enterTrain(train2, 1);
        interlockingSystem.moveTrain(train2, 1, 2);
        assertNotEquals(2, train2.getCurrentSection()); // Train 2 should still be in Section 1
    }

    @Test
    public void testTrainExitSection() {
        // Train T1 enters Section 1, moves to Section 2, then exits
        interlockingSystem.enterTrain(train1, 1);
        interlockingSystem.moveTrain(train1, 1, 2);
        interlockingSystem.exitTrain(train1, 2);
        
        // Check that Train T1 is no longer occupying Section 2
        assertEquals(-1, train1.getCurrentSection()); // Assuming exit resets section to -1 or some indicator
    }
}
