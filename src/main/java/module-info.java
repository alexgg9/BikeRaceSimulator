module psp.bikeracesimulator {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.desktop;


    opens psp.bikeracesimulator to javafx.fxml;
    exports psp.bikeracesimulator;
}