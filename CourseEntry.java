
/**
 *
 * @author corey
 */
public class CourseEntry 
{
    private String courseCode;
    private String description;

    public CourseEntry(String courseCode, String description) {
        this.courseCode = courseCode;
        this.description = description;
    }

    public String getCourseCode() {
        return courseCode;
    }

    public String getDescription() {
        return description;
    }
    
}
