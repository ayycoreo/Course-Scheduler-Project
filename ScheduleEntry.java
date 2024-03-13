import java.sql.Timestamp;
/**
 *
 * @author corey
 */
public class ScheduleEntry 
{
    private String semester;
    private String courseCode;
    private String studentID;
    private String status;
    private Timestamp timeStamp;

    public ScheduleEntry(String semester, String courseCode, String studentID, String status, Timestamp timeStamp) {
        this.semester = semester;
        this.courseCode = courseCode;
        this.studentID = studentID;
        this.status = status;
        this.timeStamp = timeStamp;
    }

    public String getSemester() {
        return semester;
    }

    public String getCourseCode() {
        return courseCode;
    }

    public String getStudentID() {
        return studentID;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Timestamp getTimeStamp() {
        return timeStamp;
    }
    
    
    
}
