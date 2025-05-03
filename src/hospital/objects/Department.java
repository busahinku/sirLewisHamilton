package hospital.objects;

import java.util.ArrayList;
import java.util.List;

public class Department {
    private String deptName;
    private Doctor head;
    private String blockName;
    private List<Doctor> doctors;

    public Department(String deptName, Doctor head, String blockName) {
        this.deptName = deptName;
        this.head = head;
        this.blockName = blockName;
        this.doctors = new ArrayList<>();
    }

    // Getters
    public String getDeptName() {
        return deptName;
    }

    public Doctor getHead() {
        return head;
    }

    public String getBlockName() {
        return blockName;
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

    public void setBlockName(String blockName) {
        this.blockName = blockName;
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

    public String GeneralInfo() {
        return "Department [Name: " + deptName + ", Head: " + head.getFullName() + ", Block: " + blockName + ", Doctors: " + doctors.size() + "]";
    }
}
