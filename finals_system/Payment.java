
public class Payment {

    public static final int CASH = 0;
    public static final int CREDIT_CARD = 1;
    public static final int GCASH = 2;

    private int type;
    private double amountTendered;
    private double totalDue;

    public Payment(int type, double amountTendered, double totalDue) {
        this.type = type;
        this.amountTendered = amountTendered;
        this.totalDue = totalDue;
    }

    public int getType() {
        return type;
    }

    public String getTypeLabel() {
        if (type == CREDIT_CARD) {
            return "Credit Card";
        } else if (type == GCASH) {
            return "GCash";
        } else {
            return "Cash";
        }
    }

    public double getAmountTendered() {
        return amountTendered;
    }

    public double getTotalDue() {
        return totalDue;
    }

    public double getChange() {
        return amountTendered - totalDue;
    }
}
