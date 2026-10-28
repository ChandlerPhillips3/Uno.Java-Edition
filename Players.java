import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;
import java.util.List;


//resource for names: https://github.com/hadley/data-baby-names/blob/master/baby-names.csv

// TODO: THIS IS MADE FOR STAFF FROM FNCD. CHANGE WITH ADDING USER CONTROLS AS WELL AS CPU CONTROLS. SEE UML FOR MORE DETAILS.
public class Players {
    public String name;
    public int age;
    public int numWins;
    public String favColorCard;
    public ArrayList <Cards> hand = new ArrayList <Cards>();

    //Getters and Setters:
    public String getName(){
        return name;
    }
    public void setName(String holdName){
        this.name=holdName;
    }

    public int getAge(){
        return age;
    }
    public void setAge(int num){
        this.age=num;
    }

    public int getNumWins(){
        return numWins;
    }
    public void setNumWins(int num){
        this.numWins=num;
    }

    public String getFavColorCard(){
        return favColorCard;
    }
    public void setFavColorCard(String fav){
        this.favColorCard=fav;
    }

   public void seeCardCount(){
        System.out.println(name + " has a total of " + hand.size()+ " cards.");
   }
   public int getCardCount(){
        return hand.size();
   }
   public void getBio(){
        //System.out.println("Name: "+name+ " |Age:" + age + " |Favorite Color Card:" + favColorCard + " |Number of Wins:" + numWins);
        System.out.printf("|Name: %12s|Age: %3s|Favorite Color Card: %8s|Number of wins: %4s|%n", name, age, favColorCard,numWins);
   }

   //Helped with debugging but also used for just the player!!!!
   public void seeCards(){
        if(hand.size() == 0){
            System.out.println(name +" currently has 0 cards in hand.");
        }
        else{
            //https://www.theserverside.com/blog/Coffee-Talk-Java-News-Stories-and-Opinions/How-to-use-Java-printf-to-format-output Resource for printing cards in a nice format
            System.out.println(name+ "'s cards");
            for( int j=0; j< hand.size();j++){//top and bottom of cards
                // System.out.printf("---------------------------%n");//ask mathew how cards can access color and number
                System.out.printf("|--------------------|%n");//18 size char per line
                System.out.printf("| %-18s |%n", hand.get(j).type);
                System.out.printf("| %18s |%n"," ");
                System.out.printf("| %7s %-10s |      %2s %n", " ",hand.get(j).cardInfo, "("+(j+1)+")");
                System.out.printf("| %18s |%n"," ");
                System.out.printf("| %18s |%n", hand.get(j).type);
                System.out.printf("|--------------------|%n");//18 size char per line
                System.out.printf("%n");
            }
        }
   }

    public void getRidOfCard(Cards This){
        hand.remove(This);
    }

   public void start(ArrayList <Cards> deck){
        //EVERYONE GETS 7 to start!
        for(int i=0;i<7;i++){
            hand.add(deck.get(0));
            deck.remove(0);
        }
   }

   public void drawCard(ArrayList <Cards> deck){
        hand.add(deck.get(0));
        deck.remove(0);
   }
}

class CPU extends Players{
    //used for naming:
    DifficultyBehavior Difficulty;
    public CPU(int Difficult) {
        //used for naming convention,
        this.setName(nameGenerator());
        //generate age: 
        Random rand = new Random(); 
        int upperbound = 40;
        int ageHolder = 10+ rand.nextInt(upperbound); 
        this.setAge(ageHolder);
        //each time initialized set number of wins to 0:
        this.setNumWins(0);
        //set favorite color card (used in playing algorithims )
        int range = 1+ rand.nextInt(4);
        switch (range) {
        case 1:
            this.setFavColorCard("Green");
            break;
        case 2:
            this.setFavColorCard("Blue");
            break;
        case 3:
            this.setFavColorCard("Red");
            break;
        case 4:
            this.setFavColorCard("Yellow");
            break;
        }
        //if 3: difficulty = Expert, if 2: difficulty= intermediate, (1) or anything else difficulty = easy:
        if(Difficult==3){
            this.Difficulty=new Expert();
        }
        else if(Difficult==2){
            this.Difficulty=new Intermediate();
        }
        else{
            this.Difficulty=new Easy();
        }
    }
    
