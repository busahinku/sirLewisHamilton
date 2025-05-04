module hospital {
    requires javafx.controls;
    requires javafx.fxml;
    
    opens hospital.ui to javafx.fxml;
    opens hospital.ui.login to javafx.fxml;
    
    exports hospital.ui;
    exports hospital.ui.login;
}
