package by.tareltos.fcqdelivery.entity.application;

import by.tareltos.fcqdelivery.entity.courier.Courier;
import by.tareltos.fcqdelivery.entity.user.User;

import java.util.Objects;

public class Application {

    private int id;
    private User owner;
    private String startPoint;
    private String finishPoint;
    private int cargo;
    private String comment;
    private Courier courier;
    private double price;
    private ApplicationStatus status;

    public Application() {
    }

    public Application(int id, User owner, String startPoint, String finishPoint, int cargo, String comment, Courier courier, double price, ApplicationStatus status) {
        this.id = id;
        this.owner = owner;
        this.startPoint = startPoint;
        this.finishPoint = finishPoint;
        this.cargo = cargo;
        this.comment = comment;
        this.courier = courier;
        this.price = price;
        this.status = status;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Application that = (Application) o;
        return id == that.id &&
                Double.compare(that.cargo, cargo) == 0 &&
                Double.compare(that.price, price) == 0 &&
                Objects.equals(owner, that.owner) &&
                Objects.equals(startPoint, that.startPoint) &&
                Objects.equals(finishPoint, that.finishPoint) &&
                Objects.equals(comment, that.comment) &&
                Objects.equals(courier, that.courier) &&
                status == that.status;
    }

    @Override
    public int hashCode() {

        return Objects.hash(id, owner, startPoint, finishPoint, cargo, comment, courier, price, status);
    }

    @Override
    public String toString() {
        return "Application{" +
                "id=" + id +
                ", owner=" + owner +
                ", startPoint='" + startPoint + '\'' +
                ", finishPoint='" + finishPoint + '\'' +
                ", cargo=" + cargo +
                ", comment='" + comment + '\'' +
                ", courier=" + courier +
                ", price=" + price +
                ", status=" + status +
                '}';
    }
}
