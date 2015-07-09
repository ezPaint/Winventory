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
<script
	src='https://cdn.datatables.net/plug-ins/1.10.7/integration/bootstrap/3/dataTables.bootstrap.js'
	type="text/javascript"></script>
<link type="text/css" rel="stylesheet"
	href='${contextPath}/resources/css/style.css'>

<script type="text/javascript">
	$(document).ready(function() {
		$('#resultsTable').DataTable();
	});
</script>
</head>

<body>
	<jsp:include page="/base.jsp" />
	<div class="container-fluid">
		<div class="row">
			<jsp:include page="hwBase.jsp" />
			<div class="col-md-8">
				<jsp:include page="/WEB-INF/includes/error.jsp" />

				<div class="main">
					<div class="boom">
						<h2 class="center">Hardware Info</h2>
					</div>

					<div class="padme">
						<%@ page import="java.util.ArrayList"%>
						<%@ page
							import="com.simoncomputing.app.winventory.domain.Hardware"%>

						<%@ page import="com.simoncomputing.app.winventory.domain.User"%>
						<%@ page
							import="com.simoncomputing.app.winventory.domain.Location"%>

						<%
						    User owner = (User) request.getAttribute("owner");

						    if (owner != null) {
						%>
						<h4 class="center">
							Item owned by <a
								href="${contextPath}/users/view?key=<%= owner.getKey() %>"><%=owner.getFirstName()%>
								<%=owner.getLastName()%></a>
						</h4>

						<%
						    }
						%>

						<%
						    Location loc = (Location) request.getAttribute("location");

						    if (loc != null) {
						%>
						<h4 class="center">
							Item stored at
							<%=loc.getDescription()%>
						</h4>
						<%
						    }
						%>

						<%
						    Hardware hardware = (Hardware) request.getAttribute("hardware");

						    if (hardware != null) {
						%>

						<div class="col-md-4">
							<img src="${contextPath}/resources/images/barcode.gif"
								class="img img-responsive">
						</div>
						<div class="col-md-8">
							<ul class="list-group" style="word-wrap: break-word;">
								<li class="list-group-item row">
									<div class="col-md-4">
										<b>Key</b>
									</div>
									<div class="col-md-8">
										<p><%=hardware.getKey()%></p>
									</div>
								</li>
								<li class="list-group-item row">
									<div class="col-md-4">
										<b>Type</b>
									</div>
									<div class="col-md-4">
										<p><%=hardware.getType()%></p>
									</div>
								</li>
								<li class="list-group-item row">
									<div class="col-md-4">
										<b>Description</b>
									</div>
									<div class="col-md-8">
										<p><%=hardware.getDescription()%></p>
									</div>
								</li>
								<li class="list-group-item row">
									<div class="col-md-4">
										<b>Cost</b>
									</div>
									<div class="col-md-8">
										<p><%=hardware.getCost()%></p>
									</div>
								</li>
								<li class="list-group-item row">
									<div class="col-md-4">
										<b>Date Purchased</b>
									</div>
									<div class="col-md-8">
										<p><%=hardware.getPurchaseDate()%></p>
									</div>
								</li>
							</ul>
						</div>

						
						
							<%-- <c:if test="${userInfo.hasPermission.updateHardware}"> --%>
							<a class="btn btn-default pull-right"
								href="edit?key=<%=hardware.getKey()%>" role="button">Edit</a>
							<%-- </c:if> --%>
						<form action="view" method="post">
							<%-- <c:if test="${userInfo.hasPermission.deleteHardware}"> --%>
							<input type="hidden" id="key" name="key"
								value="<%=hardware.getKey()%>">
							<button type="submit" class="btn btn-danger pull-right">Delete</button>
							<%-- </c:if> --%>
						</form>
						<div class="container">
						
						</div>

						<%
						    }
						%>
						
					</div>
				</div>
			</div>
		</div>
	</div>
	<br>
	<br>
	<br>
	<jsp:include page="/WEB-INF/includes/footer.jsp" />
</body>

</html>