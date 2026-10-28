// Curated by Mathew Kim and Chandler Phillips

// REFERENCE: https://www.baeldung.com/java-observer-pattern
import java.util.Scanner; //https://www.w3schools.com/java/java_user_input.asp: used for user input
import java.lang.Thread;
import java.util.Observer;
import java.util.Observable;
import java.io.ByteArrayOutputStream;

// TODO: THIS IS MADE FOR FNCD TO RUN THE SIMULATION. REPLACE WITH A MENU. SEE UML FOR MORE DETAILS.

@Deprecated
public class Menu /*implements Observer*/ {
    // make a main function to test the code
    private Logger logOn;
    public void update(Observable o, Object arg) {
        System.out.println("Updating");
    }

    public static void playUNO() throws InterruptedException{
        //set up the game
        PRINT p=new PRINT();//FAST WAY TO PRINT STUFF
        Activities game = new Activities();
        //: Number of players
        Scanner takeInStuff = new Scanner(System.in);
        p.o("Starting Up UNO!");
        p.o("----------------");
        p.o("How many opponents would you like to play Uno? (Pick a number 3-9)");
        int oppNum = takeInStuff.nextInt();
        takeInStuff.nextLine();
        while(oppNum < 3 || oppNum > 9){
            p.o("Please pick a number between 3-9");
            oppNum = takeInStuff.nextInt();
            takeInStuff.nextLine();
        }
        p.blank();
        p.o("You have chose to play against "+ oppNum+" opponents");
        p.blank();
        p.o("what difficulty would you like your opponents to be?(1-3)");
        p.o("1. Easy");
        p.o("2. Intermediate");
        p.o("3. Expert");
        int difficulty = takeInStuff.nextInt();
        p.blank();
        while(difficulty!=1 && difficulty!=2 && difficulty!=3){
            p.o("Please pick a number between 1-3");
            difficulty = takeInStuff.nextInt();
            p.blank();
            //p.o(Integer.toString(difficulty));
        }
        if(difficulty == 1){
            p.o("You have chosen 1: CPU difficulty is set to Easy");
        }
        else if(difficulty == 2){
            p.o("You have chosen 2: CPU difficulty is set to Intermediate");
        }
        else{
            p.o("You have chosen 3: CPU difficulty is set to Expert");
        }  
        p.blank();
        p.o("Let's Get This Game Started!");
        p.blank();
        Long Time = (long) 2000;
        Thread.sleep(Time);
        game.START(oppNum,difficulty, takeInStuff);
        boolean gametime = false;
        while (gametime == false){
            Thread.sleep(Time);
            p.o("<<<<<<<<<<<<<<<<<<<<<<<<<<<START TURN<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<");
            game.EachTurn(takeInStuff);
            p.o(">>>>>>>>>>>>>>>>>>>>>>>>>>>>END TURN >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
        }
    }
}