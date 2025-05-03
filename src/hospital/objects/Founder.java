package hospital.objects;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Founder extends Person {
    private List<Department> departments;
    private List<Doctor> doctors;
    private double totalRevenue;
    private double totalExpenses;
    private double salary;
    private List<FinancialTransaction> transactions;
    private List<HospitalReport> reports;

    public Founder(String id, String firstName, String lastName, int age, char gender,
                  String phoneNumber, String username, String password, double salary) {
        super(id, firstName, lastName, age, gender, phoneNumber, username, password);
        this.departments = new ArrayList<>();
        this.doctors = new ArrayList<>();
        this.totalRevenue = 0.0;
        this.totalExpenses = 0.0;
        this.salary = salary;
        this.transactions = new ArrayList<>();
        this.reports = new ArrayList<>();
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

    public double getTotalExpenses() {
        return totalExpenses;
    }

    public double getNetIncome() {
        return totalRevenue - totalExpenses;
    }

    public double getSalary() {
        return salary;
    }

    public List<FinancialTransaction> getTransactions() {
        return new ArrayList<>(transactions);
    }

    public List<HospitalReport> getReports() {
        return new ArrayList<>(reports);
    }

    // Methods
    public void createDepartment(String name, Doctor head, String location) {
        Department department = new Department(name, head, location);
        departments.add(department);
        addExpense("Department Setup", 50000.0, "Initial setup cost for " + name);
    }

    public void hireDoctor(Doctor doctor, Department department) {
        doctors.add(doctor);
        if (department != null && !doctor.isPrivate()) {
            department.addDoctor(doctor);
            addExpense("Doctor Salary", doctor.getSalary(), "Monthly salary for Dr. " + doctor.getFullName());
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

    public void addRevenue(double amount, String source, String description) {
        this.totalRevenue += amount;
        transactions.add(new FinancialTransaction("Revenue", amount, source, description));
    }

    public void addExpense(String category, double amount, String description) {
        this.totalExpenses += amount;
        transactions.add(new FinancialTransaction("Expense", -amount, category, description));
    }

    public void generateMonthlyReport() {
        HospitalReport report = new HospitalReport(
            "Monthly Report - " + LocalDateTime.now().getMonth(),
            departments.size(),
            doctors.size(),
            totalRevenue,
            totalExpenses,
            getNetIncome(),
            new ArrayList<>(transactions)
        );
        reports.add(report);
    }

    public void generateStartupReport() {
        double startupCosts = 0.0;
        List<FinancialTransaction> startupTransactions = new ArrayList<>();
        
        // Calculate initial setup costs
        for (Department dept : departments) {
            startupCosts += 50000.0; // Base setup cost per department
        }
        
        for (Doctor doc : doctors) {
            startupCosts += doc.getSalary() * 3; // 3 months salary as initial cost
        }
        
        startupTransactions.add(new FinancialTransaction("Expense", -startupCosts, "Startup Costs", "Initial hospital setup"));
        
        HospitalReport startupReport = new HospitalReport(
            "Startup Report",
            departments.size(),
            doctors.size(),
            0.0, // No initial revenue
            startupCosts,
            -startupCosts,
            startupTransactions
        );
        reports.add(startupReport);
    }

    public String getFinancialSummary() {
        return String.format("Financial Summary [Revenue: $%.2f, Expenses: $%.2f, Net Income: $%.2f]",
                totalRevenue, totalExpenses, getNetIncome());
    }

    // Inner class for financial transactions
    public static class FinancialTransaction {
        private String type; // "Revenue" or "Expense"
        private double amount;
        private String category;
        private String description;
        private LocalDateTime date;

        public FinancialTransaction(String type, double amount, String category, String description) {
            this.type = type;
            this.amount = amount;
            this.category = category;
            this.description = description;
            this.date = LocalDateTime.now();
        }

        public String getType() {
            return type;
        }

        public double getAmount() {
            return amount;
        }

        public String getCategory() {
            return category;
        }

        public String getDescription() {
            return description;
        }

        public LocalDateTime getDate() {
            return date;
        }
    }

    // Inner class for hospital reports
    public static class HospitalReport {
        private String title;
        private int departmentCount;
        private int doctorCount;
        private double totalRevenue;
        private double totalExpenses;
        private double netIncome;
        private List<FinancialTransaction> transactions;
        private LocalDateTime generationDate;

        public HospitalReport(String title, int departmentCount, int doctorCount,
                            double totalRevenue, double totalExpenses, double netIncome,
                            List<FinancialTransaction> transactions) {
            this.title = title;
            this.departmentCount = departmentCount;
            this.doctorCount = doctorCount;
            this.totalRevenue = totalRevenue;
            this.totalExpenses = totalExpenses;
            this.netIncome = netIncome;
            this.transactions = new ArrayList<>(transactions);
            this.generationDate = LocalDateTime.now();
        }

        public String getTitle() {
            return title;
        }

        public int getDepartmentCount() {
            return departmentCount;
        }

        public int getDoctorCount() {
            return doctorCount;
        }

        public double getTotalRevenue() {
            return totalRevenue;
        }

        public double getTotalExpenses() {
            return totalExpenses;
        }

        public double getNetIncome() {
            return netIncome;
        }

        public List<FinancialTransaction> getTransactions() {
            return new ArrayList<>(transactions);
        }

        public LocalDateTime getGenerationDate() {
            return generationDate;
        }

        public String getReportSummary() {
            return String.format("Report: %s\n" +
                               "Departments: %d\n" +
                               "Doctors: %d\n" +
                               "Revenue: $%.2f\n" +
                               "Expenses: $%.2f\n" +
                               "Net Income: $%.2f\n" +
                               "Generated: %s",
                               title, departmentCount, doctorCount,
                               totalRevenue, totalExpenses, netIncome,
                               generationDate);
        }
    }
}

