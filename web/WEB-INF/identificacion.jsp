<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page errorPage="errores.jsp?pagOrigen=identificacion.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Identificación</title>
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
    <c:if test="${not empty errorCaptcha}">
		<p>Fallaste en el captcha intentalo de nuevo</p>
    </c:if>
	<form action="/pr03/controlador" method="post">
		<label for="login">Login:</label><input id="login" type="text" name="login" maxlength="12" placeholder="Máximo 12" required="required" pattern="\S*"><br>
		<label for="clave">Clave:</label><input id="clave" type="password" name="clave" maxlength="12" placeholder="Máximo 12" required="required" autocomplete="off" pattern="\S*"><br>
		<input type="hidden" name="accion" value="acceder">
		<p>Introduce el contenido del captcha que ves en la imagen</p>
		<img alt="captcha" src="captcha/${captchaSelec.getArchivo()}"><br/>
		<input type="text" required="required" name="respcaptcha"><br/>
		<input type="submit" value="Acceder">
	</form>
	</div>
</body>
</html>