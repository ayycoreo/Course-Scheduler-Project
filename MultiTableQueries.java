import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
/**
 *
 * @author corey
 */
public class MultiTableQueries 
{
    
    private static Connection connection;
    private static PreparedStatement getAllClassDescriptions;
    private static PreparedStatement getScheduledStudentsByClass;
    private static PreparedStatement getWaitListedStudentsByClass;
    private static ResultSet resultSet;
    
    
    
    public static ArrayList<ClassDescription> getAllClassDescriptions(String semester)
    {
        connection = DBConnection.getConnection();
        
        ArrayList<ClassDescription> classDescriptions = new ArrayList<ClassDescription>();
        
        try
        {
            getAllClassDescriptions = connection.prepareStatement("select app.class.coursecode, description, seats from app.class, app.course where semester = ? and app.class.coursecode = app.course.coursecode order by app.class.coursecode");
            getAllClassDescriptions.setString(1,semester);
            resultSet = getAllClassDescriptions.executeQuery();
            
            while(resultSet.next())
            {
               ClassDescription currentClassDescription = new ClassDescription(resultSet.getString(1),resultSet.getString(2),resultSet.getInt(3));
               classDescriptions.add(currentClassDescription);
            }
            
        }
        
        catch(SQLException sqlException)
        {
            sqlException.printStackTrace();
        }
        return classDescriptions;
    }
    
    
    public static ArrayList<StudentEntry> getScheduledStudentsByClass(String semester, String courseCode)
    {
        connection = DBConnection.getConnection();
        ArrayList<StudentEntry> scheduledListStudents = new ArrayList<StudentEntry>();
        
        try
        {
           getScheduledStudentsByClass = connection.prepareStatement("select app.student.studentid,firstname, lastname from app.student, app.schedule where app.student.studentid = app.schedule.studentid and app.schedule.semester = ? and app.schedule.coursecode = ? and app.schedule.status = ? order by app.schedule.timestamp");
           getScheduledStudentsByClass.setString(1,semester);
           getScheduledStudentsByClass.setString(2,courseCode);
           getScheduledStudentsByClass.setString(3,"Scheduled");
           resultSet = getScheduledStudentsByClass.executeQuery();
           
           while(resultSet.next())
           {
               StudentEntry scheduledStudent = new StudentEntry(resultSet.getString(1), resultSet.getString(2), resultSet.getString(3));
               scheduledListStudents.add(scheduledStudent);
           }
           
        }
        catch(SQLException sqlException)
        {
            sqlException.printStackTrace();
        }
        
        return scheduledListStudents;
        
        
    }
    
    public static ArrayList<StudentEntry> getWaitListedStudentsByClass(String semester, String courseCode)
    {
        connection = DBConnection.getConnection();
        ArrayList<StudentEntry> WaitListedStudents = new ArrayList<StudentEntry>();
        
        try
        {
           getWaitListedStudentsByClass = connection.prepareStatement("select app.student.studentid,firstname, lastname from app.student, app.schedule where app.student.studentid = app.schedule.studentid and app.schedule.semester = ? and app.schedule.coursecode = ? and app.schedule.status = ? order by app.schedule.timestamp");
           getWaitListedStudentsByClass.setString(1,semester);
           getWaitListedStudentsByClass.setString(2,courseCode);
           getWaitListedStudentsByClass.setString(3,"Waitlisted");
           resultSet = getWaitListedStudentsByClass.executeQuery();
           
           while(resultSet.next())
           {
               StudentEntry waitListedStudent = new StudentEntry(resultSet.getString(1), resultSet.getString(2), resultSet.getString(3));
               WaitListedStudents.add(waitListedStudent);
           }
           
        }
        catch(SQLException sqlException)
        {
            sqlException.printStackTrace();
        }
        
        return WaitListedStudents;
    }
}
