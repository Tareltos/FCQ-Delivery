<%@ page pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib uri="/WEB-INF/printdate.tld" prefix="m" %>
<c:set var="language"
       value="${not empty param.language ? param.language : not empty language ? language : pageContext.request.locale}"
       scope="session"/>
<fmt:setLocale value="${language}"/>
<fmt:setBundle basename=".content"/>
<div class="footer">
    <div class="text-white bg-secondary">
        <div class="container">
            <div class="row">
                <div class="p-2 col-md-12">
                    <p class="text-center">
                        <fmt:message key="footer.text.phone"/>+375-29-717-71-71&nbsp; <fmt:message key="footer.text.email"/>fcq-dev@fcq.com&nbsp; <fmt:message key="footer.text.address"/><fmt:message key="footer.text.address.text"/></p>
                </div>
            </div>
            <div class="row">
                <div class="col-md-12 mt-3">
                    <h6 style="margin-left: 40%"><m:date/></h6>
                    <p class="text-center text-white">© Copyright 2018 Pingendo - <fmt:message key="footer.text.right"/> </p>
                </div>
            </div>
        </div>
    </div>
</div>

