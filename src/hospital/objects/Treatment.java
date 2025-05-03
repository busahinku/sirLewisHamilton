package hospital.objects;

import java.time.LocalDateTime;

public class Treatment {
    private String treatmentId;
    private Patient patient;
    private Doctor doctor;
    private String advice;
    private LocalDateTime issueDate;
    private String category; // e.g., "Exercise", "Diet", "Lifestyle"
    private String frequency; // e.g., "Daily", "Weekly", "As needed"
    private String duration; // e.g., "2 weeks", "1 month", "Ongoing"

    public Treatment(String treatmentId, Patient patient, Doctor doctor,
                    String advice, String category, String frequency, String duration) {
        this.treatmentId = treatmentId;
        this.patient = patient;
        this.doctor = doctor;
        this.advice = advice;
        this.category = category;
        this.frequency = frequency;
        this.duration = duration;
        this.issueDate = LocalDateTime.now();
    }

    // Getters
    public String getTreatmentId() {
        return treatmentId;
    }

    public Patient getPatient() {
        return patient;
    }

    public Doctor getDoctor() {
        return doctor;
    }

    public String getAdvice() {
        return advice;
    }

    public LocalDateTime getIssueDate() {
        return issueDate;
    }

    public String getCategory() {
        return category;
    }

    public String getFrequency() {
        return frequency;
    }

    public String getDuration() {
        return duration;
    }

    // Methods
    public void updateAdvice(String newAdvice) {
        this.advice = newAdvice;
        System.out.println("Treatment advice updated for patient: " + patient.getFullName());
    }

    public void updateFrequency(String newFrequency) {
        this.frequency = newFrequency;
        System.out.println("Treatment frequency updated for patient: " + patient.getFullName());
    }

    public void updateDuration(String newDuration) {
        this.duration = newDuration;
        System.out.println("Treatment duration updated for patient: " + patient.getFullName());
    }

    public String GeneralInfo() {
        return String.format("Treatment [ID: %s, Patient: %s, Doctor: %s, Category: %s, Advice: %s, Frequency: %s, Duration: %s, Date: %s]",
                treatmentId, patient.getFullName(), doctor.getFullName(), category, advice, frequency, duration, issueDate);
    }
}
