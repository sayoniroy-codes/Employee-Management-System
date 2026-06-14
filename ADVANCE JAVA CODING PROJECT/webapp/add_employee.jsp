<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
    // Security Check: Only Admins can see this page!
    String role = (String) session.getAttribute("userRole");
    if (role == null || !role.equals("Admin")) {
        response.sendRedirect("login.jsp");
        return;
    }
%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Add New Employee</title>
    <style>
        body { font-family: Arial, sans-serif; background-color: #f4f4f9; padding: 20px; display: flex; justify-content: center; }
        .form-container { background: white; padding: 30px; border-radius: 8px; box-shadow: 0 4px 8px rgba(0,0,0,0.1); width: 400px; }
        h3 { text-align: center; color: #333; }
        label { font-weight: bold; display: block; margin-top: 15px; }
        input, select { width: 100%; padding: 10px; margin-top: 5px; border: 1px solid #ccc; border-radius: 4px; box-sizing: border-box; }
        .submit-btn { width: 100%; padding: 12px; background-color: #28a745; color: white; border: none; border-radius: 4px; cursor: pointer; margin-top: 20px; font-weight: bold; }
        .submit-btn:hover { background-color: #218838; }
        .cancel-btn { display: block; text-align: center; margin-top: 15px; color: #dc3545; text-decoration: none; }
    </style>
</head>
<body>

    <div class="form-container">
        <h3>Register New Employee</h3>
        
        <form action="AddEmployeeServlet" method="POST">
            
            <label>Full Name:</label>
            <input type="text" name="empName" required>
            
            <label>Email Address:</label>
            <input type="email" name="empEmail" required>
            
            <label>Temporary Password:</label>
            <input type="password" name="empPassword" required>
            
            <label>Department:</label>
            <select name="empDept">
                <option value="IT">IT</option>
                <option value="HR">HR</option>
                <option value="Finance">Finance</option>
                <option value="Management">Management</option>
            </select>
            
            <label>Salary (₹):</label>
            <input type="number" step="0.01" name="empSalary" required>
            
            <label>System Role:</label>
            <select name="empRole">
                <option value="Employee">Employee</option>
                <option value="Admin">Admin</option>
            </select>
            
            <button type="submit" class="submit-btn">Save Employee</button>
            <a href="DashboardServlet" class="cancel-btn">Cancel</a>
        </form>
    </div>

</body>
</html>