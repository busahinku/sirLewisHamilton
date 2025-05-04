package hospital.objects;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class HospitalReport {
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
        return "Report: " + title + "\n" +
                "Departments: " + departmentCount + "\n" +
                "Doctors: " + doctorCount + "\n" +
                "Revenue: $" + String.format("%.2f", totalRevenue) + "\n" +
                "Expenses: $" + String.format("%.2f", totalExpenses) + "\n" +
                "Net Income: $" + String.format("%.2f", netIncome) + "\n" +
                "Generated: " + generationDate;
    }
} 