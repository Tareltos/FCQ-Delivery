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
        <form class="form-inline m-0" style="margin-top: 2%; width: 100%; background-color: #7e7e7e;" method="POST"
              action="${pageContext.request.contextPath}/applications">
            <input hidden name="action" value="find_applications">
            <th>
            <td style="margin-right: 2%"><fmt:message key="search.label"/></td>
            <td style="margin-right: 2%">
                <input type="text" name="email"
                       pattern="^[A-Za-z0-9][A-Za-z0-9\.\-_]*[A-Za-z0-9]*@([A-Za-z0-9]+([A-Za-z0-9-]*[A-Za-z0-9]+)*\.)+[A-Za-z]*$"
                       class="form-control mr-2" type="text" placeholder="email@amail.com">
            </td>
            <td style="margin-right: 2%">
                <select required name="status" type="text" class="form-control">
                    <option value="all"><fmt:message key="allAppStatus.label"/></option>
                    <option value="new"><fmt:message key="newAppStatus.label"/></option>
                    <option value="waiting"><fmt:message key="waitingAppStatus.label"/></option>
                    <option value="confirmed"><fmt:message key="confirmedAppStatus.label"/></option>
                    <option value="delivered"><fmt:message key="deliveredAppStatus.label"/></option>
                    <option value="canceled"><fmt:message key="canceledAppStatus.label"/></option>
                </select>
            </td>
            <td style="margin-right: 2%">
                <button class="btn btn-warning" type="submit"><fmt:message key="searchButton.text"/></button>
            </td>
            </th>
        </form>
        <table class="table table-hover">
            <thead class="thead-inverse">
            <tr>
                <th><fmt:message key="emailField"/></th>
                <th><fmt:message key="appStartPoint.label"/></th>
                <th><fmt:message key="appFinishPoint.label"/></th>
                <th><fmt:message key="dateOfStart.label"/></th>
                <th><fmt:message key="comment.label"/></th>
                <th><fmt:message key="userInfo.label.status"/></th>
                <th/>
            </tr>
            </thead>
            <tbody>
            <c:if test="${appList.size() ==0}">
                <h3 style="color: #491217;"><fmt:message
                        key="searchingNullResult.text"></fmt:message></h3>
            </c:if>
            <c:forEach items="${appList}" var="app">
                <tr>
                    <td>${app.owner.email}</td>
                    <td>${app.startPoint}</td>
                    <td>${app.finishPoint}</td>
                    <td>${app.deliveryDate}</td>
                    <td>${app.comment}</td>
                    <td>
                        <c:if test="${app.status.status=='new'}">
                            <button type="button" class="btn btn-primary" disabled><fmt:message
                                    key="newAppStatus.label"/></button>
                        </c:if>
                        <c:if test="${app.status.status=='waiting'}">
                            <button type="button" class="btn btn-warning" disabled><fmt:message
                                    key="waitingAppStatus.label"/></button>
                        </c:if>
                        <c:if test="${app.status.status=='confirmed'}">
                            <button type="button" class="btn btn-danger" disabled><fmt:message
                                    key="confirmedAppStatus.label"/></button>
                        </c:if>
                        <c:if test="${app.status.status=='delivered'}">
                            <button type="button" class="btn btn-success" disabled><fmt:message
                                    key="deliveredAppStatus.label"/></button>
                        </c:if>
                        <c:if test="${app.status.status=='canceled'}">
                            <button type="button" class="btn btn-info"  title="${app.cancelationReason}" disabled><fmt:message
                                    key="canceledAppStatus.label"/></button>
                        </c:if>
                    </td>
                    <td>
                        <form method="GET" action="${pageContext.request.contextPath}/applications">
                            <input hidden name="action" value="get_app_details">
                            <input hidden name="id" value="${app.id}">
                            <button title="<fmt:message key="info.title"/> ">
                                <i class="fa d-inline fa-lg fa-info-circle"></i>
                            </button>
                        </form>
                        <c:if test="${app.status.status=='new'}">
                            <form method="GET" action="${pageContext.request.contextPath}/applications">
                                <input hidden name="action" value="delete_application">
                                <input hidden name="id" value="${app.id}">
                                <button title="<fmt:message key="deleteApplication.button"/> ">
                                    <i class="fa d-inline fa-lg fa-trash"></i>
                                </button>
                            </form>
                        </c:if>
                    </td>
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