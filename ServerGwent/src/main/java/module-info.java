module ServerGwent {
    requires javafx.fxml;
    requires javafx.media;
    requires javafx.controls;
    requires com.google.gson;
    requires org.json;
    requires java.sql;
    exports Controller;
    exports WebConnection;
    opens Controller to javafx.fxml;
    opens Model to com.google.gson, javafx.base;
    opens WebConnection to  com.google.gson;
    opens Model.chat to com.google.gson;
}