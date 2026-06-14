import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

// This annotation links exactly to the action="LoginServlet" in the JSP form
@WebServlet("/LoginServlet")
public class LoginServlet extends HttpServlet {
    
    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        
        String email = request.getParameter("userEmail");
        String password = request.getParameter("userPassword");
        
        // Database credentials
        String dbUrl = "jdbc:mysql://localhost:3306/employee_system";
        String dbUser = "root";
        String dbPass = ""; 
        
        try {
           
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection conn = DriverManager.getConnection(dbUrl, dbUser, dbPass);
            
            // 3. Securely query the database to prevent SQL injection
            String sql = "SELECT * FROM employees WHERE email = ? AND password = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, email);
            stmt.setString(2, password);
            
            ResultSet rs = stmt.executeQuery();
            
            
            if (rs.next()) {
                // User exists! Get their name and role
                String name = rs.getString("name");
                String role = rs.getString("role");
                
                // Create a session to remember they are logged in
                HttpSession session = request.getSession();
                session.setAttribute("userName", name);
                session.setAttribute("userRole", role);
                
               
                if ("Admin".equals(role)) {
                    response.sendRedirect("DashboardServlet");
                } else {
                    response.sendRedirect("employee_profile.jsp");
                }
            } else {
            
                request.setAttribute("errorMessage", "Invalid Email or Password. Please try again.");
                request.getRequestDispatcher("login.jsp").forward(request, response);
            }
            
            rs.close();
            stmt.close();
            conn.close();
            
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("errorMessage", "Database Connection Error!");
            request.getRequestDispatcher("login.jsp").forward(request, response);
        }
    }
}
