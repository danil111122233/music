<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Регистрация</title>
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

        .register-container {
            background-color: #ffffff;
            padding: 40px;
            box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
            border-radius: 8px;
            width: 100%;
            max-width: 400px;
            text-align: center;
        }

        .register-container h2 {
            margin-bottom: 20px;
            color: #343a40;
            font-size: 24px;
        }

        .register-container label {
            display: block;
            text-align: left;
            margin: 10px 0 5px;
            font-weight: bold;
            color: #495057;
        }

        .register-container input {
            width: 100%;
            padding: 10px;
            margin: 10px 0;
            border: 1px solid #ced4da;
            border-radius: 4px;
            font-size: 16px;
        }

        .register-container input:focus {
            border-color: #80bdff;
            outline: none;
            box-shadow: 0 0 5px rgba(128, 189, 255, 0.5);
        }

        .register-container button {
            width: 100%;
            padding: 10px;
            background-color: #28a745;
            border: none;
            color: white;
            border-radius: 4px;
            font-size: 16px;
            cursor: pointer;
            transition: background-color 0.3s ease;
            margin-top: 10px;
        }

        .register-container button:hover {
            background-color: #218838;
        }

        .register-container button:active {
            background-color: #1e7e34;
        }

        .register-container .login-link {
            margin-top: 15px;
        }

        .register-container .login-link a {
            color: #007bff;
            text-decoration: none;
        }

        .register-container .login-link a:hover {
            text-decoration: underline;
        }

        .error-message {
            display: none;
            margin-top: 15px;
            padding: 10px;
            background-color: #f8d7da;
            color: #721c24;
            border: 1px solid #f5c6cb;
            border-radius: 4px;
        }
    </style>
    <script>
        document.addEventListener("DOMContentLoaded", function () {
            var errorMessage = document.getElementById("error-message");
            if (errorMessage.textContent.trim() !== "") {
                errorMessage.style.display = "block";
            }
        });
    </script>
</head>
<body>
<div class="register-container">
    <h2>Регистрация</h2>
    <form action="registration" method="post">
        <label for="name">Имя</label>
        <input type="text" id="name" name="name" placeholder="Имя" required>

        <label for="debutYear">Год дебюта</label>
        <input type="number" id="debutYear" name="debutYear" placeholder="Год дебюта" required>

        <label for="email">Электронная почта</label>
        <input type="email" id="email" name="email" placeholder="email@email.ru" required>

        <label for="password">Пароль</label>
        <input type="password" id="password" name="password" placeholder="Пароль" required>

        <button type="submit">Зарегистрироваться</button>
    </form>
    <div class="login-link">
        Уже есть аккаунт? <a href="<c:url value='/login'/>">Войти</a>
    </div>
    <% if (request.getAttribute("error") != null) { %>
    <div class="error_message">
        <%= request.getAttribute("error") %>
    </div>
    <% } %>
</div>
</body>
</html>
