package Core.Model;


import java.text.SimpleDateFormat;
import java.util.Date;



public class Board {

    private Integer id;

    private String name;

    private Date date;
    public Board( String name) {
        this.name = name;
        this.date = new Date();
    }

    public String getFormattedDate() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy MMM dd"); // Format the date as "YYYY-MM-DD"
        return sdf.format(date);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