    public Cards playing(Cards LastPlayed, CPU thisguy){
        Cards card=Difficulty.playCard(LastPlayed, thisguy);
        return card;
    }

    public String nameGenerator() {
        // grab a random name from monstertruck.csv by accessing the file with scanner
        Scanner scanner = new Scanner(getClass().getResourceAsStream("names.csv"));
        List<String> names = new ArrayList<String>();
        while (scanner.hasNextLine()) {
            names.add(scanner.nextLine());
        }
        scanner.close();
        // return a random name from the list
        return names.get((int) (Math.random() * names.size()));
    }
}

class MainPlayer extends Players {
    private int userCommand = 0;

    public void setUserName(Scanner input) {
        input.nextLine();
        System.out.println("Please Enter your name: ");
        name = input.nextLine();
    }

    public void setUserAge(Scanner input) {
        System.out.println("Please Enter your age: ");
        age = input.nextInt();
    }

    public void setUserFavColorCard(Scanner input) {
        input.nextLine();
        System.out.println("Please Enter your favorite color card: ");
        favColorCard = input.nextLine();
    }

    // Use in Activities.java
    public void userInterface() {
        System.out.println("It is now your turn " + name + ": What would you like to do? (Pick a number 1-5):");
        System.out.println("1. Play one of your cards");
        System.out.println("2. Draw a card from deck");
        System.out.println("3. See Opponent's Card Count");
        System.out.println("4. Help (lord knows I need it)");
        System.out.println("5: Call Uno");
        System.out.println("6: Quit");
    }

    public int setUserCommand(Scanner input) {
        userCommand = input.nextInt();
        return userCommand;
    }

    // See User.java and Activities.java from Project 4 for more information on how to properly utilize 
    public void intro() {
        System.out.println("Hello" + name + "and welcome to...");
        System.out.printf("|--------------------|%n");
        System.out.printf("| UNO               |%n");
        System.out.printf("| Java Edition     |%n");
        System.out.printf("|--------------------|%n");
        System.out.println("Would you Like to Play a Game of Uno? (Y/N) *Enter Q to Quit*");
    }

    // In the case for difficultyPicker, USE IN Menu.java otherwise it won't work. 
    public static int difficultyPicker(Scanner input) {
        System.out.println("Please select a difficulty: ");
        System.out.println("1. Easy");
        System.out.println("2. Intermediate");
        System.out.println("3. Expert");
        int difficulty = input.nextInt();
        if (difficulty > 3 || difficulty < 1) {
            System.out.println("Invalid Input, please try again");
            difficulty = input.nextInt();
        }
        if(difficulty==1){
            System.out.println("You have chosen 1: CPU difficulty is set to Easy");
        }
        else if(difficulty==2){
            System.out.println("You have chosen 2: CPU difficulty is set to Intermediate");
        }
        else{
            System.out.println("You have chosen 3: CPU difficulty is set to Expert");
        }  
        return difficulty;
    }

    // Same thing applies for opponentsSelect, USE IN Menu.java otherwise it won't work.
    public static int opponentsSelect(Scanner input) {
        System.out.println("Okay! How many opponents would you like to face? (Enter a Number 2-10) *Enter Q to quit at any time*");
        int opponents = input.nextInt();
        if (opponents > 10 || opponents < 2) {
            System.out.println("Invalid Input, please try again");
            opponents = input.nextInt();
        }
        System.out.println("You have chosen " + opponents + " opponents");
        return opponents;
    }

    public void printHand() {
        System.out.println("Your hand is: ");
        for (int i = 0; i < hand.size(); i++) {
            System.out.println((i + 1) + ". " + hand.get(i).cardInfo);
        }
    }

    public Cards playCard(int cardNum) {
        // Return card
        return hand.get(cardNum - 1);
    }
}