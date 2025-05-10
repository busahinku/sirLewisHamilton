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

public class PatientDashboard {
    private Stage primaryStage;
    private DashboardManager dashboardManager;
    private List<Button> allSidebarButtons;

    public PatientDashboard(Stage primaryStage, DashboardManager dashboardManager) {
        this.primaryStage = primaryStage;
        this.dashboardManager = dashboardManager;
    }

    public void show(Patient patient) {
        BorderPane root = new BorderPane();

        // Sidebar
        VBox sidebar = new VBox(18);
        sidebar.setPadding(new Insets(32, 0, 32, 0));
        sidebar.setStyle("-fx-background-color: #0a1622; -fx-min-width: 290px; -fx-max-width: 290px;");
        sidebar.setPrefWidth(290);

        // User Info
        VBox userBox = new VBox(0);
        Label nameLabel = new Label(patient.getFullName());
        nameLabel.setFont(Font.font("Inter", FontWeight.BOLD, 28));
        nameLabel.setTextFill(Color.WHITE);
        Label roleLabel = new Label("Patient");
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
        Button btnCreateAppt = new Button("Create Appointment");
        Button btnBills = new Button("Bills");
        Button btnMedRec = new Button("Medical Record");
        Button btnPrescriptions = new Button("Prescriptions");
        Button btnReviews = new Button("Reviews");
        Label systemLabel = new Label("System");
        systemLabel.setTextFill(Color.WHITE);
        systemLabel.setFont(Font.font("Inter", FontWeight.BOLD, 15));
        systemLabel.setPadding(new Insets(18, 0, 0, 0));
        Button btnLogout = new Button("Log Out");
        btnLogout.setStyle("-fx-background-color: #dc2626; -fx-text-fill: white; -fx-font-size: 16; -fx-background-radius: 8; -fx-alignment: CENTER_LEFT; -fx-padding: 8 0 8 18; -fx-min-width: 220; -fx-cursor: hand;");
        btnLogout.setOnMouseEntered(e -> btnLogout.setStyle("-fx-background-color: #b91c1c; -fx-text-fill: white; -fx-font-size: 16; -fx-background-radius: 8; -fx-alignment: CENTER_LEFT; -fx-padding: 8 0 8 18; -fx-min-width: 220; -fx-cursor: hand;"));
        btnLogout.setOnMouseExited(e -> btnLogout.setStyle("-fx-background-color: #dc2626; -fx-text-fill: white; -fx-font-size: 16; -fx-background-radius: 8; -fx-alignment: CENTER_LEFT; -fx-padding: 8 0 8 18; -fx-min-width: 220; -fx-cursor: hand;"));
        allSidebarButtons = Arrays.asList(btnMain, btnAppointments, btnCreateAppt, btnBills, btnMedRec, btnPrescriptions, btnReviews);
        for (Button b : allSidebarButtons) {
            b.setStyle("-fx-background-color: transparent; -fx-text-fill: #e0e6ed; -fx-font-size: 16; -fx-alignment: CENTER_LEFT; -fx-padding: 8 0 8 18;");
            b.setPrefWidth(220);
        }
        btnMain.setStyle("-fx-background-color: #2563eb; -fx-text-fill: white; -fx-font-size: 16; -fx-background-radius: 8; -fx-alignment: CENTER_LEFT; -fx-padding: 8 0 8 18;");
        navBox.getChildren().addAll(btnMain, btnAppointments, btnCreateAppt, btnBills, btnMedRec, btnPrescriptions, btnReviews, systemLabel, btnLogout);
        sidebar.getChildren().addAll(userBox, sep1, navBox);

        // Main Content Area
        StackPane mainContent = new StackPane();
        mainContent.setPadding(new Insets(32, 32, 32, 32));

        // Main Page Content
        VBox mainPage = createMainPage(patient);
        Button addMoneyBtn = new Button("Add Money");
        addMoneyBtn.setStyle("-fx-background-color: #22c55e; -fx-text-fill: white; -fx-background-radius: 8;");
        addMoneyBtn.setOnAction(ev -> {
            TextInputDialog dialog = new TextInputDialog();
            dialog.setTitle("Add Money");
            dialog.setHeaderText(null);
            dialog.setContentText("Enter amount to add:");
            Optional<String> result = dialog.showAndWait();
            result.ifPresent(amountStr -> {
                try {
                    double amount = Double.parseDouble(amountStr);
                    if (amount <= 0) throw new Exception();
                    patient.setBalance(patient.getBalance() + amount);
                    showAlert("Success", "Balance updated. New balance: $" + String.format("%.2f", patient.getBalance()));
                    // Refresh main page
                    mainContent.getChildren().clear();
                    VBox refreshedMainPage = createMainPage(patient);
                    refreshedMainPage.getChildren().add(addMoneyBtn);
                    ScrollPane refreshedScrollPane = new ScrollPane(refreshedMainPage);
                    refreshedScrollPane.setFitToWidth(true);
                    refreshedScrollPane.setStyle("-fx-background-color:transparent;");
                    mainContent.getChildren().add(refreshedScrollPane);
                } catch (Exception ex) {
                    showAlert("Error", "Please enter a valid positive number.");
                }
            });
        });
        mainPage.getChildren().add(addMoneyBtn);
        ScrollPane mainScrollPane = new ScrollPane(mainPage);
        mainScrollPane.setFitToWidth(true);
        mainScrollPane.setStyle("-fx-background-color:transparent;");
        mainContent.getChildren().clear();
        mainContent.getChildren().add(mainScrollPane);

        // Sidebar navigation actions
        btnMain.setOnAction(e -> {
            mainContent.getChildren().clear();
            VBox refreshedMainPage = createMainPage(patient);
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
            List<Appointment> appointments = patient.getAppointments();
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
        btnCreateAppt.setOnAction(e -> {
            setActiveSidebarButton(btnCreateAppt);
            VBox createPage = new VBox(16);
            createPage.setPadding(new Insets(18));
            createPage.setStyle("");
            createPage.setMaxWidth(800);
            createPage.setMinWidth(400);
            Label title = new Label("Create Appointment");
            title.setFont(Font.font("Inter", FontWeight.BOLD, 22));
            title.setTextFill(Color.web("#222"));
            createPage.getChildren().add(title);

            // Find founder to get departments
            Founder founder = null;
            for (Person p : dashboardManager.getUsers()) {
                if (p instanceof Founder) {
                    founder = (Founder)p;
                    break;
                }
            }
            if (founder == null) {
                Label error = new Label("No departments found (founder missing).");
                error.setTextFill(Color.RED);
                createPage.getChildren().add(error);
                mainContent.getChildren().clear();
                mainContent.getChildren().add(new ScrollPane(createPage));
                return;
            }

            // Department selection
            Label deptLabel = new Label("1. Choose Department");
            deptLabel.setFont(Font.font("Inter", FontWeight.BOLD, 16));
            ComboBox<Department> deptBox = new ComboBox<>();
            deptBox.setPromptText("Select Department");
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

            // Doctor selection
            Label docLabel = new Label("2. Choose Doctor");
            docLabel.setFont(Font.font("Inter", FontWeight.BOLD, 16));
            ComboBox<Doctor> docBox = new ComboBox<>();
            docBox.setPromptText("Select Doctor");
            for (Person p : dashboardManager.getUsers()) {
                if (p instanceof Doctor) {
                    docBox.getItems().add((Doctor)p);
                }
            }
            // Set custom cell factory and button cell for doctor ComboBox
            docBox.setCellFactory(cb -> new ListCell<>() {
                @Override protected void updateItem(Doctor doc, boolean empty) {
                    super.updateItem(doc, empty);
                    setText(empty || doc == null ? null : doc.getFullName() + " (" + doc.getSpecialty() + ")");
                }
            });
            docBox.setButtonCell(new ListCell<>() {
                @Override protected void updateItem(Doctor doc, boolean empty) {
                    super.updateItem(doc, empty);
                    setText(empty || doc == null ? null : doc.getFullName() + " (" + doc.getSpecialty() + ")");
                }
            });

            // Available slots UI
            Label slotLabel = new Label("3. Choose Date & Time");
            slotLabel.setFont(Font.font("Inter", FontWeight.BOLD, 16));
            VBox slotBox = new VBox(8);
            slotBox.setPadding(new Insets(0,0,0,0));
            slotBox.setStyle("");
            slotBox.setMinHeight(60);
            slotBox.setMaxHeight(200);
            slotBox.setPrefHeight(120);
            slotBox.setPrefWidth(400);
            slotBox.getChildren().clear();

            // Track selected slot
            final LocalDateTime[] selectedSlot = {null};
            Button createBtn = new Button("Create Appointment");
            createBtn.setStyle("-fx-background-color: #22c55e; -fx-text-fill: white; -fx-background-radius: 8;");
            createBtn.setDisable(true);

            // When department is selected, populate doctors
            deptBox.setOnAction(ev -> {
                Department dept = deptBox.getValue();
                docBox.getItems().clear();
                if (dept != null) {
                    docBox.getItems().addAll(dept.getDoctors());
                    docBox.setDisable(false);
                } else {
                    docBox.setDisable(true);
                }
                slotBox.getChildren().clear();
                createBtn.setDisable(true);
            });

            // When doctor is selected, show available slots
            docBox.setOnAction(ev -> {
                slotBox.getChildren().clear();
                createBtn.setDisable(true);
                Doctor doc = docBox.getValue();
                if (doc == null || doc.getStaticSchedule() == null) {
                    slotBox.getChildren().add(new Label("No schedule available for this doctor."));
                    return;
                }
                // Show available slots for the next 7 days
                List<Button> slotButtons = new ArrayList<>();
                LocalDateTime now = LocalDateTime.now();
                for (int i = 0; i < 7; i++) {
                    LocalDateTime day = now.plusDays(i);
                    StaticSchedule.Day scheduleDay;
                    try {
                        scheduleDay = StaticSchedule.Day.values()[day.getDayOfWeek().getValue() - 1];
                    } catch (Exception ex) {
                        continue;
                    }
                    List<java.time.LocalTime> availableTimes = doc.getStaticSchedule().getAvailableTimeSlots(scheduleDay, doc.getAppointments());
                    if (!availableTimes.isEmpty()) {
                        Label dayLabel = new Label(day.getDayOfWeek() + " (" + day.toLocalDate() + "):");
                        dayLabel.setFont(Font.font("Inter", FontWeight.BOLD, 14));
                        slotBox.getChildren().add(dayLabel);
                        FlowPane timePane = new FlowPane();
                        timePane.setHgap(8);
                        timePane.setVgap(8);
                        for (java.time.LocalTime t : availableTimes) {
                            Button slotBtn = new Button(t.toString());
                            slotBtn.setStyle("-fx-background-color: #2563eb; -fx-text-fill: white; -fx-background-radius: 8;");
                            slotBtn.setOnAction(evt -> {
                                // Deselect all other buttons
                                for (Button b : slotButtons) b.setStyle("-fx-background-color: #2563eb; -fx-text-fill: white; -fx-background-radius: 8;");
                                slotBtn.setStyle("-fx-background-color: #22c55e; -fx-text-fill: white; -fx-background-radius: 8;");
                                selectedSlot[0] = LocalDateTime.of(day.toLocalDate(), t);
                                createBtn.setDisable(false);
                            });
                            slotButtons.add(slotBtn);
                            timePane.getChildren().add(slotBtn);
                        }
                        // Wrap the timePane in a horizontal ScrollPane to prevent overflow
                        ScrollPane timeScroll = new ScrollPane(timePane);
                        timeScroll.setHbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
                        timeScroll.setVbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
                        timeScroll.setFitToHeight(true);
                        timeScroll.setFitToWidth(false);
                        timeScroll.setPrefHeight(48);
                        timeScroll.setMaxWidth(700);
                        timeScroll.setStyle("-fx-background-color:transparent; -fx-padding: 0 0 8 0;");
                        timeScroll.setOnMousePressed(event -> {
                            timeScroll.setUserData(event.getX());
                        });
                        timeScroll.setOnMouseDragged(event -> {
                            double startX = (double) timeScroll.getUserData();
                            double deltaX = startX - event.getX();
                            timeScroll.setHvalue(timeScroll.getHvalue() + deltaX / timeScroll.getContent().getBoundsInLocal().getWidth());
                            timeScroll.setUserData(event.getX());
                        });
                        slotBox.getChildren().add(timeScroll);
                    }
                }
                if (slotBox.getChildren().isEmpty()) {
                    slotBox.getChildren().add(new Label("No available slots in the next 7 days."));
                }
            });

            createBtn.setOnAction(ev -> {
                Department dept = deptBox.getValue();
                Doctor doc = docBox.getValue();
                LocalDateTime slot = selectedSlot[0];
                if (dept == null || doc == null || slot == null) {
                    showAlert("Error", "Please select department, doctor, and slot.");
                    return;
                }
                int duration = 20; // Default duration, or you can add a field for this
                StaticSchedule.Day day = StaticSchedule.Day.values()[slot.getDayOfWeek().getValue() - 1];
                if (!doc.isAvailable(day, slot.toLocalTime(), duration)) {
                    showAlert("Error", "This time slot is no longer available for this doctor.");
                    return;
                }
                Appointment appt = doc.scheduleAppointment(patient, day, slot.toLocalTime(), duration);
                showAlert("Appointment Created", "Appointment created successfully.");
                btnAppointments.fire();
            });

            createPage.getChildren().addAll(deptLabel, deptBox, docLabel, docBox, slotLabel, slotBox, createBtn);
            ScrollPane scrollPane = new ScrollPane(createPage);
            scrollPane.setFitToWidth(true);
            scrollPane.setStyle("-fx-background-color:transparent;");
            mainContent.getChildren().clear();
            mainContent.getChildren().add(scrollPane);
        });
        btnBills.setOnAction(e -> {
            setActiveSidebarButton(btnBills);
            VBox billPage = new VBox(16);
            billPage.setPadding(new Insets(18));
            billPage.setStyle("-fx-background-color: #fff; -fx-background-radius: 12; -fx-effect: dropshadow(gaussian, #e0e0e0, 8, 0, 0, 2);");
            billPage.setMaxWidth(800);
            billPage.setMinWidth(400);
            Label title = new Label("Bills");
            title.setFont(Font.font("Inter", FontWeight.BOLD, 22));
            title.setTextFill(Color.web("#222"));
            billPage.getChildren().add(title);
            List<Bill> bills = patient.getBills();
            if (bills.isEmpty()) {
                Label info = new Label("No bills found.");
                info.setFont(Font.font("Inter", 15));
                info.setTextFill(Color.web("#333"));
                billPage.getChildren().add(info);
            } else {
                for (Bill bill : bills) {
                    VBox billBox = new VBox(2);
                    billBox.setStyle("-fx-background-color: #f1f5f9; -fx-background-radius: 8; -fx-padding: 10 12 10 12; -fx-effect: dropshadow(gaussian, #e0e0e0, 2, 0, 0, 1);");
                    Label info = new Label(bill.toString());
                    info.setFont(Font.font("Inter", 14));
                    info.setTextFill(Color.web("#222"));
                    info.setWrapText(true);
                    if (!bill.isPaid()) {
                        Button payBtn = new Button("Pay");
                        payBtn.setStyle("-fx-background-color: #22c55e; -fx-text-fill: white; -fx-background-radius: 8;");
                        double unpaid = bill.getTotalAmount() - bill.getPaidAmount();
                        payBtn.setDisable(patient.getBalance() < unpaid);
                        payBtn.setOnAction(ev -> {
                            patient.payBill(bill, unpaid, "Card");
                            showAlert("Bill Paid", "Bill paid successfully.");
                            btnBills.fire();
                        });
                        billBox.getChildren().addAll(info, payBtn);
                        if (patient.getBalance() < unpaid) {
                            Label warn = new Label("Insufficient balance. Please add money to pay this bill.");
                            warn.setFont(Font.font("Inter", 12));
                            warn.setTextFill(Color.web("#ef4444"));
                            billBox.getChildren().add(warn);
                        }
                    } else {
                        billBox.getChildren().add(info);
                    }
                    billPage.getChildren().add(billBox);
                }
            }
            Button addMoneyBtn2 = new Button("Add Money");
            addMoneyBtn2.setStyle("-fx-background-color: #22c55e; -fx-text-fill: white; -fx-background-radius: 8;");
            addMoneyBtn2.setOnAction(ev -> {
                TextInputDialog dialog = new TextInputDialog();
                dialog.setTitle("Add Money");
                dialog.setHeaderText(null);
                dialog.setContentText("Enter amount to add:");
                Optional<String> result = dialog.showAndWait();
                result.ifPresent(amountStr -> {
                    try {
                        double amount = Double.parseDouble(amountStr);
                        if (amount <= 0) throw new Exception();
                        patient.setBalance(patient.getBalance() + amount);
                        showAlert("Success", "Balance updated. New balance: $" + String.format("%.2f", patient.getBalance()));
                        btnBills.fire();
                    } catch (Exception ex) {
                        showAlert("Error", "Please enter a valid positive number.");
                    }
                });
            });
            billPage.getChildren().add(addMoneyBtn2);
            ScrollPane scrollPane = new ScrollPane(billPage);
            scrollPane.setFitToWidth(true);
            scrollPane.setStyle("-fx-background-color:transparent;");
            mainContent.getChildren().clear();
            mainContent.getChildren().add(scrollPane);
        });
        btnMedRec.setOnAction(e -> {
            setActiveSidebarButton(btnMedRec);
            VBox medPage = new VBox(16);
            medPage.setPadding(new Insets(18));
            medPage.setStyle("-fx-background-color: #fff; -fx-background-radius: 12; -fx-effect: dropshadow(gaussian, #e0e0e0, 8, 0, 0, 2);");
            medPage.setMaxWidth(800);
            medPage.setMinWidth(400);
            Label title = new Label("Medical Record");
            title.setFont(Font.font("Inter", FontWeight.BOLD, 22));
            title.setTextFill(Color.web("#222"));
            medPage.getChildren().add(title);
            MedicalRecord record = patient.getMedicalRecord();
            if (record == null) {
                Label info = new Label("No medical record found.");
                info.setFont(Font.font("Inter", 15));
                info.setTextFill(Color.web("#333"));
                medPage.getChildren().add(info);
            } else {
                Label info = new Label("Diagnoses: " + record.getDiagnoses() +
                        "\nProcedures: " + record.getProcedures() +
                        "\nMedications: " + record.getMedications() +
                        "\nAllergies: " + record.getAllergies() +
                        "\nImmunizations: " + record.getImmunizations() +
                        "\nLab Results: " + record.getLabResults() +
                        "\nBlood Type: " + record.getBloodType() +
                        "\nHeight: " + record.getHeight() +
                        "\nWeight: " + record.getWeight() +
                        "\nNotes: " + record.getNotes());
                info.setFont(Font.font("Inter", 14));
                info.setTextFill(Color.web("#222"));
                info.setWrapText(true);
                medPage.getChildren().add(info);
            }
            ScrollPane scrollPane = new ScrollPane(medPage);
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
            Label title = new Label("Prescriptions");
            title.setFont(Font.font("Inter", FontWeight.BOLD, 22));
            title.setTextFill(Color.web("#222"));
            prescPage.getChildren().add(title);
            List<Prescription> prescriptions = patient.getPrescriptions();
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
        btnReviews.setOnAction(e -> {
            setActiveSidebarButton(btnReviews);
            VBox reviewPage = new VBox(16);
            reviewPage.setPadding(new Insets(18));
            reviewPage.setStyle("");
            reviewPage.setMaxWidth(800);
            reviewPage.setMinWidth(400);
            Label title = new Label("Reviews");
            title.setFont(Font.font("Inter", FontWeight.BOLD, 22));
            title.setTextFill(Color.web("#222"));
            reviewPage.getChildren().add(title);

            // List all doctors and their average ratings
            Label allDocsLabel = new Label("All Doctors and Average Ratings:");
            allDocsLabel.setFont(Font.font("Inter", FontWeight.BOLD, 16));
            reviewPage.getChildren().add(allDocsLabel);
            for (Person p : dashboardManager.getUsers()) {
                if (p instanceof Doctor) {
                    Doctor doc = (Doctor)p;
                    double avg = doc.calculateAverageRating();
                    String avgText = avg > 0 ? String.format("%.2f/5", avg) : "No reviews yet";
                    Label docLabel = new Label(doc.getFullName() + " (" + doc.getSpecialty() + ") - Average Rating: " + avgText);
                    docLabel.setFont(Font.font("Inter", 14));
                    docLabel.setTextFill(Color.web("#333"));
                    reviewPage.getChildren().add(docLabel);
                }
            }

            // List all reviews for this patient
            List<Review> allReviews = new ArrayList<>();
            for (Person p : dashboardManager.getUsers()) {
                if (p instanceof Doctor) {
                    for (Review r : ((Doctor)p).getReviews()) {
                        if (r.getReviewer().equals(patient)) {
                            allReviews.add(r);
                        }
                    }
                }
            }
            if (allReviews.isEmpty()) {
                Label info = new Label("No reviews found.");
                info.setFont(Font.font("Inter", 15));
                info.setTextFill(Color.web("#333"));
                reviewPage.getChildren().add(info);
            } else {
                for (Review r : allReviews) {
                    VBox rBox = new VBox(2);
                    rBox.setStyle("-fx-background-color: #f1f5f9; -fx-background-radius: 8; -fx-padding: 10 12 10 12; -fx-effect: dropshadow(gaussian, #e0e0e0, 2, 0, 0, 1);");
                    Label info = new Label(r.GeneralInfo());
                    info.setFont(Font.font("Inter", 14));
                    info.setTextFill(Color.web("#222"));
                    info.setWrapText(true);
                    rBox.getChildren().add(info);
                    reviewPage.getChildren().add(rBox);
                }
            }
            // Add review for any doctor
            Label addLabel = new Label("Add Review for a Doctor:");
            addLabel.setFont(Font.font("Inter", FontWeight.BOLD, 16));
            ComboBox<Doctor> docBox = new ComboBox<>();
            docBox.setPromptText("Select Doctor");
            for (Person p : dashboardManager.getUsers()) {
                if (p instanceof Doctor) {
                    docBox.getItems().add((Doctor)p);
                }
            }
            // Show average rating for selected doctor
            Label avgRatingLabel = new Label();
            avgRatingLabel.setFont(Font.font("Inter", 13));
            avgRatingLabel.setTextFill(Color.web("#666"));
            docBox.setOnAction(ev -> {
                Doctor selected = docBox.getValue();
                if (selected != null) {
                    double avg = selected.calculateAverageRating();
                    avgRatingLabel.setText("Average Rating: " + (avg > 0 ? String.format("%.2f/5", avg) : "No reviews yet"));
                } else {
                    avgRatingLabel.setText("");
                }
            });
            TextField ratingField = new TextField();
            ratingField.setPromptText("Rating (1-5)");
            TextField commentField = new TextField();
            commentField.setPromptText("Comment");
            Button addBtn = new Button("Add Review");
            addBtn.setStyle("-fx-background-color: #2563eb; -fx-text-fill: white; -fx-background-radius: 8;");
            addBtn.setOnAction(ev -> {
                Doctor doc = docBox.getValue();
                String ratingStr = ratingField.getText().trim();
                String comment = commentField.getText().trim();
                if (doc == null || ratingStr.isEmpty() || comment.isEmpty()) {
                    showAlert("Error", "Fill all fields.");
                    return;
                }
                try {
                    int rating = Integer.parseInt(ratingStr);
                    if (rating < 1 || rating > 5) throw new Exception();
                    Review review = new Review(patient, doc, comment, rating);
                    doc.addReview(review);
                    showAlert("Review Added", "Review added successfully.");
                    btnReviews.fire();
                } catch (Exception ex) {
                    showAlert("Error", "Invalid rating.");
                }
            });
            reviewPage.getChildren().addAll(addLabel, docBox, avgRatingLabel, ratingField, commentField, addBtn);
            ScrollPane scrollPane = new ScrollPane(reviewPage);
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

    // Main page for patient
    private VBox createMainPage(Patient patient) {
        VBox mainPage = new VBox(10);
        mainPage.setPadding(new Insets(10, 16, 10, 16));
        mainPage.setStyle("-fx-background-color: #fff; -fx-background-radius: 12; -fx-effect: dropshadow(gaussian, #e0e0e0, 8, 0, 0, 2;");
        mainPage.setMaxWidth(700);
        mainPage.setMinWidth(400);
        Label mainTitle = new Label("Welcome, " + patient.getFullName());
        mainTitle.setFont(Font.font("Inter", FontWeight.BOLD, 26));
        mainTitle.setTextFill(Color.web("#222"));
        Label mainRole = new Label("Patient");
        mainRole.setFont(Font.font("Inter", FontWeight.NORMAL, 15));
        mainRole.setTextFill(Color.web("#666"));
        Separator mainSep = new Separator();
        mainSep.setPadding(new Insets(0, 0, 4, 0));
        Label info = new Label(
            "Age: " + patient.getAge() +
            "\nInsurance: " + (patient.hasInsurance() ? patient.getInsuranceProvider() : "None") +
            "\nBalance: $" + String.format("%.2f", patient.getBalance()) +
            "\nCurrent Room: " + (patient.getCurrentRoom() != null ? patient.getCurrentRoom().getRoomName() : "None")
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