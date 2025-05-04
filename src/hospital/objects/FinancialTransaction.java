package hospital.objects;

import java.time.LocalDateTime;

public class FinancialTransaction {
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