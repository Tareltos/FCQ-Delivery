package by.tareltos.fcqdelivery.entity.courier;

import java.util.Objects;
/**
 * The class serves to store objects with properties
 * <b>carNumber</b>, <b>carProducer/b>
 * <b>carModel</b>,<b>driverPhone</b>,
 * <b>driverName</b>,<b>driverEmail</b>,
 * <b>maxCargo</b>,<b>kmTax</b>,
 * <b>imageFileName</b>,<b>status</b>.
 *
 * @version 1.0
 * @autor Tarelko Vitali
 */
public class Courier {

    private String carNumber;
    private String carProducer;
    private String carModel;
    private String driverPhone;
    private String driverName;
    private String driverEmail;
    private int maxCargo;
    private double kmTax;
    private String imageFileName;
    private CourierStatus status;


    public Courier() {
    }

    public Courier(String carNumber, String carProducer, String carModel, String driverPhone, String driverName, String driverEmail, int maxCargo, double kmTax, String image, CourierStatus status) {
        this.carNumber = carNumber;
        this.carProducer = carProducer;
        this.carModel = carModel;
        this.driverPhone = driverPhone;
        this.driverName = driverName;
        this.driverEmail = driverEmail;
        this.maxCargo = maxCargo;
        this.kmTax = kmTax;
        this.imageFileName = image;
        this.status = status;
    }

    public String getCarNumber() {
        return carNumber;
    }

    public void setCarNumber(String carNumber) {
        this.carNumber = carNumber;
    }

    public String getCarProducer() {
        return carProducer;
    }

    public void setCarProducer(String carProducer) {
        this.carProducer = carProducer;
    }

    public String getCarModel() {
        return carModel;
    }

    public void setCarModel(String carModel) {
        this.carModel = carModel;
    }

    public String getDriverPhone() {
        return driverPhone;
    }

    public void setDriverPhone(String driverPhone) {
        this.driverPhone = driverPhone;
    }

    public String getDriverName() {
        return driverName;
    }

    public void setDriverName(String driverName) {
        this.driverName = driverName;
    }

    public String getDriverEmail() {
        return driverEmail;
    }

    public void setDriverEmail(String driverEmail) {
        this.driverEmail = driverEmail;
    }

    public int getMaxCargo() {
        return maxCargo;
    }

    public void setMaxCargo(int maxCargo) {
        this.maxCargo = maxCargo;
    }

    public double getKmTax() {
        return kmTax;
    }

    public void setKmTax(double kmTax) {
        this.kmTax = kmTax;
    }

    public String getImageFileName() {
        return imageFileName;
    }

    public void setImageFileName(String imageFileName) {
        this.imageFileName = imageFileName;
    }

    public CourierStatus getStatus() {
        return status;
    }

    public void setStatus(CourierStatus status) {
        this.status = status;
    }
    /**
     * @see java.lang.Object
     */
    @Override
    public boolean equals(Object o) {

        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Courier courier = (Courier) o;
        return maxCargo == courier.maxCargo &&
                Double.compare(courier.kmTax, kmTax) == 0 &&
                Objects.equals(carNumber, courier.carNumber) &&
                Objects.equals(carProducer, courier.carProducer) &&
                Objects.equals(carModel, courier.carModel) &&
                Objects.equals(driverPhone, courier.driverPhone) &&
                Objects.equals(driverName, courier.driverName) &&
                Objects.equals(driverEmail, courier.driverEmail) &&
                Objects.equals(imageFileName, courier.imageFileName) &&
                status == courier.status;
    }
    /**
     * @see java.lang.Object
     */
    @Override
    public int hashCode() {

        return Objects.hash(carNumber, carProducer, carModel, driverPhone, driverName, driverEmail, maxCargo, kmTax, imageFileName, status);
    }
    /**
     * @see java.lang.Object
     */
    @Override
    public String toString() {
        return "Courier{" +
                "carNumber='" + carNumber + '\'' +
                ", carProducer='" + carProducer + '\'' +
                ", carModel='" + carModel + '\'' +
                ", driverPhone='" + driverPhone + '\'' +
                ", driverName='" + driverName + '\'' +
                ", driverEmail='" + driverEmail + '\'' +
                ", maxCargo=" + maxCargo +
                ", kmTax=" + kmTax +
                ", imageFileName='" + imageFileName + '\'' +
                ", status=" + status +
                '}';
    }
}
