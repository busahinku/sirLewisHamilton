package hospital;

import hospital.objects.*;
import hospital.ui.LoginScreen;
import javafx.application.Application;
import javafx.stage.Stage;
import java.util.ArrayList;
import java.util.List;

public class Main extends Application {
    private Founder founder;
    private Department cardiology;
    private Department neurology;
    private Doctor cardiologist;
    private Doctor neurologist;
    private List<Person> users;

    @Override
    public void start(Stage primaryStage) {
        initializeHospitalData();
        LoginScreen loginScreen = new LoginScreen(primaryStage, users);
        loginScreen.show();
    }

    private void initializeHospitalData() {
        // Create hospital founder
        founder = new Founder("F001", "John", "Doe", 55, 'M', "555-0001", 
            "founder", "admin123", 150000.0);

        // Create departments
        cardiology = new Department("Cardiology", null, "Block A");
        neurology = new Department("Neurology", null, "Block B");

        // Create doctors
        cardiologist = new Doctor("D001", "Michael", "Smith", 45, 'M', "555-1001", 
            "msmith", "doc123", "Cardiology", "Cardiologist", "301", (short)15, false, 120000.0);
        neurologist = new Doctor("D002", "Sarah", "Johnson", 40, 'F', "555-1002", 
            "sjohnson", "doc456", "Neurology", "Neurologist", "302", (short)12, false, 110000.0);

        // Create private doctors
        PrivateDoctors privateDoctor = new PrivateDoctors("PD001", "James", "Wilson", 50, 'M', "555-3001", 
            "jwilson", "priv123", "Wilson Cardiology Clinic", "123 Main St", "Cardiology", 2.5, (short)20);

        // Create assistants
        Assistant assistant = new Assistant("A001", "Jessica", "Parker", 28, 'F', "555-5001", 
            "jparker", "asst123", cardiologist, (short)3, "Patient Coordination",
            cardiology, 55000.0, "Monday-Friday 8AM-4PM");

        // Create patients
        Patient patient = new Patient("P001", "Robert", "Brown", 35, 'M', "555-2001", 
            "rbrown", "pat123", true, "Blue Cross", "BC123456");

        // Create pharmacist
        Pharmacist pharmacist = new Pharmacist("PH001", "David", "Miller", 35, 'M', "555-4001", 
            "dmiller", "pharm123", "Main Pharmacy", 80000.0, "Monday-Friday 9AM-5PM");

        // Assign doctors to departments
        cardiology.setHead(cardiologist);
        neurology.setHead(neurologist);

        // Create departments through founder
        founder.createDepartment("Cardiology", cardiologist, "Block A");
        founder.createDepartment("Neurology", neurologist, "Block B");

        // Hire doctors through founder
        founder.hireDoctor(cardiologist, cardiology);
        founder.hireDoctor(neurologist, neurology);

        // Initialize users list for authentication
        users = new ArrayList<>();
        users.add(founder);
        users.add(cardiologist);
        users.add(neurologist);
        users.add(privateDoctor);
        users.add(assistant);
        users.add(patient);
        users.add(pharmacist);
    }

    public static void main(String[] args) {
        launch(args);
    }
}