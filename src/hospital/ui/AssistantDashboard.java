package hospital.ui;

import hospital.objects.Assistant;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class AssistantDashboard {
    private Stage primaryStage;
    private DashboardManager dashboardManager;

    public AssistantDashboard(Stage primaryStage, DashboardManager dashboardManager) {
        this.primaryStage = primaryStage;
        this.dashboardManager = dashboardManager;
    }

    public void show(Assistant assistant) {
        VBox root = new VBox(10);
        root.setPadding(new Insets(20));
        
        Text title = new Text("Assistant Dashboard - " + assistant.getFullName());
        title.setFont(Font.font("Tahoma", FontWeight.BOLD, 24));
        
        Button manageAppointments = new Button("Manage Appointments");
        Button viewSchedule = new Button("View Schedule");
        Button contactDoctor = new Button("Contact Doctor");
        Button logout = new Button("Logout");
        
        manageAppointments.setOnAction(e -> {
            // TODO: Show appointment management
            System.out.println("Showing appointment management...");
        });
        
        viewSchedule.setOnAction(e -> {
            // TODO: Show schedule
            System.out.println("Showing schedule...");
        });
        
        contactDoctor.setOnAction(e -> {
            // TODO: Show doctor contact
            System.out.println("Contacting doctor...");
        });

        logout.setOnAction(e -> {
            // Return to login screen
            LoginScreen loginScreen = new LoginScreen(primaryStage, dashboardManager.getUsers());
            loginScreen.show();
        });
        
        root.getChildren().addAll(title, manageAppointments, viewSchedule, contactDoctor, logout);
        
        Scene scene = new Scene(root, 1280, 720);
        primaryStage.setScene(scene);
    }
} 