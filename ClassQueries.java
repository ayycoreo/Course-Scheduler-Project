import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 *
 * @author corey
 */
public class ClassQueries 
{
    
    private static Connection connection;
    private static PreparedStatement addClass;
    private static PreparedStatement dropClass;
    private static PreparedStatement getAllCourseCodes;
    private static PreparedStatement getClassSeats;
    private static ResultSet resultSet;
    
    
    public static void addClass(ClassEntry Class)
    {
        connection = DBConnection.getConnection();
        
        try
        {
            addClass = connection.prepareStatement("insert into app.class (semester, coursecode, seats) values (?, ?, ?)");
            addClass.setString(1,Class.getSemester());
            addClass.setString(2,Class.getCourseCode());
            addClass.setInt(3,Class.getSeats());
            
            addClass.executeUpdate();
        }
        catch(SQLException sqlException)
        {
            sqlException.printStackTrace();
        }
    }
    
    public static void dropClass(String semester, String courseCode)
    {
        connection = DBConnection.getConnection();
        
        try
        {
            dropClass = connection.prepareStatement("delete from app.class where semester = ? and courseCode = ?");
            dropClass.setString(1, semester);
            dropClass.setString(2, courseCode);
            
            dropClass.executeUpdate();
        
        }
        catch(SQLException sqlException)
        {
            sqlException.printStackTrace();
        }
    }
    
    public static ArrayList<String> getAllCourseCodes(String semester) 
    {
        connection = DBConnection.getConnection();
        
        ArrayList<String> courseCodeList = new ArrayList<String>();
        
        try
        {
            getAllCourseCodes = connection.prepareStatement("select coursecode from app.class where semester = ?");
            getAllCourseCodes.setString(1,semester);
            resultSet = getAllCourseCodes.executeQuery();  
            
            while(resultSet.next())
            {
                String currentCourse = new String(resultSet.getString(1));
                courseCodeList.add(currentCourse);
            }
        }
        catch(SQLException sqlException)
        {
            sqlException.printStackTrace();
        }
        return courseCodeList;
    }
    
    
    public static int getClassSeats(String semester, String courseCode)
    {
        connection = DBConnection.getConnection();
        int seats = 0;
        try
        {
            getClassSeats = connection.prepareStatement("select seats from app.class where semester = ? and coursecode = ?");
            getClassSeats.setString(1,semester);
            getClassSeats.setString(2,courseCode);
            resultSet = getClassSeats.executeQuery();
            
            resultSet.next();
            
            seats = resultSet.getInt(1);
            
        }
        catch(SQLException sqlException)
        {
            sqlException.printStackTrace();
        }
        
        return seats;
    }
   
    
    
    
}
