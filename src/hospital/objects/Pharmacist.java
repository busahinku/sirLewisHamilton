package hospital.objects;

import java.util.ArrayList;
import java.util.List;

public class Pharmacist extends Person {
    private String location;
    private List<String> medications;
    private double salary;
    private String workSchedule;
    private List<Prescription> prescriptions;

    public Pharmacist(String id, String firstName, String lastName, int age, char gender,
                     String phoneNumber, String username, String password,
                     String location, double salary, String workSchedule) {
        super(id, firstName, lastName, age, gender, phoneNumber, username, password);
        this.location = location;
        this.medications = new ArrayList<>();
        this.salary = salary;
        this.workSchedule = workSchedule;
        this.prescriptions = new ArrayList<>();
    }

    // Getters
    public String getLocation() {
        return location;
    }

    public List<String> getMedications() {
        return new ArrayList<>(medications);
    }

    public double getSalary() {
        return salary;
    }

    public String getWorkSchedule() {
        return workSchedule;
    }

    public List<Prescription> getPrescriptions() {
        return new ArrayList<>(prescriptions);
    }

    // Methods
    public void checkStock(String medication) {
        boolean inStock = medications.contains(medication);
        System.out.println("Medication " + medication + " is " + "in stock");
    }

    public void dispenseMedication(Prescription prescription) {
        if (medications.contains(prescription.getMedication())) {
            prescriptions.add(prescription);
            System.out.println("Dispensing medication for prescription: " + prescription.GeneralInfo());
            // Implementation would include checking stock, updating inventory, etc.
        } else {
            System.out.println("Cannot dispense: Medication " + prescription.getMedication() + " is out of stock");
        }
    }

    public void addPrescription(Prescription prescription) {
        prescriptions.add(prescription);
    }

    public void removePrescription(Prescription prescription) {
        prescriptions.remove(prescription);
    }

    public String GeneralInfo() {
        return "Pharmacist [Name: " + getFullName() +
                ", Location: " + location +
                ", Schedule: " + workSchedule + "]";
    }
}
