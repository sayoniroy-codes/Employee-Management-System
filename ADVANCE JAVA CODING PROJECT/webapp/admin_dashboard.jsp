<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.List" %>
<%@ page import="model.Employee" %>
<%
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
    <title>Admin Dashboard</title>
    
    <link rel="stylesheet" href="https://cdn.datatables.net/1.13.6/css/jquery.dataTables.min.css">
    <script src="https://code.jquery.com/jquery-3.7.0.min.js"></script>
    <script src="https://cdn.datatables.net/1.13.6/js/jquery.dataTables.min.js"></script>

    <style>
        body { font-family: Arial, sans-serif; background-color: #f4f4f9; padding: 20px; }
        .navbar { background-color: #0056b3; padding: 15px; color: white; display: flex; justify-content: space-between; align-items: center; border-radius: 5px; }
        .logout-btn { background-color: #dc3545; color: white; padding: 8px 15px; text-decoration: none; border-radius: 4px; }
        .content { background: white; padding: 20px; margin-top: 20px; border-radius: 5px; box-shadow: 0 2px 4px rgba(0,0,0,0.1); }
        
        table { width: 100%; border-collapse: collapse; margin-top: 15px; }
        th, td { padding: 12px; text-align: left; border-bottom: 1px solid #ddd; }
        th { background-color: #0056b3; color: white; }
        
        .action-btn { padding: 5px 10px; text-decoration: none; border-radius: 3px; font-size: 14px; margin-right: 5px; color: white; }
        .edit-btn { background-color: #ffc107; color: black; }
        .delete-btn { background-color: #dc3545; }
        .add-btn { background-color: #28a745; color: white; padding: 10px 15px; text-decoration: none; border-radius: 4px; display: inline-block; margin-bottom: 20px; font-weight: bold; }
        
        /* Small tweak so DataTables looks nice with our blue headers */
        table.dataTable thead th, table.dataTable thead td { border-bottom: none; }
    </style>
</head>
<body>

    <div class="navbar">
        <h2>Welcome to the Admin Dashboard, <%= session.getAttribute("userName") %>!</h2>
        <a href="login.jsp" class="logout-btn">Logout</a>
    </div>

    <div class="content">
        <h3>Employee Control Panel</h3>
        <a href="add_employee.jsp" class="add-btn">+ Add New Employee</a>

        <table id="employeeTable" class="display">
            <thead>
                <tr>
                    <th>ID</th>
                    <th>Name</th>
                    <th>Email</th>
                    <th>Department</th>
                    <th>Salary</th>
                    <th>Role</th>
                    <th>Actions</th>
                </tr>
            </thead>
            <tbody>
                <%
                    List<Employee> employees = (List<Employee>) request.getAttribute("employeeData");
                    if (employees != null && !employees.isEmpty()) {
                        for (Employee emp : employees) {
                %>
                <tr>
                    <td><%= emp.getId() %></td>
                    <td><%= emp.getName() %></td>
                    <td><%= emp.getEmail() %></td>
                    <td><%= emp.getDepartment() %></td>
                    <td>₹<%= emp.getSalary() %></td>
                    <td><%= emp.getRole() %></td>
                    <td>
                        <a href="edit_employee.jsp?id=<%= emp.getId() %>" class="action-btn edit-btn">Edit</a>
                        <a href="DeleteEmployeeServlet?id=<%= emp.getId() %>" class="action-btn delete-btn" onclick="return confirm('Are you sure you want to completely delete this employee?');">Delete</a>
                    </td>
                </tr>
                <%
                        }
                    }
                %>
            </tbody>
        </table>
    </div>

    <script>
        $(document).ready(function() {
            $('#employeeTable').DataTable({
                "pageLength": 5, // Show 5 employees per page by default
                "lengthMenu": [5, 10, 25, 50], // Let the user choose how many to see
                "language": {
                    "emptyTable": "No employees found in the database."
                }
            });
        });
    </script>

</body>
</html>
