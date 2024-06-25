<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="ru">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Личный кабинет музыкального артиста</title>
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
        table {
            width: 100%;
            border-collapse: collapse;
            margin-bottom: 20px;
            background-color: #fff;
            box-shadow: 0 2px 4px rgba(0,0,0,0.1);
        }
        th, td {
            padding: 10px;
            text-align: left;
            border-bottom: 1px solid #ddd;
        }
        th {
            background-color: #f2f2f2;
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
        hr {
            margin: 40px 0;
            border: none;
            border-top: 1px solid #ddd;
        }
        a {
            color: #007bff;
            text-decoration: none;
        }
        a:hover {
            text-decoration: underline;
        }
        .error-message {
            color: #dc3545;
            font-size: 14px;
            margin-top: 10px;
        }
    </style>
</head>
<body>
<h1>Личный кабинет музыкального артиста: ${artistName}</h1>

<% if (request.getAttribute("error") != null) { %>
<div class="error-message">
    <%= request.getAttribute("error") %>
</div>
<% } %>

<h2>Альбомы</h2>
<table>
    <thead>
    <tr>
        <th>ID</th>
        <th>Название</th>
        <th>Дата релиза</th>
        <th>Действия</th>
    </tr>
    </thead>
    <tbody>
    <c:forEach var="album" items="${albums}">
        <tr>
            <td>${album.id()}</td>
            <td>${album.title()}</td>
            <td>${album.release()}</td>
            <td>
                <form action="${pageContext.request.contextPath}/album/edit" method="get">
                    <input type="hidden" name="idAlbum" value="${album.id()}">
                    <input type="submit" value="Изменить альбом">
                </form>
                <form action="${pageContext.request.contextPath}/album/delete" method="post">
                    <input type="hidden" name="idAlbum" value="${album.id()}">
                    <input type="submit" value="Удалить альбом" class="delete">
                </form>
            </td>
        </tr>
    </c:forEach>
    </tbody>
</table>

<h2>Награды</h2>
<table>
    <thead>
    <tr>
        <th>ID</th>
        <th>Год</th>
        <th>Место</th>
    </tr>
    </thead>
    <tbody>
    <c:forEach var="award" items="${awards}">
        <tr>
            <td>${award.id()}</td>
            <td>${award.year()}</td>
            <td>${award.place()}</td>
        </tr>
    </c:forEach>
    </tbody>
</table>

<h2>Добавить альбом</h2>
<form action="${pageContext.request.contextPath}/album/add" method="post">
    <label for="title">Название:</label>
    <input type="text" id="title" name="title" required><br>
    <label for="release">Дата релиза:</label>
    <input type="date" id="release" name="release" required><br>
    <input type="submit" value="Добавить альбом">
</form>

<hr>
<a href="<c:url value='/logout' />">Выход</a>
</body>
</html>
