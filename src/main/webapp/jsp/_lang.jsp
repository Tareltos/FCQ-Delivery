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
<div class="locale" style="width: 100%; background-color: #0b0b0b; color: white">
    <form style="margin-left: 45%">
        <fmt:message key="language.text.label">: </fmt:message>
        <select id="language" name="language" onchange="submit()">
            <option value="ru" ${language == 'ru' ? 'selected' : ''}>Русский</option>
            <option value="en" ${language == 'en' ? 'selected' : ''}>English</option>
            <option value="es" ${language == 'es' ? 'selected' : ''}>Español</option>
        </select>
    </form>
</div>

