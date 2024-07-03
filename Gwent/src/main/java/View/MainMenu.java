package View;

import Model.CardBuilder;
import Model.Factions.ScoiaTael;
import Model.Factions.Skellige;
import Model.User;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.util.Duration;
import webConnection.Client;

import java.net.URL;
import java.util.ArrayList;

public class MainMenu extends Application {
    public TextField opponentName;
    private static Alert alert;


    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        alert = new Alert(Alert.AlertType.NONE);
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
        if (opponentName.getText().isEmpty() || opponentName.getText().equals("")) {
            Client.getConnection().doInServer("MainController", "searchForOpponent", User.getLoggedUser().getUsername());
            alert.setTitle("Search Opponent");
            alert.setHeaderText(null);
            alert.setContentText("Search Opponent...");
            ButtonType buttonType = new ButtonType("Cancel");
            alert.getButtonTypes().setAll(buttonType);
            alert.showAndWait().ifPresent(response -> {
                if (response == buttonType) {
                    alert.close();
                    Client.getConnection().doInServer("MainController", "cancelSearch", User.getLoggedUser().getUsername());
                }
            });
        } else {
            Client.getConnection().doInServer("MainController", "playWithFriend", User.getLoggedUser().getUsername(), opponentName.getText());
        }
    }
    public static void goToPreGame(ArrayList<Object> objects) {
        User opponent = User.getUserByName((String) objects.get(0));
        if (opponent == null) opponent = new User((String) objects.get(0));
        opponent.setOpponentUser(User.getLoggedUser());
        User.getLoggedUser().setOpponentUser(opponent);
        User.getLoggedUser().readyForGame();
        User.getLoggedUser().getOpponentUser().readyForGame();
        Platform.runLater(() -> {
            if(alert.isShowing()) alert.close();
            PreGameMenu preGameMenu = new PreGameMenu();
            try {
                preGameMenu.start(ApplicationController.getStage());
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });
    }

    public static void offline(ArrayList<Object> objects) {
        Platform.runLater(() -> {
            Alert alert1 = new Alert(Alert.AlertType.ERROR);
            alert1.setTitle("Error");
            alert1.setHeaderText("OFFLINE");
            alert1.setContentText("Your friend is offline.");
            alert1.show();
            Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(2), event -> alert1.close()));
            timeline.setCycleCount(1);
            timeline.play();
        });

    }
    public static void inGame(ArrayList<Object> objects) {
        Platform.runLater(() -> {
            Alert alert1 = new Alert(Alert.AlertType.ERROR);
            alert1.setTitle("Error");
            alert1.setHeaderText("Playing");
            alert1.setContentText("Your friend is already in game.");
            alert1.show();
            Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(2), event -> alert1.close()));
            timeline.setCycleCount(1);
            timeline.play();
        });
    }
    public static void wrongFriend(ArrayList<Object> objects) {
        Platform.runLater(() -> {
            Alert alert1 = new Alert(Alert.AlertType.ERROR);
            alert1.setTitle("Error");
            alert1.setHeaderText("Wrong Friend");
            alert1.setContentText("You don't have a friend with this name.");
            alert1.show();
            Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(2), event -> alert1.close()));
            timeline.setCycleCount(1);
            timeline.play();
        });
    }
    public static void waitFriend(ArrayList<Object> objects) {
        Platform.runLater(() -> {
            alert.setTitle("Waiting");
            alert.setHeaderText("Wait For Friend");
            alert.setContentText("You Should wait for your friend response.");
            alert.getButtonTypes().setAll(ButtonType.OK);
            alert.show();
        });
    }
    public static void gameRequest(ArrayList<Object> objects) {
        Platform.runLater(() -> {
            Alert alert1 = new Alert(Alert.AlertType.CONFIRMATION);
            alert1.setTitle("Game Request");
            alert1.setHeaderText("You Have a Game Request");
            alert1.setContentText((String) objects.get(0) + " wants play with you");
            ButtonType accept = new ButtonType("Accept");
            ButtonType reject = new ButtonType("Reject");
            alert1.getButtonTypes().setAll(accept, reject);
            alert1.showAndWait().ifPresent(response -> {
                if (response == accept) {
                    goToPreGame(objects);
                    objects.add(User.getLoggedUser().getUsername());
                    Client.getConnection().doInServer("MainController", "acceptFriend", objects);
                } else if (response == reject) {
                    Client.getConnection().doInServer("MainController", "rejectFriend", objects);
                }
            });
        });
    }
    public static void rejectRequest(ArrayList<Object> objects) {
        Platform.runLater(() -> {
            Alert alert1 = new Alert(Alert.AlertType.ERROR);
            alert1.setTitle("Error");
            alert1.setHeaderText("Reject Request");
            alert1.setContentText("Your friend rejected the request.");
            alert1.show();
            Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(2), event -> alert1.close()));
            timeline.setCycleCount(1);
            timeline.play();
        });
    }

}
