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
        <a class="navbar-brand"><i class="fa d-inline fa-lg fa-truck"></i><b>&nbsp; FCQ-Delivery</b></a>
        <button class="navbar-toggler navbar-toggler-right" type="button" data-toggle="collapse"
                data-target="#navbar2SupportedContent" aria-controls="navbar2SupportedContent"
                aria-expanded="false" aria-label="Toggle navigation"><span class="navbar-toggler-icon"></span></button>
        <div class="collapse navbar-collapse text-center justify-content-end" id="navbar2SupportedContent">
            <ul class="navbar-nav">

                <form method="POST" action="${pageContext.request.contextPath}/">
                    <li class="nav-item">
                        <input type="hidden" name="action" value="main"/>
                        <button class="btn navbar-btn ml-2 text-white btn-secondary"><i
                                class="fa d-inline fa-lg fa-home"></i>&nbsp;<fmt:message
                                key="menu.button.main"/>
                        </button>
                    </li>
                </form>
                <c:if test="${loginedUser !=null}">
                    <c:if test="${loginedUser.role.role !='admin'}">
                        <form method="POST" action="${pageContext.request.contextPath}/couriers">
                            <li class="nav-item">
                                <input type="hidden" name="action" value="get_couriers_pg"/>
                                <input type="hidden" name="firstRow" value="0"/>
                                <input type="hidden" name="rowCount" value="3"/>
                                <button class="btn navbar-btn ml-2 text-white btn-secondary"><i
                                        class="fa d-inline fa-lg fa-taxi"></i><fmt:message
                                        key="menu.button.couriers"/>
                                </button>
                            </li>
                        </form>
                    </c:if>
                </c:if>
                <c:if test="${loginedUser !=null}">
                    <c:if test="${loginedUser.role.role !='admin'}">
                        <c:if test="${loginedUser.role.role !='customer'}">
                        <form method="POST" action="${pageContext.request.contextPath}/courierForm">
                            <li class="nav-item">
                                <input type="hidden" name="action" value="courier_form"/>
                                <button class="btn navbar-btn ml-2 text-white btn-secondary"><i
                                        class="fa d-inline fa-lg fa-plus-circle"></i><fmt:message
                                        key="menu.button.createCourier"/>
                                </button>
                            </li>
                        </form>
                        </c:if>
                    </c:if>
                </c:if>
                <c:if test="${loginedUser !=null}">
                    <c:if test="${loginedUser.role.role !='admin'}">
                        <c:if test="${loginedUser.role.role !='manager'}">
                            <form method="POST" action="${pageContext.request.contextPath}/applications">
                                <li class="nav-item">
                                    <input type="hidden" name="action" value="get_newapp_form"/>
                                    <button class="btn navbar-btn ml-2 text-white btn-secondary"><i
                                            class="fa d-inline fa-lg fa-hand-o-right"></i>&nbsp;<fmt:message
                                            key="menu.button.doapplication"/>
                                    </button>
                                </li>
                            </form>
                        </c:if>
                    </c:if>
                </c:if>
                <c:if test="${loginedUser !=null}">
                    <c:if test="${loginedUser.role.role !='admin'}">
                        <form method="POST" action="${pageContext.request.contextPath}/applications">
                            <li class="nav-item">
                                <input type="hidden" name="action" value="get_applications"/>
                                <button class="btn navbar-btn ml-2 text-white btn-secondary"><i
                                        class="fa d-inline fa-lg fa-align-justify"></i>&nbsp;<fmt:message
                                        key="menu.button.applications"/>
                                </button>
                            </li>
                        </form>
                    </c:if>
                </c:if>
                <c:if test="${loginedUser.role.role =='admin'}">
                    <form method="POST" action="${pageContext.request.contextPath}/users">
                        <input type="hidden" name="action" value="get_users"/>
                        <button class="btn navbar-btn ml-2 text-white btn-secondary" style="background-color: green"><i
                                class="fa d-inline fa-lg fa-users"></i>&nbsp;<fmt:message key="menu.button.users"/>
                        </button>
                    </form>
                </c:if>

                <form method="POST" action="${pageContext.request.contextPath}/singIn">
                    <input type="hidden" name="action" value="sing_in"/>
                    <button class="btn navbar-btn ml-2 text-white btn-secondary"><i
                            class="fa d-inline fa-lg fa-user-circle-o"></i>&nbsp;<fmt:message
                            key="menu.button.cabinet"/>
                    </button>
                </form>

                <c:if test="${loginedUser !=null}">
                    <form method="POST" action="${pageContext.request.contextPath}/logout">
                        <input type="hidden" name="action" value="log_out"/>
                        <button class="btn navbar-btn ml-2 text-white btn-secondary" style="background-color: red"><i
                                class="fa d-inline fa-lg fa-sign-out"></i>&nbsp;<fmt:message key="logout.menu.button"/>
                        </button>
                    </form>
                </c:if>
            </ul>


        </div>
    </div>
</nav>
<script src="https://code.jquery.com/jquery-3.2.1.slim.min.js"
        integrity="sha384-KJ3o2DKtIkvYIK3UENzmM7KCkRr/rE9/Qpg6aAZGJwFDMVNA/GpGFF93hXpG5KkN"
        crossorigin="anonymous"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.12.9/umd/popper.min.js"
        integrity="sha384-ApNbgh9B+Y1QKtv3Rn7W3mgPxhU9K/ScQsAP7hUibX39j7fakFPskvXusvfa0b4Q"
        crossorigin="anonymous"></script>
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/js/bootstrap.min.js"
        integrity="sha384-JZR6Spejh4U02d8jOt6vLEHfe/JQGiRRSQQxSfFWpi1MquVdAyjUar5+76PVCmYl"
        crossorigin="anonymous"></script>
