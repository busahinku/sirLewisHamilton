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
    private boolean isCompleted;

    public Appointment(String appointmentId, Patient patient, Doctor doctor,
                      LocalDateTime dateTime, int durationMinutes) {
        this.appointmentId = appointmentId;
        this.patient = patient;
        this.doctor = doctor;
        this.dateTime = dateTime;
        this.durationMinutes = durationMinutes;
        this.isCompleted = false;
        
        // Calculate cost and hospital revenue
        if (doctor.isPrivate()) {
            this.cost = doctor.getAppointmentFee() * durationMinutes;
            this.hospitalRevenue = this.cost * 0.10; // 10% goes to hospital
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

    // Setters
    public void setDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }

    public void setDurationMinutes(int durationMinutes) {
        this.durationMinutes = durationMinutes;
        // Recalculate cost and revenue if duration changes
        if (doctor.isPrivate()) {
            this.cost = doctor.getAppointmentFee() * durationMinutes;
            this.hospitalRevenue = this.cost * 0.10;
        } else {
            this.cost = doctor.calculateAppointmentCost(durationMinutes);
            this.hospitalRevenue = this.cost;
        }
    }

    public void setCompleted(boolean completed) {
        isCompleted = completed;
    }

    @Override
    public String toString() {
        return String.format("Appointment [ID: %s, Patient: %s, Doctor: %s, Date: %s, Duration: %d mins, Cost: $%.2f]",
                appointmentId, patient.getFullName(), doctor.getFullName(), dateTime, durationMinutes, cost);
    }
}
