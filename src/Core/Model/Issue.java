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
private  Project project;

    public Project getProject() {
        return project;
    }

    public void setProject(Project project) {
        this.project = project;
    }

    // Constructor
    public Issue(String title, String description, Status status,
                 Types types, Priority priority, ArrayList<String> tags) {

        this.title = title;
        this.description = description;
        this.status = status;
        this.createDate = new Date();
        this.types = types;
        this.priority = priority;
        this.tags = tags;

    }

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
