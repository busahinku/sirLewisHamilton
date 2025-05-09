package hospital.ui;

import hospital.objects.Pharmacist;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class PharmacistDashboard {
    private Stage primaryStage;
    private DashboardManager dashboardManager;

    public PharmacistDashboard(Stage primaryStage, DashboardManager dashboardManager) {
        this.primaryStage = primaryStage;
        this.dashboardManager = dashboardManager;
    }

    public void show(Pharmacist pharmacist) {
        VBox root = new VBox(10);
        root.setPadding(new Insets(20));
        
        Text title = new Text("Pharmacist Dashboard - " + pharmacist.getFullName());
        title.setFont(Font.font("Tahoma", FontWeight.BOLD, 24));
        
        Button manageInventory = new Button("Manage Inventory");
        Button viewPrescriptions = new Button("View Prescriptions");
        Button dispenseMedication = new Button("Dispense Medication");
        Button logout = new Button("Logout");
        
        manageInventory.setOnAction(e -> {
            // TODO: Show inventory management
            System.out.println("Showing inventory management...");
        });
        
        viewPrescriptions.setOnAction(e -> {
            // TODO: Show prescriptions
            System.out.println("Showing prescriptions...");
        });
        
        dispenseMedication.setOnAction(e -> {
            // TODO: Show medication dispensing
            System.out.println("Showing medication dispensing...");
        });

        logout.setOnAction(e -> {
            // Return to login screen
            LoginScreen loginScreen = new LoginScreen(primaryStage, dashboardManager.getUsers());
            loginScreen.show();
        });
        
        root.getChildren().addAll(title, manageInventory, viewPrescriptions, dispenseMedication, logout);
        
        Scene scene = new Scene(root, 1280, 720);
        primaryStage.setScene(scene);
    }
} 