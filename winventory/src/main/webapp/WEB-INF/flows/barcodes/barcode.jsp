<%@ page language="java" contentType="text/html; charset=US-ASCII"
    pageEncoding="US-ASCII"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<c:set var="contextPath" value="${pageContext.request.contextPath}" />
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta charset="UTF-8">
<title>Inventory</title>

<link type="text/css" rel="stylesheet"
	href='${contextPath}/resources/css/style.css'>
<link type="text/css" rel="stylesheet"
	href='${contextPath}/resources/css/normalize.css' />
<link type="text/css" rel="stylesheet"
	href='${contextPath}/resources/css/bootstrap.css' />

<script src='${contextPath}/resources/js/actions.js'
	type="text/javascript"></script>
<script src='${contextPath}/resources/js/jquery-1.11.3.min.js'
	type="text/javascript"></script>
<script src='${contextPath}/resources/js/bootstrap.min.js'
	type="text/javascript"></script>
</head>
<body>
	<jsp:include page="/base.jsp"/>
    <div class="container-fluid">
	    <form class="form-group center" method="post" action="${contextPath}/barcodes/barcode">
       		<label>Barcode Entry</label>
       		<input style="width:50%;margin-left:25%" id="barcode" name="barcode" class="form-control center"
       			pattern=".{11,13}" placeholder="Scan Barcode Now" type="text" autocomplete="off" autofocus/>
	   		<input type="hidden" id="toSubmit" name="toSubmit" value="false"/>
	   	</form>
	   	<c:if test="${not empty error}">
			<div class="alert alert-danger text-center">${error}</div>
		</c:if>
		<br> <br>
		<form id="form" action="${contextPath}/barcodes/barcode" method="post">
			<div class="row">
				<div class="col-xs-6 form-group">
					<label>Owner</label> <input id="person" name="person" class="form-control"
						type="text" value="${user.firstName}" readonly/>
				</div>
				<div class="col-xs-6 form-group">
					<label>Location</label> <input id="location" name="location" class="form-control"
						type="text" value="${location.description}" readonly/>
				</div>
			<c:if test="${not empty hardware}">
				<div class="col-xs-12">
					<label for="hardwares">Hardware</label>
					<table id="hardwares" class="table">
						<thead>
							<tr>
								<th>Key</th>
								<th>Type</th>
								<th>Cost</th>
								<th>Condition</th>
								<th>Description</th>
								<th>User</th>
								<th>Location</th>
								<th>Remove</th>
							</tr>
						</thead>
						<tbody>
							<c:forEach var="i" items="${hardware}">
								<tr>
									<td><c:out value="${i.key}"/></td>
									<td><c:out value="${i.type}"/></td>
									<td><c:out value="${i.cost}"/></td>
									<td><c:out value="${i.condition}"/></td>
									<td><c:out value="${i.getShortDescription()}"/></td>
									<td><c:out value="${ub.read(i.userId).firstName}"/></td>
									<td><c:out value="${lb.read(i.locationId).description}"/></td>
									<td><button class="btn btn-danger" onClick="this.parentNode.parentNode.remove()">Delete</button></td>
								</tr>
							</c:forEach>
						</tbody>
					</table>
				</div>
				</div>
				<div class="center">
				 	<button class="btn btn-default" onclick="finalSubmission()">Confirm Changes</button>
				</div>
			</c:if>
		</form>
	</div>
    <script>
    	function finalSubmission(){
    		var hiddenInput = document.getElementById("toSubmit");
    		hiddenInput.value="true"
    		document.getElementById("form").submit();
    	}
    
		function placeBarcode() {
			var bar = document.getElementById("barcode").value;
			var table = bar.charAt(0);
			switch (table) {
			case "1":
				placeAsPerson(bar);
				break;
			case "2":
				placeAsHardware(bar);
				break;
			case "3":
				alert("try again");
				break;
			case "4":
				placeAsLocation(bar);
				break;
			default:
				alert("try again");
				break;
			}
			document.getElementById("barcode").value = "";
			document.getElementById("form").submit();
		}
	
		function placeAsPerson(code) {
			var person = document.getElementById("person");
			if (person.value == '') {
				person.value = code;
			}
			document.getElementById("location").value="Cannot modify both Owner and Location";
		}
	
		function placeAsHardware(code) {
			var hardwares = document.getElementById("hardwares");
			var input = document.createElement("input");
			input.name="hardware";
			input.type="text";
			input.value=code;
			input.className="form-control";
			input.readOnly=true;
			document.getElementById("hardwares").appendChild(input);
		}
	
		function placeAsLocation(code) {
			var location = document.getElementById("location");
			if (location.value==""){
				location.value = code;
			}
			document.getElementById("person").value="Cannot modify both Owner and Location";
		}
		
		function removeInput(obj) {
			obj.parentNode.parentNode.remove();
		}
	</script>
</body>
</html>