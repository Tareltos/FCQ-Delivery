<%@ page pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<c:set var="language"
       value="${not empty param.language ? param.language : not empty language ? language : pageContext.request.locale}"
       scope="session"/>
<fmt:setLocale value="${language}"/>
<fmt:setBundle basename=".content"/>
<nav class="navbar navbar-expand-md bg-primary navbar-dark">
    <div class="container">
        <a class="navbar-brand" href="#"><i class="fa d-inline fa-lg fa-truck"></i><b>&nbsp; FCQ-Delivery</b></a>
        <button class="navbar-toggler navbar-toggler-right" type="button" data-toggle="collapse"
                data-target="#navbar2SupportedContent" aria-controls="navbar2SupportedContent"
                aria-expanded="false" aria-label="Toggle navigation"><span class="navbar-toggler-icon"></span></button>
        <div class="collapse navbar-collapse text-center justify-content-end" id="navbar2SupportedContent">
            <ul class="navbar-nav">
                <li class="nav-item">
                    <a class="nav-link" href="${pageContext.request.contextPath}/main?action=main"><i class="fa d-inline fa-lg fa-home"></i> <fmt:message
                            key="menu.button.main"/>
                        <br>
                    </a>
                </li>

                <li class="nav-item">
                                     <a class="nav-link" href="#"><i
                            class="fa d-inline fa-lg fa-volume-control-phone"></i>&nbsp;<fmt:message
                            key="menu.button.contacts"/></a>
                </li>
                <li class="nav-item">
                    <a class="nav-link" href="#"><i class="fa d-inline fa-lg fa-taxi"></i>&nbsp;<fmt:message
                            key="menu.button.couriers"/></a>
                </li>
                <li class="nav-item">
                    <a class="nav-link" href="#"><i class="fa d-inline fa-lg fa-hand-o-right"></i>&nbsp;<fmt:message
                            key="menu.button.doapplication"/></a>
                </li>
                <li class="nav-item">
                    <a class="nav-link" href="#"><i class="fa d-inline fa-lg fa-align-justify"></i>&nbsp;<fmt:message
                            key="menu.button.applications"/><br>
                    </a>
                </li>
            </ul>
       <form method="GET" action="${pageContext.request.contextPath}/singIn">
             <input type="hidden" name="action" value="singin"/>
            <button class="btn navbar-btn ml-2 text-white btn-secondary" ><i class="fa d-inline fa-lg fa-user-circle-o"></i><fmt:message key="menu.button.cabinet"/></button>
        </form>
        </div>
    </div>
</nav>

