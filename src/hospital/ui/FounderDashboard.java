package hospital.ui;

import hospital.objects.Founder;
import hospital.objects.Doctor;
import hospital.objects.Room;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.Separator;
import javafx.scene.control.TextInputDialog;
import javafx.scene.control.Dialog;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ListCell;
import javafx.scene.control.ComboBox;
import javafx.scene.control.CheckBox;
import hospital.objects.Department;
import hospital.objects.Assistant;
import hospital.objects.Pharmacist;
import java.util.Arrays;
import java.util.List;
import hospital.objects.StaticSchedule;

public class FounderDashboard {
    private Stage primaryStage;
    private DashboardManager dashboardManager;
    private List<Button> allSidebarButtons;

    public FounderDashboard(Stage primaryStage, DashboardManager dashboardManager) {
        this.primaryStage = primaryStage;
        this.dashboardManager = dashboardManager;
    }

    public void show(Founder founder) {
        BorderPane root = new BorderPane();

        // Sidebar
        VBox sidebar = new VBox(18);
        sidebar.setPadding(new Insets(32, 0, 32, 0));
        sidebar.setStyle("-fx-background-color: #0a1622; -fx-min-width: 290px; -fx-max-width: 290px;");
        sidebar.setPrefWidth(290);

        // User Info
        VBox userBox = new VBox(0);
        Label nameLabel = new Label(founder.getFullName());
        nameLabel.setFont(Font.font("Inter", FontWeight.BOLD, 28));
        nameLabel.setTextFill(Color.WHITE);
        Label roleLabel = new Label("Founder");
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
        Button btnCreateDept = new Button("Create a Dept or Room");
        Button btnCreateDoc = new Button("Create a Doctor or Assistant");
        Button btnSeeWorkers = new Button("See All Workers");
        Button btnSeeDepts = new Button("See All Depts & Rooms");
        Button btnAddDoc = new Button("Add Doctor");
        Button btnFireDoc = new Button("Fire Doctor");
        Label systemLabel = new Label("System");
        systemLabel.setTextFill(Color.WHITE);
        systemLabel.setFont(Font.font("Inter", FontWeight.BOLD, 15));
        systemLabel.setPadding(new Insets(18, 0, 0, 0));
        Button btnLogout = new Button("Log Out");
        btnLogout.setStyle("-fx-background-color: #071425; -fx-text-fill: white; -fx-font-size: 16; -fx-background-radius: 8; -fx-alignment: CENTER_LEFT; -fx-padding: 8 0 8 18; -fx-min-width: 220; -fx-cursor: hand;");
        btnLogout.setOnMouseEntered(e -> btnLogout.setStyle("-fx-background-color: #071425; -fx-text-fill: white; -fx-font-size: 16; -fx-background-radius: 8; -fx-alignment: CENTER_LEFT; -fx-padding: 8 0 8 18; -fx-min-width: 220; -fx-cursor: hand;"));
        btnLogout.setOnMouseExited(e -> btnLogout.setStyle("-fx-background-color: #071425; -fx-text-fill: white; -fx-font-size: 16; -fx-background-radius: 8; -fx-alignment: CENTER_LEFT; -fx-padding: 8 0 8 18; -fx-min-width: 220; -fx-cursor: hand;"));
        // Collect all sidebar buttons for easy style management
        allSidebarButtons = Arrays.asList(btnMain, btnCreateDept, btnCreateDoc, btnSeeWorkers, btnSeeDepts, btnAddDoc, btnFireDoc);
        // Style buttons
        for (Button b : allSidebarButtons) {
            b.setStyle("-fx-background-color: transparent; -fx-text-fill: #e0e6ed; -fx-font-size: 16; -fx-alignment: CENTER_LEFT; -fx-padding: 8 0 8 18;");
            b.setPrefWidth(220);
        }
        btnMain.setStyle("-fx-background-color: #2563eb; -fx-text-fill: white; -fx-font-size: 16; -fx-background-radius: 8; -fx-alignment: CENTER_LEFT; -fx-padding: 8 0 8 18;");
        navBox.getChildren().addAll(btnMain, btnCreateDept, btnCreateDoc, btnSeeWorkers, btnSeeDepts, btnAddDoc, btnFireDoc, systemLabel, btnLogout);

        sidebar.getChildren().addAll(userBox, sep1, navBox);

        // Main Content Area (StackPane for swapping pages)
        StackPane mainContent = new StackPane();
        mainContent.setPadding(new Insets(32, 32, 32, 32));

        // Main Page Content (refactored)
        VBox mainPage = createMainPage(founder);
        ScrollPane mainScrollPane = new ScrollPane(mainPage);
        mainScrollPane.setFitToWidth(true);
        mainScrollPane.setStyle("-fx-background-color:transparent;");
        mainContent.getChildren().clear();
        mainContent.getChildren().add(mainScrollPane);

        // Sidebar navigation actions
        btnMain.setOnAction(e -> {
            mainContent.getChildren().clear();
            VBox refreshedMainPage = createMainPage(founder);
            ScrollPane refreshedScrollPane = new ScrollPane(refreshedMainPage);
            refreshedScrollPane.setFitToWidth(true);
            refreshedScrollPane.setStyle("-fx-background-color:transparent;");
            mainContent.getChildren().add(refreshedScrollPane);
            setActiveSidebarButton(btnMain);
        });
        btnLogout.setOnAction(e -> {
            LoginScreen loginScreen = new LoginScreen(primaryStage, dashboardManager.getUsers());
            loginScreen.show();
        });
        btnCreateDept.setOnAction(e -> {
            // Tab-like toggle buttons
            ToggleGroup toggleGroup = new ToggleGroup();
            ToggleButton deptTab = new ToggleButton("Create Department");
            ToggleButton roomTab = new ToggleButton("Create Room");
            deptTab.setToggleGroup(toggleGroup);
            roomTab.setToggleGroup(toggleGroup);
            deptTab.setSelected(true);
            HBox tabBox = new HBox(8, deptTab, roomTab);
            tabBox.setPadding(new Insets(0, 0, 16, 0));

            // Department Form
            VBox deptForm = new VBox(10);
            deptForm.setPadding(new Insets(10));
            deptForm.setAlignment(javafx.geometry.Pos.TOP_LEFT);
            Label deptTitle = new Label("Create Department");
            deptTitle.setFont(Font.font("Inter", FontWeight.BOLD, 18));
            TextField deptNameField = new TextField();
            deptNameField.setPromptText("Department Name");
            ComboBox<Doctor> headDoctorBox = new ComboBox<>();
            headDoctorBox.setPromptText("Head Doctor (optional)");
            headDoctorBox.getItems().addAll(founder.getDoctors());
            headDoctorBox.setCellFactory(cb -> new ListCell<>() {
                @Override protected void updateItem(Doctor doc, boolean empty) {
                    super.updateItem(doc, empty);
                    setText(empty || doc == null ? null : doc.getFullName());
                }
            });
            headDoctorBox.setButtonCell(new ListCell<>() {
                @Override protected void updateItem(Doctor doc, boolean empty) {
                    super.updateItem(doc, empty);
                    setText(empty || doc == null ? null : doc.getFullName());
                }
            });
            TextField deptLocationField = new TextField();
            deptLocationField.setPromptText("Location");
            Button createDeptBtn = new Button("Create Department");
            createDeptBtn.setStyle("-fx-background-color: #2563eb; -fx-text-fill: white; -fx-font-size: 15; -fx-background-radius: 8;");
            deptForm.getChildren().addAll(deptTitle, new Label("Name:"), deptNameField, new Label("Head Doctor:"), headDoctorBox, new Label("Location:"), deptLocationField, createDeptBtn);

            // Room Form
            VBox roomForm = new VBox(10);
            roomForm.setPadding(new Insets(10));
            roomForm.setAlignment(javafx.geometry.Pos.TOP_LEFT);
            Label roomTitle = new Label("Create Room");
            roomTitle.setFont(Font.font("Inter", FontWeight.BOLD, 18));
            TextField roomNameField = new TextField();
            roomNameField.setPromptText("Room Name/Number");
            ComboBox<String> roomTypeBox = new ComboBox<>();
            roomTypeBox.getItems().addAll("General", "ICU", "Operating", "Emergency");
            roomTypeBox.setPromptText("Room Type");
            TextField capacityField = new TextField();
            capacityField.setPromptText("Capacity");
            TextField rateField = new TextField();
            rateField.setPromptText("Hourly Rate");
            TextField equipmentField = new TextField();
            equipmentField.setPromptText("Equipment (comma separated)");
            Button createRoomBtn = new Button("Create Room");
            createRoomBtn.setStyle("-fx-background-color: #2563eb; -fx-text-fill: white; -fx-font-size: 15; -fx-background-radius: 8;");
            roomForm.getChildren().addAll(roomTitle, new Label("Name/Number:"), roomNameField, new Label("Type:"), roomTypeBox, new Label("Capacity:"), capacityField, new Label("Hourly Rate:"), rateField, new Label("Equipment:"), equipmentField, createRoomBtn);

            StackPane formPane = new StackPane(deptForm, roomForm);
            roomForm.setVisible(false);
            deptTab.setOnAction(ev -> { deptForm.setVisible(true); roomForm.setVisible(false); });
            roomTab.setOnAction(ev -> { deptForm.setVisible(false); roomForm.setVisible(true); });

            VBox content = new VBox(10, tabBox, formPane);
            content.setPadding(new Insets(10, 10, 10, 10));
            ScrollPane formScrollPane = new ScrollPane(content);
            formScrollPane.setFitToWidth(true);
            formScrollPane.setStyle("-fx-background-color:transparent;");
            mainContent.getChildren().clear();
            mainContent.getChildren().add(formScrollPane);

            // Department creation logic
            createDeptBtn.setOnAction(ev -> {
                String name = deptNameField.getText().trim();
                Doctor head = headDoctorBox.getValue();
                String location = deptLocationField.getText().trim();
                if (name.isEmpty() || location.isEmpty()) {
                    showAlert("Error", "Name and Location are required.");
                    return;
                }
                founder.createDepartment(name, head, location);
                showAlert("Success", "Department created successfully.");
                deptNameField.clear();
                headDoctorBox.setValue(null);
                deptLocationField.clear();
                // Optionally, refresh the main page if it's currently visible
                // (not strictly necessary unless you want live update)
            });

            // Room creation logic
            createRoomBtn.setOnAction(ev -> {
                String name = roomNameField.getText().trim();
                String type = roomTypeBox.getValue();
                String capStr = capacityField.getText().trim();
                String rateStr = rateField.getText().trim();
                String equipment = equipmentField.getText().trim();
                if (name.isEmpty() || type == null || capStr.isEmpty() || rateStr.isEmpty()) {
                    showAlert("Error", "All fields except equipment are required.");
                    return;
                }
                try {
                    int capacity = Integer.parseInt(capStr);
                    double rate = Double.parseDouble(rateStr);
                    // Room creation (add to a list or system as needed)
                    Room newRoom = new Room(name, type, capacity, rate, equipment);
                    founder.addRoom(newRoom);
                    showAlert("Success", "Room created successfully.\n" + newRoom.GeneralInfo());
                    roomNameField.clear();
                    roomTypeBox.setValue(null);
                    capacityField.clear();
                    rateField.clear();
                    equipmentField.clear();
                } catch (Exception ex) {
                    showAlert("Error", "Invalid capacity or rate.");
                }
            });

            setActiveSidebarButton(btnCreateDept);
        });
        btnCreateDoc.setOnAction(e -> {
            setActiveSidebarButton(btnCreateDoc);
            // Tabbed interface for Doctor/Assistant
            ToggleGroup toggleGroup = new ToggleGroup();
            ToggleButton docTab = new ToggleButton("Create Doctor");
            ToggleButton asstTab = new ToggleButton("Create Assistant");
            docTab.setToggleGroup(toggleGroup);
            asstTab.setToggleGroup(toggleGroup);
            docTab.setSelected(true);
            HBox tabBox = new HBox(8, docTab, asstTab);
            tabBox.setPadding(new Insets(0, 0, 16, 0));

            // Doctor Form
            VBox docForm = new VBox(10);
            docForm.setPadding(new Insets(10));
            docForm.setAlignment(javafx.geometry.Pos.TOP_LEFT);
            Label docTitle = new Label("Create Doctor");
            docTitle.setFont(Font.font("Inter", FontWeight.BOLD, 18));
            TextField docIdField = new TextField();
            docIdField.setPromptText("ID");
            TextField docFirstNameField = new TextField();
            docFirstNameField.setPromptText("First Name");
            TextField docLastNameField = new TextField();
            docLastNameField.setPromptText("Last Name");
            TextField docAgeField = new TextField();
            docAgeField.setPromptText("Age");
            TextField docGenderField = new TextField();
            docGenderField.setPromptText("Gender (M/F)");
            TextField docPhoneField = new TextField();
            docPhoneField.setPromptText("Phone Number");
            TextField docUsernameField = new TextField();
            docUsernameField.setPromptText("Username");
            TextField docPasswordField = new TextField();
            docPasswordField.setPromptText("Password");
            TextField docDeptField = new TextField();
            docDeptField.setPromptText("Department");
            TextField docSpecialtyField = new TextField();
            docSpecialtyField.setPromptText("Specialty");
            TextField docOfficeField = new TextField();
            docOfficeField.setPromptText("Office Number");
            TextField docExpField = new TextField();
            docExpField.setPromptText("Experience (years)");
            CheckBox privateCheck = new CheckBox("Private Doctor");
            TextField docSalaryField = new TextField();
            docSalaryField.setPromptText("Salary");
            Button createDocBtn = new Button("Create Doctor");
            createDocBtn.setStyle("-fx-background-color: #2563eb; -fx-text-fill: white; -fx-font-size: 15; -fx-background-radius: 8;");
            docForm.getChildren().addAll(docTitle, docIdField, docFirstNameField, docLastNameField, docAgeField, docGenderField, docPhoneField, docUsernameField, docPasswordField, docDeptField, docSpecialtyField, docOfficeField, docExpField, privateCheck, docSalaryField, createDocBtn);

            // Assistant Form
            VBox asstForm = new VBox(10);
            asstForm.setPadding(new Insets(10));
            asstForm.setAlignment(javafx.geometry.Pos.TOP_LEFT);
            Label asstTitle = new Label("Create Assistant");
            asstTitle.setFont(Font.font("Inter", FontWeight.BOLD, 18));
            TextField asstIdField = new TextField();
            asstIdField.setPromptText("ID");
            TextField asstFirstNameField = new TextField();
            asstFirstNameField.setPromptText("First Name");
            TextField asstLastNameField = new TextField();
            asstLastNameField.setPromptText("Last Name");
            TextField asstAgeField = new TextField();
            asstAgeField.setPromptText("Age");
            TextField asstGenderField = new TextField();
            asstGenderField.setPromptText("Gender (M/F)");
            TextField asstPhoneField = new TextField();
            asstPhoneField.setPromptText("Phone Number");
            TextField asstUsernameField = new TextField();
            asstUsernameField.setPromptText("Username");
            TextField asstPasswordField = new TextField();
            asstPasswordField.setPromptText("Password");
            ComboBox<Doctor> supervisorBox = new ComboBox<>();
            supervisorBox.setPromptText("Supervisor Doctor");
            supervisorBox.getItems().addAll(founder.getDoctors());
            supervisorBox.setCellFactory(cb -> new ListCell<>() {
                @Override protected void updateItem(Doctor doc, boolean empty) {
                    super.updateItem(doc, empty);
                    setText(empty || doc == null ? null : doc.getFullName());
                }
            });
            supervisorBox.setButtonCell(new ListCell<>() {
                @Override protected void updateItem(Doctor doc, boolean empty) {
                    super.updateItem(doc, empty);
                    setText(empty || doc == null ? null : doc.getFullName());
                }
            });
            TextField asstExpField = new TextField();
            asstExpField.setPromptText("Experience (years)");
            TextField asstDutyField = new TextField();
            asstDutyField.setPromptText("Duty");
            ComboBox<Department> asstDeptBox = new ComboBox<>();
            asstDeptBox.setPromptText("Department");
            asstDeptBox.getItems().addAll(founder.getDepartments());
            asstDeptBox.setCellFactory(cb -> new ListCell<>() {
                @Override protected void updateItem(Department dept, boolean empty) {
                    super.updateItem(dept, empty);
                    setText(empty || dept == null ? null : dept.getName());
                }
            });
            asstDeptBox.setButtonCell(new ListCell<>() {
                @Override protected void updateItem(Department dept, boolean empty) {
                    super.updateItem(dept, empty);
                    setText(empty || dept == null ? null : dept.getName());
                }
            });
            TextField asstSalaryField = new TextField();
            asstSalaryField.setPromptText("Salary");
            TextField asstScheduleField = new TextField();
            asstScheduleField.setPromptText("Work Schedule");
            Button createAsstBtn = new Button("Create Assistant");
            createAsstBtn.setStyle("-fx-background-color: #2563eb; -fx-text-fill: white; -fx-font-size: 15; -fx-background-radius: 8;");
            asstForm.getChildren().addAll(asstTitle, asstIdField, asstFirstNameField, asstLastNameField, asstAgeField, asstGenderField, asstPhoneField, asstUsernameField, asstPasswordField, supervisorBox, asstExpField, asstDutyField, asstDeptBox, asstSalaryField, asstScheduleField, createAsstBtn);

            StackPane formPane = new StackPane(docForm, asstForm);
            asstForm.setVisible(false);
            docTab.setOnAction(ev -> { docForm.setVisible(true); asstForm.setVisible(false); });
            asstTab.setOnAction(ev -> { docForm.setVisible(false); asstForm.setVisible(true); });

            VBox content = new VBox(10, tabBox, formPane);
            content.setPadding(new Insets(10, 10, 10, 10));
            ScrollPane formScrollPane = new ScrollPane(content);
            formScrollPane.setFitToWidth(true);
            formScrollPane.setStyle("-fx-background-color:transparent;");
            mainContent.getChildren().clear();
            mainContent.getChildren().add(formScrollPane);

            // Doctor creation logic
            createDocBtn.setOnAction(ev -> {
                try {
                    String id = docIdField.getText().trim();
                    String firstName = docFirstNameField.getText().trim();
                    String lastName = docLastNameField.getText().trim();
                    int age = Integer.parseInt(docAgeField.getText().trim());
                    char gender = docGenderField.getText().trim().charAt(0);
                    String phone = docPhoneField.getText().trim();
                    String username = docUsernameField.getText().trim();
                    String password = docPasswordField.getText().trim();
                    String dept = docDeptField.getText().trim();
                    String specialty = docSpecialtyField.getText().trim();
                    String office = docOfficeField.getText().trim();
                    short exp = Short.parseShort(docExpField.getText().trim());
                    boolean isPrivate = privateCheck.isSelected();
                    double salary = Double.parseDouble(docSalaryField.getText().trim());
                    Doctor newDoc = new Doctor(id, firstName, lastName, age, gender, phone, username, password, dept, specialty, office, exp, isPrivate, salary);
                    newDoc.setStaticSchedule(new StaticSchedule());
                    founder.hireDoctor(newDoc, null); // Optionally assign department later
                    dashboardManager.getUsers().add(newDoc);
                    showAlert("Success", "Doctor created successfully.\n" + newDoc.GeneralInfo());
                    docIdField.clear(); docFirstNameField.clear(); docLastNameField.clear(); docAgeField.clear(); docGenderField.clear(); docPhoneField.clear(); docUsernameField.clear(); docPasswordField.clear(); docDeptField.clear(); docSpecialtyField.clear(); docOfficeField.clear(); docExpField.clear(); privateCheck.setSelected(false); docSalaryField.clear();
                } catch (Exception ex) {
                    showAlert("Error", "Invalid input: " + ex.getMessage());
                }
            });
            // Assistant creation logic
            createAsstBtn.setOnAction(ev -> {
                try {
                    String id = asstIdField.getText().trim();
                    String firstName = asstFirstNameField.getText().trim();
                    String lastName = asstLastNameField.getText().trim();
                    int age = Integer.parseInt(asstAgeField.getText().trim());
                    char gender = asstGenderField.getText().trim().charAt(0);
                    String phone = asstPhoneField.getText().trim();
                    String username = asstUsernameField.getText().trim();
                    String password = asstPasswordField.getText().trim();
                    Doctor supervisor = supervisorBox.getValue();
                    short exp = Short.parseShort(asstExpField.getText().trim());
                    String duty = asstDutyField.getText().trim();
                    Department dept = asstDeptBox.getValue();
                    double salary = Double.parseDouble(asstSalaryField.getText().trim());
                    String schedule = asstScheduleField.getText().trim();
                    Assistant newAsst = new Assistant(id, firstName, lastName, age, gender, phone, username, password, supervisor, exp, duty, dept, salary, schedule);
                    dashboardManager.getUsers().add(newAsst);
                    // Add to founder's assistant list if you have one, or to the system
                    showAlert("Success", "Assistant created successfully.\n" + newAsst.GeneralInfo());
                    asstIdField.clear(); asstFirstNameField.clear(); asstLastNameField.clear(); asstAgeField.clear(); asstGenderField.clear(); asstPhoneField.clear(); asstUsernameField.clear(); asstPasswordField.clear(); supervisorBox.setValue(null); asstExpField.clear(); asstDutyField.clear(); asstDeptBox.setValue(null); asstSalaryField.clear(); asstScheduleField.clear();
                } catch (Exception ex) {
                    showAlert("Error", "Invalid input: " + ex.getMessage());
                }
            });
        });
        btnSeeWorkers.setOnAction(e -> {
            setActiveSidebarButton(btnSeeWorkers);
            VBox workersPage = new VBox(18);
            workersPage.setPadding(new Insets(18));
            workersPage.setStyle("-fx-background-color: #fff; -fx-background-radius: 12; -fx-effect: dropshadow(gaussian, #e0e0e0, 8, 0, 0, 2);");
            workersPage.setMaxWidth(800);
            workersPage.setMinWidth(400);
            Label title = new Label("All Workers");
            title.setFont(Font.font("Inter", FontWeight.BOLD, 24));
            title.setTextFill(Color.web("#222"));
            // Doctors
            Label docTitle = new Label("Doctors");
            docTitle.setFont(Font.font("Inter", FontWeight.BOLD, 18));
            VBox docList = new VBox(4);
            for (var doc : founder.getDoctors()) {
                Label l = new Label(doc.GeneralInfo());
                l.setFont(Font.font("Inter", 14));
                docList.getChildren().add(l);
            }
            // Assistants (if you have a list, otherwise skip or use dashboardManager.getUsers())
            Label asstTitle = new Label("Assistants");
            asstTitle.setFont(Font.font("Inter", FontWeight.BOLD, 18));
            VBox asstList = new VBox(4);
            for (var user : dashboardManager.getUsers()) {
                if (user instanceof Assistant) {
                    Label l = new Label(((Assistant)user).GeneralInfo());
                    l.setFont(Font.font("Inter", 14));
                    asstList.getChildren().add(l);
                }
            }
            // Pharmacists
            Label pharmTitle = new Label("Pharmacists");
            pharmTitle.setFont(Font.font("Inter", FontWeight.BOLD, 18));
            VBox pharmList = new VBox(4);
            for (var user : dashboardManager.getUsers()) {
                if (user instanceof Pharmacist) {
                    Label l = new Label(user.getFullName()); // Replace with generalInfo if available
                    l.setFont(Font.font("Inter", 14));
                    pharmList.getChildren().add(l);
                }
            }
            workersPage.getChildren().addAll(title, docTitle, docList, asstTitle, asstList, pharmTitle, pharmList);
            ScrollPane scrollPane = new ScrollPane(workersPage);
            scrollPane.setFitToWidth(true);
            scrollPane.setStyle("-fx-background-color:transparent;");
            mainContent.getChildren().clear();
            mainContent.getChildren().add(scrollPane);
        });
        btnSeeDepts.setOnAction(e -> {
            setActiveSidebarButton(btnSeeDepts);
            VBox deptsRoomsPage = new VBox(18);
            deptsRoomsPage.setPadding(new Insets(18));
            deptsRoomsPage.setStyle("-fx-background-color: #fff; -fx-background-radius: 12; -fx-effect: dropshadow(gaussian, #e0e0e0, 8, 0, 0, 2);");
            deptsRoomsPage.setMaxWidth(800);
            deptsRoomsPage.setMinWidth(400);
            Label title = new Label("All Departments & Rooms");
            title.setFont(Font.font("Inter", FontWeight.BOLD, 24));
            title.setTextFill(Color.web("#222"));
            // Departments
            Label deptTitle = new Label("Departments");
            deptTitle.setFont(Font.font("Inter", FontWeight.BOLD, 18));
            VBox deptList = new VBox(8);
            for (var dept : founder.getDepartments()) {
                String head = dept.getHead() != null ? dept.getHead().getFullName() : "None";
                VBox deptBox = new VBox(2);
                Label deptInfo = new Label("• " + dept.getName() + " (Head: " + head + ", Location: " + dept.getLocation() + ")");
                deptInfo.setFont(Font.font("Inter", 14));
                deptBox.getChildren().add(deptInfo);
                // List doctors in this department
                java.util.List<Doctor> deptDoctors = dept.getDoctors();
                if (deptDoctors != null && !deptDoctors.isEmpty()) {
                    VBox docList = new VBox(0);
                    for (var doc : deptDoctors) {
                        Label docLabel = new Label("    - " + doc.getFullName() + " (" + doc.getSpecialty() + ")");
                        docLabel.setFont(Font.font("Inter", 13));
                        docList.getChildren().add(docLabel);
                    }
                    deptBox.getChildren().add(docList);
                }
                deptList.getChildren().add(deptBox);
            }
            // Rooms (if founder.getRooms() exists, otherwise show placeholder)
            Label roomTitle = new Label("Rooms");
            roomTitle.setFont(Font.font("Inter", FontWeight.BOLD, 18));
            VBox roomList = new VBox(4);
            try {
                var method = founder.getClass().getMethod("getRooms");
                java.util.List<?> rooms = (java.util.List<?>) method.invoke(founder);
                for (Object roomObj : rooms) {
                    // Assume Room has GeneralInfo()
                    try {
                        java.lang.reflect.Method infoMethod = roomObj.getClass().getMethod("GeneralInfo");
                        String info = (String) infoMethod.invoke(roomObj);
                        Label l = new Label(info);
                        l.setFont(Font.font("Inter", 14));
                        roomList.getChildren().add(l);
                    } catch (Exception ex) {
                        // fallback: toString
                        Label l = new Label(roomObj.toString());
                        l.setFont(Font.font("Inter", 14));
                        roomList.getChildren().add(l);
                    }
                }
            } catch (Exception ex) {
                // No getRooms() method or error
                Label l = new Label("No room data available.");
                l.setFont(Font.font("Inter", 14));
                roomList.getChildren().add(l);
            }
            deptsRoomsPage.getChildren().addAll(title, deptTitle, deptList, roomTitle, roomList);
            ScrollPane scrollPane = new ScrollPane(deptsRoomsPage);
            scrollPane.setFitToWidth(true);
            scrollPane.setStyle("-fx-background-color:transparent;");
            mainContent.getChildren().clear();
            mainContent.getChildren().add(scrollPane);
        });
        btnAddDoc.setOnAction(e -> {
            setActiveSidebarButton(btnAddDoc);
            VBox addDocPage = new VBox(18);
            addDocPage.setPadding(new Insets(18));
            addDocPage.setStyle("-fx-background-color: #fff; -fx-background-radius: 12; -fx-effect: dropshadow(gaussian, #e0e0e0, 8, 0, 0, 2);");
            addDocPage.setMaxWidth(600);
            addDocPage.setMinWidth(400);
            Label title = new Label("Add Doctor (Hire)");
            title.setFont(Font.font("Inter", FontWeight.BOLD, 22));
            title.setTextFill(Color.web("#222"));
            // List of doctors not already hired
            List<Doctor> availableDocs = new java.util.ArrayList<>();
            for (var user : dashboardManager.getUsers()) {
                if (user instanceof Doctor && !founder.getDoctors().contains(user)) {
                    availableDocs.add((Doctor)user);
                }
            }
            ComboBox<Doctor> docBox = new ComboBox<>();
            docBox.setPromptText("Select Doctor");
            docBox.getItems().addAll(availableDocs);
            docBox.setCellFactory(cb -> new ListCell<>() {
                @Override protected void updateItem(Doctor doc, boolean empty) {
                    super.updateItem(doc, empty);
                    setText(empty || doc == null ? null : doc.getFullName());
                }
            });
            docBox.setButtonCell(new ListCell<>() {
                @Override protected void updateItem(Doctor doc, boolean empty) {
                    super.updateItem(doc, empty);
                    setText(empty || doc == null ? null : doc.getFullName());
                }
            });
            ComboBox<Department> deptBox = new ComboBox<>();
            deptBox.setPromptText("Assign to Department (optional)");
            deptBox.getItems().addAll(founder.getDepartments());
            deptBox.setCellFactory(cb -> new ListCell<>() {
                @Override protected void updateItem(Department dept, boolean empty) {
                    super.updateItem(dept, empty);
                    setText(empty || dept == null ? null : dept.getName());
                }
            });
            deptBox.setButtonCell(new ListCell<>() {
                @Override protected void updateItem(Department dept, boolean empty) {
                    super.updateItem(dept, empty);
                    setText(empty || dept == null ? null : dept.getName());
                }
            });
            Button hireBtn = new Button("Hire Doctor");
            hireBtn.setStyle("-fx-background-color: #2563eb; -fx-text-fill: white; -fx-font-size: 15; -fx-background-radius: 8;");
            Label result = new Label("");
            result.setTextFill(Color.web("#22c55e"));
            hireBtn.setOnAction(ev -> {
                Doctor doc = docBox.getValue();
                Department dept = deptBox.getValue();
                if (doc == null) {
                    result.setText("Please select a doctor to hire.");
                    return;
                }
                founder.hireDoctor(doc, dept);
                result.setText("Doctor hired successfully.");
                docBox.getItems().remove(doc);
                docBox.setValue(null);
                deptBox.setValue(null);
            });
            addDocPage.getChildren().addAll(title, docBox, deptBox, hireBtn, result);
            mainContent.getChildren().clear();
            mainContent.getChildren().add(addDocPage);
        });
        btnFireDoc.setOnAction(e -> {
            setActiveSidebarButton(btnFireDoc);
            VBox fireDocPage = new VBox(18);
            fireDocPage.setPadding(new Insets(18));
            fireDocPage.setStyle("-fx-background-color: #fff; -fx-background-radius: 12; -fx-effect: dropshadow(gaussian, #e0e0e0, 8, 0, 0, 2);");
            fireDocPage.setMaxWidth(600);
            fireDocPage.setMinWidth(400);
            Label title = new Label("Fire Doctor");
            title.setFont(Font.font("Inter", FontWeight.BOLD, 22));
            title.setTextFill(Color.web("#222"));
            ComboBox<Doctor> docBox = new ComboBox<>();
            docBox.setPromptText("Select Doctor to Fire");
            docBox.getItems().addAll(founder.getDoctors());
            docBox.setCellFactory(cb -> new ListCell<>() {
                @Override protected void updateItem(Doctor doc, boolean empty) {
                    super.updateItem(doc, empty);
                    setText(empty || doc == null ? null : doc.getFullName());
                }
            });
            docBox.setButtonCell(new ListCell<>() {
                @Override protected void updateItem(Doctor doc, boolean empty) {
                    super.updateItem(doc, empty);
                    setText(empty || doc == null ? null : doc.getFullName());
                }
            });
            Button fireBtn = new Button("Fire Doctor");
            fireBtn.setStyle("-fx-background-color: #ef4444; -fx-text-fill: white; -fx-font-size: 15; -fx-background-radius: 8;");
            Label result = new Label("");
            result.setTextFill(Color.web("#ef4444"));
            fireBtn.setOnAction(ev -> {
                Doctor doc = docBox.getValue();
                if (doc == null) {
                    result.setText("Please select a doctor to fire.");
                    return;
                }
                founder.fireDoctor(doc);
                result.setText("Doctor fired successfully.");
                docBox.getItems().remove(doc);
                docBox.setValue(null);
            });
            fireDocPage.getChildren().addAll(title, docBox, fireBtn, result);
            mainContent.getChildren().clear();
            mainContent.getChildren().add(fireDocPage);
        });

        // Layout
        root.setLeft(sidebar);
        root.setCenter(mainContent);
        
        Scene scene = new Scene(root, 1280, 720);
        primaryStage.setScene(scene);
    }

