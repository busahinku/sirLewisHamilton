package hospital.ui;

import hospital.objects.*;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
import java.time.LocalDateTime;
import java.util.*;

public class DoctorDashboard {
    private Stage primaryStage;
    private DashboardManager dashboardManager;
    private List<Button> allSidebarButtons;

    public DoctorDashboard(Stage primaryStage, DashboardManager dashboardManager) {
        this.primaryStage = primaryStage;
        this.dashboardManager = dashboardManager;
    }

    public void show(Doctor doctor) {
        BorderPane root = new BorderPane();

        // Sidebar
        VBox sidebar = new VBox(18);
        sidebar.setPadding(new Insets(32, 0, 32, 0));
        sidebar.setStyle("-fx-background-color: #0a1622; -fx-min-width: 290px; -fx-max-width: 290px;");
        sidebar.setPrefWidth(290);

        // User Info
        VBox userBox = new VBox(0);
        Label nameLabel = new Label(doctor.getFullName());
        nameLabel.setFont(Font.font("Inter", FontWeight.BOLD, 28));
        nameLabel.setTextFill(Color.WHITE);
        Label roleLabel = new Label("Doctor - " + doctor.getSpecialty());
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
        Button btnAppointments = new Button("Appointments");
        Button btnPatients = new Button("Patients");
        Button btnPrescriptions = new Button("Write Prescription");
        Button btnMedicalRecords = new Button("Medical Records");
        Button btnRooms = new Button("Manage Rooms");
        Button btnSchedule = new Button("Schedule");
        Label systemLabel = new Label("System");
        systemLabel.setTextFill(Color.WHITE);
        systemLabel.setFont(Font.font("Inter", FontWeight.BOLD, 15));
        systemLabel.setPadding(new Insets(18, 0, 0, 0));
        Button btnLogout = new Button("Log Out");
        btnLogout.setStyle("-fx-background-color: #dc2626; -fx-text-fill: white; -fx-font-size: 16; -fx-background-radius: 8; -fx-alignment: CENTER_LEFT; -fx-padding: 8 0 8 18; -fx-min-width: 220; -fx-cursor: hand;");
        btnLogout.setOnMouseEntered(e -> btnLogout.setStyle("-fx-background-color: #b91c1c; -fx-text-fill: white; -fx-font-size: 16; -fx-background-radius: 8; -fx-alignment: CENTER_LEFT; -fx-padding: 8 0 8 18; -fx-min-width: 220; -fx-cursor: hand;"));
        btnLogout.setOnMouseExited(e -> btnLogout.setStyle("-fx-background-color: #dc2626; -fx-text-fill: white; -fx-font-size: 16; -fx-background-radius: 8; -fx-alignment: CENTER_LEFT; -fx-padding: 8 0 8 18; -fx-min-width: 220; -fx-cursor: hand;"));
        allSidebarButtons = Arrays.asList(btnMain, btnAppointments, btnPatients, btnPrescriptions, btnMedicalRecords, btnRooms, btnSchedule);
        for (Button b : allSidebarButtons) {
            b.setStyle("-fx-background-color: transparent; -fx-text-fill: #e0e6ed; -fx-font-size: 16; -fx-alignment: CENTER_LEFT; -fx-padding: 8 0 8 18;");
            b.setPrefWidth(220);
        }
        btnMain.setStyle("-fx-background-color: #2563eb; -fx-text-fill: white; -fx-font-size: 16; -fx-background-radius: 8; -fx-alignment: CENTER_LEFT; -fx-padding: 8 0 8 18;");
        navBox.getChildren().addAll(btnMain, btnAppointments, btnPatients, btnPrescriptions, btnMedicalRecords, btnRooms, btnSchedule, systemLabel, btnLogout);
        sidebar.getChildren().addAll(userBox, sep1, navBox);

        // Main Content Area
        StackPane mainContent = new StackPane();
        mainContent.setPadding(new Insets(32, 32, 32, 32));

        // Main Page Content
        VBox mainPage = createMainPage(doctor);
        ScrollPane mainScrollPane = new ScrollPane(mainPage);
        mainScrollPane.setFitToWidth(true);
        mainScrollPane.setStyle("-fx-background-color:transparent;");
        mainContent.getChildren().clear();
        mainContent.getChildren().add(mainScrollPane);

        // Sidebar navigation actions
        btnMain.setOnAction(e -> {
            mainContent.getChildren().clear();
            VBox refreshedMainPage = createMainPage(doctor);
            ScrollPane refreshedScrollPane = new ScrollPane(refreshedMainPage);
            refreshedScrollPane.setFitToWidth(true);
            refreshedScrollPane.setStyle("-fx-background-color:transparent;");
            mainContent.getChildren().add(refreshedScrollPane);
            setActiveSidebarButton(btnMain);
        });

        btnAppointments.setOnAction(e -> {
            setActiveSidebarButton(btnAppointments);
            VBox apptPage = new VBox(16);
            apptPage.setPadding(new Insets(18));
            apptPage.setStyle("-fx-background-color: #fff; -fx-background-radius: 12; -fx-effect: dropshadow(gaussian, #e0e0e0, 8, 0, 0, 2);");
            apptPage.setMaxWidth(800);
            apptPage.setMinWidth(400);
            Label title = new Label("Appointments");
            title.setFont(Font.font("Inter", FontWeight.BOLD, 22));
            title.setTextFill(Color.web("#222"));
            apptPage.getChildren().add(title);
            List<Appointment> appointments = doctor.getAppointments();
            if (appointments.isEmpty()) {
                Label info = new Label("No appointments found.");
                info.setFont(Font.font("Inter", 15));
                info.setTextFill(Color.web("#333"));
                apptPage.getChildren().add(info);
            } else {
                for (Appointment appt : appointments) {
                    VBox apptBox = new VBox(2);
                    apptBox.setStyle("-fx-background-color: #f1f5f9; -fx-background-radius: 8; -fx-padding: 10 12 10 12; -fx-effect: dropshadow(gaussian, #e0e0e0, 2, 0, 0, 1);");
                    Label info = new Label(appt.GeneralInfo());
                    info.setFont(Font.font("Inter", 14));
                    info.setTextFill(Color.web("#222"));
                    info.setWrapText(true);
                    HBox btnBox = new HBox(8);
                    Button cancelBtn = new Button("Cancel");
                    cancelBtn.setStyle("-fx-background-color: #ef4444; -fx-text-fill: white; -fx-background-radius: 8;");
                    cancelBtn.setOnAction(ev -> {
                        appt.cancel();
                        showAlert("Appointment Cancelled", "Appointment has been cancelled.");
                        btnAppointments.fire();
                    });
                    btnBox.getChildren().add(cancelBtn);
                    if (!appt.isCompleted() && !appt.getStatus().equals("Completed")) {
                        Button completeBtn = new Button("Complete");
                        completeBtn.setStyle("-fx-background-color: #22c55e; -fx-text-fill: white; -fx-background-radius: 8;");
                        completeBtn.setOnAction(ev -> {
                            appt.setCompleted(true);
                            // Generate Bill
                            Patient patient = appt.getPatient();
                            boolean billExists = false;
                            for (Bill bill : patient.getBills()) {
                                for (BillItem item : bill.getItems()) {
                                    if (item.getDescription().contains(appt.getAppointmentId())) {
                                        billExists = true;
                                        break;
                                    }
                                }
                                if (billExists) break;
                            }
                            if (!billExists) {
                                String billId = "BILL-" + java.util.UUID.randomUUID();
                                Bill bill = new Bill(billId, patient, LocalDateTime.now().plusDays(14));
                                bill.addItem("Doctor Appointment (" + appt.getAppointmentId() + ")", appt.getCost());
                                patient.addBill(bill);
                            }
                            showAlert("Appointment Completed", "Appointment marked as completed and bill generated.");
                            btnAppointments.fire();
                        });
                        btnBox.getChildren().add(completeBtn);
                    }
                    apptBox.getChildren().addAll(info, btnBox);
                    apptPage.getChildren().add(apptBox);
                }
            }
            ScrollPane scrollPane = new ScrollPane(apptPage);
            scrollPane.setFitToWidth(true);
            scrollPane.setStyle("-fx-background-color:transparent;");
            mainContent.getChildren().clear();
            mainContent.getChildren().add(scrollPane);
        });

        btnPatients.setOnAction(e -> {
            setActiveSidebarButton(btnPatients);
            VBox patientsPage = new VBox(16);
            patientsPage.setPadding(new Insets(18));
            patientsPage.setStyle("-fx-background-color: #fff; -fx-background-radius: 12; -fx-effect: dropshadow(gaussian, #e0e0e0, 8, 0, 0, 2);");
            patientsPage.setMaxWidth(800);
            patientsPage.setMinWidth(400);
            Label title = new Label("Patients");
            title.setFont(Font.font("Inter", FontWeight.BOLD, 22));
            title.setTextFill(Color.web("#222"));
            patientsPage.getChildren().add(title);
            List<Patient> patients = doctor.getPatients();
            if (patients.isEmpty()) {
                Label info = new Label("No patients found.");
                info.setFont(Font.font("Inter", 15));
                info.setTextFill(Color.web("#333"));
                patientsPage.getChildren().add(info);
            } else {
                for (Patient patient : patients) {
                    VBox patientBox = new VBox(2);
                    patientBox.setStyle("-fx-background-color: #f1f5f9; -fx-background-radius: 8; -fx-padding: 10 12 10 12; -fx-effect: dropshadow(gaussian, #e0e0e0, 2, 0, 0, 1);");
                    Label info = new Label(patient.GeneralInfo());
                    info.setFont(Font.font("Inter", 14));
                    info.setTextFill(Color.web("#222"));
                    info.setWrapText(true);
                    patientBox.getChildren().add(info);
                    patientsPage.getChildren().add(patientBox);
                }
            }
            ScrollPane scrollPane = new ScrollPane(patientsPage);
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
            Label title = new Label("Write Prescription");
            title.setFont(Font.font("Inter", FontWeight.BOLD, 22));
            title.setTextFill(Color.web("#222"));
            prescPage.getChildren().add(title);

            // Patient selection
            ComboBox<Patient> patientBox = new ComboBox<>();
            patientBox.setPromptText("Select Patient");
            patientBox.getItems().addAll(doctor.getPatients());
            patientBox.setCellFactory(cb -> new ListCell<>() {
                @Override protected void updateItem(Patient patient, boolean empty) {
                    super.updateItem(patient, empty);
                    setText(empty || patient == null ? null : patient.getFullName());
                }
            });
            patientBox.setButtonCell(new ListCell<>() {
                @Override protected void updateItem(Patient patient, boolean empty) {
                    super.updateItem(patient, empty);
                    setText(empty || patient == null ? null : patient.getFullName());
                }
            });

            // Prescription details
            TextField medicationField = new TextField();
            medicationField.setPromptText("Medication Name");
            TextField dosageField = new TextField();
            dosageField.setPromptText("Dosage");
            TextField frequencyField = new TextField();
            frequencyField.setPromptText("Frequency");
            TextField durationField = new TextField();
            durationField.setPromptText("Duration");
            TextArea notesArea = new TextArea();
            notesArea.setPromptText("Additional Notes");
            notesArea.setPrefRowCount(3);

            Button writeBtn = new Button("Write Prescription");
            writeBtn.setStyle("-fx-background-color: #2563eb; -fx-text-fill: white; -fx-background-radius: 8;");
            writeBtn.setOnAction(ev -> {
                Patient patient = patientBox.getValue();
                String medication = medicationField.getText().trim();
                String dosage = dosageField.getText().trim();
                String frequency = frequencyField.getText().trim();
                String duration = durationField.getText().trim();
                String notes = notesArea.getText().trim();

                if (patient == null || medication.isEmpty() || dosage.isEmpty() || frequency.isEmpty() || duration.isEmpty()) {
                    showAlert("Error", "Please fill all required fields.");
                    return;
                }

                Prescription prescription = new Prescription(
                    medication,
                    dosage,
                    frequency,
                    patient,
                    doctor,
                    notes
                );
                patient.addPrescription(prescription);
                showAlert("Success", "Prescription written successfully.");
                
                // Clear fields
                patientBox.setValue(null);
                medicationField.clear();
                dosageField.clear();
                frequencyField.clear();
                durationField.clear();
                notesArea.clear();
            });

            prescPage.getChildren().addAll(patientBox, medicationField, dosageField, frequencyField, durationField, notesArea, writeBtn);
            ScrollPane scrollPane = new ScrollPane(prescPage);
            scrollPane.setFitToWidth(true);
            scrollPane.setStyle("-fx-background-color:transparent;");
            mainContent.getChildren().clear();
            mainContent.getChildren().add(scrollPane);
        });

        btnMedicalRecords.setOnAction(e -> {
            setActiveSidebarButton(btnMedicalRecords);
            VBox medRecPage = new VBox(16);
            medRecPage.setPadding(new Insets(18));
            medRecPage.setStyle("-fx-background-color: #fff; -fx-background-radius: 12; -fx-effect: dropshadow(gaussian, #e0e0e0, 8, 0, 0, 2);");
            medRecPage.setMaxWidth(800);
            medRecPage.setMinWidth(400);
            Label title = new Label("Medical Records");
            title.setFont(Font.font("Inter", FontWeight.BOLD, 22));
            title.setTextFill(Color.web("#222"));
            medRecPage.getChildren().add(title);

            // Patient selection
            ComboBox<Patient> patientBox = new ComboBox<>();
            patientBox.setPromptText("Select Patient");
            patientBox.getItems().addAll(doctor.getPatients());
            patientBox.setCellFactory(cb -> new ListCell<>() {
                @Override protected void updateItem(Patient patient, boolean empty) {
                    super.updateItem(patient, empty);
                    setText(empty || patient == null ? null : patient.getFullName());
                }
            });
            patientBox.setButtonCell(new ListCell<>() {
                @Override protected void updateItem(Patient patient, boolean empty) {
                    super.updateItem(patient, empty);
                    setText(empty || patient == null ? null : patient.getFullName());
                }
            });

            // Medical record fields
            TextField diagnosesField = new TextField();
            diagnosesField.setPromptText("Diagnoses (comma separated)");
            TextField proceduresField = new TextField();
            proceduresField.setPromptText("Procedures (comma separated)");
            TextField medicationsField = new TextField();
            medicationsField.setPromptText("Medications (comma separated)");
            TextField allergiesField = new TextField();
            allergiesField.setPromptText("Allergies (comma separated)");
            TextField immunizationsField = new TextField();
            immunizationsField.setPromptText("Immunizations (comma separated)");
            TextField labResultsField = new TextField();
            labResultsField.setPromptText("Lab Results");
            TextField bloodTypeField = new TextField();
            bloodTypeField.setPromptText("Blood Type");
            TextField heightField = new TextField();
            heightField.setPromptText("Height (cm)");
            TextField weightField = new TextField();
            weightField.setPromptText("Weight (kg)");
            TextArea notesArea = new TextArea();
            notesArea.setPromptText("Additional Notes");
            notesArea.setPrefRowCount(3);

            Button saveBtn = new Button("Save Medical Record");
            saveBtn.setStyle("-fx-background-color: #2563eb; -fx-text-fill: white; -fx-background-radius: 8;");
            saveBtn.setOnAction(ev -> {
                Patient patient = patientBox.getValue();
                if (patient == null) {
                    showAlert("Error", "Please select a patient.");
                    return;
                }

                String recordId = "MR-" + java.util.UUID.randomUUID();
                MedicalRecord record = new MedicalRecord(
                    recordId,
                    patient,
                    bloodTypeField.getText().trim(),
                    Double.parseDouble(heightField.getText().trim()),
                    Double.parseDouble(weightField.getText().trim())
                );
                // Populate lists from input fields
                for (String diag : diagnosesField.getText().trim().split(",")) {
                    if (!diag.trim().isEmpty()) record.addDiagnosis(diag.trim());
                }
                for (String proc : proceduresField.getText().trim().split(",")) {
                    if (!proc.trim().isEmpty()) record.addProcedure(proc.trim());
                }
                for (String med : medicationsField.getText().trim().split(",")) {
                    if (!med.trim().isEmpty()) record.addMedication(med.trim());
                }
                for (String allergy : allergiesField.getText().trim().split(",")) {
                    if (!allergy.trim().isEmpty()) record.addAllergy(allergy.trim());
                }
                for (String imm : immunizationsField.getText().trim().split(",")) {
                    if (!imm.trim().isEmpty()) record.addImmunization(imm.trim());
                }
                for (String lab : labResultsField.getText().trim().split(",")) {
                    if (!lab.trim().isEmpty()) record.addLabResult(lab.trim());
                }
                if (!notesArea.getText().trim().isEmpty()) {
                    record.addNote(notesArea.getText().trim());
                }
                patient.setMedicalRecord(record);
                showAlert("Success", "Medical record saved successfully.");
                
                // Clear fields
                patientBox.setValue(null);
                diagnosesField.clear();
                proceduresField.clear();
                medicationsField.clear();
                allergiesField.clear();
                immunizationsField.clear();
                labResultsField.clear();
                bloodTypeField.clear();
                heightField.clear();
                weightField.clear();
                notesArea.clear();
            });

            medRecPage.getChildren().addAll(patientBox, diagnosesField, proceduresField, medicationsField, 
                allergiesField, immunizationsField, labResultsField, bloodTypeField, heightField, 
                weightField, notesArea, saveBtn);
            ScrollPane scrollPane = new ScrollPane(medRecPage);
            scrollPane.setFitToWidth(true);
            scrollPane.setStyle("-fx-background-color:transparent;");
            mainContent.getChildren().clear();
            mainContent.getChildren().add(scrollPane);
        });

        btnRooms.setOnAction(e -> {
            setActiveSidebarButton(btnRooms);
            VBox roomsPage = new VBox(16);
            roomsPage.setPadding(new Insets(18));
            roomsPage.setStyle("-fx-background-color: #fff; -fx-background-radius: 12; -fx-effect: dropshadow(gaussian, #e0e0e0, 8, 0, 0, 2);");
            roomsPage.setMaxWidth(800);
            roomsPage.setMinWidth(400);
            Label title = new Label("Manage Rooms");
            title.setFont(Font.font("Inter", FontWeight.BOLD, 22));
            title.setTextFill(Color.web("#222"));
            roomsPage.getChildren().add(title);

            // Find founder to get rooms
            Founder founder = null;
            for (Person p : dashboardManager.getUsers()) {
                if (p instanceof Founder) {
                    founder = (Founder)p;
                    break;
                }
            }
            if (founder == null) {
                Label error = new Label("No rooms found (founder missing).");
                error.setTextFill(Color.RED);
                roomsPage.getChildren().add(error);
                mainContent.getChildren().clear();
                mainContent.getChildren().add(new ScrollPane(roomsPage));
                return;
            }

            // Room selection
            ComboBox<Room> roomBox = new ComboBox<>();
            roomBox.setPromptText("Select Room");
            roomBox.getItems().addAll(founder.getRooms());
            roomBox.setCellFactory(cb -> new ListCell<>() {
                @Override protected void updateItem(Room room, boolean empty) {
                    super.updateItem(room, empty);
                    setText(empty || room == null ? null : room.getRoomName() + " (" + room.getRoomType() + ")");
                }
            });
            roomBox.setButtonCell(new ListCell<>() {
                @Override protected void updateItem(Room room, boolean empty) {
                    super.updateItem(room, empty);
                    setText(empty || room == null ? null : room.getRoomName() + " (" + room.getRoomType() + ")");
                }
            });

            // Patient selection
            ComboBox<Patient> patientBox = new ComboBox<>();
            patientBox.setPromptText("Select Patient");
            patientBox.getItems().addAll(doctor.getPatients());
            patientBox.setCellFactory(cb -> new ListCell<>() {
                @Override protected void updateItem(Patient patient, boolean empty) {
                    super.updateItem(patient, empty);
                    setText(empty || patient == null ? null : patient.getFullName());
                }
            });
            patientBox.setButtonCell(new ListCell<>() {
                @Override protected void updateItem(Patient patient, boolean empty) {
                    super.updateItem(patient, empty);
                    setText(empty || patient == null ? null : patient.getFullName());
                }
            });

            Button assignBtn = new Button("Assign Room");
            assignBtn.setStyle("-fx-background-color: #2563eb; -fx-text-fill: white; -fx-background-radius: 8;");
            assignBtn.setOnAction(ev -> {
                Room room = roomBox.getValue();
                Patient patient = patientBox.getValue();
                if (room == null || patient == null) {
                    showAlert("Error", "Please select both room and patient.");
                    return;
                }
                if (room.getCurrentPatient() != null) {
                    showAlert("Error", "Room is already occupied.");
                    return;
                }
                room.assignPatient(patient);
                showAlert("Success", "Room assigned successfully.");
                
                // Clear selections
                roomBox.setValue(null);
                patientBox.setValue(null);
            });

            roomsPage.getChildren().addAll(roomBox, patientBox, assignBtn);
            ScrollPane scrollPane = new ScrollPane(roomsPage);
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
            schedulePage.setMaxWidth(800);
            schedulePage.setMinWidth(400);
            Label title = new Label("Schedule");
            title.setFont(Font.font("Inter", FontWeight.BOLD, 22));
            title.setTextFill(Color.web("#222"));
            schedulePage.getChildren().add(title);

            if (doctor.getStaticSchedule() == null) {
                Label info = new Label("No schedule available.");
                info.setFont(Font.font("Inter", 15));
                info.setTextFill(Color.web("#333"));
                schedulePage.getChildren().add(info);
            } else {
                for (StaticSchedule.Day day : StaticSchedule.Day.values()) {
                    VBox dayBox = new VBox(2);
                    dayBox.setStyle("-fx-background-color: #f1f5f9; -fx-background-radius: 8; -fx-padding: 10 12 10 12; -fx-effect: dropshadow(gaussian, #e0e0e0, 2, 0, 0, 1);");
                    Label dayLabel = new Label(day.toString());
                    dayLabel.setFont(Font.font("Inter", FontWeight.BOLD, 14));
                    dayLabel.setTextFill(Color.web("#222"));
                    List<java.time.LocalTime> times = doctor.getStaticSchedule().getAvailableTimeSlots(day, doctor.getAppointments());
                    if (times.isEmpty()) {
                        Label noSlots = new Label("No available slots");
                        noSlots.setFont(Font.font("Inter", 13));
                        noSlots.setTextFill(Color.web("#666"));
                        dayBox.getChildren().addAll(dayLabel, noSlots);
                    } else {
                        Label timesLabel = new Label("Available times: " + String.join(", ", times.stream().map(t -> t.toString()).toArray(String[]::new)));
                        timesLabel.setFont(Font.font("Inter", 13));
                        timesLabel.setTextFill(Color.web("#666"));
                        dayBox.getChildren().addAll(dayLabel, timesLabel);
                    }
                    schedulePage.getChildren().add(dayBox);
                }
            }
            ScrollPane scrollPane = new ScrollPane(schedulePage);
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

    // Main page for doctor
    private VBox createMainPage(Doctor doctor) {
        VBox mainPage = new VBox(10);
        mainPage.setPadding(new Insets(10, 16, 10, 16));
        mainPage.setStyle("-fx-background-color: #fff; -fx-background-radius: 12; -fx-effect: dropshadow(gaussian, #e0e0e0, 8, 0, 0, 2;");
        mainPage.setMaxWidth(700);
        mainPage.setMinWidth(400);
        Label mainTitle = new Label("Welcome, " + doctor.getFullName());
        mainTitle.setFont(Font.font("Inter", FontWeight.BOLD, 26));
        mainTitle.setTextFill(Color.web("#222"));
        Label mainRole = new Label("Doctor - " + doctor.getSpecialty());
        mainRole.setFont(Font.font("Inter", FontWeight.NORMAL, 15));
        mainRole.setTextFill(Color.web("#666"));
        Separator mainSep = new Separator();
        mainSep.setPadding(new Insets(0, 0, 4, 0));
        Label info = new Label(
            "Department: " + doctor.getDepartment() +
            "\nOffice: " + doctor.getOfficeNumber() +
            "\nExperience: " + doctor.getExperience() + " years" +
            "\nStatus: " + (doctor.isPrivate() ? "Private Doctor" : "Hospital Doctor")
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