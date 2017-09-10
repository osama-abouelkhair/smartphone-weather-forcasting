<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html xmlns:th="http://www.thymeleaf.org">

<head>
<meta http-equiv="Content-Type"
	content="text/html; charset=windows-1256">
<script type="text/javascript"
	src="https://ajax.googleapis.com/ajax/libs/jquery/2.1.3/jquery.min.js"></script>
<title>Insert title here</title>
</head>
<body>
	<h1>Smartphone Weather Forecasting</h1>

	<p>
		<font size="3"> This portal is a POC for the smartphone based
			weather forecasting.<br> A mobile should send its surrounding
			temperature with a latitude and longitude. If a get temperature
			request sent to the backend, it calculates the average temperatures
			within certain region and within 1 hour.<br> To test the backend
			enter a latitude and a longitude then submit it. A random temperatures
			should be populated to the DB. then the average temperature should
			appear.<br>
		</font>
	</p>

	<h2>Enter a latitude and longitude</h2>
	<form th:action="@{/record}" method="post">
		<input type="text" th:id="latitude" 
				th:name="latitude"
				 th:value="*{latitude}"
				  th:placeholder="Latitude" />
		<input type="text" 
				th:id="longitude" 
				th:name="longitude" 
				th:value="*{longitude}"
				th:placeholder="Longitude" />
		<div class="clearfix">
			<button type="submit" value="Submit">Submit</button>
		</div>
	</form>

	

</body>

</html>