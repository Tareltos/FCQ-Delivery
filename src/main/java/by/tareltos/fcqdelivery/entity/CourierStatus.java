package by.tareltos.fcqdelivery.entity;

public enum CourierStatus {

    ACTIVE("active"),
    BLOCKED("blocked");

    private String status;

    CourierStatus(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }

}
