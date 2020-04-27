<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt_rt" %>
<fmt:setLocale value="${sessionScope.locale}"/>
<fmt:setBundle var="application" basename="application"/>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
        <title><fmt:message key="specialties.page" bundle="${application}"/></title>
        <style><%@include file="/WEB-INF/css/student/specialties.css" %></style>
        <style><%@include file="/WEB-INF/css/right_corner.css" %></style>
        <style><%@include file="/WEB-INF/css/title.css" %></style>
        <style><%@include file="/WEB-INF/css/back_button.css" %></style>
    </head>
    <body>
        <div class="right-corner">
            <div class="language">
                <fmt:message var="choose_ukrainian" key="choose.ukrainian" bundle="${application}"/>
                <a href="${pageContext.request.contextPath}/api/student/specialties?locale=ua" title="${choose_ukrainian}">UA</a>/
                <a href="${pageContext.request.contextPath}/api/student/specialties?locale=en" title="choose English">EN</a>
            </div>
            <%@include file="/WEB-INF/jsp/student/logout.jsp" %>
        </div>
        <fmt:message var="back_button" key="back" bundle="${application}"/>
        <fmt:message var="back_message" key="back.message" bundle="${application}"/>
        <a class="back-button" href="${pageContext.request.contextPath}/api/home?locale=${sessionScope.locale}"
            title="${back_message}"><span class="symbol">&#11013;</span>${back_button}</a>
        <h3 class="title"><fmt:message key="specialties.page" bundle="${application}"/></h3>
        <div class="table-container">
            <div class="scroll-form">
                <table class="specialty-table">
                    <tbody>
                        <c:forEach items="${specialties}" var="specialty">
                            <tr>
                                <fmt:message var="specialty_locale" key="${specialty}" bundle="${application}"/>
                                <fmt:message var="specialty_message" key="specialty.message" bundle="${application}"/>
                                <td><a class="spec-ref" href="${pageContext.request.contextPath}/api/student/specialty?${specialty}&locale=${sessionScope.locale}"
                                    title="${specialty_message}">${specialty_locale}</a></td>
                            </tr>
                        </c:forEach>
                    </tbody>
                </table>
            </div>
        </div>
    </body>
</html>