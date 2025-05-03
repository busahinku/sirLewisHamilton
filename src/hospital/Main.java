package hospital;

import hospital.objects.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

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
        founder.hireDoctor(cardiologist, cardiology);
        founder.hireDoctor(neurologist, neurology);

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

        // Create schedules
        Schedule schedule1 = new Schedule("S001", cardiologist, now, now.plusMonths(1));
        schedule1.addShift(now.plusDays(1), now.plusDays(1).plusHours(8), "Cardiology Ward");
        schedule1.addShift(now.plusDays(2), now.plusDays(2).plusHours(8), "Cardiology Ward");

        Schedule schedule2 = new Schedule("S002", neurologist, now, now.plusMonths(1));
        schedule2.addShift(now.plusDays(1), now.plusDays(1).plusHours(8), "Neurology Ward");
        schedule2.addShift(now.plusDays(2), now.plusDays(2).plusHours(8), "Neurology Ward");

        // Create inventory items
        Inventory med1 = new Inventory("I001", "Lisinopril", "Medication", 100, 20, 5.0, 
            "PharmaCo", "Pharmacy");
        Inventory med2 = new Inventory("I002", "Sumatriptan", "Medication", 50, 10, 8.0, 
            "MediCorp", "Pharmacy");

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

        // Test schedules
        System.out.println("\n=== Testing Schedules ===");
        System.out.println(schedule1.GeneralInfo());
        System.out.println(schedule2.GeneralInfo());

        // Test inventory management
        System.out.println("\n=== Testing Inventory ===");
        med1.addStock(50);
        med2.removeStock(10);
        System.out.println(med1.GeneralInfo());
        System.out.println(med2.GeneralInfo());

        // Test department management
        System.out.println("\n=== Testing Departments ===");
        System.out.println(cardiology.GeneralInfo());
        System.out.println(neurology.GeneralInfo());

        // Test doctor management
        System.out.println("\n=== Testing Doctors ===");
        System.out.println(cardiologist.GeneralInfo());
        System.out.println(neurologist.GeneralInfo());

        // Test patient discharge
        System.out.println("\n=== Testing Patient Discharge ===");
        patient1.checkOut();
        patient2.checkOut();
        System.out.println(room101.GeneralInfo());
        System.out.println(room102.GeneralInfo());

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

        // Add expenses for medications
        founder.addExpense("Medication", med1.getUnitPrice() * 50, "Purchase of " + med1.getItemName());
        founder.addExpense("Medication", med2.getUnitPrice() * 10, "Purchase of " + med2.getItemName());

        // Generate monthly report
        founder.generateMonthlyReport();
        System.out.println("\nMonthly Report:");
        System.out.println(founder.getReports().get(1).getReportSummary());

        // Print financial summary
        System.out.println("\nCurrent Financial Status:");
        System.out.println(founder.getFinancialSummary());

        // Print detailed transactions
        System.out.println("\nRecent Transactions:");
        for (Founder.FinancialTransaction transaction : founder.getTransactions()) {
            System.out.printf("%s: $%.2f - %s (%s)%n",
                transaction.getType(),
                Math.abs(transaction.getAmount()),
                transaction.getDescription(),
                transaction.getCategory());
        }

        // Test appointment scheduling system
        System.out.println("\n=== Testing Appointment Scheduling System ===");
        
        // Get available slots for next week
        LocalDateTime nextWeek = now.plusWeeks(1);
        System.out.println("\nAvailable slots for Dr. Smith (Cardiologist) next week:");
        List<LocalDateTime> availableSlots = cardiologist.getAvailableSlots(now, nextWeek, 30);
        for (LocalDateTime slot : availableSlots) {
            System.out.println(slot);
        }

        // Patient tries to schedule an appointment
        LocalDateTime desiredTime = now.plusDays(2).withHour(10).withMinute(0);
        Appointment newAppointment = cardiologist.scheduleAppointment(patient1, desiredTime, 30);
        if (newAppointment != null) {
            System.out.println("\nAppointment scheduled successfully:");
            System.out.println(newAppointment.GeneralInfo());
        }

        // Try to schedule overlapping appointment
        System.out.println("\nTrying to schedule overlapping appointment:");
        Appointment overlappingAppointment = cardiologist.scheduleAppointment(patient2, desiredTime, 30);
        if (overlappingAppointment == null) {
            System.out.println("Failed to schedule overlapping appointment (as expected)");
        }

        // Get doctor's upcoming appointments
        System.out.println("\nDr. Smith's upcoming appointments:");
        for (Appointment appointment : cardiologist.getUpcomingAppointments()) {
            System.out.println(appointment.GeneralInfo());
        }

        // Try to reschedule appointment
        System.out.println("\nRescheduling appointment:");
        LocalDateTime newTime = desiredTime.plusHours(2);
        cardiologist.rescheduleAppointment(newAppointment, newTime);
        System.out.println("Updated appointment details:");
        System.out.println(newAppointment.GeneralInfo());

        // Cancel appointment
        System.out.println("\nCancelling appointment:");
        cardiologist.cancelAppointment(newAppointment);
        System.out.println("Appointment status: " + newAppointment.getStatus());

        // Get appointments for specific date
        System.out.println("\nAppointments for " + now.toLocalDate() + ":");
        for (Appointment appointment : cardiologist.getAppointmentsForDate(now)) {
            System.out.println(appointment.GeneralInfo());
        }
    }
}