// REFERENCE: https://www.geeksforgeeks.org/java-singleton-design-pattern-practices-examples/
public class Tracker {
    // Lazy singleton instance
    private static Tracker instance = null;

    // initialize tracker
    public static Tracker getInstance() {
        if (instance == null) {
            instance = new Tracker();
        }
        return instance;
    }

    // WARNING: MAY CREATE BUNCHES OF FILES. THIS RECORDS EVERY TURN!!!
    public void turnTracker(Activities Activities) {
        // Take data and print it to the terminal
        System.out.println("Tracker: " + Activities.getTurnTracker());
        System.out.println();
    }
}
