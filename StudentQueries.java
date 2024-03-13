import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
/**
 *
 * @author corey
 */
public class StudentQueries 
{
 
    private static Connection connection;
    private static PreparedStatement addStudent;
    private static PreparedStatement getAllStudents;
    private static PreparedStatement getStudent;
    private static PreparedStatement dropStudent;
    private static ResultSet resultSet;
    
    public static void addStudent(StudentEntry student)
    {
        connection = DBConnection.getConnection();
        try
        {
            addStudent = connection.prepareStatement("insert into app.student (studentID, firstname, lastname) values (?, ?, ?)");
            addStudent.setString(1, student.getStudentID());
            addStudent.setString(2, student.getFirstName());
            addStudent.setString(3,  student.getLastName());
            addStudent.executeUpdate();
        }
        catch(SQLException sqlException)
        {
            sqlException.printStackTrace();
        }
    }
    
    public static StudentEntry getStudent(String studentID)
    {
        connection = DBConnection.getConnection();
        StudentEntry theWantedStudent = null;
    
        try 
        {
            getStudent = connection.prepareStatement("select studentid, firstname, lastname from app.student where studentid = ?");
            getStudent.setString(1, studentID);
       
            resultSet = getStudent.executeQuery();
        
            if(resultSet.next()) 
            {
                theWantedStudent = new StudentEntry(resultSet.getString(1),resultSet.getString(2), resultSet.getString(3));
            }
        } 
        
        catch (SQLException sqlException) 
        {
            sqlException.printStackTrace();
        } 
    
        return theWantedStudent;
    }
    
    
    public static void dropStudent(String studentID)
    {
        connection = DBConnection.getConnection();
        
        try
        {
            dropStudent = connection.prepareStatement("delete from app.student where studentid = ?");
            dropStudent.setString(1,studentID);
            
            dropStudent.executeUpdate();
        }
        
         catch(SQLException sqlException)
        {
            sqlException.printStackTrace();
        }
 
        
    }
    public static ArrayList<StudentEntry> getAllStudents()
    {
        connection = DBConnection.getConnection();
        ArrayList<StudentEntry> studentList = new ArrayList<StudentEntry>();
        try
        {
            getAllStudents = connection.prepareStatement("select studentid,firstname,lastname from app.student order by lastname,firstname");
            resultSet = getAllStudents.executeQuery();
            
            while(resultSet.next())
            {
               StudentEntry currentStudent = new StudentEntry(resultSet.getString(1),resultSet.getString(2),resultSet.getString(3));
               studentList.add(currentStudent);
            }
        }
        catch(SQLException sqlException)
        {
            sqlException.printStackTrace();
        }
        
        return studentList;
        
    }
    
}
