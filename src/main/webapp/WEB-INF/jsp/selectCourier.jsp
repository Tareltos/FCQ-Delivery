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
                    <input required name="comment" type="text" class="form-control" value="${application.comment}">
                </div>
                <hr size="1px" style=" background-color: #1c7430">
                <select required name="courier" type="text" class="form-control">
                    <c:forEach  items="${couriers}" var="courier">
                        <option value="${courier.carNumber}">${courier.carProducer} ${courier.carModel} ${courier.kmTax}</option>
                    </c:forEach>
                </select>
                <div class="form-group"><label><fmt:message
                        key="distance.label"/></label>
                    <input required name="distance" type="text" class="form-control" pattern="[0-9]\d*" >
                </div>
                <hr size="1px" style=" background-color: #1c7430">
                <input hidden name="id" value="${application.id}">
                <input hidden name="locale" value="${language}">
                <input hidden name="action" value="calculate_price_and_save">
                <button type="submit" class="btn btn-secondary" style="background-color: green; margin-left: 45%">
                    <fmt:message key="button.saveButtob"/></button>
            </form>
        </div>

    </div>
</div>

<script src="http://ajax.googleapis.com/ajax/libs/jquery/1.10.1/jquery.min.js"></script>
<script src="../js"></script>
</body>
<jsp:include page="_footer.jsp"></jsp:include>
</html>