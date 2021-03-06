package by.tareltos.fcqdelivery.entity.user;

public enum UserRole {
    ADMIN("admin"),
    MANAGER("manager"),
    CUSTOMER("customer");

    private String role;

    UserRole(String role) {
        this.role = role;
    }

    public String getRole() {
        return role;
    }
}
