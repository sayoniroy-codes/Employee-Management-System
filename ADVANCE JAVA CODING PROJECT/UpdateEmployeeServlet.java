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

@WebServlet("/UpdateEmployeeServlet")
public class UpdateEmployeeServlet extends HttpServlet {
    
    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        // 1. Security Check
        HttpSession session = request.getSession();
        String role = (String) session.getAttribute("userRole");
        if (role == null || !role.equals("Admin")) {
            response.sendRedirect("login.jsp");
            return;
        }

        // 2. Catch the updated data from the form (including the hidden ID!)
        int id = Integer.parseInt(request.getParameter("empId"));
        String name = request.getParameter("empName");
        String email = request.getParameter("empEmail");
        String department = request.getParameter("empDept");
        String empRole = request.getParameter("empRole");
        
        double salary = 0.0;
        try {
            salary = Double.parseDouble(request.getParameter("empSalary"));
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }

        // Database credentials
        String dbUrl = "jdbc:mysql://localhost:3306/employee_system";
        String dbUser = "root";
        String dbPass = ""; 
        
        try {
            // 3. Connect to XAMPP
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection conn = DriverManager.getConnection(dbUrl, dbUser, dbPass);
            
            // 4. Run the UPDATE query
            // We tell it exactly which row to overwrite using the "WHERE emp_id = ?" part
            String sql = "UPDATE employees SET name=?, email=?, department=?, salary=?, role=? WHERE emp_id=?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            
            stmt.setString(1, name);
            stmt.setString(2, email);
            stmt.setString(3, department);
            stmt.setDouble(4, salary);
            stmt.setString(5, empRole);
            stmt.setInt(6, id);
            
            // Execute the update!
            stmt.executeUpdate(); 
            
            stmt.close();
            conn.close();
            
            // 5. Success! Route back to the dashboard
            response.sendRedirect("DashboardServlet");
            
        } catch (Exception e) {
            e.printStackTrace();
            response.sendRedirect("DashboardServlet"); // Fail safe
        }
    }
}
