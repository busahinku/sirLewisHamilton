package hospital.objects;

public class Doctor extends Person {
    private String department;
    private String specialty;
    private String title;
    private String officeNumber;
    private short experience;
    private boolean isPrivate;
    private double salary; // Monthly salary for hospital doctors
    private double appointmentFee; // For private doctors
    private String privatePracticeLocation; // For private doctors only

    public Doctor(String id, String firstName, String lastName, int age, char gender, 
                 String phoneNumber, String username, String password,
                 String department, String specialty, String title, 
                 String officeNumber, short experience, boolean isPrivate, double salary) {
        super(id, firstName, lastName, age, gender, phoneNumber, username, password);
        this.department = department;
        this.specialty = specialty;
        this.title = title;
        this.officeNumber = officeNumber;
        this.experience = experience;
        this.isPrivate = isPrivate;
        this.salary = salary;
        this.appointmentFee = 0.0;
        this.privatePracticeLocation = null;
    }

    // Getters
    public String getDepartment() {
        return department;
    }

    public String getSpecialty() {
        return specialty;
    }

    public String getTitle() {
        return title;
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

    // Setters
    public void setDepartment(String department) {
        this.department = department;
    }

    public void setSpecialty(String specialty) {
        this.specialty = specialty;
    }

    public void setTitle(String title) {
        this.title = title;
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

    // Calculate appointment cost
    public double calculateAppointmentCost(int durationMinutes) {
        if (isPrivate) {
            return appointmentFee + 5.0; // Base fee + hospital's cut
        } else {
            // Hospital doctor: cost based on duration
            return durationMinutes * 2.0; // $2 per minute
        }
    }

    @Override
    public String toString() {
        String baseInfo = String.format("Doctor [Name: %s, Specialty: %s, Title: %s, Experience: %d years]",
                getFullName(), specialty, title, experience);
        
        if (isPrivate) {
            return baseInfo + String.format(", Private Practice at: %s, Fee: $%.2f", 
                    privatePracticeLocation, appointmentFee);
        } else {
            return baseInfo + String.format(", Department: %s, Office: %s", 
                    department, officeNumber);
        }
    }
}
