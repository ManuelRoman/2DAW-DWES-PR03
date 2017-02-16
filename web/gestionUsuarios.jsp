<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ page errorPage="WEB-INF/errores.jsp?pagOrigen=gestionUsuarios.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Gestión de usuarios</title>
<style>
	body{
	text-align:center
	}
	div{
	position: absolute;
	left: 30%;
	}
</style>
</head>
<body>
	<div>
	<p>Gestión de usuarios</p>
	<form action="/pr03/controlador" method="post">
		<input type="hidden" name="accion" value="inicio">
		<input type="submit" value="Inicio">
	</form>
	</div>
</body>
</html>