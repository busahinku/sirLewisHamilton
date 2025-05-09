package hospital.objects;

import java.time.LocalDateTime;

public class Room {
    private String roomName;
    private String roomType; // e.g., "General", "ICU", "Operating", "Emergency"
    private int capacity;
    private boolean isAvailable;
    private Patient currentPatient;
    private LocalDateTime occupiedSince;
    private double hourlyRate;
    private String equipment; // List of equipment in the room
    private String notes;

    public Room(String roomName, String roomType, int capacity, double hourlyRate, String equipment) {
        this.roomName = roomName;
        this.roomType = roomType;
        this.capacity = capacity;
        this.isAvailable = true;
        this.currentPatient = null;
        this.occupiedSince = null;
        this.hourlyRate = hourlyRate;
        this.equipment = equipment;
        this.notes = "";
    }

    // Getters
    public String getRoomName() {
        return roomName;
    }

    public String getRoomType() {
        return roomType;
    }

    public int getCapacity() {
        return capacity;
    }

    public boolean isAvailable() {
        return isAvailable;
    }

    public Patient getCurrentPatient() {
        return currentPatient;
    }

    public LocalDateTime getOccupiedSince() {
        return occupiedSince;
    }

    public double getHourlyRate() {
        return hourlyRate;
    }

    public String getEquipment() {
        return equipment;
    }

    public String getNotes() {
        return notes;
    }

    // Methods
    public void assignPatient(Patient patient) {
        if (this.currentPatient == null) {
            this.currentPatient = patient;
            this.isAvailable = false;
            this.occupiedSince = LocalDateTime.now();
            System.out.println("Patient " + patient.getFullName() + " assigned to room " + roomName);
        } else {
            System.out.println("Room " + roomName + " is already occupied");
        }
    }

    public void dischargePatient() {
        if (this.currentPatient != null) {
            System.out.println("Patient " + currentPatient.getFullName() + " discharged from room " + roomName);
            this.currentPatient = null;
            this.isAvailable = true;
            this.occupiedSince = null;
        }
    }

    public double calculateRoomCharge() {
        if (!isAvailable && occupiedSince != null) {
            long hours = java.time.Duration.between(occupiedSince, LocalDateTime.now()).toHours();
            return hours * hourlyRate;
        }
        return 0.0;
    }

    public void addEquipment(String newEquipment) {
        this.equipment += ", " + newEquipment;
    }

    public void addNote(String note) {
        this.notes += "\n" + LocalDateTime.now() + ": " + note;
    }

    public String GeneralInfo() {
        String status;
        if (currentPatient != null) {
            status = "Occupied";
        } else {
            status = "Available";
        }

        return "Room [Name: " + roomName +
                ", Type: " + roomType +
                ", Rate: $" + String.format("%.2f", hourlyRate) + "/hr" +
                ", Equipment: " + equipment +
                ", Status: " + status + "]";
    }
}