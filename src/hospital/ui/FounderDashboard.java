package hospital.ui;

import hospital.objects.Founder;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class FounderDashboard {
    private Stage primaryStage;
    private DashboardManager dashboardManager;

    public FounderDashboard(Stage primaryStage, DashboardManager dashboardManager) {
        this.primaryStage = primaryStage;
        this.dashboardManager = dashboardManager;
    }

    public void show(Founder founder) {
        VBox root = new VBox(10);
        root.setPadding(new Insets(20));
        
        Text title = new Text("Founder Dashboard - " + founder.getFullName());
        title.setFont(Font.font("Tahoma", FontWeight.BOLD, 24));
        
        Button viewFinancials = new Button("View Financial Reports");
        Button manageDepartments = new Button("Manage Departments");
        Button viewStaff = new Button("View Staff");
        Button logout = new Button("Logout");
        
        viewFinancials.setOnAction(e -> {
            // TODO: Show financial reports
            System.out.println("Showing financial reports...");
        });
        
        manageDepartments.setOnAction(e -> {
            // TODO: Show department management
            System.out.println("Showing department management...");
        });
        
        viewStaff.setOnAction(e -> {
            // TODO: Show staff list
            System.out.println("Showing staff list...");
        });

        logout.setOnAction(e -> {
            // Return to login screen
            LoginScreen loginScreen = new LoginScreen(primaryStage, dashboardManager.getUsers());
            loginScreen.show();
        });
        
        root.getChildren().addAll(title, viewFinancials, manageDepartments, viewStaff, logout);
        
        Scene scene = new Scene(root, 1280, 720);
        primaryStage.setScene(scene);
    }
} 