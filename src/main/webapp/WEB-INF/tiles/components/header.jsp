<%@page contentType="text/html" pageEncoding="UTF-8"%>
<div class="row">
  <nav class="navbar navbar-inverse" role="navigation">
    <div class="container-fluid">
      <div class="navbar-header">
        <a class="navbar-brand" href="">ArcheAge Info</a>
      </div>
      <div class="collapse navbar-collapse"
        id="bs-example-navbar-collapse-1">
        <ul class="nav navbar-nav">
          <li class="active"><a href="#">Home</a></li>
          <li><a href="#">Articles</a></li>
        </ul>

        <ul class="nav navbar-nav navbar-right ">
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
                    <input type="hidden" name="${_csrf.parameterName}"
                      value="${_csrf.token}" /> <input
                      type="submit"
                  class="btn btn-sm
                      btn-success btn-block" value="Авторизация" />
                  </div>
                </form>


              </div>
            </div></li>
        </ul>
      </div>
    </div>
  </nav>
</div>