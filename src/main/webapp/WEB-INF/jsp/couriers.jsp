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
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/theme.css" type="text/css">
    <title><fmt:message key="page.title.label"/></title>
    <link rel='stylesheet prefetch' href='http://netdna.bootstrapcdn.com/font-awesome/3.2.0/css/font-awesome.min.css'>
</head>
<jsp:include page="_header.jsp"></jsp:include>
<body>
<div class="container" style="min-height: 80%;">
    <div class="row">
        <c:forEach items="${courierList}" var="courier">
            <div class="col-md-4 p-4" style="border: 4px double black; background: #dbe1f5;">
                <img class="img-fluid d-block rounded-0 mx-auto" src="${pageContext.request.contextPath}/files/${courier.imageFileName}">
                <p><i><fmt:message key="courierCarModel.text"/>${courier.carProducer} ${courier.carModel}</i></p>
                <p><i><fmt:message key="courierMaxCargo.text"/>${courier.maxCargo}</i></p>
                <p><i><fmt:message key="courierTax.text"/>${courier.kmTax}</i></p>
                <p><i><fmt:message key="courierStatus.text"/>${courier.status.status}</i></p>
                <c:if test="${loginedUser.role.role =='manager'}">
                    <a href="${pageContext.request.contextPath}/courierForm?action=edit_courier&carNumber=${courier.carNumber}"><img
                            src="${pageContext.request.contextPath}/img/edit.png" width="20" height="20" title="<fmt:message key="editImgTitle.text"/>">
                    </a>
                </c:if>
            </div>
        </c:forEach>
    </div>
    <ul class="pagination" style="margin-bottom: 3%; margin-left: 45%; margin-top: 15%;">
        <c:if test="${firstRow !='0'}">
            <li class="page-item">
                <a class="page-link"
                   href="${pageContext.request.contextPath}/courierForm?action=get_couriers_pg&firstRow=${firstRow-rowCount}&rowCount=${rowCount}"
                   aria-label="Previous" style="background-color: #bf1d1c">
                    <span aria-hidden="true">«</span>
                    <span class="sr-only">Previous</span>
                </a>
            </li>
        </c:if>
        <c:if test="${firstRow+rowCount < allCount}">
        <li class="page-item">
            <a class="page-link"
               href="${pageContext.request.contextPath}/courierForm?action=get_couriers_pg&firstRow=${firstRow+rowCount}&rowCount=${rowCount}"
               aria-label="Next" style="background-color: #bf1d1c">
                <span aria-hidden="true">»</span>
                <span class="sr-only">Next</span>
            </a>
        </li>
        </c:if>
    </ul>
</div>
<script src="http://ajax.googleapis.com/ajax/libs/jquery/1.10.1/jquery.min.js"></script>
<script src="../js"></script>
</body>
<jsp:include page="_footer.jsp"></jsp:include>
</html>