    // Helper: Show alert dialog
    private void showAlert(String title, String message) {
        javafx.scene.control.Alert alert = new javafx.scene.control.Alert(javafx.scene.control.Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    // Helper: Show add revenue dialog
    private void showAddRevenueDialog(Founder founder, Label financialSummary) {
        Dialog<Void> dialog = new Dialog<>();
        dialog.setTitle("Add Revenue");
        dialog.setHeaderText("Enter revenue details");

        ButtonType okButtonType = new ButtonType("OK", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(okButtonType, ButtonType.CANCEL);

        VBox vbox = new VBox(10);
        vbox.setPadding(new Insets(10));
        TextField amountField = new TextField();
        amountField.setPromptText("Amount");
        TextField sourceField = new TextField();
        sourceField.setPromptText("Source");
        TextField descField = new TextField();
        descField.setPromptText("Description (optional)");
        vbox.getChildren().addAll(new Label("Amount:"), amountField, new Label("Source:"), sourceField, new Label("Description:"), descField);
        dialog.getDialogPane().setContent(vbox);

        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == okButtonType) {
                String amountStr = amountField.getText().trim();
                String source = sourceField.getText().trim();
                String desc = descField.getText().trim();
                if (amountStr.isEmpty() || source.isEmpty()) {
                    showAlert("Error", "Amount and Source are required.");
                    return null;
                }
                try {
                    double amount = Double.parseDouble(amountStr);
                    founder.addRevenue(amount, source, desc);
                    financialSummary.setText(founder.getFinancialSummary());
                    showAlert("Success", "Revenue added successfully.");
                } catch (Exception ex) {
                    showAlert("Error", "Invalid amount.");
                }
            }
            return null;
        });
        dialog.showAndWait();
    }

