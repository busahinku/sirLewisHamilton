package hospital;

import hospital.objects.*;
import java.time.LocalDateTime;
import java.time.LocalTime;


public class Main {
    public static void main(String[] args) {
        // Create hospital founder
        Founder founder = new Founder("F001", "John", "Doe", 55, 'M', "555-0001", 
            "founder", "admin123", 150000.0);

        // Create departments
        Department cardiology = new Department("Cardiology", null, "Block A");
        Department neurology = new Department("Neurology", null, "Block B");

        // Create doctors
        Doctor cardiologist = new Doctor("D001", "Michael", "Smith", 45, 'M', "555-1001", 
            "msmith", "doc123", "Cardiology", "Cardiologist", "301", (short)15, false, 120000.0);
        Doctor neurologist = new Doctor("D002", "Sarah", "Johnson", 40, 'F', "555-1002", 
            "sjohnson", "doc456", "Neurology", "Neurologist", "302", (short)12, false, 110000.0);

        // Assign doctors to departments
        cardiology.setHead(cardiologist);
        neurology.setHead(neurologist);

        // Create departments through founder
        founder.createDepartment("Cardiology", cardiologist, "Block A");
        founder.createDepartment("Neurology", neurologist, "Block B");

        // Hire doctors through founder
        founder.hireDoctor(cardiologist, cardiology);
        founder.hireDoctor(neurologist, neurology);

        // Print department information
        System.out.println("\n=== Department Information ===");
        System.out.println(cardiology.GeneralInfo());
        System.out.println(neurology.GeneralInfo());

        // Print doctors in each department
        System.out.println("\n=== Doctors in Cardiology ===");
        for (Doctor doctor : cardiology.getDoctors()) {
            System.out.println(doctor.GeneralInfo());
        }

        System.out.println("\n=== Doctors in Neurology ===");
        for (Doctor doctor : neurology.getDoctors()) {
            System.out.println(doctor.GeneralInfo());
        }

        // Create patients
        Patient patient1 = new Patient("P001", "Robert", "Brown", 35, 'M', "555-2001", 
            "rbrown", "pat123", true, "Blue Cross", "BC123456");
        Patient patient2 = new Patient("P002", "Emily", "Davis", 28, 'F', "555-2002", 
            "edavis", "pat456", true, "Aetna", "AE789012");

        // Create rooms
        Room room101 = new Room("101", "Standard", 2, 100.0, "Basic monitoring equipment");
        Room room102 = new Room("102", "ICU", 1, 250.0, "Advanced life support equipment");

        // Create appointments
        LocalDateTime now = LocalDateTime.now();
        Appointment appointment1 = new Appointment("A001", patient1, cardiologist, 
            now.plusHours(1), 30);
        Appointment appointment2 = new Appointment("A002", patient2, neurologist, 
            now.plusHours(2), 45);

        // Create medical records
        MedicalRecord record1 = new MedicalRecord("MR001", patient1, "A+", 180.0, 80.0);
        record1.addDiagnosis("Hypertension");
        record1.addProcedure("Blood pressure monitoring");
        record1.addMedication("Lisinopril");

        MedicalRecord record2 = new MedicalRecord("MR002", patient2, "B+", 165.0, 60.0);
        record2.addDiagnosis("Migraine");
        record2.addProcedure("Neurological examination");
        record2.addMedication("Sumatriptan");

        // Create prescriptions
        Prescription prescription1 = new Prescription("Lisinopril", "10mg daily", 
            "Take with food", patient1, cardiologist, "For blood pressure");
        Prescription prescription2 = new Prescription("Sumatriptan", "50mg as needed", 
            "Take at onset of migraine", patient2, neurologist, "For migraine relief");

        // Create bills
        Bill bill1 = new Bill("B001", patient1, now.plusDays(30));
        bill1.addItem("Consultation", 200.0);
        bill1.addItem("Blood test", 150.0);

        Bill bill2 = new Bill("B002", patient2, now.plusDays(30));
        bill2.addItem("Neurological examination", 300.0);
        bill2.addItem("MRI scan", 500.0);

        // Create reviews
        Review review1 = new Review(patient1, cardiologist, 
            "Excellent doctor, very thorough", 5);
        Review review2 = new Review(patient2, neurologist, 
            "Very knowledgeable and caring", 4);

        // Test patient check-in and check-out
        System.out.println("\n=== Testing Room Management ===");
        patient1.checkIn(room101);
        patient2.checkIn(room102);
        System.out.println(room101.GeneralInfo());
        System.out.println(room102.GeneralInfo());

        // Test bill payments
        System.out.println("\n=== Testing Billing System ===");
        patient1.payBill(bill1, 350.0, "Credit Card");
        patient2.payBill(bill2, 400.0, "Insurance");
        System.out.println(bill1.GeneralInfo());
        System.out.println(bill2.GeneralInfo());

        // Test appointment completion
        System.out.println("\n=== Testing Appointments ===");
        appointment1.setCompleted(true);
        appointment2.setCompleted(true);
        System.out.println(appointment1.GeneralInfo());
        System.out.println(appointment2.GeneralInfo());

        // Test medical records
        System.out.println("\n=== Testing Medical Records ===");
        System.out.println(record1.GeneralInfo());
        System.out.println(record2.GeneralInfo());

        // Test prescriptions
        System.out.println("\n=== Testing Prescriptions ===");
        System.out.println(prescription1.GeneralInfo());
        System.out.println(prescription2.GeneralInfo());

        // Test reviews
        System.out.println("\n=== Testing Reviews ===");
        System.out.println(review1.GeneralInfo());
        System.out.println(review2.GeneralInfo());

        // Test hospital revenue and reporting
        System.out.println("\n=== Testing Hospital Financial System ===");
        
        // Generate startup report
        founder.generateStartupReport();
        System.out.println("\nStartup Report:");
        System.out.println(founder.getReports().get(0).getReportSummary());

        // Add revenue from appointments
        founder.addRevenue(appointment1.getCost(), "Appointment", "Cardiology consultation for " + patient1.getFullName());
        founder.addRevenue(appointment2.getCost(), "Appointment", "Neurology consultation for " + patient2.getFullName());

        // Add revenue from bill payments
        founder.addRevenue(bill1.getTotalAmount(), "Bill Payment", "Payment from " + patient1.getFullName());
        founder.addRevenue(bill2.getTotalAmount(), "Bill Payment", "Payment from " + patient2.getFullName());

        // Generate monthly report
        founder.generateMonthlyReport();
        System.out.println("\nMonthly Report:");
        System.out.println(founder.getReports().get(1).getReportSummary());

        // Print financial summary
        System.out.println("\nCurrent Financial Status:");
        System.out.println(founder.getFinancialSummary());

        // Print detailed transactions
        System.out.println("\nRecent Transactions:");
        for (FinancialTransaction transaction : founder.getTransactions()) {
            System.out.printf("%s: $%.2f - %s (%s)%n",
                transaction.getType(),
                Math.abs(transaction.getAmount()),
                transaction.getDescription(),
                transaction.getCategory());
        }

        // Test appointment scheduling
        System.out.println("\n=== Testing Appointment Scheduling ===");
        
        // Create and set StaticSchedule for the doctor
        StaticSchedule staticSchedule = new StaticSchedule();
        cardiologist.setStaticSchedule(staticSchedule);
        
        // Get available slots for Monday
        System.out.println("\nAvailable slots for Monday:");
        System.out.println(cardiologist.getAvailableSlots(StaticSchedule.Day.MONDAY));

        // Patient tries to schedule an appointment for Monday at 10:00
        System.out.println("\nPatient trying to schedule appointment for Monday at 10:00");
        Appointment newAppointment = cardiologist.scheduleAppointment(patient1, StaticSchedule.Day.MONDAY, LocalTime.of(10, 0), 30);
        if (newAppointment != null) {
            System.out.println("Appointment scheduled successfully:");
            System.out.println(newAppointment.GeneralInfo());
        }

        // Show available slots after scheduling
        System.out.println("\nAvailable slots after scheduling:");
        System.out.println(cardiologist.getAvailableSlots(StaticSchedule.Day.MONDAY));

        // Cancel the appointment
        System.out.println("\nCancelling the appointment...");
        cardiologist.cancelAppointment(newAppointment);

        // Show available slots after cancellation
        System.out.println("\nAvailable slots after cancellation:");
        System.out.println(cardiologist.getAvailableSlots(StaticSchedule.Day.MONDAY));

        // Try to schedule another appointment at the same time
        System.out.println("\nTrying to schedule another appointment at 10:00");
        Appointment newAppointment2 = cardiologist.scheduleAppointment(patient2, StaticSchedule.Day.MONDAY, LocalTime.of(10, 0), 30);
        if (newAppointment2 != null) {
            System.out.println("Appointment scheduled successfully after cancellation:");
            System.out.println(newAppointment2.GeneralInfo());
        }

        // Try to schedule an appointment outside working hours
        System.out.println("\nTrying to schedule appointment outside working hours:");
        Appointment lateAppointment = cardiologist.scheduleAppointment(patient2, StaticSchedule.Day.MONDAY, LocalTime.of(17, 30), 30);
        if (lateAppointment == null) {
            System.out.println("Failed to schedule appointment outside working hours (as expected)");
        }

        // Test patient discharge
        System.out.println("\n=== Testing Patient Discharge ===");
        patient1.checkOut();
        patient2.checkOut();
        System.out.println(room101.GeneralInfo());
        System.out.println(room102.GeneralInfo());
    }
}