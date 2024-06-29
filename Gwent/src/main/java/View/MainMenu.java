package View;

import Controller.ApplicationController;
import Controller.MainController;
import Controller.RegisterController;

import Model.Card;
import Model.CardBuilder;
import Model.Faction;
import Model.CardBuilder;
import Model.Factions.Monsters;
import Model.Factions.ScoiaTael;
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
import java.util.concurrent.CountDownLatch;

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

    public void goToPregameMenu(MouseEvent mouseEvent) throws Exception {
        if (!MainController.canGoPregameMenu(opponentName.getText())){
            ApplicationController.alert("Wrong Username", "There is no user with this username");
        } else {
                PreGameMenu preGameMenu = new PreGameMenu();
                try {
                    preGameMenu.start(ApplicationController.getStage());
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }
        }

    // TODO delete method below later


    public void directToGameMenu(MouseEvent mouseEvent) {
        ScoiaTael scoiaTael = new ScoiaTael();
        User.getLoggedUser().getDeck().add(CardBuilder.scoiaTael("HavekarHealer", scoiaTael));
        User.getLoggedUser().getDeck().add(CardBuilder.scoiaTael("HavekarHealer", scoiaTael));
        User.getLoggedUser().getDeck().add(CardBuilder.scoiaTael("HavekarHealer", scoiaTael));
        User.getLoggedUser().getDeck().add(CardBuilder.scoiaTael("Ciaranaep", scoiaTael));
        User.getLoggedUser().getDeck().add(CardBuilder.scoiaTael("Eithne", scoiaTael));
        User.getLoggedUser().getDeck().add(CardBuilder.scoiaTael("Seasenthessis", scoiaTael));
        User.getLoggedUser().getDeck().add(CardBuilder.scoiaTael("DennisCranmer", scoiaTael));
        User.getLoggedUser().getDeck().add(CardBuilder.scoiaTael("Schirru", scoiaTael));
        User.getLoggedUser().getDeck().add(CardBuilder.scoiaTael("IsengrimFaoiltiarna", scoiaTael));
        User.getLoggedUser().getDeck().add(CardBuilder.scoiaTael("Milva", scoiaTael));
        User.getLoggedUser().getDeck().add(CardBuilder.neutral("Decoy"));


        Skellige skellige =new Skellige();
        User.getLoggedUser().setOpponentUser(new User("Ali", "1", "1", "1", "1", "1"));
        User.getLoggedUser().setOpponentUser(new User("Ali", "1", "1", "1", "1", "1"));
        User.getLoggedUser().getOpponentUser().getDeck().add(CardBuilder.skellige("Mardroeme", skellige));
        User.getLoggedUser().getOpponentUser().getDeck().add(CardBuilder.skellige("Mardroeme", skellige));
        User.getLoggedUser().getOpponentUser().getDeck().add(CardBuilder.skellige("Mardroeme", skellige));
        User.getLoggedUser().getOpponentUser().getDeck().add(CardBuilder.skellige("Berserker", skellige));
        User.getLoggedUser().getOpponentUser().getDeck().add(CardBuilder.skellige("Berserker", skellige));
        User.getLoggedUser().getOpponentUser().getDeck().add(CardBuilder.skellige("Berserker", skellige));
        User.getLoggedUser().getOpponentUser().getDeck().add(CardBuilder.skellige("Svanrige", skellige));
        User.getLoggedUser().getOpponentUser().getDeck().add(CardBuilder.skellige("Udalryk", skellige));
        User.getLoggedUser().getOpponentUser().getDeck().add(CardBuilder.skellige("ClanAnCraite", skellige));
        User.getLoggedUser().getOpponentUser().getDeck().add(CardBuilder.skellige("ClanAnCraite", skellige));
        User.getLoggedUser().getOpponentUser().getDeck().add(CardBuilder.skellige("ClanAnCraite", skellige));
        User.getLoggedUser().getOpponentUser().getDeck().add(CardBuilder.skellige("Cerys", skellige));
        User.getLoggedUser().getOpponentUser().getDeck().add(CardBuilder.skellige("BirnaBran", skellige));
        User.getLoggedUser().getOpponentUser().getDeck().add(CardBuilder.skellige("BirnaBran", skellige));
        User.getLoggedUser().getOpponentUser().getDeck().add(CardBuilder.neutral("Decoy"));
        User.getLoggedUser().getOpponentUser().getDeck().add(CardBuilder.neutral("Decoy"));
        User.getLoggedUser().getOpponentUser().getDeck().add(CardBuilder.neutral("Decoy"));
        User.getLoggedUser().getOpponentUser().setOpponentUser(User.getLoggedUser());
        User.setTurnUser(User.getLoggedUser());
        try {
            new GameMenu().start(ApplicationController.getStage());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
