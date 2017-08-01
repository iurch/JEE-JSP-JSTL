<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page isELIgnored="false" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Profesores</title>
</head>
<body>
	<c:catch var="err">
		<jsp:useBean id="teacherBean" class="packt.book.jee.eclipse.ch4.bean.Teacher"></jsp:useBean>
		<c:set var="teachers" value="${teacherBean.getTeachers() }"></c:set>
	</c:catch>
	
	<c:choose>
		<c:when test="${err != null }">
			<c:set var="errMsg" value="${err.message }"></c:set>
		</c:when>
		<c:otherwise></c:otherwise>
	</c:choose>
	
	<h2>Teachers</h2>
	<c:if test="${errMsg  }">
		<span style="color:red;">
			<c:out value="${errMsg }"></c:out>
		</span>
	</c:if>
	
	<table>
		<thead>
			<tr>
				<th>Nombre</th>
				<th>Apellido</th>
				<th>Materia</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${teachers }" var="teacher">
				<tr>
					<td>${teacher.firstName }</td>
					<td>${teacher.lastName }</td>
					<td>${teacher.designation }</td>
				</tr>
			 </c:forEach>
		</tbody>
	</table>
</body>
</html>