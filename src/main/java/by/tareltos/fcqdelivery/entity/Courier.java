package by.tareltos.fcqdelivery.entity;

import java.util.Arrays;
import java.util.Objects;

/**
 * Task 1 Chapter A
 * Created by Vitali Tarelko on 26.03.2018.
 * tareltos@gmail.com; skype: tareltos
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
    private byte[] image;
    private CourierStatus status;


    public Courier() {
    }

    public Courier(String carNumber, String carProducer, String carModel, String driverPhone, String driverName, String driverEmail, int maxCargo, double kmTax, byte[] image, CourierStatus status) {
        this.carNumber = carNumber;
        this.carProducer = carProducer;
        this.carModel = carModel;
        this.driverPhone = driverPhone;
        this.driverName = driverName;
        this.driverEmail = driverEmail;
        this.maxCargo = maxCargo;
        this.kmTax = kmTax;
        this.image = image;
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

    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }

    public CourierStatus getStatus() {
        return status;
    }

    public void setStatus(CourierStatus status) {
        this.status = status;
    }

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
                Arrays.equals(image, courier.image) &&
                status == courier.status;
    }

    @Override
    public int hashCode() {

        int result = Objects.hash(carNumber, carProducer, carModel, driverPhone, driverName, driverEmail, maxCargo, kmTax, status);
        result = 31 * result + Arrays.hashCode(image);
        return result;
    }

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
                ", status=" + status +
                '}';
    }
}
