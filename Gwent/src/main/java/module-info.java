module grafic {
    requires javafx.fxml;
    requires javafx.media;
    requires javafx.controls;
    requires com.google.gson;
    requires org.json;
    requires org.simplejavamail.core;
    requires org.simplejavamail;
    exports View;
    opens View to javafx.fxml;
    exports Controller;
    opens Controller to javafx.fxml;
    opens Model to com.google.gson, javafx.base;
    opens webConnection to com.google.gson;
    opens Model.chat to com.google.gson;
}