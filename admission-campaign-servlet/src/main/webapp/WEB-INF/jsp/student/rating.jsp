<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt_rt" %>
<fmt:setLocale value="${sessionScope.locale}"/>
<fmt:setBundle var="application" basename="application"/>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
        <title><fmt:message key="rating.page" bundle="${application}"/></title>
        <style><%@include file="/WEB-INF/css/student/rating.css" %></style>
        <style><%@include file="/WEB-INF/css/right_corner.css" %></style>
        <style><%@include file="/WEB-INF/css/title.css" %></style>
    </head>
    <body>
        <div class="right-corner">
            <div class="language">
                <fmt:message var="choose_ukrainian" key="choose.ukrainian" bundle="${application}"/>
                <a href="${pageContext.request.contextPath}/api/student/rating?locale=ua" title="${choose_ukrainian}">UA</a>/
                <a href="${pageContext.request.contextPath}/api/student/rating?locale=en" title="choose English">EN</a>
            </div>
            <%@include file="/WEB-INF/jsp/student/logout.jsp" %>
        </div>
        <h3 class="title"><fmt:message key="your.rating" bundle="${application}"/></h3>
        <c:choose>
            <c:when test="${sessionScope.exception.message == 'Login exception! User doesn`t exist!'}">

            </c:when>
            <c:otherwise>

            </c:otherwise>
        </c:choose>
    </body>
</html>