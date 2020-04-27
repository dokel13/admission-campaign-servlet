<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt_rt" %>
<fmt:setLocale value="${sessionScope.locale}"/>
<fmt:setBundle var="application" basename="application"/>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
        <title><fmt:message key="${subject}" bundle="${application}"/></title>
        <style><%@include file="/WEB-INF/css/admin/subject.css" %></style>
        <style><%@include file="/WEB-INF/css/right_corner.css" %></style>
        <style><%@include file="/WEB-INF/css/title.css" %></style>
        <style><%@include file="/WEB-INF/css/back_button.css" %></style>
    </head>
    <body>
        <div class="right-corner">
            <div class="language">
                <fmt:message var="choose_ukrainian" key="choose.ukrainian" bundle="${application}"/>
                <a href="${pageContext.request.contextPath}/api/admin/subject?${subject}&page=${sessionScope.page}&locale=ua" title="${choose_ukrainian}">UA</a>/
                <a href="${pageContext.request.contextPath}/api/admin/subject?${subject}&page=${sessionScope.page}&locale=en" title="choose English">EN</a>
            </div>
            <%@include file="/WEB-INF/jsp/student/logout.jsp" %>
        </div>
        <fmt:message var="back_button" key="back" bundle="${application}"/>
        <fmt:message var="back_message" key="back.message" bundle="${application}"/>
        <a class="back-button" href="${pageContext.request.contextPath}/api/home?locale=${sessionScope.locale}"
                                            title="${back_message}"><span class="symbol">&#11013;</span>${back_button}</a>
        <h3 class="title"><fmt:message key="${subject}" bundle="${application}"/></h3>
        <div class="table-container">
            <form class="checkbox-form" method="post" action="${pageContext.request.contextPath}/api/student/exams?locale=${sessionScope.locale}">
                <div>
                    <table class="subject-table-1">
                        <thead>
                            <tr>
                                <th><fmt:message key="subject" bundle="${application}"/></th>
                                <th class="th-2"><fmt:message key="mark" bundle="${application}"/></th>
                            </tr>
                        </thead>
                    </table>
                </div>
                <div class="scroll-form">
                    <table class="subject-table-2">
                        <tbody>
                            <c:forEach items="${subjects}" var="subject">
                                <tr>
                                    <fmt:message var="subject_locale" key="${subject}" bundle="${application}"/>
                                    <fmt:message var="subject_message" key="subject.message" bundle="${application}"/>
                                    <td><label for="${subject}" title="${subject_message}">${subject_locale}</label></td>
                                    <td><input type="checkbox" name="subjects" id="${subject}" value="${subject}" /></td>
                                </tr>
                            </c:forEach>
                        </tbody>
                    </table>
                </div>
                <div class="form-button">
                    <fmt:message var="save" key="save" bundle="${application}"/>
                    <input type="submit" class="btnSubmit" value="${save}" />
                </div>
            </form>
        </div>
    </body>
</html>