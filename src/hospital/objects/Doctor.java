package hospital.objects;

import java.time.LocalDateTime;
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
    private Schedule schedule;
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
        this.schedule = null;
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

    public Schedule getSchedule() {
        return schedule;
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

    public void setSchedule(Schedule schedule) {
        this.schedule = schedule;
    }

    // Methods
    public void addAppointment(Appointment appointment) {
        if (!appointments.contains(appointment)) {
            appointments.add(appointment);
            if (!patients.contains(appointment.getPatient())) {
                patients.add(appointment.getPatient());
            }
        }
    }

    public void cancelAppointment(Appointment appointment) {
        if (appointments.contains(appointment)) {
            appointment.cancelAppointment();
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

    public void prescribeMedication(Patient patient, String medication, String dosage, String instructions) {
        Prescription prescription = new Prescription(medication, dosage, instructions, patient, this, "No additional notes");
        patient.addPrescription(prescription);
    }

    public void updatePatientMedicalRecord(Patient patient, String diagnosis, String procedure, String labResult) {
        if (patient.getMedicalRecord() != null) {
            patient.updateMedicalRecord(diagnosis, procedure, labResult);
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

    public boolean isAvailable(LocalDateTime dateTime, int durationMinutes) {
        if (schedule != null) {
            // Check if the time is within doctor's working hours
            for (Schedule.Shift shift : schedule.getShifts()) {
                if (dateTime.toLocalDate().equals(shift.getStartTime().toLocalDate())) {
                    if (dateTime.isBefore(shift.getStartTime()) || 
                        dateTime.plusMinutes(durationMinutes).isAfter(shift.getEndTime())) {
                        return false;
                    }
                }
            }
        }

        // Check for overlapping appointments
        for (Appointment appointment : appointments) {
            if (appointment.getStatus().equals("Cancelled")) continue;
            
            LocalDateTime appointmentEnd = appointment.getDateTime().plusMinutes(appointment.getDurationMinutes());
            LocalDateTime requestedEnd = dateTime.plusMinutes(durationMinutes);
            
            if (!(requestedEnd.isBefore(appointment.getDateTime()) || 
                  dateTime.isAfter(appointmentEnd))) {
                return false;
            }
        }
        return true;
    }



    // Scheduling Part We Are Taking HELPP
    public List<LocalDateTime> getAvailableSlots(LocalDateTime startDate, LocalDateTime endDate, int durationMinutes) {
        List<LocalDateTime> availableSlots = new ArrayList<>();
        LocalDateTime current = startDate;

        // Only check working hours (9 AM to 5 PM)
        while (current.isBefore(endDate)) {
            // Skip weekends
            if (current.getDayOfWeek().getValue() >= 6) {
                current = current.plusDays(1).withHour(9).withMinute(0);
                continue;
            }

            // Only check between 9 AM and 5 PM
            if (current.getHour() >= 9 && current.getHour() < 17) {
                if (isAvailable(current, durationMinutes)) {
                    availableSlots.add(current);
                }
            }

            // Move to next 30-minute slot
            current = current.plusMinutes(30);

            // If we've passed 5 PM, move to next day at 9 AM
            if (current.getHour() >= 17) {
                current = current.plusDays(1).withHour(9).withMinute(0);
            }
        }
        return availableSlots;
    }

    public Appointment scheduleAppointment(Patient patient, LocalDateTime dateTime, int durationMinutes) {
        // Check if the requested time is in the future
        if (dateTime.isBefore(LocalDateTime.now())) {
            System.out.println("Cannot schedule appointment in the past.");
            return null;
        }

        // Work Hours Check
        if (dateTime.getHour() < 9 || dateTime.getHour() >= 17) {
            System.out.println("Appointments can only be scheduled between 9 AM and 5 PM.");
            return null;
        }

        // Check if the requested time is on a weekend
        if (dateTime.getDayOfWeek().getValue() >= 6) {
            System.out.println("Appointments cannot be scheduled on weekends.");
            return null;
        }

        if (!isAvailable(dateTime, durationMinutes)) {
            System.out.println("Sorry, the selected time slot is not available.");
            System.out.println("Here are the available slots for the next week:");
            List<LocalDateTime> availableSlots = getAvailableSlots(
                LocalDateTime.now().plusDays(1).withHour(9).withMinute(0),
                LocalDateTime.now().plusWeeks(1),
                durationMinutes
            );
            for (LocalDateTime slot : availableSlots) {
                System.out.println(slot);
            }
            return null;
        }


        id = id + 1;
        String textAppointmentIDC = String.valueOf(id);
        String appointmentId = "A" + textAppointmentIDC;
        Appointment appointment = new Appointment(appointmentId, patient, this, dateTime, durationMinutes);
        appointments.add(appointment);
        patient.addAppointment(appointment);
        return appointment;
    }

    public void rescheduleAppointment(Appointment appointment, LocalDateTime newDateTime) {
        if (appointments.contains(appointment) && isAvailable(newDateTime, appointment.getDurationMinutes())) {
            appointment.setDateTime(newDateTime);
            appointment.addNote("Appointment rescheduled to " + newDateTime);
        } else {
            System.out.println("Cannot reschedule appointment. The new time slot is not available.");
        }
    }

    public List<Appointment> getUpcomingAppointments() {
        List<Appointment> upcoming = new ArrayList<>();
        LocalDateTime now = LocalDateTime.now();
        
        for (Appointment appointment : appointments) {
            if (appointment.getDateTime().isAfter(now) && 
                !appointment.getStatus().equals("Cancelled")) {
                upcoming.add(appointment);
            }
        }
        return upcoming;
    }

    public List<Appointment> getAppointmentsForDate(LocalDateTime date) {
        List<Appointment> dayAppointments = new ArrayList<>();
        
        for (Appointment appointment : appointments) {
            if (appointment.getDateTime().toLocalDate().equals(date.toLocalDate()) && 
                !appointment.getStatus().equals("Cancelled")) {
                dayAppointments.add(appointment);
            }
        }
        return dayAppointments;
    }
}
