<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri='http://java.sun.com/jsp/jstl/core' prefix='c'%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<script>
    $(document).ready(function(){
    
        $(".loginForm").submit(function(event){
            var data = $(this).serialize();
            console.log("submit: "+data.toString())
            event.preventDefault();
            
            $.ajax({
                type:"post",
                data:data,
                url: $(".loginForm").attr("action"),
                success:function(data){
                    console.log("ajax success")
                    $("#loginForm").html(data)
                },
                error: function(data){
                    console.log("ajax error: "+data)
                }
            })
        })
        
    })
    </script>
   <c:if test="${not empty SPRING_SECURITY_LAST_EXCEPTION}">
      <div class="alert alert-danger">
        <h3><spring:message code="Security.header"/></h3>
        <b><spring:message code="Security.reason"/>: </b> 
        <c:out value="${SPRING_SECURITY_LAST_EXCEPTION.message}" />
 
      </div>
    </c:if>
    
     <form action="preAuth" method="post" class="loginForm">
                  <div class="form-group">
                    <label for="loginForm1"><spring:message code="nav.auth.login"/></label> <input
                      type="text" id="loginForm1" class="form-control"
                      name="userLogin" placeholder="Login">
                  </div>
                  <div class="form-group">
                    <label for="loginForm2"><spring:message code="nav.auth.pass"/></label> <input
                      type="text" id="loginForm2" class="form-control"
                      name="password" placeholder="Password">
                  </div>
                  <div class="form-group text-center">
                    <input type="hidden" name="${_csrf.parameterName}"
                      value="${_csrf.token}" /> <input
                      type="submit"
                  class="btn btn-sm
                      btn-success btn-block" value='<spring:message code="nav.auth.submit"/>' />
                  </div>
                </form>