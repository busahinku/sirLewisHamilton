package hospital.ui;

import hospital.objects.Patient;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class PatientDashboard {
    private Stage primaryStage;
    private DashboardManager dashboardManager;

    public PatientDashboard(Stage primaryStage, DashboardManager dashboardManager) {
        this.primaryStage = primaryStage;
        this.dashboardManager = dashboardManager;
    }

    public void show(Patient patient) {
        VBox root = new VBox(10);
        root.setPadding(new Insets(20));
        
        Text title = new Text("Patient Dashboard - " + patient.getFullName());
        title.setFont(Font.font("Tahoma", FontWeight.BOLD, 24));
        
        Button viewAppointments = new Button("View Appointments");
        Button viewMedicalRecords = new Button("View Medical Records");
        Button viewBills = new Button("View Bills");
        Button logout = new Button("Logout");
        
        viewAppointments.setOnAction(e -> {
            // TODO: Show appointments
            System.out.println("Showing appointments...");
        });
        
        viewMedicalRecords.setOnAction(e -> {
            // TODO: Show medical records
            System.out.println("Showing medical records...");
        });
        
        viewBills.setOnAction(e -> {
            // TODO: Show bills
            System.out.println("Showing bills...");
        });

        logout.setOnAction(e -> {
            // Return to login screen
            LoginScreen loginScreen = new LoginScreen(primaryStage, dashboardManager.getUsers());
            loginScreen.show();
        });
        
        root.getChildren().addAll(title, viewAppointments, viewMedicalRecords, viewBills, logout);
        
        Scene scene = new Scene(root, 1280, 720);
        primaryStage.setScene(scene);
    }
} 