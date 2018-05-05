package by.tareltos.fcqdelivery.specification.account;

import by.tareltos.fcqdelivery.specification.SqlSpecification;

/**
 * The class is used to create and return sql query
 *
 * @autor Tarelko Vitali
 * @see by.tareltos.fcqdelivery.specification.SqlSpecification
 */
public class AccountByCadDetailsSpecification implements SqlSpecification {
    /**Parameter stores an query to the database */
    private String query = "SELECT * FROM account WHERE card_number = \"%s\" and expiration_month =  %s and expiration_year =  %s and first_name = \"%s\" and last_name = \"%s\" and csv =  %s  ";
    /**Parameter that will be added in query like card_number*/
    private String cardNumber;
    /**Parameter that will be added in query like expiration_month*/
    private String expMonth;
    /**Parameter that will be added in query like expiration_year*/
    private String expYear;
    /**Parameter that will be added in query like first_name*/
    private String fName;
    /**Parameter that will be added in query like last_name*/
    private String lName;
    /**Parameter that will be added in query like csv*/
    private String csv;

    /**
     * Constructor for creating a new object with certain parameters
     * @param cardNumber - number of credit/debit card
     * @param expMonth - expiration month of card
     * @param expYear - expiration year of card
     * @param fName - first name of owner
     * @param lName - last name of owner
     * @param csv - csv code of card
     *
     */
    public AccountByCadDetailsSpecification(String cardNumber, String expMonth, String expYear, String fName, String lName, String csv) {
        this.cardNumber = cardNumber;
        this.csv = csv;
        this.expMonth = expMonth;
        this.expYear = expYear;
        this.fName = fName;
        this.lName = lName;
    }

    /**
     * @see by.tareltos.fcqdelivery.specification.SqlSpecification
     */
    @Override
    public String toSqlClauses() {
        return String.format(query, cardNumber, expMonth, expYear, fName, lName, csv);
    }
}
