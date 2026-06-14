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

@WebServlet("/AddEmployeeServlet")
public class AddEmployeeServlet extends HttpServlet {
    
    // Notice we use doPost here because your HTML form uses method="POST"
    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        // 1. Security Check: Only Admins can add employees
        HttpSession session = request.getSession();
        String role = (String) session.getAttribute("userRole");
        if (role == null || !role.equals("Admin")) {
            response.sendRedirect("login.jsp");
            return;
        }

        // 2. Catch the data sent from the HTML form
        String name = request.getParameter("empName");
        String email = request.getParameter("empEmail");
        String password = request.getParameter("empPassword");
        String department = request.getParameter("empDept");
        String salaryStr = request.getParameter("empSalary");
        String empRole = request.getParameter("empRole");

        // Convert the salary text into a math number (Double)
        double salary = 0.0;
        try {
            salary = Double.parseDouble(salaryStr);
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
            
            // 4. Securely INSERT the new employee into the database
            String sql = "INSERT INTO employees (name, email, password, department, salary, role) VALUES (?, ?, ?, ?, ?, ?)";
            PreparedStatement stmt = conn.prepareStatement(sql);
            
            stmt.setString(1, name);
            stmt.setString(2, email);
            stmt.setString(3, password);
            stmt.setString(4, department);
            stmt.setDouble(5, salary);
            stmt.setString(6, empRole);
            
            // Execute the save command!
            stmt.executeUpdate();
            // Trigger Email Notification (Lab Q4)
utility.EmailUtility.sendEmail(email, "Welcome to the Team!", "Hello " + name + ",\nYour employee account has been successfully created in the system."); 
            
            stmt.close();
            conn.close();
            
            // 5. Success! Route them back to the dashboard to see the updated table
            response.sendRedirect("DashboardServlet");
            
        } catch (Exception e) {
            e.printStackTrace();
            // If it fails (like using an email that already exists), send them back to the form
            response.sendRedirect("add_employee.jsp");
        }
    }
}
