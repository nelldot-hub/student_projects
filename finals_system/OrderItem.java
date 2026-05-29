
public class OrderItem {

    private FuelProduct fuelProduct;
    private double quantity;

    public OrderItem(FuelProduct fuelProduct, double quantity) {
        this.fuelProduct = fuelProduct;
        this.quantity = quantity;
    }

    public FuelProduct getFuelProduct() {
        return fuelProduct;
    }

    public double getQuantity() {
        return quantity;
    }

    public void setQuantity(double quantity) {
        this.quantity = quantity;
    }

    public double getItemTotal() {
        return fuelProduct.getPrice() * quantity;
    }
}
