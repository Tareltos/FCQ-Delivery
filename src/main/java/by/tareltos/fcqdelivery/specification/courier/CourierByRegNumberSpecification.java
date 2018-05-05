package by.tareltos.fcqdelivery.specification.courier;

import by.tareltos.fcqdelivery.specification.SqlSpecification;
/**
 * The class is used to create and return sql query
 *
 * @autor Tarelko Vitali
 * @see by.tareltos.fcqdelivery.specification.SqlSpecification
 */
public class CourierByRegNumberSpecification implements SqlSpecification {
    /**Parameter stores an query to the database */
    private String query = "SELECT * FROM courier WHERE car_number = \"%s\" ";
   /**Parameter that will be added in query like car_number*/
    private String carNumber;
    /**
     * Constructor for creating a new object with certain parameters
     * @param carNumber - primary key of courier in database
     *
     */
    public CourierByRegNumberSpecification(String carNumber) {
        this.carNumber = carNumber;
    }
    /**
     * @see by.tareltos.fcqdelivery.specification.SqlSpecification
     */
    @Override
    public String toSqlClauses() {
        return String.format(query, carNumber);
    }
}
