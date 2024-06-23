package View;

import Controller.ApplicationController;
import Controller.RegisterController;
import Model.Card;
import Model.CardBuilder;
import Model.Faction;
import Model.Factions.Monsters;
import Model.Factions.Skellige;
import Model.User;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.net.URL;

public class MainMenu extends Application {
    public TextField opponentName;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        stage.setHeight(720);
        stage.setWidth(900);
        URL url = RegisterMenu.class.getResource("/FXML/MainMenu.fxml");
        Pane root = FXMLLoader.load(url);
        Scene scene = new Scene(root);
        stage.setScene(scene);
        root.setBackground(new Background(ApplicationController.createBackGroundImage("/backgrounds/main.jpg"
                , stage.getHeight(), stage.getWidth())));
        stage.show();
    }

    public void goToProfileMenu(MouseEvent mouseEvent) {
        ProfileMenu profileMenu = new ProfileMenu();
        try {
            profileMenu.start(ApplicationController.getStage());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void goToRegisterMenu(MouseEvent mouseEvent) {
        RegisterMenu registerMenu = new RegisterMenu();
        try {
            registerMenu.start(ApplicationController.getStage());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void goToPreGameMenu(MouseEvent mouseEvent) throws Exception {
        User user = User.getUserByName(opponentName.getText());
        if (user == null) {
            ApplicationController.alert("Wrong Username","There is no user with this username");
        } else {
            User.setTurnUser(User.getLoggedUser());
            User.getLoggedUser().setOpponentUser(user);
            user.setOpponentUser(User.getLoggedUser());
            PreGameMenu preGameMenu = new PreGameMenu();
            preGameMenu.start(ApplicationController.getStage());
        }
    }
    // TODO delete method below later
    public void directToGameMenu(MouseEvent mouseEvent) {
        Skellige skellige = new Skellige();
        User.getLoggedUser().getDeck().add(CardBuilder.skellige("Mardroeme", skellige));
        User.getLoggedUser().getDeck().add(CardBuilder.skellige("Mardroeme", skellige));
        User.getLoggedUser().getDeck().add(CardBuilder.skellige("Mardroeme", skellige));
        User.getLoggedUser().getDeck().add(CardBuilder.skellige("Berserker", skellige));
        User.getLoggedUser().getDeck().add(CardBuilder.skellige("Svanrige", skellige));
        User.getLoggedUser().getDeck().add(CardBuilder.skellige("Udalryk", skellige));
        User.getLoggedUser().getDeck().add(CardBuilder.skellige("DonarAnHindar", skellige));
        User.getLoggedUser().getDeck().add(CardBuilder.skellige("ClanAnCraite", skellige));
        User.getLoggedUser().getDeck().add(CardBuilder.skellige("ClanAnCraite", skellige));
        User.getLoggedUser().getDeck().add(CardBuilder.skellige("ClanAnCraite", skellige));
        User.getLoggedUser().getDeck().add(CardBuilder.skellige("WarLongship", skellige));
        User.getLoggedUser().getDeck().add(CardBuilder.skellige("WarLongship", skellige));
        User.getLoggedUser().getDeck().add(CardBuilder.skellige("WarLongship", skellige));
        User.getLoggedUser().getDeck().add(CardBuilder.skellige("DraigBon-Dhu", skellige));
/////////////////
        Monsters monsters = new Monsters();
        User.getLoggedUser().setOpponentUser(new User("Ali", "1", "1", "1", "1", "1"));
        User.getLoggedUser().getOpponentUser().getDeck().add(CardBuilder.monsters("Draug", monsters));
        User.getLoggedUser().getOpponentUser().getDeck().add(CardBuilder.monsters("Leshen", monsters));
        User.getLoggedUser().getOpponentUser().getDeck().add(CardBuilder.monsters("Kayran", monsters));
        User.getLoggedUser().getOpponentUser().getDeck().add(CardBuilder.monsters("Toad", monsters));
        User.getLoggedUser().getOpponentUser().getDeck().add(CardBuilder.monsters("ArachasBehemoth", monsters));
        User.getLoggedUser().getOpponentUser().getDeck().add(CardBuilder.monsters("CroneWeavess", monsters));
        User.getLoggedUser().getOpponentUser().getDeck().add(CardBuilder.monsters("CroneWhispess", monsters));
        User.getLoggedUser().getOpponentUser().getDeck().add(CardBuilder.monsters("EarthElemental", monsters));
        User.getLoggedUser().getOpponentUser().getDeck().add(CardBuilder.monsters("Fiend", monsters));
        User.getLoggedUser().getOpponentUser().getDeck().add(CardBuilder.monsters("FireElemental", monsters));
        User.getLoggedUser().getOpponentUser().getDeck().add(CardBuilder.monsters("Wyvern", monsters));
        User.getLoggedUser().getOpponentUser().getDeck().add(CardBuilder.monsters("Ghoul", monsters));
        User.getLoggedUser().getOpponentUser().getDeck().add(CardBuilder.monsters("Ghoul", monsters));
        User.getLoggedUser().getOpponentUser().getDeck().add(CardBuilder.monsters("Ghoul", monsters));
        User.getLoggedUser().getOpponentUser().setOpponentUser(User.getLoggedUser());
        try {
            new GameMenu().start(ApplicationController.getStage());
        } catch (Exception e){
            e.printStackTrace();
        }
    }

}
