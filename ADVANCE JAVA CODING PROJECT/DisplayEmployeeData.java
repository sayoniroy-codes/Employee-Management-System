import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class DisplayEmployeeData {
    public static void main(String[] args) {
        String url = "jdbc:mysql://localhost:3306/CompanyDB";
        String user = "root"; 
        String password = ""; // Assuming no password for default XAMPP MySQL

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection conn = DriverManager.getConnection(url, user, password);
            Statement stmt = conn.createStatement();
            
            String query = "SELECT * FROM Employee";
            ResultSet rs = stmt.executeQuery(query);

            System.out.println("emp_id\temp_name\tsalary\tdept_name");
            System.out.println("-------------------------------------------------");
            
            while(rs.next()) {
                System.out.println(rs.getInt("emp_id") + "\t" + 
                                   rs.getString("emp_name") + "\t\t" + 
                                   rs.getDouble("salary") + "\t" + 
                                   rs.getString("dept_name"));
            }
            
            rs.close();
            stmt.close();
            conn.close();
            
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}