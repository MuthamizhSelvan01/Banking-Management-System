import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.text.DecimalFormat;
import java.util.ArrayList;

public class EnhancedBankingSystemGUI extends JFrame {
    private double balance = 0.0;
    private JTextField amountField;
    private JTextArea historyArea;
    private JLabel balanceLabel;
    private ArrayList<String> transactionHistory = new ArrayList<>();
    private DecimalFormat formatter = new DecimalFormat("₹0.00");

    public EnhancedBankingSystemGUI() {
        setTitle("🏦 Smart Banking System");
        setSize(500, 400);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null); // center the frame

        try {
            UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
        } catch (Exception ignored) {}

        // NORTH panel – Input
        JPanel inputPanel = new JPanel(new FlowLayout());
        inputPanel.setBorder(BorderFactory.createTitledBorder("Transaction"));
        inputPanel.add(new JLabel("Amount (₹):"));
        amountField = new JTextField(10);
        inputPanel.add(amountField);

        // CENTER panel – Buttons
        JPanel buttonPanel = new JPanel(new GridLayout(2, 2, 10, 10));
        JButton depositBtn = new JButton("Deposit");
        JButton withdrawBtn = new JButton("Withdraw");
        JButton checkBtn = new JButton("Check Balance");
        JButton resetBtn = new JButton("Reset Account");

        buttonPanel.add(depositBtn);
        buttonPanel.add(withdrawBtn);
        buttonPanel.add(checkBtn);
        buttonPanel.add(resetBtn);
        buttonPanel.setBorder(BorderFactory.createTitledBorder("Actions"));

        // SOUTH panel – History and Balance
        JPanel southPanel = new JPanel(new BorderLayout());
        historyArea = new JTextArea(8, 40);
        historyArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(historyArea);
        scrollPane.setBorder(BorderFactory.createTitledBorder("Transaction History"));

        balanceLabel = new JLabel("Current Balance: ₹0.00", JLabel.CENTER);
        balanceLabel.setFont(new Font("SansSerif", Font.BOLD, 16));
        southPanel.add(scrollPane, BorderLayout.CENTER);
        southPanel.add(balanceLabel, BorderLayout.SOUTH);

        // Add panels to frame
        setLayout(new BorderLayout(10, 10));
        add(inputPanel, BorderLayout.NORTH);
        add(buttonPanel, BorderLayout.CENTER);
        add(southPanel, BorderLayout.SOUTH);

        // Button functionality
        depositBtn.addActionListener(e -> deposit());
        withdrawBtn.addActionListener(e -> withdraw());
        checkBtn.addActionListener(e -> checkBalance());
        resetBtn.addActionListener(e -> resetAccount());

        setVisible(true);
    }

    private void deposit() {
        String input = amountField.getText();
        try {
            double amount = Double.parseDouble(input);
            if (amount <= 0) {
                showMessage("Enter a positive amount.");
                return;
            }
            balance += amount;
            String record = "Deposited: " + formatter.format(amount);
            transactionHistory.add(record);
            updateHistory(record);
        } catch (NumberFormatException e) {
            showMessage("Invalid input. Please enter a valid number.");
        }
        amountField.setText("");
    }

    private void withdraw() {
        String input = amountField.getText();
        try {
            double amount = Double.parseDouble(input);
            if (amount <= 0) {
                showMessage("Enter a positive amount.");
            } else if (amount > balance) {
                showMessage("Insufficient funds.");
            } else {
                balance -= amount;
                String record = "Withdrawn: " + formatter.format(amount);
                transactionHistory.add(record);
                updateHistory(record);
            }
        } catch (NumberFormatException e) {
            showMessage("Invalid input. Please enter a valid number.");
        }
        amountField.setText("");
    }

    private void checkBalance() {
        String msg = "Current Balance: " + formatter.format(balance);
        updateHistory(msg);
    }

    private void resetAccount() {
        int confirm = JOptionPane.showConfirmDialog(this,
                "Are you sure you want to reset your account?",
                "Confirm Reset", JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            balance = 0.0;
            transactionHistory.clear();
            historyArea.setText("");
            balanceLabel.setText("Current Balance: ₹0.00");
            showMessage("Account has been reset.");
        }
    }

    private void updateHistory(String entry) {
        balanceLabel.setText("Current Balance: " + formatter.format(balance));
        historyArea.append(entry + "\n");
    }

    private void showMessage(String msg) {
        JOptionPane.showMessageDialog(this, msg, "Notice", JOptionPane.INFORMATION_MESSAGE);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(EnhancedBankingSystemGUI::new);
    }
}
