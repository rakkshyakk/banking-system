package coursework;

//import java.util.LinkedList;

public class Main {

    public static void main(String[] args) {
    	
    	 CourseworkGUI.main(args);//Launching the GUI
    	 
////    	Code for the Part 1 of the Assessment
    	
        Account account1 = new Account("Jeffrey", "Ting",1, 2000);
        Account account2 = new Account("Hiran", "Patel",2, 1000);

        System.out.println("Account1 Holder: " + account1.getFirstName() + " " + account1.getLastName());
        System.out.println("Account Number: " + account1.getAccountNum());
        System.out.println("Balance: " + account1.getBalance());

        System.out.println("Account2 Holder: " + account2.getFirstName() + " " + account2.getLastName());
        System.out.println("Account Number: " + account2.getAccountNum());
        System.out.println("Balance: " + account2.getBalance());

        int depositAmount = 250;
        account1.deposit(depositAmount);
        System.out.println("\nDeposited " + depositAmount + " into account1.");
        System.out.println("Balance of Account1 after deposit: " + account1.getBalance());

        int withdrawAmount = 500;
        account2.withdraw(withdrawAmount);
        System.out.println("\nWithdrew " + withdrawAmount + " from account2.");
        System.out.println("Balance of Account2 after withdrawal: " + account2.getBalance());

        Transaction t = new Transaction();
        int transferAmount = 250;
        t.transfer(account1, account2, transferAmount);
        System.out.println("\nTransferred " + transferAmount + " from account1 to account2.");
        System.out.println("Balance of Account1 after transfer: " + account1.getBalance());
        System.out.println("Balance of Account2 after transfer: " + account2.getBalance());

//    	Code for the Part 2 of the Assessment
//    	String file = "csvFiles/Accounts.csv"; 
//        ReadAccounts readAccounts = new ReadAccounts(file);
//
//        LinkedList<String> firstNames = readAccounts.getFirstNames();
//        LinkedList<String> lastNames = readAccounts.getLastNames();
//        LinkedList<Integer> accountList = readAccounts.getAccounts();
//        LinkedList<Double> balanceList = readAccounts.getBalances();
//
//        LinkedList<Account> accounts = new LinkedList<>();
//
//        for (int i = 0; i < firstNames.size(); i++) {
//            accounts.add(new Account(firstNames.get(i), lastNames.get(i), accountList.get(i), balanceList.get(i)));
//        }
//
//        for (Account account : accounts) {
//            System.out.println("Account Holder: " + account.getFirstName() + " " + account.getLastName());
//            System.out.println("Account Number: " + account.getAccountNum());
//            System.out.println("Balance: " + account.getBalance());
//        }
//
//        Transaction t = new Transaction();
//        int transferAmount1 = 250;
//        System.out.println("\nTransferring " + transferAmount1 + " from Account " + accounts.get(0).getAccountNum() + 
//                           " (" + accounts.get(0).getFirstName() + " " + accounts.get(0).getLastName() + ") to Account " + 
//                           accounts.get(1).getAccountNum() + " (" + accounts.get(1).getFirstName() + " " + accounts.get(1).getLastName() + ").");
//        t.transfer(accounts.get(0), accounts.get(1), transferAmount1);
//
//        int transferAmount2 = 500;
//        System.out.println("Transferring " + transferAmount2 + " from Account " + accounts.get(2).getAccountNum() + 
//                           " (" + accounts.get(2).getFirstName() + " " + accounts.get(2).getLastName() + ") to Account " + 
//                           accounts.get(3).getAccountNum() + " (" + accounts.get(3).getFirstName() + " " + accounts.get(3).getLastName() + ").");
//        t.transfer(accounts.get(2), accounts.get(3), transferAmount2);
//
//        System.out.println("\nFinal Balances After Transfers:");
//        for (int i = 0; i < accounts.size(); i++) {
//            System.out.println("Account Number: " + accounts.get(i).getAccountNum() +
//                               " | Account Holder: " + accounts.get(i).getFirstName() + " " + accounts.get(i).getLastName() +
//                               " | Balance: " + accounts.get(i).getBalance());
//   
//    }
    }
    }
    

