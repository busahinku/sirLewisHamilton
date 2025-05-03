package hospital.objects;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Inventory {
    private String itemId;
    private String itemName;
    private String category; // e.g., "Medical Supplies", "Equipment", "Medication"
    private int quantity;
    private int minimumQuantity;
    private double unitPrice;
    private String supplier;
    private LocalDateTime lastRestocked;
    private String location;
    private List<InventoryTransaction> transactions;
    private String notes;

    public Inventory(String itemId, String itemName, String category, int quantity,
                    int minimumQuantity, double unitPrice, String supplier, String location) {
        this.itemId = itemId;
        this.itemName = itemName;
        this.category = category;
        this.quantity = quantity;
        this.minimumQuantity = minimumQuantity;
        this.unitPrice = unitPrice;
        this.supplier = supplier;
        this.lastRestocked = LocalDateTime.now();
        this.location = location;
        this.transactions = new ArrayList<>();
        this.notes = "";
    }

    // Getters
    public String getItemId() {
        return itemId;
    }

    public String getItemName() {
        return itemName;
    }

    public String getCategory() {
        return category;
    }

    public int getQuantity() {
        return quantity;
    }

    public int getMinimumQuantity() {
        return minimumQuantity;
    }

    public double getUnitPrice() {
        return unitPrice;
    }

    public String getSupplier() {
        return supplier;
    }

    public LocalDateTime getLastRestocked() {
        return lastRestocked;
    }

    public String getLocation() {
        return location;
    }

    public List<InventoryTransaction> getTransactions() {
        return new ArrayList<>(transactions);
    }

    public String getNotes() {
        return notes;
    }

    // Methods
    public void addStock(int amount) {
        this.quantity += amount;
        this.lastRestocked = LocalDateTime.now();
        transactions.add(new InventoryTransaction("Restock", amount, unitPrice));
    }

    public void removeStock(int amount) {
        if (this.quantity >= amount) {
            this.quantity -= amount;
            transactions.add(new InventoryTransaction("Usage", -amount, unitPrice));
        } else {
            System.out.println("Insufficient stock available");
        }
    }

    public void updateMinimumQuantity(int newMinimum) {
        this.minimumQuantity = newMinimum;
    }

    public void updateUnitPrice(double newPrice) {
        this.unitPrice = newPrice;
    }

    public void addNote(String note) {
        this.notes += "\n" + LocalDateTime.now() + ": " + note;
    }

    public String GeneralInfo() {
        return String.format("Inventory [ID: %s, Name: %s, Category: %s, Quantity: %d, Price: $%.2f, Location: %s]",
                itemId, itemName, category, quantity, unitPrice, location);
    }

    // Inner class for inventory transactions
    public static class InventoryTransaction {
        private String type; // "Restock" or "Usage"
        private int quantity;
        private double unitPrice;
        private LocalDateTime transactionDate;

        public InventoryTransaction(String type, int quantity, double unitPrice) {
            this.type = type;
            this.quantity = quantity;
            this.unitPrice = unitPrice;
            this.transactionDate = LocalDateTime.now();
        }

        public String getType() {
            return type;
        }

        public int getQuantity() {
            return quantity;
        }

        public double getUnitPrice() {
            return unitPrice;
        }

        public LocalDateTime getTransactionDate() {
            return transactionDate;
        }

        public double getTotalValue() {
            return quantity * unitPrice;
        }
    }
} 