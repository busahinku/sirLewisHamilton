package hospital.ui;

import hospital.objects.Pharmacist;
import hospital.objects.Inventory;
import hospital.objects.Prescription;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Separator;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
import java.util.Arrays;
import java.util.List;

public class PharmacistDashboard {
    private Stage primaryStage;
    private DashboardManager dashboardManager;
    private List<Button> allSidebarButtons;
    // For demo, a static inventory list. Replace with your global inventory management.
    public static List<Inventory> inventoryList = new java.util.ArrayList<>();

    public PharmacistDashboard(Stage primaryStage, DashboardManager dashboardManager) {
        this.primaryStage = primaryStage;
        this.dashboardManager = dashboardManager;
    }

    public void show(Pharmacist pharmacist) {
        BorderPane root = new BorderPane();

        // Sidebar
        VBox sidebar = new VBox(18);
        sidebar.setPadding(new Insets(32, 0, 32, 0));
        sidebar.setStyle("-fx-background-color: #0a1622; -fx-min-width: 290px; -fx-max-width: 290px;");
        sidebar.setPrefWidth(290);

        // User Info
        VBox userBox = new VBox(0);
        Label nameLabel = new Label(pharmacist.getFullName());
        nameLabel.setFont(Font.font("Inter", FontWeight.BOLD, 28));
        nameLabel.setTextFill(Color.WHITE);
        Label roleLabel = new Label("Pharmacist");
        roleLabel.setFont(Font.font("Inter", FontWeight.NORMAL, 14));
        roleLabel.setTextFill(Color.LIGHTGRAY);
        userBox.getChildren().addAll(nameLabel, roleLabel);
        userBox.setPadding(new Insets(0, 0, 18, 24));

        Separator sep1 = new Separator();
        sep1.setStyle("-fx-border-color: #000B19; -fx-border-width: 1 0 0 0; -fx-background-color: #000B19; -fx-color: #000B19;");
        sep1.setPrefWidth(180);
        sep1.setPadding(new Insets(0, 0, 0, 0));

        // Navigation Buttons
        VBox navBox = new VBox(8);
        navBox.setPadding(new Insets(0, 0, 0, 12));
        Button btnMain = new Button("Main Page");
        Button btnInventory = new Button("Manage Inventory");
        Button btnPrescriptions = new Button("View Prescriptions");
        Button btnDispense = new Button("Dispense Medication");
        Label systemLabel = new Label("System");
        systemLabel.setTextFill(Color.WHITE);
        systemLabel.setFont(Font.font("Inter", FontWeight.BOLD, 15));
        systemLabel.setPadding(new Insets(18, 0, 0, 0));
        Button btnLogout = new Button("Log Out");
        btnLogout.setStyle("-fx-background-color: #dc2626; -fx-text-fill: white; -fx-font-size: 16; -fx-background-radius: 8; -fx-alignment: CENTER_LEFT; -fx-padding: 8 0 8 18; -fx-min-width: 220; -fx-cursor: hand;");
        btnLogout.setOnMouseEntered(e -> btnLogout.setStyle("-fx-background-color: #b91c1c; -fx-text-fill: white; -fx-font-size: 16; -fx-background-radius: 8; -fx-alignment: CENTER_LEFT; -fx-padding: 8 0 8 18; -fx-min-width: 220; -fx-cursor: hand;"));
        btnLogout.setOnMouseExited(e -> btnLogout.setStyle("-fx-background-color: #dc2626; -fx-text-fill: white; -fx-font-size: 16; -fx-background-radius: 8; -fx-alignment: CENTER_LEFT; -fx-padding: 8 0 8 18; -fx-min-width: 220; -fx-cursor: hand;"));
        allSidebarButtons = Arrays.asList(btnMain, btnInventory, btnPrescriptions, btnDispense);
        for (Button b : allSidebarButtons) {
            b.setStyle("-fx-background-color: transparent; -fx-text-fill: #e0e6ed; -fx-font-size: 16; -fx-alignment: CENTER_LEFT; -fx-padding: 8 0 8 18;");
            b.setPrefWidth(220);
        }
        btnMain.setStyle("-fx-background-color: #2563eb; -fx-text-fill: white; -fx-font-size: 16; -fx-background-radius: 8; -fx-alignment: CENTER_LEFT; -fx-padding: 8 0 8 18;");
        navBox.getChildren().addAll(btnMain, btnInventory, btnPrescriptions, btnDispense, systemLabel, btnLogout);
        sidebar.getChildren().addAll(userBox, sep1, navBox);

        // Main Content Area
        StackPane mainContent = new StackPane();
        mainContent.setPadding(new Insets(32, 32, 32, 32));

        // Main Page Content
        VBox mainPage = createMainPage(pharmacist);
        ScrollPane mainScrollPane = new ScrollPane(mainPage);
        mainScrollPane.setFitToWidth(true);
        mainScrollPane.setStyle("-fx-background-color:transparent;");
        mainContent.getChildren().clear();
        mainContent.getChildren().add(mainScrollPane);

        // Sidebar navigation actions
        btnMain.setOnAction(e -> {
            mainContent.getChildren().clear();
            VBox refreshedMainPage = createMainPage(pharmacist);
            ScrollPane refreshedScrollPane = new ScrollPane(refreshedMainPage);
            refreshedScrollPane.setFitToWidth(true);
            refreshedScrollPane.setStyle("-fx-background-color:transparent;");
            mainContent.getChildren().add(refreshedScrollPane);
            setActiveSidebarButton(btnMain);
        });
        btnInventory.setOnAction(e -> {
            setActiveSidebarButton(btnInventory);
            VBox invPage = new VBox(16);
            invPage.setPadding(new Insets(18));
            invPage.setStyle("-fx-background-color: #fff; -fx-background-radius: 12; -fx-effect: dropshadow(gaussian, #e0e0e0, 8, 0, 0, 2);");
            invPage.setMaxWidth(800);
            invPage.setMinWidth(400);
            Label title = new Label("Manage Inventory");
            title.setFont(Font.font("Inter", FontWeight.BOLD, 22));
            title.setTextFill(Color.web("#222"));
            invPage.getChildren().add(title);
            if (inventoryList.isEmpty()) {
                Label info = new Label("No inventory items found.");
                info.setFont(Font.font("Inter", 15));
                info.setTextFill(Color.web("#333"));
                invPage.getChildren().add(info);
            } else {
                for (Inventory item : inventoryList) {
                    VBox itemBox = new VBox(2);
                    itemBox.setStyle("-fx-background-color: #f1f5f9; -fx-background-radius: 8; -fx-padding: 10 12 10 12; -fx-effect: dropshadow(gaussian, #e0e0e0, 2, 0, 0, 1);");
                    Label info = new Label(item.GeneralInfo());
                    info.setFont(Font.font("Inter", 14));
                    info.setTextFill(Color.web("#222"));
                    info.setWrapText(true);
                    HBox btnBox = new HBox(8);
                    Button addStockBtn = new Button("Add Stock");
                    addStockBtn.setStyle("-fx-background-color: #22c55e; -fx-text-fill: white; -fx-background-radius: 8;");
                    addStockBtn.setOnAction(ev -> {
                        item.addStock(10); // Demo: add 10 units
                        showAlert("Stock Added", "Added 10 units to " + item.getItemName());
                        btnInventory.fire();
                    });
                    Button removeStockBtn = new Button("Remove Stock");
                    removeStockBtn.setStyle("-fx-background-color: #ef4444; -fx-text-fill: white; -fx-background-radius: 8;");
                    removeStockBtn.setOnAction(ev -> {
                        item.removeStock(5); // Demo: remove 5 units
                        showAlert("Stock Removed", "Removed 5 units from " + item.getItemName());
                        btnInventory.fire();
                    });
                    btnBox.getChildren().addAll(addStockBtn, removeStockBtn);
                    itemBox.getChildren().addAll(info, btnBox);
                    invPage.getChildren().add(itemBox);
                }
            }
            ScrollPane scrollPane = new ScrollPane(invPage);
            scrollPane.setFitToWidth(true);
            scrollPane.setStyle("-fx-background-color:transparent;");
            mainContent.getChildren().clear();
            mainContent.getChildren().add(scrollPane);
        });
        btnPrescriptions.setOnAction(e -> {
            setActiveSidebarButton(btnPrescriptions);
            VBox prescPage = new VBox(16);
            prescPage.setPadding(new Insets(18));
            prescPage.setStyle("-fx-background-color: #fff; -fx-background-radius: 12; -fx-effect: dropshadow(gaussian, #e0e0e0, 8, 0, 0, 2);");
            prescPage.setMaxWidth(800);
            prescPage.setMinWidth(400);
            Label title = new Label("View Prescriptions");
            title.setFont(Font.font("Inter", FontWeight.BOLD, 22));
            title.setTextFill(Color.web("#222"));
            prescPage.getChildren().add(title);
            List<Prescription> prescriptions = pharmacist.getPrescriptions();
            if (prescriptions.isEmpty()) {
                Label info = new Label("No prescriptions found.");
                info.setFont(Font.font("Inter", 15));
                info.setTextFill(Color.web("#333"));
                prescPage.getChildren().add(info);
            } else {
                for (Prescription p : prescriptions) {
                    VBox pBox = new VBox(2);
                    pBox.setStyle("-fx-background-color: #f1f5f9; -fx-background-radius: 8; -fx-padding: 10 12 10 12; -fx-effect: dropshadow(gaussian, #e0e0e0, 2, 0, 0, 1);");
                    Label info = new Label(p.GeneralInfo());
                    info.setFont(Font.font("Inter", 14));
                    info.setTextFill(Color.web("#222"));
                    info.setWrapText(true);
                    pBox.getChildren().add(info);
                    prescPage.getChildren().add(pBox);
                }
            }
            ScrollPane scrollPane = new ScrollPane(prescPage);
            scrollPane.setFitToWidth(true);
            scrollPane.setStyle("-fx-background-color:transparent;");
            mainContent.getChildren().clear();
            mainContent.getChildren().add(scrollPane);
        });
        btnDispense.setOnAction(e -> {
            setActiveSidebarButton(btnDispense);
            VBox dispPage = new VBox(16);
            dispPage.setPadding(new Insets(18));
            dispPage.setStyle("-fx-background-color: #fff; -fx-background-radius: 12; -fx-effect: dropshadow(gaussian, #e0e0e0, 8, 0, 0, 2);");
            dispPage.setMaxWidth(800);
            dispPage.setMinWidth(400);
            Label title = new Label("Dispense Medication");
            title.setFont(Font.font("Inter", FontWeight.BOLD, 22));
            title.setTextFill(Color.web("#222"));
            dispPage.getChildren().add(title);
            List<Prescription> prescriptions = pharmacist.getPrescriptions();
            if (prescriptions.isEmpty()) {
                Label info = new Label("No prescriptions to dispense.");
                info.setFont(Font.font("Inter", 15));
                info.setTextFill(Color.web("#333"));
                dispPage.getChildren().add(info);
            } else {
                for (Prescription p : prescriptions) {
                    VBox pBox = new VBox(2);
                    pBox.setStyle("-fx-background-color: #f1f5f9; -fx-background-radius: 8; -fx-padding: 10 12 10 12; -fx-effect: dropshadow(gaussian, #e0e0e0, 2, 0, 0, 1);");
                    Label info = new Label(p.GeneralInfo());
                    info.setFont(Font.font("Inter", 14));
                    info.setTextFill(Color.web("#222"));
                    info.setWrapText(true);
                    Button dispenseBtn = new Button("Dispense");
                    dispenseBtn.setStyle("-fx-background-color: #2563eb; -fx-text-fill: white; -fx-background-radius: 8;");
                    dispenseBtn.setOnAction(ev -> {
                        pharmacist.dispenseMedication(p);
                        showAlert("Medication Dispensed", "Medication dispensed for prescription: " + p.GeneralInfo());
                        btnDispense.fire();
                    });
                    pBox.getChildren().addAll(info, dispenseBtn);
                    dispPage.getChildren().add(pBox);
                }
            }
            ScrollPane scrollPane = new ScrollPane(dispPage);
            scrollPane.setFitToWidth(true);
            scrollPane.setStyle("-fx-background-color:transparent;");
            mainContent.getChildren().clear();
            mainContent.getChildren().add(scrollPane);
        });
        btnLogout.setOnAction(e -> {
            LoginScreen loginScreen = new LoginScreen(primaryStage, dashboardManager.getUsers());
            loginScreen.show();
        });
        
        root.setLeft(sidebar);
        root.setCenter(mainContent);
        Scene scene = new Scene(root, 1280, 720);
        primaryStage.setScene(scene);
    }

