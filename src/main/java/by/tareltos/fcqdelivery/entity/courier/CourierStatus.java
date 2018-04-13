package by.tareltos.fcqdelivery.entity.courier;

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
