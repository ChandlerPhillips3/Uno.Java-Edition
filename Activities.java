import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

//Taking time breaks Java: https://stackoverflow.com/questions/43507587/how-to-pause-my-java-program-for-2-seconds

public class Activities {
    private ArrayList<Players> Opponents= new ArrayList <Players>();
    public static ArrayList<Cards> deck;
    public static ArrayList<Cards> discardPile;
    MainPlayer mainPlayer = new MainPlayer();
    private ArrayList<Players> order= new ArrayList <Players>();
    public int turnTracker = 0;
    //Factory For Easy Creation:
    Factory getNew = new Factory();
    //Easy print statements:
    PRINT p = new PRINT();
    //CPU opp= getNew.getOPP(1);

    public String getTurnTracker() {
        return ("Turn Tracker: " + turnTracker);
    }

    //FOR START OF THE GAME
    public void START(int numOpponents,int Difficulty, Scanner input) {
        ////Make Deck
        generateCards();
        discardPile = new ArrayList<Cards>();
        ////Make Opponents
        for(int i = 0; i < numOpponents; i++){
            Opponents.add(getNew.getOPP(Difficulty));
        }
        ////Randomize order
        Collections.shuffle(Opponents);

        //get everyones BIO
        p.o("Here are your Opponents today:");
        for(int j = 0; j < Opponents.size(); j++){
            Opponents.get(j).start(deck);
            Opponents.get(j).getBio();
        }
        //should be 7
        //Opponents.get(0).seeCardCount();
        //should display all cards rigth away
        // Opponents.get(0).seeCards();
        // Opponents.get(1).seeCards();
        while(deck.get(0).type == "Wild"){
            Collections.shuffle(deck);
        }
        discardPile.add( deck.get(0));
        deck.remove(0);
        p.blank();
        p.o("The First Card to be played is a:");
        p.blank();
        SHOWCURRENTCARD();
        //organize order of players:
        order = Opponents;

        // Add in MainPlayer
        mainPlayer.setUserName(input);
        mainPlayer.setUserAge(input);
        mainPlayer.setUserFavColorCard(input);
        order.add(mainPlayer);
        System.out.println("Welcome to the game, " + mainPlayer.getName() + "!");
        mainPlayer.start(deck);
    }
    //EACH TURN IS GAMEPLAY!!!!!!
    public Boolean EachTurn(Scanner input){
        printOrder();
        boolean gameOver = false;
        Players current = order.get(0);
        //current.seeCards();
        p.o("It is currently "+ current.getName()+ "'s turn!");
        SHOWPREVIOUSCARD();
        if(current instanceof CPU) {
            boolean doWeNeedToSkip = false;
            CPU thisCpu = (CPU) order.get(0);
            Cards result = thisCpu.playing(lastPlayed(),thisCpu);
            if(result == null){
                p.o(thisCpu.getName() +" had to draw a card because they couldn't play anything.");
                doWeNeedToSkip=true;
                thisCpu.drawCard(deck);
            } else if(result.cardInfo.contains("Cancel")){
                cancel();
                doWeNeedToSkip=true;
                discardPile.add(result);
            } else if(result.cardInfo.contains("DrawFour")){
                drawCard(order.get(1), 4);
                doWeNeedToSkip=true;
                discardPile.add(result);
            } else if(result.cardInfo.contains("DrawTwo")){
                drawCard(order.get(1), 2);
                doWeNeedToSkip=true;
                discardPile.add(result);
            } else if(result.cardInfo.contains("Shuffle")){
                discardPile.add(result);
                shuffleUsersHand();
                doWeNeedToSkip=true;
            } else if(result.cardInfo.contains("Reverse")){
                p.o("The order has now Reversed!");
                order=reverse(order);
                printOrder();
                discardPile.add(result);
                doWeNeedToSkip=true;
            } else {
                p.o(thisCpu.getName() +" has chosen to play a " + result.cardInfo + " with " + result.type);
                discardPile.add(result);
                doWeNeedToSkip=true;
            }

            if(doWeNeedToSkip==true){
                Players Hold=order.get(0);
                order.remove(0);
                order.add(Hold);
            }
        } else if (current instanceof MainPlayer) {
            boolean doWeNeedToSkip = false;
            commandLine(input, mainPlayer);
            doWeNeedToSkip = true;

            // if(doWeNeedToSkip==true){
            //     Players Hold=order.get(0);
            //     order.remove(0);
            //     order.add(Hold);
            // }
        }

        //SEE What value is returned: use funcitons based off value
        gameOver=checkWin();
        SHOWCURRENTCARD();
        //once a player is out of cards the game is finished and the return value will be true breaking the loop!
        turnTracker++;
        return gameOver;
    }
    //SHOWN DURING EACH TURN SO PLAYER KNOWS WHAT CARDS TO PLAY
    public void SHOWPREVIOUSCARD() {
        p.o("The previous card was:");
        p.blank();
        discardPile.get(discardPile.size()-1).showCard();
        p.blank();
    }

