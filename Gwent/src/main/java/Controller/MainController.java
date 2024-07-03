package Controller;

import Model.User;

public class MainController {


    public static boolean canGoPregameMenu(String text) {
        User user = User.getUserByName(text);
        if (user == null) {
            return false;
        }
        //User.setTurnUser(User.getLoggedUser());
        User.getLoggedUser().setOpponentUser(user);
        user.setOpponentUser(User.getLoggedUser());
        user.readyForGame();
        User.getLoggedUser().readyForGame();
        return true;
    }

    public void interMenu(String menuName) {

    }


    public static void exitMenu() {

    }

    public static String showCurrent() {
        return null;
    }

}
