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
	href='https://cdn.datatables.net/plug-ins/1.10.7/integration/bootstrap/3/dataTables.bootstrap.css' />
<link type="text/css" rel="stylesheet"
	href='${contextPath}/resources/css/style.css'>
</head>
<body>
	<jsp:include page="/base.jsp" />
	<div class="container-fluid">
		<div class="row">
			<jsp:include page="adminBase.jsp" />
			<div class="col-md-8">
				<div class="main">
					<div class="boom">
						<h2 class="center">Create Role</h2>
					</div>

					<div class="padme">
						<div class="row">
							<div class="form-group col-md-4">
								<label for="role">Role Title:</label> <input type="text"
									class="form-control" id="roleTitle">
							</div>
						</div>
						<h4>Select the Permissions this role should have:</h4>
						<div class="row">
							<div class="col-md-4">
								<div class="radio">
									<label> <input type="radio" name="adminPerms"
										value="true"> Admin Permissions
									</label>
								</div>
							</div>
						</div>

						<!-- TODO: disable checkboxes if admin radio is selected -->

						<div class="col-md-4">
							<h5>User Permissions</h5>
							<div class="checkbox">
								<label><input type="checkbox" value="">Create
									User</label>
							</div>
							<div class="checkbox">
								<label><input type="checkbox" value="">Edit User</label>
							</div>
							<div class="checkbox">
								<label><input type="checkbox" value="">Delete
									User</label>
							</div>
						</div>
						<div class="col-md-4">
							<h5>Hardware Permissions</h5>
							<div class="checkbox">
								<label><input type="checkbox" value="">Create
									Hardware</label>
							</div>
							<div class="checkbox">
								<label><input type="checkbox" value="">Edit
									Hardware</label>
							</div>
							<div class="checkbox">
								<label><input type="checkbox" value="">Delete
									Hardware</label>
							</div>
						</div>
						<div class="col-md-4">
							<h5>Software Permissions</h5>
							<div class="checkbox">
								<label><input type="checkbox" value="">Create
									Software</label>
							</div>
							<div class="checkbox">
								<label><input type="checkbox" value="">Edit
									Software</label>
							</div>
							<div class="checkbox">
								<label><input type="checkbox" value="">Delete
									Software</label>
							</div>
						</div>
						<div class="row">
							<div class="col-md-4">
								<a class="btn btn-default" href="insert-success" role="button">Create
									Role</a>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
	<jsp:include page="/WEB-INF/includes/footer.jsp" />
</body>
</html>