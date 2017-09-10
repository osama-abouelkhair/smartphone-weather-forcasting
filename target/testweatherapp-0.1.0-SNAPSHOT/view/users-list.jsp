<%@ page language="java" contentType="text/html; charset=windows-1256"
    pageEncoding="windows-1256"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=windows-1256">
<title>Insert title here</title>
</head>
<body>
	<table>
		<tr>
			<th>ID</th>
			<th>Serial Number</th>
			<th>Rank</th>
		</tr>
		
		<c:forEach var="user" items="${users}">
			<tr>
			<td>${user.id}</td>
			<td>${user.sn}</td>
			<td>${user.rank}</td>
			</tr>
		</c:forEach>
	</table>
	
	<h2>Verfied User</h2>
	<p><c:out value="${user.id }"></c:out></p>
	<p><c:out value="${user.sn }"></c:out></p>
</body>
</html>