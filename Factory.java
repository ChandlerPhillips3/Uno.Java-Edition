// REFERENCE: https://www.tutorialspoint.com/design_pattern/factory_pattern.htm 

/* Here is our factory class pattern:
 * Factory patterns deals with when we want to declare a "new" object, we place object creation code in a subclass 
 * We have created a method for staff to be created as well as vehicles to be created for our fncds
 * when an FNCD needs a new object of staff type Driver lets say, it will call getStaff with the input "Driver" in order to create a new driver object and will return that object to be used
 * this is the same way for vehicle but the parameter should be a vehicle name instead.
 * If the paramater doesn't match a staff member or vehicle there are edge cases to make sure we are returning something
 * Now because this is our program we dont check if an object is null when it is returned back to actions because we know how the code works and get the desired outcome but in an actual application that would be necessary!
 * Or at least a warning that the factory returned somehting null.
 */

public class Factory {
    public Cards getCards(String name) {
        if (name == null) {
            return null;
        }
        // Construct a deck using cards.java including everything from the redCards, yellowCards, blueCards, GreenCards classes
        if (name.equalsIgnoreCase("RedCards")) {
            return new RedCards();
        } else if (name.equalsIgnoreCase("YellowCards")) {
            return new YellowCards();
        } else if (name.equalsIgnoreCase("GreenCards")) {
            return new GreenCards();
        }
        else if(name.equalsIgnoreCase("BlueCards")) {
            return new BlueCards();
        }
        return null;
    }

    public Players getOPP(int difficulty) {
        // if (difficulty == null) {
        //     return null;
        // }
        if (difficulty==1 || difficulty==2 || difficulty==3){//1 is easy, 2 is intermediate, 3 is expert
            return new CPU(difficulty);
        }
        return null;
    }
}

