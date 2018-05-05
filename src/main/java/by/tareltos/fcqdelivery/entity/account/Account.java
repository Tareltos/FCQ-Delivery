package by.tareltos.fcqdelivery.entity.account;

import java.util.Objects;
/**
 * The class serves to store objects with properties
 * <b>cardNumber</b>, <b>expirationMonth/b>
 * <b>expirationYear</b>,<b>firstName</b>,
 * <b>lastName</b>,<b> balance</b>,
 * <b>csv</b>.
 *
 * @version 1.0
 * @autor Tarelko Vitali
 */
public class Account {
    /**
     * Property - number of debit/credit card
     */
    private String cardNumber;
    /**
     * Property - expiration month of debit/credit card
     */
    private int expirationMonth;
    /**
     * Property - expiration year of debit/credit card
     */
    private int expirationYear;
    /**
     * Property - first name of owner
     */
    private String firstName;
    /**
     * Property - last name of owner
     */
    private String lastName;
    /**
     * Property - balance in bank account
     */
    private Double balance;
    /**
     * Property - csv of debit/credit card
     */
    private int csv;

    /**
     * Constructor - creating a new object
     */
    public Account() {
    }
    /**
     * Constructor - creating a new object whith parameters
     *
     * @param cardNumber - customer card number
     * @param expirationMonth - card expiration month
     * @param expirationYear - card expiration year
     * @param firstName - owner first name
     * @param lastName - owner last name
     * @param balance - current bank account balance
     * @param csv - private csv code
     */
    public Account(String cardNumber, int expirationMonth, int expirationYear, String firstName, String lastName, Double balance, int csv) {
        this.cardNumber = cardNumber;
        this.expirationMonth = expirationMonth;
        this.expirationYear = expirationYear;
        this.firstName = firstName;
        this.lastName = lastName;
        this.balance = balance;
        this.csv = csv;
    }
    /**
     * Function of obtaining the value of the field {@link Account#cardNumber}
     * @return  number of debit/credit card
     */
    public String getCardNumber() {
        return cardNumber;
    }
    /**
     * CardNumber identification procedure{@link Account#cardNumber}
     * @param cardNumber - customer card number
     */
    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }
    /**
     * Function of obtaining the value of the field {@link Account#expirationMonth}
     * @return   card expiration month
     */
    public int getExpirationMonth() {
        return expirationMonth;
    }
    /**
     * ExpirationMonth identification procedure{@link Account#expirationMonth}
     * @param expirationMonth- card expiration month
     */
    public void setExpirationMonth(int expirationMonth) {
        this.expirationMonth = expirationMonth;
    }
    /**
     * Function of obtaining the value of the field {@link Account#expirationYear}
     * @return   card expiration year
     */
    public int getExpirationYear() {
        return expirationYear;
    }
    /**
     * ExpirationYear identification procedure{@link Account#expirationYear}
     * @param expirationYear- card expiration month
     */
    public void setExpirationYear(int expirationYear) {
        this.expirationYear = expirationYear;
    }
    /**
     * Function of obtaining the value of the field {@link Account#firstName}
     * @return   owner first name
     */
    public String getFirstName() {
        return firstName;
    }
    /**
     * FirstName identification procedure{@link Account#firstName}
     * @param firstName- owner first name
     */
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }
    /**
     * Function of obtaining the value of the field {@link Account#lastName}
     * @return   owner last name
     */
    public String getLastName() {
        return lastName;
    }
    /**
     * LastName identification procedure{@link Account#lastName}
     * @param lastName- owner last name
     */
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
    /**
     * Function of obtaining the value of the field {@link Account#balance}
     * @return   current bank account balance
     */
    public Double getBalance() {
        return balance;
    }
    /**
     * Balance identification procedure{@link Account#balance}
     * @param balance- current bank account balance
     */
    public void setBalance(Double balance) {
        this.balance = balance;
    }
    /**
     * Function of obtaining the value of the field {@link Account#csv}
     * @return   private csv code
     */
    public int getCsv() {
        return csv;
    }
    /**
     * CSV code identification procedure{@link Account#csv}
     * @param csv- private csv code
     */
    public void setCsv(int csv) {
        this.csv = csv;
    }
    /**
     * @see java.lang.Object
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Account account = (Account) o;
        return expirationMonth == account.expirationMonth &&
                expirationYear == account.expirationYear &&
                csv == account.csv &&
                Objects.equals(cardNumber, account.cardNumber) &&
                Objects.equals(firstName, account.firstName) &&
                Objects.equals(lastName, account.lastName) &&
                Objects.equals(balance, account.balance);
    }
    /**
     * @see java.lang.Object
     */
    @Override
    public int hashCode() {

        return Objects.hash(cardNumber, expirationMonth, expirationYear, firstName, lastName, balance, csv);
    }
    /**
     * @see java.lang.Object
     */
    @Override
    public String toString() {
        return "Account{" +
                "cardNumber='" + cardNumber + '\'' +
                ", expirationMonth=" + expirationMonth +
                ", expirationYear=" + expirationYear +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", balance=" + balance +
                ", csv=" + csv +
                '}';
    }
}
