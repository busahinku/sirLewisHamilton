package hospital.ui;

import hospital.objects.Assistant;
import hospital.objects.Doctor;
import hospital.objects.Department;
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

public class AssistantDashboard {
    private Stage primaryStage;
    private DashboardManager dashboardManager;
    private List<Button> allSidebarButtons;

    public AssistantDashboard(Stage primaryStage, DashboardManager dashboardManager) {
        this.primaryStage = primaryStage;
        this.dashboardManager = dashboardManager;
    }

    public void show(Assistant assistant) {
        BorderPane root = new BorderPane();

        // Sidebar
        VBox sidebar = new VBox(18);
        sidebar.setPadding(new Insets(32, 0, 32, 0));
        sidebar.setStyle("-fx-background-color: #0a1622; -fx-min-width: 290px; -fx-max-width: 290px;");
        sidebar.setPrefWidth(290);

        // User Info
        VBox userBox = new VBox(0);
        Label nameLabel = new Label(assistant.getFullName());
        nameLabel.setFont(Font.font("Inter", FontWeight.BOLD, 28));
        nameLabel.setTextFill(Color.WHITE);
        Label roleLabel = new Label("Assistant");
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
        Button btnDoctor = new Button("Assigned Doctor");
        Button btnDept = new Button("Assigned Department");
        Button btnSchedule = new Button("My Schedule");
        Button btnAppointments = new Button("Manage Appointments");
        Label systemLabel = new Label("System");
        systemLabel.setTextFill(Color.WHITE);
        systemLabel.setFont(Font.font("Inter", FontWeight.BOLD, 15));
        systemLabel.setPadding(new Insets(18, 0, 0, 0));
        Button btnLogout = new Button("Log Out");
        btnLogout.setStyle("-fx-background-color: #dc2626; -fx-text-fill: white; -fx-font-size: 16; -fx-background-radius: 8; -fx-alignment: CENTER_LEFT; -fx-padding: 8 0 8 18; -fx-min-width: 220; -fx-cursor: hand;");
        btnLogout.setOnMouseEntered(e -> btnLogout.setStyle("-fx-background-color: #b91c1c; -fx-text-fill: white; -fx-font-size: 16; -fx-background-radius: 8; -fx-alignment: CENTER_LEFT; -fx-padding: 8 0 8 18; -fx-min-width: 220; -fx-cursor: hand;"));
        btnLogout.setOnMouseExited(e -> btnLogout.setStyle("-fx-background-color: #dc2626; -fx-text-fill: white; -fx-font-size: 16; -fx-background-radius: 8; -fx-alignment: CENTER_LEFT; -fx-padding: 8 0 8 18; -fx-min-width: 220; -fx-cursor: hand;"));
        allSidebarButtons = Arrays.asList(btnMain, btnDoctor, btnDept, btnSchedule, btnAppointments);
        for (Button b : allSidebarButtons) {
            b.setStyle("-fx-background-color: transparent; -fx-text-fill: #e0e6ed; -fx-font-size: 16; -fx-alignment: CENTER_LEFT; -fx-padding: 8 0 8 18;");
            b.setPrefWidth(220);
        }
        btnMain.setStyle("-fx-background-color: #2563eb; -fx-text-fill: white; -fx-font-size: 16; -fx-background-radius: 8; -fx-alignment: CENTER_LEFT; -fx-padding: 8 0 8 18;");
        navBox.getChildren().addAll(btnMain, btnDoctor, btnDept, btnSchedule, btnAppointments, systemLabel, btnLogout);
        sidebar.getChildren().addAll(userBox, sep1, navBox);

        // Main Content Area
        StackPane mainContent = new StackPane();
        mainContent.setPadding(new Insets(32, 32, 32, 32));

        // Main Page Content
        VBox mainPage = createMainPage(assistant);
        ScrollPane mainScrollPane = new ScrollPane(mainPage);
        mainScrollPane.setFitToWidth(true);
        mainScrollPane.setStyle("-fx-background-color:transparent;");
        mainContent.getChildren().clear();
        mainContent.getChildren().add(mainScrollPane);

        // Sidebar navigation actions
        btnMain.setOnAction(e -> {
            mainContent.getChildren().clear();
            VBox refreshedMainPage = createMainPage(assistant);
            ScrollPane refreshedScrollPane = new ScrollPane(refreshedMainPage);
            refreshedScrollPane.setFitToWidth(true);
            refreshedScrollPane.setStyle("-fx-background-color:transparent;");
            mainContent.getChildren().add(refreshedScrollPane);
            setActiveSidebarButton(btnMain);
        });
        btnDoctor.setOnAction(e -> {
            setActiveSidebarButton(btnDoctor);
            VBox doctorPage = new VBox(16);
            doctorPage.setPadding(new Insets(18));
            doctorPage.setStyle("-fx-background-color: #fff; -fx-background-radius: 12; -fx-effect: dropshadow(gaussian, #e0e0e0, 8, 0, 0, 2);");
            doctorPage.setMaxWidth(600);
            doctorPage.setMinWidth(400);
            Label title = new Label("Assigned Doctor");
            title.setFont(Font.font("Inter", FontWeight.BOLD, 22));
            title.setTextFill(Color.web("#222"));
            Doctor doc = assistant.getSupervisor();
            Label info = new Label(doc != null ? doc.GeneralInfo() : "No supervisor assigned.");
            info.setFont(Font.font("Inter", 15));
            info.setTextFill(Color.web("#333"));
            doctorPage.getChildren().addAll(title, info);
            ScrollPane scrollPane = new ScrollPane(doctorPage);
            scrollPane.setFitToWidth(true);
            scrollPane.setStyle("-fx-background-color:transparent;");
            mainContent.getChildren().clear();
            mainContent.getChildren().add(scrollPane);
        });
        btnDept.setOnAction(e -> {
            setActiveSidebarButton(btnDept);
            VBox deptPage = new VBox(16);
            deptPage.setPadding(new Insets(18));
            deptPage.setStyle("-fx-background-color: #fff; -fx-background-radius: 12; -fx-effect: dropshadow(gaussian, #e0e0e0, 8, 0, 0, 2);");
            deptPage.setMaxWidth(600);
            deptPage.setMinWidth(400);
            Label title = new Label("Assigned Department");
            title.setFont(Font.font("Inter", FontWeight.BOLD, 22));
            title.setTextFill(Color.web("#222"));
            Department dept = assistant.getDepartment();
            Label info = new Label(dept != null ? ("Name: " + dept.getName() + "\nHead: " + (dept.getHead() != null ? dept.getHead().getFullName() : "None") + "\nLocation: " + dept.getLocation()) : "No department assigned.");
            info.setFont(Font.font("Inter", 15));
            info.setTextFill(Color.web("#333"));
            deptPage.getChildren().addAll(title, info);
            ScrollPane scrollPane = new ScrollPane(deptPage);
            scrollPane.setFitToWidth(true);
            scrollPane.setStyle("-fx-background-color:transparent;");
            mainContent.getChildren().clear();
            mainContent.getChildren().add(scrollPane);
        });
        btnSchedule.setOnAction(e -> {
            setActiveSidebarButton(btnSchedule);
            VBox schedulePage = new VBox(16);
            schedulePage.setPadding(new Insets(18));
            schedulePage.setStyle("-fx-background-color: #fff; -fx-background-radius: 12; -fx-effect: dropshadow(gaussian, #e0e0e0, 8, 0, 0, 2);");
            schedulePage.setMaxWidth(600);
            schedulePage.setMinWidth(400);
            Label title = new Label("My Schedule");
            title.setFont(Font.font("Inter", FontWeight.BOLD, 22));
            title.setTextFill(Color.web("#222"));
            Label info = new Label("Work Schedule: " + assistant.getWorkSchedule());
            info.setFont(Font.font("Inter", 15));
            info.setTextFill(Color.web("#333"));
            schedulePage.getChildren().addAll(title, info);
            ScrollPane scrollPane = new ScrollPane(schedulePage);
            scrollPane.setFitToWidth(true);
            scrollPane.setStyle("-fx-background-color:transparent;");
            mainContent.getChildren().clear();
            mainContent.getChildren().add(scrollPane);
        });
        btnAppointments.setOnAction(e -> {
            setActiveSidebarButton(btnAppointments);
            VBox apptPage = new VBox(16);
            apptPage.setPadding(new Insets(18));
            apptPage.setStyle("-fx-background-color: #fff; -fx-background-radius: 12; -fx-effect: dropshadow(gaussian, #e0e0e0, 8, 0, 0, 2);");
            apptPage.setMaxWidth(600);
            apptPage.setMinWidth(400);
            Label title = new Label("Manage Appointments");
            title.setFont(Font.font("Inter", FontWeight.BOLD, 22));
            title.setTextFill(Color.web("#222"));
            Doctor supervisor = assistant.getSupervisor();
            if (supervisor == null) {
                Label info = new Label("No supervisor doctor assigned.");
                info.setFont(Font.font("Inter", 15));
                info.setTextFill(Color.web("#333"));
                apptPage.getChildren().addAll(title, info);
            } else {
                List<hospital.objects.Appointment> appointments = supervisor.getAppointments();
                if (appointments.isEmpty()) {
                    Label info = new Label("No appointments found for your supervisor doctor.");
                    info.setFont(Font.font("Inter", 15));
                    info.setTextFill(Color.web("#333"));
                    apptPage.getChildren().addAll(title, info);
                } else {
                    apptPage.getChildren().add(title);
                    for (hospital.objects.Appointment appt : appointments) {
                        VBox apptBox = new VBox(2);
                        apptBox.setStyle("-fx-background-color: #f1f5f9; -fx-background-radius: 8; -fx-padding: 10 12 10 12; -fx-effect: dropshadow(gaussian, #e0e0e0, 2, 0, 0, 1);");
                        Label info = new Label(
                            "Patient: " + appt.getPatient().getFullName() +
                            "\nDate: " + appt.getDateTime().toString() +
                            "\nStatus: " + appt.getStatus()
                        );
                        info.setFont(Font.font("Inter", 14));
                        info.setTextFill(Color.web("#222"));
                        HBox btnBox = new HBox(8);
                        Button callBtn = new Button("Call Patient");
                        callBtn.setStyle("-fx-background-color: #2563eb; -fx-text-fill: white; -fx-background-radius: 8;");
                        callBtn.setOnAction(ev -> {
                            showAlert("Calling Patient", "Calling " + appt.getPatient().getFullName() + "...");
                        });
                        Button cancelBtn = new Button("Cancel Appointment");
                        cancelBtn.setStyle("-fx-background-color: #ef4444; -fx-text-fill: white; -fx-background-radius: 8;");
                        cancelBtn.setOnAction(ev -> {
                            appt.cancel();
                            supervisor.cancelAppointment(appt);
                            showAlert("Appointment Cancelled", "Appointment for " + appt.getPatient().getFullName() + " has been cancelled.");
                            btnAppointments.fire(); // Refresh the list
                        });
                        btnBox.getChildren().addAll(callBtn, cancelBtn);
                        apptBox.getChildren().addAll(info, btnBox);
                        apptPage.getChildren().add(apptBox);
                    }
                }
            }
            ScrollPane scrollPane = new ScrollPane(apptPage);
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

    // Main page for assistant
    private VBox createMainPage(Assistant assistant) {
        VBox mainPage = new VBox(10);
        mainPage.setPadding(new Insets(10, 16, 10, 16));
        mainPage.setStyle("-fx-background-color: #fff; -fx-background-radius: 12; -fx-effect: dropshadow(gaussian, #e0e0e0, 8, 0, 0, 2;");
        mainPage.setMaxWidth(700);
        mainPage.setMinWidth(400);
        Label mainTitle = new Label("Welcome, " + assistant.getFullName());
        mainTitle.setFont(Font.font("Inter", FontWeight.BOLD, 26));
        mainTitle.setTextFill(Color.web("#222"));
        Label mainRole = new Label("Assistant");
        mainRole.setFont(Font.font("Inter", FontWeight.NORMAL, 15));
        mainRole.setTextFill(Color.web("#666"));
        Separator mainSep = new Separator();
        mainSep.setPadding(new Insets(0, 0, 4, 0));
        Label info = new Label(
            "Department: " + (assistant.getDepartment() != null ? assistant.getDepartment().getName() : "None") +
            "\nDuty: " + assistant.getDuty() +
            "\nSupervisor: " + (assistant.getSupervisor() != null ? assistant.getSupervisor().getFullName() : "None") +
            "\nExperience: " + assistant.getExperience() + " years" +
            "\nSalary: $" + String.format("%.2f", assistant.getSalary()) +
            "\nWork Schedule: " + assistant.getWorkSchedule()
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