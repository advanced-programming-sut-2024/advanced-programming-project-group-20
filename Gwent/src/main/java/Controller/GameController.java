package Controller;

import Model.Board;
import Model.Card;
import Model.User;
import javafx.fxml.FXML;
import javafx.scene.layout.HBox;

public class GameController {
    public HBox deckHbox;

    @FXML
    public void initialize() {
        User player;
        User.setLoggedUser(player=new User("ali","123","reza","Nilfgaard","a@.com",2,"yes"));
       player.setOpponentUser(new User("ali","123","reza","Nilfgaard","a@.com",2,"yes"));
       User.setTurnUser(player);
       player.getDeck().add();

    }
public static String  vetoCard(Card card){
    return null;
}
public static void showCardInfo(Card card){

}
public static void showRemainingCard(Card card){

}
public static void showBurnedCard(Card card){

}
public static String showRowCard(int number){
    return null;
}
public static void showSpells(){

}
public static void placeCard(Card putCard,int rowNumber){

}
public static void showLeader(){

}
public static void leaderAction(){

}
public static void showPlayersInfo(){

}
public static void showTurnInfo(){

}
public static void showTotalScore(){

}
public static void showTotalScoreOfRow(int rowNumber){


}
public static void passTurn(){

}
public static boolean checkEnding(){
    return true;
}
private static void endGame(){

}
private static void killCard(Card card){

};

}
