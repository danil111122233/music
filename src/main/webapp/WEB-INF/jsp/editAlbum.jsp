<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="ru">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Редактирование альбома</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            line-height: 1.6;
            background-color: #f0f0f0;
            margin: 20px;
        }
        h1, h2 {
            color: #333;
        }
        form {
            margin-bottom: 20px;
        }
        label {
            font-weight: bold;
        }
        input[type="text"], input[type="date"], input[type="submit"] {
            padding: 8px;
            margin: 5px 0;
            border: 1px solid #ccc;
            border-radius: 4px;
            width: 100%;
            box-sizing: border-box;
        }
        input[type="submit"] {
            background-color: #4CAF50;
            color: white;
            border: none;
            cursor: pointer;
        }
        input[type="submit"]:hover {
            background-color: #45a049;
        }
        .error-message {
            color: #dc3545;
            font-size: 14px;
            margin-top: 10px;
        }
    </style>
</head>
<body>
<h1>Редактирование альбома</h1>

<form action="${pageContext.request.contextPath}/album/edit" method="post">
    <input type="hidden" name="idAlbum" value="${album.id()}">

    <label for="title">Название:</label>
    <input type="text" id="title" name="title" value="${album.title()}"><br>

    <label for="release">Дата релиза:</label>
    <input type="date" id="release" name="release" value="${album.release()}"><br>

    <input type="submit" value="Сохранить изменения">
</form>

<c:if test="${not empty error}">
    <div class="error-message">${error}</div>
</c:if>

<hr>
<a href="${pageContext.request.contextPath}/account">Назад в личный кабинет</a>
</body>
</html>
