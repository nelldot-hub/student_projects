
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.List;
import java.awt.Image;

public class POSMainFrame extends JFrame {

    private List<FuelProduct> menu;
    private Order order;

    private JTable menuTable;
    private DefaultTableModel menuTableModel;
    private JTextField qtyField;

    private JTable orderTable;
    private DefaultTableModel orderTableModel;
    private JLabel totalLabel;

    private JRadioButton byAmountBtn;

    public POSMainFrame() {
        this.menu = new ArrayList<>();
        this.order = new Order();
        initializeProducts();
        setupFrame();
        buildUI();
    }

    private void initializeProducts() {
        menu.add(new FuelProduct(1, "Diesel", 83.81, "L"));
        menu.add(new FuelProduct(2, "Premium Diesel", 92.38, "L"));
        menu.add(new FuelProduct(3, "Unleaded 91", 91.06, "L"));
        menu.add(new FuelProduct(4, "Premium 95", 97.16, "L"));
        menu.add(new FuelProduct(5, "Premium 97", 103.37, "L"));
        menu.add(new FuelProduct(6, "LPG Tank (11kg)", 1630.00, "Tank"));
        menu.add(new FuelProduct(7, "Kerosene", 147.07, "L"));
    }

    private void setupFrame() {
        setTitle("Siklab Fuel by Lakbay Corporation - Point of Sale System");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1050, 650);
        setMinimumSize(new Dimension(900, 520));
        setLocationRelativeTo(null);
    }

    private void buildUI() {
        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBorder(new EmptyBorder(10, 10, 10, 10));

        JPanel headerPanel = new JPanel(new BorderLayout(10, 0));
        headerPanel.setBorder(new EmptyBorder(5, 10, 10, 10));

        ImageIcon logoIcon = new ImageIcon("siklab_logo.png");
        Image logoImage = logoIcon.getImage().getScaledInstance(80, 80, Image.SCALE_SMOOTH);
        logoIcon = new ImageIcon(logoImage);
        JLabel logoLabel = new JLabel(logoIcon);
        logoLabel.setBorder(new EmptyBorder(0, 0, 0, 10));
        headerPanel.add(logoLabel, BorderLayout.WEST);

        JPanel headerTextPanel = new JPanel(new GridLayout(2, 1));
        headerTextPanel.setOpaque(false);

        JLabel stationLabel = new JLabel("SIKLAB FUEL STATION", SwingConstants.CENTER);
        stationLabel.setFont(new Font("SansSerif", Font.BOLD, 24));
        stationLabel.setForeground(new Color(255, 49, 49));

        JLabel corpLabel = new JLabel("LAKBAY CORPORATION", SwingConstants.CENTER);
        corpLabel.setFont(new Font("SansSerif", Font.PLAIN, 16));
        corpLabel.setForeground(new Color(24, 0, 173));

        headerTextPanel.add(stationLabel);
        headerTextPanel.add(corpLabel);
        headerPanel.add(headerTextPanel, BorderLayout.CENTER);

        ImageIcon lakbayIcon = new ImageIcon("lakbay_logo.png");
        Image lakbayImage = lakbayIcon.getImage().getScaledInstance(80, 80, Image.SCALE_SMOOTH);
        lakbayIcon = new ImageIcon(lakbayImage);
        JLabel lakbayLabel = new JLabel(lakbayIcon);
        lakbayLabel.setBorder(new EmptyBorder(0, 10, 0, 0));
        headerPanel.add(lakbayLabel, BorderLayout.EAST);

        mainPanel.add(headerPanel, BorderLayout.NORTH);

        JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT,
                buildMenuPanel(), buildOrderPanel());
        splitPane.setDividerLocation(460);
        splitPane.setResizeWeight(0.45);
        mainPanel.add(splitPane, BorderLayout.CENTER);

        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JButton newTransactionBtn = new JButton("New Transaction");
        newTransactionBtn.setFont(new Font("SansSerif", Font.BOLD, 13));
        newTransactionBtn.addActionListener(e -> resetForNewTransaction());
        bottomPanel.add(newTransactionBtn);
        mainPanel.add(bottomPanel, BorderLayout.SOUTH);

        setContentPane(mainPanel);
    }

    private JPanel buildMenuPanel() {
        JPanel panel = new JPanel(new BorderLayout(5, 5));
        panel.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createEtchedBorder(), " PRODUCTS ",
                TitledBorder.CENTER, TitledBorder.TOP,
                new Font("SansSerif", Font.BOLD, 14)));

        String[] productCols = {"#", "Product", "Unit", "Price (PHP)"};
        menuTableModel = new DefaultTableModel(productCols, 0) {
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        for (FuelProduct product : menu) {
            menuTableModel.addRow(new Object[]{
                product.getId(),
                product.getName(),
                product.getUnit(),
                String.format("%.2f", product.getPrice())
            });
        }

        menuTable = new JTable(menuTableModel);
        menuTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        menuTable.setRowHeight(26);
        menuTable.setFont(new Font("SansSerif", Font.PLAIN, 13));
        menuTable.getTableHeader().setFont(new Font("SansSerif", Font.BOLD, 13));
        menuTable.getTableHeader().setReorderingAllowed(false);

        menuTable.getColumnModel().getColumn(0).setPreferredWidth(30);
        menuTable.getColumnModel().getColumn(1).setPreferredWidth(200);
        menuTable.getColumnModel().getColumn(2).setPreferredWidth(50);
        menuTable.getColumnModel().getColumn(3).setPreferredWidth(100);

        DefaultTableCellRenderer rightRenderer = new DefaultTableCellRenderer();
        rightRenderer.setHorizontalAlignment(SwingConstants.RIGHT);
        menuTable.getColumnModel().getColumn(3).setCellRenderer(rightRenderer);

        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(SwingConstants.CENTER);
        menuTable.getColumnModel().getColumn(0).setCellRenderer(centerRenderer);
        menuTable.getColumnModel().getColumn(2).setCellRenderer(centerRenderer);

        JScrollPane menuScroll = new JScrollPane(menuTable);
        panel.add(menuScroll, BorderLayout.CENTER);

        JPanel addPanel = new JPanel(new BorderLayout(4, 4));
        addPanel.setBorder(new EmptyBorder(4, 8, 6, 8));

        JPanel modePanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 8, 0));
        modePanel.add(new JLabel("Order by:"));

        JRadioButton byQtyBtn = new JRadioButton("Quantity (L / tank)");
        byAmountBtn = new JRadioButton("Amount (PHP)");
        byQtyBtn.setFont(new Font("SansSerif", Font.PLAIN, 12));
        byAmountBtn.setFont(new Font("SansSerif", Font.PLAIN, 12));
        byQtyBtn.setSelected(true);

        ButtonGroup modeGroup = new ButtonGroup();
        modeGroup.add(byQtyBtn);
        modeGroup.add(byAmountBtn);

        modePanel.add(byQtyBtn);
        modePanel.add(byAmountBtn);
        addPanel.add(modePanel, BorderLayout.NORTH);

        JPanel inputPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 8, 0));

        JLabel inputLabel = new JLabel("Quantity:");
        inputLabel.setPreferredSize(new Dimension(95, 20));
        qtyField = new JTextField("1.00", 8);
        qtyField.setFont(new Font("SansSerif", Font.PLAIN, 13));

        JButton addBtn = new JButton("Add to Order");
        addBtn.setFont(new Font("SansSerif", Font.BOLD, 13));
        addBtn.setBackground(new Color(76, 153, 0));
        addBtn.setForeground(Color.BLACK);
        addBtn.setFocusPainted(false);
        addBtn.addActionListener(e -> onAddToOrder(byAmountBtn.isSelected()));

        inputPanel.add(inputLabel);
        inputPanel.add(qtyField);
        inputPanel.add(addBtn);
        addPanel.add(inputPanel, BorderLayout.SOUTH);

        byQtyBtn.addActionListener(e -> {
            inputLabel.setText("Quantity:");
            qtyField.setText("1.00");
            qtyField.requestFocus();
        });

        byAmountBtn.addActionListener(e -> {
            int selectedRow = menuTable.getSelectedRow();
            if (selectedRow >= 0) {
                FuelProduct selected = menu.get(selectedRow);
                if (selected.getUnit().equals("tank")) {
                    JOptionPane.showMessageDialog(null,
                            "LPG tank exchange cannot be ordered by amount.\nPlease use Quantity mode.",
                            "Not Allowed", JOptionPane.WARNING_MESSAGE);
                    byQtyBtn.setSelected(true);
                    inputLabel.setText("Quantity:");
                    return;
                }
            }
            inputLabel.setText("Amount (PHP):");
            qtyField.setText("100.00");
            qtyField.requestFocus();
        });

        panel.add(addPanel, BorderLayout.SOUTH);
        return panel;
    }

    private JPanel buildOrderPanel() {
        JPanel panel = new JPanel(new BorderLayout(5, 5));
        panel.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createEtchedBorder(), " CURRENT ORDER ",
                TitledBorder.CENTER, TitledBorder.TOP,
                new Font("SansSerif", Font.BOLD, 14)));

        String[] orderCols = {"Product", "Qty", "Unit", "Unit Price", "Total"};
        orderTableModel = new DefaultTableModel(orderCols, 0) {
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        orderTable = new JTable(orderTableModel);
        orderTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        orderTable.setRowHeight(26);
        orderTable.setFont(new Font("SansSerif", Font.PLAIN, 13));
        orderTable.getTableHeader().setFont(new Font("SansSerif", Font.BOLD, 13));
        orderTable.getTableHeader().setReorderingAllowed(false);

        orderTable.getColumnModel().getColumn(0).setPreferredWidth(160);
        orderTable.getColumnModel().getColumn(1).setPreferredWidth(60);
        orderTable.getColumnModel().getColumn(2).setPreferredWidth(50);
        orderTable.getColumnModel().getColumn(3).setPreferredWidth(90);
        orderTable.getColumnModel().getColumn(4).setPreferredWidth(100);

        DefaultTableCellRenderer rightRenderer = new DefaultTableCellRenderer();
        rightRenderer.setHorizontalAlignment(SwingConstants.RIGHT);
        orderTable.getColumnModel().getColumn(1).setCellRenderer(rightRenderer);
        orderTable.getColumnModel().getColumn(3).setCellRenderer(rightRenderer);
        orderTable.getColumnModel().getColumn(4).setCellRenderer(rightRenderer);

        JScrollPane orderScroll = new JScrollPane(orderTable);
        panel.add(orderScroll, BorderLayout.CENTER);

        JPanel southPanel = new JPanel(new BorderLayout());

        JPanel totalPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        totalLabel = new JLabel("Running Total: PHP 0.00");
        totalLabel.setFont(new Font("SansSerif", Font.BOLD, 15));
        totalPanel.add(totalLabel);
        southPanel.add(totalPanel, BorderLayout.NORTH);

        JPanel btnPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 8, 8));

        JButton removeBtn = new JButton("Remove Selected");
        removeBtn.setFont(new Font("SansSerif", Font.PLAIN, 12));
        removeBtn.addActionListener(this::onRemoveSelected);
        btnPanel.add(removeBtn);

        JButton cancelBtn = new JButton("Cancel Order");
        cancelBtn.setFont(new Font("SansSerif", Font.PLAIN, 12));
        cancelBtn.setBackground(new Color(204, 0, 0));
        cancelBtn.setForeground(Color.BLACK);
        cancelBtn.setFocusPainted(false);
        cancelBtn.addActionListener(this::onCancelOrder);
        btnPanel.add(cancelBtn);

        JButton checkoutBtn = new JButton("Checkout");
        checkoutBtn.setFont(new Font("SansSerif", Font.BOLD, 13));
        checkoutBtn.setBackground(new Color(0, 102, 204));
        checkoutBtn.setForeground(Color.BLACK);
        checkoutBtn.setFocusPainted(false);
        checkoutBtn.addActionListener(this::onCheckout);
        btnPanel.add(checkoutBtn);

        southPanel.add(btnPanel, BorderLayout.SOUTH);
        panel.add(southPanel, BorderLayout.SOUTH);

        return panel;
    }

    private void onAddToOrder(boolean byAmount) {

        int selectedRow = menuTable.getSelectedRow();
        if (selectedRow < 0) {
            JOptionPane.showMessageDialog(this,
                    "Please select a product first.",
                    "No Selection", JOptionPane.WARNING_MESSAGE);
            return;
        }

        FuelProduct selected = menu.get(selectedRow);

        if (selected.getUnit().equals("Tank") && byAmount) {
            JOptionPane.showMessageDialog(this,
                    "LPG tank exchange cannot be ordered by amount.\nPlease use Quantity mode.",
                    "Not Allowed", JOptionPane.WARNING_MESSAGE);
            return;
        }

        double inputValue;
        try {
            inputValue = Double.parseDouble(qtyField.getText().trim());
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this,
                    "Please enter a valid number.",
                    "Invalid Input", JOptionPane.ERROR_MESSAGE);
            qtyField.requestFocus();
            qtyField.selectAll();
            return;
        }

        if (inputValue <= 0) {
            JOptionPane.showMessageDialog(this,
                    "Value must be greater than 0.",
                    "Invalid Input", JOptionPane.ERROR_MESSAGE);
            qtyField.requestFocus();
            qtyField.selectAll();
            return;
        }

        double quantity;

        if (byAmount) {
            double pricePerLiter = selected.getPrice();
            quantity = inputValue / pricePerLiter;
            quantity = Math.round(quantity * 100.0) / 100.0;

            int confirm = JOptionPane.showConfirmDialog(this,
                    String.format(
                            "PHP %.2f worth of %s\n"
                            + "= %.4f liters at PHP %.2f/L\n\n"
                            + "Add to order?",
                            inputValue,
                            selected.getName(),
                            quantity,
                            pricePerLiter),
                    "Confirm Quantity",
                    JOptionPane.YES_NO_OPTION,
                    JOptionPane.INFORMATION_MESSAGE);

            if (confirm != JOptionPane.YES_OPTION) {
                return;
            }

        } else {
            quantity = inputValue;

            if (selected.getUnit().equals("tank") && quantity != Math.floor(quantity)) {
                JOptionPane.showMessageDialog(this,
                        "LPG tank exchange must be a whole number.",
                        "Invalid Quantity", JOptionPane.ERROR_MESSAGE);
                qtyField.requestFocus();
                qtyField.selectAll();
                return;
            }
        }

        order.addItem(selected, quantity);
        refreshOrderTable();
        qtyField.setText(byAmount ? "100.00" : "1.00");
        menuTable.clearSelection();
    }

    private void onRemoveSelected(ActionEvent e) {
        int selectedRow = orderTable.getSelectedRow();
        if (selectedRow < 0) {
            JOptionPane.showMessageDialog(this,
                    "Please select an item from the order to remove.",
                    "No Selection", JOptionPane.WARNING_MESSAGE);
            return;
        }

        String itemName = order.getItems().get(selectedRow).getFuelProduct().getName();
        int confirm = JOptionPane.showConfirmDialog(this,
                "Remove \"" + itemName + "\" from the order?",
                "Confirm Removal", JOptionPane.YES_NO_OPTION);

        if (confirm == JOptionPane.YES_OPTION) {
            order.removeItem(selectedRow);
            refreshOrderTable();
        }
    }

    private void onCancelOrder(ActionEvent e) {
        if (order.isEmpty()) {
            JOptionPane.showMessageDialog(this,
                    "There is no order to cancel.",
                    "Empty Order", JOptionPane.INFORMATION_MESSAGE);
            return;
        }

        int confirm = JOptionPane.showConfirmDialog(this,
                "Are you sure you want to cancel the entire order?",
                "Cancel Order", JOptionPane.YES_NO_OPTION,
                JOptionPane.WARNING_MESSAGE);

        if (confirm == JOptionPane.YES_OPTION) {
            resetForNewTransaction();
            JOptionPane.showMessageDialog(this,
                    "Order has been cancelled.",
                    "Cancelled", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    private void onCheckout(ActionEvent e) {
        if (order.isEmpty()) {
            JOptionPane.showMessageDialog(this,
                    "Cannot checkout. Your order is empty!",
                    "Empty Order", JOptionPane.WARNING_MESSAGE);
            return;
        }

        Discount selectedDiscount = showDiscountDialog();
        if (selectedDiscount == null) {
            return;
        }
        order.setDiscount(selectedDiscount);

        Payment payment = showPaymentDialog();
        if (payment == null) {
            return;
        }

        String receiptText = Receipt.buildReceiptText(order, payment);
        String transactionId = Receipt.getLastTransactionId();
        showReceiptDialog(receiptText, transactionId);

        resetForNewTransaction();
    }

    private Discount showDiscountDialog() {
        JPanel panel = new JPanel(new GridLayout(0, 1, 5, 5));
        panel.setBorder(new EmptyBorder(10, 10, 10, 10));

        JLabel label = new JLabel("Is there any applicable discount?");
        label.setFont(new Font("SansSerif", Font.BOLD, 13));
        panel.add(label);

        ButtonGroup group = new ButtonGroup();

        JRadioButton noneBtn = new JRadioButton("No Discount");
        noneBtn.setFont(new Font("SansSerif", Font.PLAIN, 12));
        noneBtn.setSelected(true); // default selection
        group.add(noneBtn);
        panel.add(noneBtn);

        JRadioButton seniorBtn = new JRadioButton("Senior Citizen (20%)");
        seniorBtn.setFont(new Font("SansSerif", Font.PLAIN, 12));
        group.add(seniorBtn);
        panel.add(seniorBtn);

        JRadioButton pwdBtn = new JRadioButton("PWD (20%)");
        pwdBtn.setFont(new Font("SansSerif", Font.PLAIN, 12));
        group.add(pwdBtn);
        panel.add(pwdBtn);

        int result = JOptionPane.showConfirmDialog(this, panel,
                "Discount Selection", JOptionPane.OK_CANCEL_OPTION,
                JOptionPane.QUESTION_MESSAGE);

        if (result != JOptionPane.OK_OPTION) {
            return null;
        }

        if (seniorBtn.isSelected()) {
            return new Discount(Discount.SENIOR);
        } else if (pwdBtn.isSelected()) {
            return new Discount(Discount.PWD);
        } else {
            return new Discount(Discount.NONE);
        }
    }

    private Payment showPaymentDialog() {
        double totalDue = order.getTotalDue();

        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBorder(new EmptyBorder(10, 10, 10, 10));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        JLabel summaryTitle = new JLabel("ORDER SUMMARY");
        summaryTitle.setFont(new Font("SansSerif", Font.BOLD, 14));
        panel.add(summaryTitle, gbc);

        gbc.gridy = 1;
        gbc.gridwidth = 2;
        JTextArea summaryArea = new JTextArea();
        summaryArea.setEditable(false);
        summaryArea.setFont(new Font("Monospaced", Font.PLAIN, 12));
        summaryArea.setBackground(panel.getBackground());

        StringBuilder summary = new StringBuilder();
        for (OrderItem item : order.getItems()) {
            summary.append(String.format(" %-20s x%-6.2f %s  PHP %8.2f%n",
                    item.getFuelProduct().getName(),
                    item.getQuantity(),
                    item.getFuelProduct().getUnit(),
                    item.getItemTotal()));
        }
        summary.append(" ----------------------------------------\n");
        summary.append(String.format(" Subtotal:                 PHP %8.2f%n",
                order.getSubtotal()));

        if (order.getDiscount().getType() != Discount.NONE) {
            summary.append(String.format(" Discount (%s):       -PHP %8.2f%n",
                    order.getDiscount().getDescription(),
                    order.getDiscountAmount()));
        }

        summary.append(String.format(" VAT (12%%):               PHP %8.2f%n",
                order.getVatAmount()));
        summary.append(" ========================================\n");
        summary.append(String.format(" TOTAL DUE:                PHP %8.2f",
                totalDue));

        summaryArea.setText(summary.toString());
        panel.add(summaryArea, gbc);

        gbc.gridy = 2;
        gbc.gridwidth = 2;
        panel.add(new JSeparator(), gbc);

        gbc.gridy = 3;
        gbc.gridwidth = 1;
        gbc.gridx = 0;
        JLabel typeLabel = new JLabel("Payment Type:");
        typeLabel.setFont(new Font("SansSerif", Font.BOLD, 12));
        panel.add(typeLabel, gbc);

        gbc.gridx = 1;
        JComboBox<String> typeCombo = new JComboBox<>(
                new String[]{"Cash", "Credit Card", "GCash"});
        typeCombo.setFont(new Font("SansSerif", Font.PLAIN, 12));
        panel.add(typeCombo, gbc);

        gbc.gridy = 4;
        gbc.gridx = 0;
        JLabel amountLabel = new JLabel("Amount Tendered:");
        amountLabel.setFont(new Font("SansSerif", Font.BOLD, 12));
        panel.add(amountLabel, gbc);

        gbc.gridx = 1;
        JTextField amountField = new JTextField(
                String.format("%.2f", totalDue), 12);
        amountField.setFont(new Font("SansSerif", Font.PLAIN, 12));
        panel.add(amountField, gbc);

        typeCombo.addActionListener(ev -> {
            boolean isCash = typeCombo.getSelectedIndex() == 0;
            amountField.setEnabled(isCash);
            if (!isCash) {
                amountField.setText(String.format("%.2f", totalDue));
            }
        });

        while (true) {
            int result = JOptionPane.showConfirmDialog(this, panel,
                    "Payment", JOptionPane.OK_CANCEL_OPTION,
                    JOptionPane.PLAIN_MESSAGE);

            if (result != JOptionPane.OK_OPTION) {
                return null;
            }

            int paymentType;
            switch (typeCombo.getSelectedIndex()) {
                case 1:
                    paymentType = Payment.CREDIT_CARD;
                    break;
                case 2:
                    paymentType = Payment.GCASH;
                    break;
                default:
                    paymentType = Payment.CASH;
                    break;
            }

            double amountTendered;
            try {
                amountTendered = Double.parseDouble(amountField.getText().trim());
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this,
                        "Please enter a valid amount.",
                        "Invalid Amount", JOptionPane.ERROR_MESSAGE);
                amountField.requestFocus();
                amountField.selectAll();
                continue;
            }

            if (amountTendered <= 0) {
                JOptionPane.showMessageDialog(this,
                        "Amount must be greater than zero.",
                        "Invalid Amount", JOptionPane.ERROR_MESSAGE);
                amountField.requestFocus();
                amountField.selectAll();
                continue;
            }

            if (paymentType == Payment.CASH && amountTendered < totalDue) {
                JOptionPane.showMessageDialog(this,
                        String.format("Insufficient amount. Need at least PHP %.2f",
                                totalDue),
                        "Insufficient Payment", JOptionPane.ERROR_MESSAGE);
                amountField.requestFocus();
                amountField.selectAll();
                continue;
            }

            return new Payment(paymentType, amountTendered, totalDue);
        }
    }

    private void showReceiptDialog(String receiptText, String transactionId) {
        JDialog dialog = new JDialog(this, "Receipt - " + transactionId, true);
        dialog.setSize(520, 580);
        dialog.setLocationRelativeTo(this);
        dialog.setLayout(new BorderLayout(10, 10));

        JTextArea receiptArea = new JTextArea(receiptText);
        receiptArea.setEditable(false);
        receiptArea.setFont(new Font("Monospaced", Font.PLAIN, 12));
        receiptArea.setMargin(new Insets(10, 10, 10, 10));
        JScrollPane scrollPane = new JScrollPane(receiptArea);
        dialog.add(scrollPane, BorderLayout.CENTER);

        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 8));

        JButton closeBtn = new JButton("Close");
        closeBtn.setFont(new Font("SansSerif", Font.BOLD, 13));
        closeBtn.addActionListener(ev -> dialog.dispose());
        bottomPanel.add(closeBtn);

        dialog.add(bottomPanel, BorderLayout.SOUTH);
        dialog.setVisible(true);
    }

    private void refreshOrderTable() {
        orderTableModel.setRowCount(0);
        for (OrderItem item : order.getItems()) {
            orderTableModel.addRow(new Object[]{
                item.getFuelProduct().getName(),
                String.format("%.2f", item.getQuantity()),
                item.getFuelProduct().getUnit(),
                String.format("%.2f", item.getFuelProduct().getPrice()),
                String.format("%.2f", item.getItemTotal())
            });
        }
        totalLabel.setText(String.format("Running Total: PHP %.2f",
                order.getSubtotal()));
    }

    private void resetForNewTransaction() {
        order.clear();
        refreshOrderTable();
        qtyField.setText("1.00");
        menuTable.clearSelection();
    }
}
