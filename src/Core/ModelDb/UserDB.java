//package Core.ModelDb;
//
//import Core.Model.Role;
//
//import javax.persistence.*;
//
//@Entity
//@Table(name = "userDB")
//public class UserDB {
//
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    @Column(name = "id")
//    private int id;
//
//    @Column(name = "name")
//    private String name;
//
//    @Column(name = "email", unique = true, columnDefinition = "VARCHAR(255) COLLATE utf8_bin")
//    private String email;
//    @Column(name = "password")
//    private String password;
//    @Column(name = "role_id")
//    private Role role;
//    @Column(name = "is_deleted")
//    private boolean isDeleted;
//
//    public UserDB() {
//
//    }
//
//    public UserDB(String nickname, String email, String password, Role roll) {
//        this.name = nickname;
//        this.email = email.toLowerCase();
//        this.password = password;
//        this.role = roll;
//
//    }
//
//
//    public String getName() {
//        return name;
//    }
//
//    public void setName(String name) {
//        this.name = name;
//    }
//
//    public String getEmail() {
//        return email;
//    }
//
//    public void setEmail(String email) {
//        this.email = email;
//    }
//
//    public String getPassword() {
//        return password;
//    }
//
//    public void setPassword(String password) {
//        this.password = password;
//    }
//
//    public Role getRole() {
//        return role;
//    }
//
//    public void setRole(Role role) {
//        this.role = role;
//    }
//
//    public int getId() {
//        return id;
//    }
//
//    public void setId(int id) {
//        this.id = id;
//    }
//
//    public boolean isDeleted() {
//        return isDeleted;
//    }
//
//    public void setDeleted(boolean deleted) {
//        isDeleted = deleted;
//    }
//
//    @Override
//    public String toString() {
//        return "User{" +
//                "id=" + id +
//                ", name='" + name + '\'' +
//                ", email='" + email + '\'' +
//                ", password='" + password + '\'' +
//                ", role=" + role +
//                '}';
//    }
//}
//
//
