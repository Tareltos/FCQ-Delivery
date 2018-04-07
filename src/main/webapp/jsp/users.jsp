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
                <th><fmt:message key="fNameField"/></th>
                <th><fmt:message key="lNameField"/></th>
                <th><fmt:message key="userInfo.label.phone"/></th>
                <th><fmt:message key="userInfo.label.role"/></th>
                <th><fmt:message key="userInfo.label.status"/></th>
                <th>
                    <a data-toggle="modal" data-target="#newUserModal" title="<fmt:message key="addUserImg"/>"><img
                            src="img/createUser.png" width="30"
                            height="30"></a>
                </th>
            </tr>
            </thead>
            <tbody>
            <c:forEach items="${userList}" var="user">
                <tr>
                    <td>${user.email}</td>
                    <td>${user.firstName}</td>
                    <td>${user.lastName}</td>
                    <td>${user.phone}</td>
                    <td>${user.role.role}</td>
                    <td>${user.status.status}</td>
                    <td>
                        <c:if test="${user.status.status =='active'}">
                            <form method="POST" action="${pageContext.request.contextPath}/users">
                                <input hidden name="mail" value="${user.email}">
                                <input hidden name="action" value="change_status">
                                <button title="<fmt:message key="blockImg"/> ">
                                    <i class="fa d-inline fa-lg fa-lock"></i>
                                </button>
                            </form>
                        </c:if>
                        <c:if test="${user.status.status =='blocked'}">
                            <form method="POST" action="${pageContext.request.contextPath}/users">
                                <input hidden name="mail" value="${user.email}">
                                <input hidden name="action" value="change_status">
                                <button title="<fmt:message key="unBlockImg"/> ">
                                    <i class="fa d-inline fa-lg fa-unlock"></i>
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
<div id="newUserModal" class="modal fade" tabindex="-1" role="dialog" aria-labelledby="gridModalLabel"
     aria-hidden="true">
    <div class="modal-dialog" role="document">
        <div class="modal-content">
            <h5 style="margin-left: 2%; margin-top: 3%; color: green; text-emphasis: #0b0b0b">Заполните форму</h5>
            <form method="POST" action="${pageContext.request.contextPath}/users">
                <input hidden name="action" value="create_user">
                <div class="form-group" style="margin-left: 5%; margin-right: 5%;"><label><fmt:message
                        key="emailField"/></label>
                    <input required type="text" class="form-control" name="mail">
                </div>
                <div class="form-group" style="margin-left: 5%; margin-right: 5%;"><label><fmt:message
                        key="fNameField"/></label>
                    <input required type="text" class="form-control" name="fName">
                </div>
                <div class="form-group" style="margin-left: 5%; margin-right: 5%;"><label><fmt:message
                        key="lNameField"/></label>
                    <input required type="text" class="form-control" name="lName"></div>
                <div class="form-group" style="margin-left: 5%; margin-right: 5%;"><label><fmt:message
                        key="userInfo.label.phone"/></label>
                    <input required minlength="13" maxlength="13" name="phone" type="text" class="form-control">
                </div>
                <div class="form-group" style="margin-left: 5%; margin-right: 5%;"><label><fmt:message
                        key="userInfo.label.role"/></label>
                    <select required name="role" type="text" class="form-control">
                        <option value="admin">Администратор</option>
                        <option value="customer">Клиент</option>
                        <option value="manager">Менеджер</option>
                    </select>
                </div>
            </form>
            <span>
                <button style="margin-left: 35%; margin-bottom: 2%;" type="button" class="btn btn-warning"
                        data-dismiss="modal">Отмена
                </button>
                <button id="formSubmit" style="margin-bottom: 2%;" type="button" class="btn btn-success"
                        onclick="formSubmit">Создать
                </button>
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