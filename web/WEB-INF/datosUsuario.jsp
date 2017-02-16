<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page errorPage="errores.jsp?pagOrigen=datosUsuario.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Datos Usuario</title>
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
	<p>Datos usuario:</p>
	<p>Login: <c:out value="${modelo.getLogin()}" default="Sin información"/></p>
	<p>Clave: <c:out value="${modelo.getClave()}" default="Sin información"/></p>
	<p>Nombre: <c:out value="${modelo.getNombre()}" default="Sin información"/></p>
	<form action="/pr03/controlador" method="post">
		<input type="hidden" name="accion" value="volver">
		<input type="submit" value="Volver">
	</form>
	</div>
</body>
</html>