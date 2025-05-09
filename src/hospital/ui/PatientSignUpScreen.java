package hospital.ui;

import hospital.objects.Patient;
import hospital.objects.Person;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import java.util.List;
import java.util.UUID;
import javafx.scene.paint.Color;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class PatientSignUpScreen {
    private Stage primaryStage;
    private List<Person> users;

    public PatientSignUpScreen(Stage primaryStage, List<Person> users) {
        this.primaryStage = primaryStage;
        this.users = users;
    }

    public void show() {
        primaryStage.setTitle("Patient Registration");

        VBox root = new VBox();
        root.setAlignment(Pos.CENTER);
        root.setSpacing(0);
        root.setStyle("-fx-background-color: white;");
        root.setPadding(new Insets(18, 0, 18, 0));

        // Logo at the top
        ImageView logo = new ImageView(new Image("file:src/resources/logo.png"));
        logo.setFitWidth(170);
        logo.setPreserveRatio(true);
        VBox logoBox = new VBox(logo);
        logoBox.setAlignment(Pos.CENTER);
        logoBox.setPadding(new Insets(18, 0, 10, 0)); // More margin above, less below

        VBox card = new VBox(18);
        card.setAlignment(Pos.TOP_LEFT);
        card.setPadding(new Insets(24, 36, 8, 24)); // More padding top, less bottom
        card.setStyle("-fx-background-color: #f2f2f2; -fx-background-radius: 12; -fx-effect: dropshadow(gaussian, #f1f1f1, 10, 0, 0, 2);");
        card.setMaxHeight(600);
        card.setMaxWidth(500);

        Text scenetitle = new Text("Patient Registration");
        scenetitle.setFont(Font.font("Georgia", FontWeight.BOLD, 22));
        VBox scenetitleBox = new VBox(scenetitle);
        scenetitleBox.setAlignment(Pos.CENTER_LEFT);

        Text subtitle = new Text("Please fill in your details to create an account.");
        subtitle.setFont(Font.font("Arial", FontWeight.NORMAL, 12));
        subtitle.setFill(Color.GRAY);
        VBox subtitleBox = new VBox(subtitle);
        subtitleBox.setAlignment(Pos.CENTER_LEFT);
        subtitleBox.setPadding(new Insets(0, 0, 0, 0));

        Separator sep = new Separator();

        // --- Three Column Layout ---
        VBox col1 = new VBox(16); // Personal Info, 16px between fields
        col1.setAlignment(Pos.TOP_LEFT);
        col1.setPrefWidth(150);
        VBox col2 = new VBox(16); // Account/Insurance
        col2.setAlignment(Pos.TOP_LEFT);
        col2.setPrefWidth(150);
        VBox col3 = new VBox(16); // Medical Info
        col3.setAlignment(Pos.TOP_LEFT);
        col3.setPrefWidth(150);

        // Helper to add label+input with tight spacing
        java.util.function.BiConsumer<Label, Control> addField = (label, field) -> {
            VBox pair = new VBox(4, label, field); // 2px spacing between label and field
            col1.getChildren().add(pair);
        };
        java.util.function.BiConsumer<Label, Control> addField2 = (label, field) -> {
            VBox pair = new VBox(4, label, field);
            col2.getChildren().add(pair);
        };
        java.util.function.BiConsumer<Label, Control> addField3 = (label, field) -> {
            VBox pair = new VBox(4, label, field);
            col3.getChildren().add(pair);
        };

        // Personal Information
        Label firstNameLabel = new Label("First Name:");
        firstNameLabel.setFont(Font.font("Arial", FontWeight.SEMI_BOLD, 13));
        firstNameLabel.setTextFill(Color.DARKGRAY);
        TextField firstNameField = new TextField();
        firstNameField.setPrefWidth(325);
        firstNameField.setPrefHeight(28);
        addField.accept(firstNameLabel, firstNameField);

        Label lastNameLabel = new Label("Last Name:");
        lastNameLabel.setFont(Font.font("Arial", FontWeight.SEMI_BOLD, 14));
        lastNameLabel.setTextFill(Color.DARKGRAY);
        TextField lastNameField = new TextField();
        lastNameField.setPrefWidth(325);
        lastNameField.setPrefHeight(28);
        addField.accept(lastNameLabel, lastNameField);

        Label ageLabel = new Label("Age:");
        ageLabel.setFont(Font.font("Arial", FontWeight.SEMI_BOLD, 14));
        ageLabel.setTextFill(Color.DARKGRAY);
        TextField ageField = new TextField();
        ageField.setPrefWidth(325);
        ageField.setPrefHeight(28);
        addField.accept(ageLabel, ageField);

        Label genderLabel = new Label("Gender (M/F):");
        genderLabel.setFont(Font.font("Arial", FontWeight.SEMI_BOLD, 14));
        genderLabel.setTextFill(Color.DARKGRAY);
        TextField genderField = new TextField();
        genderField.setPrefWidth(325);
        genderField.setPrefHeight(28);
        addField.accept(genderLabel, genderField);

        // Account Information
        Label phoneLabel = new Label("Phone Number:");
        phoneLabel.setFont(Font.font("Arial", FontWeight.SEMI_BOLD, 14));
        phoneLabel.setTextFill(Color.DARKGRAY);
        TextField phoneField = new TextField();
        phoneField.setPrefWidth(325);
        phoneField.setPrefHeight(28);
        addField2.accept(phoneLabel, phoneField);

        Label usernameLabel = new Label("Username:");
        usernameLabel.setFont(Font.font("Arial", FontWeight.SEMI_BOLD, 14));
        usernameLabel.setTextFill(Color.DARKGRAY);
        TextField usernameField = new TextField();
        usernameField.setPrefWidth(325);
        usernameField.setPrefHeight(28);
        addField2.accept(usernameLabel, usernameField);

        Label passwordLabel = new Label("Password:");
        passwordLabel.setFont(Font.font("Arial", FontWeight.SEMI_BOLD, 14));
        passwordLabel.setTextFill(Color.DARKGRAY);
        PasswordField passwordField = new PasswordField();
        passwordField.setPrefWidth(325);
        passwordField.setPrefHeight(28);
        addField2.accept(passwordLabel, passwordField);

        // Insurance Information
        Label hasInsuranceLabel = new Label("Has Insurance:");
        hasInsuranceLabel.setFont(Font.font("Arial", FontWeight.SEMI_BOLD, 14));
        hasInsuranceLabel.setTextFill(Color.DARKGRAY);
        CheckBox hasInsuranceCheck = new CheckBox();
        VBox insuranceBox = new VBox(2, hasInsuranceLabel, hasInsuranceCheck);
        col2.getChildren().add(insuranceBox);

        Label insuranceProviderLabel = new Label("Insurance Provider:");
        insuranceProviderLabel.setFont(Font.font("Arial", FontWeight.SEMI_BOLD, 14));
        insuranceProviderLabel.setTextFill(Color.DARKGRAY);
        TextField insuranceProviderField = new TextField();
        insuranceProviderField.setPrefWidth(325);
        insuranceProviderField.setPrefHeight(28);
        addField2.accept(insuranceProviderLabel, insuranceProviderField);

        Label insurancePolicyLabel = new Label("Insurance Policy Number:");
        insurancePolicyLabel.setFont(Font.font("Arial", FontWeight.SEMI_BOLD, 14));
        insurancePolicyLabel.setTextFill(Color.DARKGRAY);
        TextField insurancePolicyField = new TextField();
        insurancePolicyField.setPrefWidth(325);
        insurancePolicyField.setPrefHeight(28);
        addField2.accept(insurancePolicyLabel, insurancePolicyField);

        // Medical Record Information
        Label bloodTypeLabel = new Label("Blood Type:");
        bloodTypeLabel.setFont(Font.font("Arial", FontWeight.SEMI_BOLD, 14));
        bloodTypeLabel.setTextFill(Color.DARKGRAY);
        TextField bloodTypeField = new TextField();
        bloodTypeField.setPrefWidth(325);
        bloodTypeField.setPrefHeight(28);
        addField3.accept(bloodTypeLabel, bloodTypeField);

        Label heightLabel = new Label("Height (cm):");
        heightLabel.setFont(Font.font("Arial", FontWeight.SEMI_BOLD, 14));
        heightLabel.setTextFill(Color.DARKGRAY);
        TextField heightField = new TextField();
        heightField.setPrefWidth(325);
        heightField.setPrefHeight(28);
        addField3.accept(heightLabel, heightField);

        Label weightLabel = new Label("Weight (kg):");
        weightLabel.setFont(Font.font("Arial", FontWeight.SEMI_BOLD, 14));
        weightLabel.setTextFill(Color.DARKGRAY);
        TextField weightField = new TextField();
        weightField.setPrefWidth(325);
        weightField.setPrefHeight(28);
        addField3.accept(weightLabel, weightField);

        HBox columns = new HBox(24, col1, col2, col3);
        columns.setAlignment(Pos.TOP_CENTER);

        // Buttons
        Button submitBtn = new Button("Submit");
        submitBtn.setStyle("-fx-background-color: #c0392b; -fx-text-fill: white; -fx-font-size: 16; -fx-background-radius: 8;");
        submitBtn.setPrefWidth(325);
        submitBtn.setPrefHeight(28);
        Button backBtn = new Button("Back to Login");
        backBtn.setStyle("-fx-background-color: #f2f2f2; -fx-font-size: 16; -fx-background-radius: 8; ");
        backBtn.setPrefWidth(325);
        backBtn.setPrefHeight(28);
        VBox btnBox = new VBox(10, submitBtn, backBtn);
        btnBox.setAlignment(Pos.CENTER);
        btnBox.setPadding(new Insets(10, 0, 0, 0));

        Text actiontarget = new Text();
        actiontarget.setFill(Color.FIREBRICK);
        VBox actionBox = new VBox(actiontarget);
        actionBox.setAlignment(Pos.CENTER_LEFT);
        actionBox.setPadding(new Insets(8, 0, 0, 0));

        // Add all to card
        card.getChildren().clear();
        card.getChildren().addAll(scenetitleBox, subtitleBox, sep, columns, btnBox, actionBox);

        // Add everything to root
        root.getChildren().clear();
        root.getChildren().addAll(logoBox, card);
        root.setPadding(new Insets(0, 0, 40, 0)); // Adds 40px padding at the bottom
        root.setSpacing(0);

        Scene scene = new Scene(root, 1280, 720);
        primaryStage.setScene(scene);

        // Event Handlers
        submitBtn.setOnAction(e -> {
            try {
                // Validate input
                if (firstNameField.getText().isEmpty() || lastNameField.getText().isEmpty() ||
                    ageField.getText().isEmpty() || genderField.getText().isEmpty() ||
                    phoneField.getText().isEmpty() || usernameField.getText().isEmpty() ||
                    passwordField.getText().isEmpty()) {
                    actiontarget.setText("Please fill in all required fields!");
                    return;
                }

                // Create new patient
                String id = "P" + UUID.randomUUID().toString().substring(0, 8);
                String firstName = firstNameField.getText();
                String lastName = lastNameField.getText();
                int age = Integer.parseInt(ageField.getText());
                char gender = genderField.getText().toUpperCase().charAt(0);
                String phoneNumber = phoneField.getText();
                String username = usernameField.getText();
                String password = passwordField.getText();
                boolean hasInsurance = hasInsuranceCheck.isSelected();
                String insuranceProvider = hasInsurance ? insuranceProviderField.getText() : "";
                String insurancePolicyNumber = hasInsurance ? insurancePolicyField.getText() : "";

                // Create patient object
                Patient newPatient = new Patient(id, firstName, lastName, age, gender,
                    phoneNumber, username, password, hasInsurance, insuranceProvider, insurancePolicyNumber);

                // Add to users list
                users.add(newPatient);

                // Show success message and return to login
                actiontarget.setText("Registration successful! Returning to login...");
                LoginScreen loginScreen = new LoginScreen(primaryStage, users);
                loginScreen.show();

            } catch (NumberFormatException ex) {
                actiontarget.setText("Please enter valid numbers for age, height, and weight!");
            } catch (Exception ex) {
                actiontarget.setText("An error occurred during registration!");
            }
        });

        backBtn.setOnAction(e -> {
            LoginScreen loginScreen = new LoginScreen(primaryStage, users);
            loginScreen.show();
        });
    }
} 