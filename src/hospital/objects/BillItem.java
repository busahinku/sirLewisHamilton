package hospital.objects;

import java.time.LocalDateTime;


public class BillItem {
    private String description;
    private double amount;
    private LocalDateTime dateAdded;

    public BillItem(String description, double amount) {
        this.description = description;
        this.amount = amount;
        this.dateAdded = LocalDateTime.now();
    }

    public String getDescription() {
        return description;
    }

    public double getAmount() {
        return amount;
    }

}