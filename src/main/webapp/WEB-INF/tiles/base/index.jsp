<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib uri='http://java.sun.com/jsp/jstl/core' prefix='c'%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<!DOCTYPE html>
<html lang="ru">
<head>
<meta charset="utf-8" />
<link href="res/css/normalize.css" rel="stylesheet" />
<link href="res/css/bootstrap.min.css" rel="stylesheet" />
<link href="res/css/aainfo.css" rel="stylesheet" />

<script
  src="res/js/jquery-2.2.3.min.js"></script>
<script src="res/js/bootstrap.min.js"></script>
<script src="res/js/bootbox.min.js"></script>
<script src="res/js/fabric.js"></script>
<title><spring:message code="document.header"/></title>
</head>
<body>
  <div class="container">
    <tiles:insertAttribute name="header" />
    <c:if test="${not empty SPRING_SECURITY_LAST_EXCEPTION}">
      <div class="alert alert-danger">
        <h3>Your login attempt was not successful</h3>
        due to:<br/>
        <c:out value="${SPRING_SECURITY_LAST_EXCEPTION.message}" />
 
      </div>
    </c:if>
    <tiles:insertAttribute name="mainblock" />
  </div>
</body>
</html>