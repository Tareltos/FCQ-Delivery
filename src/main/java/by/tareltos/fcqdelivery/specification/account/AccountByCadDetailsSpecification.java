package by.tareltos.fcqdelivery.specification.account;

import by.tareltos.fcqdelivery.specification.SqlSpecification;

public class AccountByCadDetailsSpecification implements SqlSpecification {

    private String query = "SELECT * FROM account WHERE card_number = \"%s\" and expiration_month =  %s and expiration_year =  %s and first_name = \"%s\" and last_name = \"%s\" and csv =  %s  ";
    private String cardNumber;
    private String expMonth;
    private String expYear;
    private String fName;
    private String lName;
    private String csv;


    public AccountByCadDetailsSpecification(String cardNumber, String expMonth, String expYear, String fName, String lName, String csv) {
        this.cardNumber = cardNumber;
        this.csv = csv;
        this.expMonth = expMonth;
        this.expYear = expYear;
        this.fName = fName;
        this.lName = lName;
    }

    @Override
    public String toSqlClauses() {
        return String.format(query, cardNumber, expMonth, expYear, fName, lName, csv);
    }
}
