import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.swing.*;

public class MobileBankingApp extends JFrame implements ActionListener {
    // GUI components
    private JLabel titleLabel = new JLabel("Mobile Banking App");
    private JLabel nameLabel = new JLabel("Name:");
    private JTextField nameTextField = new JTextField();
    private JLabel accountLabel = new JLabel("Account Number:");
    private JTextField accountTextField = new JTextField();
    private JLabel balanceLabel = new JLabel("Balance:");
    private JTextField balanceTextField = new JTextField();
    private JButton createAccountButton = new JButton("Create Account");
    private JLabel depositLabel = new JLabel("Deposit Amount:");
    private JTextField depositTextField = new JTextField();
    private JButton depositButton = new JButton("Deposit");
    private JLabel withdrawLabel = new JLabel("Withdraw Amount:");
    private JTextField withdrawTextField = new JTextField();
    private JButton withdrawButton = new JButton("Withdraw");
    private JButton viewBalanceButton = new JButton("View Balance"); // Added button
    private JTextArea transactionsTextArea = new JTextArea("No transactions yet.");

    // MobileBankingApp properties
    private String name;
    private String accountNumber;
    private double balance;
    private ArrayList<Transaction> transactionList = new ArrayList<Transaction>();

    public MobileBankingApp() {
        // Set up GUI components
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(9, 2));
        panel.add(titleLabel);
        panel.add(new JLabel());
        panel.add(nameLabel);
        panel.add(nameTextField);
        panel.add(accountLabel);
        panel.add(accountTextField);
        panel.add(balanceLabel);
        panel.add(balanceTextField);
        panel.add(new JLabel());
        panel.add(createAccountButton);
        panel.add(depositLabel);
        panel.add(depositTextField);
        panel.add(new JLabel());
        panel.add(depositButton);
        panel.add(withdrawLabel);
        panel.add(withdrawTextField);
        panel.add(new JLabel());
        panel.add(withdrawButton);
        panel.add(viewBalanceButton); // Added button
        panel.add(new JScrollPane(transactionsTextArea));
        add(panel);

        // Add action listeners to buttons
        createAccountButton.addActionListener(this);
        depositButton.addActionListener(this);
        withdrawButton.addActionListener(this);
        viewBalanceButton.addActionListener(this); // Added button

        // Set frame properties
        setTitle("Mobile Banking App");
        setSize(500, 500);
        setVisible(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == createAccountButton) {
            // Validate input and create new account if input is valid
            String namePattern = "[a-zA-Z]+";
            String accountPattern = "\\d{10}";
            String balancePattern = "\\d+(\\.\\d+)?";
            Pattern pattern = Pattern.compile(namePattern);
            Matcher nameMatcher = pattern.matcher(nameTextField.getText());
            Matcher accountMatcher = Pattern.compile(accountPattern).matcher(accountTextField.getText());
            Matcher balanceMatcher = Pattern.compile(balancePattern).matcher(balanceTextField.getText());
            if (!nameMatcher.matches()) {
                JOptionPane.showMessageDialog(this, "Invalid name. Please enter only alphabetical characters.");
            } else if (!accountMatcher.matches()) {
                JOptionPane.showMessageDialog(this, "Invalid account number. Please enter 10 digits only.");
            } else if (!balanceMatcher.matches()) {
                JOptionPane.showMessageDialog(this, "Invalid balance. Please enter a valid decimal number.");
            } else {
                // Create new account if input is valid
                name = nameTextField.getText();
                accountNumber = accountTextField.getText();
                balance = Double.parseDouble(balanceTextField.getText());
                JOptionPane.showMessageDialog(this, "Account has been created successfully!");
            }
        } else if (e.getSource() == depositButton) {
            // Deposit entered amount into account balance and add a new transaction
            double amount = Double.parseDouble(depositTextField.getText());
            balance += amount;
            Transaction transaction = new Transaction("Deposit", amount);
            transactionList.add(transaction);
            updateTransactionHistory();
            JOptionPane.showMessageDialog(this, "Deposit of " + amount + " has been made successfully!");
        } else if (e.getSource() == withdrawButton) {
            // Withdraw entered amount from account balance if funds are available and add a new transaction
            double amount = Double.parseDouble(withdrawTextField.getText());
            if (balance >= amount) {
                balance -= amount;
                Transaction transaction = new Transaction("Withdrawal", amount);
                transactionList.add(transaction);
                updateTransactionHistory();
                JOptionPane.showMessageDialog(this, "Withdrawal of " + amount + " has been made successfully!");
            } else {
                JOptionPane.showMessageDialog(this, "Insufficient funds.");
            }
        } else if (e.getSource() == viewBalanceButton) {
            // Display the current account balance
            JOptionPane.showMessageDialog(this, "Current balance: " + balance);
        }
    }

    private void updateTransactionHistory() {
        transactionsTextArea.setText(""); // Clear existing text
        for (Transaction transaction : transactionList) {
            String text = transaction.getType() + ": " + transaction.getAmount();
            transactionsTextArea.append(text + "\n");
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new MobileBankingApp();
            }
        });
    }
}

