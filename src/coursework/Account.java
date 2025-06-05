package coursework;

class Account extends Customer {

    private Double balance;
    private int accountNumber;

    public Account(String fName, String lName,  int accountNumber,double balance) { 
        super(fName, lName);
        this.accountNumber = accountNumber;
        this.balance = balance;
    }

    public Double getBalance() {
        return balance;
    }

    public int getAccountNum() {
        return accountNumber;
    }

    public void deposit(int amount) {
        this.balance += amount;
    }

    public void withdraw(int amount) {
        this.balance -= amount;
    }
}
