//package Core.ModelDb;
//
//import Core.Model.Priority;
//import Core.Model.Status;
//import Core.Model.Types;
//
//import javax.persistence.*;
//import java.text.SimpleDateFormat;
//import java.util.ArrayList;
//import java.util.Date;
//import java.util.List;
//
//
//@Entity
//@Table(name = "issuedb")
//public class IssueDB {
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    @Column(name = "id")
//    private int id;
//
//    @Column(name = "title")
//    private String title;
//
//    @Column(name = "description")
//    private String description;
//
//    @Enumerated(EnumType.STRING)
//    @Column(name = "status")
//    private Status status;
//
//    @Temporal(TemporalType.TIMESTAMP)
//    @Column(name = "create_date")
//    private Date createDate;
//
//    @Temporal(TemporalType.DATE)
//    @Column(name = "update_date")
//    private Date updateDate;
//
//    @Enumerated(EnumType.STRING)
//    @Column(name = "type")
//    private Types type;
//
//    @Enumerated(EnumType.STRING)
//    @Column(name = "priority")
//    private Priority priority;
//
//    @Column(name = "tag")
//    private ArrayList<String> tags;
//
//
//
//    // Constructor
//    public IssueDB(String title, String description, Status status,
//                   Types type, Priority priority, ArrayList<String> tags) {
//
//        this.title = title;
//        this.description = description;
//        this.status = status;
//        this.createDate = new Date();
//        this.type = type;
//        this.priority = priority;
//        this.tags = tags;
//
//    }
//
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
//
//    public Status getStatus() {
//        return status;
//    }
//
//    public void setStatus(Status status) {
//        this.status = status;
//    }
//
//    public Date getCreateDate() {
//        return createDate;
//    }
//
//    public void setCreateDate(Date createDate) {
//        this.createDate = createDate;
//    }
//
//    public Date getUpdateDate() {
//        return updateDate;
//    }
//
//    public void setUpdateDate(Date updateDate) {
//        this.updateDate = updateDate;
//    }
//
//    public Types getType() {
//        return type;
//    }
//
//    public void setType(Types type) {
//        this.type = type;
//    }
//
//    public Priority getPriority() {
//        return priority;
//    }
//
//    public void setPriority(Priority priority) {
//        this.priority = priority;
//    }
//
//    public List<String> getTags() {
//        return tags;
//    }
//
//    public void setTags(ArrayList<String> tags) {
//        this.tags = tags;
//    }
//}
