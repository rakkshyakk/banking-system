package coursework;

class Customer {

    private String firstName;
    private String lastName;

    public Customer(String fName, String lName) {
        this.firstName = fName;
        this.lastName = lName;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setFirstName(String fName) {
        this.firstName = fName;
    }

    public void setLastName(String lName) {
        this.lastName = lName;
    }
}