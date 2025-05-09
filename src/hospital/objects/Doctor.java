package hospital.objects;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class Doctor extends Person {
    public static int id = 0000000;
    private String department;
    private String specialty;
    private String officeNumber;
    private short experience;
    private boolean isPrivate;
    private double salary; // Monthly salary for hospital doctors
    private double appointmentFee; // For private doctors
    private String privatePracticeLocation; // For private doctors only
    private List<Appointment> appointments;
    private List<Patient> patients;
    private StaticSchedule staticSchedule;
    private List<Review> reviews;

    public Doctor(String id, String firstName, String lastName, int age, char gender, 
                 String phoneNumber, String username, String password,
                 String department, String specialty, String officeNumber, 
                 short experience, boolean isPrivate, double salary) {
        super(id, firstName, lastName, age, gender, phoneNumber, username, password);
        this.department = department;
        this.specialty = specialty;
        this.officeNumber = officeNumber;
        this.experience = experience;
        this.isPrivate = isPrivate;
        this.salary = salary;
        this.appointmentFee = 0.0;
        this.privatePracticeLocation = null;
        this.appointments = new ArrayList<>();
        this.patients = new ArrayList<>();
        this.reviews = new ArrayList<>();
        this.staticSchedule = null;
    }

    // Getters
    public String getDepartment() {
        return department;
    }

    public String getSpecialty() {
        return specialty;
    }

    public String getOfficeNumber() {
        return officeNumber;
    }

    public short getExperience() {
        return experience;
    }

    public boolean isPrivate() {
        return isPrivate;
    }

    public double getSalary() {
        return salary;
    }

    public double getAppointmentFee() {
        return appointmentFee;
    }

    public String getPrivatePracticeLocation() {
        return privatePracticeLocation;
    }

    public List<Appointment> getAppointments() {
        return new ArrayList<>(appointments);
    }

    public List<Patient> getPatients() {
        return new ArrayList<>(patients);
    }

    public StaticSchedule getStaticSchedule() {
        return staticSchedule;
    }

    public List<Review> getReviews() {
        return new ArrayList<>(reviews);
    }

    // Setters
    public void setDepartment(String department) {
        this.department = department;
    }

    public void setSpecialty(String specialty) {
        this.specialty = specialty;
    }

    public void setOfficeNumber(String officeNumber) {
        this.officeNumber = officeNumber;
    }

    public void setSalary(double salary) {
        this.salary = salary;
    }

    public void setAppointmentFee(double appointmentFee) {
        if (isPrivate) {
            this.appointmentFee = appointmentFee;
        }
    }

    public void setPrivatePracticeLocation(String location) {
        if (isPrivate) {
            this.privatePracticeLocation = location;
        }
    }

    public void setStaticSchedule(StaticSchedule schedule) {
        this.staticSchedule = schedule;
    }

    // Methods


    public void cancelAppointment(Appointment appointment) {
        if (appointments.contains(appointment)) {
            appointments.remove(appointment);
            // Update the status of the appointment
            appointment.setStatus("Cancelled");
            // Add a note about the cancellation
            appointment.addNote("Appointment cancelled by patient");
            System.out.println("Appointment cancelled successfully.");
        } else {
            System.out.println("Appointment not found in doctor's schedule.");
        }
    }

    public void addReview(Review review) {
        if (!reviews.contains(review)) {
            reviews.add(review);
        }
    }

    public double calculateAverageRating() {
        if (reviews.isEmpty()) return 0.0;
        double sum = 0.0;
        for (Review review : reviews) {
            sum += review.getRating();
        }
        return sum / reviews.size();
    }

    public double calculateAppointmentCost(int durationMinutes) {
        if (isPrivate) {
            return appointmentFee * durationMinutes;
        } else {
            // For hospital doctors, cost is based on their salary and experience
            return (salary / (30 * 8 * 60)) * durationMinutes; // Convert monthly salary to per-minute rate
        }
    }

    public String GeneralInfo() {
        String baseInfo = "Doctor [Name: " + getFullName() +
                ", Specialty: " + specialty +
                ", Experience: " + experience + " years]";

        if (isPrivate) {
            return baseInfo + ", Private Practice at: " + privatePracticeLocation +
                    ", Fee: $" + appointmentFee;
        } else {
            return baseInfo + ", Department: " + department +
                    ", Office: " + officeNumber;
        }
    }

    public boolean isAvailable(StaticSchedule.Day day, LocalTime time, int durationMinutes) {
        if (staticSchedule == null) {
            return false;
        }
        
        // Check if the time slot exists in our static schedule
        if (!staticSchedule.isTimeSlotAvailable(day, time)) {
            return false;
        }

        // Check for overlapping appointments
        for (Appointment appointment : appointments) {
            // Convert appointment time to StaticSchedule.Day for comparison
            StaticSchedule.Day appointmentDay = StaticSchedule.Day.values()[appointment.getDateTime().toLocalDate().getDayOfWeek().getValue() - 1];
            LocalTime appointmentTime = appointment.getDateTime().toLocalTime();
            
            if (appointmentDay == day && appointmentTime.equals(time)) {
                return false;
            }
        }
        return true;
    }


    public String getAvailableSlots(StaticSchedule.Day day) {
        if (staticSchedule == null) {
            return "No schedule available";
        }
        return staticSchedule.getAvailableTimeSlotsAsString(day, appointments);
    }

    public Appointment scheduleAppointment(Patient patient, StaticSchedule.Day day, LocalTime time, int durationMinutes) {
        // Availability of the time.
        if (!isAvailable(day, time, durationMinutes)) {
            System.out.println("Sorry, the selected time slot is not available.");
            System.out.println("Available time slots:");
            System.out.println(staticSchedule.getAvailableTimeSlotsAsString(day, appointments));
            return null;
        }

        id = id + 1;
        String textAppointmentIDC = String.valueOf(id);
        String appointmentId = "SIRL" + textAppointmentIDC;
        
        // Convert day and time to LocalDateTime for the appointment
        LocalDateTime dateTime = LocalDateTime.of(2025, 5, day.ordinal() + 19, time.getHour(), time.getMinute());
        Appointment appointment = new Appointment(appointmentId, patient, this, dateTime, durationMinutes);
        appointments.add(appointment);
        patient.addAppointment(appointment);
        return appointment;
    }
}
