package hospital.ui;

import hospital.objects.PrivateDoctors;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class PrivateDoctorDashboard {
    private Stage primaryStage;
    private DashboardManager dashboardManager;

    public PrivateDoctorDashboard(Stage primaryStage, DashboardManager dashboardManager) {
        this.primaryStage = primaryStage;
        this.dashboardManager = dashboardManager;
    }

    public void show(PrivateDoctors privateDoctor) {
        VBox root = new VBox(10);
        root.setPadding(new Insets(20));
        
        Text title = new Text("Private Doctor Dashboard - " + privateDoctor.getFullName());
        title.setFont(Font.font("Tahoma", FontWeight.BOLD, 24));
        
        Button viewPrivateAppointments = new Button("View Private Appointments");
        Button manageClinic = new Button("Manage Clinic");
        Button viewEarnings = new Button("View Earnings");
        Button logout = new Button("Logout");
        
        viewPrivateAppointments.setOnAction(e -> {
            // TODO: Show private appointments
            System.out.println("Showing private appointments...");
        });
        
        manageClinic.setOnAction(e -> {
            // TODO: Show clinic management
            System.out.println("Showing clinic management...");
        });
        
        viewEarnings.setOnAction(e -> {
            // TODO: Show earnings
            System.out.println("Showing earnings...");
        });

        logout.setOnAction(e -> {
            // Return to login screen
            LoginScreen loginScreen = new LoginScreen(primaryStage, dashboardManager.getUsers());
            loginScreen.show();
        });
        
        root.getChildren().addAll(title, viewPrivateAppointments, manageClinic, viewEarnings, logout);
        
        Scene scene = new Scene(root, 1280, 720);
        primaryStage.setScene(scene);
    }
} 