<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri='http://java.sun.com/jsp/jstl/core' prefix='c'%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
   
<form action="authenticate" method="post">
       <div class="form-group">
        <label for="loginForm2"><spring:message code="nav.auth.secret"/></label> <input
          type="text" id="loginForm2" class="form-control"
          name="secret">
      </div>
      <div class="form-group text-center">
       <label for="remeberMeId" style="font-size:0.85em; color:grey; font-weight:normal;"><spring:message code="nav.auth.remember"/></label><input type="checkbox" name="remember-me" id="rememberMeId" style="position:relative; top:2px; left:5px"/>
        <input type="hidden"  name="userLogin" value="${login}">
        <input type="hidden" name="password" value="${password}">
        <input type="hidden" name="${_csrf.parameterName}"
          value="${_csrf.token}" /> <input
          type="submit"
      class="btn btn-sm
          btn-success btn-block" value='<spring:message code="nav.auth.submit"/>' />
      </div>
</form>