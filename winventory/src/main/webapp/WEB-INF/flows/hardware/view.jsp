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
						<%@ page
							import="com.simoncomputing.app.winventory.domain.Hardware"%>
						<%@ page import="java.util.ArrayList"%>

						<%
						    Hardware hardware = (Hardware) request.getAttribute("hardware");

						    if (hardware != null) {
						%>


						<div class="media">
							<div class="media-left">
								<img class="media-object"
									src="${contextPath}/resources/images/barcode.gif">
							</div>
							<div class="media-body">
								<ul class="list-group">
									<li class="list-group-item row">
										<div class="col-md-3">
											<b>Key</b>
										</div>
										<div class="col-md-9">
											<p><%=hardware.getKey()%></p>
										</div>
									</li>
									<li class="list-group-item row">
										<div class="col-md-3">
											<b>Type</b>
										</div>
										<div class="col-md-9">
											<p><%=hardware.getType()%></p>
										</div>
									</li>
									<li class="list-group-item row">
										<div class="col-md-3">
											<b>Description</b>
										</div>
										<div class="col-md-9">
											<p><%=hardware.getDescription()%></p>
										</div>
									</li>
									<li class="list-group-item row">
										<div class="col-md-3">
											<b>Cost</b>
										</div>
										<div class="col-md-9">
											<p><%=hardware.getCost()%></p>
										</div>
									</li>
									<li class="list-group-item row">
										<div class="col-md-3">
											<b>Date Purchased</b>
										</div>
										<div class="col-md-9">
											<p><%=hardware.getPurchaseDate()%></p>
										</div>
									</li>
								</ul>
							</div>
						</div>
						<div>
							<%-- <c:if test="${userInfo.hasPermission.updateHardware}"> --%>
							<a class="btn btn-default" href="edit?key=<%=hardware.getKey()%>"
								role="button">Edit</a>
							<%-- </c:if> --%>
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