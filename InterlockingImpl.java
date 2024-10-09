import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

// Enum to represent the train types
enum TrainType {
    PASSENGER,
    FREIGHT
}

// Class representing a Train
class Train {
    private String id;
    private TrainType type;
    private int currentSection;

    public Train(String id, TrainType type, int section) {
        this.id = id;
        this.type = type;
        this.currentSection = section;
    }

    public String getId() { return id; }
    public TrainType getType() { return type; }
    public int getCurrentSection() { return currentSection; }
    public void setCurrentSection(int section) { this.currentSection = section; }
}

// Class representing a Track Section
class TrackSection {
    private int sectionId;
    private boolean isOccupied;
    private Train currentTrain;

    public TrackSection(int sectionId) {
        this.sectionId = sectionId;
        this.isOccupied = false;
        this.currentTrain = null;
    }

    public synchronized boolean enter(Train train) {
        if (!isOccupied) {
            this.isOccupied = true;
            this.currentTrain = train;
            train.setCurrentSection(this.sectionId);
            System.out.println("Train " + train.getId() + " has entered Section " + sectionId);
            return true;
        }
        return false;
    }

    public synchronized void exit() {
        if (currentTrain != null) {
            System.out.println("Train " + currentTrain.getId() + " is leaving Section " + sectionId);
            this.isOccupied = false;
            this.currentTrain = null;
        }
    }

    public boolean isOccupied() {
        return isOccupied;
    }

    public int getSectionId() {
        return sectionId;
    }
}

// Interface for the interlocking system
interface Interlocking {
    void enterTrain(Train train, int section);
    void exitTrain(Train train, int section);
    void moveTrain(Train train, int fromSection, int toSection);
}

// Implementation of the interlocking system
public class InterlockingImpl implements Interlocking {
    private Map<Integer, TrackSection> trackMap = new HashMap<>();

    // Constructor to initialize track sections
    public InterlockingImpl() {
        // Initialize track sections (1 to 11 based on your description)
        for (int i = 1; i <= 11; i++) {
            trackMap.put(i, new TrackSection(i));
        }
    }

    @Override
    public synchronized void enterTrain(Train train, int section) {
        TrackSection trackSection = trackMap.get(section);
        if (trackSection != null && trackSection.enter(train)) {
            System.out.println("Train " + train.getId() + " entered Section " + section);
        } else {
            System.out.println("Train " + train.getId() + " could not enter Section " + section + " (Occupied)");
        }
    }

    @Override
    public synchronized void exitTrain(Train train, int section) {
        TrackSection trackSection = trackMap.get(section);
        if (trackSection != null) {
            trackSection.exit();
        }
    }

    @Override
    public synchronized void moveTrain(Train train, int fromSection, int toSection) {
        TrackSection currentSection = trackMap.get(fromSection);
        TrackSection nextSection = trackMap.get(toSection);

        if (currentSection != null && nextSection != null) {
            if (currentSection.isOccupied() && !nextSection.isOccupied()) {
                currentSection.exit();
                nextSection.enter(train);
                System.out.println("Train " + train.getId() + " moved from Section " + fromSection + " to Section " + toSection);
            } else {
                System.out.println("Train " + train.getId() + " cannot move from Section " + fromSection + " to Section " + toSection + " (Next section occupied or current section empty)");
            }
        }
    }

    public static void main(String[] args) {
        InterlockingImpl interlockingSystem = new InterlockingImpl();
        
        // Create some trains
        Train train1 = new Train("T1", TrainType.PASSENGER, 1);
        Train train2 = new Train("T2", TrainType.FREIGHT, 1);

        // Simulate train operations
        interlockingSystem.enterTrain(train1, 1);
        interlockingSystem.moveTrain(train1, 1, 2);
        interlockingSystem.enterTrain(train2, 1);
        interlockingSystem.moveTrain(train2, 1, 2); // This should fail as Section 2 is occupied
        interlockingSystem.exitTrain(train1, 2);
        interlockingSystem.moveTrain(train2, 1, 2); // Now should succeed
    }
}
