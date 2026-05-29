
import java.util.ArrayList;
import java.util.List;

public class Order {

    private static final double VAT_RATE = 0.12;

    private List<OrderItem> items;
    private Discount discount;

    public Order() {
        this.items = new ArrayList<>();
        this.discount = new Discount(Discount.NONE);
    }

    public void addItem(FuelProduct product, double quantity) {
        for (OrderItem item : items) {
            if (item.getFuelProduct().getId() == product.getId()) {
                item.setQuantity(item.getQuantity() + quantity);
                return;
            }
        }
        items.add(new OrderItem(product, quantity));
    }

    public boolean removeItem(int index) {
        if (index >= 0 && index < items.size()) {
            items.remove(index);
            return true;
        }
        return false;
    }

    public void clear() {
        items.clear();
        discount = new Discount(Discount.NONE);
    }

    public List<OrderItem> getItems() {
        return items;
    }

    public boolean isEmpty() {
        return items.isEmpty();
    }

    public Discount getDiscount() {
        return discount;
    }

    public void setDiscount(Discount discount) {
        this.discount = discount;
    }

    public double getSubtotal() {
        double subtotal = 0;
        for (OrderItem item : items) {
            subtotal += item.getItemTotal();
        }
        return subtotal;
    }

    public double getVatExclusiveSubtotal() {
        return getSubtotal() / (1 + VAT_RATE);
    }

    public double getDiscountAmount() {
        if (discount.getType() == Discount.NONE) {
            return 0;
        }
        if (discount.isVatExempt()) {
            return getVatExclusiveSubtotal() * discount.getRate();
        }
        return getSubtotal() * discount.getRate();
    }

    public double getVatAmount() {
        if (discount.isVatExempt()) {
            return 0;
        }
        double afterDiscount = getSubtotal() - getDiscountAmount();
        return afterDiscount - (afterDiscount / (1 + VAT_RATE));
    }

    public double getVatableAmount() {
        if (discount.isVatExempt()) {
            return getVatExclusiveSubtotal() - getDiscountAmount();
        }
        double afterDiscount = getSubtotal() - getDiscountAmount();
        return afterDiscount / (1 + VAT_RATE);
    }

    public double getTotalDue() {
        if (discount.isVatExempt()) {
            return getVatExclusiveSubtotal() - getDiscountAmount();
        }
        return getSubtotal() - getDiscountAmount();
    }
}
