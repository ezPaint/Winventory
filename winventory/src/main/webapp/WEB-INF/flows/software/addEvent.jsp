<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c"       uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="contextPath" value="${pageContext.request.contextPath}"/>


<!DOCTYPE html>
<html lang="en">

<head>
<meta charset="UTF-8">
<title>Winventory | Applications</title>
<link rel="shortcut icon"
	href="${contextPath}/resources/images/favicon.png">

    <link type="text/css" rel="stylesheet" href='${contextPath}/resources/css/style.css'>
    <link type="text/css" rel="stylesheet" href='${contextPath}/resources/css/normalize.css' />
    <link type="text/css" rel="stylesheet" href='${contextPath}/resources/css/bootstrap.css' />

    <script src='${contextPath}/resources/js/actions.js' type="text/javascript"></script>
    <script src='${contextPath}/resources/js/jquery-1.11.3.min.js' type="text/javascript"></script>
    <script src='${contextPath}/resources/js/bootstrap.min.js' type="text/javascript"></script>
</head>



<body>
	<c:import url="/base.jsp"></c:import>
   
        	        <h1 class="center">Add an event for S${sessionScope.software.getKey()}</h1>
					<form action="${contextPath}/addEvent" method="POST">
					  What happened: 		<input type="text" name="description"><br>
					  The date it happened: <input type="text" name="dateSpecified"><br>
					  Associated hardware:	<input type="text" name="hardware"><br>
					  <button type="submit" class="btn btn-success">Add</button>
					</form>
					
					<form action="${contextPath}/addEvent" method="POST">
						<button type="submit" class="btn btn-warning" name="cancel">Cancel</button>
					</form>
        	
        <div class="top-header">
            <div class="center">
                <br>
                <h4>SimonComputing © 2015</h4>
                <br>
            </div>
        </div>
    </body>

</html>