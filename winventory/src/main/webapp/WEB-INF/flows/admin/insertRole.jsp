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
						<form action="insert-role" method="post">
							<div class="row">
								<div class="form-group col-md-4" id="roleTitle">
									<label for="role">Role Title:</label> <input type="text"
										name="roleTitle" class="form-control">
								</div>
							</div>
							<h4>Select the Permissions this role should have:</h4>
							<div class="row">
								<div class="col-md-4">
									<div class="radio">
										<label> <input type="radio" name="adminPerms"
											value="adminPerms"> Admin Permissions
										</label>
									</div>
								</div>
							</div>

							<!-- TODO: disable checkboxes if admin radio is selected -->

							<div class="col-md-4" id="user_div">
								<h5>User Permissions</h5>
								<div class="checkbox">
									<label><input type="checkbox" value="createUser" name="createUser">Create
										User</label>
								</div>
								<div class="checkbox">
									<label><input type="checkbox" value="editUser" name="editUser">Edit
										User</label>
								</div>
								<div class="checkbox">
									<label><input type="checkbox" value="deleteUser" name="deleteUser">Delete
										User</label>
								</div>
							</div>
							<div class="col-md-4" id="hardware_div">
								<h5>Hardware Permissions</h5>
								<div class="checkbox">
									<label><input type="checkbox" value="createHardware" name="createHardware">Create
										Hardware</label>
								</div>
								<div class="checkbox">
									<label><input type="checkbox" value="editHardware" name="editHardware">Edit
										Hardware</label>
								</div>
								<div class="checkbox">
									<label><input type="checkbox" value="deleteHardware" name="deleteHardware">Delete
										Hardware</label>
								</div>
							</div>
							<div class="col-md-4" id="software_div">
								<h5>Software Permissions</h5>
								<div class="checkbox">
									<label><input type="checkbox" value="createSoftware" name="createSoftware">Create
										Software</label>
								</div>
								<div class="checkbox">
									<label><input type="checkbox" value="editSoftware" name="editSoftware">Edit
										Software</label>
								</div>
								<div class="checkbox">
									<label><input type="checkbox" value="deleteSoftware" name="deleteSoftware">Delete
										Software</label>
								</div>
							</div>

							<button type="submit" class="btn btn-default">Create
								Role</button>
						</form>
					</div>
				</div>
			</div>
		</div>
	</div>
	<jsp:include page="/WEB-INF/includes/footer.jsp" />
</body>
</html>