
public class Discount {

    public static final int NONE = 0;
    public static final int SENIOR = 1;
    public static final int PWD = 2;

    private int type;

    public Discount(int type) {
        this.type = type;
    }

    public int getType() {
        return type;
    }

    public String getDescription() {
        if (type == SENIOR) {
            return "Senior Citizen (20%)";
        } else if (type == PWD) {
            return "PWD (20%)";
        } else {
            return "No Discount";
        }
    }

    public double getRate() {
        if (type == SENIOR || type == PWD) {
            return 0.20;
        } else {
            return 0.0;
        }
    }

    public boolean isVatExempt() {
        if (type == SENIOR || type == PWD) {
            return true;
        } else {
            return false;
        }
    }
}
