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
    <link rel="stylesheet" href="../css/style.css" media="screen" type="text/css"/>
</head>
<jsp:include page="_header.jsp"></jsp:include>
<body>
<div class="box">
    <nav id="tabs" class="tabs">
        <a id="tabLogin" class="iconLogin active blueBox" title="<fmt:message key="loginForm.singin"/>"></a>
        <a id="tabRegister" class="iconRegister greenBox" title="<fmt:message key="loginForm.register"/>"></a>
        <a id="tabForgot" class="iconForgot redBox" title="<fmt:message key="loginForm.forgot"/>"></a>
    </nav>


    <div class="containerWrapper">
        <!-- login container -->
        <div id="containerLogin" class="tabContainer active">
            <form method="POST" action="${pageContext.request.contextPath}/doLogin">
                <input hidden name="action" value="log_in">
                <h2 class="loginTitle"><fmt:message key="loginForm.loginTitle"/></h2>
                <c:if test="${message != null}">
                    <h6 style="color: red"><fmt:message key="${message}"/></h6>
                </c:if>
                <br>
                <div class="loginContent">
                    <div class="inputWrapper">
                        <input required type="text" name="mail"
                               pattern="^[A-Za-z0-9][A-Za-z0-9\.\-_]*[A-Za-z0-9]*@([A-Za-z0-9]+([A-Za-z0-9-]*[A-Za-z0-9]+)*\.)+[A-Za-z]*$"
                               title="<fmt:message key="emailFieldTitle"/>"
                               placeholder="<fmt:message key="emailField"/>"/>
                    </div>
                    <div class="inputWrapper">
                        <input required minlength="5" type="password" name="password"
                               placeholder="<fmt:message key="passField"/>"/>
                    </div>
                </div>
                <button class="blueBox"><span class="iconLogin"></span> <fmt:message key="button.singin"/></button>
                <div class="clear"></div>
            </form>
        </div>

        <!-- register container -->
        <div id="containerRegister" class="tabContainer">
            <form method="POST" action="${pageContext.request.contextPath}/doRegistration">
                <input hidden name="action" value="registration">
                <h2 class="loginTitle"><fmt:message key="loginForm.regTitle"/></h2>
                <div class="registerContent">
                    <div class="inputWrapper">
                        <input required minlength="1" name="fName" type="text"
                               placeholder="<fmt:message key="fNameField"/>"/>
                    </div>
                    <div class="inputWrapper">
                        <input required minlength="1" name="lName" type="text"
                               placeholder="<fmt:message key="lNameField"/>"/>
                    </div>

                    <div class="inputWrapper">
                        <input required type="text" name="mail"
                               pattern="^[A-Za-z0-9][A-Za-z0-9\.\-_]*[A-Za-z0-9]*@([A-Za-z0-9]+([A-Za-z0-9-]*[A-Za-z0-9]+)*\.)+[A-Za-z]*$"
                               title="<fmt:message key="emailFieldTitle"/>"
                               placeholder="<fmt:message key="emailField"/>"/>
                    </div>
                    <div class=" inputWrapper">
                        <input required minlength="13" maxlength="13" name="phone" type="text"
                               placeholder="<fmt:message key="phoneField"/>"/>
                    </div>
                </div>
                <button class="greenBox"><span class="iconRegister"></span> <fmt:message key="button.register"/>
                </button>
                <div class="clear"></div>
            </form>
        </div>
        <div class="clear"></div>

        <!-- forgot container -->
        <div id="containerForgot" class="tabContainer">
            <form method="POST" action="${pageContext.request.contextPath}/reset">
                <input hidden name="action" value="reset_pass">
                <h2 class="loginTitle"><fmt:message key="loginForm.forgotTitle"/></h2>
                <div class="loginContent">
                    <div class="inputWrapper">
                        <input required type="text" name="mail"
                               pattern="^[A-Za-z0-9][A-Za-z0-9\.\-_]*[A-Za-z0-9]*@([A-Za-z0-9]+([A-Za-z0-9-]*[A-Za-z0-9]+)*\.)+[A-Za-z]*$"
                               title="<fmt:message key="emailFieldTitle"/>"
                               placeholder="<fmt:message key="emailField"/>"/>
                    </div>
                    <div class="placeholder"></div>
                </div>
                <button class="redBox"><span class="iconForgot"></span> <fmt:message key="button.reset"/></button>
                <div class="clear"></div>
            </form>
        </div>
        <div class="clear"></div>
    </div>
</div>
<script src="http://ajax.googleapis.com/ajax/libs/jquery/1.10.1/jquery.min.js"></script>
<script src="../js/index.js"></script>
</body>
<jsp:include page="_footer.jsp"></jsp:include>
</html>