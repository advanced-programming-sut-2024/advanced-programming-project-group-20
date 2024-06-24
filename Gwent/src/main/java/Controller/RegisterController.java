package Controller;

import Model.User;
import View.LoginMenu;
import View.PrivateFieldAdapter;
import View.RegisterMenu;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import javafx.scene.control.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import java.io.*;
import java.util.ArrayList;
import java.util.Random;

//import com.google.gson.Gson;
//import com.google.gson.reflect.TypeToken;
public class RegisterController {
    //  public AnchorPane root;
    private static void pickQuestion(int questionNumber, String answer, String answerConfirm) {

    }

    public static String login(String username, String password) {
        return null;
    }

    public static String forgetPassword(String username) {
        return null;
    }

    private static boolean checkAnswer(int questionNumber, String answer) {
        return true;
    }

    public static void register(TextField usernameField, TextField passwordField, TextField emailField, TextField nickNameField, TextField repeatedPasswordField) {
        if (usernameField.getText().isEmpty()) {
            ApplicationController.alert("invalid username", "username section is empty!");

        } else if (passwordField.getText().isEmpty()) {
            ApplicationController.alert("invalid password", "password section is empty!");

        } else if (emailField.getText().isEmpty()) {
            ApplicationController.alert("invalid email", "email section is empty!");

        } else if (nickNameField.getText().isEmpty()) {
            ApplicationController.alert("invalid nickname", "nickname section is empty!");

        } else if (User.giveUserByUsername(usernameField.getText()) != null) {
            ApplicationController.alert("invalid username", "this username already given");

        } else if (!usernameField.getText().matches("[-a-zA-Z0-9]+")) {
            ApplicationController.alert("invalid username", "username should contains a-z,A-Z,numbers and -");

        } else if (!emailField.getText().matches("[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}")) {
            ApplicationController.alert("invalid email", "enter a valid email");

        } else if (!passwordField.getText().matches("\\S+")) {
            ApplicationController.alert("invalid password", "password should contains a-z,A-Z,numbers and special characters");

        } else if (!passwordField.getText().matches("^(?=.*[A-Z])(?=.*[0-9])(?=.*[a-z])(?=.*[^a-zA-Z0-9\\s]).+$")) {
            ApplicationController.alert("invalid password", "password should contains a-z,A-Z,numbers and special characters");

        } else if (!passwordField.getText().equals(repeatedPasswordField.getText())) {
            ApplicationController.alert("repeated password is not similar with password", "enter a password or back to register menu");
            passwordField.setText("");
            repeatedPasswordField.setText("");

        } else {
            //first we show security questions then register
            showSecurityQuestions(usernameField, passwordField, nickNameField, emailField);
        }
    }

