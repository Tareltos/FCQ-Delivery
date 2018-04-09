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
        <c:forEach items="${courierList}" var="courier">
            <div class="col-md-4 p-4" style="border: 4px double black; background: #dbe1f5;">
                <img class="img-fluid d-block rounded-0 mx-auto" src="../img/${courier.imageFileName}">
                <p><i>Автомобиль: ${courier.carProducer} ${courier.carModel}</i></p>
                <p><i>Грузоподъемность: ${courier.maxCargo}</i></p>
                <p><i>Тарив за км: ${courier.kmTax}</i></p>
                <c:if test="${loginedUser.role.role =='manager'}">
                    <a href="${pageContext.request.contextPath}/courierForm?action=edit_courier&carNumber=${courier.carNumber}"><img
                            src="img/edit.png" width="20" height="20" title="Редактировать">
                    </a>
                </c:if>
            </div>
        </c:forEach>
    </div>
</div>
<script src="http://ajax.googleapis.com/ajax/libs/jquery/1.10.1/jquery.min.js"></script>
<script src="../js/index.js"></script>
</body>
<jsp:include page="_footer.jsp"></jsp:include>
</html>