public interface DifficultyBehavior {
    public Cards playCard(Cards Lastplayed, CPU current);
}

class Easy implements DifficultyBehavior{
    //EASY DIFFICULTY: Basically plays the first card that matches the last played card based on type or color, if they dont they have to play a wild card or draw from the deck
    @Override
    public Cards playCard(Cards Lastplayed,CPU current){//pass last card in and player themselves!
        //90% chance plays with card matching either color or number if there is one
        //plays wild card or draws a new card if not
        boolean isInfo=false;
        boolean isColor=false;
        boolean isWild=false;
        Cards wePlayThis=null;
        //loop and see if there is one first of all matching!
        for(int i=0; i< current.getCardCount();i++){
            if(Lastplayed.type==current.hand.get(i).type){//same color 
                isColor=true;
            }
            if(Lastplayed.cardInfo==current.hand.get(i).cardInfo){//same number/type of card
                isInfo=true;
            }
            if(current.hand.get(i).type.contains("Wild")){//if they have a wild card
                isWild=true;
            }
        }
        //System.out.println("WILD: "+ isWild);
        //if they have a card that matches both type and color:
        if (isInfo==true && isColor==true){
            //System.out.println("COLOR AND TYPE MATCH");
            //loop until one matches and play that one!
            for(int j=0;j< current.getCardCount();j++){
                if(current.hand.get(j).cardInfo==Lastplayed.cardInfo || current.hand.get(j).type==Lastplayed.type){
                    wePlayThis=current.hand.get(j);
                    current.hand.remove(j);
                    return wePlayThis;
                }
            }
        } else if(isInfo==true && isColor==false){
            //loop for first matching color
            //System.out.println("Type MATCH");
            for(int j=0;j< current.getCardCount();j++){
                if( current.hand.get(j).cardInfo==Lastplayed.cardInfo ){
                    wePlayThis=current.hand.get(j);
                    current.hand.remove(j);
                    return wePlayThis;
                }
            }
        } else if(isInfo==false && isColor==true){
            //loop for first matching type /num
            //System.out.println("INFO MATCH");
            for(int j=0;j< current.getCardCount();j++){
                if(current.hand.get(j).type==Lastplayed.type){
                    wePlayThis=current.hand.get(j);
                    current.hand.remove(j);
                    return wePlayThis;
                }
            }
        } else if(isWild==true) {
            //System.out.println("WILD TRYING");
            //change it to the color they have the most of BE SURE NOT TO ADD NEW CARD TO THE DISCARD DECK!!!
            int countR=0;
            int countB=0;
            int countG=0;
            int countY=0;
            for(int j=0;j< current.getCardCount();j++){
                if(current.hand.get(j).cardInfo.contains("Blue")){
                    countB++;
                }
                if(current.hand.get(j).cardInfo.contains("Yellow")){
                    countY++;
                }
                if(current.hand.get(j).cardInfo.contains("Green")){
                    countG++;
                }
                if(current.hand.get(j).cardInfo.contains("Red")){
                    countR++;
                }
            }
            String ColorHolder= "Red";
            int max=countR;
            if(countB>max){
                ColorHolder= "Blue";
                max=countB;
            }
            if(countG>max){
                ColorHolder= "Green";
                max=countG;
            }
            if(countY>max){
                ColorHolder= "Yellow";
                max=countY;
            }
            //change color of wild to whatver max there was!
            for(int j=0;j< current.getCardCount();j++){
                if( current.hand.get(j).type=="Wild"){
                    wePlayThis=current.hand.get(j);
                    current.hand.remove(j);
                    wePlayThis.type=ColorHolder;
                    System.out.println(current.getName() + " Has Changed the Color to " + ColorHolder + " Using a Wild Card!");
                    return wePlayThis;
                }
            }
        }
        else{
            //DRAW A CARD NERD
            return wePlayThis;
        }
        return wePlayThis;
    }
}

class Intermediate implements DifficultyBehavior{

