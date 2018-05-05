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
    <title><fmt:message key="page.title.label"/></title>
    <link rel='stylesheet prefetch' href='http://netdna.bootstrapcdn.com/font-awesome/3.2.0/css/font-awesome.min.css'>
</head>
<jsp:include page="_header.jsp"></jsp:include>
<body>
<div class="container" style="min-height: 80%;">
    <div class="row">

            <c:if test="${application.status.status=='new'}">
               <h3><fmt:message key="userInfo.label.status"/></h3> <button type="button" class="btn btn-primary"
                        style="margin-bottom: 2%;">${application.status.status}</button>
            </c:if>
            <c:if test="${application.status.status=='waiting'}">
                <h3><fmt:message key="userInfo.label.status"/></h3> <button type="button" class="btn btn-warning"
                        style="margin-bottom: 2%;">${application.status.status}</button>
            </c:if>
            <c:if test="${application.status.status=='confirmed'}">
                <h3><fmt:message key="userInfo.label.status"/></h3> <button type="button" class="btn btn-danger"
                        style="margin-bottom: 2%;">${application.status.status}</button>
            </c:if>
            <c:if test="${application.status.status=='delivered'}">
                <h3><fmt:message key="userInfo.label.status"/></h3> <button type="button" class="btn btn-success"
                        style="margin-bottom: 2%;">${application.status.status}</button>
            </c:if>
            <c:if test="${application.status.status=='canceled'}">
                <h3><fmt:message key="userInfo.label.status"/></h3><button type="button" class="btn btn-info"
                        style="margin-bottom: 2%;">${application.status.status}</button>
            </c:if>
        <div class="col-md-12 bg-light">
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
            <h3 style="color: #1c7430;"><fmt:message key="selectedCourierNumber.label"/></h3>
            <c:if test="${null!=application.courier}">
                <h4>${application.courier.carNumber} ${application.courier.carProducer} ${application.courier.carModel}</h4>
                <h4><fmt:message key="fNameField"/>: ${application.courier.driverName} <fmt:message
                        key="footer.text.phone"/> ${application.courier.driverPhone}
                    <fmt:message key="footer.text.email"/> ${application.courier.driverEmail}</h4>
                <hr size="1px" style=" background-color: #1c7430">
                <h4 style=""><fmt:message key="appTotalPrice.label"/>  ${application.price}</h4>
            </c:if>
            <c:if test="${null==application.courier}">
                <fmt:message key="courierNotSelected.label"/>
                <c:if test="${'manager'== loginedUser.role.role}">
                    <a href="${pageContext.request.contextPath}/applications?action=select_courier&id=${application.id}"
                       type="submit" class="btn btn-danger">
                        <fmt:message key="selectCourier.button"/></a>
                </c:if>
            </c:if>
            <c:if test="${null!=application.courier}">
                <c:if test="${'customer'== loginedUser.role.role}">
                    <c:if test="${'waiting'== application.status.status}">
                        <a data-toggle="modal" data-target="#paymentModal" class="btn btn-warning"><fmt:message
                                key="submitApplication.button"/></a>
                        <a data-toggle="modal" data-target="#cancelModal" class="btn btn-primary"><fmt:message
                                key="cancelApplication.button"/></a>
                    </c:if>
                </c:if>
            </c:if>
            <hr size="1px" style=" background-color: #1c7430">
            <c:if test="${null!=application.courier}">
                <c:if test="${'customer'== loginedUser.role.role}">
                    <c:if test="${'confirmed'== application.status.status}">
                        <a href="${pageContext.request.contextPath}/applications?action=complete_application&id=${application.id}"
                           type="submit" class="btn btn-warning">
                            <fmt:message key="completeApplication.button"/></a>
                    </c:if>
                </c:if>
            </c:if>
            <hr size="1px" style=" background-color: #1c7430">
        </div>
    </div>