    public void SHOWCURRENTCARD() {
        if(deck.size() > 0){
            //use showCard from cards.java
            p.o("The current card is now:");
            p.blank();
            lastPlayed().showCard();
            p.blank();
        }
        else{
            System.out.println("DECK IS OUT OF CARDS: Shuffling the Discarded deck to be played!");
            addBackDiscard();
        }
        
    }

    public Cards lastPlayed(){
        return discardPile.get(discardPile.size()-1);
    }

    public boolean checkWin(){
        for(int j=0; j< order.size();j++){
            if(order.get(j).getCardCount()==0){
                return true;
            }
        }
        return false;
    }

    public void printOrder(){
        p.o("The current order of Players is as follows:");
        for(int i=0;i<order.size();i++){
            System.out.print(order.get(i).getName());
            if(i !=order.size()-1){
                System.out.print(" -> ");
            }
        }
        System.out.print(" (Repeat!)");
        p.blank();
    }

    //refrence: https://www.geeksforgeeks.org/reverse-an-arraylist-in-java/
    public ArrayList<Players> reverse(ArrayList<Players> alist) {
        // Arraylist for storing reversed elements
        ArrayList<Players> revArrayList = new ArrayList<Players>();
        for (int i = alist.size() - 1; i >= 0; i--) {
            // Append the elements in reverse order
            revArrayList.add(alist.get(i));
        }
 
        // Return the reversed arraylist
        return revArrayList;
    }

    // For cards such as drawFour or drawTwo
    public void drawCard(Players victim, int i) {
        p.o(order.get(0).getName() + " Has Made " + order.get(1).getName() + " Draw " + i + " cards from the Deck!");
        if(deck.size() <= i){
            addBackDiscard();
        }
        for(int draw=0;draw<i;draw++){
            victim.drawCard(deck);
        }
    }

    public void shuffleUsersHand() {
        p.o(order.get(0).getName()+" Has Chosen To Swap his remaining cards with new ones!");
        if(deck.size() <= order.get(0).hand.size()){
            addBackDiscard();
        }
        int hold=order.get(0).getCardCount();
        deck.addAll(order.get(0).hand);
        order.get(0).hand.removeAll( order.get(0).hand);
        for(int swap = 0; swap < hold; swap++){
            order.get(0).drawCard(deck);
        }
    }

    public void cancel() {
        p.o(order.get(0).getName()+" Has Chosen To Skip "+ order.get(1).getName());
        Players Hold=order.get(0);
        order.remove(0);
        order.add(Hold);
    }

    public void addBackDiscard() {
        //save last card to start discard pile:
        Cards Hold= discardPile.get(discardPile.size()-1);
        discardPile.remove(discardPile.size()-1);
        for(int numCards=0;numCards<discardPile.size();numCards++){
            if(discardPile.get(numCards).cardInfo.contains("Switch") || discardPile.get(numCards).cardInfo.contains("DrawFour")){
                discardPile.get(numCards).type="Wild";
            }
            deck.add(discardPile.get(numCards));
        }
        discardPile.removeAll(discardPile);
        discardPile.add(Hold);
        shuffleDeck(deck);
    }

    public ArrayList<Cards> makeDeck() {
        ArrayList<Cards> deck = new ArrayList<Cards>();
        // add in 25 of every card
        for (int i = 0; i < 25; i++) {
            deck.add(new RedCards());
            deck.add(new YellowCards());
            deck.add(new GreenCards());
            deck.add(new BlueCards());
        }
        // add in 5 of every special card
        for (int i = 0; i < 8; i++) {
            deck.add(new WildCards());
        }
        return deck;
    }

