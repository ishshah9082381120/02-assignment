/*
Welcome to JDoodle!


*/
import java.util.ArrayList;
import java.util.List;

// Class representing a Place in the Petri Net (i.e., a section of the railway)
class Place {
    private String id;
    private int tokens;  // Represents the number of trains in this section

    public Place(String id, int tokens) {
        this.id = id;
        this.tokens = tokens;
    }

    public String getId() {
        return id;
    }

    public int getTokens() {
        return tokens;
    }

    public void addTokens(int tokens) {
        this.tokens += tokens;
    }

    public void removeTokens(int tokens) {
        this.tokens -= tokens;
    }

    @Override
    public String toString() {
        return "Place{" +
                "id='" + id + '\'' +
                ", tokens=" + tokens +
                '}';
    }
}

// Class representing a Transition in the Petri Net (i.e., moving trains between sections)
class Transition {
    private String id;
    private List<Place> inputPlaces = new ArrayList<>();
    private List<Place> outputPlaces = new ArrayList<>();

    public Transition(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void addInputPlace(Place place) {
        inputPlaces.add(place);
    }

    public void addOutputPlace(Place place) {
        outputPlaces.add(place);
    }

    // Fire transition (move tokens from input to output places)
    public void fire() {
        if (canFire()) {
            for (Place place : inputPlaces) {
                place.removeTokens(1);
            }
            for (Place place : outputPlaces) {
                place.addTokens(1);
            }
            System.out.println("Transition " + id + " fired!");
        } else {
            System.out.println("Transition " + id + " cannot fire.");
        }
    }

    // Check if the transition can fire
    public boolean canFire() {
        for (Place place : inputPlaces) {
            if (place.getTokens() == 0) {
                return false;
            }
        }
        return true;
    }
}

// Main class to simulate the Petri Net for a train moving through track sections
public class MyClass {  // Change this line to match your filename
    public static void main(String[] args) {
        // Create places (representing track sections)
        Place section1 = new Place("Section 1", 1); // Start with 1 train
        Place section2 = new Place("Section 2", 0);
        Place section3 = new Place("Section 3", 0);

        // Create transitions (representing train movement between sections)
        Transition moveToSection2 = new Transition("Move to Section 2");
        moveToSection2.addInputPlace(section1);
        moveToSection2.addOutputPlace(section2);

        Transition moveToSection3 = new Transition("Move to Section 3");
        moveToSection3.addInputPlace(section2);
        moveToSection3.addOutputPlace(section3);

        // Initial State
        System.out.println("Initial State:");
        System.out.println(section1);
        System.out.println(section2);
        System.out.println(section3);
        System.out.println();

        // Simulate the Petri Net
        moveToSection2.fire(); // Move train from Section 1 to Section 2
        System.out.println("After moving to Section 2:");
        System.out.println(section1);
        System.out.println(section2);
        System.out.println(section3);
        System.out.println();

        moveToSection3.fire(); // Move train from Section 2 to Section 3
        System.out.println("After moving to Section 3:");
        System.out.println(section1);
        System.out.println(section2);
        System.out.println(section3);
    }
}