    // Helper: Show add expense dialog
    private void showAddExpenseDialog(Founder founder, Label financialSummary) {
        Dialog<Void> dialog = new Dialog<>();
        dialog.setTitle("Add Expense");
        dialog.setHeaderText("Enter expense details");

        ButtonType okButtonType = new ButtonType("OK", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(okButtonType, ButtonType.CANCEL);

        VBox vbox = new VBox(10);
        vbox.setPadding(new Insets(10));
        TextField amountField = new TextField();
        amountField.setPromptText("Amount");
        TextField categoryField = new TextField();
        categoryField.setPromptText("Category");
        TextField descField = new TextField();
        descField.setPromptText("Description (optional)");
        vbox.getChildren().addAll(new Label("Amount:"), amountField, new Label("Category:"), categoryField, new Label("Description:"), descField);
        dialog.getDialogPane().setContent(vbox);

        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == okButtonType) {
                String amountStr = amountField.getText().trim();
                String category = categoryField.getText().trim();
                String desc = descField.getText().trim();
                if (amountStr.isEmpty() || category.isEmpty()) {
                    showAlert("Error", "Amount and Category are required.");
                    return null;
                }
                try {
                    double amount = Double.parseDouble(amountStr);
                    founder.addExpense(category, amount, desc);
                    financialSummary.setText(founder.getFinancialSummary());
                    showAlert("Success", "Expense added successfully.");
                } catch (Exception ex) {
                    showAlert("Error", "Invalid amount.");
                }
            }
            return null;
        });
        dialog.showAndWait();
    }

    // Helper: Set active sidebar button style
    private void setActiveSidebarButton(Button activeButton) {
        for (Button b : allSidebarButtons) {
            b.setStyle("-fx-background-color: transparent; -fx-text-fill: #e0e6ed; -fx-font-size: 16; -fx-alignment: CENTER_LEFT; -fx-padding: 8 0 8 18;");
        }
        activeButton.setStyle("-fx-background-color: #2563eb; -fx-text-fill: white; -fx-font-size: 16; -fx-background-radius: 8; -fx-alignment: CENTER_LEFT; -fx-padding: 8 0 8 18;");
    }

    // Refactored: Main page creation
    private VBox createMainPage(Founder founder) {
        VBox mainPage = new VBox(10);
        mainPage.setPadding(new Insets(10, 16, 10, 16));
        mainPage.setStyle("-fx-background-color: #fff; -fx-background-radius: 12; -fx-effect: dropshadow(gaussian, #e0e0e0, 8, 0, 0, 2);");
        mainPage.setMaxWidth(700);
        mainPage.setMinWidth(400);
        Label mainTitle = new Label("Welcome, " + founder.getFullName());
        mainTitle.setFont(Font.font("Inter", FontWeight.BOLD, 26));
        mainTitle.setTextFill(Color.web("#222"));
        Label mainRole = new Label("Founder");
        mainRole.setFont(Font.font("Inter", FontWeight.NORMAL, 15));
        mainRole.setTextFill(Color.web("#666"));
        Separator mainSep = new Separator();
        mainSep.setPadding(new Insets(0, 0, 4, 0));
        // Founder Info (detailed)
        Label founderInfoTitle = new Label("Founder Information");
        founderInfoTitle.setFont(Font.font("Inter", FontWeight.BOLD, 20));
        founderInfoTitle.setTextFill(Color.web("#222"));
        founderInfoTitle.setPadding(new Insets(18, 0, 0, 0));
        Label founderInfo = new Label(
            "Name: " + founder.getFullName() +
            "\nPhone: " + founder.getPhoneNumber() +
            "\nUsername: " + founder.getUsername() +
            "\nAge: " + founder.getAge() +
            "\nGender: " + founder.getGender() +
            "\nSalary: $" + String.format("%.2f", founder.getSalary()) +
            "\nNet Income: $" + String.format("%.2f", founder.getNetIncome())
        );
        founderInfo.setFont(Font.font("Inter", 15));
        founderInfo.setTextFill(Color.web("#333"));
        founderInfo.setStyle("-fx-padding: 0 0 0 0;");
        founderInfo.setAlignment(javafx.geometry.Pos.TOP_LEFT);
        // Hospital Info (detailed)
        Label hospitalInfoTitle = new Label("Hospital Information");
        hospitalInfoTitle.setFont(Font.font("Inter", FontWeight.BOLD, 20));
        hospitalInfoTitle.setTextFill(Color.web("#222"));
        hospitalInfoTitle.setPadding(new Insets(18, 0, 0, 0));
        StringBuilder deptNames = new StringBuilder();
        for (var dept : founder.getDepartments()) {
            deptNames.append("    • ").append(dept.getName()).append(" (Head: ").append(dept.getHead() != null ? dept.getHead().getFullName() : "None").append(")\n");
        }
        StringBuilder docNames = new StringBuilder();
        for (var doc : founder.getDoctors()) {
            docNames.append("    • ").append(doc.getFullName()).append(" (" + doc.getSpecialty() + ")\n");
        }
        Label hospitalInfo = new Label(
            "Departments: " + founder.getDepartments().size() +
            (deptNames.length() > 0 ? "\n" + deptNames.toString() : "") +
            "Doctors: " + founder.getDoctors().size() +
            (docNames.length() > 0 ? "\n" + docNames.toString() : "")
        );
        hospitalInfo.setFont(Font.font("Inter", 15));
        hospitalInfo.setTextFill(Color.web("#333"));
        hospitalInfo.setStyle("-fx-padding: 0 0 0 0;");
        hospitalInfo.setAlignment(javafx.geometry.Pos.TOP_LEFT);
        // Financial Summary (highlight net income)
        Label financialSummaryTitle = new Label("Financial Summary");
        financialSummaryTitle.setFont(Font.font("Inter", FontWeight.BOLD, 20));
        financialSummaryTitle.setTextFill(Color.web("#222"));
        financialSummaryTitle.setPadding(new Insets(18, 0, 0, 0));
        String summary = founder.getFinancialSummary();
        double netIncome = founder.getNetIncome();
        String summaryNoPrefix = summary.replaceFirst("Financial Summary ", "");
        String[] parts = summaryNoPrefix.split(", Net Income: ");
        String summaryMain = parts.length > 0 ? parts[0] : summaryNoPrefix;
        String netIncomeStr = parts.length > 1 ? parts[1].replace("]", "") : String.format("%.2f", netIncome);
        Label financialSummary = new Label(summaryMain + ", Net Income: ");
        financialSummary.setFont(Font.font("Inter", 15));
        financialSummary.setTextFill(Color.web("#333"));
        Label netIncomeLabel = new Label("$" + netIncomeStr);
        netIncomeLabel.setFont(Font.font("Inter", FontWeight.BOLD, 15));
        netIncomeLabel.setTextFill(netIncome >= 0 ? Color.web("#22c55e") : Color.web("#ef4444"));
        HBox financialBox = new HBox(4, financialSummary, netIncomeLabel);
        financialBox.setAlignment(javafx.geometry.Pos.CENTER_LEFT);
        financialBox.setStyle("-fx-padding: 0 0 0 0;");
        // Reports (show latest after generating)
        Button btnMonthlyReport = new Button("Generate Monthly Report");
        btnMonthlyReport.setStyle("-fx-background-color: #2563eb; -fx-text-fill: white; -fx-font-size: 15; -fx-background-radius: 8;");
        Button btnStartupReport = new Button("Generate Startup Report");
        btnStartupReport.setStyle("-fx-background-color: #2563eb; -fx-text-fill: white; -fx-font-size: 15; -fx-background-radius: 8;");
        Button btnAddRevenue = new Button("Add Revenue");
        btnAddRevenue.setStyle("-fx-background-color: #22c55e; -fx-text-fill: white; -fx-font-size: 15; -fx-background-radius: 8;");
        Button btnAddExpense = new Button("Add Expense");
        btnAddExpense.setStyle("-fx-background-color: #ef4444; -fx-text-fill: white; -fx-font-size: 15; -fx-background-radius: 8;");
        btnMonthlyReport.setOnAction(e -> {
            founder.generateMonthlyReport();
            var rpts = founder.getReports();
            if (!rpts.isEmpty()) {
                var report = rpts.get(rpts.size() - 1);
                showAlert(report.getTitle(),
                    "Departments: " + report.getDepartmentCount() +
                    "\nDoctors: " + report.getDoctorCount() +
                    "\nTotal Revenue: $" + report.getTotalRevenue() +
                    "\nTotal Expenses: $" + report.getTotalExpenses() +
                    "\nNet Income: $" + report.getNetIncome()
                );
            }
        });
        btnStartupReport.setOnAction(e -> {
            founder.generateStartupReport();
            var rpts = founder.getReports();
            if (!rpts.isEmpty()) {
                var report = rpts.get(rpts.size() - 1);
                showAlert(report.getTitle(),
                    "Departments: " + report.getDepartmentCount() +
                    "\nDoctors: " + report.getDoctorCount() +
                    "\nTotal Revenue: $" + report.getTotalRevenue() +
                    "\nTotal Expenses: $" + report.getTotalExpenses() +
                    "\nNet Income: $" + report.getNetIncome()
                );
            }
        });
        btnAddRevenue.setOnAction(e -> showAddRevenueDialog(founder, financialSummary));
        btnAddExpense.setOnAction(e -> showAddExpenseDialog(founder, financialSummary));
        HBox reportBtns = new HBox(12, btnMonthlyReport, btnStartupReport, btnAddRevenue, btnAddExpense);
        reportBtns.setPadding(new Insets(24, 0, 0, 0));
        reportBtns.setAlignment(javafx.geometry.Pos.CENTER_LEFT);
        mainPage.getChildren().clear();
        mainPage.getChildren().addAll(
            mainTitle, mainRole, mainSep,
            founderInfoTitle, founderInfo,
            hospitalInfoTitle, hospitalInfo,
            financialSummaryTitle, financialBox,
            reportBtns
        );
        return mainPage;
    }
} 