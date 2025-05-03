package hospital.objects;

import java.util.ArrayList;
import java.util.List;

public class Patient extends Person {
    private boolean hasInsurance;
    private String insuranceProvider;
    private String insurancePolicyNumber;
    private double balance;
    private List<Appointment> appointments;
    private List<Prescription> prescriptions;
    private MedicalRecord medicalRecord;
    private Room currentRoom;
    private List<Bill> bills;

    public Patient(String id, String firstName, String lastName, int age, char gender,
                  String phoneNumber, String username, String password,
                  boolean hasInsurance, String insuranceProvider, String insurancePolicyNumber) {
        super(id, firstName, lastName, age, gender, phoneNumber, username, password);
        this.hasInsurance = hasInsurance;
        this.insuranceProvider = insuranceProvider;
        this.insurancePolicyNumber = insurancePolicyNumber;
        this.balance = 0.0;
        this.appointments = new ArrayList<>();
        this.prescriptions = new ArrayList<>();
        this.bills = new ArrayList<>();
        this.medicalRecord = null;
        this.currentRoom = null;
    }

    // Getters
    public boolean hasInsurance() {
        return hasInsurance;
    }

    public String getInsuranceProvider() {
        return insuranceProvider;
    }

    public String getInsurancePolicyNumber() {
        return insurancePolicyNumber;
    }

    public double getBalance() {
        return balance;
    }

    public List<Appointment> getAppointments() {
        return new ArrayList<>(appointments);
    }

    public List<Prescription> getPrescriptions() {
        return new ArrayList<>(prescriptions);
    }

    public MedicalRecord getMedicalRecord() {
        return medicalRecord;
    }

    public Room getCurrentRoom() {
        return currentRoom;
    }

    public List<Bill> getBills() {
        return new ArrayList<>(bills);
    }

    // Setters
    public void setHasInsurance(boolean hasInsurance) {
        this.hasInsurance = hasInsurance;
    }

    public void setInsuranceProvider(String insuranceProvider) {
        this.insuranceProvider = insuranceProvider;
    }

    public void setInsurancePolicyNumber(String insurancePolicyNumber) {
        this.insurancePolicyNumber = insurancePolicyNumber;
    }

    public void setMedicalRecord(MedicalRecord medicalRecord) {
        this.medicalRecord = medicalRecord;
    }

    public void setCurrentRoom(Room room) {
        this.currentRoom = room;
    }

    // Methods
    public void addAppointment(Appointment appointment) {
        if (!appointments.contains(appointment)) {
            appointments.add(appointment);
        }
    }

    public void cancelAppointment(Appointment appointment) {
        appointments.remove(appointment);
    }

    public void addPrescription(Prescription prescription) {
        if (!prescriptions.contains(prescription)) {
            prescriptions.add(prescription);
            if (medicalRecord != null) {
                medicalRecord.addMedication(prescription.getMedication());
            }
        }
    }

    public void addBill(Bill bill) {
        if (!bills.contains(bill)) {
            bills.add(bill);
        }
    }

    public void payBill(Bill bill, double amount, String paymentMethod) {
        if (bills.contains(bill)) {
            bill.makePayment(amount, paymentMethod);
            if (bill.isPaid()) {
                balance -= bill.getTotalAmount();
            } else {
                balance -= amount;
            }
        }
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public void updateMedicalRecord(String diagnosis, String procedure, String labResult) {
        if (medicalRecord != null) {
            if (diagnosis != null) medicalRecord.addDiagnosis(diagnosis);
            if (procedure != null) medicalRecord.addProcedure(procedure);
            if (labResult != null) medicalRecord.addLabResult(labResult);
        }
    }

    public void checkIn(Room room) {
        if (currentRoom != null) {
            currentRoom.dischargePatient();
        }
        room.assignPatient(this);
        currentRoom = room;
    }

    public void checkOut() {
        if (currentRoom != null) {
            currentRoom.dischargePatient();
            currentRoom = null;
        }
    }

    public String GeneralInfo() {
        String insurance = hasInsurance ? insuranceProvider : "None";
        String room = (currentRoom != null) ? currentRoom.getRoomNumber() : "None";

        return "Patient [Name: " + getFullName() +
                ", Age: " + getAge() +
                ", Insurance: " + insurance +
                ", Balance: $" + String.format("%.2f", balance) +
                ", Current Room: " + room + "]";
    }
}
