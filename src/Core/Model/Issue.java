package Core.Model;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Issue {

    private int id;
    private String title;
    private String description;
    private Status status;
    private Date createDate;
    private Date updateDate;
    private Types types;
    private Priority priority;
    private ArrayList<String> tags;
    private int projectId;
    private int userId;


    public Issue(int id, String title, String description,Status status,
                 Types types, Priority priority, ArrayList<String> tags, int projectId,int userId) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.status = Status.TODO;
        this.createDate = new Date();
        this.types = types;
        this.priority = priority;
        this.tags = tags;
        this.projectId=projectId;
        this.userId=userId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public Types getTypes() {
        return types;
    }

    public void setTypes(Types types) {
        this.types = types;
    }

    public int getProjectId() {
        return projectId;
    }

    public void setProjectId(int projectId) {
        this.projectId = projectId;
    }

    // Constructor




    public String getFormattedDate() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy MMM dd"); // Format the date as "YYYY-MM-DD"
        return sdf.format(createDate);
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public Date getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(Date updateDate) {
        this.updateDate = updateDate;
    }

    public Types getType() {
        return types;
    }

    public void setType(Types types) {
        this.types = types;
    }

    public Priority getPriority() {
        return priority;
    }

    public void setPriority(Priority priority) {
        this.priority = priority;
    }

    public List<String> getTags() {
        return tags;
    }

    public void setTags(ArrayList<String> tags) {
        this.tags = tags;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
