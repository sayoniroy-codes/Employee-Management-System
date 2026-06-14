import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import model.Employee;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;


@WebServlet("/DashboardServlet")
public class DashboardServlet extends HttpServlet {
    
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
       
        HttpSession session = request.getSession();
        String role = (String) session.getAttribute("userRole");
        if (role == null || !role.equals("Admin")) {
            response.sendRedirect("login.jsp");
            return;
        }

       
        List<Employee> employeeList = new ArrayList<>();
        
  
        String dbUrl = "jdbc:mysql://localhost:3306/employee_system";
        String dbUser = "root";
        String dbPass = ""; 
        
        try {
         
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection conn = DriverManager.getConnection(dbUrl, dbUser, dbPass);
            
          
            String sql = "SELECT * FROM employees";
            PreparedStatement stmt = conn.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();
            
          
            while (rs.next()) {
                Employee emp = new Employee(
                    rs.getInt("emp_id"),
                    rs.getString("name"),
                    rs.getString("email"),
                    rs.getString("department"),
                    rs.getDouble("salary"),
                    rs.getString("role")
                );
                employeeList.add(emp); 
            }
            
            rs.close();
            stmt.close();
            conn.close();
            
          
            request.setAttribute("employeeData", employeeList);
            request.getRequestDispatcher("admin_dashboard.jsp").forward(request, response);
            
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
