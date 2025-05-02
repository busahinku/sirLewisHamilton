package hospital.objects;

import java.time.LocalDateTime;

public class Prescription {
    private String medication;
    private String dosageType;
    private String usage;
    private Patient patient;
    private Doctor doctor;
    private LocalDateTime issueDate;
    private String notes;

    public Prescription(String medication, String dosageType, String usage,
                       Patient patient, Doctor doctor, String notes) {
        this.medication = medication;
        this.dosageType = dosageType;
        this.usage = usage;
        this.patient = patient;
        this.doctor = doctor;
        this.issueDate = LocalDateTime.now();
        this.notes = notes;
    }

    // Getters
    public String getMedication() {
        return medication;
    }

    public String getDosageType() {
        return dosageType;
    }

    public String getUsage() {
        return usage;
    }

    public Patient getPatient() {
        return patient;
    }

    public Doctor getDoctor() {
        return doctor;
    }

    public LocalDateTime getIssueDate() {
        return issueDate;
    }

    public String getNotes() {
        return notes;
    }

    // Methods
    public void cancelPrescription() {
        System.out.println("Prescription cancelled for patient: " + patient.getFullName());
    }

    public void renewPrescription(String newNotes) {
        this.issueDate = LocalDateTime.now();
        this.notes = newNotes;
        System.out.println("Prescription renewed for patient: " + patient.getFullName());
    }

    public void addMedication(String newMedication) {
        this.medication += ", " + newMedication;
    }

    @Override
    public String toString() {
        return String.format("Prescription [Medication: %s, Type: %s, Usage: %s, Patient: %s, Doctor: %s, Date: %s]",
                medication, dosageType, usage, patient.getFullName(), doctor.getFullName(), issueDate);
    }
}