    //INTERMEDIATE DIFFICULTY: Basically plays the  card that matches the last played card based on type or color and has a greater count of cards, if they dont they have to play a wild card or draw from the deck
    @Override
    public Cards playCard(Cards Lastplayed,CPU current){//pass last card in and player themselves!
        //90% chance plays with card matching either color or number if there is one
        //plays wild card or draws a new card if not
        boolean isType=false;
        boolean isColor=false;
        boolean isWild=false;
        Cards wePlayThis=null;


        //NEW: changes the order of 
        int cornerMatch=0;
        int middleMatch=0;
        //loop and see if there is one first of all matching!
        for(int i=0; i< current.getCardCount();i++){
            if(Lastplayed.cardInfo==current.hand.get(i).cardInfo){//same color 
                isColor=true;
                middleMatch++;
            }
            if(Lastplayed.type==current.hand.get(i).type){//same number/type of card
                isType=true;
                cornerMatch++;
            }
            if(current.hand.get(i).type.contains("Wild")){//if they have a wild card
                isWild=true;
            }
        }
        //if they have a card that matches both type and color:
        if(middleMatch> cornerMatch){
            if(isColor==true){
                //loop for first matching color
                for(int j=0;j< current.getCardCount();j++){
                    if(current.hand.get(j).cardInfo==Lastplayed.cardInfo || current.hand.get(j).type==Lastplayed.type){
                        wePlayThis=current.hand.get(j);
                        current.hand.remove(j);
                        return wePlayThis;
                    }
                }
            }
            else if(isType==true){
                //loop for first matching type /num
                for(int j=0;j< current.getCardCount();j++){
                    if( current.hand.get(j).type==Lastplayed.type){
                        wePlayThis=current.hand.get(j);
                        current.hand.remove(j);
                        return wePlayThis;
                    }
                }
            }
            //dont have any matches but has wild card to play
            else if(isWild==true){
                //change it to the color they have the most of BE SURE NOT TO ADD NEW CARD TO THE DISCARD DECK!!!
                int countR=0;
                int countB=0;
                int countG=0;
                int countY=0;
                for(int j=0;j< current.getCardCount();j++){
                    if(current.hand.get(j).cardInfo.contains("Blue")){
                        countB++;
                    }
                    if(current.hand.get(j).cardInfo.contains("Yellow")){
                        countY++;
                    }
                    if(current.hand.get(j).cardInfo.contains("Green")){
                        countG++;
                    }
                    if(current.hand.get(j).cardInfo.contains("Red")){
                        countR++;
                    }
                }
                String ColorHolder= "Red";
                int max=countR;
                if(countB>max){
                    ColorHolder= "Blue";
                    max=countB;
                }
                if(countG>max){
                    ColorHolder= "Green";
                    max=countG;
                }
                if(countY>max){
                    ColorHolder= "Yellow";
                    max=countY;
                }
                //change color of wild to whatver max there was!
                for(int j = 0; j < current.getCardCount(); j++){
                    if( current.hand.get(j).cardInfo=="Wild"){
                        wePlayThis=current.hand.get(j);
                        current.hand.remove(j);
                        wePlayThis.cardInfo=ColorHolder;
                        System.out.println(current.getName() + " Has Changed the Color to " + ColorHolder + " Using a Wild Card!");
                        return wePlayThis;
                    }
                }
            }
            else{
                //DRAW A CARD NERD
                return wePlayThis;
            }
            return wePlayThis;
        }
        else
        {
            if(isType==true){
                //loop for first matching type /num
                for(int j=0;j< current.getCardCount();j++){
                    if( current.hand.get(j).type==Lastplayed.type){
                        wePlayThis=current.hand.get(j);
                        current.hand.remove(j);
                        return wePlayThis;
                    }
                }
            }
            else if(isColor==true){
                //loop for first matching color
                for(int j=0;j< current.getCardCount();j++){
                    if(current.hand.get(j).cardInfo==Lastplayed.cardInfo || current.hand.get(j).type==Lastplayed.type){
                        wePlayThis=current.hand.get(j);
                        current.hand.remove(j);
                        return wePlayThis;
                    }
                }
            }
            //dont have any matches but has wild card to play
            else if(isWild==true){
                //change it to the color they have the most of BE SURE NOT TO ADD NEW CARD TO THE DISCARD DECK!!!
                int countR=0;
                int countB=0;
                int countG=0;
                int countY=0;
                for(int j=0;j< current.getCardCount();j++){
                    if(current.hand.get(j).cardInfo.contains("Blue")){
                        countB++;
                    }
                    if(current.hand.get(j).cardInfo.contains("Yellow")){
                        countY++;
                    }
                    if(current.hand.get(j).cardInfo.contains("Green")){
                        countG++;
                    }
                    if(current.hand.get(j).cardInfo.contains("Red")){
                        countR++;
                    }
                }
                String ColorHolder= "Red";
                int max=countR;
                if(countB>max){
                    ColorHolder= "Blue";
                    max=countB;
                }
                if(countG>max){
                    ColorHolder= "Green";
                    max=countG;
                }
                if(countY>max){
                    ColorHolder= "Yellow";
                    max=countY;
                }
                //change color of wild to whatver max there was!
                for(int j = 0; j < current.getCardCount(); j++){
                    if( current.hand.get(j).cardInfo=="Wild"){
                        wePlayThis=current.hand.get(j);
                        current.hand.remove(j);
                        wePlayThis.cardInfo=ColorHolder;
                        System.out.println(current.getName() + " Has Changed the Color to " + ColorHolder + " Using a Wild Card!");
                        return wePlayThis;
                    }
                }
            }
            else{
                //DRAW A CARD NERD
                return wePlayThis;
            }
            return wePlayThis;
        }
    }
}

class Expert implements DifficultyBehavior{

