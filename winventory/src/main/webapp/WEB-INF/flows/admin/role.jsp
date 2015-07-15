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
						<h2 class="center">Roles</h2>
					</div>
					<div class="padme">
						<br>
						<c:if test="${not empty pass}">
							<div class="alert alert-success" role="alert">
								<span class="glyphicon glyphicon-ok" aria-hidden="true"></span>
								<c:out value="${pass}" />
							</div>
						</c:if>
						<c:if test="${not empty fail}">
							<div class="alert alert-danger" role="alert">
								<span class="glyphicon glyphicon-remove" aria-hidden="true"></span>
								<c:out value="${fail}" />
							</div>
						</c:if>

						<div class="table-responsive">
							<table class="table table-striped" id="resultsTable">
								<thead>
									<tr>
										<th style="border-bottom: 0px">Role Name</th>
										<th style="border-bottom: 0px">Permissions</th>
										<th style="border-bottom: 0px">Delete</th>
									</tr>
								</thead>
								<tbody>
									<c:forEach var="role" items="${roles}">
										<tr>
											<th style="padding-top: 15px"><c:out
													value="${role.title}" /></th>
											<th>
												<form action="role/view-permissions" method="get"
													role="form">
													<button class="btn btn-default" type="submit" name="key"
														value="${role.key}">View Permissions</button>
												</form>
											</th>
											<th>
												<form action="role" method="post" role="form">
													<button class="btn btn-default" type="submit" name="delete"
														value="${role.key}">Delete</button>
												</form>
											</th>
										</tr>
									</c:forEach>
								</tbody>
							</table>
						</div>
					</div>
					<div class="boom">
						<h2 class="center">Add a New Role</h2>
					</div>
					<div class="padme">
						<form action="role" method="post">
							<div class="row">
								<div class="form-group col-md-4" id="roleTitle">
									<label for="role">Role Title:</label> <input type="text"
										name="roleTitle" class="form-control" pattern="^[a-zA-Z ]*$"
										required>
								</div>
								<div class="col-md-4">
									<div class="help-block with-errors"></div>
								</div>
							</div>
							<h4>Select the permissions this role should have:</h4>
							
							<div id="checkboxes">
								<c:forEach var="permission" items="${refPerms}">
									<div class="checkbox">
										<label><input type="checkbox" value="${permission.code}"
											name="${permission.code}">${permission.description}</label>
									</div>
								</c:forEach>
							</div>
							<div class="row">
								<div class="col-md-8">
									<button type="submit" class="btn btn-default" name="create">Create
										Role</button>
								</div>
							</div>
						</form>
					</div>
				</div>
			</div>
		</div>
	</div>
	<jsp:include page="/WEB-INF/includes/footer.jsp" />
</body>
</html>