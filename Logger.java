// REFERENCES: https://www.tutorialspoint.com/how-to-implement-reactive-streams-using-flow-api-in-java-9#:~:text=JavaObject%20Oriented%20ProgrammingProgramming,end-user%20API%20like%20RxJava.
// REFERENCES: https://www.w3schools.com/java/java_files_create.asp
// REFERENCES: https://www.geeksforgeeks.org/java-singleton-design-pattern-practices-examples/

import java.util.Observable;
import java.io.File;
import java.io.FileWriter; 
import java.io.IOException;

/*HERE IS OUR OBSERVER PATTERN
An observer pattern basically allows some communcation between different objects. In this case: 
In this pattern we have a subject(Logger) which writes the outputs for each day (such as washing, reparing, selling, etc) into seperate files being able to be observed
We also have a tracker in order to keep track of cumulative data of pay total to workers and the money earned by the FNCD from selling cars
Observer patterns support loose coupling and can ve a pull or push appriach to get events to subscribers.
 */


 /* HERE IS OUR SINGLETON PATTERN AS WELL WITH EAGER INSTANCIATION:
  * We can tell its a singleton pattern from its private instance variable to store the single instance
  * It is eager instanciation since the ovject is created in advance and already ready to be used
  * just like its name suggests, it is used to ensure that only one object instance of a class is instantiated and accessed
  */
@Deprecated
public class Logger extends Observable {
    // Eager singleton instance
    private static Logger instance = new Logger(null);
    
    private Tracker tracker = Tracker.getInstance();
    private String fileName;
    private String inputs;

    public Logger(String inputs){
        this.inputs = inputs;
    }

    public String getInputs(){
        return inputs;
    }
    
    public void setInputs(String Input){
        this.inputs = Input;
        setChanged();
        notifyObservers();
    }

    // Create a txt file 
    public void fileCreator(int iterator) {
        try {
            File fileCreated = new File("Logger-" + iterator + ".txt");
            fileName = fileCreated.getName();
            if (fileCreated.createNewFile()) {
                System.out.println("File created: " + fileCreated.getName());
            } else {
                System.out.println("File already exists.");
            }
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }

    public void fileWriter(String input) {
        try {
            FileWriter myWriter = new FileWriter(fileName);
            myWriter.write(input);
            myWriter.close();
            System.out.println("Successfully wrote to the file.");
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }

    public static Logger getInstance(){
        return instance;
    }

    // TODO: REPLACE MONEYTRACKER WITH POINTS
    public void Tracker(Activities Activities) {
        tracker.turnTracker(Activities);
    }
}