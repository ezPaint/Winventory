<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<c:set var="contextPath" value="${pageContext.request.contextPath}" />

<!DOCTYPE html>
<html lang="en">

<head>
<link rel="shortcut icon" href="${contextPath}/resources/images/favicon.png"> 

<meta charset="UTF-8">
<title>Winventory | Search Hardware</title>

<!-- Include Required Prerequisites for Date Range Picker -->
<script type="text/javascript"
	src="//cdn.jsdelivr.net/jquery/2.1.3/jquery.min.js"></script>
<script type="text/javascript"
	src="${contextPath}/resources/js/moment.min.js"></script>

<!-- Include Date Range Picker -->
<script type="text/javascript"
	src="//cdn.jsdelivr.net/bootstrap.daterangepicker/1/daterangepicker.js"></script>
<link rel="stylesheet" type="text/css"
	href="//cdn.jsdelivr.net/bootstrap.daterangepicker/1/daterangepicker-bs3.css" />

<link type="text/css" rel="stylesheet"
	href='${contextPath}/resources/css/style.css'>
<link type="text/css" rel="stylesheet"
	href='${contextPath}/resources/css/normalize.css' />
<link type="text/css" rel="stylesheet"
	href='${contextPath}/resources/css/bootstrap.css' />

<script src='${contextPath}/resources/js/actions.js'
	type="text/javascript"></script>
<script src='${contextPath}/resources/js/bootstrap.min.js'
	type="text/javascript"></script>
<script src='${contextPath}/resources/js/validator.js'
	type="text/javascript"></script>
<script>
	$(function() {
		$('input[name="date"]').daterangepicker({
			format : 'YYYY-MM-DD',
			minDate : '1970-01-01',
			separator : '  to  '
		});
	});
	function addDiv(container, name, type) {
		var newDiv = document.createElement('div');
		newDiv.innerHTML = "<br><div class=\"input-group extra-box\">"
				+ "<input name=\"" + name + "\" type=\"" + type + "\" class=\"form-control\" pattern=\"^[a-zA-Z0-9\s]*$\">"
				+ "</div>";
		document.getElementById(container).appendChild(newDiv);
	}
</script>

<style>
.double-input .form-control {
	width: 50%;
	border-right-width: 1px;
}

.double-input .form-control:focus {
	border-right-width: 1px;
}

.input-group-addon {
	min-width: 50px;
	text-align: center;
}

.padme {
	padding: 30px;
}
</style>
</head>

<body>
<body>
	<jsp:include page="/base.jsp" />
	<div class="container-fluid">
		<div class="row">
			<jsp:include page="hwBase.jsp" />
			<div class="col-xs-8">
				<jsp:include page="/WEB-INF/includes/error.jsp" />
				<div class="main">
					<div class="boom">
						<h2 class="center">Hardware Advanced Search</h2>
					</div>
					<div class="padme">

						<form action="advanced-search" data-toggle="validator" role="form"
							method="post">
							<div class="form-group" id="type_container">
								<div class="input-group" id="type_div">
									<span class="input-group-addon" id="basic-addon1">Type</span> <input
										name="type" type="text" class="form-control"
										placeholder="Laptop" pattern="^[a-zA-Z0-9\s]*$"> <span
										class="input-group-btn" aria-hidden="true">
										<button type="button" class="btn btn-default"
											onClick="addDiv('type_container', 'type', 'text'); return false;">
											<span class="glyphicon glyphicon-plus" aria-hidden="true"></span>
										</button> <br>
									</span>
								</div>
								<div class="col-sm-10 col-sm-offset-2">
									<div class="help-block with-errors"></div>
								</div>
							</div>

							<div class="form-group" id="description_container">
								<div class="input-group" id="description_div">
									<span class="input-group-addon" id="basic-addon1">Description</span>
									<input name="description" type="text" class="form-control"
										placeholder="MacbookPro 13" pattern="^[a-zA-Z0-9\s]*$">
									<span class="input-group-btn" aria-hidden="true">
										<button type="button" class="btn btn-default"
											onclick="addDiv('description_container', 'description', 'text'); return false;">
											<span class="glyphicon glyphicon-plus" aria-hidden="true"></span>
										</button> <br>
									</span>
								</div>
								<div class="col-sm-10 col-sm-offset-2">
									<div class="help-block with-errors"></div>
								</div>
							</div>

							<div class="form-group" id="condition_container">
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
								<div class="col-sm-10 col-sm-offset-2">
									<div class="help-block with-errors"></div>
								</div>
							</div>

							<div class="form-group" id="serial_container">
								<div class="input-group" id="serial">
									<span class="input-group-addon" id="basic-addon1">Serial
										No</span> <input name="serial" type="text" class="form-control"
										placeholder="13456FFB" pattern="^[a-zA-Z0-9\s]*$"> <span
										class="input-group-btn" aria-hidden="true">
										<button class="btn btn-default" type="button"
											onclick="addDiv('serial_container', 'serial', 'text'); return false;">
											<span class="glyphicon glyphicon-plus" aria-hidden="true"></span>
										</button> <br>
									</span>
								</div>
								<div class="col-sm-10 col-sm-offset-2">
									<div class="help-block with-errors"></div>
								</div>
							</div>

							<div class="form-group" id="date_control">
								<div class="input-group">
									<span class="input-group-addon" id="basic-addon1">Date
										Purchased</span> <input name="date" type="text" id="date"
										class="form-control" placeholder="YYYY-MM-DD  to  YYY-MM-DD">
								</div>
							</div>

							<div class="form-group">
								<div class="input-group">
									<input type="number" class="form-control" placeholder="12"
										name="minCost" aria-describedby="basic-addon2"> <span
										class="input-group-addon"><</span> <span
										class="input-group-addon">Cost</span> <span
										class="input-group-addon"><</span> <input type="number"
										class="form-control" placeholder="57" name="maxCost"
										aria-describedby="basic-addon2">
								</div>
							</div>

							<div class="form-group" id="search_container">
								<h5>Search by Ownership</h5>
								<div class="radio">
									<label> <input type="radio" name="optionsSearch"
										id="search1" value="all" checked> Search All
									</label>
								</div>
								<div class="radio">
									<label> <input type="radio" name="optionsSearch"
										id="search2" value="owned"> Search Owned
									</label>
								</div>
								<div class="radio">
									<label> <input type="radio" name="optionsSearch"
										id="search3" value="stored"> Search Stored
									</label>
								</div>
							</div>
							<div class="form-group" id="search_container">
							<h5>Search by Activity</h5>
								<div class="radio">
									<label> <input type="radio" name="activeSearch"
										value="all" checked> Search All
									</label>
								</div>
								<div class="radio">
									<label> <input type="radio" name="activeSearch"
										 value="active"> Search Active
									</label>
								</div>
								<div class="radio">
									<label> <input type="radio" name="activeSearch"
										 value="inActive"> Search Not Active
									</label>
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