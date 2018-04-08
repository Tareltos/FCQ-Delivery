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
            <form method="POST" action="${pageContext.request.contextPath}/createNewCourier">
                <div class="form-group"><label><fmt:message
                        key="carNumber.label"/></label>
                    <input required minlength="8" maxlength="8" type="text" class="form-control" name="carNumber"
                           placeholder="2222AA-7" value="${courier.carNumber}">
                </div>
                <div class="form-group"><label><fmt:message
                        key="carProducer.label"/></label>
                    <input required minlength="2" type="text" class="form-control" name="carProducer" placeholder="VW"
                           value="${courier.carProducer}">
                </div>
                <div class="form-group"><label><fmt:message
                        key="carModel.label"/></label>
                    <input required minlength="2" type="text" class="form-control" name="carModel" placeholder="Polo"
                           value="${courier.carModel}">
                </div>
                <div class="form-group"><label><fmt:message
                        key="carPhoto.label"/></label>
                    <input type="file" class="form-control-file" id="exampleFormControlFile1" name="img"
                           value="${courier.imageFileName}">
                </div>
                <div class="form-group"><label><fmt:message
                        key="fNameField"/></label>
                    <input required type="text" class="form-control" name="name"
                           placeholder="<fmt:message key="fNameField"/>" value="${courier.driverName}">
                </div>
                <div class="form-group"><label><fmt:message
                        key="userInfo.label.phone"/></label>
                    <input required minlength="13" maxlength="13" name="phone" type="text" class="form-control"
                           placeholder="<fmt:message key="phoneField"/>" value="${courier.driverPhone}">
                </div>
                <div class="form-group"><label><fmt:message
                        key="emailField"/></label>
                    <input required name="email" type="text" class="form-control" placeholder="driver@email.com"
                           value="${courier.driverEmail}">
                </div>
                <div class="form-group"><label><fmt:message
                        key="carMaxCargo.label"/></label>
                    <input required name="cargo" type="text" class="form-control" placeholder="1500"
                           value="${courier.maxCargo}">
                </div>
                <div class="form-group"><label><fmt:message
                        key="carTax.label"/></label>
                    <input required name="tax" type="text" class="form-control" placeholder="0.34"
                           value="${courier.kmTax}">
                </div>
                <div class="form-group" style="margin-left: 5%; margin-right: 5%;"><label><fmt:message
                        key="userInfo.label.status"/></label>
                    <select required name="status" type="text" class="form-control">
                        <c:if test="${courier.status.status =='active'}">
                            <option value="active"><fmt:message
                                    key="statusActive.label"/></option>
                            <option value="blocked"><fmt:message
                                    key="statusBlocked.label"/></option>
                        </c:if>
                        <option value="blocked"><fmt:message
                                key="statusBlocked.label"/></option>
                        <option value="active"><fmt:message
                                key="statusActive.label"/></option>
                    </select>
                </div>

                <input hidden name="action" value="create_courier">
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