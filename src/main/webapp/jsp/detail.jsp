<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: zhanghuan
  Date: 2018/5/22
  Time: 17:35
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" %>
<html>
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <!-- 上述3个meta标签*必须*放在最前面，任何其他内容都*必须*跟随其后！ -->

    <!-- 新 Bootstrap 核心 CSS 文件 -->
    <link rel="stylesheet" href="//cdn.bootcss.com/bootstrap/3.3.5/css/bootstrap.min.css">

    <!-- HTML5 shim and Respond.js for IE8 support of HTML5 elements and media queries -->
    <!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
    <!--[if lt IE 9]>
    <script src="//cdn.bootcss.com/html5shiv/3.7.2/html5shiv.min.js"></script>
    <script src="//cdn.bootcss.com/respond.js/1.4.2/respond.min.js"></script>
    <![endif]-->

    <!-- jQuery文件  务必在bootstrap.min.js 之前引入 -->
    <script src="//cdn.bootcss.com/jquery/1.11.3/jquery.min.js"></script>
    <!-- 最新的 Bootstrap 核心 JavaScript 文件 -->
    <script src="//cdn.bootcss.com/bootstrap/3.3.5/js/bootstrap.min.js"></script>
    <title>list</title>
</head>
<body class="container">
<h1>Book Detail</h1>
${book}
<form action="/book/${book.bookId}/appoint" method="post">
    <div class="form-group">
        <label for="bookId">bookId</label>
        <input class="form-control" id="bookId" type="text" value="<c:out value='${book.bookId}'/>" name="bookId" />
    </div>
    <div class="form-group">
        <label for="name">name</label>
        <input class="form-control " id="name" type="text" value="<c:out value='${book.name}'/>" name="name" />
    </div>
    <div class="form-group">
        <label for="number">number</label>
        <input class="form-control " id="number" type="text" value="<c:out value='${book.number}'/>" name="number" />
    </div>
    <div class="form-group">
        <label for="number">studentId</label>
        <input class="form-control " id="studentId" type="text" name="studentId" />
    </div>
    <div class="form-group">
        <button type="submit" class="form-control btn btn-primary">秒杀</button>
    </div>
</form>
</body>
</html>
