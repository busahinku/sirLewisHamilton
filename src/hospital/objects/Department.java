package hospital.objects;

import java.util.ArrayList;
import java.util.List;

public class Department {
    private String deptName;
    private Doctor head;
    private String blockNumber;
    private List<Doctor> doctors;

    public Department(String deptName, Doctor head, String blockNumber) {
        this.deptName = deptName;
        this.head = head;
        this.blockNumber = blockNumber;
        this.doctors = new ArrayList<>();
        if (head != null) {
            this.doctors.add(head);
        }
    }

    // Getters
    public String getDeptName() {
        return deptName;
    }

    public Doctor getHead() {
        return head;
    }

    public String getBlockNumber() {
        return blockNumber;
    }

    public List<Doctor> getDoctors() {
        return new ArrayList<>(doctors);
    }

    // Setters
    public void setHead(Doctor head) {
        this.head = head;
        if (!doctors.contains(head)) {
            doctors.add(head);
        }
    }

    public void setBlockNumber(String blockNumber) {
        this.blockNumber = blockNumber;
    }

    // Methods
    public void addDoctor(Doctor doctor) {
        if (!doctors.contains(doctor)) {
            doctors.add(doctor);
        }
    }

    public void removeDoctor(Doctor doctor) {
        doctors.remove(doctor);
    }

    @Override
    public String toString() {
        return String.format("Department [Name: %s, Head: %s, Block: %s, Doctors: %d]",
                deptName, head.getFullName(), blockNumber, doctors.size());
    }
}