    private static void showSecurityQuestions(TextField usernameField, TextField passwordField, TextField nickNameField, TextField emailField) {
        ApplicationController.setDisable(RegisterMenu.root);
//        puting white rectangle
        Rectangle whiteOverlay = new Rectangle(ApplicationController.getStage().getWidth(), ApplicationController.getStage().getWidth(), Color.rgb(255, 255, 255, 0.8));
        RegisterMenu.root.getChildren().add(whiteOverlay);
        Label label4 = new Label("Answer one of these questions and press continue:)");
        label4.setLayoutX(90);
        label4.setLayoutY(100);
        label4.setPrefHeight(100);
        label4.setPrefWidth(800);
        label4.setStyle("-fx-font-size: 30");
        RegisterMenu.root.getChildren().add(label4);
        Label label = new Label("What's your father's name?");
        label.setLayoutX(50);
        label.setLayoutY(200);
        label.setPrefHeight(100);
        label.setPrefWidth(500);
        label.setStyle("-fx-font-size: 30");
        RegisterMenu.root.getChildren().add(label);
        Label label1 = new Label("What sport do you like most?");
        label1.setLayoutX(100);
        label1.setLayoutY(300);
        label1.setPrefHeight(100);
        label1.setPrefWidth(500);
        label1.setStyle("-fx-font-size: 30");
        RegisterMenu.root.getChildren().add(label1);
        Label label2 = new Label("Who was your first grade teacher?");
        label2.setLayoutX(120);
        label2.setLayoutY(400);
        label2.setPrefHeight(120);
        label2.setPrefWidth(500);
//        label2.setStyle("-fx-background-color: #cb1f1f");
        label2.setStyle("-fx-font-size: 30");

        RegisterMenu.root.getChildren().add(label2);
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
        RegisterMenu.root.getChildren().add(textField);
        RegisterMenu.root.getChildren().add(textField1);
        RegisterMenu.root.getChildren().add(textField2);
        //
        Button button1 = new Button("continue");
        button1.setLayoutX(400);
        button1.setLayoutY(550);
        button1.setPrefHeight(50);
        button1.setPrefWidth(200);
        button1.setOnAction(e -> {
            if ((textField.getText().isEmpty() && textField1.getText().isEmpty() && !textField2.getText().isEmpty())) {
                addANewUser(usernameField, passwordField, emailField, nickNameField, label2, textField2);
            } else if (textField.getText().isEmpty() && !textField1.getText().isEmpty() && textField2.getText().isEmpty()) {
                addANewUser(usernameField, passwordField, emailField, nickNameField, label1, textField1);
            } else if (!textField.getText().isEmpty() && textField1.getText().isEmpty() && textField2.getText().isEmpty()) {
                addANewUser(usernameField, passwordField, emailField, nickNameField, label, textField);
            } else {
                ApplicationController.alert("error", "answer exactly one of questions!!");
            }
        });
        RegisterMenu.root.getChildren().add(button1);
    }

    private static void saveTheUserInGson(ArrayList<User> list) {

//        File file = new File("users.json");
////        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        User user = new User("f", "f", "f", "f","f", "f");
////        Gson gson= new Gson();
//        Gson gson = new GsonBuilder()
//                .registerTypeAdapter(User.class, new PrivateFieldAdapter())
//                .create();
//        String json = gson.toJson(list);
//        try (PrintWriter pw = new PrintWriter(file)) {
//            pw.write(json);
//        } catch (FileNotFoundException e) {
//            e.printStackTrace();
//        }
        Gson gson = new Gson();
        String json = gson.toJson(user);
        System.out.println(json);

        try (FileWriter writer = new FileWriter("users.json")) {
            gson.toJson(user, writer);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


    public static void addANewUser(TextField usernameField, TextField passwordField, TextField emailField
            , TextField nickNameField, Label secureQuestion, TextField secureAnswer) {
        User user = new User(usernameField.getText(), passwordField.getText(),
                nickNameField.getText(), emailField.getText(), secureQuestion.getText(), secureAnswer.getText());
//        saveTheUserInGson(User.getAllUsers());
//        user.saveUserToJSon();
        User.setLoggedUser(User.giveUserByUsername(usernameField.getText()));
        LoginMenu loginMenu = new LoginMenu();
        try {
            loginMenu.start(ApplicationController.getStage());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static void randomPassword(TextField passwordField, TextField repeatedPasswordField) {
        Random random = new Random();
        String uppercase = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        String lowercase = "abcdefghijklmnopqrstuvwxyz";
        String digits = "0123456789";
        String specialChars = "!@#$%^&*()-_=+[]{},.<>?";
        StringBuilder randomPassword = new StringBuilder(8);

        randomPassword.append(uppercase.charAt(random.nextInt(uppercase.length())));
        randomPassword.append(digits.charAt(random.nextInt(digits.length())));
        randomPassword.append(lowercase.charAt(random.nextInt(lowercase.length())));
        randomPassword.append(specialChars.charAt(random.nextInt(specialChars.length())));
        String validChars = uppercase + lowercase + digits + specialChars;
        for (int i = 4; i < 8; i++) {
            randomPassword.append(validChars.charAt(random.nextInt(validChars.length())));
        }
        //showing confirmation alert
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmation Dialog");
        alert.setHeaderText("your password: " + randomPassword);
        alert.setContentText("do you want to set this for your password");

        alert.showAndWait().ifPresent(response -> {
            if (response == ButtonType.OK) {
                passwordField.setText(String.valueOf(randomPassword));
                repeatedPasswordField.setText(String.valueOf(randomPassword));
            }
        });
    }


}
