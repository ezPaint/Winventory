<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<c:set var="contextPath" value="${pageContext.request.contextPath}" />

<!DOCTYPE html>
<html lang="en">

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
<script>
	function addDiv(container, name, type) {
		var newDiv = document.createElement('div');
		newDiv.innerHTML = "<br><div class=\"input-group extra-box\">"
				+ "<input name=\"" + name + "\" type=\"" + type + "\" class=\"form-control\">"
				+ "</div>";
		document.getElementById(container).appendChild(newDiv);
	}
</script>
</head>

<body>
<body>
	<jsp:include page="/base.jsp" />
	<div class="container-fluid">
		<div class="row">
			<jsp:include page="hwBase.jsp" />
			<div class="col-md-8">
				<jsp:include page="/WEB-INF/includes/error.jsp" />
				<div class="main">
					<div class="boom">
						<h2 class="center">Hardware Advanced Search</h2>
					</div>
					<div class="padme">
						<br>
						<form action="advanced-search" method="post">
							<div id="type_container">
								<div class="input-group" id="type_div">
									<span class="input-group-addon" id="basic-addon1">Type</span> <input
										name="type" type="text" class="form-control"
										placeholder="Laptop"> <span class="input-group-btn"
										aria-hidden="true">
										<button type="button" class="btn btn-default"
											onClick="addDiv('type_container', 'type', 'text'); return false;">
											<span class="glyphicon glyphicon-plus" aria-hidden="true"></span>
										</button> <br>
									</span>
								</div>
							</div>
							<br>
							<div id="description_container">
								<div class="input-group" id="description_div">
									<span class="input-group-addon" id="basic-addon1">Description</span>
									<input name="description" type="text" class="form-control"
										placeholder="MacbookPro 13"> <span
										class="input-group-btn" aria-hidden="true">
										<button type="button" class="btn btn-default"
											onclick="addDiv('description_container', 'description', 'text'); return false;">
											<span class="glyphicon glyphicon-plus" aria-hidden="true"></span>
										</button> <br>
									</span>
								</div>
							</div>
							<br>
							<div id="cost_container">
								<div class="input-group" id="cost_div">
									<span class="input-group-addon" id="basic-addon1">Cost</span> <input
										name="cost" type="text" class="form-control" placeholder="70">
									<span class="input-group-btn" aria-hidden="true">
										<button class="btn btn-default" type="button"
											onclick="addDiv('cost_container', 'cost', 'text'); return false;">
											<span class="glyphicon glyphicon-plus" aria-hidden="true"></span>
										</button> <br>
									</span>
								</div>
							</div>
							<br>
							<div id="date_container">
								<div class="input-group">
									<span class="input-group-addon" id="basic-addon1">Date</span> <input
										name="date" type="date" class="form-control"> <span
										class="input-group-btn" aria-hidden="true">
										<button class="btn btn-default" type="button"
											onclick="addDiv('date_container', 'date', 'date'); return false;">
											<span class="glyphicon glyphicon-plus" aria-hidden="true"></span>
										</button> <br>
									</span>
								</div>
							</div>
							<br>
							<div id="condition_container">
								<div class="input-group" id="condition">
									<span class="input-group-addon" id="basic-addon1">Condition</span>
									<input name="condition" type="text" class="form-control"
										placeholder="Good"> <span class="input-group-btn"
										aria-hidden="true">
										<button class="btn btn-default" type="button"
											onclick="addDiv('condition_container', 'condition', 'text'); return false;">
											<span class="glyphicon glyphicon-plus" aria-hidden="true"></span>
										</button> <br>
									</span>
								</div>
							</div>
							<br>
							<div id="serial_container">
								<div class="input-group" id=serial>
									<span class="input-group-addon" id="basic-addon1">Serial
										No</span> <input name="serial" type="text" class="form-control"
										placeholder="13456FFB"> <span class="input-group-btn"
										aria-hidden="true">
										<button class="btn btn-default" type="button"
											onclick="addDiv('serial_container', 'serial', 'text'); return false;">
											<span class="glyphicon glyphicon-plus" aria-hidden="true"></span>
										</button> <br>
									</span>
								</div>
							</div>
							<br>
							<button type="submit" class="btn btn-default">Search</button>
							<button type="reset" class="btn btn-default">Clear</button>
						</form>
					</div>
				</div>
				<br> <br>
			</div>
			<br> <br> <br> <br>
		</div>
	</div>
	<br>
	<br>
	<br>
	<br>
	<jsp:include page="/WEB-INF/includes/footer.jsp" />
</body>

</html>