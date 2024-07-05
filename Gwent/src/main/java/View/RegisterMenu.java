package View;

import View.ApplicationController;
//import Controller.RegisterController;
import Controller.RegisterController;
import Model.Factions.Nilfgaard;
import Model.GameHistory;
import Model.User;
import com.google.gson.*;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Screen;
import javafx.stage.Stage;
import webConnection.Client;

import java.net.URL;
import java.util.ArrayList;

public class RegisterMenu extends Application {
    public static Pane root;
    @FXML
    private TextField repeatedPasswordField;
    @FXML
    private TextField emailField;
    @FXML
    private TextField nickNameField;
    @FXML
    private TextField passwordField;
    @FXML
    private TextField usernameField;


    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        //setting title and Icon
        ApplicationController.setStage(stage);
        stage.setTitle("Gwent");
        //======================
        stage.setHeight(720);
        stage.setWidth(900);
        SetHeightAndWidth(stage);
        URL url = RegisterMenu.class.getResource("/FXML/RegisterMenu.fxml");
        root = FXMLLoader.load(url);
        Scene scene = new Scene(root);
        stage.centerOnScreen();
        stage.setScene(scene);
        ApplicationController.setIcon();
        if (User.getAllUsers().isEmpty()) {
            Client.getConnection().doInServer("RegisterController", "parseFile", new ArrayList<Object>());
        }
        root.setBackground(new Background(ApplicationController.createBackGroundImage("/backgrounds/hh.jpg", stage.getHeight(), stage.getWidth())));
        stage.show();
    }

    public static void loadAllUsersFromServer(ArrayList<Object> objects) {
        Gson gson = new Gson();
//todo check
        User.getAllUsers().clear();
        User.setAllUsers(new ArrayList<>());
        for (Object object : objects) {
            if (object==null)
                continue;
            User user = gson.fromJson(gson.toJson(object), User.class);
            System.out.println("load from server userss"+ user.getUsername());
            User.getAllUsers().add(user);
        }
    }

    public static void printRespond(String respond) {
        System.out.println(respond);
    }

    private void SetHeightAndWidth(Stage stage) {
        stage.setX((Screen.getPrimary().getVisualBounds().getWidth() - stage.getWidth() - 400) / 2);
        stage.setY((Screen.getPrimary().getVisualBounds().getHeight() - stage.getHeight() - 300) / 2);
    }

    public void goToLoginMenu(MouseEvent mouseEvent) {
        try {
            new LoginMenu().start(ApplicationController.getStage());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void register(MouseEvent mouseEvent) {
        ArrayList<Object> objects = new ArrayList<>();

        objects.add(usernameField.getText());
        objects.add(passwordField.getText());
        objects.add(emailField.getText());
        objects.add(nickNameField.getText());
        objects.add(repeatedPasswordField.getText());
        Client.getConnection().doInServer("RegisterController", "register", objects.toArray());
    }

    //TODO delete this later
    public void gotoMainMenu(MouseEvent mouseEvent) throws Exception {
        Object[] objects = new Object[2];
        objects[0] = "hamid";
        objects[1] = "ali";
        Client.getConnection().doInServer("PreGameController", "getUserByName", objects);
    }
    public static void setLogin(ArrayList<Object> objects) throws Exception {
        Gson gson = new Gson();
        User user = gson.fromJson(gson.toJson(objects.get(0)), User.class);
        User.setLoggedUser(user);
        User opponent = gson.fromJson(gson.toJson(objects.get(1)), User.class);
        user.setOpponentUser(opponent);
        opponent.setOpponentUser(user);
        User.getLoggedUser().readyForGame();
        User.getLoggedUser().getOpponentUser().readyForGame();
        Platform.runLater(() -> {
            PreGameMenu preGameMenu = new PreGameMenu();
            try {
                preGameMenu.start(ApplicationController.getStage());
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });

    }

//    public void makeRandomPassword(MouseEvent mouseEvent) {
//        RegisterController.randomPassword(passwordField, repeatedPasswordField);
//    }
    public void makeRandomPassword(MouseEvent mouseEvent) {
        ArrayList<Object> objects = new ArrayList<>();
        objects.add(passwordField.getText());
        objects.add(repeatedPasswordField.getText());
        //todo
        Client.getConnection().doInServer("RegisterController", "randomPassword", objects.toArray());

//        RegisterController.randomPassword(objects);
    }

    public void WriteRandomPasswordFromServer(ArrayList<Object> objects) {
        passwordField.setText((String) objects.get(0));
        repeatedPasswordField.setText((String) objects.get(1));
    }

    public static void showSecurityQuestions(ArrayList<Object> objects) {
        String username = (String) objects.get(0);
        String password = (String) objects.get(1);
        String email = (String) objects.get(2);
        String nickName = (String) objects.get(3);

        ApplicationController.setDisable(RegisterMenu.root);
//        puting white rectangle
        Rectangle whiteOverlay = new Rectangle(ApplicationController.getStage()
                .getWidth(), ApplicationController.getStage().getWidth(), Color.rgb(255, 255, 255, 0.8));
        Platform.runLater(() -> {
            RegisterMenu.root.getChildren().add(whiteOverlay);
        });
        Label label4 = new Label("Answer one of these questions and press continue:)");
        label4.setLayoutX(90);
        label4.setLayoutY(100);
        label4.setPrefHeight(100);
        label4.setPrefWidth(800);
        label4.setStyle("-fx-font-size: 30");
        Label label = new Label("What's your father's name?");
        label.setLayoutX(50);
        label.setLayoutY(200);
        label.setPrefHeight(100);
        label.setPrefWidth(500);
        label.setStyle("-fx-font-size: 30");
        Label label1 = new Label("What sport do you like most?");
        label1.setLayoutX(100);
        label1.setLayoutY(300);
        label1.setPrefHeight(100);
        label1.setPrefWidth(500);
        label1.setStyle("-fx-font-size: 30");
        Label label2 = new Label("Who was your first grade teacher?");
        label2.setLayoutX(120);
        label2.setLayoutY(400);
        label2.setPrefHeight(120);
        label2.setPrefWidth(500);
        label2.setStyle("-fx-font-size: 30");
//========================textFields
        TextField textField = new TextField();
        textField.setLayoutX(450);
        textField.setLayoutY(225);
        textField.setPrefHeight(50);
        textField.setPrefWidth(200);
        textField.setStyle("-fx-background-color: #ece7e7");
        TextField textField1 = new TextField();
        textField1.setLayoutX(525);
        textField1.setLayoutY(325);
        textField1.setPrefHeight(50);
        textField1.setPrefWidth(200);
        textField1.setStyle("-fx-background-color: #ece7e7");
        TextField textField2 = new TextField();
        textField2.setLayoutX(625);
        textField2.setLayoutY(425);
        textField2.setPrefHeight(50);
        textField2.setPrefWidth(200);
        textField2.setStyle("-fx-background-color: #ece7e7");


        //
        Button button1 = new Button("continue");
        button1.setLayoutX(400);
        button1.setLayoutY(550);
        button1.setPrefHeight(50);
        button1.setPrefWidth(200);
        button1.setOnAction(e -> {

            if ((textField.getText().isEmpty() && textField1.getText().isEmpty() && !textField2.getText().isEmpty())) {
                addANewUser(username, password, email, nickName, label2, textField2);

                Client.getConnection().doInServer("RegisterController", "addUserToServerModel"
                        , username, password, email, nickName, label2.getText(), textField2.getText());

            } else if (textField.getText().isEmpty() && !textField1.getText().isEmpty() && textField2.getText().isEmpty()) {
                addANewUser(username, password, email, nickName, label1, textField1);

                Client.getConnection().doInServer("RegisterController", "addUserToServerModel"
                        , username, password, email, nickName, label1.getText(), textField1.getText());

            } else if (!textField.getText().isEmpty() && textField1.getText().isEmpty() && textField2.getText().isEmpty()) {
                addANewUser(username, password, email, nickName, label, textField);

                Client.getConnection().doInServer("RegisterController", "addUserToServerModel",
                       User.getUserByName(username));

            } else {
                ApplicationController.alert("error", "answer exactly one of questions!!");
            }
        });
        Platform.runLater(() -> {
            RegisterMenu.root.getChildren().add(label4);
            RegisterMenu.root.getChildren().add(label);
            RegisterMenu.root.getChildren().add(label1);
            RegisterMenu.root.getChildren().add(label2);
            RegisterMenu.root.getChildren().add(textField);
            RegisterMenu.root.getChildren().add(textField1);
            RegisterMenu.root.getChildren().add(textField2);
            RegisterMenu.root.getChildren().add(button1);
        });

    }



    public static void addANewUser(String username, String password, String email
            , String nickName, Label secureQuestion, TextField secureAnswer) {
        User user = new User(username, password,
                nickName, email, secureQuestion.getText(), secureAnswer.getText());
        User.setLoggedUser(User.giveUserByUsername(username));
        LoginMenu loginMenu = new LoginMenu();
        try {
            loginMenu.start(ApplicationController.getStage());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static void showConfirmAlertOfRandomPassword(ArrayList<Object> objects) {
        Platform.runLater(() -> {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Confirmation Dialog");
            alert.setHeaderText("Your password: " + objects.get(0));
            alert.setContentText("Do you want to set this for your password");

            alert.showAndWait().ifPresent(response -> {
                if (response == ButtonType.OK) {
                    for (Node node : root.getChildren()) {
                        if (node instanceof VBox) {
                            for (Node node1 : ((VBox) node).getChildren()) {
                                if (((TextField) node1).getPromptText().matches("(password|repeat password)")) {
                                    ((TextField) node1).setText((String) objects.get(0));
                                }
                            }
                        }
                    }
                }
            });
        });
    }
}