import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet("/DeleteEmployeeServlet")
public class DeleteEmployeeServlet extends HttpServlet {
    
    // We use doGet here because clicking a standard hyperlink sends a GET request!
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        // 1. Security Check
        HttpSession session = request.getSession();
        String role = (String) session.getAttribute("userRole");
        if (role == null || !role.equals("Admin")) {
            response.sendRedirect("login.jsp");
            return;
        }

        // 2. Catch the ID from the URL
        String empId = request.getParameter("id");
        
        if (empId != null) {
            String dbUrl = "jdbc:mysql://localhost:3306/employee_system";
            String dbUser = "root";
            String dbPass = ""; 
            
            try {
                // 3. Connect to XAMPP and run the DELETE query
                Class.forName("com.mysql.cj.jdbc.Driver");
                Connection conn = DriverManager.getConnection(dbUrl, dbUser, dbPass);
                
                String sql = "DELETE FROM employees WHERE emp_id=?";
                PreparedStatement stmt = conn.prepareStatement(sql);
                stmt.setInt(1, Integer.parseInt(empId));
                
                // Execute the deletion!
                stmt.executeUpdate(); 
                
                stmt.close();
                conn.close();
                
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        
        // 4. Route back to the dashboard to see the updated table
        response.sendRedirect("DashboardServlet");
    }
}
