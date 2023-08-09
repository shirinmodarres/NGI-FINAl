//package Core.Model;
//
//import javax.persistence.*;
//
//@Entity
//@Table(name = "user_project")
//public class UserProject {
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    @Column(name = "id")
//    private int id;
//
//    @ManyToOne
//    @JoinColumn(name = "user_id")
//    private User user;
//
//    @ManyToOne
//    @JoinColumn(name = "project_id")
//    private Project project;
//
//    public UserProject(User user, Project project) {
//        this.user = user;
//        this.project = project;
//    }
//
//    // Default constructor for Hibernate
//    public UserProject() {
//    }
//
//    // Getters and setters
//
//    public int getId() {
//        return id;
//    }
//
//    public void setId(int id) {
//        this.id = id;
//    }
//
//    public User getUser() {
//        return user;
//    }
//
//    public void setUser(User user) {
//        this.user = user;
//    }
//
//    public Project getProject() {
//        return project;
//    }
//
//    public void setProject(Project project) {
//        this.project = project;
//    }
//}
