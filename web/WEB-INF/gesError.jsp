<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page isErrorPage="true"%>
<%@ page import="pr03.modelo.beans.BeanError"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Página de gestión de Errores</title>
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
   				<jsp:forward page="identificacion.jsp"/>
			</c:if>
			<c:choose>
				<c:when test="${not empty error}">
					<p>
						<c:out value="${error.toString()}" default="Error" />
					</p>
				</c:when>
				<c:otherwise>
					<p>
						Página del error:
						<c:out value="${param.pagOrigen}" default="Error" />
					</p>
					<p>
						Error:
						<%=exception.toString()%></p>
				</c:otherwise>
			</c:choose>
			<form action="/pr03/controlador" method="post">
				<input type="hidden" name="accion" value="inicio"> <input
					type="submit" value="Inicio">
			</form>
		</div>
</body>
</html>