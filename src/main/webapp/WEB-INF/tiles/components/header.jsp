<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri='http://java.sun.com/jsp/jstl/core' prefix='c'%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<div class="row">
  <nav class="navbar navbar-inverse" role="navigation">
       <div class="container-fluid" id="navfluid">
         <div class="navbar-header">
         <button type="button" class="navbar-toggle" data-toggle="collapse" data-target="#navigationbar">
         <span class="sr-only">Toggle navigation</span>
         <span class="icon-bar"></span>
         <span class="icon-bar"></span>
         <span class="icon-bar"></span>
        </button>
       <a class="navbar-brand" href="">ArcheAge Info</a>
      </div>

      <div class="collapse navbar-collapse" id="navigationbar">
        <ul class="nav navbar-nav">
          <li class="active"><a href=""><span class="glyphicon glyphicon-home" style="margin-right:5px; position:relative; top:1px;"></span><spring:message code="nav.home"/></a></li>
          <li><a href="formXmlStructure" target=_blank><span class="glyphicon glyphicon-random" style="margin-right:5px; position:relative; top:2px;"></span><spring:message code="nav.formXml"/></a></li>
        </ul>

        <ul class="nav navbar-nav navbar-right ">
        <sec:authorize access="hasRole('ANONYMOUS')">
          <li class="dropdown"><a href="#" class="dropdown-toggle"
            data-toggle="dropdown"><span class="glyphicon glyphicon-user" style="margin-right:5px; position:relative; top:2px;"></span><spring:message code="nav.auth"/> <b class="caret"></b></a>
            <div class="dropdown-menu">
              <div class="col-md-12">
                 <div id="loginForm">
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
              </div>
        
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
        
              </div>
            </div>
           </li>
           </sec:authorize>
           <sec:authorize access="hasRole('ADMIN')">
            <li class="dropdown"><a href="#"
              class="dropdown-toggle" data-toggle="dropdown"
              role="button" aria-haspopup="true" aria-expanded="false"><span class="glyphicon glyphicon-user" style="margin-right:5px; position:relative; top:2px;"></span><sec:authentication property="principal.username" />
                <span class="caret"></span>
            </a>
              <ul class="dropdown-menu">
                <li>
                      <c:url var="logoutUrl" value="/authenticate/logout" />
                       <form action="${logoutUrl}" method="post">
                        <input type="submit" value='<spring:message code="nav.auth.logout"/>' class="btn btn-default" style="border:none; background:none; display:table; width:100%; text-align:left"/> 
                        <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
                      </form>
                </li>
              </ul></li>
          </sec:authorize>
        </ul>
      </div>
    </div>
  </nav>
</div>