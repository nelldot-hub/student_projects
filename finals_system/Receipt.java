
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Receipt {

    private static int transactionCounter = 1000;
    private static String lastTransactionId = "";

    public static String getLastTransactionId() {
        return lastTransactionId;
    }

    public static String buildReceiptText(Order order, Payment payment) {

        transactionCounter++;
        lastTransactionId = "TXN-" + transactionCounter;

        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

        StringBuilder sb = new StringBuilder();

        sb.append("==========================================================\n");
        sb.append("                  LAKBAY CORPORATION                      \n");
        sb.append("                 SIKLAB FUEL STATION                      \n");
        sb.append("            OFFICIAL SALES RECEIPT (POS)                  \n");
        sb.append("==========================================================\n");
        sb.append(String.format(" TXN #: %-20s  Date: %s%n",
                lastTransactionId, now.format(formatter)));
        sb.append("----------------------------------------------------------\n");

        sb.append(" PRODUCTS SOLD:\n");
        sb.append("+--------------------+-------+------+----------+----------+\n");
        sb.append("| Product            |  Qty  | Unit |Unit Price|   Total  |\n");
        sb.append("+--------------------+-------+------+----------+----------+\n");

        for (OrderItem item : order.getItems()) {
            sb.append(String.format("| %-18s | %5.2f | %-4s | %8.2f | %8.2f |%n",
                    item.getFuelProduct().getName(),
                    item.getQuantity(),
                    item.getFuelProduct().getUnit(),
                    item.getFuelProduct().getPrice(),
                    item.getItemTotal()));
        }
        sb.append("+--------------------+-------+------+----------+----------+\n\n");

        sb.append(String.format("  Subtotal:                           PHP %10.2f%n",
                order.getSubtotal()));

        if (order.getDiscount().getType() != Discount.NONE) {
            sb.append("----------------------------------------------------------\n");
            sb.append("  DISCOUNT DETAILS:\n");
            sb.append(String.format("  Type: %s%n",
                    order.getDiscount().getDescription()));
            if (order.getDiscount().isVatExempt()) {
                sb.append(String.format("  VAT-Exclusive Base:          PHP %10.2f%n",
                        order.getVatExclusiveSubtotal()));
            }
            sb.append(String.format("  Discount Amount:           - PHP %10.2f%n",
                    order.getDiscountAmount()));
        }

        // VAT section
        sb.append("----------------------------------------------------------\n");
        sb.append("  VAT BREAKDOWN:\n");
        if (order.getDiscount().isVatExempt()) {
            sb.append("  Status:                        VAT EXEMPT\n");
            sb.append(String.format("  VATable Amount:              PHP %10.2f%n", 0.00));
            sb.append(String.format("  VAT (12%%):                   PHP %10.2f%n", 0.00));
        } else {
            sb.append(String.format("  VATable Amount:              PHP %10.2f%n",
                    order.getVatableAmount()));
            sb.append(String.format("  VAT (12%%):                   PHP %10.2f%n",
                    order.getVatAmount()));
        }

        sb.append("==========================================================\n");
        sb.append(String.format("  TOTAL DUE:                         PHP %10.2f%n",
                order.getTotalDue()));
        sb.append("==========================================================\n");

        sb.append("  PAYMENT DETAILS:\n");
        sb.append(String.format("  Payment Type:    %s%n",
                payment.getTypeLabel()));
        sb.append(String.format("  Amount Tendered: PHP %10.2f%n",
                payment.getAmountTendered()));

        if (payment.getType() == Payment.CASH) {
            sb.append(String.format("  Change:          PHP %10.2f%n",
                    payment.getChange()));
        }

        sb.append("==========================================================\n");
        sb.append("       Thank you for fueling up at Siklab Fuel!           \n");
        sb.append("           Drive safe! - Lakbay Corporation               \n");
        sb.append("==========================================================\n");

        return sb.toString();
    }
}
