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
    <h5 style="margin-left: 40%; color: green; text-emphasis: #1c7430"><fmt:message key="fillForm.label"/></h5>
    <div class="row">
        <h4 style="color: red; position: center">${errorMessage}${successfulMsg}</h4>
        <div class="col-md-12 bg-light">
            <form method="POST" action="${pageContext.request.contextPath}/applications">
                <div class="form-group"><label><fmt:message
                        key="appStartPoint.label"/></label>
                    <input required type="text" class="form-control" name="start" value="${application.startPoint}">
                </div>

                <div class="form-group"><label><fmt:message
                        key="appFinishPoint.label"/></label>
                    <input required type="text" class="form-control" name="finish"
                           value="${application.finishPoint}">
                </div>
                <div class="form-group"><label><fmt:message
                        key="weight.label"/></label>
                    <input required name="weight" type="text" class="form-control" value="${application.cargo}"
                           pattern="[0-9]\d*">
                </div>
                <div class="form-group"><label><fmt:message
                        key="dateOfStart.label"/></label>
                    <input required name="date" type="date" class="form-control" value="${application.deliveryDate}">
                </div>
                <div class="form-group"><label><fmt:message
                        key="comment.label"/></label>
                    <input required name="comment" type="text" class="form-control" value="${applicationcomment}">
                </div>

                <input hidden name="action" value="save_application">
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