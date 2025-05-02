package hospital.objects;

import java.util.ArrayList;
import java.util.List;

public class Patient extends Person {
    private boolean hasInsurance;
    private String medicalHistory;
    private double balance;
    private List<String> allergies;
    private List<Appointment> appointments;
    private List<Prescription> prescriptions;

    public Patient(String id, String firstName, String lastName, int age, char gender,
                  String phoneNumber, String username, String password,
                  boolean hasInsurance, String medicalHistory, double balance) {
        super(id, firstName, lastName, age, gender, phoneNumber, username, password);
        this.hasInsurance = hasInsurance;
        this.medicalHistory = medicalHistory;
        this.balance = balance;
        this.allergies = new ArrayList<>();
        this.appointments = new ArrayList<>();
        this.prescriptions = new ArrayList<>();
    }

    // Getters
    public boolean hasInsurance() {
        return hasInsurance;
    }

    public String getMedicalHistory() {
        return medicalHistory;
    }

    public double getBalance() {
        return balance;
    }

    public List<String> getAllergies() {
        return new ArrayList<>(allergies);
    }

    public List<Appointment> getAppointments() {
        return new ArrayList<>(appointments);
    }

    public List<Prescription> getPrescriptions() {
        return new ArrayList<>(prescriptions);
    }

    // Setters
    public void setHasInsurance(boolean hasInsurance) {
        this.hasInsurance = hasInsurance;
    }

    public void setMedicalHistory(String medicalHistory) {
        this.medicalHistory = medicalHistory;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    // Methods
    public void addAllergy(String allergy) {
        if (!allergies.contains(allergy)) {
            allergies.add(allergy);
        }
    }

    public void removeAllergy(String allergy) {
        allergies.remove(allergy);
    }

    public void addAppointment(Appointment appointment) {
        if (!appointments.contains(appointment)) {
            appointments.add(appointment);
            // Deduct appointment cost from balance
            this.balance -= appointment.getCost();
        }
    }

    public void cancelAppointment(Appointment appointment) {
        if (appointments.remove(appointment)) {
            // Refund the appointment cost
            this.balance += appointment.getCost();
        }
    }

    public void addPrescription(Prescription prescription) {
        if (!prescriptions.contains(prescription)) {
            prescriptions.add(prescription);
        }
    }

    public void removePrescription(Prescription prescription) {
        prescriptions.remove(prescription);
    }

    public void payBill(double amount) {
        if (amount > 0) {
            this.balance += amount;
        }
    }

    @Override
    public String toString() {
        return String.format("Patient [Name: %s, Age: %d, Insurance: %s, Balance: $%.2f, Allergies: %d, Appointments: %d]",
                getFullName(), getAge(), hasInsurance ? "Yes" : "No", balance, allergies.size(), appointments.size());
    }
}
