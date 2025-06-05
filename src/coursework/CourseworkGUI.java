package coursework;

import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Color;
import javax.swing.JTextField;
import javax.swing.JOptionPane;
import java.util.LinkedList;
import javax.swing.JTextArea;
import java.io.FileWriter;
import java.io.IOException;

public class CourseworkGUI extends JFrame {

    private static final long serialVersionUID = 1L;
    private JPanel contentPane;
    private JTextField accDeposit;
    private JTextField accWithdraw;
    private JTextField acc1Transfer;
    private JTextField acc2Transfer;
    private JTextField depositInput;
    private JTextField withdrawInput;
    private JTextField transferAmount;
    private JTextArea details;

    public void writeAccountsToCSV(String fileName, LinkedList<String> firstNames, LinkedList<String> lastNames, LinkedList<Integer> accountNumbers, LinkedList<Double> balances) {
        try (FileWriter writer = new FileWriter(fileName)) {
            for (int i = 0; i < firstNames.size(); i++) {
                writer.write(firstNames.get(i) + "," + lastNames.get(i) + "," + accountNumbers.get(i) + "," + String.format("%.2f", balances.get(i)) + "\n");
            }
            System.out.println("Data written to " + fileName);
        } catch (IOException e) {
            System.out.println("An error occurred while writing to the file.");
            e.printStackTrace();
        }
    }

