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
    <div class="row">
        <div class="col-md-12 bg-light">
            <h3><fmt:message key="emailField"/>: ${loginedUser.email}, <fmt:message
                    key="userInfo.label.role"/>${loginedUser.role.role}</h3>
            <h6 style="color: red">${errorLoginMessage}${successfulMsg}</h6>
            <form method="POST" action="${pageContext.request.contextPath}/doLogin">
                <input hidden name="action" value="saveUser">
                <div class="form-group"><label><fmt:message key="fNameField"/></label>
                    <input required type="text" class="form-control" name="fName" value="${loginedUser.firstName}"></div>
                <div class="form-group"><label><fmt:message key="lNameField"/></label>
                    <input required type="text" class="form-control" name="lName" value="${loginedUser.lastName}"></div>
                <div class="form-group"><label><fmt:message key="userInfo.label.phone"/></label>
                    <input required minlength="13" maxlength="13" name="phone" type="text" class="form-control" name=""
                           value="${loginedUser.phone}"></div>
                <button type="submit" class="btn btn-secondary" style="background-color: green; margin-left: 45%">
                    <fmt:message key="button.saveButtob"/></button>
            </form>
        </div>
    </div>
</div>
<script src="http://ajax.googleapis.com/ajax/libs/jquery/1.10.1/jquery.min.js"></script>
<script src="../js/index.js"></script>
</body>
<jsp:include page="_footer.jsp"></jsp:include>
</html>