    //EXPERT DIFFICULTY: WILD ALWAYS PLAYED IF IN THERE
    @Override
    public Cards playCard(Cards Lastplayed,CPU current){//pass last card in and player themselves!
        //90% chance plays with card matching either color or number if there is one
        //plays wild card or draws a new card if not
        boolean isType=false;
        boolean isColor=false;
        boolean isWild=false;
        Cards wePlayThis=null;


        //NEW: changes the order of 
        int cornerMatch=0;
        int middleMatch=0;
        //loop and see if there is one first of all matching!
        for(int i=0; i< current.getCardCount();i++){
            if(Lastplayed.cardInfo==current.hand.get(i).cardInfo){//same color 
                isColor=true;
                middleMatch++;
            }
            if(Lastplayed.type==current.hand.get(i).type){//same number/type of card
                isType=true;
                cornerMatch++;
            }
            if(current.hand.get(i).type.contains("Wild")){//if they have a wild card
                isWild=true;
            }
        }
        //if they have a card that matches both type and color:
        if(middleMatch> cornerMatch){
            if(isWild==true){
                //change it to the color they have the most of BE SURE NOT TO ADD NEW CARD TO THE DISCARD DECK!!!
                int countR=0;
                int countB=0;
                int countG=0;
                int countY=0;
                for(int j=0;j< current.getCardCount();j++){
                    if(current.hand.get(j).cardInfo.contains("Blue")){
                        countB++;
                    }
                    if(current.hand.get(j).cardInfo.contains("Yellow")){
                        countY++;
                    }
                    if(current.hand.get(j).cardInfo.contains("Green")){
                        countG++;
                    }
                    if(current.hand.get(j).cardInfo.contains("Red")){
                        countR++;
                    }
                }
                String ColorHolder= "Red";
                int max=countR;
                if(countB>max){
                    ColorHolder= "Blue";
                    max=countB;
                }
                if(countG>max){
                    ColorHolder= "Green";
                    max=countG;
                }
                if(countY>max){
                    ColorHolder= "Yellow";
                    max=countY;
                }
                //change color of wild to whatver max there was!
                for(int j = 0; j < current.getCardCount(); j++){
                    if( current.hand.get(j).cardInfo=="Wild"){
                        wePlayThis=current.hand.get(j);
                        current.hand.remove(j);
                        wePlayThis.cardInfo=ColorHolder;
                        return wePlayThis;
                    }
                }
            }
            else if(isColor==true){
                //loop for first matching color
                for(int j=0;j< current.getCardCount();j++){
                    if(current.hand.get(j).cardInfo==Lastplayed.cardInfo || current.hand.get(j).type==Lastplayed.type){
                        wePlayThis=current.hand.get(j);
                        current.hand.remove(j);
                        return wePlayThis;
                    }
                }
            }
            else if(isType==true){
                //loop for first matching type /num
                for(int j=0;j< current.getCardCount();j++){
                    if( current.hand.get(j).type==Lastplayed.type){
                        wePlayThis=current.hand.get(j);
                        current.hand.remove(j);
                        return wePlayThis;
                    }
                }
            }
            //dont have any matches but has wild card to play
            else{
                //DRAW A CARD NERD
                return wePlayThis;
            }
            return wePlayThis;
        }
        else
        {
            if(isWild==true){
                //change it to the color they have the most of BE SURE NOT TO ADD NEW CARD TO THE DISCARD DECK!!!
                int countR=0;
                int countB=0;
                int countG=0;
                int countY=0;
                for(int j=0;j< current.getCardCount();j++){
                    if(current.hand.get(j).cardInfo.contains("Blue")){
                        countB++;
                    }
                    if(current.hand.get(j).cardInfo.contains("Yellow")){
                        countY++;
                    }
                    if(current.hand.get(j).cardInfo.contains("Green")){
                        countG++;
                    }
                    if(current.hand.get(j).cardInfo.contains("Red")){
                        countR++;
                    }
                }
                String ColorHolder= "Red";
                int max=countR;
                if(countB>max){
                    ColorHolder= "Blue";
                    max=countB;
                }
                if(countG>max){
                    ColorHolder= "Green";
                    max=countG;
                }
                if(countY>max){
                    ColorHolder= "Yellow";
                    max=countY;
                }
                //change color of wild to whatver max there was!
                for(int j = 0; j < current.getCardCount(); j++){
                    if( current.hand.get(j).cardInfo=="Wild"){
                        wePlayThis=current.hand.get(j);
                        current.hand.remove(j);
                        wePlayThis.cardInfo=ColorHolder;
                        return wePlayThis;
                    }
                }
            }
            else if(isType==true){
                //loop for first matching type /num
                for(int j=0;j< current.getCardCount();j++){
                    if( current.hand.get(j).type==Lastplayed.type){
                        wePlayThis=current.hand.get(j);
                        current.hand.remove(j);
                        return wePlayThis;
                    }
                }
            }
            else if(isColor==true){
                //loop for first matching color
                for(int j=0;j< current.getCardCount();j++){
                    if(current.hand.get(j).cardInfo==Lastplayed.cardInfo || current.hand.get(j).type==Lastplayed.type){
                        wePlayThis=current.hand.get(j);
                        current.hand.remove(j);
                        return wePlayThis;
                    }
                }
            }
            //dont have any matches but has wild card to play
            else{
                //DRAW A CARD NERD
                return wePlayThis;
            }
            return wePlayThis;
        }
    }
}

