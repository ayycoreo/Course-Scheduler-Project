

/**
 *
 * @author corey
 */
public class ClassDescription 
{
    private String description;
    private String courseCode;
    private int seats;

    public ClassDescription(String description, String courseCode, int seats) {
        this.description = description;
        this.courseCode = courseCode;
        this.seats = seats;
    }

    public String getDescription() {
        return description;
    }

    public String getCourseCode() {
        return courseCode;
    }

    public int getSeats() {
        return seats;
    }
    
    
    
}
