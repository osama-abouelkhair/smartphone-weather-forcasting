<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html xmlns:th="http://www.thymeleaf.org">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=windows-1256">
<title>Insert title here</title>
</head>
<body>
	
		
	
	<h2>Temperature</h2>
	<font size=6><span th:text="${temperature}"></span></font>
	
	<table >
			<tr th:each="temp : ${temps}">
				<td>Temp: <span th:text="${temp.temperature}" ></span></td>
				<td>City <span th:text="${temp.city}" ></span></td>
				<td>Country <span th:text="${temp.country}" ></span></td>
			</tr>
		
	</table>
	
</body>
</html>