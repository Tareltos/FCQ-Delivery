package by.tareltos.fcqdelivery.entity.application;

public enum ApplicationStatus {
    NEW("new"),
    WAITING("waiting"),
    CONFIRMED("confirmed"),
    DELIVERED("delivered"),
    CANCELED("canceled");


    private String status;

    ApplicationStatus(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }
}
