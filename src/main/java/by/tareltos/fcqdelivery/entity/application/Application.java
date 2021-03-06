package by.tareltos.fcqdelivery.entity.application;

import by.tareltos.fcqdelivery.entity.courier.Courier;
import by.tareltos.fcqdelivery.entity.user.User;

import java.util.Objects;
/**
 * The class serves to store objects with properties
 * <b>id</b>, <b>owner/b>
 * <b>startPoint</b>,<b>finishPoint</b>,
 * <b>deliveryDate</b>,<b>cargo</b>,
 * <b>comment</b>,<b>price</b>,
 * <b>status</b>,<b>cancelationReason</b>.
 *
 * @version 1.0
 * @autor Tarelko Vitali
 */
public class Application {

    private int id;
    private User owner;
    private String startPoint;
    private String finishPoint;
    private String deliveryDate;
    private int cargo;
    private String comment;
    private Courier courier;
    private double price;
    private ApplicationStatus status;
    private String cancelationReason;

    public Application() {
    }

    public Application(int id, User owner, String startPoint, String finishPoint, String deliveryDate, int cargo, String comment, Courier courier, double price, ApplicationStatus status, String cancelationReason) {
        this.id = id;
        this.owner = owner;
        this.startPoint = startPoint;
        this.finishPoint = finishPoint;
        this.deliveryDate = deliveryDate;
        this.cargo = cargo;
        this.comment = comment;
        this.courier = courier;
        this.price = price;
        this.status = status;
        this.cancelationReason = cancelationReason;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public User getOwner() {
        return owner;
    }

    public void setOwner(User owner) {
        this.owner = owner;
    }

    public String getStartPoint() {
        return startPoint;
    }

    public void setStartPoint(String startPoint) {
        this.startPoint = startPoint;
    }

    public String getFinishPoint() {
        return finishPoint;
    }

    public void setFinishPoint(String finishPoint) {
        this.finishPoint = finishPoint;
    }

    public int getCargo() {
        return cargo;
    }

    public void setCargo(int cargo) {
        this.cargo = cargo;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public Courier getCourier() {
        return courier;
    }

    public void setCourier(Courier courier) {
        this.courier = courier;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public ApplicationStatus getStatus() {
        return status;
    }

    public void setStatus(ApplicationStatus status) {
        this.status = status;
    }

    public String getDeliveryDate() {
        return deliveryDate;
    }

    public void setDeliveryDate(String deliveryDate) {
        this.deliveryDate = deliveryDate;
    }

    public String getCancelationReason() {
        return cancelationReason;
    }

    public void setCancelationReason(String cancelationReason) {
        this.cancelationReason = cancelationReason;
    }
    /**
     * @see java.lang.Object
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Application that = (Application) o;
        return id == that.id &&
                cargo == that.cargo &&
                Double.compare(that.price, price) == 0 &&
                Objects.equals(owner, that.owner) &&
                Objects.equals(startPoint, that.startPoint) &&
                Objects.equals(finishPoint, that.finishPoint) &&
                Objects.equals(deliveryDate, that.deliveryDate) &&
                Objects.equals(comment, that.comment) &&
                Objects.equals(courier, that.courier) &&
                status == that.status &&
                Objects.equals(cancelationReason, that.cancelationReason);
    }
    /**
     * @see java.lang.Object
     */
    @Override
    public int hashCode() {

        return Objects.hash(id, owner, startPoint, finishPoint, deliveryDate, cargo, comment, courier, price, status, cancelationReason);
    }
    /**
     * @see java.lang.Object
     */
    @Override
    public String toString() {
        return "Application{" +
                "id=" + id +
                ", owner=" + owner +
                ", startPoint='" + startPoint + '\'' +
                ", finishPoint='" + finishPoint + '\'' +
                ", deliveryDate='" + deliveryDate + '\'' +
                ", cargo=" + cargo +
                ", comment='" + comment + '\'' +
                ", courier=" + courier +
                ", price=" + price +
                ", status=" + status +
                ", cancelationReason='" + cancelationReason + '\'' +
                '}';
    }
}
