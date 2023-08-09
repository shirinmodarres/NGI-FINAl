package Core.Model;

import java.util.Locale;


public class User {

    private int id;

    private String name;

    private String email;
    private String password;
    private Role role;
//    private boolean isDeleted;

    public User(){

    }

    public User(int id, String nickname, String email, String password, Role roll) {
        this.id=id;
        this.name = nickname;
        this.email = email.toLowerCase();
        this.password = password;
        this.role = roll;

    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

//    public boolean isDeleted() {
//        return isDeleted;
//    }

//    public void setDeleted(boolean deleted) {
//        isDeleted = deleted;
//    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", role=" + role +
                '}';
    }
}
