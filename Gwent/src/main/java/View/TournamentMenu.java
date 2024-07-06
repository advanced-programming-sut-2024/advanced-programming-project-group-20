package View;

import Model.Tournament;
import Model.User;
import com.google.gson.Gson;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.RotateEvent;
import javafx.stage.Stage;
import javafx.util.Duration;
import webConnection.Client;

import java.util.ArrayList;

public class TournamentMenu extends Application {
    public Label pot0;
    public Label pot1;
    public Label pot2;
    public Label pot3;
    public Label pot4;
    public Label pot5;
    public Label pot6;
    public Label pot7;
    public Label pot8;
    public Label pot9;
    public Label pot10;
    public Label pot11;
    public Label pot12;
    public Label pot13;
    public Label pot14;
    public Label pot15;
    public Label pot16;
    public Label pot17;
    public Label pot18;
    public Label pot19;
    public Label pot20;
    public Label pot21;
    public Label pot22;
    public Label pot23;
    public Label pot24;
    public Label pot25;
    public Label pot26;
    public Label pot27;
    public Label champion;
    public Button register;
    public Button play;
    public Button activeGames;
    private ArrayList<Label> pots = new ArrayList<>();

    @Override
    public void start(Stage stage) throws Exception {

    }
    @FXML
    public void initialize() {
        setPots();
    }
    private void setPots() {
        pots.add(pot0);
        pots.add(pot1);
        pots.add(pot2);
        pots.add(pot3);
        pots.add(pot4);
        pots.add(pot5);
        pots.add(pot6);
        pots.add(pot7);
        pots.add(pot8);
        pots.add(pot9);
        pots.add(pot10);
        pots.add(pot11);
        pots.add(pot12);
        pots.add(pot13);
        pots.add(pot14);
        pots.add(pot15);
        pots.add(pot16);
        pots.add(pot17);
        pots.add(pot18);
        pots.add(pot19);
        pots.add(pot20);
        pots.add(pot21);
        pots.add(pot22);
        pots.add(pot23);
        pots.add(pot24);
        pots.add(pot25);
        pots.add(pot26);
        pots.add(pot27);
    }

    public void setContents() {
        for (int i = 0; i < 28; i++) {
            if (Tournament.getTournament().getTable()[i] == null) {
                pots.get(i).setText(Tournament.getTournament().getTable()[i]);
            }
        }
        if (Tournament.getTournament().getTable()[7] != null) ApplicationController.getRoot().getChildren().remove(register);
        else {
            ApplicationController.getRoot().getChildren().remove(play);
            ApplicationController.getRoot().getChildren().remove(activeGames);
        }
        if (!Tournament.getTournament().getNames().contains(User.getLoggedUser().getUsername())) {
            ApplicationController.getRoot().getChildren().remove(play);
        }
    }

    public void activeGame(MouseEvent mouseEvent) {
        Client.getConnection().doInServer("TournamentController","getCurrentGames",User.getLoggedUser().getUsername());
    }

    public void play(MouseEvent mouseEvent) {
        Client.getConnection().doInServer("TournamentController","gameSender",User.getLoggedUser().getUsername());
    }

    public void register (MouseEvent mouseEvent) {
        Client.getConnection().doInServer("TournamentController","register",User.getLoggedUser().getUsername());
    }

    public void reload(MouseEvent mouseEvent) {
        setContents();
    }

    public static void updateTournament(ArrayList<Object> objects) {
        Gson gson = new Gson();
        Tournament tournament = gson.fromJson(gson.toJson(objects), Tournament.class);
        Tournament.setTournament(tournament);
    }

    public static void notHaveOpp(ArrayList<Object> objects) {
        Platform.runLater(() -> {
            Alert alert1 = new Alert(Alert.AlertType.ERROR);
            alert1.setTitle("Error");
            alert1.setHeaderText("You Should Wait");
            alert1.setContentText("Your opponent not determined yet.");
            alert1.show();
            Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(3), event -> alert1.close()));
            timeline.setCycleCount(1);
            timeline.play();
        });
    }

    public static void offlineOpp(ArrayList<Object> objects) {
        Platform.runLater(() -> {
            Alert alert1 = new Alert(Alert.AlertType.ERROR);
            alert1.setTitle("Error");
            alert1.setHeaderText("Offline Opponent");
            alert1.setContentText("Your Opponent is offline.");
            alert1.show();
            Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(3), event -> alert1.close()));
            timeline.setCycleCount(1);
            timeline.play();
        });
    }

    public static void gameRequest(ArrayList<Object> objects) {
        Platform.runLater(() -> {
            Alert alert1 = new Alert(Alert.AlertType.CONFIRMATION);
            alert1.setTitle("Tournament Game");
            alert1.setHeaderText("Game in Tournament");
            alert1.setContentText("Your opponent is waiting for you.");
            ButtonType accept = new ButtonType("Accept");
            ButtonType reject = new ButtonType("Give Up");
            alert1.getButtonTypes().setAll(accept, reject);
            alert1.showAndWait().ifPresent(response -> {
                if (response == accept) {
                    Client.getConnection().doInServer("TournamentController", "acceptGame", User.getLoggedUser().getUsername());
                } else if (response == reject) {
                    Client.getConnection().doInServer("TournamentController", "giveUp", User.getLoggedUser().getUsername());
                }
            });
        });
    }
}
