<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib uri='http://java.sun.com/jsp/jstl/core' prefix='c'%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title><tiles:insertAttribute name="title" /></title>
</head>
<body>
	<h2>Who told you that you could enter?!</h2>
	<form action="customLogin" method="post">

		<div>
			<label for="username">Login: </label><input type="text" 
				id="username" name="username" placeholder="Enter Username" required>
		</div>
		<div>
			<label for="pass">Login: </label><input type="text" 
				id="pass" name="pass" placeholder="Enter Pass" required>
		</div>
		<input type="submit" value="Log in">

	</form>
	<img src='res/img/loginGuardian.jpeg' />
</body>
</html>