import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;


/**
 *
 * @author corey
 */
public class ScheduleQueries 
{
 
    private static Connection connection;
    private static PreparedStatement addScheduleEntry;
    private static PreparedStatement getScheduleByStudent;
    private static PreparedStatement getScheduledStudentCount;
    private static PreparedStatement getWaitListedStudentsByClass;
    private static PreparedStatement dropStudentScheduleByCourse;
    private static PreparedStatement dropScheduleByCourse;
    private static PreparedStatement updateScheduleEntry;
    private static ResultSet resultSet;
    
    
    
    
    public static void addScheduleEntry(ScheduleEntry entry)
    {
        connection = DBConnection.getConnection();
        
        try
        {
            addScheduleEntry = connection.prepareStatement("insert into app.schedule (semester,studentid,coursecode,status,timestamp) values (?, ?, ?, ?, ?)");
            addScheduleEntry.setString(1,entry.getSemester());
            addScheduleEntry.setString(2,entry.getStudentID());
            addScheduleEntry.setString(3,entry.getCourseCode());
            addScheduleEntry.setString(4,entry.getStatus());
            addScheduleEntry.setTimestamp(5,entry.getTimeStamp());
            
            addScheduleEntry.executeUpdate();
        }
        
        catch(SQLException sqlException)
        {
            sqlException.printStackTrace();
        }
    }
    
    public static ArrayList<ScheduleEntry> getScheduleByStudent(String semester, String studentID)
    {
        connection = DBConnection.getConnection();
        ArrayList<ScheduleEntry> studentSchedule = new ArrayList<ScheduleEntry>();
        try
        {
            getScheduleByStudent = connection.prepareStatement("select semester, coursecode, studentid, status, timestamp from app.schedule where semester = ? and studentid = ? order by timestamp");
            getScheduleByStudent.setString(1,semester);
            getScheduleByStudent.setString(2,studentID);
            
            resultSet = getScheduleByStudent.executeQuery();
            
            while(resultSet.next())
            {
                ScheduleEntry currentSchedule = new ScheduleEntry(resultSet.getString(1),resultSet.getString(2),resultSet.getString(3),resultSet.getString(4),resultSet.getTimestamp(5));
                studentSchedule.add(currentSchedule);
            }
        }
        catch(SQLException sqlException)
        {
            sqlException.printStackTrace();
        }
        
        return studentSchedule;
    }
    
    public static int getScheduledStudentCount(String currentSemester, String courseCode)
    {
        connection = DBConnection.getConnection();
        
        int count = 0;
        
        try
        {
            getScheduledStudentCount = connection.prepareStatement("select count(studentid) from app.schedule where semester = ? and coursecode = ?");
            getScheduledStudentCount.setString(1,currentSemester);
            getScheduledStudentCount.setString(2,courseCode);
            resultSet = getScheduledStudentCount.executeQuery();
            
            while(resultSet.next())
            {
                count = resultSet.getInt(1);
            }
        }
        
       catch(SQLException sqlException)
        {
            sqlException.printStackTrace();
        }
        return count;
    }
    
    
    public static ArrayList<ScheduleEntry> getWaitListedStudentsByClass(String semester, String courseCode)
    {
        
        connection = DBConnection.getConnection();
        ArrayList<ScheduleEntry> studentSchedule = new ArrayList<ScheduleEntry>();
        
        try
        {
            getWaitListedStudentsByClass = connection.prepareStatement("select semester, coursecode, studentid, status, timestamp from app.schedule where semester = ? and coursecode = ? and status = ? order by timestamp");
            getWaitListedStudentsByClass.setString(1, semester);
            getWaitListedStudentsByClass.setString(2, courseCode);
            getWaitListedStudentsByClass.setString(3, "Waitlisted");
            
            resultSet = getWaitListedStudentsByClass.executeQuery();

            while(resultSet.next())
            {
                ScheduleEntry currentStudentSchedule = new ScheduleEntry(resultSet.getString(1), resultSet.getString(2), resultSet.getString(3), resultSet.getString(4), resultSet.getTimestamp(5));
                studentSchedule.add(currentStudentSchedule);
            }
        }
        catch(SQLException sqlException)
        {
            sqlException.printStackTrace();
        }
        return studentSchedule;
    }
    
    public static void dropStudentScheduleByCourse(String semester, String studentID, String courseCode)
    {
        
        connection = DBConnection.getConnection();
        
        try
        {
            dropStudentScheduleByCourse = connection.prepareStatement("delete from app.schedule where semester = ? and studentid = ? and coursecode = ?");
            dropStudentScheduleByCourse.setString(1, semester);
            dropStudentScheduleByCourse.setString(2, studentID);
            dropStudentScheduleByCourse.setString(3, courseCode);
            
            dropStudentScheduleByCourse.executeUpdate();
        }
        
        catch(SQLException sqlException)
        {
            sqlException.printStackTrace();
        }
    }
    
    public static void dropScheduleByCourse(String semester, String courseCode)
    {
        
        connection = DBConnection.getConnection();
  
        try
        {
            dropScheduleByCourse = connection.prepareStatement("delete from app.schedule where semester = ? and coursecode = ?");
            dropScheduleByCourse.setString(1, semester);
            dropScheduleByCourse.setString(2, courseCode);
            
            dropScheduleByCourse.executeUpdate();
        }
        
        catch(SQLException sqlException)
        {
            sqlException.printStackTrace();
        }
    }
    
    public static void updateScheduleEntry(ScheduleEntry entry)
    {
        connection = DBConnection.getConnection();
        
        try
        {
            updateScheduleEntry = connection.prepareStatement("update app.schedule set status = ? where semester = ? and studentid  = ? and coursecode = ?");
            
            if (entry.getStatus().contentEquals("Waitlisted"))
            {
                updateScheduleEntry.setString(1, "Scheduled");
            }
            
            else
            {
                updateScheduleEntry.setString(1, "Waitlisted");
            }
            updateScheduleEntry.setString(2, entry.getSemester());
            updateScheduleEntry.setString(3, entry.getStudentID());
            updateScheduleEntry.setString(4, entry.getCourseCode());
            
            updateScheduleEntry.executeUpdate();
        }
        
        catch(SQLException sqlException)
        {
            sqlException.printStackTrace();
        }
    }
        
        
        
    }
    
    

