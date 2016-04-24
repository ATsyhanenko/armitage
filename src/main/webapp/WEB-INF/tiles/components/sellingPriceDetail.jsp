<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri='http://java.sun.com/jsp/jstl/core' prefix='c'%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<table class="table table-hover hint">
  <thead>
    <tr class="success">
      <td>%</td>
      <td><spring:message code="price.form.profit"/></td>
    </tr>
  </thead>
  <tbody>
    <c:forEach items="${priceList}" var="element">
      <tr>
          <td>${element.percent}</td>
          <td>${element.formattedPrice}</td>
      </tr>
    </c:forEach>
  </tbody>
</table>