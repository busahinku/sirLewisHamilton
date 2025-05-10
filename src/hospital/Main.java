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
        // Uncomment the next line to demo the PharmacistDashboard with sample data:
        // demoPharmacistDashboard(primaryStage);
        initializeHospitalData();
        LoginScreen loginScreen = new LoginScreen(primaryStage, users);
        loginScreen.show();
    }

    private void initializeHospitalData() {
        // Create hospital founder
        founder = new Founder("F001", "Aysen", "Akkaya", 40, 'F', "5319870221",
            "founder", "founder", 150000.0);

        // Create departments
        cardiology = new Department("Cardiology", null, "Block A");
        neurology = new Department("Neurology", null, "Block B");

        // Create doctors
        cardiologist = new Doctor("D001", "Aysegul", "Eren", 30, 'F', "5245287101",
            "doctor", "doctor", "Cardiology", "Cardiologist", "301", (short)15, false, 120000.0);
        cardiologist.setStaticSchedule(new StaticSchedule());
        neurologist = new Doctor("D002", "Ates", "Hekimoglu", 40, 'M', "512389722",
            "ates", "ates", "Neurology", "Neurologist", "302", (short)12, false, 110000.0);
        neurologist.setStaticSchedule(new StaticSchedule());

        // Create private doctors
        PrivateDoctors privateDoctor = new PrivateDoctors("PD001", "Ilker", "Ayrik", 50, 'M', "5249162514",
            "private", "private", "Ilker Cardiology Clinic", "123 Main St", "Cardiology", 2.5, (short)20);

        // Create assistants
        Assistant assistant = new Assistant("A001", "Pelin", "Erkaya", 25, 'F', "5137162758",
            "assistant", "assistant", cardiologist, (short)3, "Patient Coordination",
            cardiology, 55000.0, "Monday-Friday 8AM-4PM");

        // Create patients
        Patient patient = new Patient("P001", "Burakcim", "Sahin", 35, 'M', "5319878790",
            "patient", "patient", true, "Blue Cross", "BC123456");

        // Create pharmacist
        Pharmacist pharmacist = new Pharmacist("PH001", "Kevin De", "Bruyne", 35, 'M', "52418176236",
            "pharmacist", "pharmacist", "Main Pharmacy", 80000.0, "Monday-Friday 9AM-5PM");

        // Add sample inventory items for all pharmacists
        hospital.ui.PharmacistDashboard.inventoryList.clear();
        Inventory inv1 = new Inventory("I001", "Paracetamol", "Medication", 100, 20, 2.5, "MedSupplier", "Main Pharmacy");
        Inventory inv2 = new Inventory("I002", "Bandage", "Medical Supplies", 50, 10, 1.0, "MedSupplier", "Main Pharmacy");
        hospital.ui.PharmacistDashboard.inventoryList.add(inv1);
        hospital.ui.PharmacistDashboard.inventoryList.add(inv2);

        // Add a sample prescription to the real pharmacist
        Prescription p1 = new Prescription("P001", "Paracetamol", "Take 1 tablet every 6 hours", patient, cardiologist, "Delivered");
        pharmacist.addPrescription(p1);

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