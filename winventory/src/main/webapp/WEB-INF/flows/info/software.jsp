<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c"       uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="contextPath" value="${pageContext.request.contextPath}"/>


<!DOCTYPE html>
<html lang="en">

<head>
    <meta charset="UTF-8">
    <title>Inventory</title>

    <link type="text/css" rel="stylesheet" href='${contextPath}/resources/css/style.css'>
    <link type="text/css" rel="stylesheet" href='${contextPath}/resources/css/normalize.css' />
    <link type="text/css" rel="stylesheet" href='${contextPath}/resources/css/bootstrap.css' />

    <script src='${contextPath}/resources/js/actions.js' type="text/javascript"></script>
    <script src='${contextPath}/resources/js/jquery-1.11.3.min.js' type="text/javascript"></script>
    <script src='${contextPath}/resources/js/bootstrap.min.js' type="text/javascript"></script>
</head>



<body>
	<c:import url="/base.jsp"></c:import>
    
        
        
        
                <div class="container-fluid">
            <div class="row">
                <c:import url="/WEB-INF/flows/software/swBase.jsp"></c:import>
                <div class="col-md-8">
                    <div class="main">
                        <h1 class="center">Application Information for S${software.getKey()}</h1>
                        <br>
                        <div class="panel panel-default">
                            <div class="panel-heading clearfix">
                                <h2 class="panel-title pull-left">Basic Application Information</h2>
                                <div class="btn-group pull-right">
                                    <a href="edit.html" class="btn btn-default btn-sm">Edit</a>
                                </div>
                            </div>
                            <div class="panel-body">
                                <p>Name: ${software.getName()}</p>
                                <p>Serial Number: ${software.getSerialNo()}</p>
                                <p>License Key: ${software.getLicenseKey()}</p>
                                <p>Version: ${software.getVersion()}</p>
                                <p>Cost: ${software.getCost()}</p>
                                <p>Purchase Date: ${software.getPurchasedDate()}</p>
                                <p>Expiration Date: ${software.getExpirationDate()}</p>
                            </div>
                        </div>
                        <div class="input-group">
                            <h4>History:</h4>
                        </div>
                        <br>
                        <div class="panel panel-default">
                            <div class="table-responsive">
                                <table class="table">
                                    <thead>
                                    <tr>
                                        <th>Owner</th>
                                        <th>Hardware Involved</th>
                                        <th>Date Description</th>
                                        <th>Description</th>
                                    </tr>
                                    </thead>
                                    <tbody>
                                    	<c:if test="${events.size() > 0}">
                                    	<c:forEach var="i" begin="0" end="${events.size() - 1}">
                                        	<c:set var="userId" value="${events.get(i).getOwnerId()}"></c:set>
                                            <c:set var="hardwareId" value="${events.get(i).getHardwareId()}"></c:set>
                                            <c:set var="date" value="${events.get(i).getDateSpecified()}"></c:set>
                                            <c:set var="desc" value="${events.get(i).getDescription()}"></c:set>
	                                        <tr>   
	                                            <td><a href="/info?${userId}"></a>${userId}</td>
	                                            <td>${hardwareId}</td>
	                                            <td>${date}</td>
	                                            <td>${desc}</td>
	                                        </tr>
                                        </c:forEach>
                                        </c:if>
                                    </tbody>
                                </table>
                            </div>
                        </div>
                        <form action=/addEvent method="POST">
                        <a href="addEvent" class="btn btn-default">Add An Event</a>
                        </form>
                        <br>
                        <br>
                        <a href="index.html" class="btn btn-default">Return To Results</a>
                        <br>
                    </div>
                    <br>
                    <br>
                    <br>
                    <br>
                </div>
            </div>
        </div>
        <div class="top-header">
            <div class="center">
                <br>
                <h4>SimonComputing Â© 2015</h4>
                <br>
            </div>
        </div>
    </body>

</html>