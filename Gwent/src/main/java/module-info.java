module grafic {
    requires javafx.fxml;
    requires javafx.media;
    requires javafx.controls;
    exports View;
    opens View to javafx.fxml;
    exports Controller;
    opens Controller to javafx.fxml;
}