package hospital.objects;

import java.util.ArrayList;
import java.util.List;

public class Founder extends Person {
    private List<Department> departments;
    private List<Doctor> doctors;
    private double totalRevenue;
    private double salary;

    public Founder(String id, String firstName, String lastName, int age, char gender,
                  String phoneNumber, String username, String password, double salary) {
        super(id, firstName, lastName, age, gender, phoneNumber, username, password);
        this.departments = new ArrayList<>();
        this.doctors = new ArrayList<>();
        this.totalRevenue = 0.0;
        this.salary = salary;
    }

    // Getters
    public List<Department> getDepartments() {
        return new ArrayList<>(departments);
    }

    public List<Doctor> getDoctors() {
        return new ArrayList<>(doctors);
    }

    public double getTotalRevenue() {
        return totalRevenue;
    }

    public double getSalary() {
        return salary;
    }

    // Methods
    public void createDepartment(String name, Doctor head, String location) {
        Department department = new Department(name, head, location);
        departments.add(department);
    }

    public void hireDoctor(Doctor doctor, Department department) {
        doctors.add(doctor);
        if (department != null && !doctor.isPrivate()) {
            department.addDoctor(doctor);
        }
    }

    public void fireDoctor(Doctor doctor) {
        doctors.remove(doctor);
        if (!doctor.isPrivate()) {
            for (Department dept : departments) {
                dept.removeDoctor(doctor);
            }
        }
    }

    public void addRevenue(double amount) {
        this.totalRevenue += amount;
    }

    public void generateHospitalReport() {
        System.out.println("\n=== Hospital Report ===");
        System.out.printf("Total Revenue: $%.2f%n", totalRevenue);
        System.out.println("\nDepartments:");
        for (Department dept : departments) {
            System.out.println("- " + dept.getDeptName() + " (Head: " + dept.getHead().getFullName() + ")");
        }
        System.out.println("\nDoctors:");
        for (Doctor doctor : doctors) {
            if (doctor.isPrivate()) {
                System.out.printf("- %s (Private) - Fee: $%.2f/min%n", 
                    doctor.getFullName(), doctor.getAppointmentFee());
            } else {
                System.out.printf("- %s (Hospital) - Salary: $%.2f/month%n", 
                    doctor.getFullName(), doctor.getSalary());
            }
        }
    }

    @Override
    public String toString() {
        return String.format("Founder [Name: %s, Departments: %d, Doctors: %d, Total Revenue: $%.2f]",
                getFullName(), departments.size(), doctors.size(), totalRevenue);
    }
}

