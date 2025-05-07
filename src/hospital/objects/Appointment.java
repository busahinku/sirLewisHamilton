package hospital.objects;

import java.time.LocalDateTime;

public class Appointment {
    private String appointmentId;
    private Patient patient;
    private Doctor doctor;
    private LocalDateTime dateTime;
    private int durationMinutes;
    private double cost;
    private double hospitalRevenue;
    private double privateDoctorRevenue;
    private boolean isCompleted;
    private String status; // "Scheduled", "Completed", "Cancelled"

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void cancel() {
        setStatus("Cancelled");
        addNote("Appointment cancelled");
    }
    private String notes;

    public Appointment(String appointmentId, Patient patient, Doctor doctor,
                      LocalDateTime dateTime, int durationMinutes) {
        this.appointmentId = appointmentId;
        this.patient = patient;
        this.doctor = doctor;
        this.dateTime = dateTime;
        this.durationMinutes = durationMinutes;
        this.isCompleted = false;
        this.status = "Scheduled";
        this.notes = "";
        
        // Calculate cost and hospital revenue
        if (doctor.isPrivate()) {
            this.cost = doctor.getAppointmentFee() * durationMinutes;
            this.hospitalRevenue = this.cost * 0.10; // 10% goes to hospital
            this.privateDoctorRevenue = this.cost * 0.90;
        } else {
            this.cost = doctor.calculateAppointmentCost(durationMinutes);
            this.hospitalRevenue = this.cost; // Hospital doctors' fees go entirely to hospital
        }
    }

    // Getters
    public String getAppointmentId() {
        return appointmentId;
    }

    public Patient getPatient() {
        return patient;
    }

    public Doctor getDoctor() {
        return doctor;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public int getDurationMinutes() {
        return durationMinutes;
    }

    public double getCost() {
        return cost;
    }

    public double getHospitalRevenue() {
        return hospitalRevenue;
    }

    public boolean isCompleted() {
        return isCompleted;
    }


    public String getNotes() {
        return notes;
    }

    // Setters
    public void setDateTime(LocalDateTime dateTime) { // How this code works? GPT gave the code for arranging time.
        this.dateTime = dateTime;
    }

    public void setCompleted(boolean completed) {
        this.isCompleted = completed;

        if (completed) {
            this.status = "Completed";
        } else {
            this.status = "Scheduled";
        }

        if (completed) {
            System.out.println("Appointment " + appointmentId + "[" + getPatient() + "->" + getDoctor() + "]" + " marked as completed");
        }
    }

    public void cancelAppointment() {
        this.status = "Cancelled";
        System.out.println("Appointment " + appointmentId + " has been cancelled");
    }

    public void addNote(String note) {
        this.notes += "\n" + LocalDateTime.now() + "| " + note;
    }

    public String GeneralInfo() {
        return "Appointment Information [ID: " + appointmentId + ", Patient: " + patient.getFullName() + ", Doctor: " + doctor.getFullName() +
                ", Date: " + dateTime + ", Duration: " + durationMinutes + ", Cost: " + cost + ", Status: " + status + "]";
    }
}
