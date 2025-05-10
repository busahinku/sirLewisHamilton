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
    private String status;


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


    // Constructors:
    public Appointment(String appointmentId, Patient patient, Doctor doctor,
                      LocalDateTime dateTime) {
        this.appointmentId = appointmentId;
        this.patient = patient;
        this.doctor = doctor;
        this.dateTime = dateTime;
        this.durationMinutes = 30;
        this.isCompleted = false;
        this.status = "Scheduled";
        this.notes = "";
        
        // Calculate cost and hospital revenue, if doctor is private appointment fee directly goes to hospital if it is not 10% is the comission of the hospital.
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


    public boolean isCompleted() {
        return isCompleted;
    }


    public String getNotes() {
        return notes;
    }


    public void setCompleted(boolean completed) {
        this.isCompleted = completed;

        if (completed) {
            this.status = "Completed";
        } else {
            this.status = "Scheduled";
        }

        if (completed) {
            System.out.println("Appointment " + appointmentId + "[" + getPatient() + "->" + getDoctor() + "]" + " completed");
        }
    }


    public void addNote(String note) {
        this.notes += "\n" + LocalDateTime.now() + "| " + note;
    }

    public String GeneralInfo() {
        return "Appointment Information [ID: " + appointmentId + ", Patient: " + patient.getFullName() + ", Doctor: " + doctor.getFullName() +
                ", Date: " + dateTime + ", Duration: " + durationMinutes + ", Cost: " + cost + ", Status: " + status + "]";
    }
}
