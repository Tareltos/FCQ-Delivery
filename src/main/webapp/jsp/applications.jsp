<%@ page pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<c:set var="language"
       value="${not empty param.language ? param.language : not empty language ? language : pageContext.request.locale}"
       scope="session"/>
<fmt:setLocale value="${language}"/>
<fmt:setBundle basename=".content"/>
<!DOCTYPE html>
<html lang="${language}">
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css"
          type="text/css">
    <link rel="stylesheet" href="../css/theme.css" type="text/css">
    <title><fmt:message key="loginForm.singin"/></title>
    <link rel='stylesheet prefetch' href='http://netdna.bootstrapcdn.com/font-awesome/3.2.0/css/font-awesome.min.css'>
</head>
<jsp:include page="_header.jsp"></jsp:include>
<body>
<div class="container" style="min-height: 80%;">
    <h6 style="color: red">${errorMessage}${successfulMsg}</h6>
    <div class="row">
        <table class="table table-hover">
            <thead class="thead-inverse">
            <tr>
                <th><fmt:message key="emailField"/></th>
                <th><fmt:message key="appStartPoint.label"/></th>
                <th><fmt:message key="appFinishPoint.label"/></th>
                <th><fmt:message key="dateOfStart.label"/></th>
                <th><fmt:message key="comment.label"/></th>
                <th><fmt:message key="userInfo.label.status"/></th>
            </tr>
            </thead>
            <tbody>
            <c:forEach items="${appList}" var="app">
                <tr>
                    <td>${app.owner.email}</td>
                    <td>${app.startPoint}</td>
                    <td>${app.finishPoint}</td>
                    <td>${app.deliveryDate}</td>
                    <td>${app.comment}</td>
                    <td>${app.status.status}</td>
                </tr>
            </c:forEach>
            </tbody>
        </table>
    </div>
</div>
<script src="http://ajax.googleapis.com/ajax/libs/jquery/1.10.1/jquery.min.js"></script>
<script src="../js/index.js"></script>
</body>
<jsp:include page="_footer.jsp"></jsp:include>
</html>