    private void updateAccountBalance(String accountNumber, double amount, boolean isDeposit) {
        try {
            String filePath = "csvFiles/Accounts.csv";
            ReadAccounts readAccounts = new ReadAccounts(filePath);

            LinkedList<String> firstNames = readAccounts.getFirstNames();
            LinkedList<String> lastNames = readAccounts.getLastNames();
            LinkedList<Integer> accountNumbers = readAccounts.getAccounts();
            LinkedList<Double> balances = readAccounts.getBalances();	

            boolean accountFound = false;

            for (int i = 0; i < accountNumbers.size(); i++) {
                if (accountNumbers.get(i) == Integer.parseInt(accountNumber)) {
                    double currentBalance = balances.get(i);
                    accountFound = true;

                    if (isDeposit) {
                        balances.set(i, currentBalance + amount);
                    } else {
                        if (currentBalance >= amount) {
                            balances.set(i, currentBalance - amount);
                        } else {
                            JOptionPane.showMessageDialog(null, "Insufficient funds.", "Error", JOptionPane.ERROR_MESSAGE);
                            return;
                        }
                    }

                    writeAccountsToCSV(filePath, firstNames, lastNames, accountNumbers, balances);
                    JOptionPane.showMessageDialog(null, (isDeposit ? "Deposit" : "Withdrawal") + " successful! New balance: Rs." + String.format("%.2f", balances.get(i)), "Success", JOptionPane.INFORMATION_MESSAGE);
                    break;
                }
            }

            if (!accountFound) {
                JOptionPane.showMessageDialog(null, "Account not found. Please check the account number.", "Error", JOptionPane.ERROR_MESSAGE);
            }

        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(null, "Please enter a valid account number.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void displayAllAccounts() {
        try {
            String filePath = "csvFiles/Accounts.csv";
            ReadAccounts readAccounts = new ReadAccounts(filePath);

            LinkedList<String> firstNames = readAccounts.getFirstNames();
            LinkedList<String> lastNames = readAccounts.getLastNames();
            LinkedList<Integer> accountNumbers = readAccounts.getAccounts();
            LinkedList<Double> balances = readAccounts.getBalances();

            StringBuilder allDetails = new StringBuilder("Account Details:\n\n");
            for (int i = 0; i < firstNames.size(); i++) {
                allDetails.append("Name: ").append(firstNames.get(i)).append(" ").append(lastNames.get(i))
                        .append("\nAccount Number: ").append(accountNumbers.get(i))
                        .append("\nBalance: Rs.").append(String.format("%.2f", balances.get(i))).append("\n\n");
            }

            details.setText(allDetails.toString());

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "An error occurred while reading the accounts.", "Error", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    CourseworkGUI frame = new CourseworkGUI();
                    frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public CourseworkGUI() {
        setTitle("Rakshyak's Banking System");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 772, 693);
        contentPane = new JPanel();
        contentPane.setBackground(new Color(192, 192, 192));
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null);

        JLabel lblAccdeposit = new JLabel("Deposit Money");
        lblAccdeposit.setFont(new Font("Tahoma", Font.PLAIN, 22));
        lblAccdeposit.setBounds(20, 67, 173, 24);
        contentPane.add(lblAccdeposit);

        JButton depositButton = new JButton("Deposit");
        depositButton.setBackground(new Color(0, 255, 0));
        depositButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    String account = accDeposit.getText();
                    String amountStr = depositInput.getText();

                    if (account.isEmpty() || amountStr.isEmpty()) {
                        JOptionPane.showMessageDialog(null, "Please enter both account number and amount.", "Error", JOptionPane.ERROR_MESSAGE);
                        return;
                    }

                    double amount = Double.parseDouble(amountStr);
                    if (amount < 0) {
                        JOptionPane.showMessageDialog(null, "Amount cannot be negative.", "Error", JOptionPane.ERROR_MESSAGE);
                        return;
                    }
                    updateAccountBalance(account, amount, true);

                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(null, "Please enter a valid number for the amount.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        depositButton.setForeground(new Color(0, 0, 0));
        depositButton.setFont(new Font("Tahoma", Font.PLAIN, 18));
        depositButton.setBounds(91, 193, 121, 29);
        contentPane.add(depositButton);

        JButton transferButton = new JButton("Transfer");
        transferButton.setBackground(new Color(0, 255, 0));
        transferButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    String filePath = "csvFiles/Accounts.csv";
                    ReadAccounts readAccounts = new ReadAccounts(filePath);

                    LinkedList<String> firstNames = readAccounts.getFirstNames();
                    LinkedList<String> lastNames = readAccounts.getLastNames();
                    LinkedList<Integer> accountNumbers = readAccounts.getAccounts();
                    LinkedList<Double> balances = readAccounts.getBalances();

                    String account1 = acc1Transfer.getText();
                    String account2 = acc2Transfer.getText();
                    String amountStr = transferAmount.getText().trim(); 

                    if (account1.isEmpty() || account2.isEmpty() || amountStr.isEmpty()) {
                        JOptionPane.showMessageDialog(null, "Please enter both account numbers and the amount.", "Error", JOptionPane.ERROR_MESSAGE);
                        return;
                    }

                    double amount = Double.parseDouble(amountStr); 
                    if (amount < 0) {
                        JOptionPane.showMessageDialog(null, "Amount cannot be negative.", "Error", JOptionPane.ERROR_MESSAGE);
                        return;
                    }
                    boolean account1Found = false;
                    boolean account2Found = false;

                    double balance1 = 0;
                    double balance2 = 0;

                    for (int i = 0; i < accountNumbers.size(); i++) {
                        if (accountNumbers.get(i) == Integer.parseInt(account1)) {
                            balance1 = balances.get(i);
                            account1Found = true;
                        }
                        if (accountNumbers.get(i) == Integer.parseInt(account2)) {
                            balance2 = balances.get(i);
                            account2Found = true;
                        }
                    }
                    if(account1.equals(account2)){
                    	JOptionPane.showMessageDialog(null, "Trasfer cannot be done between same accounts.", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                    

                    else if (account1Found && account2Found) {
                        if (balance1 >= amount) {
                            balances.set(accountNumbers.indexOf(Integer.parseInt(account1)), balance1 - amount); // Update balance of account1
                            balances.set(accountNumbers.indexOf(Integer.parseInt(account2)), balance2 + amount); // Update balance of account2
                            writeAccountsToCSV(filePath, firstNames, lastNames, accountNumbers, balances);
                            JOptionPane.showMessageDialog(null, "Transfer successful!", "Success", JOptionPane.INFORMATION_MESSAGE);
                        } else {
                            JOptionPane.showMessageDialog(null, "Insufficient funds in Account 1.", "Error", JOptionPane.ERROR_MESSAGE);
                        }
                    } else {
                        JOptionPane.showMessageDialog(null, "One or both account numbers not found.", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(null, "Please enter a valid account number.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        transferButton.setForeground(new Color(0, 0, 0));
        transferButton.setFont(new Font("Tahoma", Font.PLAIN, 18));
        transferButton.setBounds(91, 608, 121, 29);
        contentPane.add(transferButton);

        JLabel lblAccwithdraw = new JLabel("Withdraw Money");
        lblAccwithdraw.setFont(new Font("Tahoma", Font.PLAIN, 22));
        lblAccwithdraw.setBounds(20, 258, 173, 24);
        contentPane.add(lblAccwithdraw);

        JButton withdrawButton = new JButton("Withdraw");
        withdrawButton.setBackground(new Color(0, 255, 0));
        withdrawButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    String account = accWithdraw.getText();
                    String amountStr = withdrawInput.getText();

                    if (account.isEmpty() || amountStr.isEmpty()) {
                        JOptionPane.showMessageDialog(null, "Please enter both account number and amount.", "Error", JOptionPane.ERROR_MESSAGE);
                        return;
                    }

                    double amount = Double.parseDouble(amountStr);
                    if (amount < 0) {
                        JOptionPane.showMessageDialog(null, "Amount cannot be negative.", "Error", JOptionPane.ERROR_MESSAGE);
                        return;
                    }
                    updateAccountBalance(account, amount, false);

                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(null, "Please enter a valid number for the amount.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        withdrawButton.setForeground(new Color(0, 0, 0));
        withdrawButton.setFont(new Font("Tahoma", Font.PLAIN, 18));
        withdrawButton.setBounds(91, 383, 121, 29);
        contentPane.add(withdrawButton);

        JLabel lblAcc1transfer = new JLabel("From (Acc No.):");
        lblAcc1transfer.setFont(new Font("Tahoma", Font.PLAIN, 16));
        lblAcc1transfer.setBounds(48, 488, 127, 14);
        contentPane.add(lblAcc1transfer);

        JLabel lblAcc2transfer = new JLabel("To (Acc No.):");
        lblAcc2transfer.setFont(new Font("Tahoma", Font.PLAIN, 16));
        lblAcc2transfer.setBounds(69, 531, 135, 14);
        contentPane.add(lblAcc2transfer);

        acc1Transfer = new JTextField();
        acc1Transfer.setBounds(176, 483, 193, 29);
        contentPane.add(acc1Transfer);
        acc1Transfer.setColumns(10);

        acc2Transfer = new JTextField();
        acc2Transfer.setColumns(10);
        acc2Transfer.setBounds(176, 526, 193, 29);
        contentPane.add(acc2Transfer);

        transferAmount = new JTextField();
        transferAmount.setBounds(176, 566, 193, 29);
        contentPane.add(transferAmount);
        transferAmount.setColumns(10);

        JLabel lblTransferAmount = new JLabel("Amount:");
        lblTransferAmount.setFont(new Font("Tahoma", Font.PLAIN, 16));
        lblTransferAmount.setBounds(91, 561, 109, 24);
        contentPane.add(lblTransferAmount);

        JLabel lblDepositacc = new JLabel("Account Number:");
        lblDepositacc.setFont(new Font("Tahoma", Font.PLAIN, 16));
        lblDepositacc.setBounds(36, 119, 146, 14);
        contentPane.add(lblDepositacc);

        JLabel lblDepositamount = new JLabel("Amount:");
        lblDepositamount.setFont(new Font("Tahoma", Font.PLAIN, 16));
        lblDepositamount.setBounds(99, 159, 146, 14);
        contentPane.add(lblDepositamount);

        accDeposit = new JTextField();
        accDeposit.setBounds(176, 114, 193, 29);
        contentPane.add(accDeposit);
        accDeposit.setColumns(10);

        depositInput = new JTextField();
        depositInput.setBounds(176, 153, 193, 29);
        contentPane.add(depositInput);
        depositInput.setColumns(10);

        JLabel lblWithdrawacc = new JLabel("Account Number:");
        lblWithdrawacc.setFont(new Font("Tahoma", Font.PLAIN, 16));
        lblWithdrawacc.setBounds(37, 310, 146, 14);
        contentPane.add(lblWithdrawacc);

        JLabel lblWithdrawamount = new JLabel("Amount:");
        lblWithdrawamount.setFont(new Font("Tahoma", Font.PLAIN, 16));
        lblWithdrawamount.setBounds(99, 348, 146, 14);
        contentPane.add(lblWithdrawamount);

        accWithdraw = new JTextField();
        accWithdraw.setBounds(175, 308, 193, 29);
        contentPane.add(accWithdraw);
        accWithdraw.setColumns(10);

        withdrawInput = new JTextField();
        withdrawInput.setBounds(176, 348, 193, 29);
        contentPane.add(withdrawInput);
        withdrawInput.setColumns(10);

        JButton showAll = new JButton("Show Details");
        showAll.setBackground(new Color(0, 255, 0));
        showAll.setForeground(new Color(0, 0, 0));
        showAll.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                displayAllAccounts();
            }
        });
        showAll.setFont(new Font("Tahoma", Font.PLAIN, 18));
        showAll.setBounds(544, 503, 146, 29);
        contentPane.add(showAll);

        details = new JTextArea();
        details.setLineWrap(true);
        details.setWrapStyleWord(true);
        details.setTabSize(10);
        details.setBounds(485, 135, 332, 361);
        contentPane.add(details);
        
        JLabel lblNewLabel = new JLabel("Banking System");
        lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 23));
        lblNewLabel.setBounds(341, -4, 193, 49);
        contentPane.add(lblNewLabel);
        
        JLabel lblTransferMoney = new JLabel("Transfer Money");
        lblTransferMoney.setFont(new Font("Tahoma", Font.PLAIN, 22));
        lblTransferMoney.setBounds(20, 450, 173, 24);
        contentPane.add(lblTransferMoney);
    }
}
