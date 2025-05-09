package hospital.ui;

import hospital.objects.Patient;
import hospital.objects.Person;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import java.util.List;
import java.util.UUID;

public class PatientSignUpScreen {
    private Stage primaryStage;
    private List<Person> users;

    public PatientSignUpScreen(Stage primaryStage, List<Person> users) {
        this.primaryStage = primaryStage;
        this.users = users;
    }

    public void show() {
        primaryStage.setTitle("Patient Registration");

        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(25, 25, 25, 25));

        Text scenetitle = new Text("Patient Registration Form");
        scenetitle.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
        grid.add(scenetitle, 0, 0, 2, 1);

        // Personal Information
        Label firstNameLabel = new Label("First Name:");
        grid.add(firstNameLabel, 0, 1);
        TextField firstNameField = new TextField();
        grid.add(firstNameField, 1, 1);

        Label lastNameLabel = new Label("Last Name:");
        grid.add(lastNameLabel, 0, 2);
        TextField lastNameField = new TextField();
        grid.add(lastNameField, 1, 2);

        Label ageLabel = new Label("Age:");
        grid.add(ageLabel, 0, 3);
        TextField ageField = new TextField();
        grid.add(ageField, 1, 3);

        Label genderLabel = new Label("Gender (M/F):");
        grid.add(genderLabel, 0, 4);
        TextField genderField = new TextField();
        grid.add(genderField, 1, 4);

        Label phoneLabel = new Label("Phone Number:");
        grid.add(phoneLabel, 0, 5);
        TextField phoneField = new TextField();
        grid.add(phoneField, 1, 5);

        // Account Information
        Label usernameLabel = new Label("Username:");
        grid.add(usernameLabel, 0, 6);
        TextField usernameField = new TextField();
        grid.add(usernameField, 1, 6);

        Label passwordLabel = new Label("Password:");
        grid.add(passwordLabel, 0, 7);
        PasswordField passwordField = new PasswordField();
        grid.add(passwordField, 1, 7);

        // Insurance Information
        Label hasInsuranceLabel = new Label("Has Insurance:");
        grid.add(hasInsuranceLabel, 0, 8);
        CheckBox hasInsuranceCheck = new CheckBox();
        grid.add(hasInsuranceCheck, 1, 8);

        Label insuranceProviderLabel = new Label("Insurance Provider:");
        grid.add(insuranceProviderLabel, 0, 9);
        TextField insuranceProviderField = new TextField();
        grid.add(insuranceProviderField, 1, 9);

        Label insurancePolicyLabel = new Label("Insurance Policy Number:");
        grid.add(insurancePolicyLabel, 0, 10);
        TextField insurancePolicyField = new TextField();
        grid.add(insurancePolicyField, 1, 10);

        // Medical Record Information
        Label bloodTypeLabel = new Label("Blood Type:");
        grid.add(bloodTypeLabel, 0, 11);
        TextField bloodTypeField = new TextField();
        grid.add(bloodTypeField, 1, 11);

        Label heightLabel = new Label("Height (cm):");
        grid.add(heightLabel, 0, 12);
        TextField heightField = new TextField();
        grid.add(heightField, 1, 12);

        Label weightLabel = new Label("Weight (kg):");
        grid.add(weightLabel, 0, 13);
        TextField weightField = new TextField();
        grid.add(weightField, 1, 13);

        // Buttons
        Button submitBtn = new Button("Submit");
        Button backBtn = new Button("Back to Login");
        HBox hbBtn = new HBox(10);
        hbBtn.setAlignment(Pos.BOTTOM_RIGHT);
        hbBtn.getChildren().addAll(backBtn, submitBtn);
        grid.add(hbBtn, 1, 14);

        Text actiontarget = new Text();
        grid.add(actiontarget, 1, 15);

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

        Scene scene = new Scene(grid, 1280, 720);
        primaryStage.setScene(scene);
    }
} 