    // this will shuffle the deck of cards it is given
    public static ArrayList<Cards> shuffleDeck(ArrayList<Cards> deck) {
        // https://www.tutorialspoint.com/shuffle-elements-of-arraylist-with-java-collections#:~:text=In%20order%20to%20shuffle%20elements,shuffle()%20method.
        Collections.shuffle(deck);
        return deck;
    }

    public void generateCards() {
        // needs work
        ArrayList<Cards> cards = makeDeck();
        // System.out.println(cards);
        deck = shuffleDeck(cards);
        // return shuffleDeck(cards);
    }

    public void NEXTPlayer(){
        Players Hold=order.get(0);
        order.remove(0);
        order.add(Hold);
    }

    // User Input
    public void commandLine(Scanner input, MainPlayer player) {
        int number = 0;
        while (number != 6) {
            // Display options for the user
            player.userInterface();
            number = player.setUserCommand(input);
            switch (number) {
                case 1: 
                {
                    printOrder();
                    System.out.println("You have chosen to play a card");
                    System.out.println("Your cards are as follows: ");
                    // Placeholder
                    //player.printHand();
                    System.out.println("Which card would you like to play?");
                    player.seeCards();
                    // play a card from the hand here
                    int cardNumber = input.nextInt();
                    Cards selectedCard = player.playCard(cardNumber);
                    Boolean checker = false; //check to see if card can be played
                    if (selectedCard.cardInfo == discardPile.get(discardPile.size()-1).cardInfo) {
                        player.getRidOfCard(selectedCard);
                        discardPile.add(selectedCard);
                        checker = true;
                    } else if (selectedCard.type == discardPile.get(discardPile.size()-1).type) {
                        player.getRidOfCard(selectedCard);
                        discardPile.add(selectedCard);
                        checker=true;
                    } else if (selectedCard.type.contains("Wild")) {
                        player.getRidOfCard(selectedCard);
                        //LET USER CHANGE COLOR INSIDE OF HERE!!!
                        System.out.println("What Color Would you like the cards to be?");
                        System.out.println("1. Red");
                        System.out.println("2. Blue");
                        System.out.println("3. Green");
                        System.out.println("4. Yellow");
                        int answer = input.nextInt();
                        while(answer<1 || answer>4){
                            p.blank();
                            System.out.println("NO Please choose an answer 1-4!");
                            answer = input.nextInt();
                        }
                        //change wild to a color
                        switch (answer){
                            case 1:
                                System.out.println(player.getName() + " Has chosen to change the color to RED!");
                                selectedCard.type="Red";
                                break;
                            case 2:
                                System.out.println(player.getName() + " Has chosen to change the color to BLUE!");
                                selectedCard.type="Blue";
                                break;
                            case 3:
                                System.out.println(player.getName() + " Has chosen to change the color to GREEN!");
                                selectedCard.type="Green";
                                break;
                            case 4:
                                System.out.println(player.getName() + " Has chosen to change the color to YELLOW!");
                                selectedCard.type="Yellow";
                                break;
                        }
                        discardPile.add(selectedCard);
                        checker=true;
                    } else {
                        System.out.println("You cannot play that card");
                        checker = false;
                    }
                    /////SPECIAL CARD PLAYED
                    if(checker==true){
                        System.out.println("CARD INFO: TYPE:" + selectedCard.type + " Card Info: "+ selectedCard.cardInfo );
                        if(selectedCard.cardInfo.contains("Cancel")){
                            cancel();
                            NEXTPlayer();
                        } else if(selectedCard.cardInfo.contains("DrawFour")){
                            drawCard(order.get(1), 4);
                            NEXTPlayer();
                        } else if(selectedCard.cardInfo.contains("DrawTwo")){
                            drawCard(order.get(1), 2);
                            NEXTPlayer();
                        } else if(selectedCard.cardInfo.contains("Shuffle")){
                            shuffleUsersHand();
                            NEXTPlayer();
                        } else if(selectedCard.cardInfo.contains("Reverse")){
                            p.o("The order has now Reversed!");
                            order=reverse(order);
                            printOrder();
                        } else {
                            //System.out.println("CARD INFO: TYPE:" + selectedCard.type + " Card Info: "+ selectedCard.cardInfo);
                            NEXTPlayer();
                        }
                        number=6;
                    }
                }
                    // add selected card to discard pile
                    break;

                case 2:
                    System.out.println("You have chosen to draw a card");
                    player.drawCard(deck);
                    NEXTPlayer();
                    number = 6;
                    break;
                case 3:
                    System.out.println("You have chosen to see Opponent's Card Count");
                    p.blank();
                    for(int i=0; i< order.size();i++){
                        System.out.println(order.get(i).getName() + " Has " + order.get(i).getCardCount() + " Card(s)");
                    }
                    break;
                case 4:
                    System.out.println("You have chosen Help.");
                    break;
                case 5:
                {
                    if (player.getCardCount() == 1) {
                        System.out.println("You have chosen to call UNO!");
                        System.out.println("Your cards are as follows: ");
                        // Placeholder
                        //player.printHand();
                        System.out.println("Which card would you like to play?");
                        player.seeCards();
                        // play a card from the hand here
                        int cardNumber = input.nextInt();
                        // play a card from the hand here
                        Cards selectedCard = player.playCard(cardNumber);
                        Boolean checker = false; //check to see if card can be played
                        if (selectedCard.cardInfo == discardPile.get(discardPile.size()-1).cardInfo) {
                            player.getRidOfCard(selectedCard);
                            discardPile.add(selectedCard);
                            System.out.println("Congratulations! You Win!");
                            System.exit(0);
                        } else if (selectedCard.type == discardPile.get(discardPile.size()-1).type) {
                            player.getRidOfCard(selectedCard);
                            discardPile.add(selectedCard);
                            System.out.println("Congratulations! You Win!");
                            System.exit(0);
                        } else if (selectedCard.type.contains("Wild")) {
                            player.getRidOfCard(selectedCard);
                            //LET USER CHANGE COLOR INSIDE OF HERE!!!
                            System.out.println("What Color Would you like the cards to be?");
                            System.out.println("1. Red");
                            System.out.println("2. Blue");
                            System.out.println("3. Green");
                            System.out.println("4. Yellow");
                            int answer = input.nextInt();
                            while(answer<1 || answer>4){
                                p.blank();
                                System.out.println("NO Please choose an answer 1-4!");
                                answer = input.nextInt();
                            }
                            //change wild to a color
                            switch (answer){
                                case 1:
                                    System.out.println(player.getName() + " Has chosen to change the color to RED!");
                                    selectedCard.type="Red";
                                    break;
                                case 2:
                                    System.out.println(player.getName() + " Has chosen to change the color to BLUE!");
                                    selectedCard.type="Blue";
                                    break;
                                case 3:
                                    System.out.println(player.getName() + " Has chosen to change the color to GREEN!");
                                    selectedCard.type="Green";
                                    break;
                                case 4:
                                    System.out.println(player.getName() + " Has chosen to change the color to YELLOW!");
                                    selectedCard.type="Yellow";
                                    break;
                            }
                            discardPile.add(selectedCard);
                            System.out.println("Congratulations! You Win!");
                            System.exit(0);
                        } else {
                            System.out.println("You cannot play that card");
                            checker = false;
                        }
                        /////SPECIAL CARD PLAYED
                        if(checker==true){
                            System.out.println("CARD INFO: TYPE:" + selectedCard.type + " Card Info: "+ selectedCard.cardInfo );
                            if(selectedCard.cardInfo.contains("Cancel")){
                                cancel();
                                NEXTPlayer();
                            } else if(selectedCard.cardInfo.contains("DrawFour")){
                                drawCard(order.get(1), 4);
                                NEXTPlayer();
                            } else if(selectedCard.cardInfo.contains("DrawTwo")){
                                drawCard(order.get(1), 2);
                                NEXTPlayer();
                            } else if(selectedCard.cardInfo.contains("Shuffle")){
                                shuffleUsersHand();
                                NEXTPlayer();
                            } else if(selectedCard.cardInfo.contains("Reverse")){
                                p.o("The order has now Reversed!");
                                order=reverse(order);
                                printOrder();
                            } else {
                                //System.out.println("CARD INFO: TYPE:" + selectedCard.type + " Card Info: "+ selectedCard.cardInfo);
                                NEXTPlayer();
                            }
                            number=6;
                        }
                    } else {
                        System.out.println("You have more than one card, you cannot call UNO!");
                    }
                }
                    break;
                case 6:
                    System.out.println("You have chosen to quit the game.");
                    System.exit(0);
                //case 6:
                
                default:
                    System.out.println("1 THROUGH 5 STOOPID!");
                    break;
            }
            System.out.println("");
            input.nextLine();
        }
    }
}