</div>
<div id="cancelModal" class="modal fade" tabindex="-1" role="dialog" aria-labelledby="gridModalLabel"
     aria-hidden="true">
    <div class="modal-dialog" role="document">
        <div class="modal-content">
            <h5 style="margin-left: 2%; margin-top: 3%; color: green; text-emphasis: #0b0b0b"><fmt:message
                    key="cancelModal.reason.label"/></h5>
            <form method="POST" action="${pageContext.request.contextPath}/applications">
                <input hidden name="action" value="cancel_application">
                <input hidden name="id" value="${application.id}">
                <div class="form-group" style="margin-left: 5%; margin-right: 5%;">
                    <input required type="text" class="form-control" name="reason">
                </div>
                <span>
                <button type="submit" style="margin-left: 35%; margin-bottom: 2%;" class="btn btn-success">
                    <fmt:message key="cancelApplication.button"/>
                </button>
                </span>
            </form>
        </div><!-- /.модальное окно-Содержание -->
    </div><!-- /.модальное-->
</div>
<div id="paymentModal" class="modal fade" tabindex="-1" role="dialog" aria-labelledby="gridModalLabel"
     aria-hidden="true">
    <div class="modal-dialog" role="document">
        <div class="modal-content">
            <h5 style="margin-left: 2%; margin-top: 3%; color: green; text-emphasis: #0b0b0b"><fmt:message
                    key="fillForm.label"/></h5>
            <form method="POST" action="${pageContext.request.contextPath}/applications">
                <input hidden name="action" value="do_payment">
                <input hidden name="id" value="${application.id}">
                <div class="form-group" style="margin-left: 5%; margin-right: 5%;"><label><fmt:message
                        key="paymentForm.cardNumber.label"/></label>
                    <input required type="text" class="form-control" name="cardNumber" placeholder="**** **** **** ****"
                           minlength="16" maxlength="16">
                </div>
                <div class="form-group" style="margin-left: 5%; margin-right: 5%;"><label><fmt:message
                        key="paymentForm.expiration.label"/></label>
                    <label><fmt:message key="paymentForm.expirationM.label"/></label>
                    <select required name="expirationMonth" type="text">
                        <option value="-">-</option>
                        <option value="1">1</option>
                        <option value="2">2</option>
                        <option value="3">3</option>
                        <option value="4">4</option>
                        <option value="5">5</option>
                        <option value="6">6</option>
                        <option value="7">7</option>
                        <option value="8">8</option>
                        <option value="9">9</option>
                        <option value="10">10</option>
                        <option value="11">11</option>
                        <option value="12">12</option>
                    </select>
                    <label><fmt:message key="paymentForm.expirationY.label"/></label>
                    <input required type="text" name="expirationYear" placeholder="19" minlength="2" maxlength="2">
                </div>

                <div class="form-group" style="margin-left: 5%; margin-right: 5%;">
                    <label><fmt:message key="paymentForm.owner.label"/></label>
                    <input required type="text" class="form-control" name="owner" placeholder="IVAN IVANOV">
                </div>
                <div class="form-group" style="margin-left: 5%; margin-right: 5%;">
                    <label><fmt:message key="paymentForm.csv.label"/></label>
                    <input required type="text" class="form-control" name="csv" maxlength="3" minlength="3"
                           placeholder="123">
                </div>

            </form>
            <span>
                <button style="margin-left: 35%; margin-bottom: 2%;" type="button" class="btn btn-warning"
                        data-dismiss="modal"><fmt:message key="cancelButton.text"/></button>
                <button id="formSubmit" style="margin-bottom: 2%;" type="button" class="btn btn-success"
                        onclick="formSubmit"><fmt:message key="submitApplication.button"/> </button>
            <script language="javascript">
                 $('#formSubmit').on('click', function () {
                     $('form').submit();
                 })
	        </script>
            </span>
        </div><!-- /.модальное окно-Содержание -->
    </div><!-- /.модальное-->
</div>
<script src="http://ajax.googleapis.com/ajax/libs/jquery/1.10.1/jquery.min.js"></script>
<script src="../js/index.js"></script>
</body>
<jsp:include page="_footer.jsp"></jsp:include>
</html>