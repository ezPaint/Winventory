<%@ page language="java" contentType="text/html; charset=US-ASCII"
	pageEncoding="US-ASCII"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<c:set var="contextPath" value="${pageContext.request.contextPath}" />
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta charset="UTF-8">
<title>Winventory | Barcode</title>
<link rel="shortcut icon"
	href="${contextPath}/resources/images/favicon.png">
<link type="text/css" rel="stylesheet"
	href='${contextPath}/resources/css/style.css'>
<link type="text/css" rel="stylesheet"
	href='${contextPath}/resources/css/normalize.css' />
<link type="text/css" rel="stylesheet"
	href='${contextPath}/resources/css/bootstrap.css' />
<link type="text/css" rel="stylesheet"
	href='https://cdn.datatables.net/plug-ins/1.10.7/integration/bootstrap/3/dataTables.bootstrap.css
' />

<script src='${contextPath}/resources/js/actions.js'
	type="text/javascript"></script>
<script src='${contextPath}/resources/js/jquery-1.11.3.min.js'
	type="text/javascript"></script>
<script src='${contextPath}/resources/js/bootstrap.min.js'
	type="text/javascript"></script>
<script
	src='https://cdn.datatables.net/1.10.7/js/jquery.dataTables.min.js'
	type="text/javascript"></script>

<script type="text/javascript">
	$(document).ready(
			function() {
				var table = $('#hardwares').DataTable({
					"scrollY" : "1000px",
					"scrollCollapse" : true,
					"paging" : false,
					"dom" : '<"top">rt<"bottom"flp><"clear">'
				});
				
				$('#hardwares tr').on(
						'click',
						'td:eq(7)',
						function() {
							var input;
							var parentNode = $(this).parent("tr");
							var pk = parentNode.find("td:nth-child(1)").text();
							var icon = parentNode.find("td:nth-child(8)");
							$(this).find("span").toggleClass(
									'glyphicon glyphicon-remove').toggleClass(
									'glyphicon glyphicon-repeat')
							if (parentNode.hasClass('alert alert-danger')) {
								parentNode.removeClass('alert alert-danger');
								$("#" + pk).remove();
							} else {
								parentNode.addClass('alert alert-danger');
								$('<input>').attr({
									type : 'hidden',
									id : pk,
									name : 'removeHw',
									value : pk
								}).appendTo('form');
							}
						});
			});
	
	var toggleRemoveClick = function($obj) {
		var input;
		var parentNode = $obj.parent("tr");
		var pk = parentNode.find("td:nth-child(1)").text();
		var icon = parentNode.find("td:nth-child(8)");
		$(this).find("span").toggleClass(
				'glyphicon glyphicon-remove').toggleClass(
				'glyphicon glyphicon-repeat')
		if (parentNode.hasClass('alert alert-danger')) {
			parentNode.removeClass('alert alert-danger');
			$("#" + pk).remove();
		} else {
			parentNode.addClass('alert alert-danger');
			$('<input>').attr({
				type : 'hidden',
				id : pk,
				name : 'removeHw',
				value : pk
			}).appendTo('form');
		}
	}
	
	function finalSubmission() {
		var hiddenInput = document.getElementById("toSubmit");
		hiddenInput.value = true;
		document.getElementById("form").submit();
	}

	function clearBarSession() {
		var clearInput = document.getElementById("clear");
		clearInput.value = true;
		document.getElementById("form").submit();
	}
</script>
</head>
<body>
	<jsp:include page="/base.jsp" />
	<div class="container-fluid">
		<div class="row">
			<div class="col-md-8 col-md-offset-2">
				<div class="main">
					<div class="boom">
						<h2 class="center">Barcode Entry</h2>
					</div>
					<div class="padme">
						<form id="form" class="form-group center" method="post"
							action="${contextPath}/barcodes/barcode">
							<c:choose>
								<c:when test="${not empty user or not empty location}">
									<c:set var="placeholder" value="Scan Hardware Barcode to Add"/>
								</c:when>
								<c:otherwise>
									<c:set var="placeholder" value="Scan User/Location Barcode or Enter Username to Begin"/>
								</c:otherwise>
							</c:choose>
							<br><input style="width: 50%; margin-left: 25%" id="barcode" name="barcode"
									class="form-control center"
									placeholder="${placeholder}" type="text"
									autocomplete="off" autofocus onfocus="this.select()"
									value="${barcode}" />
							<input type="hidden" id="toSubmit"
							name="toSubmit" value=false /> <input type="hidden" id="clear"
							name="clear" value=false /> <input type="hidden" id="userId"
							name="userId" value="${user.key}" /> <input type="hidden"
							id="locationId" name="locationId" value="${location.key}" />
							<c:forEach var="i" items="${hardwareKeys}">
								<input type="hidden" id="hardwareId" name="hardwareId"
									value="${i}" />
							</c:forEach>
							<c:forEach var="i" items="${removalKeys}">
								<input type="hidden" id="removeHw" name="removeHw"
									value="${i}" />
							</c:forEach>
						</form>
						<c:if test="${not empty error}">
							<div class="alert alert-danger text-center">${error}</div>
						</c:if>
						<c:if test="${not empty success}">
							<div class="alert alert-success text-center">${success}</div>
						</c:if>
						<br>
						<div class="row">
							<div class="col-xs-6 form-group">
								<label>Owner</label> <input class="form-control" type="text"
									value="${user.username}" readonly />
							</div>
							<div class="col-xs-6 form-group">
								<label>Location</label> <input class="form-control" type="text"
									value="${location.description}" readonly />
							</div>
							<c:if test="${not empty hardware}">
								<div class="col-xs-12">
									<label for="hardwares">Associated Hardware</label>
									<table id="hardwares" class="table" style="width: 100%">
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
												<tr id="hardwareId${i.key}">
													<td><a href="${contextPath}/hardware/view?key=<c:out value="${i.key}" />" class="btn btn-primary"><c:out value="${i.key}" /></a></td>
													<td><c:out value="${i.type}" /></td>
													<td><c:out value="${i.cost}" /></td>
													<td><c:out value="${i.condition}" /></td>
													<td><c:out value="${i.getShortDescription()}" /></td>
													<c:choose>
														<c:when test="${not empty i.getUser().username}">
															<td><a href="${contextPath}/users/view?key=<c:out value="${i.getUserId()}" />"><c:out value="${i.getUser().username}" /></a></td>
														</c:when>
														<c:otherwise>
															<td>No Owner</td>
														</c:otherwise>
													</c:choose>
													<c:choose>
														<c:when
															test="${not empty i.getLocation().description}">
															<td><a href="${contextPath}/location/view-location?key=<c:out value="${i.getLocationId()}" />"><c:out value="${i.getLocation().description}" /></a></td>
														</c:when>
														<c:otherwise>
															<td>No Registered Location</td>
														</c:otherwise>
													</c:choose>
													<td><span style="margin-left: 15%"
														class="glyphicon glyphicon-remove"></span>
														<c:if test="${removalKeys.contains(i.key)}">
															<script>
																//document.getElementById("hardwareId${i.key}")cells[7].click();
																toggleRemoveClick($("#hardwareId${i.key} td:eq(7)"));
															</script>
														</c:if>
													</td>
												</tr>
											</c:forEach>
										</tbody>
									</table>
								</div>
								<div class="center">
									<button class="btn btn-info" onclick="finalSubmission()">Confirm
										Changes</button>
									<br> <br>
									<button class="btn btn-success" onclick="clearBarSession()">New
										Search</button>
								</div>
							</c:if>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>

</body>
</html>