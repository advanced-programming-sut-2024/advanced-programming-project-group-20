module grafic {
    requires javafx.fxml;
    requires javafx.media;
    requires javafx.controls;
    requires com.google.gson;
    exports View;
    opens View to javafx.fxml;
    exports Controller;
    opens Controller to javafx.fxml;
    opens Model to com.google.gson, javafx.base;
}