package View;

import Model.Faction;
import Model.Leader;
import Model.User;
import com.google.gson.Gson;
import javafx.animation.KeyFrame;
import javafx.animation.ScaleTransition;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Duration;
import webConnection.Client;

import java.net.URL;
import java.util.ArrayList;

public class MainMenu extends Application {
    public TextField opponentName;
    private static Alert alert;
    public Button gameMode;
    public Button goProfile;
    public Button gameMenu;
    public Button requestHistory;
    public Button randomGame;
    public Button register;
    private ScaleTransition scaleTransition;



    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        alert = new Alert(Alert.AlertType.NONE);
        stage.setHeight(720);
        stage.setWidth(1200);
        URL url = RegisterMenu.class.getResource("/FXML/MainMenu.fxml");
        Pane root = FXMLLoader.load(url);
        Scene scene = new Scene(root);
        stage.setScene(scene);
        root.setBackground(new Background(ApplicationController.createBackGroundImage("/backgrounds/Ciri_CGI_1920x1080_EN.jpg"
                , stage.getHeight(), stage.getWidth())));
        stage.show();
    }

    @FXML
    public void initialize() {
//        setScaleTransition();

        if (User.getLoggedUser().isPrivateGame()) gameMode.setText("Private");
        else gameMode.setText("Public");

        gameMode.setOnMouseClicked(mouseEvent -> {
            User.getLoggedUser().setPrivateGame(!User.getLoggedUser().isPrivateGame());
            if (User.getLoggedUser().isPrivateGame()) gameMode.setText("Private");
            else gameMode.setText("Public");
        });
    }

    private void setScaleTransition() {
        scaleTransition = new ScaleTransition(Duration.millis(200), gameMenu);
        scaleTransition.setToX(1.2); // Scale factor for x-axis
        scaleTransition.setToY(1.2); // Scale factor for y-axis
        scaleTransition = new ScaleTransition(Duration.millis(200), goProfile);
        scaleTransition.setToX(1.2); // Scale factor for x-axis
        scaleTransition.setToY(1.2); // Scale factor for y-axis
        scaleTransition = new ScaleTransition(Duration.millis(200), gameMode);
        scaleTransition.setToX(1.2); // Scale factor for x-axis
        scaleTransition.setToY(1.2); // Scale factor for y-axis
        scaleTransition = new ScaleTransition(Duration.millis(200), randomGame);
        scaleTransition.setToX(1.2); // Scale factor for x-axis
        scaleTransition.setToY(1.2); // Scale factor for y-axis
        scaleTransition = new ScaleTransition(Duration.millis(200), requestHistory);
        scaleTransition.setToX(1.2); // Scale factor for x-axis
        scaleTransition.setToY(1.2); // Scale factor for y-axis

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
            Client.getConnection().doInServer("MainController", "searchForOpponent",
                    User.getLoggedUser().getUsername(),User.getLoggedUser().isPrivateGame());
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
            Client.getConnection().doInServer("MainController", "playWithFriend",
                    User.getLoggedUser().getUsername(), opponentName.getText(),User.getLoggedUser().isPrivateGame());
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
            objects.add(User.getLoggedUser().getUsername());
            alert1.getButtonTypes().setAll(accept, reject);
            alert1.showAndWait().ifPresent(response -> {
                if (response == accept) {
                    objects.add(User.getLoggedUser().getUsername());
                    Client.getConnection().doInServer("MainController", "acceptFriend", objects.toArray());
                } else if (response == reject) {
                    Client.getConnection().doInServer("MainController", "rejectFriend", objects.toArray());
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

    public static void showGameRequests(ArrayList<Object> objects) {
        Platform.runLater(() -> {
            ArrayList<String> names = (ArrayList<String>) objects.get(0);
            VBox newRoot = new VBox(10);
            newRoot.setPadding(new Insets(20));
            newRoot.setAlignment(Pos.TOP_CENTER);
            for (String name : names) {
                Label nameLabel = new Label(name);
                nameLabel.setStyle("-fx-font-weight: bold;-fx-background-color: #00ff59");
                newRoot.getChildren().add(nameLabel);
            }
            Scene newScene = new Scene(newRoot, 300, 200);
            Stage newStage = new Stage();
            newStage.setScene(newScene);
            newStage.setTitle("Game Requests");
            newStage.show();
            Button submitButton = new Button("OK");
            submitButton.setOnAction(event -> {
                newStage.close();
            });
        });
    }

    public static void showRandomGames(ArrayList<Object> objects) {
        Platform.runLater(() -> {
            ArrayList<String> names = (ArrayList<String>) objects.get(0);
            VBox newRoot = new VBox(10);
            newRoot.setAlignment(Pos.TOP_CENTER);
            newRoot.setPadding(new Insets(20));
            Scene newScene = new Scene(newRoot, 300, 200);
            Stage newStage = new Stage();
            newStage.setScene(newScene);
            newStage.setTitle("Random Games");
            newStage.show();
            Button submitButton = new Button("OK");
            submitButton.setOnAction(event -> {
                newStage.close();
            });
            for (String name : names) {
                Label nameLabel = new Label(name);
                nameLabel.setStyle("-fx-font-weight: bold;-fx-background-color: yellow");
                nameLabel.setOnMouseClicked(mouseEvent -> {
                    String[] parts = name.split(" ");
                    boolean privateGame;
                    if (parts[1].equals("private")) privateGame = true;
                    else privateGame = false;
                    Client.getConnection().doInServer("MainController", "startRandomGame",
                            User.getLoggedUser().getUsername(), parts[0], privateGame);
                    newStage.close();
                });
                newRoot.getChildren().add(nameLabel);
            }

        });
    }

    public static void showCurrentGames(ArrayList<Object> objects) {
        Platform.runLater(() -> {
            ArrayList<String> names = (ArrayList<String>) objects.get(0);
            VBox newRoot = new VBox(10);
            newRoot.setAlignment(Pos.TOP_CENTER);
            newRoot.setPadding(new Insets(20));
            Scene newScene = new Scene(newRoot, 300, 200);
            Stage newStage = new Stage();
            newStage.setScene(newScene);
            newStage.setTitle("TV");
            newStage.show();
            Button submitButton = new Button("OK");
            submitButton.setOnAction(event -> {
                newStage.close();
            });
            for (String name : names) {
                Label nameLabel = new Label(name);
                nameLabel.setStyle("-fx-font-weight: bold;-fx-background-color: yellow");
                nameLabel.setOnMouseClicked(mouseEvent -> {
                    String[] parts = name.split(" ");
                    boolean privateGame;
                    if (parts[2].equals("private")) privateGame = true;
                    else privateGame = false;
                    Client.getConnection().doInServer("MainController", "seeGame",
                            User.getLoggedUser().getUsername(), parts[0],parts[1], privateGame);
                    newStage.close();
                });
                newRoot.getChildren().add(nameLabel);
            }

        });
    }

    public static void showGame(ArrayList<Object> objects) throws Exception {
        Gson gson = new Gson();
        User user = gson.fromJson(gson.toJson(objects.get(0)), User.class);
        User user2 = gson.fromJson(gson.toJson(objects.get(1)), User.class);
        user.readyForGame();
        user2.readyForGame();
        user.setFaction(Faction.giveFactionByName(user.getFactionName()));
        user.setLeader(Leader.giveLeaderByNameAndFaction(user.getLeaderName(), user.getFaction()));
        user2.setFaction(Faction.giveFactionByName(user2.getFactionName()));
        user2.setLeader(Leader.giveLeaderByNameAndFaction(user2.getLeaderName(), user2.getFaction()));
        user.setOpponentUser(user2);
        user2.setOpponentUser(user);
        Platform.runLater(() -> {
            Spectator spectator = new Spectator();
            Spectator.setGameUser(user);
            try {
                spectator.start(ApplicationController.getStage());
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });
    }

    public static void privateGame(ArrayList<Object> objects) {
        Platform.runLater(() -> {
            Alert alert1 = new Alert(Alert.AlertType.ERROR);
            alert1.setTitle("Error");
            alert1.setHeaderText("Private Game");
            alert1.setContentText("You don't have access to this private game.");
            alert1.show();
            Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(3), event -> alert1.close()));
            timeline.setCycleCount(1);
            timeline.play();
        });
    }


    public void requestHistories(MouseEvent actionEvent) {
        Client.getConnection().doInServer("MainController","getGameRequests",User.getLoggedUser().getUsername());
    }

    public void randomGames(MouseEvent actionEvent) {
        Client.getConnection().doInServer("MainController","getRandomGames",User.getLoggedUser().getUsername());
    }

    public void TV(MouseEvent mouseEvent) {
        Client.getConnection().doInServer("MainController","getCurrentGames",User.getLoggedUser().getUsername());
    }
    public void enlargeButton(MouseEvent event) {
        // Expand button on mouse enter
        Button button = (Button) event.getSource();
        button.setScaleX(1.1);
        button.setScaleY(1.1);

    }

    public void shrinkButton(MouseEvent event) {
        // Shrink button on mouse exit
        Button button = (Button) event.getSource();
        button.setScaleX(1.0);
        button.setScaleY(1.0);
    }

    public void showGameHistories(MouseEvent mouseEvent) {
//        Client.getConnection().doInServer("MainMenu","getGameHistories",User.getLoggedUser().getUsername());
    }

}
