package Core.Model;


import java.text.SimpleDateFormat;
import java.util.Date;

public class Project {

    private int id;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    private String title;

    private String description;

    private Date createDate;

    public Project() {

    }

    public Project(String title, String description) {
        this.createDate = new Date();
        this.title = title;
        this.description = description;

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
}
