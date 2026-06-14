<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.sql.*" %>
<%
    // Security Check
    String role = (String) session.getAttribute("userRole");
    if (role == null || !role.equals("Admin")) {
        response.sendRedirect("login.jsp");
        return;
    }

    String empId = request.getParameter("id");
    String name = "", email = "", dept = "", empRole = "";
    double salary = 0.0;

    // Fetch the specific employee's current data from XAMPP
    try {
        Class.forName("com.mysql.cj.jdbc.Driver");
        Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/employee_system", "root", "");
        PreparedStatement stmt = conn.prepareStatement("SELECT * FROM employees WHERE emp_id=?");
        stmt.setInt(1, Integer.parseInt(empId));
        ResultSet rs = stmt.executeQuery();
        
        if(rs.next()) {
            name = rs.getString("name");
            email = rs.getString("email");
            dept = rs.getString("department");
            salary = rs.getDouble("salary");
            empRole = rs.getString("role");
        }
        rs.close(); stmt.close(); conn.close();
    } catch(Exception e) {
        e.printStackTrace();
    }
%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Edit Employee</title>
    <style>
        body { font-family: Arial, sans-serif; background-color: #f4f4f9; padding: 20px; display: flex; justify-content: center; }
        .form-container { background: white; padding: 30px; border-radius: 8px; box-shadow: 0 4px 8px rgba(0,0,0,0.1); width: 400px; }
        h3 { text-align: center; color: #333; }
        label { font-weight: bold; display: block; margin-top: 15px; }
        input, select { width: 100%; padding: 10px; margin-top: 5px; border: 1px solid #ccc; border-radius: 4px; box-sizing: border-box; }
        .submit-btn { width: 100%; padding: 12px; background-color: #ffc107; color: black; border: none; border-radius: 4px; cursor: pointer; margin-top: 20px; font-weight: bold; }
        .submit-btn:hover { background-color: #e0a800; }
        .cancel-btn { display: block; text-align: center; margin-top: 15px; color: #dc3545; text-decoration: none; }
    </style>
</head>
<body>

    <div class="form-container">
        <h3>Edit Employee Details</h3>
        
        <form action="UpdateEmployeeServlet" method="POST">
            
            <input type="hidden" name="empId" value="<%= empId %>">
            
            <label>Full Name:</label>
            <input type="text" name="empName" value="<%= name %>" required>
            
            <label>Email Address:</label>
            <input type="email" name="empEmail" value="<%= email %>" required>
            
            <label>Department:</label>
            <select name="empDept">
                <option value="IT" <%= dept.equals("IT") ? "selected" : "" %>>IT</option>
                <option value="HR" <%= dept.equals("HR") ? "selected" : "" %>>HR</option>
                <option value="Finance" <%= dept.equals("Finance") ? "selected" : "" %>>Finance</option>
                <option value="Management" <%= dept.equals("Management") ? "selected" : "" %>>Management</option>
            </select>
            
            <label>Salary (₹):</label>
            <input type="number" step="0.01" name="empSalary" value="<%= salary %>" required>
            
            <label>System Role:</label>
            <select name="empRole">
                <option value="Employee" <%= empRole.equals("Employee") ? "selected" : "" %>>Employee</option>
                <option value="Admin" <%= empRole.equals("Admin") ? "selected" : "" %>>Admin</option>
            </select>
            
            <button type="submit" class="submit-btn">Update Employee</button>
            <a href="DashboardServlet" class="cancel-btn">Cancel</a>
        </form>
    </div>

</body>
</html>