    // Helper: Set active sidebar button style
    private void setActiveSidebarButton(Button activeButton) {
        for (Button b : allSidebarButtons) {
            b.setStyle("-fx-background-color: transparent; -fx-text-fill: #e0e6ed; -fx-font-size: 16; -fx-alignment: CENTER_LEFT; -fx-padding: 8 0 8 18;");
        }
        activeButton.setStyle("-fx-background-color: #2563eb; -fx-text-fill: white; -fx-font-size: 16; -fx-background-radius: 8; -fx-alignment: CENTER_LEFT; -fx-padding: 8 0 8 18;");
    }

    // Main page for pharmacist
    private VBox createMainPage(Pharmacist pharmacist) {
        VBox mainPage = new VBox(10);
        mainPage.setPadding(new Insets(10, 16, 10, 16));
        mainPage.setStyle("-fx-background-color: #fff; -fx-background-radius: 12; -fx-effect: dropshadow(gaussian, #e0e0e0, 8, 0, 0, 2;");
        mainPage.setMaxWidth(700);
        mainPage.setMinWidth(400);
        Label mainTitle = new Label("Welcome, " + pharmacist.getFullName());
        mainTitle.setFont(Font.font("Inter", FontWeight.BOLD, 26));
        mainTitle.setTextFill(Color.web("#222"));
        Label mainRole = new Label("Pharmacist");
        mainRole.setFont(Font.font("Inter", FontWeight.NORMAL, 15));
        mainRole.setTextFill(Color.web("#666"));
        Separator mainSep = new Separator();
        mainSep.setPadding(new Insets(0, 0, 4, 0));
        Label info = new Label(
            "Location: " + pharmacist.getLocation() +
            "\nSalary: $" + String.format("%.2f", pharmacist.getSalary()) +
            "\nWork Schedule: " + pharmacist.getWorkSchedule()
        );
        info.setFont(Font.font("Inter", 15));
        info.setTextFill(Color.web("#333"));
        info.setStyle("-fx-padding: 0 0 0 0;");
        info.setAlignment(javafx.geometry.Pos.TOP_LEFT);
        mainPage.getChildren().addAll(mainTitle, mainRole, mainSep, info);
        return mainPage;
    }

    // Helper: Show alert dialog
    private void showAlert(String title, String message) {
        javafx.scene.control.Alert alert = new javafx.scene.control.Alert(javafx.scene.control.Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
} 