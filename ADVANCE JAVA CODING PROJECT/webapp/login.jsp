<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Employee Management System - Login</title>
    <style>
        body { font-family: Arial, sans-serif; background-color: #f4f4f9; display: flex; justify-content: center; align-items: center; height: 100vh; margin: 0; }
        .login-box { background: white; padding: 30px; border-radius: 8px; box-shadow: 0 4px 8px rgba(0,0,0,0.1); width: 300px; text-align: center; }
        input[type="email"], input[type="password"] { width: 90%; padding: 10px; margin: 10px 0; border: 1px solid #ccc; border-radius: 4px; }
        button { width: 100%; padding: 10px; background-color: #0056b3; color: white; border: none; border-radius: 4px; cursor: pointer; }
        button:hover { background-color: #004494; }
        .error { color: red; font-size: 14px; margin-bottom: 10px; }
    </style>
</head>
<body>

    <div class="login-box">
        <h2>System Login</h2>
        
        <% 
            String errorMsg = (String) request.getAttribute("errorMessage");
            if(errorMsg != null) { 
        %>
            <div class="error"><%= errorMsg %></div>
        <% } %>

        <form action="LoginServlet" method="POST">
            <input type="email" name="userEmail" placeholder="Enter your email" required>
            <input type="password" name="userPassword" placeholder="Enter your password" required>
            <button type="submit">Login</button>
        </form>
    </div>

</body>
</html>