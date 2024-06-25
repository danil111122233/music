<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Вход в аккаунт</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            margin: 0;
            padding: 0;
            display: flex;
            justify-content: center;
            align-items: center;
            height: 100vh;
            background-color: #e9ecef;
        }

        .login-container {
            background-color: #ffffff;
            padding: 40px;
            box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
            border-radius: 8px;
            width: 100%;
            max-width: 400px;
            text-align: center;
        }

        .login-container h2 {
            margin-bottom: 20px;
            color: #343a40;
            font-size: 24px;
        }

        .login-container label {
            display: block;
            text-align: left;
            margin: 10px 0 5px;
            font-weight: bold;
            color: #495057;
        }

        .login-container input {
            width: 100%;
            padding: 10px;
            margin: 10px 0;
            border: 1px solid #ced4da;
            border-radius: 4px;
            font-size: 16px;
        }

        .login-container input:focus {
            border-color: #80bdff;
            outline: none;
            box-shadow: 0 0 5px rgba(128, 189, 255, 0.5);
        }

        .login-container button {
            width: 100%;
            padding: 10px;
            background-color: #007bff;
            border: none;
            color: white;
            border-radius: 4px;
            font-size: 16px;
            cursor: pointer;
            transition: background-color 0.3s ease;
            margin-bottom: 10px;
        }

        .login-container button:hover {
            background-color: #0056b3;
        }

        .login-container button:active {
            background-color: #004085;
        }

        .register-container {
            text-align: center;
        }

        .register-container a {
            display: inline-block;
            padding: 10px 20px;
            background-color: #6c757d;
            color: white;
            border-radius: 4px;
            text-decoration: none;
            font-size: 16px;
            transition: background-color 0.3s ease;
        }

        .register-container a:hover {
            background-color: #5a6268;
        }

        .register-container a:active {
            background-color: #4e555b;
        }
    </style>
</head>
<body>
<div class="login-container">
    <h2>Вход в аккаунт</h2>
    <form action="login" method="post">
        <label for="email">Электронная почта</label>
        <input type="email" id="email" name="email" placeholder="email@email.ru" required>

        <label for="password">Пароль</label>
        <input type="password" id="password" name="password" placeholder="Пароль" required>

        <button type="submit">Войти</button>
    </form>
    <div class="register-container">
        <a href="<c:url value='/registration'/>">Зарегистрироваться</a>
    </div>
    <% if (request.getAttribute("error") != null) { %>
    <div class="error_message">
        <%= request.getAttribute("error") %>
    </div>
    <% } %>
</div>
</body>
</html>
