package by.tareltos.fcqdelivery.entity.user;

import java.util.Objects;

/**
 * The class serves to store objects with properties
 * <b>email</b>, <b>password/b>
 * <b>firstName</b>,<b>lastName</b>,
 * <b>phone</b>,<b>role</b>,
 * <b>status/b>.
 *
 * @version 1.0
 * @autor Tarelko Vitali
 */
public class User {

    private String email;
    private String password;
    private String firstName;
    private String lastName;
    private String phone;
    private UserRole role;
    private UserStatus status;


    public User() {
    }

    public User(String email, String password, String firstName, String lastName, String phone, UserRole role, UserStatus status) {
        this.email = email;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
        this.phone = phone;
        this.role = role;
        this.status = status;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public UserRole getRole() {
        return role;
    }

    public void setRole(UserRole role) {
        this.role = role;
    }

    public UserStatus getStatus() {
        return status;
    }

    public void setStatus(UserStatus status) {
        this.status = status;
    }
    /**
     * @see java.lang.Object
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(email, user.email) &&
                Objects.equals(password, user.password) &&
                Objects.equals(firstName, user.firstName) &&
                Objects.equals(lastName, user.lastName) &&
                Objects.equals(phone, user.phone) &&
                role == user.role &&
                status == user.status;
    }
    /**
     * @see java.lang.Object
     */
    @Override
    public int hashCode() {

        return Objects.hash(email, password, firstName, lastName, phone, role, status);
    }
    /**
     * @see java.lang.Object
     */
    @Override
    public String toString() {
        return "User{" +
                "email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", phone='" + phone + '\'' +
                ", role=" + role +
                ", status=" + status +
                '}';
    }
}