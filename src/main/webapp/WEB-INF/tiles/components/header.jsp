<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri='http://java.sun.com/jsp/jstl/core' prefix='c'%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
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
          <li class="active"><a href="">Home</a></li>
          <li><a href="formXmlStructure" target=_blank>FormXML</a></li>
        </ul>

        <ul class="nav navbar-nav navbar-right ">
        <sec:authorize access="hasRole('ANONYMOUS')">
          <li class="dropdown"><a href="#" class="dropdown-toggle"
            data-toggle="dropdown">Auth <b class="caret"></b></a>
            <div class="dropdown-menu">
              <div class="col-md-12">
                <form action="authenticate" method="post">
                  <div class="form-group">
                    <label for="loginForm1">Login</label> <input
                      type="text" id="loginForm1" class="form-control"
                      name="userLogin" placeholder="Login">
                  </div>
                  <div class="form-group">
                    <label for="loginForm2">Password</label> <input
                      type="text" id="loginForm2" class="form-control"
                      name="password" placeholder="Password">
                  </div>
                  <div class="form-group">
                   <label for="remeberMeId">Remember me </label><input type="checkbox" name="remember-me" id="rememberMeId" style="position:relative; top:2px; left:5px"/>
                    <input type="hidden" name="${_csrf.parameterName}"
                      value="${_csrf.token}" /> <input
                      type="submit"
                  class="btn btn-sm
                      btn-success btn-block" value="Авторизация" />
                  </div>
                </form>


              </div>
            </div>
           </li>
           </sec:authorize>
           <sec:authorize access="hasRole('ADMIN')">
            <li class="dropdown"><a href="#"
              class="dropdown-toggle" data-toggle="dropdown"
              role="button" aria-haspopup="true" aria-expanded="false"><sec:authentication property="principal.username" />
                <span class="caret"></span>
            </a>
              <ul class="dropdown-menu">
                <li>
                      <c:url var="logoutUrl" value="/authenticate/logout" />
                       <form action="${logoutUrl}" method="post">
                        <input type="submit" value="Log out" class="btn btn-default" style="border:none; background:none; display:table; width:100%; text-align:left"/> 
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