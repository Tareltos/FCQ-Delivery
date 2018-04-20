package by.tareltos.fcqdelivery.specification.courier;

import by.tareltos.fcqdelivery.specification.SqlSpecification;

public class CourierByRegNumberSpecification implements SqlSpecification {

    private String query = "SELECT * FROM courier WHERE car_number = \"%s\" ";
    private String carNumber;

    public CourierByRegNumberSpecification(String carNumber) {
        this.carNumber = carNumber;
    }

    @Override
    public String toSqlClauses() {
        return String.format(query, carNumber);
    }
}
