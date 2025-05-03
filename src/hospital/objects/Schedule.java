package hospital.objects;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Schedule {
    private String scheduleId;
    private Person staffMember;
    private List<Shift> shifts;
    private int totalHours;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private String notes;

    public Schedule(String scheduleId, Person staffMember, LocalDateTime startDate, LocalDateTime endDate) {
        this.scheduleId = scheduleId;
        this.staffMember = staffMember;
        this.shifts = new ArrayList<>();
        this.totalHours = 0;
        this.startDate = startDate;
        this.endDate = endDate;
        this.notes = "";
    }

    // Getters
    public String getScheduleId() {
        return scheduleId;
    }

    public Person getStaffMember() {
        return staffMember;
    }

    public List<Shift> getShifts() {
        return new ArrayList<>(shifts);
    }

    public int getTotalHours() {
        return totalHours;
    }

    public LocalDateTime getStartDate() {
        return startDate;
    }

    public LocalDateTime getEndDate() {
        return endDate;
    }

    public String getNotes() {
        return notes;
    }

    // Methods
    public void addShift(LocalDateTime startTime, LocalDateTime endTime, String location) {
        Shift shift = new Shift(startTime, endTime, location);
        shifts.add(shift);
        updateTotalHours();
    }

    public void removeShift(Shift shift) {
        if (shifts.remove(shift)) {
            updateTotalHours();
        }
    }

    public void updateShift(Shift shift, LocalDateTime newStartTime, LocalDateTime newEndTime, String newLocation) {
        shift.setStartTime(newStartTime);
        shift.setEndTime(newEndTime);
        shift.setLocation(newLocation);
        updateTotalHours();
    }

    private void updateTotalHours() {
        totalHours = 0;
        for (Shift shift : shifts) {
            totalHours += shift.getDurationHours();
        }
    }

    public void addNote(String note) {
        this.notes += "\n" + LocalDateTime.now() + ": " + note;
    }

    public String GeneralInfo() {
        return String.format("Schedule [ID: %s, Staff: %s, Period: %s to %s, Total Hours: %d]",
                scheduleId, staffMember.getFullName(), startDate, endDate, totalHours);
    }

    // Inner class for shifts
    public static class Shift {
        private LocalDateTime startTime;
        private LocalDateTime endTime;
        private String location;

        public Shift(LocalDateTime startTime, LocalDateTime endTime, String location) {
            this.startTime = startTime;
            this.endTime = endTime;
            this.location = location;
        }

        public LocalDateTime getStartTime() {
            return startTime;
        }

        public LocalDateTime getEndTime() {
            return endTime;
        }

        public String getLocation() {
            return location;
        }

        public void setStartTime(LocalDateTime startTime) {
            this.startTime = startTime;
        }

        public void setEndTime(LocalDateTime endTime) {
            this.endTime = endTime;
        }

        public void setLocation(String location) {
            this.location = location;
        }

        public int getDurationHours() {
            return (int) java.time.Duration.between(startTime, endTime).toHours();
        }
    }
} 