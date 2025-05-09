package hospital.ui;

import hospital.objects.Doctor;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class DoctorDashboard {
    private Stage primaryStage;
    private DashboardManager dashboardManager;

    public DoctorDashboard(Stage primaryStage, DashboardManager dashboardManager) {
        this.primaryStage = primaryStage;
        this.dashboardManager = dashboardManager;
    }

    public void show(Doctor doctor) {
        VBox root = new VBox(10);
        root.setPadding(new Insets(20));
        
        Text title = new Text("Doctor Dashboard - " + doctor.getFullName());
        title.setFont(Font.font("Tahoma", FontWeight.BOLD, 24));
        
        Button viewAppointments = new Button("View Appointments");
        Button managePatients = new Button("Manage Patients");
        Button viewSchedule = new Button("View Schedule");
        Button logout = new Button("Logout");
        
        viewAppointments.setOnAction(e -> {
            // TODO: Show appointments
            System.out.println("Showing appointments...");
        });
        
        managePatients.setOnAction(e -> {
            // TODO: Show patient management
            System.out.println("Showing patient management...");
        });
        
        viewSchedule.setOnAction(e -> {
            // TODO: Show schedule
            System.out.println("Showing schedule...");
        });

        logout.setOnAction(e -> {
            // Return to login screen
            LoginScreen loginScreen = new LoginScreen(primaryStage, dashboardManager.getUsers());
            loginScreen.show();
        });
        
        root.getChildren().addAll(title, viewAppointments, managePatients, viewSchedule, logout);
        
        Scene scene = new Scene(root, 1280, 720);
        primaryStage.setScene(scene);
    }
} 