// REFERENCE: Some logic was taken from Professor Bruce Montegomery
// REFERENCE: https://stackoverflow.com/questions/68002731/how-to-generate-random-number-in-range-of-truncated-normal-distribution

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Cards {
    public String cardInfo;
    public String type;

    public static List<String> makeList() {
        // Read in the csv file
        Scanner scanner = new Scanner(Cards.class.getResourceAsStream("cards.csv"));
        List<String> list = new ArrayList<>();
        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            // Add in two of every card whose name is 1 through 9
            if (line.matches("[1-9]|Cancel|Reverse|Shuffle|DrawTwo")) {
                list.add(line);
                list.add(line);
            } else if (line.equals("0")) {
                list.add(line);
            }
        }
        scanner.close();
        return list;
    }

    public Cards() {
        super();
    }

    public void showCard() {
        System.out.printf("|--------------------|%n", " ");
        System.out.printf("| %-18s |%n", this.type);
        System.out.printf("| %18s |%n", " ");
        System.out.printf("| %7s %-10s |%n", this.cardInfo, " ");
        System.out.printf("| %18s |%n", " ");
        System.out.printf("| %18s |%n", this.type);
        System.out.printf("|--------------------|%n", " ");
    }
}

class RedCards extends Cards {
    private static List<String> cardList = makeList();
    public RedCards() {
        super();
        this.cardInfo = cardList.get(0);
        cardList.remove(0);
        this.type = "Red";
    }
}

class YellowCards extends Cards {
    public static List<String> cardList = makeList();
    public YellowCards() {
        super();
        this.cardInfo = cardList.get(0);
        cardList.remove(0);
        this.type = "Yellow";
    }    
}

class GreenCards extends Cards {
    public static List<String> cardList = makeList();
    public GreenCards() {
        super();
        this.cardInfo = cardList.get(0);
        cardList.remove(0);
        this.type = "Green";
    }    
}

class BlueCards extends Cards {
    public static List<String> cardList = makeList();
    public BlueCards() {
        super();
        this.cardInfo = cardList.get(0);
        cardList.remove(0);
        this.type = "Blue";
    }    
}

class WildCards extends Cards {
    public static List<String> wildCards =  wildList();
    public WildCards() {
        super();
        this.cardInfo = wildCards.get(0);
        wildCards.remove(0);
        this.type = "Wild";
    }

    private static List<String> wildList() {
        // Read in the csv file
        Scanner scanner = new Scanner(Cards.class.getResourceAsStream("cards.csv"));
        List<String> list = new ArrayList<>();
        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            // Add in two of every card whose name is 1 through 9
            if (line.matches("Switch|DrawFour")) {
                list.add(line);
                list.add(line);
                list.add(line);
                list.add(line);
            } 
        }
        //System.out.println(list);
        scanner.close();
        return list;
    }
}