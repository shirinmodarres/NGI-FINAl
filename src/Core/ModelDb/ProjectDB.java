//package Core.ModelDb;
//
//import javax.persistence.*;
//import java.text.SimpleDateFormat;
//import java.util.ArrayList;
//import java.util.Date;
//
//
//@Entity
//@Table(name = "projectDB")
//public class ProjectDB {
//
//
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    @Column(name = "project-id")
//    private int id;
//
//    @Column(name = "title")
//    private String title;
//    @Lob
//    @Column(name = "description")
//    private String description;
//    @Temporal(TemporalType.TIMESTAMP)
//    @Column(name = "date")
//    private Date createDate;
//
//    public ProjectDB() {
//
//    }
//
//    public ProjectDB(String title, String description) {
//        this.createDate = new Date();
//        this.title = title;
//        this.description = description;
//
//    }
//    public int getId() {
//        return id;
//    }
//
//    public void setId(int id) {
//        this.id = id;
//    }
//    public String getFormattedDate() {
//        SimpleDateFormat sdf = new SimpleDateFormat("yyyy MMM dd"); // Format the date as "YYYY-MM-DD"
//        return sdf.format(createDate);
//    }
//
//    public String getTitle() {
//        return title;
//    }
//
//    public void setTitle(String title) {
//        this.title = title;
//    }
//
//    public String getDescription() {
//        return description;
//    }
//
//    public void setDescription(String description) {
//        this.description = description;
//    }
//}
//
//
