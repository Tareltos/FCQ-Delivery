package by.tareltos.fcqdelivery.entity.user;

public enum UserStatus {
    ACTIVE("active"),
    BLOCKED("blocked");

    private String status;

    UserStatus(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }
}
