package hospital;

import hospital.objects.*;
import java.time.LocalDateTime;

public class Main {
    public static void main(String[] args) {
        // Create founder with salary
        Founder founder = new Founder(
            "F001", "Michael", "Johnson", 55, 'M',
            "555-0001", "founder", "admin123", 15000.0
        );

        // Create departments
        Doctor deptHead = new Doctor(
            "D001", "John", "Smith", 45, 'M',
            "555-0101", "jsmith", "doc123",
            "Cardiology", "Heart Specialist",
            "Dr.", "301", (short)15, false, 12000.0
        );
        
        founder.createDepartment("Cardiology", deptHead, "A1");

        // Create private doctor
        Doctor privateDoctor = new Doctor(
            "D002", "Jane", "Doe", 35, 'F',
            "555-0102", "jdoe", "doc456",
            "General Medicine", "General Practitioner",
            "Dr.", "202", (short)8, true, 0.0
        );
        privateDoctor.setAppointmentFee(10.0); // $10 per minute
        privateDoctor.setPrivatePracticeLocation("123 Private Practice St");
        founder.hireDoctor(privateDoctor, null);

        // Create hospital doctor
        Doctor hospitalDoctor = new Doctor(
            "D003", "Robert", "Wilson", 40, 'M',
            "555-0103", "rwilson", "doc789",
            "Cardiology", "Cardiac Surgeon",
            "Dr.", "302", (short)12, false, 15000.0
        );
        founder.hireDoctor(hospitalDoctor, founder.getDepartments().get(0));

        // Create an assistant with salary
        Assistant assistant = new Assistant(
            "A001", "Mary", "Wilson", 28, 'F',
            "555-0104", "mwilson", "assist123",
            hospitalDoctor, (short)3, "Patient Care",
            founder.getDepartments().get(0), 45000.0, "9AM-5PM"
        );

        // Create a pharmacist with salary
        Pharmacist pharmacist = new Pharmacist(
            "P001", "Robert", "Brown", 40, 'M',
            "555-0105", "rbrown", "pharm123",
            "Main Pharmacy", 60000.0, "9AM-5PM"
        );
        pharmacist.addMedication("Aspirin");
        pharmacist.addMedication("Amoxicillin");

        // Create a patient
        Patient patient = new Patient(
            "PT001", "Alice", "Johnson", 28, 'F',
            "555-0201", "ajohnson", "patient123",
            true, "No major issues", 1000.0
        );
        patient.addAllergy("Penicillin");

        // Create appointments
        Appointment privateDoctorAppointment = new Appointment(
            "A001", patient, privateDoctor,
            LocalDateTime.now().plusDays(1), 30
        );
        patient.addAppointment(privateDoctorAppointment);
        founder.addRevenue(privateDoctorAppointment.getHospitalRevenue());

        Appointment hospitalDoctorAppointment = new Appointment(
            "A002", patient, hospitalDoctor,
            LocalDateTime.now().plusDays(2), 30
        );
        patient.addAppointment(hospitalDoctorAppointment);
        founder.addRevenue(hospitalDoctorAppointment.getHospitalRevenue());

        // Create a prescription
        Prescription prescription = new Prescription(
            "Aspirin", "Tablet", "1 tablet twice daily",
            patient, hospitalDoctor, "Take after meals"
        );

        // Create a treatment
        Treatment treatment = new Treatment(
            "T001", patient, hospitalDoctor,
            "Do 30 minutes of cardio exercise daily",
            "Exercise", "Daily", "1 month"
        );

        // Demonstrate assistant functionality
        System.out.println("\n=== Assistant Actions ===");
        assistant.manageAppointment(privateDoctorAppointment);
        assistant.updateSchedule(hospitalDoctor, "Surgery at 2PM");

        // Demonstrate pharmacist functionality
        System.out.println("\n=== Pharmacy Operations ===");
        pharmacist.checkStock("Aspirin");
        pharmacist.dispenseMedication(prescription);

        // Demonstrate treatment functionality
        System.out.println("\n=== Treatment Details ===");
        System.out.println(treatment);
        treatment.updateAdvice("Do 45 minutes of cardio exercise daily");
        System.out.println("Updated treatment: " + treatment);

        // Generate hospital report
        System.out.println("\n=== Hospital Report ===");
        founder.generateHospitalReport();

        // Show patient's appointments
        System.out.println("\n=== Patient's Appointments ===");
        for (Appointment appointment : patient.getAppointments()) {
            System.out.println(appointment);
        }

        // Demonstrate appointment cancellation
        System.out.println("\n=== Cancelling Appointment ===");
        patient.cancelAppointment(privateDoctorAppointment);
        System.out.println("Appointment cancelled. Updated hospital revenue: $" + founder.getTotalRevenue());

        // Show salaries
        System.out.println("\n=== Salaries ===");
        System.out.printf("Founder Salary: $%.2f/month%n", founder.getSalary());
        System.out.printf("Hospital Doctor Salary: $%.2f/month%n", hospitalDoctor.getSalary());
        System.out.printf("Private Doctor Earnings: $%.2f (from appointments)%n", 
            privateDoctorAppointment.getCost() * 0.90); // 90% of appointment cost
        System.out.printf("Assistant Salary: $%.2f/year%n", assistant.getSalary());
        System.out.printf("Pharmacist Salary: $%.2f/year%n", pharmacist.getSalary());
    }
}