<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<c:set var="contextPath" value="${pageContext.request.contextPath}" />

<!DOCTYPE html>
<html lang="en">

<head>
<meta charset="UTF-8">
<title>Winventory | Admin</title>
<link rel="shortcut icon"
	href="${contextPath}/resources/images/favicon.png">

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
		$('#resultsTable').DataTable({
			"pagingType" : "full"
		});
		document.getElementById("loader").style.visibility = "hidden";
		document.getElementById("resultsTable").style.display = "table";
	});
</script>
</head>
<body>
	<jsp:include page="/base.jsp" />
	<div class="container-fluid">
		<div class="row">
			<jsp:include page="adminBase.jsp" />
			<div class="col-md-8">
				<div class="main">
					<div class="boom">
						<h2 class="center">View Permissions for: ${role.title} </h2>
					</div>
					<div class="padme">
						<br>
						<div class="table-responsive">
							<table class="table table-striped" id="resultsTable">
								<thead>
									<tr>
										<th style="border-bottom: 0px">Permission Name</th>
										<th style="border-bottom: 0px">Description</th>
									</tr>
								</thead>
								<tbody>
									<c:forEach var="i" items="${permissions}">
										<tr>
											<td style="padding-top: 10px"><c:out value="${i.code}" /></th>
											<td style="padding-top: 10px"><c:out value="${i.description}" /></th>
										</tr>
									</c:forEach>
								</tbody>
							</table>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
	<jsp:include page="/WEB-INF/includes/footer.jsp" />
</body>
</html>