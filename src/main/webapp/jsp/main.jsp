<%@ page pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<c:set var="language" value="${not empty param.language ? param.language : not empty language ? language : pageContext.request.locale}" scope="session"/>
<fmt:setLocale value="${language}"/>
<fmt:setBundle basename=".content"/>
<head>
    <title>FCQ-Delivery</title>
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css"
          type="text/css">
    <link rel="stylesheet" href="../css/theme.css" type="text/css">
</head>
<jsp:include page="_lang.jsp"/>
<jsp:include page="_header.jsp"></jsp:include>
<body>
<div class="py-5 style">
    <div class="container">
        <div class="row">
            <div class="col-md-5 order-2 order-md-1">
                <img class="d-block img-fluid rounded-circle mx-auto" src="../img/main.png"></div>
            <div class="col-md-7 order-1 order-md-2">
                <h3 class="">FCQ-Delivery</h3>
                <p class="my-3 style"><fmt:message key="main.description.text"/></p>
            </div>
        </div>
    </div>
</div>
<div class="py-2 text-center bg-light style">
    <div class="container">
        <div class="row">
            <div class="col-md-4 p-4" style="margin-left: 10%">
                <img class="img-fluid d-block rounded-circle mx-auto" src="../img/m1.PNG">
                <p class="my-4"><i><fmt:message key="main.description.lable1"/></i></p>
            </div>
            <div class="col-md-4 p-4" style="margin-left: 10%">
                <img class="img-fluid d-block rounded-circle mx-auto" src="../img/m3.PNG">
                <p class="my-4"><i><fmt:message key="main.description.lable3"/></i></p>
            </div>
        </div>
    </div>
</div>

</body>
<jsp:include page="_footer.jsp"></jsp:include>
